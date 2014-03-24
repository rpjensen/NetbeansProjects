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
                builder.append(", ").append(
        			current.data);
            }
            current = current.next;
        }
        builder.append(">");
        return builder.toString();
	}



}

