import java.sql.*;
import java.util.*;
import javax.swing.*;

public class FormatList{

  DBConnection db = new DBConnection();
  Statement st = null;
  ResultSet res = null;
  
  

  public FormatList(){
    openStatement(db.createConnection());

  }

  public void openStatement(Connection c){
    try{
      st = c.createStatement();
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,e.getMessage());
    }
  }
  public Statement getStatement(){
    return st;
  }


  /**
  *End of creation of statement to use in listeners
  */

  public ResultSet initList(Statement s){
    String getList = String.format("SELECT * FROM medlem");
    try{
      res = s.executeQuery(getList);
    }
    catch(SQLException e){
      JOptionPane.showMessageDialog(null,"SQL fel!!! " + e.getMessage());
    }
    return res;
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
  public Object[][] getData(ResultSet res){
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
      int active = res.getInt(8);

      data[index][0]=id;
      data[index][1]=givenName;
      data[index][2]=familyName;
      data[index][3]=email;
      data[index][4]=gender;
      data[index][5]=birth;
      data[index][6]=memberSince;
      data[index][7]=active;

      index++;

    }
    res.close();
  }
  catch(SQLException e){
  }

    return data;
  }
  
  
  

  /*public void formatElements(){
    int tmp = 0;

    for(int i=0; i<ml.size(); i++){
       char[] c = ml.get(i).getGivenName().toCharArray();
       for(int j=c.length; j<20; j++){
       tmp=j;
       }
        String f = "%-"+tmp+"s";
        ml.get(i).setGivenName(String.format(f,ml.get(i).getGivenName()));

        c = ml.get(i).getFamilyName().toCharArray();
        for(int k=c.length; k<30; k++){
          tmp=k;
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
