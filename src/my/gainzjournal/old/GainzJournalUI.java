package my.gainzjournal.old;

import my.gainzjournal.GainzJournalBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jhl2298
 */

import my.gainzjournal.datastructures.*;
import static javax.swing.ScrollPaneConstants.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;
import java.awt.event.ActionEvent;

public class GainzJournalUI extends javax.swing.JFrame {

    private GainzJournalBean bean = new GainzJournalBean();
    
    /**
     * Creates new form GainzJournalUI
     */
    public GainzJournalUI() {
        initComponents();
        myInitComponents();
    }
    
    /*
     * since the NetBeans IDE uses code folding, use my method to initialize
     * components
     */
    public void myInitComponents() {
        // get rid of horizontal scroll for jScrollPane1
        jScrollPane1.setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);
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
    
    private void exerciseSetFieldData(Exercise currentExercise) {
        exerciseField.setText(currentExercise.getExerciseName());
        weightField.setText("");
        setsField.setText("");
        repsField.setText("");
    }
       
    private boolean isEmptyFieldData() {
        return (workoutDateField.getText().trim().isEmpty()
            && workoutTypeField.getText().trim().isEmpty()
            && exerciseField.getText().trim().isEmpty()
            && weightField.getText().trim().isEmpty()
            && setsField.getText().trim().isEmpty()
            && repsField.getText().trim().isEmpty());
   }
    
    private Exercise exerciseGetFieldData() {
        Exercise ex = new Exercise();
        // assign a exercise ID everytime an Exercise Object is created
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
    
    private boolean isWSREmpty() {
        return (weightField.getText().trim().isEmpty()
            && setsField.getText().trim().isEmpty()
            && repsField.getText().trim().isEmpty());
   }
    
    private class ExerciseButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            
            Exercise currentExercise = exerciseGetFieldData();
            
            switch (event.getActionCommand()) {
            
            case "Add more sets and reps":
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
            case "Add exercise":
                break;
                
            }
            
        }
    }
    
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        
        newButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        firstButton = new javax.swing.JButton();
        previousButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        lastButton = new javax.swing.JButton();
        
        workoutIdField = new javax.swing.JTextField();
        workoutDateField = new javax.swing.JTextField();
        workoutTypeField = new javax.swing.JTextField();
        exerciseField = new javax.swing.JTextField();
        weightField = new javax.swing.JTextField();
        setsField = new javax.swing.JTextField();
        repsField = new javax.swing.JTextField();
        
        // "Add more sets and reps" Button
        addMoreSetsButton = new javax.swing.JButton();
        // "Add exercise" Button
        addExerciseButton = new javax.swing.JButton();
        
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        newButton.setText("New");

        jPanel1.add(newButton, new java.awt.GridBagConstraints());

        updateButton.setText("Update");
        jPanel1.add(updateButton, new java.awt.GridBagConstraints());

        deleteButton.setText("Delete");
        jPanel1.add(deleteButton, new java.awt.GridBagConstraints());

        firstButton.setText("First");
        jPanel1.add(firstButton, new java.awt.GridBagConstraints());

        previousButton.setText("Previous");
        jPanel1.add(previousButton, new java.awt.GridBagConstraints());

        nextButton.setText("Next");
        jPanel1.add(nextButton, new java.awt.GridBagConstraints());

        lastButton.setText("Last");
        jPanel1.add(lastButton, new java.awt.GridBagConstraints());

        jLabel1.setText("Workout date");

        jLabel2.setText("Workout type");

        jLabel3.setText("Exercise");

        jLabel4.setText("Weight");

        addMoreSetsButton.setText("Add more sets and reps");

        jLabel5.setText("Sets");

        jLabel6.setText("Reps");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exerciseField, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(weightField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(setsField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(repsField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addMoreSetsButton)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(exerciseField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(weightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(setsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(repsField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addMoreSetsButton))
                .addContainerGap())
        );

        addExerciseButton.setText("Add exercise");

        jLabel7.setText("Workout ID");

        jLabel8.setText("Click the \"New\" button to submit a new workout entry!");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(workoutDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(workoutTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(workoutIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addExerciseButton)
                    .addComponent(jLabel8))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(5, 5, 5)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(workoutDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(workoutIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(workoutTypeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addExerciseButton)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GainzJournalUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GainzJournalUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GainzJournalUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GainzJournalUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GainzJournalUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton firstButton;
    private javax.swing.JButton lastButton;
    private javax.swing.JButton newButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton previousButton;
    private javax.swing.JButton updateButton;
    
    private javax.swing.JButton addMoreSetsButton;
    private javax.swing.JButton addExerciseButton;
    
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JTextField exerciseField;
    private javax.swing.JTextField weightField;
    private javax.swing.JTextField setsField;
    private javax.swing.JTextField repsField;
    
    private javax.swing.JTextField workoutIdField;
    private javax.swing.JTextField workoutDateField;
    private javax.swing.JTextField workoutTypeField;
    // End of variables declaration//GEN-END:variables
}
