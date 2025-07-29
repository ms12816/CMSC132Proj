package listClasses;

import java.util.*;


public class BasicLinkedList<T> implements Iterable<T> {

	/* Node definition */
	protected class Node {
		protected T data;
		protected Node next;

		protected Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/* We have both head and tail */
	protected Node head, tail;

	/* size */
	protected int listSize;

	//Returns the instance variable that keeps track of the list's size
	public int getSize() {
		return listSize;
	}

	public BasicLinkedList<T> addToEnd(T data){
		//Create new node using parameter data type
		Node newNode = new Node(data);
		//If head is null than this is the first addition to an empty list,
		//head and tail must point to same node (the new added one).
		if(head == null) {
			head = newNode;
			tail = newNode;
		} else {
			tail.next = newNode;
			tail = newNode;
		}
		//Add to size of list
		listSize++;
		//Return reference to current object
		return this;
	}

	public BasicLinkedList<T> addToFront(T data) {
		//Create new node using data parameter
	    Node newNode = new Node(data);
	    //Make the newNode both head and tail if the list is empty
	    if (head == null) {
	        head = newNode;
	        tail = newNode;
	    } else {
	    	//If list is not empty, simply link the new node to the front of the list 
	    	//and set the head pointer to the new node
	        newNode.next = head;
	        head = newNode;
	    }
	    //Record that list size is increased and return reference to current variable
	    listSize++;
	    return this;
	}

	//Return data from first node (if list is not empty)
	public T getFirst() {
		if(head == null) {
			return null;
		}
		return head.data;
	}
	//Return data from last node (if list is not empty)
	public T getLast() {
		if(tail == null) {
			return null;
		}
		return tail.data;
	}

	public T retrieveFirstElement() {
		//If list is empty return null
		if(head == null) return null;
		//Assign first node data to a temporary variable
		T holdData = head.data;
		//Set the starting node to the originally next-up starting node
		head = head.next;
		//Now check if head is null, list is empty, and tail should point to null as well
		if(head == null) tail = null;
		
		
		listSize--;
				
		return holdData;
	}

	public T retrieveLastElement() {
		//Return null if list is empty
		if(head == null) return null;
		//If there is one item in the list remove it and make sure the list is empty
		if(head == tail) {
			T holdData = head.data;
			head = null;
			tail = null;
			listSize--;
			return holdData;
		}
		//Move through the linked list stopping one node before the tail node 
		Node currNode = head;
		while(currNode.next != tail) {
			currNode = currNode.next;
		}
		//Use the determined second to last node to remove the last node 
		T holdData = tail.data;
		tail = currNode;
		//Make sure tail node points to null to avoid unwanted linkages
		tail.next = null;
		
		listSize--;
		return holdData;
	}

	public BasicLinkedList<T> remove(T targetData, java.util.Comparator<T> comparable){
		//Iterate through first part of linked list until target is reached
		while(head != null && comparable.compare(targetData, head.data) == 0) {
			head = head.next;
			listSize--;
		}
		//If list is now empty point tail to null
		if(head == null) {
			tail = null;
			return this;
		}
		//Keep going through the list to remove all other targetData objects save the current target reached
		Node currNode = head;
		//Move through list until the next node is null (end is reached)
		while(currNode.next != null) {
			//If next node is the target point the current node to the one after the next one, 
			//thereby removing it from the linked list
			if(comparable.compare(currNode.next.data, targetData) == 0) {
				currNode.next = currNode.next.next;
				listSize--;
			} else {
				//If the next node is not the target, simply keep moving through the nodes
				currNode = currNode.next;
			}
		}
		if (currNode.next == null) {
		    tail = currNode;
		}
		
		//Return reference to current object
		return this;
	}


	@Override
	public Iterator<T> iterator() {
		//Anonymous class
		return new Iterator<T>() {
			//Current node to keep track
			private Node currNode = head;
			//Returns true if there is a node that has not been checked yet
			@Override
			public boolean hasNext() {
				return currNode != null;
			}

			@Override
			public T next() {
				//Checks if there is a node available to move to
				if(hasNext()) {
					//If true move to next node, return data of previous node
					T holdData = currNode.data;
					currNode = currNode.next;
					return holdData;
				}
				return null;
			}
			//Not implemented
			@Override
			public void remove() {
				throw new IllegalArgumentException("");
			}


		};

	}
	
	public ArrayList<T> getReverseArrayList(){
		ArrayList<T> arr = new ArrayList<T>();
		reverseArrayListRecursion(head, arr);
		
		return arr;	
	}
	
	//Private helper method for recursion in ArrayList form
	private void reverseArrayListRecursion(Node n, ArrayList<T> arr) {
		//Base case if there current node is null return to go back through the recursion
		if(n == null) return;
		//First recursively look through the entire list, before going back through the call stack 
		//and adding all the nodes in reverse order
		reverseArrayListRecursion(n.next, arr);
		arr.add(n.data);
	}
	
	public BasicLinkedList<T> getReverseList(){
		//Create new linked list type which will be populated recursively through helper method
		BasicLinkedList<T> revList = new BasicLinkedList<T>();
		reverseListRecursion(revList, head);
		return revList;
	}
	
	//Private helper method for recursively reversing the linked list
	private void reverseListRecursion(BasicLinkedList<T> newList, Node currNode) {
		//Base case if the node is null than go back and link the new list together
		if(currNode == null) return;
		reverseListRecursion(newList, currNode.next);
		//Use addToEnd method to link together nodes in reverse order (occurs after the recursion).
		newList.addToEnd(currNode.data);		
	}
}