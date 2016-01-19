package my.gainzjournaltesting;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jhl2298
 */

import my.gainzjournal.Workout;
import my.gainzjournal.Exercise;
import java.util.ArrayList;
public class WorkoutTesting {
    
    public static void main(String args[]) {
        WorkoutTest1();
    }
    
    public static void WorkoutTest1() {
        Workout workout1 = new Workout("Chest");
        System.out.println(workout1.getDate());
        System.out.println("Workout type is " + workout1.getWorkoutType() + ".");
        
        Exercise exercise1 = new Exercise("Bench Press");
        System.out.println("Exercise name is " + exercise1.getExerciseName() + ".");
        exercise1.addSetsReps(135, 1, 10);
        exercise1.addSetsReps(185, 1, 8);
        exercise1.addSetsReps(225, 1, 6);
        exercise1.addSetsReps(255, 1, 5);
        exercise1.addSetsReps(275, 1, 5);
        exercise1.addSetsReps(245, 4, 5);
        ArrayList<String> setsAndReps = exercise1.getSetsReps();
        for (int index = 0; index < setsAndReps.size(); index++) {
            System.out.println(setsAndReps.get(index));
        }
        
        workout1.addExercise(exercise1);
    }
    
}
