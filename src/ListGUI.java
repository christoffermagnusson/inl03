import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;

public class ListGUI extends JPanel{

    FormatList fl = new FormatList();
	  GridBagConstraints con;
    String [] col = {"Id","Given name","Family name","Email","Gender","Birthday","Member since"
    ,"Active"};
    JTable memberTable = new JTable(fl.getData(fl.initList(fl.getStatement())),col);
    
    JScrollPane listPane = new JScrollPane(memberTable,
      JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
    JLabel menuLabel = new JLabel("Menu");
    JPanel mlPanel = new JPanel(new GridLayout(1,1));

    JPanel listPanel = new JPanel();
    JLabel listLabel = new JLabel("Sort by");
    JComboBox<String> combox = new JComboBox<>();
    JButton sortButton = new JButton("Sort");

	
	JPanel tools = new JPanel();
	JTextField searchText = new JTextField(15);
	JButton search = new JButton("Search");

	Color backgrounds = new Color(182,200,222);

	public ListGUI(){
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
        tools.add(search);

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
        combox.setMaximumSize(new Dimension(200,200));
        listPanel.add(combox);
        //listPanel.add(Box.createRigidArea(new Dimension(20,1)));
        listPanel.add(sortButton);
        listPanel.setBorder(new LineBorder(Color.gray,1));
        listPanel.setBackground(backgrounds);

        
        
        
        

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

	
}
