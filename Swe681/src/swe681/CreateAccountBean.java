package swe681;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import swe681.resources.AppLog;
import swe681.resources.AuthenticationService;
import swe681.resources.UserProfile;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

@ManagedBean
@RequestScoped
public class CreateAccountBean extends BaseBean {
	public String username = "";
	public String loginname = "";
	public String password = "";
	public String passwordConfirm = "";

	public CreateAccountBean() {
		super();
	}

	public CreateAccountBean(String username, String loginname, String password, String passwordConfirm) {
		super();
		this.username = username;
		this.loginname = loginname;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
	}

	public void setUsername(String x) {
		this.username = x.trim();
	}

	public String getUsername() {
		return username;
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

	public void setPasswordConfirm(String x) {
		this.passwordConfirm = x;
	}

	public String getPasswordConfirm() {
		return this.passwordConfirm.trim();
	}

	public String createAccount() {
		try {
			if (this.username == null || this.username.isEmpty() || this.loginname == null || this.loginname.isEmpty()
					|| this.password == null && this.password.isEmpty()
					|| this.passwordConfirm == null && this.passwordConfirm.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("You must enter a login name, password, and password confirmation."));
				return null;
			}

			if (!this.loginname.matches("^([A-Za-z0-9]{6,12})$")) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Username must be between 6 and 12 characters, and alphanumeric only."));
				return null;
			}
			
			//TODO: Add more input validations
			
			// if we got here, then all validations passed			
			AuthenticationService auth = new AuthenticationService();			
			UserProfile user = auth.createAccount(this.username, this.loginname, this.password);
			if (user!= null) {
				//put the logged in user object in session
		        FacesContext context = FacesContext.getCurrentInstance();
	            context.getExternalContext().getSessionMap().put("user", user);
				return "AccountCreated";
			}else {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("New account creation failed, please enter a unique username and loginname."));
			}			
		} catch (Exception e) {
			AppLog.getLogger().severe("Exception in  CreateAccountBean.createAccount(): " + e.getMessage());
		}
		// all else, return to same page
		return null;
	}

	public String returnToWelcome() {
		return "Welcome";
	}

}
