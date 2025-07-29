package listClasses;

import java.util.*;


/**
 * Implements a generic sorted list using a provided Comparator. It extends
 * BasicLinkedList class.
 * 
 *  @author Dept of Computer Science, UMCP
 *  
 */

public class SortedLinkedList<T> extends BasicLinkedList<T> {
	private Comparator<T> comparator;

	public SortedLinkedList(Comparator<T> comparator) {
		//Call to BasicLinkedList Constructor 
		super();
		this.comparator = comparator;
	}

	public SortedLinkedList<T> add(T element){
		//Create new node using data passed in
		Node newNode = new Node(element);
		//Check if list is empty or added element should be new head element
		if(head == null || comparator.compare(element, head.data) <= 0) {			
			newNode.next = head;
			head = newNode;
			listSize++;
			return this;
		} 
		//Start at head of linked list
		Node currNode = super.head;
		//Move through list while the new Node is "less" than the currentNode.
		while(currNode.next != null && comparator.compare(currNode.next.data, element) < 0) {
			currNode = currNode.next;			
		}
		//Since this will sort each node into the list correctly, once the currNode is greater than or equal to 
		//The new node (using the comparator) that is where the new node should be inserted.
		newNode.next = currNode.next;
		currNode.next = newNode;
		
		listSize++;
		//Return reference to current object.
		
		return this;
	}
	
	public SortedLinkedList<T> remove(T targetData){
		//Return a call to the superclass remove method after casting it to a sorted linked list 
		return (SortedLinkedList<T>) super.remove(targetData, comparator);
	}
	//Invalid operation for sorted linked list
	public SortedLinkedList<T> addToEnd(T data){
		throw new UnsupportedOperationException("Invalid operation for sorted list");
	}
	//INvalid operation for sorted linked list
	public SortedLinkedList<T> addToFront(T data){
		throw new UnsupportedOperationException("Invalid operation for sorted list");
	}

}