package my.gainzjournal.datastructures;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jhl2298
 */

import java.util.ArrayList;

public class Exercise {

	private int exerciseId;
    private int workoutId;
    private String exerciseName;
    private String weightSetsReps;

    public Exercise() {

    }
    
    public void setExerciseId(int eid) {
    	exerciseId = eid;
    }
    
    public int getExerciseId() {
    	return this.exerciseId;
    }
    
    public void setWorkoutId(int id) {
    	workoutId = id;
    }
    
    public int getWorkoutId() {
    	return this.workoutId;
    }
    
    public void setExerciseName(String name) {
    	exerciseName = name;
    }
    
    public String getExerciseName() {
        return this.exerciseName;
    }
    
    public void setWeightSetsReps(String value) {
    	weightSetsReps = value;
    }
    
    public String getWeightSetsReps() {
    	return this.weightSetsReps;
    }
        
}