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
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) {
        ArrayList<Integer> doctorMatchings = problem.getDoctorMatching();
        ArrayList<ArrayList<Integer>> cityPreferences = problem.getCityPreference();
        ArrayList<ArrayList<Integer>> doctorPreferences = problem.getDoctorPreference();
        // For each pair of matchings, check for the unstable conditions
        for (int i = 0; i < doctorMatchings.size(); i++) {
            for (int j = i + 1; j < doctorMatchings.size(); j++) {
                int firstMatching = doctorMatchings.get(i);
                int secondMatching = doctorMatchings.get(j);
                if (firstMatching == -1 && secondMatching != -1) {
                    ArrayList<Integer> preferences = cityPreferences.get(secondMatching);
                    // use indexOf method to find the ranking in the list of preferences
                    if (preferences.indexOf(i) < preferences.indexOf(j)) {
                        return false;
                    }
                } else if (secondMatching == -1 && firstMatching != -1) {
                    ArrayList<Integer> preferences = cityPreferences.get(firstMatching);
                    if (preferences.indexOf(j) < preferences.indexOf(i)) {
                        return false;
                    }
                } else if (firstMatching != -1 && secondMatching != -1) {
                    ArrayList<Integer> cityPreference = cityPreferences.get(firstMatching);
                    ArrayList<Integer> doctorPreference = doctorPreferences.get(j);
                    if (cityPreference.indexOf(j) < cityPreference.indexOf(i) &&
                        doctorPreference.indexOf(firstMatching) < doctorPreference.indexOf(secondMatching)) {
                        return false;
                    }
                }
            }
        }
        // all pairs are valid, this is a stable matching
        return true;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_cityoptimal(Matching problem) {
        // array of doctors, true means doctor is unpaired, false doctor is paired
        boolean[] freeDoctors = new boolean[problem.getDoctorCount()];
        Arrays.fill(freeDoctors, true);
        // list of offers that each city has made
        ArrayList<ArrayList<Integer>> offers = new ArrayList<>();
        for (int index = 0; index < problem.getCityCount(); index++) {
            offers.add(new ArrayList<>());
        }
        ArrayList<ArrayList<Integer>> doctorPreferences = problem.getDoctorPreference();
        ArrayList<ArrayList<Integer>> cityPreferences = problem.getCityPreference();
        ArrayList<Integer> positionsLeft = problem.getCityPositions();
        int city = 0;
        // array of matches made
        int[] doctorMatches = new int[problem.getDoctorCount()];
        Arrays.fill(doctorMatches, -1);
        // while an available city exists and it has not made offers to every doctor
        while ((city = getAvailableCity(positionsLeft, offers, problem.getDoctorCount())) != -1) {
            ArrayList<Integer> preferences = cityPreferences.get(city);
            // get highest ranked doctor that it has not made an offer to
            int doctor = getPairing(preferences, offers.get(city));
            ArrayList<Integer> doctorPreference = doctorPreferences.get(doctor);
            // if that doctor is free match it to this city
            if (freeDoctors[doctor]) {
                doctorMatches[doctor] = city;
                freeDoctors[doctor] = false;
                positionsLeft.set(city, positionsLeft.get(city) - 1);
            } else {
                // else check if the city that it is already mapped to has a worse ranking
                int matchedCity = doctorMatches[doctor];
                // if so, match to city instead
                if (doctorPreference.indexOf(matchedCity) > doctorPreference.indexOf(city)) {
                    doctorMatches[doctor] = city;
                    // update number of positions for both cities
                    positionsLeft.set(city, positionsLeft.get(city) - 1);
                    positionsLeft.set(matchedCity, positionsLeft.get(matchedCity) + 1);
                }
            }
            System.out.println(positionsLeft);
            // add to this cities offers list
            offers.get(city).add(doctor);
        }
        // make list out of matches array and store in problem
        return constructMatching(doctorMatches, problem);
    }

    // Make a list out of a matches array and store in Matching object
    private Matching constructMatching(int[] doctorMatches, Matching problem) {
        ArrayList<Integer> matches = new ArrayList<>();
        for (int index = 0; index < doctorMatches.length; index++) {
            matches.add(doctorMatches[index]);
        }
        problem.setDoctorMatching(matches);
        return problem;
    }

    // For doctor_optimal method, get list of all doctors assigned to a city
    private ArrayList<Integer> getMatches(int[] doctorMatches, int city) {
        ArrayList<Integer> matches = new ArrayList<>();
        for (int index = 0; index < doctorMatches.length; index++) {
            if (doctorMatches[index] == city) {
                matches.add(index);
            }
        }
        return matches;
    }

    // Find highest ranked object(city, or doctor) that has not yet been made an offer to
    private int getPairing(ArrayList<Integer> preferences, ArrayList<Integer> offers) {
        for (int index = 0; index < preferences.size(); index++) {
            int option = preferences.get(index);
            if (!offers.contains(option)) {
                return option;
            }
        }
        return -1;
    }

    // Find an available city with some positions left and has not made offer
    // to all doctors yet
    private int getAvailableCity(ArrayList<Integer> positionsLeft,
                    ArrayList<ArrayList<Integer>> offers, int numDoctors) {
        for (int index = 0; index < positionsLeft.size(); index++) {
            if (positionsLeft.get(index) > 0 && offers.get(index).size() < numDoctors) {
                return index;
            }
        }
        return -1;
    }

    // Find an available doctor that has not yet made offers to all cities
    private int getAvailableDoctor(boolean[] freeDoctors,
                    ArrayList<ArrayList<Integer>> offers, int numCities) {
        for (int index = 0; index < freeDoctors.length; index++) {
            if (freeDoctors[index] && offers.get(index).size() < numCities) {
                return index;
            }
        }
        return -1;
    }

    // Given a list of city preferences, find the worst ranked doctor that the city
    // has been assigned
    private int findWorstMatch(ArrayList<Integer> cityPreference, int doctor,
                    ArrayList<Integer> matchedDoctors) {
        int worstMatch = -1;
        int worstPreference = cityPreference.indexOf(doctor);
        for (int index = 0; index < matchedDoctors.size(); index++) {
            int matchedDoctor = matchedDoctors.get(index);
            int preference = cityPreference.indexOf(matchedDoctor);
            if (preference > worstPreference) {
                worstPreference = preference;
                worstMatch = matchedDoctor;
            }
        }
        return worstMatch;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_doctoroptimal(Matching problem) {
        // array of doctors, true means doctor is unpaired, false doctor is paired
        boolean[] freeDoctors = new boolean[problem.getDoctorCount()];
        Arrays.fill(freeDoctors, true);
        // list of offers made by each doctor
        ArrayList<ArrayList<Integer>> offers = new ArrayList<>();
        for (int index = 0; index < problem.getDoctorCount(); index++) {
            offers.add(new ArrayList<>());
        }
        ArrayList<ArrayList<Integer>> doctorPreferences = problem.getDoctorPreference();
        ArrayList<ArrayList<Integer>> cityPreferences = problem.getCityPreference();
        ArrayList<Integer> positionsLeft = problem.getCityPositions();
        int doctor = 0;
        // array of matches made
        int[] doctorMatches = new int[problem.getDoctorCount()];
        Arrays.fill(doctorMatches, -1);
        // while there is still an unpaired doctor that has not made offers to all cities, run
        // the matchin algorithm
        while ((doctor = getAvailableDoctor(freeDoctors, offers, problem.getCityCount())) != -1) {
            ArrayList<Integer> preferences = doctorPreferences.get(doctor);
            // get highest ranked city in the doctor's preferences
            int city = getPairing(preferences, offers.get(doctor));
            ArrayList<Integer> cityPreference = cityPreferences.get(city);
            // if the city still has spots left, pair the doctor with this city
            if (positionsLeft.get(city) > 0) {
                doctorMatches[doctor] = city;
                freeDoctors[doctor] = false;
                positionsLeft.set(city, positionsLeft.get(city) - 1);
            } else {
                // else find the worst ranked doctor and replace that doctor with this doctor
                ArrayList<Integer> matchedDoctors = getMatches(doctorMatches, city);
                int worstMatch = findWorstMatch(cityPreference, doctor, matchedDoctors);
                if (worstMatch != -1) {
                    doctorMatches[doctor] = city;
                    doctorMatches[worstMatch] = -1;
                    freeDoctors[doctor] = false;
                    freeDoctors[worstMatch] = true;
                }
            }
            // add this city to the doctors list of offers
            offers.get(doctor).add(city);
        }
        // construct the list of matches from the array and store in problem
        return constructMatching(doctorMatches, problem);
    }
}
