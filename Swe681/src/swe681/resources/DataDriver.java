package swe681.resources;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class DataDriver {
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";
	static final String DB_USER = "jpannee";
	static final String DB_PASS = "ystone";

	private Connection conn = null;

	public DataDriver() {
		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (Exception ex) {
			System.out.println("exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public boolean userJoinGame(double gameId, String loginnamePlayer2) {
		try {
			conn.setAutoCommit(false);
			PreparedStatement pStmt = conn.prepareStatement("if exists (select Loginname from UserProfile where Loginname = ? AND CurrentGameId = 0) update UserProfile set CurrentGameId = ? where Loginname = ?; ");
			pStmt.setString(1, loginnamePlayer2);
			pStmt.setDouble(2, gameId);		
			pStmt.setString(3, loginnamePlayer2);
			pStmt.execute();

			PreparedStatement pStmt2 = conn.prepareStatement("if exists (select Player1 from GameInstance where GameId = ? AND Player2 is null) update GameInstance set Player2 = ? where GameId = ?; ");
			pStmt2.setDouble(1, gameId);
			pStmt2.setString(2, loginnamePlayer2);
			pStmt2.setDouble(3, gameId);	
			pStmt2.execute();
			conn.commit();
			conn.setAutoCommit(true);			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				AppLog.getLogger().severe("There was an exception running DataDriver.userJoinGame when trying to rollback transaction: " + e1.getMessage());
				return false;
			}
			AppLog.getLogger().severe("There was an exception running DataDriver.userJoinGame: " + e.getMessage());
			return false;
		}
		return true;		
	}
	

	public GameInstance getGameInstanceDetails(int gameId) {
		ResultSet rsInstance = getGameInstanceRS(gameId);
		GameInstance game = mapResultSetToGameInstance(rsInstance).get(0);
		ResultSet rsState = getGameStateRS(gameId);
		game.gameState = mapResultSetToStringArray(rsState);

		return game;
	}

	public ArrayList<UserProfile> getAllPlayers() {
		ResultSet rs = this.getAllPlayersRS();
		return this.mapResultSetToUserProfiles(rs);

	}

	private ResultSet getAllPlayersRS() {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement("select   Username," + "  Loginname," + "  PasswordHash,"
					+ "  HashSalt," + "  CurrentGameId," + "  Wins," + "  Losses  from UserProfile");
			rs = pStmt.executeQuery();
		} catch (Exception e) {
			AppLog.getLogger().severe("There was an exception running DataDriver.getAllPlayersRS: " + e.getMessage());
		}
		return rs;
	}
	
	public UserProfile getPlayerByLoginname(String loginname){
		ResultSet rs = getPlayerByLoginnameRS(loginname);
		return this.mapResultSetToUserProfiles(rs).get(0);
	}
	
	private ResultSet getPlayerByLoginnameRS(String loginname){
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement("select Username," + "  Loginname," + "  PasswordHash,"
					+ "  HashSalt," + "  CurrentGameId," + "  Wins," + "  Losses  from UserProfile where Loginname = ?");
			pStmt.setString(1, loginname);
			rs = pStmt.executeQuery();
			rs.last(); 
			int total = rs.getRow();
			if(total > 1) {
				throw new Exception("There are more than one user in the database with the loginname " + loginname);
			}else {
				//reset back to first
				rs.beforeFirst();
			}
		} catch (Exception e) {
			AppLog.getLogger().severe("There was an exception running DataDriver.getAllPlayersRS: " + e.getMessage());
		}
		return rs;
	}
		
 
	private ArrayList<UserProfile> mapResultSetToUserProfiles(ResultSet rs) {
		ArrayList ll = new ArrayList<UserProfile>();
		try {

			// Fetch each row from the result set
			while (rs.next()) {
				String Username = rs.getString("Username");
				String Loginname = rs.getString("Loginname");
				String PasswordHash = rs.getString("PasswordHash");
				String HashSalt = rs.getString("HashSalt");
				int CurrentGameId = rs.getInt("CurrentGameId");
				int Wins = rs.getInt("Wins");
				int Losses = rs.getInt("Losses");

				UserProfile user = new UserProfile();
				user.username = Username;
				user.loginname = Loginname;
				user.passwordHash = PasswordHash;
				user.hashSalt = HashSalt;
				user.currentGameId = CurrentGameId;
				user.wins = Wins;
				user.losses = Losses;

				ll.add(user);
			}
		} catch (Exception se) {
			AppLog.getLogger().severe("Exception in DataDriver.mapResultSetToUserProfiles: " + se.getMessage());
		}
		return ll;
	}

	private ResultSet getGameInstanceJoinableRS() {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement(
					"select GameId, Player1, Player2, CurrentState, CurrentPlayerTurn, Player1Prisoners, Player2Prisoners, Player1FinalScore, Player2FinalScore "
							+ " from GameInstance " + " where Player2 is null and CurrentState = 'inprogress'");
			rs = pStmt.executeQuery();
		} catch (Exception e) {
			AppLog.getLogger()
					.severe("There was an exception running DataDriver.getGameInstanceJoinable: " + e.getMessage());
		}
		return rs;
	}

	private ResultSet getGameInstanceRS(int gameId) {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement(
					"select GameId, Player1, Player2, CurrentState, CurrentPlayerTurn, Player1Prisoners, Player2Prisoners, Player1FinalScore, Player2FinalScore, Info "
							+ " from GameInstance where GameId = ? ");
			pStmt.setInt(1, gameId);
			rs = pStmt.executeQuery();
		} catch (Exception e) {
			AppLog.getLogger().severe("There was an exception running DataDriver.getGameInstanceRS: " + e.getMessage());
		}
		return rs;
	}

	private ResultSet getGameStateRS(int gameId) {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement(
					"select GameId, BoardRow, BoardCol, OwnedBy " + " from GameState " + " where GameId = ? ");
			pStmt.setInt(1, gameId);
			rs = pStmt.executeQuery();
		} catch (Exception e) {
			AppLog.getLogger().severe("There was an exception running DataDriver.getGameStateRS: " + e.getMessage());
		}
		return rs;
	}

	public ArrayList<GameInstance> getGameInstanceJoinable() {
		ResultSet rs = getGameInstanceJoinableRS();
		return mapResultSetToGameInstance(rs);
	}

	private ResultSet getGameHistoryRS(String playerLoginName) {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement(
					"select GameId, Player1, Player2, CurrentState, CurrentPlayerTurn, Player1Prisoners, Player2Prisoners, Player1FinalScore, Player2FinalScore "
							+ " from GameInstance " + " where (Player2 = ? or Player1 = ?) and CurrentState = 'done'");
			pStmt.setString(1, playerLoginName);
			pStmt.setString(2, playerLoginName);
			rs = pStmt.executeQuery();
		} catch (Exception e) {
			AppLog.getLogger().severe("There was an exception running DataDriver.getGameHistoryRS: " + e.getMessage());
		}
		return rs;
	}

	public ArrayList<GameInstance> getGameHistoryForPlayer(String playerLoginName) {
		ResultSet rs = getGameHistoryRS(playerLoginName);
		return mapResultSetToGameInstance(rs);
	}

	private ArrayList<GameInstance> mapResultSetToGameInstance(ResultSet rs) {
		ArrayList ll = new ArrayList<GameInstance>();
		try {

			// Fetch each row from the result set
			while (rs.next()) {
				int gameId = rs.getInt("GameId");
				String player1 = rs.getString("Player1");
				String player2 = rs.getString("Player2");
				String currentState = rs.getString("CurrentState");
				String currentPlayerTurn = rs.getString("CurrentPlayerTurn");
				int player1Prisoners = rs.getInt("Player1Prisoners");
				int player2Prisoners = rs.getInt("Player2Prisoners");
				int player1FinalScore = rs.getInt("Player1FinalScore");
				int player2FinalScore = rs.getInt("Player2FinalScore");

				GameInstance game = new GameInstance();
				game.gameId = gameId;
				game.player1 = player1;
				game.player2 = player2;
				game.currentState = currentState;
				game.currentPlayerTurn = currentPlayerTurn;
				game.player1Prisoners = player1Prisoners;
				game.player2Prisoners = player2Prisoners;
				game.player1FinalScore = player1FinalScore;
				game.player2FinalScore = player2FinalScore;

				ll.add(game);
			}
		} catch (Exception se) {
			AppLog.getLogger().severe("Exception in DataDriver.mapResultSetToGameInstance: " + se.getMessage());
		}
		return ll;
	}

	public String[][] mapResultSetToStringArray(ResultSet rs) {
		String[][] stringArray = new String[9][9];
		try {
			while (rs.next()) {
				String row = rs.getString("BoardRow");
				int col = Integer.parseInt(rs.getString("BoardCol"));
				String player = rs.getString("OwnedBy");
				if (row.equals("A")) {
					stringArray[0][col] = player;
				} else if (row.equals("B")) {
					stringArray[1][col] = player;
				} else if (row.equals("C")) {
					stringArray[2][col] = player;
				} else if (row.equals("D")) {
					stringArray[3][col] = player;
				} else if (row.equals("E")) {
					stringArray[4][col] = player;
				} else if (row.equals("F")) {
					stringArray[5][col] = player;
				} else if (row.equals("G")) {
					stringArray[6][col] = player;
				} else if (row.equals("H")) {
					stringArray[7][col] = player;
				} else if (row.equals("I")) {
					stringArray[8][col] = player;
				}
			}
		} catch (Exception e) {
			AppLog.getLogger()
					.severe("There was an exception running DataDriver.mapResultSetToStringArray: " + e.getMessage());
		}
		return stringArray;
	}

	public ArrayList<GameLog> getGameLogForInstance(double gameId) {
		ResultSet rs = getGameLogForInstanceResultSet(gameId);
		return mapResultSetToGameLog(rs);
	}

	private ResultSet getGameLogForInstanceResultSet(double gameId) {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement(
					"select GameId, Player, MovePlayed, DatePlayed " + " from GameLog " + " where GameId = ? ");
			pStmt.setDouble(1, gameId);
			rs = pStmt.executeQuery();
		} catch (Exception e) {
			AppLog.getLogger().severe(
					"There was an exception running DataDriver.getGameLogForInstanceResultSet: " + e.getMessage());
		}
		return rs;
	}

	private ArrayList<GameLog> mapResultSetToGameLog(ResultSet rs) {
		ArrayList ll = new ArrayList<GameLog>();
		try {

			// Fetch each row from the result set
			while (rs.next()) {
				int gameId = rs.getInt("GameId");
				String player = rs.getString("Player");
				String move = rs.getString("MovePlayed");
				Timestamp time = rs.getTimestamp("DatePlayed");

				GameLog log = new GameLog();
				log.gameId = gameId;
				log.player = player;
				log.movePlayed = move;
				log.datePlayed = time;
				ll.add(log);
			}
		} catch (Exception se) {
			AppLog.getLogger().severe("Exception in DataDriver.mapResultSetToGameLog: " + se.getMessage());
		}
		return ll;
	}
	

	
}
