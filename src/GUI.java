import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.util.*;


public class GUI extends JFrame{ // Main window
    JPanel addMember = new AddMemberView();
    ListModel listModel = new ListModel();
    JPanel list = new ListView(listModel); 
    JPanel updateMember = new UpdateView(); 
	
	public GUI(){
		
		JTabbedPane tab = new JTabbedPane();
		JScrollPane listHandler = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
      JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tab.addTab("Listhandler",listHandler);
		tab.addTab("Add member",addMember);
		tab.addTab("Update member",updateMember);

		add(tab);

		setVisible(true);
		setSize(new Dimension(1080,860));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		
		
		
	}
	
	
}
