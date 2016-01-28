package my.gainzjournal;

import java.awt.EventQueue;

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
import java.util.Random;

import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class GainzJournalUserInterface extends JFrame {

    private GainzJournalBean bean = new GainzJournalBean();
	
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
	
    private class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
        	
        	Workout w = getFieldData();
        	
        	Exercise currentExercise = exerciseGetFieldData();
        	
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
                    }
                    break;
                // user clicked "New"
                // **** BRING THE GUI BACK TO NORMAL WHEN "New"????? *****
                case "New":
                    w.setWorkoutId(new Random().nextInt(Integer.MAX_VALUE) + 1);
                    w.setDate("");
                    w.setWorkoutType("");
                    
                    // Exercise field begins here
                    currentExercise.setExerciseId(0);
                    currentExercise.setWorkoutId(0);
                    currentExercise.setExerciseName("");
                    currentExercise.setWeightSetsReps("");
                    
                    setFieldData(w);
                    exerciseSetFieldData(currentExercise);
                    newButton.setText("Save");
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
		            setFieldData(bean.moveFirst()); break;
		        // user clicked "Previous"
		        case "Previous":
		            setFieldData(bean.movePrevious()); break;
		        // user clicked "Next"
		        case "Next":
		            setFieldData(bean.moveNext()); break;
		        // user clicked "Last"
		        case "Last":
		            setFieldData(bean.moveLast()); break;
                default:
                    JOptionPane.showMessageDialog(null,
                    "invalid command");
            }
        }
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
    
    private class ExerciseButtonHandler implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent event) {
    		
    		Exercise currentExercise = exerciseGetFieldData();
    		
    		switch (event.getActionCommand()) {
    		
    		case "Add weight, sets, reps":
    			if (isWSREmpty()) {
                    JOptionPane.showMessageDialog(null, 
                    		"Please input the amount of weight lifted,"
                    		+ " number of sets, and number of reps for each set.");
                    return;
    			}
    			if (bean.addMoreSetsAndReps(currentExercise) != null) {
    				JOptionPane.showMessageDialog(null, 
                    		"Additional weight, sets, and reps added "
                    		+ "successfully.");
    			}
    			break;
    		case "Add Exercise":
    			break;
    			
    		}
    		
    	}
    }
    
    private Exercise exerciseGetFieldData() {
    	Exercise ex = new Exercise();
    	// assign a exercise ID every time an Exercise Object is created
    	int currentExerciseId = new Random().nextInt(Integer.MAX_VALUE) + 1;
    	ex.setExerciseId(currentExerciseId);
    	
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
    
    private void exerciseSetFieldData(Exercise currentExercise) {
    	exerciseField.setText(currentExercise.getExerciseName());
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
		
		JLabel jlabel1 = new JLabel("Click the \"New\" to submit a new workout entry!");
		jlabel1.setBounds(6, 6, 257, 16);
		contentPane.add(jlabel1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 25, 444, 196);
		contentPane.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new MigLayout("", "[][grow]", "[][][grow]"));
		
		JLabel lblWorkoutId = new JLabel("Workout Date");
		panel_1.add(lblWorkoutId, "cell 0 0,alignx trailing");
		
		workoutDateField = new JTextField();
		panel_1.add(workoutDateField, "flowx,cell 1 0,alignx left");
		workoutDateField.setColumns(10);
		
		JLabel lblWorkoutType = new JLabel("Workout Type");
		panel_1.add(lblWorkoutType, "cell 0 1,alignx trailing");
		
		workoutTypeField = new JTextField();
		panel_1.add(workoutTypeField, "cell 1 1,alignx left");
		workoutTypeField.setColumns(10);
		
		JLabel lblWorkoutDate = new JLabel("Workout ID");
		panel_1.add(lblWorkoutDate, "cell 1 0");
		
		workoutIdField = new JTextField();
		panel_1.add(workoutIdField, "cell 1 0,growx");
		workoutIdField.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, "cell 0 2 2 1,grow");
		panel_2.setLayout(null);
		
		JLabel lblExercise = new JLabel("Exercise");
		lblExercise.setBounds(6, 6, 55, 16);
		panel_2.add(lblExercise);
		
		exerciseField = new JTextField();
		exerciseField.setBounds(61, 1, 122, 26);
		panel_2.add(exerciseField);
		exerciseField.setColumns(10);
		
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setBounds(6, 34, 46, 16);
		panel_2.add(lblWeight);
		
		weightField = new JTextField();
		weightField.setBounds(47, 31, 40, 26);
		panel_2.add(weightField);
		weightField.setColumns(10);
		
		JLabel lblSets = new JLabel("Sets");
		lblSets.setBounds(97, 34, 25, 16);
		panel_2.add(lblSets);
		
		setsField = new JTextField();
		setsField.setBounds(124, 31, 30, 26);
		panel_2.add(setsField);
		setsField.setColumns(10);
		
		JLabel lblReps = new JLabel("Reps");
		lblReps.setBounds(164, 34, 30, 16);
		panel_2.add(lblReps);
		
		repsField = new JTextField();
		repsField.setBounds(197, 31, 30, 26);
		panel_2.add(repsField);
		repsField.setColumns(10);
		
		addExerciseButton = new JButton("Add Exercise");
		addExerciseButton.setBounds(193, 0, 99, 28);
		panel_2.add(addExerciseButton);
		
		addMoreSetsButton = new JButton("Add weight, sets, reps");
		addMoreSetsButton.setBounds(240, 28, 149, 28);
		panel_2.add(addMoreSetsButton);
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
}
