import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ListView extends JFrame{
  ListController controller = new ListController(this,new ListModel());
  ListModel model;


  InternalListView listPanel; 
  
  JPanel searchPanel = new JPanel(new GridLayout(1,2));
  JTextField searchField = new JTextField(20);
  JButton searchButton = new JButton("Search");


  public ListView(ListModel m){
    model = m;


    setLayout(new GridLayout(1,2));
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);


    searchField.addActionListener(controller);
    searchButton.addActionListener(controller);
    searchPanel.add(searchField);
    searchPanel.add(searchButton);

    add(searchPanel);
    
    listPanel = new InternalListView(model.initList("SELECT * FROM person"));
    add(listPanel);
    



  }
  public JButton getSearchButton(){
    return searchButton;
  }
  public JTextField getSearchField(){
    return searchField;
  }
  public void updateList(DefaultListModel<String> def){
    listPanel.setList(def);
  }
}








