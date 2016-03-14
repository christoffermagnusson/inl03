import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class ListController implements ActionListener{

ListModel model;
ListView view;
String query = null;

public ListController(ListView v, ListModel m){
  view=v;
  model=m;
}

public void actionPerformed(ActionEvent e){
  if(e.getSource()==view.getSearchField()){
    query = "SELECT * FROM person WHERE name = '"+ view.getSearchField().getText() +"'";
    view.updateList(model.initList(query));
  }
  if(e.getSource()==view.getSearchButton()){
    query = "SELECT * FROM person WHERE name = '"+ view.getSearchField().getText() +"'";
    view.updateList(model.initList(query));
  }

}

public String getQuery(){
  return query;
}

}