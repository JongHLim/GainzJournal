package my.gainzjournal;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import my.gainzjournal.datastructures.Exercise;
import my.gainzjournal.datastructures.Workout;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.GridBagLayout;


public class GainzJournalUserInterface extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GainzJournalBean bean = new GainzJournalBean();
	// fill the exercise TreeMap every time the app opens
	private TreeMap<Integer, LinkedHashMap<String, String>> exercisesTreeMap = bean.fillExerciseMap();
	
	private JPanel contentPane;
	private JTextField workoutDateField;
	private JTextField workoutTypeField;
	private JTextField workoutIdField;
	private JTextField exerciseField;
	private JTextField weightField;
	private JTextField setsField;
	private JTextField repsField;
	
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton firstButton;
    private javax.swing.JButton lastButton;
    private javax.swing.JButton newButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton previousButton;
    private javax.swing.JButton updateButton;
    
    private javax.swing.JButton addMoreSetsButton;
    private javax.swing.JButton addExerciseButton;
    
    private int workoutId;
    private int exerciseId;
    
    private Exercise currentExercise = new Exercise();
    private JPanel exerciseListPanel;
    private JScrollPane scroll;
	
    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
        	
        	workoutId = 1;
        	exerciseId = 1001;
        	
        	Workout w = getFieldData();
        	
        	currentExercise = exerciseGetFieldData(currentExercise);
        	
        	// for the case when the user clicks "New" then "First", "Previous",
        	// "Next", and "Last"
        	newButton.setText("New");
            switch (event.getActionCommand()) {
            	// user clicked "Save"
                case "Save":
                    // fields are empty
                    if (isEmptyFieldData()) {
                        JOptionPane.showMessageDialog(null, 
                        		"Cannot create an empty workout entry.");
                        return;
                    }
                    if (bean.create(w) != null) {
                    	bean.createExercise(currentExercise, w);
                        JOptionPane.showMessageDialog(null, 
                        		"New workout entry was created successfully.");
                        newButton.setText("New");
                        // update the TreeMap
                        bean.updateExercisesMap(currentExercise);
                    }
                    break;
                // user clicked "New"
                // **** BRING THE GUI BACK TO NORMAL WHEN "New"????? *****
                case "New":
                	workoutId += bean.getLastWorkoutId();
                    w.setWorkoutId(workoutId);
                    w.setDate("");
                    w.setWorkoutType("");
                    
                    // Exercise field begins here
                    currentExercise = new Exercise();
                    exerciseId += bean.getLastExerciseId();
                    currentExercise.setExerciseId(exerciseId);
                    currentExercise.setWorkoutId(workoutId);
                    currentExercise.setExerciseName("");
                    currentExercise.setWeightSetsReps("");
                    
                    setFieldData(w);
                    emptyExerciseFields();
                    newButton.setText("Save");
                    clearExerciseListPanel();
                    break;
                    
                // user clicked "Update"
                // ***** When a user creates a new workout entry then tries to update
                // it, there is an error. *****
                case "Update":
                    if (isEmptyFieldData()) {
                       JOptionPane.showMessageDialog(null, 
                    		   "Cannot update an empty workout record");
                       return;
                    }
                    if (bean.update(w) != null)
                       JOptionPane.showMessageDialog(null,"Workout entry with ID:" + 
                       String.valueOf(w.getWorkoutId()
                       + " is updated successfully"));
                    break;
                    
                // user clicked "Delete"
                case "Delete":
                    if (isEmptyFieldData()) {
                       JOptionPane.showMessageDialog(null,
                       "Cannot delete an empty workout record");
                       return;
                    }
                    w = bean.getCurrent();
                    bean.delete();
                    
                    JOptionPane.showMessageDialog(
                       null,"Workout with ID:"
                       + String.valueOf(w.getWorkoutId()
                       + " is deleted successfully"));
                       break;
                // user clicked "First"
		        case "First":
		            setFieldData(bean.moveFirst()); 
		            emptyExerciseFields();
		            listExercises(); break;
		        // user clicked "Previous"
		        case "Previous":
		            setFieldData(bean.movePrevious()); 
		            emptyExerciseFields();
		            listExercises(); break;
		        // user clicked "Next"
		        case "Next":
		            setFieldData(bean.moveNext()); 
		            emptyExerciseFields();
		            listExercises(); break;
		        // user clicked "Last"
		        case "Last":
		            setFieldData(bean.moveLast()); 
		            emptyExerciseFields();
		            listExercises(); break;
                default:
                    JOptionPane.showMessageDialog(null,
                    "invalid command");
            }
        }
    }
    
    private class ExerciseButtonHandler implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent event) {
    		
    		exerciseId = 1001;
    		
    		currentExercise = exerciseGetFieldData(currentExercise);
    		currentExercise.setWorkoutId(Integer.parseInt(workoutIdField.getText()));
    		Workout w = getFieldData();
    		
    		switch (event.getActionCommand()) {
    		
    		case "Add weight, sets, reps":
    			if (workoutIdField.getText().equals("")) {
    				JOptionPane.showMessageDialog(null, "Please create a workout entry first.");
    				return;
    			}
    			if (exerciseField.getText().equals("")) {
    				JOptionPane.showMessageDialog(null, "Please input the name of the exercise.");
    				return;
    			}
    			if (isWSREmpty()) {
                    JOptionPane.showMessageDialog(null, 
                    		"Please input the amount of weight lifted,"
                    		+ " number of sets, and number of reps for each set.");
                    return;
    			}
    			LinkedHashMap<String, String> workoutExercises = bean.getWorkoutExercises();
    			if (bean.addMoreSetsAndReps(currentExercise, workoutExercises) != null) {
    				JOptionPane.showMessageDialog(null, "Additional weight, sets, "
    						+ "and reps added successfully.");
    				// UPDATE TREEMAP
    				bean.updateExercisesMap(currentExercise);
    				listExercises();
                	System.out.println("WORK WORK WORKOUTID: " + currentExercise.getWorkoutId());
    			}
    			break;
    		case "Add Exercise":
    			if (workoutIdField.getText().equals("")) {
    				JOptionPane.showMessageDialog(null, "Please create a workout entry first.");
    				return;
    			}
    			if (exerciseField.getText().equals("")) {
    				JOptionPane.showMessageDialog(null, "Please input the name of the exercise.");
    				return;
    			}
    			if (isWSREmpty()) {
                    JOptionPane.showMessageDialog(null, 
                    		"Please input the amount of weight lifted,"
                    		+ " number of sets, and number of reps for each set.");
                    return;
    			}
    			exerciseId += bean.getLastExerciseId();
    			// ********
    			System.out.println("EXERCISE ID in BUTTON HANDLER: " + exerciseId);
    			currentExercise.setExerciseId(exerciseId);

    			if (bean.createExercise(currentExercise, w) != null) {
    				JOptionPane.showMessageDialog(null, "New exercise created successfully.");
    				// UPDATE TREEMAP
    				bean.updateExercisesMap(currentExercise);
    				listExercises();
                	System.out.println("WORK WORK WORKOUTID: " + currentExercise.getWorkoutId());
    			}
    			break;
    		}
    		
    	}
    }
    
    private void clearExerciseListPanel() {
    	exerciseListPanel.removeAll();
        exerciseListPanel.updateUI();
        exerciseListPanel.revalidate();
        exerciseListPanel.repaint();
    }
	
    private Workout getFieldData() {
        Workout w = new Workout();
        // if the ID Field is not empty... retrieve
        if (!workoutIdField.getText().equals(""))
            w.setWorkoutId(Integer.parseInt(workoutIdField.getText()));
        w.setDate(workoutDateField.getText());
        w.setWorkoutType(workoutTypeField.getText());
        // rest of the fields are retrieved from exerciseGetFieldData()
        return w;
    }
    
    private void setFieldData(Workout w) {
        workoutIdField.setText(String.valueOf(w.getWorkoutId()));
        workoutDateField.setText(w.getDate());
        workoutTypeField.setText(w.getWorkoutType());
    }
    
    private boolean isEmptyFieldData() {
        return (workoutDateField.getText().trim().isEmpty()
            && workoutTypeField.getText().trim().isEmpty()
            && exerciseField.getText().trim().isEmpty()
            && weightField.getText().trim().isEmpty()
            && setsField.getText().trim().isEmpty()
            && repsField.getText().trim().isEmpty());
   }
    
    private Exercise exerciseGetFieldData(Exercise ex) {
    	
    	// set the exercise name...
    	// allow users to set exercise even if weight sets reps fields are empty
    	ex.setExerciseName(exerciseField.getText());
    	
		// save the weightSetsReps in the form: 315*1*1
		String weight = weightField.getText();
		String sets = setsField.getText();
		String reps = repsField.getText();
		String result = weight + "*" + sets + "*" + reps;
		ex.setWeightSetsReps(result);
    	
    	return ex;
    }
    
    private void emptyExerciseFields() {
    	exerciseField.setText("");
    	weightField.setText("");
    	setsField.setText("");
    	repsField.setText("");
    }
    
    private boolean isWSREmpty() {
        return (weightField.getText().trim().isEmpty()
            && setsField.getText().trim().isEmpty()
            && repsField.getText().trim().isEmpty());
   }
	
	/**
	 * Create the frame.
	 */
	public GainzJournalUserInterface() {
		initComponents();
		myInitComponents();
	}
	
	public void myInitComponents() {
        // make ID not editable
        workoutIdField.setEnabled(false);
        
        // action depending on what button the user presses
        newButton.addActionListener(new ButtonHandler());
        updateButton.addActionListener(new ButtonHandler()); 
        deleteButton.addActionListener(new ButtonHandler()); 
        firstButton.addActionListener(new ButtonHandler()); 
        previousButton.addActionListener(new ButtonHandler()); 
        nextButton.addActionListener(new ButtonHandler()); 
        lastButton.addActionListener(new ButtonHandler()); 
        
        addMoreSetsButton.addActionListener(new ExerciseButtonHandler());
        addExerciseButton.addActionListener(new ExerciseButtonHandler());
    }

	public void initComponents() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 472, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 222, 456, 33);
		contentPane.add(panel);
		
		newButton = new JButton("New");
		panel.add(newButton);
		
		updateButton = new JButton("Update");
		panel.add(updateButton);
		
		deleteButton = new JButton("Delete");
		panel.add(deleteButton);
		
		firstButton = new JButton("First");
		panel.add(firstButton);
		
		previousButton = new JButton("Previous");
		panel.add(previousButton);
		
		nextButton = new JButton("Next");
		panel.add(nextButton);
		
		lastButton = new JButton("Last");
		panel.add(lastButton);
		
		JLabel jlabel1 = new JLabel("Click the \"New\" button to submit a new workout entry!");
		jlabel1.setBounds(6, 6, 257, 16);
		contentPane.add(jlabel1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 25, 444, 196);
		contentPane.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblWorkoutId = new JLabel("Workout Date");
		lblWorkoutId.setBounds(7, 13, 74, 16);
		panel_1.add(lblWorkoutId);
		
		workoutDateField = new JTextField();
		workoutDateField.setBounds(85, 7, 122, 28);
		panel_1.add(workoutDateField);
		workoutDateField.setColumns(10);
		
		JLabel lblWorkoutType = new JLabel("Workout Type");
		lblWorkoutType.setBounds(7, 45, 74, 16);
		panel_1.add(lblWorkoutType);
		
		workoutTypeField = new JTextField();
		workoutTypeField.setBounds(85, 39, 122, 28);
		panel_1.add(workoutTypeField);
		workoutTypeField.setColumns(10);
		
		JLabel lblWorkoutDate = new JLabel("Workout ID");
		lblWorkoutDate.setBounds(211, 13, 60, 16);
		panel_1.add(lblWorkoutDate);
		
		workoutIdField = new JTextField();
		workoutIdField.setBounds(275, 7, 156, 28);
		panel_1.add(workoutIdField);
		workoutIdField.setColumns(10);
		
		JPanel exercisePanel = new JPanel();
		exercisePanel.setBounds(7, 71, 424, 55);
		panel_1.add(exercisePanel);
		exercisePanel.setLayout(null);
		
		JLabel lblExercise = new JLabel("Exercise");
		lblExercise.setBounds(6, 6, 55, 16);
		exercisePanel.add(lblExercise);
		
		exerciseField = new JTextField();
		exerciseField.setBounds(61, 1, 122, 26);
		exercisePanel.add(exerciseField);
		exerciseField.setColumns(10);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setBounds(6, 32, 46, 16);
		exercisePanel.add(lblWeight);
		
		weightField = new JTextField();
		weightField.setBounds(47, 29, 40, 26);
		exercisePanel.add(weightField);
		weightField.setColumns(10);
		
		JLabel lblSets = new JLabel("Sets");
		lblSets.setBounds(97, 32, 25, 16);
		exercisePanel.add(lblSets);
		
		setsField = new JTextField();
		setsField.setBounds(124, 29, 30, 26);
		exercisePanel.add(setsField);
		setsField.setColumns(10);
		
		JLabel lblReps = new JLabel("Reps");
		lblReps.setBounds(164, 32, 30, 16);
		exercisePanel.add(lblReps);
		
		repsField = new JTextField();
		repsField.setBounds(197, 29, 30, 26);
		exercisePanel.add(repsField);
		repsField.setColumns(10);
		
		addExerciseButton = new JButton("Add Exercise");
		addExerciseButton.setBounds(193, 0, 99, 28);
		exercisePanel.add(addExerciseButton);
		
		addMoreSetsButton = new JButton("Add weight, sets, reps");
		addMoreSetsButton.setBounds(240, 26, 149, 28);
		exercisePanel.add(addMoreSetsButton);
		
		exerciseListPanel = new JPanel();
		exerciseListPanel.setBounds(0, 129, 438, 61);

		GridBagLayout gbl_exerciseListPanel = new GridBagLayout();
		gbl_exerciseListPanel.columnWidths = new int[]{0};
		gbl_exerciseListPanel.rowHeights = new int[]{0};
		gbl_exerciseListPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_exerciseListPanel.rowWeights = new double[]{Double.MIN_VALUE};
		exerciseListPanel.setLayout(gbl_exerciseListPanel);
		
		scroll = new JScrollPane(exerciseListPanel);
		scroll.setBounds(0, 128, 431, 62);
		scroll.setViewportView(exerciseListPanel);
		
		panel_1.add(scroll, exerciseListPanel);
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GainzJournalUserInterface frame = new GainzJournalUserInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
    // list the exercises for the current workout on the exercise list panel
    private void listExercises() {
        // get all exercises for the current workout
    	LinkedHashMap<String, String> workoutExercises = bean.getWorkoutExercises();
        exerciseListPanel.removeAll();
        exerciseListPanel.updateUI();
        
        JLabel label;
        GridBagLayout gbl_exerciseListPanel = new GridBagLayout();
		gbl_exerciseListPanel.columnWidths = new int[]{0};
		gbl_exerciseListPanel.rowHeights = new int[]{0};
		gbl_exerciseListPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_exerciseListPanel.rowWeights = new double[]{Double.MIN_VALUE};
		exerciseListPanel.setLayout(gbl_exerciseListPanel);
		GridBagConstraints c = new GridBagConstraints();
		
    	int i = 0;
        for (String exercise : workoutExercises.keySet()) {

        	label = new JLabel(exercise + ": " + workoutExercises.get(exercise));
        	c.fill = GridBagConstraints.HORIZONTAL;
        	c.gridx = 0;
        	c.gridy = i;
        	exerciseListPanel.add(label, c);
        	i++;
        }
        
        exerciseListPanel.revalidate();
        exerciseListPanel.repaint();
        
        for (String exercise : workoutExercises.keySet()) {
        	System.out.println("Exercise: " + exercise + ", WSR: " + workoutExercises.get(exercise));
        }
    }
}
