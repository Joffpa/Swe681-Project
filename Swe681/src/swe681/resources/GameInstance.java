package swe681.resources;

public class GameInstance {
	public double gameId ;
	public String player1;
	public String player2;
	public String currentState;
	public String currentPlayerTurn;
	public int player1Prisoners;
	public int player2Prisoners;;
	public int player1FinalScore;
	public int player2FinalScore;	
	
	public String[][] gameState;
	
	public String[][] getGameState(){
		return this.gameState;
	}
	
	public double getGameId() {
		return this.gameId;
	}
	
	public String getPlayer1() {
		return this.player1;
	}

	public String getPlayer2() {
		return this.player2;
	}
	
	public String getCurrentState() {
		return this.currentState;
	}
	
	public String getCurrentPlayerTurn() {
		return this.currentPlayerTurn;
	}
	
	public int getPlayer1Prisoners() {
		return this.player1Prisoners;
	}
	
	public int getPlayer2Prisoners() {
		return this.player2Prisoners;
	}
	
	public int getPlayer1FinalScore() {
		return this.player1FinalScore;
	}
	
	public int getPlayer2FinalScore() {
		return this.player2FinalScore;
	}
}
