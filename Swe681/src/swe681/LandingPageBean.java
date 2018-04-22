package swe681;

import swe681.resources.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

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

	public List<GameInstance> getAllJoinableGames() {
		return this.allJoinableGames;
	}

	public String joinGame(GameInstance game) {		
		//TODO: check to make sure user is allowed to join this game
		// is the player already in a game? 
		//get the latest user data for this user
		UserProfile userInSession = contextHelper.getLoggedInUser();
		if(userInSession == null) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("There was a problem getting your account, please log in again."));
			return null;
		}
		
		UserProfile user = dataDriver.getPlayerByLoginname(userInSession.loginname);
		//update session to make sure we have the latest data
		contextHelper.setLoggedInUser(user);
		
		if(user.currentGameId > 0) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("You are already in a game, and cannot join a new one."));
			return null;
		}else {
			//set current players gameid, set player2 on the game
			boolean joinSuccess = dataDriver.userJoinGame(game.gameId, user.loginname);
			if(joinSuccess) {
				return "Game";				
			}else {
				return null; 
			}			
		}
	}

	public String startNewGame() {

		return null;
	}

}
