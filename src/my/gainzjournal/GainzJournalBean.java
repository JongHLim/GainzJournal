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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.sql.rowset.JdbcRowSet;
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
	private TreeMap<Integer, LinkedHashMap<String, String>> exerciseTreeMap = new TreeMap<>();
	// to use for deleting the workouts in the database
	private TreeMap<Integer, HashSet<Integer>> allExerciseIds = new TreeMap<>();
	private int lastExerciseId = 0;
	 
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
    
    public void saveHelper() {
    	try {
    		workoutRowSet.next();
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    // when the user clicks "Save"
    public Workout create(Workout w) {
    	int id =0;
        try {
            workoutRowSet.moveToInsertRow();
            workoutRowSet.updateInt("workoutId", w.getWorkoutId());
            id = w.getWorkoutId();
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
        try {
        	if (id != 0) {
            	exerciseRowSet.setCommand("SELECT * FROM Exercise WHERE workoutId='" + id + "';");
            	exerciseRowSet.execute();
            	exerciseRowSet.next();
        	}

        } catch (SQLException ex) {
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
    	int workoutId = 0;
        try {
            workoutRowSet.moveToCurrentRow();
            workoutId = workoutRowSet.getInt("workoutId");
            workoutRowSet.deleteRow();
        } catch (SQLException ex) {
           try {
               workoutRowSet.rollback();
           } catch (SQLException e) { }
           ex.printStackTrace();
        }
        
        // DELETE ALL exercises for current workout
        HashSet<Integer> exerciseIds = allExerciseIds.get(workoutId);
        Iterator<Integer> it = exerciseIds.iterator();
        try {
        	while (it.hasNext()) {
        		int exerciseId = it.next();
        		exerciseRowSet.setCommand("SELECT * FROM Exercise WHERE exerciseId='" + exerciseId + "';");
        		exerciseRowSet.execute();
        		exerciseRowSet.next();
        		exerciseRowSet.deleteRow();
        	}
        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
     }
    
    public Workout getCurrent() {
        Workout w = new Workout();
        int id = 0;
        try {
            workoutRowSet.moveToCurrentRow();
            w.setWorkoutId(workoutRowSet.getInt("workoutId"));
            id = workoutRowSet.getInt("workoutId");
            w.setDate(workoutRowSet.getString("date"));
            w.setWorkoutType(workoutRowSet.getString("workoutType"));

           // ***** FINISH OTHERS *****
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        try {
        	if (id != 0) {
            	exerciseRowSet.setCommand("SELECT * FROM Exercise WHERE workoutId='" + id + "';");
            	exerciseRowSet.execute();
            	exerciseRowSet.next();
        	}

        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return w;
     }
    
    public Workout moveFirst() {
        Workout w = new Workout();
        int id = 0;
        try {
            workoutRowSet.first();
            w.setWorkoutId(workoutRowSet.getInt("workoutId"));
            id = workoutRowSet.getInt("workoutId");
            w.setDate(workoutRowSet.getString("date"));
            w.setWorkoutType(workoutRowSet.getString("workoutType"));
            // ***** FINISH OTHERS *****

        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        
        try {
        	if (id != 0) {
            	exerciseRowSet.setCommand("SELECT * FROM Exercise WHERE workoutId='" + id + "';");
            	exerciseRowSet.execute();
            	exerciseRowSet.next();
        	}

        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return w;
     }

     public Workout moveLast() {
        Workout w = new Workout();
        int id = 0;
        try {
            workoutRowSet.last();
            w.setWorkoutId(workoutRowSet.getInt("workoutId"));
            id = workoutRowSet.getInt("workoutId");
            w.setDate(workoutRowSet.getString("date"));
            w.setWorkoutType(workoutRowSet.getString("workoutType"));
           // ***** FINISH OTHERS *****
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        try {
        	if (id != 0) {
            	exerciseRowSet.setCommand("SELECT * FROM Exercise WHERE workoutId='" + id + "';");
            	exerciseRowSet.execute();
            	exerciseRowSet.next();
        	}

        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return w;
     }

     public Workout moveNext() {
        Workout w = new Workout();
        int id = 0;
        try {
           if (workoutRowSet.next() == false)
               workoutRowSet.previous();
           w.setWorkoutId(workoutRowSet.getInt("workoutId"));
           id = workoutRowSet.getInt("workoutId");
           w.setDate(workoutRowSet.getString("date"));
           w.setWorkoutType(workoutRowSet.getString("workoutType"));
           // ***** FINISH OTHERS *****
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        try {
        	if (id != 0) {
            	exerciseRowSet.setCommand("SELECT * FROM Exercise WHERE workoutId='" + id + "';");
            	exerciseRowSet.execute();
            	exerciseRowSet.next();
        	}

        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return w;
     }

     public Workout movePrevious() {
        Workout w = new Workout();
        int id = 0;
        try {
           if (workoutRowSet.previous() == false)
               workoutRowSet.next();
           w.setWorkoutId(workoutRowSet.getInt("workoutId"));
           id = workoutRowSet.getInt("workoutId");
           w.setDate(workoutRowSet.getString("date"));
           w.setWorkoutType(workoutRowSet.getString("workoutType"));
           // ***** FINISH OTHERS *****
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        try {
        	if (id != 0) {
            	exerciseRowSet.setCommand("SELECT * FROM Exercise WHERE workoutId='" + id + "';");
            	exerciseRowSet.execute();
            	exerciseRowSet.next();
        	}

        } catch (SQLException ex) {
        	ex.printStackTrace();
        }
        return w;
     }
     
     public Exercise addMoreSetsAndReps(Exercise ex, int wid) {
    	 try {
    		 
    		 // get the exercise that the user is trying to update
    		 try {
            	exerciseRowSet.setCommand("SELECT * FROM Exercise WHERE exercise='" + ex.getExerciseName() + "' "
            			+ "AND workoutId='" + wid + "';");
            	exerciseRowSet.execute();
            	exerciseRowSet.next();
    		 } catch (SQLException e) {
    			e.printStackTrace();
    		 }
    		 
    		 // update to the form 135*1*10/185*1*10
    		 if (exerciseRowSet.isAfterLast())
    			 exerciseRowSet.previous();
    		 else
    			 exerciseRowSet.moveToCurrentRow();
    		 String newWsr = ex.getWeightSetsReps();
    		 String oldWsr = exerciseRowSet.getString("weightSetsReps");
    		 exerciseRowSet.updateString("weightSetsReps", oldWsr + "/" + newWsr);
    		 ex.setWeightSetsReps(oldWsr + "/" + newWsr);
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
     
     // create a TreeMap of <Workout ID, <Exercise, WeightSetsReps>>
     public TreeMap<Integer, LinkedHashMap<String, String>> fillExerciseMap() {
    	 LinkedHashMap<String, String> exercises;
    	 HashSet<Integer> exerciseIds;
    	 try {
    		 // RowSet is empty
        	 if (!exerciseRowSet.isBeforeFirst())
        		 // return empty TreeMap
        		 return exerciseTreeMap;
        	 exerciseRowSet.beforeFirst();
        	 while (exerciseRowSet.next()) {
        		 int exerciseId = exerciseRowSet.getInt("exerciseId");
        		 int workoutId = exerciseRowSet.getInt("workoutId");
        		 String exercise = exerciseRowSet.getString("exercise");
        		 String weightSetsReps = exerciseRowSet.getString("weightSetsReps");
        		 // the TreeMap does not contain the current workout ID
        		 if (!exerciseTreeMap.containsKey(workoutId)) {
        			 exercises = new LinkedHashMap<>();
        			 // add the new exercise for this workout ID
        			 exercises.put(exercise, weightSetsReps);
        			 exerciseTreeMap.put(workoutId, exercises);
        		 }
        		 // the TreeMap contains the current workout ID, just update it by adding the new exercise
        		 else {
        			 exercises = exerciseTreeMap.get(workoutId);
        			 exercises.put(exercise, weightSetsReps);
        			 exerciseTreeMap.put(workoutId, exercises);
        		 }
        		 int id = exerciseRowSet.getInt("exerciseId");
        		 if (id > lastExerciseId)
        			 lastExerciseId = id;
        		 
        		 // for exercise IDs
        		 if (allExerciseIds.containsKey(workoutId))
        			 exerciseIds = allExerciseIds.get(workoutId);
        		 else
        			 exerciseIds = new HashSet<>();
    			 exerciseIds.add(exerciseId);
    			 allExerciseIds.put(workoutId, exerciseIds);
        	 }
    	 } catch (SQLException e) {
    		 e.printStackTrace();
    	 }
    	 return exerciseTreeMap;
     }
     
     // we need to update the map every time we add more (weight, sets, reps) and new exercises
     public void updateExercisesMap(Exercise ex) {
    	 int workoutId = ex.getWorkoutId();
    	 String exerciseName = ex.getExerciseName();
    	 String wsr = ex.getWeightSetsReps();
    	 // *****
    	 System.out.println("updating exercise map... WSR: " + wsr);
    	 System.out.println("WORKOUT ID: " + workoutId);
    	 LinkedHashMap<String, String> exercises;
    	 if (exerciseTreeMap.containsKey(workoutId)) {
    		 exercises = exerciseTreeMap.get(workoutId);

    	 } else {
    		 exercises = new LinkedHashMap<>();

    	 }
		 exercises.put(exerciseName, wsr);
		 exerciseTreeMap.put(workoutId, exercises);
		 
		 int exerciseId = ex.getExerciseId();
		 if (exerciseId > lastExerciseId)
			 lastExerciseId = exerciseId;
		 
		 // for exercise IDs
		 HashSet<Integer> exerciseIds;
		 if (allExerciseIds.containsKey(workoutId))
			 exerciseIds = allExerciseIds.get(workoutId);
		 else
			 exerciseIds = new HashSet<>();
		 exerciseIds.add(exerciseId);
		 allExerciseIds.put(workoutId, exerciseIds);
     }
     
     public LinkedHashMap<String, String> getWorkoutExercises(boolean justSaved) {
    	 LinkedHashMap<String, String> currentExercises;
    	 try {
    		 if (justSaved)
    			 workoutRowSet.next();
    		 else
    			 workoutRowSet.moveToCurrentRow();
        	 int id = workoutRowSet.getInt("workoutId");
        	 currentExercises = exerciseTreeMap.get(id);
        	 return currentExercises;
    	 } catch (SQLException e) {
    		 
    	 }
    	 currentExercises = new LinkedHashMap<>();
    	 return currentExercises;
     }
     
     public int getLastExerciseId() {
    	 return this.lastExerciseId;
     }
}
