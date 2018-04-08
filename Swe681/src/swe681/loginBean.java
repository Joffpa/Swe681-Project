package swe681;

import javax.faces.bean.ManagedBean;

import swe681.resources.IAMDatabaseAuthenticationTester;

@ManagedBean
public class loginBean {

	public String response = "";	
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String str) {
		this.response = str.trim();
	}
	
	public String testDB() {
		try {
			IAMDatabaseAuthenticationTester.run(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "done";
	}
	
}
