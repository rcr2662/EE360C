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
public class Program3 extends AbstractProgram3 {
    private ArrayList<ArrayList<ArrayList<Integer>>> r = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<ArrayList<Integer>>>> c = new ArrayList<>();
    /**
     * Determines the solution of the optimal response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "responseTime" field set to the optimal response time
     */
    @Override
    public TownPlan findOptimalResponseTime(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> housePositions = town.getHousePositions();
        
        if(k >= n) town.setResponseTime(0);
        else if (k == 1){
            r.add(new ArrayList<>());
            for (int i = 0; i < n; i++){
                r.get(0).add(new ArrayList<>());
                for (int j = 0; j < n; j++){
                    if (i == j){
                        r.get(0).get(i).add(0);
                    }
                    else r.get(0).get(i).add(-1);
                }
            }
            
            for (int i = 0; i < n - 1; i++){
                for (int j = i + 1; j < n; j++){
                    r.get(0).get(i).set(j, (housePositions.get(j) - housePositions.get(i))/2);
                }
                
            }
        }
        else {
            TownPlan newTown = new TownPlan(n, k - 1, housePositions);
            findOptimalResponseTime(newTown);
            r.add(new ArrayList<>());
                for (int i = 0; i < n; i++){
                    r.get(k-1).add(new ArrayList<>());
                    for (int j = 0; j < n; j++){
                        if (i == j){
                            for(int s = 0; s < k; s++){
                                r.get(k-1).get(i).add(0);
                            }
                            j += k-1;
                        }
                        else r.get(k-1).get(i).add(-1);
                        
                    }
                    
                }
            int min_max = Integer.MAX_VALUE;
            int max;
            for (int i = 0; i < n - k; i++){
                for (int j = i + k; j < n; j++){
                    min_max = Integer.MAX_VALUE;
                    int q = i+1;
                    for(int p = i + k - 2; p < j; p++){
                        max = Math.max(r.get(k-2).get(i).get(p), r.get(k-2).get(q).get(j));
                        if(max < min_max) min_max = max;
                        q++;
                    }
                    r.get(k-1).get(i).set(j, min_max);
                }
            }
            for (int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                }
            }
            town.setResponseTime(r.get(k-1).get(0).get(n-1));
        }
        return town;
    }

    /**
     * Determines the solution of the set of police station positions that optimize response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "policeStationPositions" field set to the optimal police station positions
     */
    @Override
    public TownPlan findOptimalPoliceStationPositions(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> housePositions = town.getHousePositions();
        ArrayList<Integer> policeStationPositions = new ArrayList<>();
        
        if(k >= n){
            for(int i = 0; i < n; i++){
                policeStationPositions.add(0);
            }
            town.setPoliceStationPositions(policeStationPositions);
        }
        else if (k == 1){
            r.add(new ArrayList<>());
            c.add(new ArrayList<>());
            for (int i = 0; i < n; i++){
                r.get(0).add(new ArrayList<>());
                c.get(0).add(new ArrayList<>());
                for (int j = 0; j < n; j++){
                    c.get(0).get(i).add(new ArrayList<>());
                    if (i == j){
                        r.get(0).get(i).add(0);
                        c.get(0).get(i).get(j).add(0);
                    }
                    else {
                        r.get(0).get(i).add(-1);
                        c.get(0).get(i).get(j).add(-1);
                    }
                }
            }
            
            for (int i = 0; i < n - 1; i++){
                for (int j = i + 1; j < n; j++){
                    r.get(0).get(i).set(j, (housePositions.get(j) - housePositions.get(i))/2);
                    c.get(0).get(i).get(j).set(0, housePositions.get(i)+ (housePositions.get(j) - housePositions.get(i))/2);
                }
                
            }
        }
        else {
            TownPlan newTown = new TownPlan(n, k - 1, housePositions);
            findOptimalResponseTime(newTown);
            r.add(new ArrayList<>());
            c.add(new ArrayList<>());
                for (int i = 0; i < n; i++){
                    r.get(k-1).add(new ArrayList<>());
                    c.get(k-1).add(new ArrayList<>());
                    for (int j = 0; j < n; j++){
                        if (i == j){
                            for(int s = 0; s < k; s++){
                                r.get(k-1).get(i).add(0);
                                for (int v = 0; v < k; v++){
                                    c.get(k-1).get(i).get(j).add(0);
                                }
                            }
                            j += k-1;
                        }
                        else {
                            r.get(k-1).get(i).add(-1);
                            for (int v = 0; v < k; v++){
                                c.get(k-1).get(i).get(j).add(-1);
                            }
                        }
                        
                    }
                    
                }
            int min_max = Integer.MAX_VALUE;
            int max;
            for (int i = 0; i < n - k; i++){
                for (int j = i + k; j < n; j++){
                    min_max = Integer.MAX_VALUE;
                    int q = i+1;
                    for(int p = i + k - 2; p < j; p++){
                        max = Math.max(r.get(k-2).get(i).get(p), r.get(k-2).get(q).get(j));
                        if(max < min_max) min_max = max;
                        q++;
                    }
                    r.get(k-1).get(i).set(j, min_max);
                }
            }
            for (int i = 0; i < n; i++){
                for (int j = 0; j < n; j++){
                }
            }
            town.setResponseTime(r.get(k-1).get(0).get(n-1));
        }
        return town;
    }
}
