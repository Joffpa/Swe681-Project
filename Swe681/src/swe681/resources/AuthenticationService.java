package swe681.resources;

public class AuthenticationService {

	public UserProfile user;
	
	
	public UserProfile loginUser(String loginName, String password) {
		//TODO: Authenticate
		//1 lookup user in DB with this login name (if not found loginUser method returns null, otherwise get all attributes for user and proceed)
		//2 get salt for this user
		//3 hash cleartext password passed in with salt from the db
		//4 compare salted hash with the salted hash stored in the db
		//5 if they match, set this user as the authenticated user and return the user object (user object will be stored in session
		//6 if they dont match, return null		
		
		UserProfile user = new UserProfile();
		user.loginname = "User1";
		user.currentGameId = 1;
		return user;
	}
	
	public UserProfile createAccount(String userName, String loginname, String password) {
		//TODO: check if account info is valid and create account
		//Check that username/login name not in database
		//if not taken, create a salt (random number)
		//hash password with the salt
		//insert new user info into database
		//return the user proflie object we just made if the account is created, otherwise return null
		
		
		return null;
	}
	
}
