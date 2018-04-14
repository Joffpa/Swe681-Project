package swe681;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.faces.bean.ManagedBean;

@ManagedBean
@RequestScoped
public class CreateAccountBean extends BaseBean {
	public String username = "";
	public String password = "";
	public String passwordConfirm = "";
	
	public CreateAccountBean() {
		super();
	}
	
	public CreateAccountBean(String username, String password, String passwordConfirm) {
		super();
		this.username = username;
		this.password = password;
		this.passwordConfirm = passwordConfirm;
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
