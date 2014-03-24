package List_Lab_08;

import java.util.NoSuchElementException;

public class LinkedLab08<T> implements ListLab08<T> {

        private class Node {

                private T data;
                private Node next;

                public Node(T data, Node next)
                {
                    this.data = data;
                    this.next = next;
                }

                public Node(T data)
                {
                    this(data, null);
                }

                public Node()
                {
                    this(null, null);
                }
        }

        private Node head;
        private Node tail;
        private int size;

        public LinkedLab08()
        {
            head = new Node();
            tail = head;
            size = 0;
        }

        public int size()
        {
            return size;
        }

        public void clear()
        {
            head.next = null;
            tail = head;
            size = 0;
        }

        public void add(T element)
        {
            tail.next = new Node(element);
            tail = tail.next;
            size++;
        }

        public T get(int index)
        {
            if (index >= size || index < 0)
                throw new IndexOutOfBoundsException("index: " + index);

            Node node = head.next;
            for (int i=0; i<index; i++)
                node = node.next;

//		return (T) node.data;
            return node.data;
        }

        public void set(int index, T element)
        {
                if (index >= size || index < 0)
                    throw new IndexOutOfBoundsException("index: " + index);

                Node node = head.next;
                for (int i=0; i<index; i++)
                        node = node.next;
                node.data = element;
        }

        public boolean contains(T element)
        {
            boolean found = false;
            Node node = head.next;
            while (node != null && !found) {
                if (element.equals(node.data))
                    found = true;
                node = node.next;
            }
            return found;
        }

/*	
* TO DO:
*
*	Your methods go here.
*/

        public void insert(T data, int index){
            //since we only insert before existing nodes tail doesn't have to update
            if (index >= size || index < 0){
                throw new IndexOutOfBoundsException("index: " + index);
            }
            if (data == null){
                throw new IllegalArgumentException("New Data is Null");
            }
            Node previous = head;
            for (int i = 0; i < index; i++){
                previous = previous.next;
            }
            //now we have the one previous node
            previous.next = new Node(data,previous.next);
            size++;
        }
        
        public void addFirst(T data){
           if (data == null){
                throw new IllegalArgumentException("New Data is Null");
            }
           if (head.next == null){
               //if head.next is null then the list is empty and we need to update tail
               //and our new node.next is null instead of head.next.next thus
               //avoiding a null pointer exception
               head.next = new Node(data, null);
               tail = head.next;
           }else{
                //the dumby points to the new node, the new node points
                // to the node dumby originally pointed to 
               head.next = new Node(data, head.next);
           }
           size++;
        }
        
        public void moveFirstToEnd(){
            if (size <= 0){
                throw new NoSuchElementException("Empty List");
            }
            //last equals first
            tail.next = head.next;
            tail = tail.next;
            head.next = head.next.next;
            tail.next = null;
            //it is a little strange but this method still works even if there
            //is only one item in the list.
        }
        
        public void remove(int index){
            if (index >= size || index < 0){
                throw new IndexOutOfBoundsException("index: " + index);
            }
            Node previous = head;
            for (int i = 0; i < index; i++){
                previous = previous.next;
            }
            previous.next = previous.next.next;
            if (previous.next == null){
                tail = previous;
            }
            size--;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("<");
            Node current = head.next;
            if (head.next != null) {
                builder.append(current.data);
                current = current.next;
                while (current != null) {
                    builder.append(", ").append(current.data);
                    current = current.next;
                }
            }
            builder.append(">");
            return builder.toString();
        }

        public String toStringSize() {
            StringBuilder builder = new StringBuilder();
            builder.append("<");
            Node current = head.next;
            for (int i = 0; i < this.size; ++i) {
                if (i == 0) {
                    builder.append(current.data);
                } else {
                    builder.append(", ").append(current.data);
                }
                current = current.next;
            }
            builder.append(">");
            return builder.toString();
        }



}

