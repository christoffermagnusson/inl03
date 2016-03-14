import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;

public class AddMemberView extends JPanel{

    AddMemberModel model = new AddMemberModel();
    String tmpGender = "";

    Color background = new Color(182,200,222);
    
    JLabel givenNameLabel = new JLabel(String.format("%20s","Given name")); // Given name field
    JTextField givenName = new JTextField(15);
    
    JLabel familyNameLabel = new JLabel(String.format("%19s","Family name")); // Family name field
    JTextField familyName = new JTextField(15);
    
    JLabel emailLabel = new JLabel(String.format("%26s","E-mail")); // E-mail field
    JTextField email = new JTextField(15);
    
    JLabel birthLabel = new JLabel(String.format("%14s","Birthdate")); // use format to adjust distance and align label components
    JComboBox<String> day = new JComboBox<>();
    JComboBox<String> month = new JComboBox<>();
    JComboBox<String> year = new JComboBox<>();
    JLabel memberSince = new JLabel(); // sätt in dateformat med dagens datum // redundant?
    
    JLabel genderLabel = new JLabel("Gender");
    ButtonGroup gender = new ButtonGroup(); // lägg till underliggande knappar
    JRadioButton male = new JRadioButton("Male");
    JRadioButton female = new JRadioButton("Female");

    JLabel roleLabel = new JLabel("Role");
    JCheckBox player = new JCheckBox("Player");
    JCheckBox coach = new JCheckBox("Coach");
    JCheckBox parent = new JCheckBox("Parent");

    JLabel teamLabel = new JLabel(String.format("%20s","Team"));
    JComboBox<String> teams = new JComboBox<>(); // input available teams from db

    JButton addButton = new JButton("Add new member");
    JButton clear = new JButton("Clear fields");


	
	public AddMemberView(){
        
        setLayout(new GridLayout(1,3));

        JPanel leftPanel = new JPanel(); // setting left and right panels as empty non-workspace
        leftPanel.setBackground(background);
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(background);
        
        JPanel middlePanel = new JPanel(); // setting middlepanel as default workspace  
        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
        middlePanel.setBackground(background);
        add(leftPanel); add(middlePanel); add(rightPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,50)));


        JPanel gnPanel = new JPanel(); //Panel holding givenname label and textfield
        gnPanel.setLayout(new BoxLayout(gnPanel,BoxLayout.X_AXIS));
        gnPanel.setBackground(background);
        gnPanel.add(givenNameLabel); gnPanel.add(Box.createRigidArea(new Dimension(20,1)));
        givenName.setMaximumSize(givenName.getPreferredSize());
        givenName.addActionListener(a);
        gnPanel.add(givenName);
        middlePanel.add(gnPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));

        JPanel fnPanel = new JPanel(); // panel holding family name label and textfield
        fnPanel.setLayout(new BoxLayout(fnPanel,BoxLayout.X_AXIS));
        fnPanel.setBackground(background);
        fnPanel.add(familyNameLabel); fnPanel.add(Box.createRigidArea(new Dimension(20,1)));
        familyName.setMaximumSize(familyName.getPreferredSize());
        familyName.addActionListener(a);
        fnPanel.add(familyName);
        middlePanel.add(fnPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));

        JPanel mailPanel = new JPanel();
        mailPanel.setLayout(new BoxLayout(mailPanel,BoxLayout.X_AXIS));
        mailPanel.setBackground(background);
        mailPanel.add(emailLabel); mailPanel.add(Box.createRigidArea(new Dimension(20,1)));
        email.setMaximumSize(email.getPreferredSize());
        email.addActionListener(a);
        mailPanel.add(email);
        middlePanel.add(mailPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));
        
        
        JPanel birth = new JPanel(); // Panel containing labels and comboboxes to be used to specify birthdate
        setupBirth(day,1,31);
        setupBirth(month,1,12);
        setupBirth(year,1900,2016);
        birth.setLayout(new BoxLayout(birth,BoxLayout.X_AXIS));
        birth.setBackground(background);
        birth.setAlignmentX(CENTER_ALIGNMENT);
        birthLabel.setMaximumSize(birthLabel.getPreferredSize());
        birthLabel.setAlignmentX(LEFT_ALIGNMENT);
        birth.add(birthLabel);
        birth.add(Box.createRigidArea(new Dimension(20,1)));

        JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new BoxLayout(dayPanel,BoxLayout.Y_AXIS));
        dayPanel.setBackground(background);
        JPanel monthPanel = new JPanel();
        monthPanel.setLayout(new BoxLayout(monthPanel,BoxLayout.Y_AXIS));
        monthPanel.setBackground(background);
        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new BoxLayout(yearPanel,BoxLayout.Y_AXIS));
        yearPanel.setBackground(background);
        
        JLabel l1 = new JLabel("Day"); JLabel l2 = new JLabel("Month");
        JLabel l3 = new JLabel("Year");
        l1.setAlignmentX(CENTER_ALIGNMENT);
        l2.setAlignmentX(CENTER_ALIGNMENT);
        l3.setAlignmentX(CENTER_ALIGNMENT);
        day.setMaximumSize(day.getPreferredSize());
        month.setMaximumSize(month.getPreferredSize());
        year.setMaximumSize(year.getPreferredSize());
        day.addActionListener(a);
        month.addActionListener(a);
        year.addActionListener(a);
        dayPanel.add(l1); dayPanel.add(day);
        monthPanel.add(l2); monthPanel.add(month);
        yearPanel.add(l3); yearPanel.add(year);

        birth.add(dayPanel); birth.add(monthPanel); birth.add(yearPanel);
        
        middlePanel.add(birth); // end birth-Panel
        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));


        JPanel genderPanel = new JPanel(); // Panel holding option for choosing gender
        genderPanel.setLayout(new BoxLayout(genderPanel,BoxLayout.X_AXIS));
        genderPanel.setBackground(background);
        gender.add(male); gender.add(female);
        male.setBackground(background); female.setBackground(background);
        genderPanel.add(genderLabel);
        genderPanel.add(Box.createRigidArea(new Dimension(15,1)));
        male.addActionListener(a); female.addActionListener(a);
        genderPanel.add(male); genderPanel.add(female);
        middlePanel.add(genderPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));


        JPanel rolePanel = new JPanel(); // Panel holding options regarding roles
        rolePanel.setLayout(new BoxLayout(rolePanel,BoxLayout.X_AXIS));
        rolePanel.setBackground(background);
        rolePanel.setAlignmentX(CENTER_ALIGNMENT);
        player.setBackground(background); coach.setBackground(background); parent.setBackground(background);
        rolePanel.add(Box.createRigidArea(new Dimension(75,1)));
        rolePanel.add(roleLabel);
        rolePanel.add(Box.createRigidArea(new Dimension(15,1)));
        player.addActionListener(a); coach.addActionListener(a); parent.addActionListener(a);
        rolePanel.add(player); rolePanel.add(coach); rolePanel.add(parent);
        middlePanel.add(rolePanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));


        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel,BoxLayout.X_AXIS));
        teamPanel.setBackground(background);
        teamPanel.add(teamLabel); teamPanel.add(Box.createRigidArea(new Dimension(20,1)));
        teamPanel.add(teams);
        setupTeams(teams,model.initTeams()); 
        teams.setMaximumSize(teams.getPreferredSize());
        teamPanel.setAlignmentX(RIGHT_ALIGNMENT);
        middlePanel.add(teamPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.setBackground(background);
        buttonPanel.add(Box.createRigidArea(new Dimension(65,1)));
        addButton.addActionListener(a);
        buttonPanel.add(addButton); 
        buttonPanel.add(Box.createRigidArea(new Dimension(10,1)));
        buttonPanel.add(clear);
        middlePanel.add(buttonPanel);


        



		setVisible(true);
		setBackground(background);
	}

	/**
	*Method for setting up birthday comboboxes
	*@param comp ComboBox to be filled with numbers
	*@param start Specified start of number range
	*@param end Specified end of number range
	*/
	private void setupBirth(JComboBox<String> comp, int start, int end){
           String tmp = "";
           for(int i=start; i<=end; i++){
            if(i<10){
            tmp = 0 +""+i;
            comp.addItem(tmp);
            }
            else{
                tmp=""+i;
                comp.addItem(tmp);
            }

        	
        }
	}
    private void setupTeams(JComboBox<String> comp,ArrayList<String> array){
           for(int i=0; i<array.size(); i++){
            comp.addItem(array.get(i));
           }
    }
    private void setupRoles(int id){
                  for(int i=0; i<3; i++){
                if(player.isSelected() && i==0){
                    model.setRoles(id,0,teams.getSelectedItem().toString());
                }
                if(coach.isSelected() && i==1){
                    model.setRoles(id,1,teams.getSelectedItem().toString());
                }
                if(parent.isSelected() && i==2){
                    model.setRoles(id,2,teams.getSelectedItem().toString());
                }
            }
    }


    ActionListener a = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==male){
                tmpGender="Man";
            }
            else if(e.getSource()==female){
                tmpGender="Kvinna";
            }
            else if(e.getSource()==addButton){
                int id = model.setId();

                model.insertNewMember(id,givenName.getText(),familyName.getText(),
                    email.getText(),tmpGender,
                    model.convertBirthday(day,month,year),model.getDates(),1
                    );

                    setupRoles(id);

            }

 
        }
    };



}

