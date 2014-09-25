/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jensen_hw3_heaps;

import java.util.Arrays;


/**
 * Max heap implementation for HW 3
 * All this class really does is allow you to turn an array into a Max heap,
 * sort using Heap Sort, and print the values in the heap at any time
 * @author Ryan Jensen
 */
public class MaxHeap {
    protected int[] heap;
    protected int heapSize;
    
    /**
     * Initialize a MaxHeap object by copying the values of an existing array
     * @param a the array of values that will be copied into the heap
     */
    public MaxHeap(int[] a){
        this.heap = Arrays.copyOfRange(a, 0, a.length);
        this.heapSize = this.heap.length;
        this.buildMaxHeap();
    }
    
    /**
     * Calculates the index of the ith node
     * @param i the node who's parent index will be returned
     * @return the index of the parent
     */
    protected int parentOf(int i){
        //exclusive lower bound since if i = 0 then it is root and has no parent
        return (i > 0 && i < this.heapSize) ? (i-1)/2 : -1;
    }
    
    /**
     * Calculate the left child's index of the ith node
     * @param i the node who's left(first) child index will be returned
     * @return the index of the left child
     */
    protected int leftChild(int i){
        return (i >= 0 && i < this.heapSize) ? (2*i + 1) : -1;
    }

    /**
     * Calculate the right child's index of the ith node
     * @param i the node who's right(second) child index will be returned
     * @return the index of the right child
     */
    protected int rightChild(int i){
        return (i >= 0 && i < this.heapSize) ? (2*i + 2) : -1;
    }
    
    /**
     * Creates a printable representation of a max heap including the values and the heap size
     */
    public void printMaxHeap(){
        System.out.printf("Heap: %s \nHeap Size: %d\n", this.toString(), this.heapSize);
    }
    
    /**
     * toString method so that there was no need to copy the subrange that was considered in the heap
     * @return a string representation of the values currently in the heap
     */
    @Override
    public String toString(){
        StringBuilder returnString = new StringBuilder("[");
        boolean first = true;
        for (int i = 0; i < this.heapSize; i++){
            if (first){
                returnString.append(this.heap[i]);
                first = false;
            }
            else {
                returnString.append(", ").append(this.heap[i]);
            }
        }
        return returnString.append("]").toString();
    }
    
    /**
     * A method to fix a heap property violation at a single point
     * Pre-Condition - i is a legitimate node of the heap
     * Pre-Condition - All nodes below node i are legitimate heaps
     * Pre-Condition - heapSize property accurately counts how many nodes are considered to be in the heap
     * Post-Condition - All nodes at i and below will be a legitimate heap
     * @param i the index of the node that is causing the violation
     */
    protected void maxHeapify(int i){
        int largestChild = this.heap[i];
        int largestChildIndex = i;
        
        if (leftChild(i) < this.heapSize && this.heap[leftChild(i)] > largestChild){
            largestChild = this.heap[this.leftChild(i)];
            largestChildIndex = this.leftChild(i);
        }
        if (rightChild(i) < this.heapSize && this.heap[rightChild(i)] > largestChild){
            largestChild = this.heap[rightChild(i)];
            largestChildIndex = rightChild(i);
        }
        if (largestChildIndex != i){
            int temp = this.heap[i];
            this.heap[i] = largestChild;
            this.heap[largestChildIndex] = temp;                    
            this.maxHeapify(largestChildIndex);
        }
    }
    
    /**
     * This method builds a max heap from the bottom up with values in the heap variable
     * It is declared final since it won't be overridden for the priority queue then we
     * won't have issues with its being called in the constructor.
     */
    protected final void buildMaxHeap(){
        for (int i = this.heapSize/2 - 1; i >= 0; i--){
            this.maxHeapify(i);
        }
    }
    
    /**
     * This method returns an ascending sorted array from indices [0,heapSize-1]
     * Pre-Condition - the heap variable contains a legitimate heap in the indices [o,heapSize-1]
     * Post-Condition - the indices [0,heapSize-1] are in ascending sorted order
     */
    public void heapSort(){
        int tempHeapSize = this.heapSize;
        for (int i = this.heapSize - 1; i >= 1; i--){
            int temp = this.heap[0];
            this.heap[0] = this.heap[i];
            this.heap[i] = temp;
            this.heapSize--;
            this.maxHeapify(0);
        }
        this.heapSize = tempHeapSize;
    }
    
}
