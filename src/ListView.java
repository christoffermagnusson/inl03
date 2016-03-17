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
    
    JPanel teamInfo = new JPanel();
    JLabel teamLabel = new JLabel();
    JLabel teamMemberCount = new JLabel();
    

     

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
        con.fill = GridBagConstraints.VERTICAL;
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

        teamInfo.setLayout(new BoxLayout(teamInfo,BoxLayout.Y_AXIS));
        teamInfo.setBackground(backgrounds);
        teamLabel.setAlignmentX(CENTER_ALIGNMENT);
        teamInfo.add(teamLabel);
        teamInfo.add(Box.createRigidArea(new Dimension(1,10)));
        teamMemberCount.setAlignmentX(CENTER_ALIGNMENT);
        teamInfo.add(teamMemberCount);
        teamInfo.setVisible(false);
        tools.add(teamInfo);


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
 



  ActionListener searchListener = new ActionListener(){
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==search){
            teamInfo.setVisible(false);
            if(model.isNumeric(searchText.getText())==false){
            updateTable("SELECT * FROM medlem WHERE givenName = '"+searchText.getText()+"'",memberColumns);
        }
        else{
            updateTable("SELECT * FROM medlem WHERE id = "+searchText.getText(),memberColumns);
        }
    }
}
};

ActionListener sortListener = new ActionListener(){
    public void actionPerformed(ActionEvent e){
    if(e.getSource()==sortButton && switchTable.getSelectedItem().toString()=="View members"){
        teamInfo.setVisible(false);
        filterTeams.setEnabled(false);
        if(combox.getSelectedItem().toString()=="ID"){
            updateTable("SELECT * FROM medlem ORDER BY id",memberColumns);
        }
        else if(combox.getSelectedItem().toString()=="Family name"){
            updateTable("SELECT * FROM medlem ORDER BY familyName",memberColumns);
        }
    }

 if(e.getSource()==sortButton && switchTable.getSelectedItem().toString()=="View teams"){
    teamInfo.setVisible(false);
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
            teamInfo.setVisible(true);
            teamLabel.setText("Team: "+teamName);
            int memberCount = model.getTeamMemberCount(teamName);
            teamMemberCount.setText("Nr of members: "+memberCount);
           }
        }
    };
  



	
}
