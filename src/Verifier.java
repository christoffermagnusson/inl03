import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Verifier extends InputVerifier{


  public boolean verify(JComponent input){
    String name = input.getName();
    String text = "";
    if(name.equals("givenName") || name.equals("familyName")){
       text = ((JTextField) input).getText();
       if(text.isEmpty()){
        return false;
       }
       else if(checkInput("[a-zA-Z[^0-9]]{1,50}",text)==false){ 
        return false;
       }
     
    }
    else if(name.equals("email")){
        text = ((JTextField) input).getText();
        if(text.isEmpty()){
          return false;
        }
        else if(checkInput("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b",text)==false){
          return false; 
        }
    }
    
  
    return true;
}
  public boolean checkInput(String restriction,String input){
    Pattern p = Pattern.compile(restriction);
    Matcher m = p.matcher(input);
    boolean match = m.matches();

    return match;
  }

}




