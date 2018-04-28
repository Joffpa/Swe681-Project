package swe681;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import swe681.resources.AppLog;
import swe681.resources.AuthenticationService;
import swe681.resources.AuthenticationService.AuthResult;
import swe681.resources.UserProfile;

import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

@ManagedBean
@RequestScoped
public class LoginBean extends BaseBean {
	public String loginname = "";
	public String password = "";

	public LoginBean() {
		super();
		System.out.println(getClass().getClassLoader().getResource("logging.properties"));
	}

	public LoginBean(String loginname, String password) {
		super();
		this.loginname = loginname;
		this.password = password;
	}

	public void setLoginname(String x) {
		this.loginname = x.trim();
	}

	public String getLoginname() {
		return loginname;
	}

	public void setPassword(String x) {
		this.password = x.trim();
	}

	public String getPassword() {
		return this.password;
	}

	public String login() {
		if (this.loginname == null || this.loginname.isEmpty() || this.password == null || this.password.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("You must enter a login name and password."));
			return null;
		}

		if (!this.loginname.matches("^([A-Za-z0-9]{5,20})$")) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("Login name must be between 5 and 20 characters, and letters only."));
			return null;
		}

		if (!this.password.matches("^([A-Za-z0-9\\!\\@\\#\\$\\%\\^\\&\\*\\?\\.\\,\\;\\:]{5,20})$")) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(
					"Password must be between 5 and 20 characters, and letters, numbers or one of the following only: [! @ # $ % ^ & * ? . , : ;]."));
			return null;
		}

		AuthenticationService authService = new AuthenticationService(contextHelper.getRequest(), dataDriver,
				contextHelper);
		AuthResult result = authService.loginUser(this.loginname, this.password);

		if (result == AuthResult.LoginSuccess) {
			AppLog.getLogger().info("Login success, login name: " + this.loginname);
			return "LandingPage";
		} else if (result == AuthResult.LockedOut) {
			AppLog.getLogger().info("User Locked out: " + this.loginname);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(
					"You have been locked out of your account for 5 mins due to 3 incorrect password attempts."));
			return null;
		} else {
			AppLog.getLogger().info("Login failed for user: " + this.loginname);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage("Login failed, please try again."));
			return null;
		}
	}

	public String createAccount() {
		return "CreateAccount";
	}

}
