package swe681;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public class BaseBean {
	public BaseBean() {
		setHeaders();
	}
	
    public void setHeaders() {
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setHeader("Cache-Control", "no-cache, no-store");
    }

}
