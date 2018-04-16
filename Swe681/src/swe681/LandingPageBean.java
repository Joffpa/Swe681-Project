package swe681;

import swe681.resources.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class LandingPageBean extends BaseBean {
	
	public List<GameInstance> allJoinableGames;
		
	public LandingPageBean() {
		super();
	}
	
	public void setAllJoinableGames(LinkedList<GameInstance> games) {
		this.allJoinableGames = games;
	}
	
	public List<GameInstance> getAllJoinableGames(){
		DataDriver driver = new DataDriver();
		this.allJoinableGames = driver.getGameInstanceJoinable();
		return this.allJoinableGames;
	}	
	
	public String joinGame(GameInstance game) {
		return null; 
	}
}
