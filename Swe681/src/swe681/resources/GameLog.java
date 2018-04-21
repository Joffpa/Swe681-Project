package swe681.resources;

import java.util.Date;

public class GameLog {

	public double gameId;
	public String player;
	public String movePlayed;
	public Date datePlayed;
	
	public double getGameId() {
		return this.gameId;
	}
	
	public String getPlayer() {
		return this.player;
	}
	
	public String getMovePlayed() {
		return this.movePlayed;
	}
	
	public Date getDatePlayed() {
		return this.datePlayed;
	}
}
