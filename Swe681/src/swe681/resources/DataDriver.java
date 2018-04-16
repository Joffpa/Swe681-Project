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

	private ResultSet getGameInstanceJoinableRS() {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement(
					"select GameId, Player1, Player2, CurrentState, CurrentPlayerTurn, Player1Prisoners, Player2Prisoners, Player1FinalScore, Player2FinalScore "
					+ " from GameInstance "
					+ " where player2 is null and CurrentState = 'inprogress'");
			rs = pStmt.executeQuery(); 
		} catch (Exception e) {
			AppLog.getLogger()
					.severe("There was an exception running DataDriver.getGameInstanceJoinable: " + e.getMessage());
		}
		return rs;
	}

	public ArrayList<GameInstance> getGameInstanceJoinable() {
		ArrayList ll = new ArrayList<GameInstance>();
		ResultSet rs = getGameInstanceJoinableRS();
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

				// Assuming you have a user object
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
		} catch (SQLException se) {
			AppLog.getLogger().severe("Exception in DataDriver.getGameInstanceJoinable: " + se.getMessage());
		}
		return ll;
	}

	public static boolean setGenericField(Object object, String fieldName, Object fieldValue) {
		Class<?> clazz = object.getClass();
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				field.set(object, fieldValue);
				return true;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
		return false;
	}
}
