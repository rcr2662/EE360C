/*
 * Roberto Reyes
 * rcr2662
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Program2 {
    private ArrayList<Student> students;    // this is a list of all Students, populated by Driver class
    private Heap minHeap;

    // additional constructors may be added, but don't delete or modify anything already here
    public Program2(int numStudents) {
        minHeap = new Heap();
        students = new ArrayList<Student>();
    }

    /**
     * findMinimumStudentCost(Student start, Student dest)
     *
     * @param start - the starting Student.
     * @param dest  - the end (destination) Student.
     * @return the minimum cost possible to get from start to dest.
     * Assume the given graph is always connected.
     */
    public int findMinimumStudentCost(Student start, Student dest) {
        //Implemented Dijkstra with min heap.
        ArrayList<Student> reached = new ArrayList<Student>();
        ArrayList<Student> unreached = new ArrayList<Student>();
        Heap heap = new Heap();
        unreached.addAll(students);

        for(Student student : unreached){
            student.setminCost(Integer.MAX_VALUE);
        }

        unreached.get(unreached.indexOf(start)).setminCost(0);
        heap.buildHeap(unreached);

        while(reached.size() < students.size()){
            Student student = heap.extractMin();
            reached.add(student);
            for (int i = 0; i < student.getNeighbors().size(); i++){
                Student neighbor = student.getNeighbors().get(i);
                int cost = student.getPrices().get(i);
                if (heap.toArrayList().contains(neighbor)){
                    if (student.getminCost() + cost < neighbor.getminCost()){
                        heap.changeKey(neighbor, student.getminCost() + cost);
                    }
                }
            }
        }


        return reached.get(reached.indexOf(dest)).getminCost();
    }

    /**
     * findMinimumClassCost()
     *
     * @return the minimum total cost required to connect (span) each student in the class.
     * Assume the given graph is always connected.
     */
    public int findMinimumClassCost() {
        //Implemented Prim with min heap.
        ArrayList<Student> reached = new ArrayList<Student>();
        ArrayList<Student> unreached = new ArrayList<Student>();
        int total = 0;
        Heap heap = new Heap();
        unreached.addAll(students);

        for(Student student : unreached){
            student.setminCost(Integer.MAX_VALUE);
        }

        unreached.get(0).setminCost(0);
        heap.buildHeap(unreached);

        while(reached.size() < students.size()){
            Student student = heap.extractMin();
            reached.add(student);
            for (int i = 0; i < student.getNeighbors().size(); i++){
                Student neighbor = student.getNeighbors().get(i);
                int cost = student.getPrices().get(i);
                if (heap.toArrayList().contains(neighbor)){
                    if (cost < neighbor.getminCost()){
                        heap.changeKey(neighbor, cost);
                    }
                }
            }
        }

        for (Student curr : reached){
            total += curr.getminCost();
        }
        return total;
    }

    //returns edges and prices in a string.
    public String toString() {
        String o = "";
        for (Student v : students) {
            boolean first = true;
            o += "Student ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<Student> ngbr = v.getNeighbors();
            for (Student n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with prices ";
            ArrayList<Integer> wght = v.getPrices();
            for (Integer i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public Heap getHeap() {
        return minHeap;
    }

    public ArrayList<Student> getAllstudents() {
        return students;
    }

    // used by Driver class to populate each Student with correct neighbors and corresponding prices
    public void setEdge(Student curr, Student neighbor, Integer price) {
        curr.setNeighborAndPrice(neighbor, price);
    }

    // used by Driver.java and sets students to reference an ArrayList of all Students
    public void setAllNodesArray(ArrayList<Student> x) {
        students = x;
    }
}
