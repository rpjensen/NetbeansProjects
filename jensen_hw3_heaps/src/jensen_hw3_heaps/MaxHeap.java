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
    
    public MaxHeap(int[] a){
        this.heap = Arrays.copyOfRange(a, 0, a.length);
        this.heapSize = this.heap.length;
        this.buildMaxHeap();
    }
    
    protected int parentOf(int i){
        //exclusive lower bound since if i = 0 then it is root and has no parent
        return (i > 0 && i < this.heapSize) ? (i-1)/2 : -1;
    }
    
    protected int leftChild(int i){
        return (i >= 0 && i < this.heapSize) ? (2*i + 1) : -1;
    }
    
    protected int rightChild(int i){
        return (i >= 0 && i < this.heapSize) ? (2*i + 2) : -1;
    }
    
    
}
