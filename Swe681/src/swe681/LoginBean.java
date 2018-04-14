package swe681;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.faces.bean.ManagedBean;

@ManagedBean
@RequestScoped
public class LoginBean extends BaseBean {
	public String username = "";
	public String password = "";
	
	public LoginBean() {
		super();
	}
	
	public LoginBean(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	
	public void setUsername(String x) {
		this.username = x.trim();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setPassword(String x) {
		this.password = x;
	}
	public String getPassword() {
		return this.password;
	}
	
	public String login() {
		//TODO: add login logic here
		boolean loginSuccess = true;
		if(loginSuccess) {
			return "LandingPage";
		}
		//returning null re-displays current page, any errors 
		return null;
	}
	
	public String createAccount() {
		return "CreateAccount";
	}
	
}
