import java.sql.*;
import java.util.*;
import javax.swing.*;

public class DBConnection{
	Connection c = null;

	public DBConnection(){
	
	}

	public Connection createConnection(){
		try{
			Class.forName("org.sqlite.JDBC");
		}
		catch(ClassNotFoundException e){
              JOptionPane.showMessageDialog(null,e.getMessage());
		}

		try{
			c = DriverManager.getConnection("jdbc:sqlite:init");
		}
		catch(SQLException e){
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
    return c;
	}
	public Connection getConnection(){
		return c;

  }
}