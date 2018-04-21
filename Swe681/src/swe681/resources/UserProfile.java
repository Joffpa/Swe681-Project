package swe681.resources;

public class UserProfile {

	public String username;
	public String loginname;
	public String passwordHash;
	public String hashSalt;
	public int currentGameId;
	public int wins;
	public int losses;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String str) {
		this.username = str.trim();
	}

	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String str) {
		this.loginname = str.trim();
	}

	public String getPasswordHash() {
		return this.passwordHash;
	}

	public void setPasswordHash(String str) {
		this.passwordHash = str.trim();
	}

	public String getHashSalt() {
		return this.hashSalt;
	}

	public void setHashSalt(String str) {
		this.hashSalt = str.trim();
	}

	public int getCurrentGameId() {
		return this.currentGameId;
	}

	public void setCurrentGameId(int id) {
		this.currentGameId = id;
	}

	public int getWins() {
		return this.wins;
	}

	public void setWins(int id) {
		this.wins = id;
	}

	public int getLosses() {
		return this.losses;
	}

	public void setLosses(int id) {
		this.losses = id;
	}
}
