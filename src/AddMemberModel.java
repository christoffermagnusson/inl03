import java.sql.*;
import java.util.*;
import javax.swing.*;

public class AddMemberModel{


Connection c = null;
Statement s = null;
ResultSet res = null;

  public AddMemberModel(){
    createConnection();
    openStatement();
  }

    public void createConnection(){
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
  }

  public void openStatement(){
    try{
      s = c.createStatement();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
  }

 

}