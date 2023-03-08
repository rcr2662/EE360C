/*
 * Name: Roberto Reyes
 * EID: rcr2662
 */

// Implement your heap here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;
import java.util.Collections;

public class Heap {
    private ArrayList<Student> minHeap;

    public Heap() {
        minHeap = new ArrayList<Student>();
    }

    /**
     * buildHeap(ArrayList<Student> students)
     * Given an ArrayList of Students, build a min-heap keyed on each Student's minCost
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param students
     */
    public void buildHeap(ArrayList<Student> students) {
        minHeap.addAll(students);
        for (int i = minHeap.size() - 1; i >= 0; i--){
            heapifyDown(i);
        }
    }

    /**
     * insertNode(Student in)
     * Insert a Student into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the Student to insert.
     */
    public void insertNode(Student in) {
        minHeap.add(in);
        heapifyUp(minHeap.size() - 1, in);
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public Student findMin() {
        return minHeap.get(0);    
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public Student extractMin() {
        Student temp = minHeap.get(0);
        delete(0);
        return temp;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        Student temp = minHeap.get(minHeap.size() - 1);
        minHeap.remove(temp);
        if (index < minHeap.size()){
            minHeap.set(index, temp);
            if (tooBig(index)) heapifyDown(index);
            else heapifyUp(index, minHeap.get(index));
        }  
    }

    /**
     * changeKey(Student r, int newCost)
     * Changes minCost of Student s to newCost and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the Student in the heap that needs to be updated.
     * @param newCost - the new cost of Student r in the heap (note that the heap is keyed on the values of minCost)
     */
    public void changeKey(Student r, int newCost) {
        delete(minHeap.indexOf(r));
        r.setminCost(newCost);;
        insertNode(r);
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + " ";
        }
        return output;
    }

    /** Heapify up(index, Student in)
     * O(log(n))
     * **/
    public void heapifyUp(int index, Student in){
        int parent_index;
        if (index > 0){
            parent_index = (index - 1) / 2;
            if (in.getminCost() < minHeap.get(parent_index).getminCost()){
                Collections.swap(minHeap, index, parent_index);
                heapifyUp(parent_index, in);
            }
        }

    }

    /** Heapify down(index)
     * O(log(n))
     * **/
    public void heapifyDown(int index){
        int size = minHeap.size() - 1;
        int left = (2 * index) + 1;
        int right = left + 1;
        int min_index = right;

        if(left > size) {
            return;
        }else if(left < size){
            if ((minHeap.get(left).getminCost() < minHeap.get(right).getminCost()) 
                || ((minHeap.get(left).getminCost() == minHeap.get(right).getminCost()) 
                & (minHeap.get(left).getName() < minHeap.get(right).getName()))){
                min_index = left;
            }
        }else if(left == size){
            min_index = left;
        }

        if((minHeap.get(min_index).getminCost() < minHeap.get(index).getminCost()) 
        || ((minHeap.get(min_index).getminCost() == minHeap.get(index).getminCost()) & (minHeap.get(min_index).getName() < minHeap.get(index).getName()))){
            Collections.swap(minHeap, min_index, index);
            heapifyDown(min_index);
        }
    }

    /**Checks if element at index is bigger than its children
    * @param index
    */
    private boolean tooBig(int index){
        if(index * 2 + 2 < minHeap.size()){
            if (minHeap.get(index).getminCost() > minHeap.get((index * 2) + 1).getminCost()
                || minHeap.get(index).getminCost() > minHeap.get((index * 2) + 2).getminCost()
                || ((minHeap.get(index).getminCost() == minHeap.get((index * 2) + 1).getminCost()) 
                & (minHeap.get(index).getName() > minHeap.get((index * 2) + 1).getName()))
                || ((minHeap.get(index).getminCost() == minHeap.get((index * 2) + 2).getminCost()) 
                & (minHeap.get(index).getName() > minHeap.get((index * 2) + 2).getName()))){
                return true;
            }
        } else if(index * 2 + 1 < minHeap.size()){
            if (minHeap.get(index).getminCost() > minHeap.get((index * 2) + 1).getminCost() 
                || ((minHeap.get(index).getminCost() == minHeap.get((index * 2) + 1).getminCost()) 
                & (minHeap.get(index).getName() > minHeap.get((index * 2) + 1).getName()))){
                    return true;
                }
        }
        
        return false;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<Student> toArrayList() {
        return minHeap;
    }
}
