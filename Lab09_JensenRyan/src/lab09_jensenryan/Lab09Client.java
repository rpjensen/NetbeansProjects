package lab09_jensenryan;


// import java.util.*;
// could be used instead of specifying the specific classes
// that are being used; but you are making your code
// self-documenting by listing each class individually

import java.util.Deque;
import java.util.ArrayDeque;
import java.util.Iterator;
//import java.util.Collection;
//import java.util.Set;
//import java.util.SortedSet;
//import java.util.List;
//import java.util.Queue;
//import java.util.Map;
//import java.util.SortedMap;

/**
* The class <code>Lab09Client</code> provides practices with using
* some of the interfaces and classes of the Java Collection Framework.
*
* @author Mark A. Holliday, Charley Sheaffer
* @version 25 March 2014
*/
public class Lab09Client
{

    /**
    * The first method typically executed within a Lab09Client object.
    * This method calls helper methods to practice using some of the
    * interfaces (that is, abstract data types) in the Java Collection Framework.
    */
    private void useJCF() 
    {
        useDequeStringElement();
        useDequeClassificationElement();
    }

    /** A class to be an element of a collection */
    private class Classification
    {
        /** the genus of the organism */
        private String genus;

        /** the species of the organism */
        private String species;

        /** the constructor */
        public Classification(String genus, String species)
        {
            this.genus = genus;
            this.species = species;
        }

        public String toString()
        {
            return "(" + this.genus + ", " + this.species + ")";
        }
    }

    /** A client for the Deque collection interface that has the
    * element in the collection being of type String.
    */
    private void useDequeStringElement()
    {
        // declare a variable (called one) of type Deque<String>
        Deque<String> one = new ArrayDeque<String>();
        // create an object of class ArrayDeque and assign its reference
        // to the variable one
        one.addFirst("red");
        one.addFirst("blue");
        one.addFirst("white");
        // add "red", "blue", "white" to the front of the deque in that sequence
       System.out.println(one);
        // call the toString method of Deque and display the result on the console
       one.addLast("red");
       one.addLast("white");
       one.addLast("green");
        // add "red", "white" and "green" to the rear of the deque in that sequence
        System.out.println(one);
        // call the toString method of Deque and display the result on the console
        System.out.println(one.getFirst());
        // use getFirst to return the element at the front of the deque
        // and display the result on the console
        System.out.println(one.getLast());
        // use getLast to return the element at the rear of the deque
        // and display the result on the console
        one.removeFirstOccurrence("white");
        System.out.println(one);
        // use removeFirstOccurrence to remove a "white" element, 
        // then call toString and display the result to the console
        one.removeLastOccurrence("red");
        System.out.println(one);
        // use removeLastOccurrence to remove a "red" element, 
        // then call toString and display the result to the console
        one.removeFirst();
        System.out.println(one);
        // use removeFirst and then call toString and display the 
        // result to the console
        one.removeLast();
        System.out.println(one);
        // use removeLast and then call toString and display the 
        // result to the console
        one.push("orange");
        System.out.println(one);
        // use push of the string "orange" and use toString to 
        // display the deque on the console
        System.out.println(one.pop());
        // use pop and display on the console the value popped
        System.out.println(one.peek());
        // use peek and display on the console the value returned
        Iterator iter = one.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }
        // create an iterator variable and iterate over the deque and display
        // on the console each value 

    }


    /** A client for the Deque collection interface that has the
    * element in the collection being of type Classification.
    */
    private void useDequeClassificationElement()
    {
        // create a Deque variable that holds a reference to an
        // ArrayDeque of Classification elements
        Deque<Classification> one = new ArrayDeque<Classification>();
        // push a Classification element with genus "Homo" and species "sapien"
        one.push(new Classification("Homo", "sapien"));
        // push a Classification element with genus "Malus" and species "domestica"
        one.push(new Classification("Malus", "domestica"));
        // create an iterator variable and iterate over the deque and display
        // on the console each value 
        Iterator iter = one.iterator();
        while (iter.hasNext()){
            System.out.println(iter.next());
        }

    }

    /** 
     * Provides the entry point for this application.  This method creates
     * a <code>Lab09Client</code> and executes the
     * useJCF() method.
     *
     * @param args Unused.
    */
    public static void main(String[] args) 
    {
        Lab09Client client = new Lab09Client();
        client.useJCF();
    }
}

