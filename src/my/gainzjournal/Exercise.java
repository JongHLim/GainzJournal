package my.gainzjournal;

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
            
    private String exerciseName;

    private ArrayList<String> setsAndReps;

    public Exercise(String name) {
        this.exerciseName = name;
        this.setsAndReps = new ArrayList<>();
    }
    
    public String getExerciseName() {
        return this.exerciseName;
    }

    // enable the user to add the weight lifted, number of sets and number of reps
    // e.g., 145 lbs x 10 sets x 10 reps
    public Exercise addSetsReps(int weightLifted, int numSets, int numReps) {
        WeightSetsByReps currentSets = new WeightSetsByReps(weightLifted, numSets, numReps);
        this.setsAndReps.add(currentSets.toString());
        return this;
    }
    
    public ArrayList<String> getSetsReps() {
        return this.setsAndReps;
    }
        
    // data structure to represent the weight, sets and reps for an exercise
    public class WeightSetsByReps {

        private int weight;
        private int sets;
        private int reps;

        public WeightSetsByReps(int weightLifted, int numSets, int numReps) {
            weight = weightLifted;
            sets = numSets;
            reps = numReps;
        }
        
        // for example: 135 lbs x 1 x 10
        public String toString() {
            String weight = Integer.toString(this.weight);
            String sets = Integer.toString(this.sets);
            String reps = Integer.toString(this.reps);
            return weight + " lbs x " + sets + " sets x " + reps + " reps";
        }

    }
        
}