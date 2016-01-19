package my.gainzjournal.datastructures;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import my.gainzjournal.datastructures.Exercise;
import java.util.Date;
import java.util.ArrayList;

/**
 *
 * @author jhl2298
 */
public class Workout {
    
    // serve as ID... unique to each entry
    private Date workoutDate;
    
    // e.g. Chest, Back, Chest/Back
    private String workoutType;
    
    private ArrayList<Exercise> exercises;
    
    // constructor for Workout data structure
    // parameter is the workout type
    public Workout(String type) {
        // date of workout
        this.workoutDate = new Date();
        this.workoutType = type;
        this.exercises = new ArrayList<>();
    }
    
    public String getWorkoutType() {
        return this.workoutType;
    }
    
    public Workout addExercise(Exercise currentExercise) {
        // with given exercise name, create and add exercise to ArrayList
        this.exercises.add(currentExercise);
        return this;
    }
    
    // return the date of current workout
    public String getDate() {
        String strings[] = this.workoutDate.toString().split(" ");
        return strings[1] + " " + strings[2] + ", " + strings[5];
    }
    
}
