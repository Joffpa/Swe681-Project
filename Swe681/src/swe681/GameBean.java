package swe681;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import swe681.resources.AppLog;
import swe681.resources.DataDriver;
import swe681.resources.GameInstance;
import swe681.resources.UserProfile;

@ManagedBean
@RequestScoped
public class GameBean {

	public GameInstance gameInstance;
	public String submittedMove;
	public String submittedCapture;
	public boolean isLoggedInUsersTurn;

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

	public boolean getIsLoggedInUsersTurn() {
		return this.isLoggedInUsersTurn;
	}

	@PostConstruct
	public void init() {
		try {
			// In @PostConstruct (will be invoked immediately after construction and
			// dependency/property injection).
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
			UserProfile currentUser = (UserProfile) session.getAttribute("user");
			// TODO: get current game instance from logged in user
			DataDriver driver = new DataDriver();
			this.gameInstance = driver.getGameInstanceDetails(currentUser.currentGameId);

			if (!this.gameInstance.player1.equals(currentUser.loginname)
					&& !this.gameInstance.player2.equals(currentUser.loginname)) {
				this.gameInstance = null;
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("You are not a valid player for this game."));
			} else {
				if (!this.gameInstance.player1.equals(this.gameInstance.currentPlayerTurn)
						&& !this.gameInstance.player2.equals(this.gameInstance.currentPlayerTurn)) {
					this.gameInstance = null;
					FacesContext.getCurrentInstance().addMessage("",
							new FacesMessage("Game has entered an illegal state and is invalidated."));
					// TODO invalidate game
					this.isLoggedInUsersTurn = false;
				} else {
					if (this.gameInstance.currentPlayerTurn.equals(currentUser.loginname)) {
						this.isLoggedInUsersTurn = true;
					} else {
						this.isLoggedInUsersTurn = false;
					}
				}
			}
		} catch (Exception e) {
			AppLog.getLogger().severe("There was an exception in GameBean.init(): " + e.getMessage());
		}
	}

	public String submitMove() {

		return null;
	}

}
