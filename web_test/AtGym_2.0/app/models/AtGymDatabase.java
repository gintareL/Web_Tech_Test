package models;
import java.sql.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DatabaseMetaData;
 
public class AtGymDatabase{

	Connection conn = null;
	
	
    public static Connection dbConnector (){
	
	try{
		String sDriverName = "org.sqlite.JDBC";
		Class.forName(sDriverName);
		Connection conn = DriverManager.getConnection("jdbc:sqlite:.\\app\\db\\atGym");
	//	JOptionPane.showMessageDialog(null, "gemacht");
		return conn;
	} catch(Exception e) {
		JOptionPane.showMessageDialog(null, e);
		return null;  
	}
     
		
 }

}