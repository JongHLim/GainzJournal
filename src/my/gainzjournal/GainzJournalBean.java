/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.gainzjournal;

/**
 *
 * @author jhl2298
 */

import java.sql.*;
import java.util.TreeMap;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;

import com.sun.rowset.JdbcRowSetImpl;

import my.gainzjournal.datastructures.*;

public class GainzJournalBean {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Workouts";
    static final String DB_USER = "jhlim84";
    static final String DB_PASS = "jonghoonlim";
    private JdbcRowSet workoutRowSet = null;
    private JdbcRowSet exerciseRowSet = null;
    
    public GainzJournalBean() {
        try {
            Class.forName(JDBC_DRIVER);
            workoutRowSet = new JdbcRowSetImpl();
            workoutRowSet.setUrl(DB_URL);
            workoutRowSet.setUsername(DB_USER);
            workoutRowSet.setPassword(DB_PASS);
            workoutRowSet.setCommand("SELECT * FROM Workout");
            workoutRowSet.execute();
        }
        catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        try {
            Class.forName(JDBC_DRIVER);
            exerciseRowSet = new JdbcRowSetImpl();
            exerciseRowSet.setUrl(DB_URL);
            exerciseRowSet.setUsername(DB_USER);
            exerciseRowSet.setPassword(DB_PASS);
            exerciseRowSet.setCommand("SELECT * FROM Exercise");
            exerciseRowSet.execute();
        }
        catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    // when the user clicks "Save"
    public Workout create(Workout w) {
        try {
            workoutRowSet.moveToInsertRow();
            workoutRowSet.updateInt("workoutId", w.getWorkoutId());
            workoutRowSet.updateString("date", w.getDate());
            workoutRowSet.updateString("workoutType", w.getWorkoutType());
            
            // take care of exercise in createExercise()
            workoutRowSet.insertRow();
            workoutRowSet.moveToCurrentRow();
            
        } catch (SQLException ex) {
            try {
                workoutRowSet.rollback();
                w = null;
            } catch (SQLException e) {

            }
         ex.printStackTrace();
      }
      return w;
   }
    
    // when the user clicks "Save"... take care of the exercise fields
    public Exercise createExercise(Exercise ex, Workout w) {
    	try {
            exerciseRowSet.moveToInsertRow();
            exerciseRowSet.updateInt("exerciseId", ex.getExerciseId());
            exerciseRowSet.updateInt("workoutId", w.getWorkoutId());
            exerciseRowSet.updateString("exercise", ex.getExerciseName());
            exerciseRowSet.updateString("weightSetsReps", ex.getWeightSetsReps());
            
            // take care of exercise in createExercise()
            exerciseRowSet.insertRow();
            exerciseRowSet.moveToCurrentRow();
            
        } catch (SQLException exception) {
            try {
            	exerciseRowSet.rollback();
                ex = null;
            } catch (SQLException e) {

            }
         exception.printStackTrace();
      }
      return ex;
    }
    
    // when the user clicks "Update"
    public Workout update(Workout w) {
        try {
            // no need to update the ID
            workoutRowSet.updateString("date", w.getDate());
            workoutRowSet.updateString("workoutType", w.getWorkoutType());
            // ***** FINISH OTHERS *****
            workoutRowSet.updateRow();
            workoutRowSet.moveToCurrentRow();
        } catch (SQLException ex) {
            try {
                workoutRowSet.rollback();
           } catch (SQLException e) {

           }
           ex.printStackTrace();
        }
        return w;
     }
    
    public void delete() {
        try {
            workoutRowSet.moveToCurrentRow();
            workoutRowSet.deleteRow();
        } catch (SQLException ex) {
           try {
               workoutRowSet.rollback();
           } catch (SQLException e) { }
           ex.printStackTrace();
        }

     }
    
    public Workout getCurrent() {
        Workout w = new Workout();
        try {
            workoutRowSet.moveToCurrentRow();
            w.setWorkoutId(workoutRowSet.getInt("workoutId"));
            w.setDate(workoutRowSet.getString("date"));
            w.setWorkoutType(workoutRowSet.getString("workoutType"));

           // ***** FINISH OTHERS *****
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return w;
     }
    
    public Workout moveFirst() {
        Workout w = new Workout();
        try {
            workoutRowSet.first();
            w.setWorkoutId(workoutRowSet.getInt("workoutId"));
            w.setDate(workoutRowSet.getString("date"));
            w.setWorkoutType(workoutRowSet.getString("workoutType"));
            // ***** FINISH OTHERS *****

        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return w;
     }

     public Workout moveLast() {
        Workout w = new Workout();
        try {
            workoutRowSet.last();
            w.setWorkoutId(workoutRowSet.getInt("workoutId"));
            w.setDate(workoutRowSet.getString("date"));
            w.setWorkoutType(workoutRowSet.getString("workoutType"));
           // ***** FINISH OTHERS *****
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return w;
     }

     public Workout moveNext() {
        Workout w = new Workout();
        try {
           if (workoutRowSet.next() == false)
               workoutRowSet.previous();
           w.setWorkoutId(workoutRowSet.getInt("workoutId"));
           w.setDate(workoutRowSet.getString("date"));
           w.setWorkoutType(workoutRowSet.getString("workoutType"));
           // ***** FINISH OTHERS *****
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return w;
     }

     public Workout movePrevious() {
        Workout w = new Workout();
        try {
           if (workoutRowSet.previous() == false)
               workoutRowSet.next();
           w.setWorkoutId(workoutRowSet.getInt("workoutId"));
           w.setDate(workoutRowSet.getString("date"));
           w.setWorkoutType(workoutRowSet.getString("workoutType"));
           // ***** FINISH OTHERS *****
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return w;
     }
     
     public Exercise addMoreSetsAndReps(Exercise ex) {
    	 try {
    		 // update to the form 135*1*10/185*1*10
    		 exerciseRowSet.next();
    		 String newWsr = ex.getWeightSetsReps();
    		 String oldWsr = exerciseRowSet.getString("weightSetsReps");
    		 exerciseRowSet.updateString("weightSetsReps", oldWsr + "/" + newWsr);
    		 exerciseRowSet.updateRow();
    		 exerciseRowSet.moveToCurrentRow();
    	 } catch (SQLException exception) {
             try {
            	 exerciseRowSet.rollback();
            } catch (SQLException e) {

            }
            exception.printStackTrace();
         }
    	 return ex;
     }
     
     public int getLastWorkoutId() {
    	 int id = 0;
    	 try {
    		 if (workoutRowSet.first()) {
        		 workoutRowSet.last();
        		 id += workoutRowSet.getInt("workoutId");
        		 return id;
    		 }
    	 } catch(SQLException e) {
    		 e.printStackTrace();
    	 }
    	 return id;
     }
     
     public int getLastExerciseId() {
    	 int id = 0;
    	 try {
    		 if (exerciseRowSet.first()) {
        		 exerciseRowSet.last();
        		 id += exerciseRowSet.getInt("exerciseId");
        		 return id;
    		 }
    	 } catch(SQLException e) {
    		 e.printStackTrace();
    	 }
    	 return id;
     }
     
     // create a TreeMap of <Workout ID, <Exercise, WeightSetsReps>>
     public TreeMap<Integer, TreeMap<String, String>> fillExerciseMap() {
    	 TreeMap<Integer, TreeMap<String, String>> result = new TreeMap<>();
    	 TreeMap<String, String> exercises;
    	 try {
    		 // RowSet is empty
        	 if (!exerciseRowSet.isBeforeFirst())
        		 // return empty TreeMap
        		 return result;
        	 exerciseRowSet.beforeFirst();
        	 while (exerciseRowSet.next()) {
        		 int workoutId = exerciseRowSet.getInt("workoutId");
        		 String exercise = exerciseRowSet.getString("exercise");
        		 String weightSetsReps = exerciseRowSet.getString("weightSetsReps");
        		 // the TreeMap does not contain the current workout ID
        		 if (!result.containsKey(workoutId)) {
        			 exercises = new TreeMap<>();
        			 // add the new exercise for this workout ID
        			 exercises.put(exercise, weightSetsReps);
        			 result.put(workoutId, exercises);
        		 }
        		 // the TreeMap contains the current workout ID, just update it by adding the new exercise
        		 else {
        			 exercises = result.get(workoutId);
        			 exercises.put(exercise, weightSetsReps);
        			 result.put(workoutId, exercises);
        		 }
        	 }
    	 } catch (SQLException e) {
    		 e.printStackTrace();
    	 }
    	 return result;
     }
}
