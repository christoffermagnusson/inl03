import java.sql.*;
import java.util.*;
import javax.swing.*;
import java.beans.*;
import java.sql.Statement;

/**
*Class representing data to be used in the tab that is presenting the table of members. 
*Database connection and statement established for functionality regarding the list tab is
*located in this class. 
*
*@author  Christoffer Magnusson, William Lidholm
*@version 1.0
*/
public class ListModel implements Model{

  Connection c = null;
  Statement st = null;
  ResultSet res = null;
  
  

  private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

  public void addPropertyChangeListener(PropertyChangeListener l){
    pcs.addPropertyChangeListener(l);
  }
  public void addPropertyChangeListener(String propName,PropertyChangeListener l){
    pcs.addPropertyChangeListener(propName,l);
  }
  public void removePropertyChangeListener(PropertyChangeListener l){
    pcs.removePropertyChangeListener(l);
  }
  public void removePropertyChangeListener(String propName,PropertyChangeListener l){
    pcs.removePropertyChangeListener(propName,l);
  }
  

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
      c = DriverManager.getConnection("jdbc:sqlite:init");
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
 




  public ResultSet initList(String q){
    
    try{
      res = st.executeQuery(q);
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"SQL fel!!! " + e.getMessage());
    }
    return res;
  }

  
  public JComboBox<String> initTeams(){
    JComboBox<String> teams = new JComboBox<>();
    try{
      res = st.executeQuery("SELECT DISTINCT team FROM funktion");
      while(res.next()){
        String team = res.getString(1);
        teams.addItem(team);
      }
      res.close();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
    return teams;
  }
  public int getTeamMemberCount(String team){
    int count =0;
    try{
      res = st.executeQuery("SELECT COUNT(*) FROM funktion WHERE team='"+team+"'");
      count = res.getInt(1);
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
    return count;
  }
    
       

  
  public int getColumns(ResultSet rs){
    int columnCount = 0;
    try{
      ResultSetMetaData meta = rs.getMetaData();
      columnCount = meta.getColumnCount();

    }
    catch(SQLException e){

    }

    return columnCount;

  }

  /**
  *        Method to used to fill JTable in GUI with elements from database
  *        Goes through whole ResultSet and transfers columnvalues into Object[][]
  *        array.
  *
  *@param  processed ResultSet to be used for transfer from database.     
  *@return returns an Object[][] array with all elements to be added to the table.
  *        Object[1] being rows and Object[2] being columns.
  */
  public Object[][] getMemberData(ResultSet res){
    Object[][] data = new Object[200][getColumns(res)];
     
    int index = 0;
    try{
    
    while(res.next()){

      int id = res.getInt(1);
      String givenName = res.getString(2);
      String familyName = res.getString(3);
      String email = res.getString(4);
      String gender = res.getString(5);
      String birth = res.getString(6);
      String memberSince = res.getString(7);
      int tmp = res.getInt(8);
      String status = convertActive(tmp);

      data[index][0]=id;
      data[index][1]=givenName;
      data[index][2]=familyName;
      data[index][3]=email;
      data[index][4]=gender;
      data[index][5]=birth;
      data[index][6]=memberSince;
      data[index][7]=status;

      index++;

    }
    res.close();
  }
  catch(SQLException e){
  }

    return data;
  }
  public String convertActive(int tmp){
    String status="";
    if(tmp==0){
      status="Inactive";
    }
    else if(tmp==1){
      status="Active";
    }
    return status;
  }
  public Object[][] getTeamData(ResultSet res){
    Object[][] data = new Object[200][getColumns(res)];

    int index = 0;
    try{
      while(res.next()){
        int id = res.getInt(1);
        String givenName = res.getString(2);
        String familyName = res.getString(3);
        int tmp = res.getInt(4);
        String role = convertRole(tmp);
        String team = res.getString(5);

        data[index][0]=id;
        data[index][1]=givenName;
        data[index][2]=familyName;
        data[index][3]=role;
        data[index][4]=team;

        index++;
      }
      res.close();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
    return data;
  }
  public String convertRole(int tmp){
     String role = "";
        if(tmp==0){
          role="Player";
        }
        else if(tmp==1){
          role="Coach";
        }
        else if(tmp==2){
          role="Parent";
        }
        return role;
  }

  public boolean isNumeric(String s){
    try{
      int tmp = Integer.parseInt(s);
      return true;
    }
    catch(NumberFormatException e){
      return false;
    }
  }
  public String getElement(String q){
    String element = "";
    
    try{
      res = st.executeQuery(q);
      while(res.next()){
        element = res.getString(1);
        
      }
      
      res.close();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
    return element;
  }
  public ArrayList<String> getChildren(String q){
    ArrayList<String> childrenNames = new ArrayList<String>();
    ArrayList<String> childrenId = new ArrayList<String>();
    try{
      res = st.executeQuery(q);
      while(res.next()){
        String tmp = res.getString(1);
        childrenId.add(tmp);
      }
      res.close();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
    try{
      for(String s : childrenId){
      res = st.executeQuery("SELECT DISTINCT givenName,familyName FROM medlem,children WHERE id=cid AND cid="+s);
        while(res.next()){
          String tmp = res.getString(1)+" "+res.getString(2);
          childrenNames.add(tmp);

        }
    }
    res.close();
  }
  catch(SQLException e){
    JOptionPane.showMessageDialog(null,e.getMessage());
  }
  return childrenNames;
  }
  
  
  

  /*public void formatElements(){ // Testa denna med att räkna tmp++ i loopen istället
    int tmp = 0;

    for(int i=0; i<ml.size(); i++){
       char[] c = ml.get(i).getGivenName().toCharArray();
       for(int j=c.length; j<20; j++){
       tmp++;
       }
        String f = "%-"+tmp+"s";
        ml.get(i).setGivenName(String.format(f,ml.get(i).getGivenName()));

        c = ml.get(i).getFamilyName().toCharArray();
        for(int k=c.length; k<30; k++){
          tmp++;
        }
        f = "%-"+tmp+"s";
        ml.get(i).setFamilyName(String.format(f,ml.get(i).getFamilyName()));   
       
    }

  }*/
  /*public String[] getToJList(){
    String [] members = new String [ml.size()];
    for(int i=0; i<ml.size(); i++){
      members[i] = ml.get(i).toString();

    }
    return members;
  }*/


}
