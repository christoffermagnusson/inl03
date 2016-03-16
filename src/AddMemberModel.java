import java.sql.*;
import java.util.*;
import java.util.Date;
import javax.swing.*;
import java.text.*;

public class AddMemberModel{


Connection c = null;
Statement s = null;
ResultSet res = null;


private int id;




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
  public ArrayList<String> initTeams(){
       ArrayList<String> teams = new ArrayList<String>();
       try{
        res = s.executeQuery("SELECT DISTINCT team FROM funktion");
        while(res.next()){
          teams.add(res.getString(1));
        }
       }
       catch(SQLException e){
        JOptionPane.showMessageDialog(null,e.getMessage());
       }
       return teams;
  }
  
  public int setId(){
    // Kolla en tillgänglig array efter ID:n annars.
    int tmp =0;
    try{
      res = s.executeQuery("SELECT COUNT(*) FROM medlem");
      tmp = res.getInt(1)+1;
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
    return tmp;
  }

  public String getDates(){
    DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
    String now = df.format(new Date()); 
    return now;
  }

  public String convertBirthday(JComboBox<String> day,JComboBox<String> month,JComboBox<String> year){
    String birth = year.getSelectedItem()+"-"+month.getSelectedItem()+"-"+day.getSelectedItem();
    return birth;
  }



  public void insertNewMember(int id,String givenName,String familyName,String email,String gender,
    String birth,String memberSince,int active){
    try{
      PreparedStatement prepSt = c.prepareStatement("INSERT INTO medlem VALUES(?,?,?,?,?,?,?,?)");
      prepSt.setInt(1,id);
      prepSt.setString(2,String.format("%s",givenName));
      prepSt.setString(3,String.format("%s",familyName));
      prepSt.setString(4,String.format("%s",email));
      prepSt.setString(5,String.format("%s",gender));
      prepSt.setString(6,String.format("%s",birth));
      prepSt.setString(7,String.format("%s",memberSince));
      prepSt.setInt(8,active);
      prepSt.executeUpdate();



      //c.commit();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }

  }
  public void setRoles(int id,int role,String team){
    try{
      
      PreparedStatement prepRoles = c.prepareStatement("INSERT INTO funktion VALUES(?,?,?)");
      prepRoles.setInt(1,id);
      prepRoles.setInt(2,role);
      prepRoles.setString(3,team);
      prepRoles.executeUpdate();
    
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
  }




 

}