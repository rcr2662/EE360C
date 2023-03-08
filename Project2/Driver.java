// This Driver file will be replaced by ours during grading
// Do not include this file in your final submission

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    private static String filename; // input file name
    private static boolean testHeap; // set to true by -h flag
    private static boolean testReachable; // set to true by -r flag
    private static boolean testMinTSize; // set to true by -m flag
    private static Program2 testProgram2; // instance of your graph
    private static ArrayList<GasStation> stations;
    private static ArrayList<int[]> testR;
    private static ArrayList<int[]> testM;
    private static int testSize;

    private static void usage() { // error message
        System.err.println("usage: java Driver [-h] [-r] [-m] <filename>");
        System.err.println("\t-h\tTest Heap implementation");
        System.err.println("\t-r\tTest Reachable implementation");
        System.err.println("\t-m\tTest Tank Size implementation");
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        stations = new ArrayList<GasStation>();
        testR = new ArrayList<int []>();
        testM = new ArrayList<int[]>();
        parseArgs(args);
        parseInputFile(filename);
        testRun();
    }

    public static void parseArgs(String[] args) {
        boolean flagsPresent = false;
        if (args.length == 0) {
            usage();
        }

        filename = "";
        testHeap = false;
        testReachable = false;
        testMinTSize = false;
        for (String s : args) {
            if (s.equals("-h")) {
                flagsPresent = true;
                testHeap = true;
            } else if(s.equals("-r")){
                flagsPresent = true;
                testReachable = true;
            } else if (s.equals("-m")) {
                flagsPresent = true;
                testMinTSize = true;
            } else if (!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }

        if (!flagsPresent) {
            testHeap = true;
            testReachable = true;
            testMinTSize = true;
        }
    }

    public static void parseInputFile(String filename)
            throws Exception {
        int numV = 0;
        Scanner sc = new Scanner(new File(filename));
        String[] inputSize = sc.nextLine().split(" ");
        numV = Integer.parseInt(inputSize[0]);
        testProgram2 = new Program2();
        for (int i = 0; i < numV; ++i) {

            //getting infos of the map
            String[] info = sc.nextLine().split(" ");
            int x_cor = Integer.parseInt(info[0]);
            int y_cor = Integer.parseInt(info[1]);
            int parts_val = Integer.parseInt(info[2]);
            GasStation currGasStation = new GasStation(i,parts_val,x_cor,y_cor);
            stations.add(i, currGasStation);
        }

        inputSize = sc.nextLine().split(" ");
        testSize =  Integer.parseInt(inputSize[0]);

        //parsing for testReachable
        for(int i = 0; i < testSize; ++i){
            String[] info = sc.nextLine().split(" ");
            int startID = Integer.parseInt(info[0]);
            int init_TSize = Integer.parseInt(info[1]);
            int[] temp = new int[]{startID, init_TSize};
            testR.add(temp);
        }

        //parsing for testMinTSize
        for(int i = 0; i < testSize; ++i){
            String[] info = sc.nextLine().split(" ");
            int startID = Integer.parseInt(info[0]);
            int destID = Integer.parseInt(info[1]);
            int[] temp = new int[]{startID, destID};
            testM.add(temp);
        }

        testProgram2.setAllNodesArray(stations);
    }

    public static GasStation getStationByID(int id){
        for(GasStation s: stations){
            if(s.getID() == id){return s;}
        }
        return null;
    }
    // feel free to alter this method however you wish, we will replace it with our own version during grading
    public static void testRun() {
        System.out.println("\nGiven graph: ");
        System.out.println(testProgram2);

        if (testHeap) {
            Heap minHeap = new Heap();
            minHeap = testProgram2.getHeap();
            stations = testProgram2.getAllStations();
            for (GasStation g : stations){
                for (GasStation s : stations){
                    if (g.getID() != s.getID() & (testProgram2.distance(g, s) < g.distance)){
                        g.distance = testProgram2.distance(g, s);
                    }
                }
            }
            minHeap.buildHeap(testProgram2.getAllStations());
        }

        if (testReachable){
            for(int i = 0; i < testSize; ++i){
                int [] pair = testR.get(i);
                System.out.println("All reachable stations from station " + pair[0] + " are:");
                System.out.println(testProgram2.findAllReachableStations(getStationByID(pair[0]),pair[1]));
            }

            System.out.println("Test reachable over\n");
        }

        if (testMinTSize) {
            // test out Program2.java findMinTankSize here
            for(int i = 0; i < testSize; ++i){
                int [] pair = testM.get(i);
                System.out.println("Min tank size needed to go from station "+pair[0]+" to station "+pair[1]+" is ");
                System.out.println(testProgram2.findMinimumTankSize(getStationByID(pair[0]),getStationByID(pair[1])));
            }
        }

        System.out.println("Test min tank size over\n");
    }

}
