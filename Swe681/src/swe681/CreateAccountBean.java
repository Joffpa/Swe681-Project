package swe681;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
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
		this.password = x;
	}
	
	public String getPassword() {
		return this.password;
	}
		
	public void setPasswordConfirm(String x) {
		this.passwordConfirm = x;
	}
	
	public String getPasswordConfirm() {
		return this.passwordConfirm;
	}
	
	public String createAccount() {
		boolean accountCreated = true;
		if(accountCreated) {
			return "AccountCreated";
		}			
		return null;
	}
	
	public String returnToWelcome() {
		return "Welcome";
	}
	
}
