import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

public class GUI extends JFrame implements ActionListener{
    
    
    GridBagConstraints con;
    String [] colors = {"Blå","Röd","Gul","Green"};
    

    JList<String> list = new JList<>(colors);
    JLabel menuLabel = new JLabel("Menu");
    JLabel listLabel = new JLabel("Sortera");
    JComboBox<String> combox = new JComboBox<>();
	JPanel tools = new JPanel();
	
	JTextField searchText = new JTextField(20);
	JButton search = new JButton("Search");
	

    
	public GUI(){
		GridBagLayout gbl = new GridBagLayout();
		setLayout(new GridLayout(1,1));
		JPanel main = new JPanel(gbl);
		GridBagConstraints con;

		JTabbedPane tab = new JTabbedPane();
		JPanel addMember = new JPanel();
		JScrollPane listHandler = new JScrollPane(main);
		tab.addTab("List Handler",listHandler);
		tab.addTab("Add member",addMember);

		tools.setLayout(new BoxLayout(tools,BoxLayout.Y_AXIS));

		setVisible(true);
		setSize(new Dimension(800,600));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		
        con = new GridBagConstraints(); // menulabel
        con.ipadx = 50;
        con.weighty = 0; con.weightx = 0;
        con.gridy = 0; con.gridx = 0;
        con.gridheight = 1; con.gridwidth = 1;
        gbl.setConstraints(menuLabel,con);
        

        con = new GridBagConstraints(); // toolbar
        con.ipadx = 50;
        con.gridy = 1; con.gridx = 0;
        con.weighty = 1; con.weightx = 0;
        con.fill = GridBagConstraints.VERTICAL;
        con.gridheight = 10; con.gridwidth = 2;
        gbl.setConstraints(tools,con);

        tools.add(Box.createRigidArea(new Dimension(1,10)));
        tools.add(searchText);
        tools.add(Box.createRigidArea(new Dimension(1,10)));
        tools.add(search);


        con = new GridBagConstraints(); // sorteringslabel
        con.gridy = 0; con.gridx = 2; 
        con.gridheight = 1; con.gridwidth = 1;
        gbl.setConstraints(listLabel, con);
        

        con = new GridBagConstraints();
        con.gridy = 0; con.gridx = 3;
        con.gridheight = 1; con.gridwidth = 1;
        combox.addItem("Efter ID");
        combox.addItem("Efter Namn");
        gbl.setConstraints(combox,con);
        

        con = new GridBagConstraints(); // listan
        con.anchor = GridBagConstraints.WEST;
        con.fill = GridBagConstraints.BOTH;
        con.gridy = 1; con.gridx = 2;
        con.weighty = 1; con.weightx = 1;
        con.gridheight = 10; con.gridwidth = 6;
        gbl.setConstraints(list,con);

        list.setVisibleRowCount(4);
        

        main.add(menuLabel);
        main.add(listLabel);
        main.add(combox);		
		main.add(tools);
		main.add(list);

		add(tab);

		pack();
		

		

	}

	public void actionPerformed(ActionEvent e){

	}
}
