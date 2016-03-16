import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UpdateView extends JPanel{

JPanel searchPanel = new JPanel();
JPanel listPanel = new JPanel();
JPanel displayPanel = new JPanel();

Color custom = new Color(182,200,222);
UpdateModel model = new UpdateModel();


//searchPanel
JTextField searchText = new JTextField(20);
JButton searchButton = new JButton("Search");

//listPanel
JList<String> list;
JScrollPane listScrollPane;
JButton pick = new JButton("Pick member");

//displayPanel
JLabel changeEmail = new JLabel("Change Email");
JTextField changeEmailText = new JTextField(20);
JLabel changeRoles = new JLabel("Update roles");
JCheckBox player = new JCheckBox("Player");
JCheckBox coach = new JCheckBox("Coach");
JCheckBox parent = new JCheckBox("Parent");

JButton updateMember = new JButton("Update");

public UpdateView(){

  
  setLayout(new GridLayout(1,3));
  setVisible(true);
  setBackground(custom);
  add(searchPanel); add(listPanel); add(displayPanel);

  searchPanel.setLayout(new BoxLayout(searchPanel,BoxLayout.Y_AXIS));
  searchPanel.setBackground(custom);
  
  searchPanel.add(Box.createRigidArea(new Dimension(1,50)));
  searchText.setMaximumSize(searchText.getPreferredSize());
  searchText.setAlignmentX(CENTER_ALIGNMENT);
  searchText.addActionListener(a);
  searchPanel.add(searchText);
  searchPanel.add(Box.createRigidArea(new Dimension(1,20)));
  searchButton.setMaximumSize(searchButton.getPreferredSize());
  searchButton.setAlignmentX(CENTER_ALIGNMENT);
  searchButton.addActionListener(a);
  searchPanel.add(searchButton);

  list = new JList<String>(model.initList("SELECT * FROM medlem"));
    listScrollPane = new JScrollPane(list,
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));
  listPanel.add(listScrollPane);
  listPanel.add(Box.createRigidArea(new Dimension(1,20)));
  pick.setMaximumSize(pick.getPreferredSize());
  pick.setAlignmentX(CENTER_ALIGNMENT);
  pick.addActionListener(a);
  listPanel.add(pick);
  listPanel.setBackground(custom);
  add(listPanel);

  displayPanel.setLayout(new BoxLayout(displayPanel,BoxLayout.Y_AXIS));
  displayPanel.setBackground(custom);

  displayPanel.add(Box.createRigidArea(new Dimension(1,50)));
  JPanel emailPanel = new JPanel();
  emailPanel.setLayout(new BoxLayout(emailPanel,BoxLayout.X_AXIS));
  emailPanel.setBackground(custom);
  changeEmail.setMaximumSize(changeEmail.getPreferredSize());
  changeEmail.setAlignmentY(CENTER_ALIGNMENT);
  emailPanel.add(changeEmail);
  emailPanel.add(Box.createRigidArea(new Dimension(20,1)));
  changeEmailText.setMaximumSize(changeEmailText.getPreferredSize());
  changeEmailText.addActionListener(a);
  emailPanel.add(changeEmailText);
  displayPanel.add(emailPanel);

  displayPanel.add(Box.createRigidArea(new Dimension(1,20)));

  JPanel rolesPanel = new JPanel();
  rolesPanel.setLayout(new BoxLayout(rolesPanel,BoxLayout.X_AXIS));
  rolesPanel.setBackground(custom);
  player.setBackground(custom); coach.setBackground(custom);
  parent.setBackground(custom);
  player.addActionListener(a); coach.addActionListener(a);
  parent.addActionListener(a);
  changeRoles.setAlignmentY(CENTER_ALIGNMENT);
  rolesPanel.add(changeRoles);
  rolesPanel.add(player); rolesPanel.add(coach);
  rolesPanel.add(parent);
  displayPanel.add(rolesPanel);

  updateMember.setMaximumSize(updateMember.getPreferredSize());
  updateMember.setAlignmentX(CENTER_ALIGNMENT);
  updateMember.addActionListener(a);
  displayPanel.add(updateMember);
  add(displayPanel);




  

}
public void setList(DefaultListModel<String> listModel){
  list.setModel(listModel);
}
public void setRoles(ArrayList<Integer> array){
  for(int i=0; i<array.size(); i++){
    if(array.get(i)==0){
      player.setSelected(true);
    }
    if(array.get(i)==1){
      coach.setSelected(true);
    }
    if(array.get(i)==2){
      parent.setSelected(true);
    }
  }
}
public void updatedRoles(){
    
    
      if(player.isSelected()){
         model.updateRole(0,model.getId());        
      }
      else{
         model.deleteRole(0,model.getId());
      }
      if(coach.isSelected()){
        model.updateRole(1,model.getId());
      }
      else{
        model.deleteRole(1,model.getId());
      }
      if(parent.isSelected()){
        model.updateRole(2,model.getId());
      }
      else{
        model.deleteRole(2,model.getId());
      }
        
}
public void clearSelection(){
 player.setSelected(false);
 coach.setSelected(false);
 parent.setSelected(false);
 changeEmailText.setText("");
 searchText.setText("");
 model.setId("");
}

ActionListener a = new ActionListener(){
  public void actionPerformed(ActionEvent e){
      if(e.getSource()==searchButton){
        String id = searchText.getText();
        model.setId(id); // sets the models ID to be used in further methods
        setList(model.initList("SELECT * FROM medlem WHERE id="+model.getId()));
        
        changeEmailText.setText(model.getEmail(model.getId()));
        setRoles(model.getRoles(model.getId()));


      }
      if(e.getSource()==updateMember){
        model.updateEmail(model.getId(),changeEmailText.getText());
        updatedRoles();
        clearSelection();

      }
  }
};



}
