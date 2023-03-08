/*
 * Name: Roberto Reyes
 * EID: rcr2662
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {


    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {
        ArrayList<Integer> stu_matching = problem.getStudentMatching();
        ArrayList<ArrayList<Integer>> hs_pref = problem.getHighSchoolPreference();
        ArrayList<ArrayList<Integer>> stu_pref = problem.getStudentPreference();

        //compares each match with all of the others to check for instabilities
        for(int i = 0; i < stu_matching.size(); i++){
            for(int j = i + 1; j < stu_matching.size(); j++){
                int firstMatch = stu_matching.get(i);
                int secondMatch = stu_matching.get(j);
                //hs prefers unmatched over the one it is matched to
                if (firstMatch == -1 && secondMatch != -1) {
                    ArrayList<Integer> preferences = hs_pref.get(secondMatch);
                    // use indexOf method to find the ranking in the list of preferences
                    if (preferences.indexOf(i) < preferences.indexOf(j)) {
                        return false;
                    }
                //hs prefers unmatched over the one it is matched to
                } else if (secondMatch == -1 && firstMatch != -1) {
                    ArrayList<Integer> preferences = hs_pref.get(firstMatch);
                    if (preferences.indexOf(j) < preferences.indexOf(i)) {
                        return false;
                    }
                //both matched but prefer the one the other is matched to
                } else if (firstMatch != -1 && secondMatch != -1) {
                    ArrayList<Integer> hsPreference = hs_pref.get(firstMatch);
                    ArrayList<Integer> studentPreference = stu_pref.get(j);
                    if (hsPreference.indexOf(j) < hsPreference.indexOf(i) &&
                        studentPreference.indexOf(firstMatch) < studentPreference.indexOf(secondMatch)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {
        ArrayList<ArrayList<Integer>> hs_pref = problem.getHighSchoolPreference();
        ArrayList<ArrayList<Integer>> stu_pref = problem.getStudentPreference();
        int hs_num = problem.getHighSchoolCount();
        int stu_num = problem.getStudentCount();
        ArrayList<Integer> hs_spots = problem.getHighSchoolSpots();
        ArrayList<Integer> stu_matching = new ArrayList<>();
        ArrayList<ArrayList<Integer>> hs_matching = new ArrayList<>();
        int student = 0;
        int hs = 0;
        int lowest = 0;

        //initialize all students to be unmatched
        for (int i = 0; i < stu_num; i++){
            stu_matching.add(-1);
        }

        //fill hs matching list
        for (int i = 0; i < hs_num; i++){
            hs_matching.add(new ArrayList<Integer>());
            System.out.println("matching size " +hs_matching.size());
        }

        for (int i = 0; i < hs_num; i++){
            (hs_matching.get(i)).add(-1);
        }
        
        //add all students to queue
        ArrayList<Integer> queue = new ArrayList<>();
        for (int i = 0; i < stu_num; i++){
            queue.add(i);
        }

        //while a student is available that hasn't made an offer to every hs
        while(queue.size() > 0){
            student  = queue.get(0);    //available student
            //if student has made an offer to every hs, skip
            if (stu_pref.get(student).size() <= 0) {
                queue.remove(0);
                continue;
            }
            hs = stu_pref.get(student).get(0);  //highest hs in student's pref list they have not offered to
            stu_pref.get(student).remove(0);    //remove hs from pref list so student cannot offer again
            //hs is not full
            if(hs_spots.get(hs) > 0){
                stu_matching.set(student, hs);
                hs_matching.get(hs).add(student);
                hs_spots.set(hs, hs_spots.get(hs) - 1);
                queue.remove(0);
            //hs is full
            } else {
                lowest = hs_matching.get(hs).get(1);
                //find hs's lowest preferred matched student
                for (int i = 1; i < hs_matching.get(hs).size(); i++){
                    if (hs_pref.get(hs).indexOf(lowest) < hs_pref.get(hs).indexOf(hs_matching.get(hs).get(i))){
                        lowest = hs_matching.get(hs).get(i);
                    }
                }
                //if student is preferred over lowest preferred matched student, add lowest matched back to queue
                //and match hs and student
                if (hs_pref.get(hs).indexOf(student) < hs_pref.get(hs).indexOf(lowest)){
                    stu_matching.set(lowest, -1);
                    (hs_matching.get(hs)).remove(hs_matching.get(hs).indexOf(lowest));
                    (hs_matching.get(hs)).add(student);
                    stu_matching.set(student, hs);
                    queue.add(lowest);
                    queue.remove(0);
                }
            }
            
        }
        problem.setStudentMatching(stu_matching);
        return problem;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_highschooloptimal(Matching problem) {
        ArrayList<ArrayList<Integer>> hs_pref = problem.getHighSchoolPreference();
        ArrayList<ArrayList<Integer>> stu_pref = problem.getStudentPreference();
        int hs_num = problem.getHighSchoolCount();
        int stu_num = problem.getStudentCount();
        ArrayList<Integer> hs_spots = problem.getHighSchoolSpots();
        ArrayList<Integer> stu_matching = new ArrayList<>();
        ArrayList<ArrayList<Integer>> hs_matching = new ArrayList<>(hs_num);
        int student = 0;
        int hs = 0;
        ArrayList<Integer> hs_match = new ArrayList<>();
        hs_match.add(-1);

        //initialize all students to be unmatched
        for (int i = 0; i < stu_num; i++){
            stu_matching.add(-1);
        }

        //initialize hs matching
        for (int i = 0; i < hs_num; i++){
            hs_matching.add(hs_match);
        }

        //add all students to queue
        ArrayList<Integer> queue = new ArrayList<>();
        for (int i = 0; i < hs_num; i++){
            queue.add(i);
        }

        //while there is a hs available that has not offered to every student
        while(queue.size() > 0){
            hs = queue.get(0);  //available hs
            //if hs is full or has offered to every student, skip
            if(hs_spots.get(hs) <= 0 || hs_pref.get(hs).size() <= 0) {
                queue.remove(0);
                continue;
            }
            student = hs_pref.get(hs).get(0);   //highest preferred student hs has not offered to
            //remove student so hs cannot offer to them again
            hs_pref.get(hs).remove(0);
            //if student is free, match
            if(stu_matching.get(student) == -1){
                hs_matching.get(hs).add(student);
                stu_matching.set(student, hs);
                hs_spots.set(hs, hs_spots.get(hs) - 1);
            //student is not free
            } else {
                //if student prefers hs over its match hs, match hs and student, add previously match hs to queue
                if(stu_pref.get(student).indexOf(hs) < stu_pref.get(student).indexOf(stu_matching.get(student))){
                    hs_matching.get(hs).add(student);
                    queue.add(stu_matching.get(student));
                    hs_spots.set(stu_matching.get(student), hs_spots.get(stu_matching.get(student)) + 1);
                    stu_matching.set(student, hs);
                    hs_spots.set(hs, hs_spots.get(hs) - 1);
                }
            }

        }

        problem.setStudentMatching(stu_matching);
        return problem;
    }
}