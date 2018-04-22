package swe681;

import swe681.resources.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class LandingPageBean extends BaseBean {
	
	public List<GameInstance> allJoinableGames;
		
	public LandingPageBean() {
		super();
	}
	
	@PostConstruct
	public void init() {
		// In @PostConstruct (will be invoked immediately after construction and
		// dependency/property injection).
		DataDriver driver = new DataDriver();
		this.allJoinableGames = driver.getGameInstanceJoinable();
	}

	
	public void setAllJoinableGames(LinkedList<GameInstance> games) {
		this.allJoinableGames = games;
	}
	
	public List<GameInstance> getAllJoinableGames(){
		return this.allJoinableGames;
	}	
	
	public String joinGame(GameInstance game) {
		//TODO: check to make sure user is allowed to join this game
		// is the player already in a game? 
		
		//if true, redirect to game page
		
		
		return null; 
	}
}
