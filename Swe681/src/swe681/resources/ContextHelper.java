package swe681.resources;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class ContextHelper {

	public void setLoggedInUser(UserProfile user) {
		// put the logged in user object in session
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("user", user);		
	}
	
	public UserProfile getLoggedInUser() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
		return (UserProfile) session.getAttribute("user");		
	}

}
