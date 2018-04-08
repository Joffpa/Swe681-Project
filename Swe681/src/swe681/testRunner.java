package swe681;

import swe681.resources.DataDriver;
import swe681.resources.IAMDatabaseAuthenticationTester;
import java.sql.ResultSet;

public class testRunner {

	private DataDriver dataDriver = new DataDriver();
	public String response = "";	
	
	public String getResponse() {
		return response;
	}

	public void setResponse(String str) {
		this.response = str.trim();
	}
	
	public String testDB() {
		try {
			System.out.println("Test Start");
			ResultSet rs = dataDriver.getAirports();
			System.out.println("Test ResultSet");
			while(rs.next()) {
				System.out.println(rs.getString("CODE"));
			}
			System.out.println("Test End");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "done";
	}
}
