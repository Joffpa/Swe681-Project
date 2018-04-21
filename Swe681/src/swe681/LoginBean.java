package swe681;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import swe681.resources.AppLog;
import swe681.resources.AuthenticationService;
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
		if(this.loginname == null || this.loginname.isEmpty() ||
				this.password == null && this.password.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage("You must enter a login name and password."));
			
			return null;
		}
		
		//TODO: Addtional input validations
		
		//TODO: add login logic here
		AuthenticationService auth = new AuthenticationService();
		UserProfile user = auth.loginUser(this.loginname, this.password);
		
		if(user != null) {
			
			//put the logged in user object in session
	        FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().getSessionMap().put("user", user);
			
			AppLog.getLogger().info("Login success, login name: " + user.getLoginname());
			return "LandingPage";
		}
		//returning null re-displays current page, any errors 		
		return null;
	}
	
	public String createAccount() {
		return "CreateAccount";
	}
	
}
