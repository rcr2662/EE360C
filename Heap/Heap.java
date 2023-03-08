/*
 * Roberto Reyes
 * rcr2662
 */

// Implement your heap here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;
import java.util.Collections;

public class Heap {
    private ArrayList<GasStation> minHeap;
    int MinDist;

    public Heap() {
        minHeap = new ArrayList<GasStation>();
    }

    /**
     * buildHeap(ArrayList<GasStation> stations)
     * Given an ArrayList of GasStation, build a min-heap keyed on each GasStation's minDist
     * Time Complexity - O(nlog(n)) or O(n)
     *
     * @param stations
     */
    public void buildHeap(ArrayList<GasStation> stations) {
        minHeap.addAll(stations);
        for (int i = minHeap.size() - 1; i >= 0; i--){
            heapifyDown(i);
        }
        for(int j = 0; j < minHeap.size(); j++){
            minHeap.get(j).index = j; 
        }
    }

    /**
     * insertNode(GasStation in)
     * Insert a GasStation into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the GasStation to insert.
     */
    public void insertNode(GasStation in) {
        minHeap.add(in);
        heapifyUp(minHeap.size() - 1, in);
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public GasStation findMin() {
        return minHeap.get(0);
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public GasStation extractMin() {
        GasStation temp = minHeap.get(0);
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
        minHeap.set(index, minHeap.get(minHeap.size() - 1));
        if (tooBig(index)) heapifyDown(index);
        else heapifyUp(index, minHeap.get(index));
        
    }

    /**
     * changeKey(GasStation r, int newDist)
     * Changes minDist of GasStation s to newDist and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the GasStation in the heap that needs to be updated.
     * @param newDist - the new cost of GasStation r in the heap (note that the heap is keyed on the values of minDist)
     */
    public void changeKey(GasStation r, double newDist) {
        delete(r.index);
        r.distance = newDist;
        insertNode(r);
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getID() + " ";
        }
        return output;
    }

    /** Heapify up(index, GasStation in)
     * given the index of the GasStation and heapify it up
     * O(log(n))
     * **/
    public void heapifyUp(int index, GasStation in){
        int parent_index;
        if (index > 0){
            parent_index = (index - 1) / 2;
            if (in.distance < minHeap.get(parent_index).distance){
                Collections.swap(minHeap, index, parent_index);
                heapifyUp(parent_index, in);
            }
        }

    }

    /** Heapify down(index)
     * given the index of the GasStation and heapify it down
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
            if (minHeap.get(left).distance < minHeap.get(right).distance){
                min_index = left;
            }
        }else if(left == size){
            min_index = left;
        }

        if(minHeap.get(min_index).distance < minHeap.get(index).distance){
            Collections.swap(minHeap, min_index, index);
            heapifyDown(min_index);
        }
        
        
    }

    /**Checks if element at index is bigger than its children
    * @param index
    */
    private boolean tooBig(int index){
        if (minHeap.get(index).distance > minHeap.get((index * 2) + 1).distance
         || minHeap.get(index).distance > minHeap.get((index * 2) + 2).distance){
             return true;
         }
        return false;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<GasStation> toArrayList() {
        return minHeap;
    }
}
