import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.util.*;
import java.beans.*;

public class ListView extends View{

    ListModel model;
    
    ArrayList<Component> compList = new ArrayList<Component>();

    

	  GridBagConstraints con;
    String [] memberColumns = {"Id","Given name","Family name","Email","Gender","Birthday","Member since"
    ,"Status"};
    String [] teamColumns = {"Id","Given name","Family name","Role","Team"};
    DefaultTableModel tableModel;
    JTable memberTable;
    JScrollPane listPane;
    
    
    
    JLabel menuLabel = new JLabel("Menu");
    JPanel mlPanel = new JPanel(new GridLayout(1,1));

    JPanel listPanel = new JPanel();
    JLabel listLabel = new JLabel("Sort by");
    JComboBox<String> combox = new JComboBox<>();
    JButton sortButton = new JButton("Sort");
    JLabel switchLabel = new JLabel("Change view");
    JComboBox<String> switchTable = new JComboBox<>();
    JButton switchButton = new JButton("Change");
    JLabel filterLabel = new JLabel("Filter by teams");
    JComboBox<String> filterTeams = new JComboBox<>();
    JButton filterButton = new JButton("Filter");

	
	JPanel tools = new JPanel();
	JTextField searchText = new JTextField(15);
	JButton search = new JButton("Search");
    
    JPanel teamPanel = new JPanel();
    JLabel teamLabel1 = new JLabel();
    JLabel teamLabel2 = new JLabel();
    JButton moreInfoButton = new JButton("More info");
    
    JFrame moreInfoFrame = new JFrame();
    
    JPanel infoPanelLeft = new JPanel();
    JLabel infoLabel1 = new JLabel();
    JLabel infoLabel2 = new JLabel();
    JLabel infoLabel3 = new JLabel();
    
    JPanel infoPanelRight = new JPanel();
    JLabel infoLabel4 = new JLabel();
    ArrayList<String> children = new ArrayList<String>();
    DefaultListModel<String> infoListModel = new DefaultListModel<>();

    JList<String> childrenList = new JList<String>();

     

	Color backgrounds = new Color(182,200,222);

	public ListView(ListModel m){
        model = m;
        setModel(m);

		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		GridBagConstraints con;
        

        con = new GridBagConstraints(); // toolbar
        con.ipadx = 50;
        con.gridy = 0; con.gridx = 0;
        con.weighty = 1; con.weightx = 0;
        con.fill = GridBagConstraints.BOTH;
        con.gridheight = 10; con.gridwidth = 2;
        gbl.setConstraints(tools,con);

        tools.setLayout(new BoxLayout(tools,BoxLayout.Y_AXIS)); 
        
        menuLabel.setAlignmentX(CENTER_ALIGNMENT);
        tools.add(menuLabel);
        tools.add(Box.createRigidArea(new Dimension(1,20)));
        searchText.setMinimumSize(searchText.getPreferredSize());
        searchText.setMaximumSize(searchText.getPreferredSize());
        tools.add(searchText);
        tools.add(Box.createRigidArea(new Dimension(1,10)));
        search.setAlignmentX(CENTER_ALIGNMENT);
        search.addActionListener(searchListener);
        searchText.addActionListener(searchListener);
        
        tools.add(search);
        tools.add(Box.createRigidArea(new Dimension(1,30)));

        

        teamPanel.setLayout(new BoxLayout(teamPanel,BoxLayout.Y_AXIS));
        teamPanel.setBackground(backgrounds);
        teamPanel.add(teamLabel1);
        teamLabel1.setAlignmentX(CENTER_ALIGNMENT);
        teamPanel.add(Box.createRigidArea(new Dimension(1,10)));
        teamPanel.add(teamLabel2);
        teamLabel2.setAlignmentX(CENTER_ALIGNMENT);
        teamPanel.setVisible(true);
        
        tools.add(teamPanel);
        tools.add(Box.createRigidArea(new Dimension(1,20)));
        moreInfoButton.addActionListener(moreInfoListener);
        moreInfoButton.setAlignmentX(CENTER_ALIGNMENT);
        moreInfoButton.setEnabled(true);
        tools.add(moreInfoButton);
        
        moreInfoFrame.setLayout(new GridLayout(1,2));
        moreInfoFrame.setVisible(false);
        moreInfoFrame.setSize(new Dimension(800,600));
        moreInfoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        infoPanelLeft.setBackground(backgrounds);
        infoPanelLeft.setLayout(new BoxLayout(infoPanelLeft,BoxLayout.Y_AXIS));
        infoPanelLeft.add(infoLabel1);
        infoPanelLeft.add(Box.createRigidArea(new Dimension(1,10)));
        infoPanelLeft.add(infoLabel2);
        infoPanelLeft.add(Box.createRigidArea(new Dimension(1,10)));
        infoPanelLeft.add(infoLabel3);

        infoPanelRight.setBackground(backgrounds);
        infoPanelRight.setLayout(new BoxLayout(infoPanelRight,BoxLayout.Y_AXIS));
        infoPanelRight.add(infoLabel4);
        infoPanelRight.add(Box.createRigidArea(new Dimension(1,10)));
        childrenList.setModel(infoListModel);
        JScrollPane childrenListPane = new JScrollPane(childrenList,
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        infoPanelRight.add(childrenListPane);

        moreInfoFrame.add(infoPanelLeft);
        moreInfoFrame.add(infoPanelRight);

        tools.setBorder(new LineBorder(Color.gray,1));
        tools.setBackground(backgrounds);
        



        con = new GridBagConstraints(); // sorteringspanel som sköter sorteringen av listan, alltså sortera efter : id eller namn
        con.gridy = 0; con.gridx = 2; 
        con.gridheight = 1; con.gridwidth = 8;
        con.fill = GridBagConstraints.BOTH;
        gbl.setConstraints(listPanel, con);

        listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.X_AXIS));
        listPanel.add(listLabel);
        listLabel.setAlignmentX(LEFT_ALIGNMENT);
        listPanel.add(Box.createRigidArea(new Dimension(20,1)));
        combox.addItem("ID");
        combox.addItem("Family name");
        combox.setAlignmentX(LEFT_ALIGNMENT);
        combox.setMinimumSize(combox.getPreferredSize());
        combox.setMaximumSize(combox.getPreferredSize());
        listPanel.add(combox);
        sortButton.addActionListener(sortListener);
        listPanel.add(sortButton);
        listPanel.add(Box.createRigidArea(new Dimension(20,1)));
        listPanel.add(switchLabel);
        listPanel.add(Box.createRigidArea(new Dimension(20,1)));
        switchTable.addItem("View members");
        switchTable.addItem("View teams");
        switchTable.setMaximumSize(switchTable.getPreferredSize());
        listPanel.add(switchTable);
        switchButton.addActionListener(switchListener);
        listPanel.add(switchButton);
        listPanel.add(Box.createRigidArea(new Dimension(20,1)));
        listPanel.add(filterLabel);
        listPanel.add(Box.createRigidArea(new Dimension(20,1)));
        filterTeams = model.initTeams();
        filterTeams.setMaximumSize(filterTeams.getPreferredSize());
        filterTeams.setEnabled(false);
        filterButton.setEnabled(false);
        listPanel.add(filterTeams);
        filterButton.addActionListener(filterListener);
        listPanel.add(filterButton);


        listPanel.setBorder(new LineBorder(Color.gray,1));
        listPanel.setBackground(backgrounds);

        
        
        tableModel = new DefaultTableModel(model.getMemberData(model.initList(
        "SELECT * FROM medlem")),memberColumns);
        memberTable = new JTable(tableModel);
        listPane = new JScrollPane(memberTable,
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        con = new GridBagConstraints(); // listan
        con.anchor = GridBagConstraints.WEST;
        con.fill = GridBagConstraints.BOTH;
        con.gridy = 1; con.gridx = 2;
        con.weighty = 1; con.weightx = 1;
        con.gridheight = 10; con.gridwidth = 6;
        gbl.setConstraints(listPane,con);

        
        

        
        add(listPanel);		
		add(tools);

        
		add(listPane);
	}

 
  public void updateTable(String query,String [] col){
    if(col==memberColumns){
    memberTable.setModel(new DefaultTableModel(model.getMemberData(model.initList
        (query)),col));
    filterTeams.setEnabled(false);
    filterButton.setEnabled(false);
}
else if(col==teamColumns){
    memberTable.setModel(new DefaultTableModel(model.getTeamData(model.initList(query)),col));
    filterTeams.setEnabled(true);
    filterButton.setEnabled(true);
}
  }
  public void displayInfo(String id){
    moreInfoFrame.setVisible(true);
    String givenName = model.getElement("SELECT givenName FROM medlem WHERE id="+id);
    String familyName = model.getElement("SELECT familyName FROM medlem WHERE id="+id);
    String team = model.getElement("SELECT team FROM funktion WHERE id="+id);
    infoLabel1.setText("First name : " + givenName);
    infoLabel2.setText("Last name : " + familyName);
    infoLabel3.setText("Team : " + team);
    infoLabel4.setText("Parent to");
    children = model.getChildren("SELECT cid FROM medlem,children WHERE id=pid AND id="+id);
    for(String child : children){
        infoListModel.addElement(child);
        childrenList.setModel(infoListModel);
        childrenList.setVisible(true);
    }
  }
 



  ActionListener searchListener = new ActionListener(){
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==search){
            
            if(model.isNumeric(searchText.getText())==false){
            updateTable("SELECT * FROM medlem WHERE givenName = '"+searchText.getText()+"'",memberColumns);

        }
        else{
            updateTable("SELECT * FROM medlem WHERE id = "+searchText.getText(),memberColumns);
            children.clear();
            infoListModel.removeAllElements();
            
        }
    }
}
};

ActionListener sortListener = new ActionListener(){
    public void actionPerformed(ActionEvent e){
    if(e.getSource()==sortButton && switchTable.getSelectedItem().toString()=="View members"){
        
        filterTeams.setEnabled(false);
        if(combox.getSelectedItem().toString()=="ID"){
            updateTable("SELECT * FROM medlem ORDER BY id",memberColumns);
        }
        else if(combox.getSelectedItem().toString()=="Family name"){
            updateTable("SELECT * FROM medlem ORDER BY familyName",memberColumns);
        }
    }

 if(e.getSource()==sortButton && switchTable.getSelectedItem().toString()=="View teams"){
    
    if(combox.getSelectedItem().toString()=="ID"){
        updateTable("SELECT DISTINCT medlem.id,givenName,familyName,role,team FROM medlem,funktion ON medlem.id=funktion.id WHERE team IS NOT NULL ORDER BY medlem.id"
            ,teamColumns);
    }
    else if(combox.getSelectedItem().toString()=="Family name"){
        updateTable("SELECT DISTINCT medlem.id,givenName,familyName,role,team FROM medlem,funktion ON medlem.id=funktion.id WHERE team IS NOT NULL ORDER BY familyName"
            ,teamColumns);   
    }
}
}
};
    ActionListener switchListener = new ActionListener(){
           public void actionPerformed(ActionEvent e){   
    if(e.getSource()==switchButton){
        if(switchTable.getSelectedItem().toString()=="View teams"){
    updateTable("SELECT DISTINCT medlem.id,givenName,familyName,role,team FROM medlem,funktion ON medlem.id=funktion.id WHERE team IS NOT NULL"
        ,teamColumns);
        }
        else if(switchTable.getSelectedItem().toString()=="View members"){
            updateTable("SELECT * FROM medlem",memberColumns);
            filterTeams.setEnabled(false);
            teamLabel1.setText("");
            teamLabel2.setText("");
        }
    }
}
    };

    ActionListener filterListener = new ActionListener(){
        public void actionPerformed(ActionEvent e){
           if(e.getSource()==filterButton){
            updateTable("SELECT DISTINCT medlem.id,givenName,familyName,role,team FROM medlem,funktion ON medlem.id=funktion.id WHERE team='"
                +filterTeams.getSelectedItem()+"' ORDER BY role DESC",teamColumns);
            String teamName = filterTeams.getSelectedItem().toString();
            
            teamLabel1.setText("Team: "+teamName);
            int memberCount = model.getTeamMemberCount(teamName);
            teamLabel2.setText("Nr of members: "+memberCount);
           }
        }
    };

    ActionListener moreInfoListener = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==moreInfoButton){
                children.clear();
            infoListModel.removeAllElements();    
                if(model.isNumeric(searchText.getText())==true){

                displayInfo(searchText.getText());
                }
                else{
                    int row = memberTable.getSelectedRow();
                    String id = memberTable.getValueAt(row,0).toString();
                    displayInfo(id);
                }
            }
        }
    };
  



	
}
