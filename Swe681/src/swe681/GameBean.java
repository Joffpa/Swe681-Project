package swe681;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import swe681.resources.DataDriver;
import swe681.resources.GameInstance;
import swe681.resources.UserProfile;

@ManagedBean
@RequestScoped
public class GameBean {
	
	public GameInstance gameInstance;
	public String submittedMove;
	public String submittedCapture;
	
	public String getSubmittedMove() {
		return this.submittedMove;
	}
	public void setSubmittedMove(String str) {
		this.submittedMove = str.trim();
	}
	
	public String getSubmittedCapture() {
		return this.submittedCapture;
	}
	
	public void setSubmittedCapture(String str) {
		this.submittedCapture = str.trim();
	}
	
	public GameInstance getGameInstance() {
		return this.gameInstance;
	}
	
	@PostConstruct
	public void init() {
		// In @PostConstruct (will be invoked immediately after construction and
		// dependency/property injection).
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		UserProfile currentUser = (UserProfile)session.getAttribute("user");
		//TODO: get current game instance from logged in user
		DataDriver driver = new DataDriver();
		this.gameInstance = driver.getGameInstanceDetails(currentUser.currentGameId);
	}
	
	
	public String submitMove() {
		
		return null;
	}
	
	

}
