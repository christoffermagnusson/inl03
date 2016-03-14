import java.sql.*;
import java.util.*;
import javax.swing.*;

public class ListModel{

  Connection c = null;
  Statement st = null;
  

  public ListModel(){
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
      c = DriverManager.getConnection("jdbc:sqlite:testdb");
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
    

  }

  public void openStatement(){
    try{
      st = c.createStatement();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
  }
  public DefaultListModel<String> initList(String q){
       ArrayList<String> array = new ArrayList<String>();
       DefaultListModel<String> list = new DefaultListModel<String>();
       
       try{
        ResultSet res=st.executeQuery(q);
        while(res.next()){
           String tmp = res.getString(1) + "," + res.getString(2);
           array.add(tmp);
        }
        for(int i=0; i<array.size(); i++){
          list.addElement(array.get(i));
        }
       }
       catch(SQLException e){
        JOptionPane.showMessageDialog(null,e.getMessage());
       }
       return list;
  }





}
