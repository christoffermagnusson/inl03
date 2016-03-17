import java.sql.*;
import java.util.*;
import javax.swing.*;


public class UpdateModel{

  Connection c = null;
  Statement s = null;
  ResultSet res = null;

  private String id;
  private String givenName;
  private String familyName;
  private String email;

  


  public UpdateModel(){
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
  public DefaultListModel<String> initList(String q){
    DefaultListModel<String> list = new DefaultListModel<String>();
    
    try{
      res = s.executeQuery(q);
      while(res.next()){

        String idString = res.getString(1);
        String givenName = res.getString(2);
        String familyName = res.getString(3);

        list.addElement(String.format("%s %s %s",idString,givenName,familyName));
      }
      res.close();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Member does not exist");
    }
    
    return list;

  }
  public String getEmail(String id){
    try{
      res = s.executeQuery("SELECT email FROM medlem WHERE id="+id);
      email = res.getString(1);
      res.close();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Member does not exist");
    }

    return email;
  }

   public void updateEmail(String id,String email){
    try{
      PreparedStatement prepEmail = c.prepareStatement("UPDATE medlem SET email=? WHERE id=?");
      prepEmail.setString(1,email);
      prepEmail.setString(2,id);
      prepEmail.executeUpdate();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Member does not exist");
    }
  }

  public void setId(String id){
    this.id=id;
  }
  public String getId(){
    return id;
  }
  public ArrayList<Integer> getRoles(String id){
    ArrayList<Integer> array = new ArrayList<Integer>();
    try{
      res = s.executeQuery("SELECT role FROM funktion WHERE id="+id+" ORDER BY role");
      while(res.next()){
         int role = res.getInt(1);
         array.add(role);
      }
      res.close();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Member does not exist");
    }
    return array;
  }


  public void createRole(int role,String id){
    try{
      res = s.executeQuery("SELECT * FROM funktion WHERE id="+id+" AND role="+role);
      if(!res.next()){
        res = s.executeQuery("SELECT * FROM medlem WHERE id="+id);
        if(res.next()){
        PreparedStatement prepRole = c.prepareStatement("INSERT INTO funktion VALUES(?,?,?)");
        prepRole.setString(1,id);
        prepRole.setInt(2,role);
        prepRole.setString(3,null);
        prepRole.executeUpdate();
      }
    }
      res.close();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Member does not exist");
    }
  }
  public void deleteRole(int role,String id){
    try{
      PreparedStatement delete = c.prepareStatement("DELETE FROM funktion WHERE id=? AND role=?");
      delete.setString(1,id);
      delete.setInt(2,role);
      delete.executeUpdate();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"Member does not exist");
    }
  }
  public void deleteMember(String id){
    try{
      PreparedStatement deleteFromMedlem = c.prepareStatement("DELETE FROM medlem WHERE id=?");
      deleteFromMedlem.setString(1,id);
      deleteFromMedlem.executeUpdate();
      PreparedStatement deleteFromFunktion = c.prepareStatement("DELETE FROM funktion WHERE id=?");
      deleteFromFunktion.setString(1,id);
      deleteFromFunktion.executeUpdate();
      PreparedStatement deleteFromChildren = c.prepareStatement("DELETE FROM children WHERE cid=?");
      deleteFromChildren.setString(1,id);
      deleteFromChildren.executeUpdate();
      PreparedStatement deleteFromParent = c.prepareStatement("DELETE FROM children WHERE pid=?");
      deleteFromParent.setString(1,id);
      deleteFromParent.executeUpdate();

    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
  }
 
 



}
