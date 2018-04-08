package swe681.resources;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class DataDriver {
	static final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	static final String DB_URL = "jdbc:oracle:thin:@apollo.vse.gmu.edu:1521:ite10g";
	static final String DB_USER = "jpannee";
	static final String DB_PASS = "ystone";

	private Connection conn = null;

	public DataDriver() {
		try {

			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
		} catch (Exception ex) {
			System.out.println("exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	public ResultSet getAirports() {
		ResultSet rs = runQuery("select * from Airport");
		return rs;
	}
	
	private ResultSet runQuery(String query) {
		ResultSet rs = null;
		try {
			PreparedStatement pStmt = conn.prepareStatement(query);
			rs = pStmt.executeQuery();
		} catch (Exception e) {
		}
		return rs;
	}


}
