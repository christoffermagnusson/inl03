import javax.swing.*;
import java.awt.*;

public class Table extends JFrame{

FormatList f = new FormatList();

  
  public Table(){
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    String [] names = {"id","first","last","email","gender","birth","since","active"};
    JTable test = new JTable(f.getData(f.initList(f.getStatement())),names);

    add(test);
  }
  public static void main(String[]args){
    new Table();
  }
}
