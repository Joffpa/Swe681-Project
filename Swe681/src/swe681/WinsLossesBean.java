package swe681;

import java.util.List;

import javax.annotation.PostConstruct;

import swe681.resources.DataDriver;
import swe681.resources.GameInstance;
import swe681.resources.UserProfile;

public class WinsLossesBean {

	public List<UserProfile> allUsers;
	
	@PostConstruct
	public void init() {
		// In @PostConstruct (will be invoked immediately after construction and
		// dependency/property injection).
		DataDriver driver = new DataDriver();
		this.allUsers = driver.getAllPlayers();
	}
	
	public List<UserProfile> getAllUsers(){
		return this.allUsers;
	}	
}
