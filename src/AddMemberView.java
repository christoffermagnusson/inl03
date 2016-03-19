import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;

public class AddMemberView extends JPanel{

    AddMemberModel model = new AddMemberModel();
    Verifier inputCheck = new Verifier();
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
    JLabel errorLabel = new JLabel("All fields must be filled in");

    JList<String> childrenList;
    JScrollPane listPane;
    

    JPanel middleLeft = new JPanel();  
    ArrayList<JLabel> labelList = new ArrayList<JLabel>();
    JLabel childrenLabel = new JLabel("Select your child");
    JButton childrenButton = new JButton("Select");
    JComboBox<Integer> amountOfChildren = new JComboBox<Integer>();
    private int childrenIndex = 0;

    ArrayList<Integer> selectedChildrens = new ArrayList<Integer>();
    ArrayList<String> selectedChildrenId = new ArrayList<String>();

    JPanel bottomLeft = new JPanel();


    


	
	public AddMemberView(){
        
        setLayout(new GridLayout(1,3));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3,1));
        leftPanel.setBackground(background);
        
        childrenList = new JList<String>();
        setList(model.initList());
        childrenList.setEnabled(false);
        childrenList.setMaximumSize(childrenList.getPreferredSize());
        listPane = new JScrollPane(childrenList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        listPane.setVisible(true);
        leftPanel.add(listPane);
       
        middleLeft.setBackground(background);
        middleLeft.setLayout(new BoxLayout(middleLeft,BoxLayout.Y_AXIS));
        middleLeft.add(Box.createRigidArea(new Dimension(1,20)));
        childrenLabel.setAlignmentX(CENTER_ALIGNMENT);
        middleLeft.add(childrenLabel);
        middleLeft.add(Box.createRigidArea(new Dimension(1,10)));
        childrenButton.addActionListener(addChildrenListener);
        childrenButton.setAlignmentX(CENTER_ALIGNMENT);
        childrenButton.setEnabled(false);
        middleLeft.add(childrenButton);
        middleLeft.add(Box.createRigidArea(new Dimension(1,10)));
        
        JPanel amountOfChildrenPanel = new JPanel();
        amountOfChildrenPanel.setBackground(background);
        amountOfChildrenPanel.setLayout(new BoxLayout(amountOfChildrenPanel,BoxLayout.X_AXIS));
        JLabel amountOfChildrenLabel = new JLabel("Nr of children");
        amountOfChildrenLabel.setAlignmentY(CENTER_ALIGNMENT);
        amountOfChildren.setMaximumSize(new Dimension(100,25));
        amountOfChildrenPanel.add(amountOfChildrenLabel);
        amountOfChildrenPanel.add(Box.createRigidArea(new Dimension(10,1)));
        setupAmountOfChildren(amountOfChildren);
        amountOfChildren.setEnabled(false);
        amountOfChildrenPanel.add(amountOfChildren);

        middleLeft.add(amountOfChildrenPanel);

        
        middleLeft.setVisible(true);

        
        


        leftPanel.add(middleLeft);
        
        
        bottomLeft.setLayout(new BoxLayout(bottomLeft,BoxLayout.Y_AXIS));
        bottomLeft.setBackground(background);
        placeLabels(setupLabels(10));

        leftPanel.add(bottomLeft);
        
        
        JPanel middlePanel = new JPanel(); // setting middlepanel as default workspace  
        middlePanel.setLayout(new BoxLayout(middlePanel,BoxLayout.Y_AXIS));
        middlePanel.setBackground(background);
        add(leftPanel); add(middlePanel); 

        middlePanel.add(Box.createRigidArea(new Dimension(1,50)));


        JPanel gnPanel = new JPanel(); //Panel holding givenname label and textfield
        gnPanel.setLayout(new BoxLayout(gnPanel,BoxLayout.X_AXIS));
        gnPanel.setBackground(background);
        gnPanel.add(givenNameLabel); gnPanel.add(Box.createRigidArea(new Dimension(20,1)));
        givenName.setMaximumSize(givenName.getPreferredSize());
        givenName.setInputVerifier(inputCheck);
        givenName.addActionListener(textFieldListener);
        givenName.setName("givenName");
        gnPanel.add(givenName);
        middlePanel.add(gnPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));

        JPanel fnPanel = new JPanel(); // panel holding family name label and textfield
        fnPanel.setLayout(new BoxLayout(fnPanel,BoxLayout.X_AXIS));
        fnPanel.setBackground(background);
        fnPanel.add(familyNameLabel); fnPanel.add(Box.createRigidArea(new Dimension(20,1)));
        familyName.setMaximumSize(familyName.getPreferredSize());
        familyName.setInputVerifier(inputCheck);
        familyName.setName("familyName");
        fnPanel.add(familyName);
        middlePanel.add(fnPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));

        JPanel mailPanel = new JPanel();
        mailPanel.setLayout(new BoxLayout(mailPanel,BoxLayout.X_AXIS));
        mailPanel.setBackground(background);
        mailPanel.add(emailLabel); mailPanel.add(Box.createRigidArea(new Dimension(20,1)));
        email.setMaximumSize(email.getPreferredSize());
        email.setInputVerifier(inputCheck);
        email.addActionListener(textFieldListener);
        email.setName("email");
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
        male.addActionListener(addMemberListener); female.addActionListener(addMemberListener);
        genderPanel.add(male); genderPanel.add(female);
        middlePanel.add(genderPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));


        JPanel rolePanel = new JPanel(); // Panel holding options regarding roles
        rolePanel.setLayout(new BoxLayout(rolePanel,BoxLayout.X_AXIS));
        rolePanel.setBackground(background);
        rolePanel.setAlignmentX(CENTER_ALIGNMENT);
        player.setBackground(background); coach.setBackground(background); parent.setBackground(background);
        player.addActionListener(addMemberListener); coach.addActionListener(addMemberListener); 
        parent.addItemListener(parentListener);
        rolePanel.add(Box.createRigidArea(new Dimension(75,1)));
        rolePanel.add(roleLabel);
        rolePanel.add(Box.createRigidArea(new Dimension(15,1)));
        
        rolePanel.add(player); rolePanel.add(coach); rolePanel.add(parent);
        middlePanel.add(rolePanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));


        JPanel teamPanel = new JPanel();
        teamPanel.setLayout(new BoxLayout(teamPanel,BoxLayout.X_AXIS));
        teamPanel.setBackground(background);
        teamPanel.add(teamLabel); teamPanel.add(Box.createRigidArea(new Dimension(10,1)));
        teamPanel.add(teams);
        setupTeams(teams,model.initTeams()); 
        teams.setMaximumSize(teams.getPreferredSize());
        teams.addActionListener(addMemberListener);
        teamPanel.setAlignmentX(RIGHT_ALIGNMENT);
        middlePanel.add(teamPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
        buttonPanel.setBackground(background);
        buttonPanel.add(Box.createRigidArea(new Dimension(65,1)));
        addButton.addActionListener(addMemberListener);
        addButton.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.add(Box.createRigidArea(new Dimension(20,1)));
        buttonPanel.add(addButton); 
        
        
        middlePanel.add(buttonPanel);

        middlePanel.add(Box.createRigidArea(new Dimension(1,20)));
        JPanel errorPanel = new JPanel();
        errorPanel.setLayout(new BoxLayout(errorPanel,BoxLayout.X_AXIS));
        errorPanel.setBackground(background);
        errorPanel.add(Box.createRigidArea(new Dimension(80,1)));
        errorLabel.setVisible(false);
        errorPanel.add(errorLabel);
        middlePanel.add(errorPanel);


        

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(background);

        add(rightPanel);

		setVisible(true);
		setBackground(background);
	}
    public void setList(DefaultListModel<String> model){
        childrenList.setModel(model);
    }
    public ArrayList<JLabel> setupLabels(int nrChildren){
       
        for(int i=0; i<nrChildren; i++){
            
            labelList.add(new JLabel());
            labelList.get(i).setName("child"+i);
        }
        return labelList;
    }

    public void placeLabels(ArrayList<JLabel> labels){
        for(JLabel l : labels){
            bottomLeft.add(Box.createRigidArea(new Dimension(1,10)));
            bottomLeft.add(l);
            bottomLeft.add(Box.createRigidArea(new Dimension(1,10)));
        }
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
    private void setupAmountOfChildren(JComboBox<Integer> children){
          for(int i=1; i<11; i++){
            children.addItem(i);
          }
    }
    private void setupRoles(int id){
                  String teamName = teams.getSelectedItem().toString();
                  
                  for(int i=0; i<3; i++){
                if(player.isSelected() && i==0){
                    model.setRoles(id,0,teamName);
                }
                if(coach.isSelected() && i==1){
                    model.setRoles(id,1,teamName);
                }
                if(parent.isSelected() && i==2){
                    model.setRoles(id,2,teamName);
                }
            }
    }
    private void setupChildren(ArrayList<String> childrenToBeSent,String parentId){
        for(int i=0; i<childrenToBeSent.size(); i++){
            model.updateChildren(childrenToBeSent.get(i),parentId);
        }
    }
    private boolean checkRoles(){
        if(player.isSelected()==false && coach.isSelected()==false && parent.isSelected()==false){
            return false;
        }
        return true;
    }
    private boolean checkGender(){
        if(male.isSelected()==false && female.isSelected()==false){
            return false;
        }
        return true;
    }
    private boolean checkTeam(){
        if(parent.isSelected()==false && coach.isSelected()==true || player.isSelected()==true){
            if(teams.getSelectedIndex()==0){
                return false;
            }
        }
        return true;
    }

    private void clearSelection(){
        givenName.setText("");
        familyName.setText("");
        email.setText("");
        gender.clearSelection();
        day.setSelectedIndex(0);
        month.setSelectedIndex(0);
        year.setSelectedIndex(0);
        player.setSelected(false);
        coach.setSelected(false);
        parent.setSelected(false);
        teams.setSelectedIndex(0);
        errorLabel.setVisible(false);
        childrenIndex=0;
        for(int i=0; i<amountOfChildren.getSelectedIndex()+1; i++){
            labelList.get(i).setText("");
        }
        amountOfChildren.setSelectedIndex(0);
        selectedChildrenId.clear();
        selectedChildrens.clear();
        
    }



    ActionListener addMemberListener = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            

            if(e.getSource()==male){
                tmpGender="Man";
            }
            else if(e.getSource()==female){
                tmpGender="Kvinna";
            }
            else if(e.getSource()==addButton){
                errorLabel.setVisible(false);
                if(checkRoles()==true && checkGender()==true && checkTeam()==true){
              
                int id = model.setId();
                String parentId = String.valueOf(id);

                model.insertNewMember(id,givenName.getText(),familyName.getText(),
                    email.getText(),tmpGender,
                    model.convertBirthday(day,month,year),model.setMemberSince(),1
                    );

                    setupRoles(id);
                    setupChildren(selectedChildrenId,parentId);
                    clearSelection();
                }
                else{
                    errorLabel.setVisible(true);
                }
            }

        
        }
    };

    ActionListener textFieldListener = new ActionListener(){
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==givenName || e.getSource()==familyName){
                if(inputCheck.verify(givenName)==false){
                   JOptionPane.showMessageDialog(null,"TEST"); // gör nånting annat, varna användaren
                }
                else if(inputCheck.verify(familyName)==false){
                    //do something
                }
            }
            else if(e.getSource()==email){
                if(inputCheck.verify(email)==false){
                    JOptionPane.showMessageDialog(null,"test");
                }
            }

        }
    };

ActionListener addChildrenListener = new ActionListener(){
    public void actionPerformed(ActionEvent e){
        
          
        
        if(e.getSource()==childrenButton){
            if(childrenIndex<(amountOfChildren.getSelectedIndex()+1)){
            labelList.get(childrenIndex).setText(childrenList.getSelectedValue());
            
            Scanner scanForId = new Scanner(childrenList.getSelectedValue()).useDelimiter(";");
            String childId = scanForId.next("[0-9]{1,4}");
            selectedChildrenId.add(childId);
            childrenIndex++;
        }
        }
    }
};
       ItemListener parentListener = new ItemListener(){
        public void itemStateChanged(ItemEvent e){
        if(e.getStateChange()==ItemEvent.SELECTED){
                
                    childrenList.setEnabled(true);
                    childrenButton.setEnabled(true);
                    amountOfChildren.setEnabled(true);
            }    
                else{
                    childrenList.setEnabled(false);
                    childrenButton.setEnabled(false);
                    amountOfChildren.setEnabled(false);
                }
            
        }
        };



}

