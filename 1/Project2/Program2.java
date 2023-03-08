/*
 * Name: Roberto Reyes
 * EID: rcr2662
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Program2 {
    private ArrayList<GasStation> stations;  // this is a list of all Cities, populated by Driver class
    private Heap minHeap;

    // additional constructor fields may be added, but don't delete or modify anything already here
    public Program2() {
        minHeap = new Heap();
        stations = new ArrayList<GasStation>();
    }

    /**
     * findAllReachableStations(GasStation start, int init_size)
     *
     * @param start     - the starting GasStation.
     * @param init_size - the initial tank size obtained
     * @return the list of all gas stations we can reach from start
     */
    public ArrayList<GasStation> findAllReachableStations(GasStation start,int init_size) {
        ArrayList<GasStation> reachable = new ArrayList<GasStation>(); 
        ArrayList<GasStation> unreachable = new ArrayList<GasStation>();
        unreachable.addAll(stations);
        int tank = init_size + start.getUpgradeValue(); //starting tank after upgrade
        int dist;   //distance between stations
        int size;   //size of reachable

        //unusable tank
        if (init_size < 0)return reachable;
        
        //add start to reachable
        reachable.add(start);
        unreachable.remove(start);

        //Keep checking what stations are reachable from already reachable
        //stations until no new ones are added
        do {
            size = reachable.size();
            for (int j = 0; j < reachable.size(); j++){
                for (int i = 0; i < unreachable.size(); i++){
                    dist = distance(reachable.get(j), unreachable.get(i));
                    if ((dist <= tank)){
                        reachable.add(unreachable.get(i));
                        tank += unreachable.get(i).getUpgradeValue();
                        unreachable.remove(unreachable.get(i));
                    }
                }
            }
        }while(size != reachable.size());
        Collections.sort(reachable, new IdComparator());
        return reachable;
        
    }

    /**
     * findMinimumTankSize()
     * @param start  - the starting GasStation
     * @param dest   - the destination Gas Station
     * @return The minimum gas tank size needed at the beginning of the trip
     */
    public int findMinimumTankSize(GasStation start, GasStation dest) {
        ArrayList<GasStation> reachable = new ArrayList<GasStation>();  //stations reachable from start
        ArrayList<GasStation> unreached = new ArrayList<GasStation>();
        GasStation closest;
        int min = 0;  //initial min tank is distance from start to dest;
        int tank = start.getUpgradeValue();
        int diff;

        reachable.add(start);
        unreached.addAll(stations);
        unreached.remove(start);
        
        //if start upgrade is larger than distance
        if (distance(start, dest) - start.getUpgradeValue()<= 0) return 0;

        //check what is the closest station to start whose upgrade
        //allows dest to be reachable from start
        while(!reachable.contains(dest)){
            closest = closest(reachable, unreached);
            reachable.add(closest);
            unreached.remove(closest);
            if (tank < distance(closest, closest.neighbor)){
            diff = distance(closest, closest.neighbor) - tank;
            min = min + diff;
            tank = tank + diff;
            }
            tank += closest.getUpgradeValue();
        }
         
        return min;
    }

    //return the gas station id and its upgrade value
    //this function can be altered for your debugging purpose
    public String toString() {
        String o = "";
        for (GasStation v : stations) {
            boolean first = true;
            o += "Gas Station ";
            o += v.getID();
            o += " has upgrade value ";
            o += v.getUpgradeValue();
            o += System.getProperty("line.separator");

        }

        return o;
    }

    public int distance(GasStation g1, GasStation g2){
        double x = g1.getXcoordinate() - g2.getXcoordinate();
        double y = g1.getYcoordinate() - g2.getYcoordinate();
        int dist = (int)Math.ceil(Math.sqrt(x*x + y*y));
        return dist;
    }

    class IdComparator implements Comparator<GasStation> {
 
        public int compare(GasStation g1, GasStation g2)
        {
            if (g1.getID() > g2.getID()) return 1;
            else return -1;
        }
    }

    public GasStation closest (ArrayList<GasStation> reachable, ArrayList<GasStation> unreached){
        GasStation closest = unreached.get(0);
        double smallest = distance(reachable.get(0), unreached.get(0));
        closest.neighbor = reachable.get(0);
        for (GasStation g : reachable){
            for (GasStation s : unreached){
                if (distance(g, s) < smallest){
                    closest = s;
                    smallest = distance(g, s);
                    closest.neighbor = g;
                }
            }
        }
        return closest;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<GasStation> getAllStations() {
        return stations;
    }


    // used by Driver.java and sets cities to reference an ArrayList of all RestStops
    public void setAllNodesArray(ArrayList<GasStation> x) {
        stations = x;
    }
}
