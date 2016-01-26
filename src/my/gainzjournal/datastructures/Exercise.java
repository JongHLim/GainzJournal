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
        
    // data structure to represent the weight, sets and reps for an exercise
    public class WeightSetsByReps {

    	private int setsId;
    	private int exerciseId;
        private int weight;
        private int sets;
        private int reps;

        public WeightSetsByReps() {

        }

        public void setSetsId(int sid) {
        	setsId = sid;
        }
        
        public int getSetsId() {
        	return this.setsId;
        }
        
        public void setExerciseId(int eid) {
        	exerciseId = eid;
        }
        
        public int getExerciseId() {
        	return this.exerciseId;
        }
        
        public void setWeight(int weightNumber) {
        	weight = weightNumber;
        }
        
        public int getWeight() {
        	return this.weight;
        }
        
        public void setSets(int setNumber) {
        	sets = setNumber;
        }
        
        public int getSets() {
        	return this.sets;
        }
        
        public void setReps(int repNumber) {
        	reps = repNumber;
        }
        
        public int getReps() {
        	return this.reps;
        }
        
    }
        
}