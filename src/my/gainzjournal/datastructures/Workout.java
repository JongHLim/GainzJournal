package my.gainzjournal.datastructures;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author jhl2298
 */
public class Workout {
    
    // serve as ID... unique to each entry
    private String workoutDate;
    
    // e.g. Chest, Back, Chest/Back
    private String workoutType;
    
    //private ArrayList<Exercise> exercises;
    
    private int workoutId;
    
    // constructor for Workout data structure
    // parameter is the workout type
    public Workout() {
        //this.exercises = new ArrayList<>();
    }
    
    public void setDate(String date) {
        this.workoutDate = date;
    }
    
        // return the date of current workout
    // ***** CHANGED ***** from above
    public String getDate() {
    	// for date automation... decided not to use
//        String strings[] = this.workoutDate.toString().split(" ");
//        return strings[1] + " " + strings[2] + ", " + strings[5];
    	return this.workoutDate;
    }
    
    public void setWorkoutId(int id) {
        this.workoutId = id;
    }
    
    public int getWorkoutId() {
        return this.workoutId;
    }
    
    public void setWorkoutType(String type) {
        this.workoutType = type;
    }
    
    public String getWorkoutType() {
        return this.workoutType;
    }
    
//    public Workout addExercise(Exercise currentExercise) {
//        // with given exercise name, create and add exercise to ArrayList
//        this.exercises.add(currentExercise);
//        return this;
//    }
    
}
