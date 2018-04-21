package swe681;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.model.ListDataModel;

import swe681.resources.DataDriver;
import swe681.resources.GameInstance;
import swe681.resources.GameLog;

@ViewScoped
public class HistoryBean  extends BaseBean  {
	
	public HistoryBean() {
		super();
	}

	public ListDataModel<GameInstance> allPastGames;
	public List<GameLog> gameLogForInstance;
	
	
	@PostConstruct
	public void init() {
		// In @PostConstruct (will be invoked immediately after construction and
		// dependency/property injection).
		//TODO: Use the logged in player as the playerLoginName		
		DataDriver driver = new DataDriver();
		this.allPastGames = new ListDataModel(driver.getGameHistoryForPlayer("User1"));
	}

	
	public List<GameLog> getGameLogForInstance(){
		return this.gameLogForInstance;
	}
		
	public ListDataModel<GameInstance> getAllPastGames(){
		return this.allPastGames;
	}	
	
	
	public String seeHistory() {		
		GameInstance game = allPastGames.getRowData();
		DataDriver driver = new DataDriver();
		this.gameLogForInstance = driver.getGameLogForInstance(game.gameId);
		
		return null;
	}
}
