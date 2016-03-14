import javax.swing.JPanel;
import javax.swing.event.*;
import javax.swing.JList;
import javax.swing.*;
import java.awt.*;

public class InternalListView extends JPanel{

JList<String> list = new JList<String>();

  public InternalListView(DefaultListModel<String> d){
    setLayout(new GridLayout(1,1));
    setVisible(true);

    list.setModel(d);
    list.setVisibleRowCount(100);
    add(list);
  }
  public void setList(DefaultListModel<String> dList){
    list.setModel(dList);
  }
}
