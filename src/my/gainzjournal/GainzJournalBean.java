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

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.JoinRowSet;

import com.sun.rowset.CachedRowSetImpl;
import com.sun.rowset.JdbcRowSetImpl;
import com.sun.rowset.JoinRowSetImpl;

import my.gainzjournal.datastructures.*;

public class GainzJournalBean {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/Workouts";
    static final String DB_USER = "jhlim84";
    static final String DB_PASS = "jonghoonlim";
    private JdbcRowSet workoutRowSet = null;
    private JdbcRowSet exerciseRowSet = null;
    private JoinRowSet joinRowSet = null;
    
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
        
//        try {
//        	Class.forName(JDBC_DRIVER);
//        	JoinRowSet joinRowSet = new JoinRowSetImpl();
//        	joinRowSet.setUrl(DB_URL);
//        	joinRowSet.setUsername(DB_USER);
//        	joinRowSet.setPassword(DB_PASS);
//        	
//        	CachedRowSet workoutCached = new CachedRowSetImpl();
//        	workoutCached.populate(workoutRowSet);
//        	workoutCached.setMatchColumn(1);
//            joinRowSet.addRowSet(workoutCached);
//            
//            CachedRowSet exerciseCached = new CachedRowSetImpl();
//            exerciseCached.populate(exerciseRowSet);
//            exerciseCached.setMatchColumn(2);
//            joinRowSet.addRowSet(exerciseCached);
//            
//        } catch (SQLException | ClassNotFoundException ex) {
//        	ex.printStackTrace();
//        }
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
}
