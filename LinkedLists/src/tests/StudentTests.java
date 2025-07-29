package tests;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.Comparator;

import org.junit.Test;
import listClasses.BasicLinkedList;
import listClasses.SortedLinkedList;
/**
 * 
 * You need student tests if you are looking for help during office hours about
 * bugs in your code.
 * 
 * @author UMCP CS Department
 *
 */
public class StudentTests {

	//BasicLinkedList tests
	@Test
	public void testAddToMethodsBasic() {
		BasicLinkedList<String> ll = new BasicLinkedList<String>();
		ll.addToFront("Middle");
		ll.addToEnd("Last");
		ll.addToFront("First");

		assertEquals(ll.getFirst(), "First");
		assertEquals(ll.getLast(), "Last");
		assertEquals(ll.getSize(), 3);
	}
	@Test
	public void testRetreivalsBasic() {
		BasicLinkedList<String> ll = new BasicLinkedList<String>();
		ll.addToFront("Robert");
		ll.addToFront("Tyler");
		ll.addToFront("Rishi");
		ll.addToFront("Thomas");

		assertEquals(ll.getSize(), 4);
		assertEquals(ll.getFirst(), "Thomas");

		assertEquals(ll.retrieveFirstElement(), "Thomas");
		assertEquals(ll.getSize(), 3);

		assertEquals(ll.retrieveLastElement(), "Robert");
		assertEquals(ll.getSize(), 2);

		assertEquals(ll.retrieveFirstElement(), "Rishi");
		assertEquals(ll.getSize(), 1);

		assertEquals(ll.getFirst(), "Tyler");

		//		System.out.println(ll.getFirst() + ll.getLast());
		assertEquals(ll.getFirst(), ll.getLast());

		assertEquals(ll.getLast(), ll.retrieveLastElement());
		assertEquals(ll.getSize(), 0);

		ll.addToEnd("Bob");
		assertEquals(ll.retrieveFirstElement(), "Bob");
		assertEquals(ll.getSize(), 0);
	}
	@Test 
	public void testReversalsBasic() {
		BasicLinkedList<String> ll = new BasicLinkedList<String>();
		ll.addToFront("Middle");
		ll.addToEnd("Last");
		ll.addToFront("First");
		ArrayList<String> arr = new ArrayList<String>();
		arr.add("Last");
		arr.add("Middle");
		arr.add("First");

		assertEquals(arr, ll.getReverseArrayList());

		BasicLinkedList<String> revll = ll.getReverseList();		
		assertEquals(revll.getFirst(), "Last");
		assertEquals(revll.getLast(), "First");
	}
	@Test
	public void testRemoveBasic() {
		BasicLinkedList<String> ll = new BasicLinkedList<String>();
		ll.addToFront("Middle");
		ll.addToEnd("Last");
		ll.addToFront("First");
		ll.addToFront("Middle");
		Comparator<String> comp = (a, b) -> a.compareTo(b);
		ll.remove("Middle", comp);
		assertEquals(ll.getSize(), 2);
		assertEquals(ll.getFirst(), "First");


	}

	//SortedLinkedList Tests
	@Test
	public void testSortedList() {
		Comparator<String> comp = (a, b) -> a.compareTo(b);
		SortedLinkedList<String> intArray = new SortedLinkedList<String>(comp);

		intArray.add("C").add("V").add("B").add("A");
		assertEquals(intArray.getFirst(), "A");

		intArray.remove("B").remove("A");
		assertEquals(intArray.retrieveFirstElement(), "C");

		assertEquals(intArray.getFirst(), "V");
		assertEquals(intArray.getSize(), 1);

		assertEquals(intArray.getFirst(), intArray.retrieveLastElement());
		assertEquals(intArray.getSize(), 0);		
	}

	//Tests for various edge cases:

	@Test
	public void testEdge1() {
		Comparator<Integer> comp = (a, b) -> a.compareTo(b);
		//Test operations on empty list
		BasicLinkedList<Integer> ll = new BasicLinkedList<Integer>();
		assertEquals(ll.retrieveLastElement(), null);
		assertEquals(ll.retrieveFirstElement(), null);
		assertEquals(ll.getFirst(), null);
		assertEquals(ll.getLast(), null);
		
		//Test for single element list
		ll.addToFront(4);
		assertEquals(ll.getFirst(), ll.getLast());
		assertEquals(ll.retrieveFirstElement(), Integer.valueOf(4));
		assertEquals(ll.getSize(), 0);
		assertEquals(ll.getFirst(), null);

		//Test for remove method when there are duplicates
		ll.addToFront(9).addToFront(8).addToFront(9);
		assertEquals(ll.getSize(), 3);
		//Remove the two nodes of integer 9
		ll.remove(Integer.valueOf(9), comp);
		assertEquals(ll.getSize(), 1);
		assertEquals(ll.getLast(), Integer.valueOf(8));
	}
	public void testEmptyListOperations() {
		BasicLinkedList<Integer> list = new BasicLinkedList<>();
		// Verify retrieval methods return null on an empty list
		assertNull(list.getFirst());
		assertNull(list.getLast());
		assertNull(list.retrieveFirstElement());
		assertNull(list.retrieveLastElement());
		// Verify that size is 0
		assertEquals(0, list.getSize());
	}

	@Test
	public void testSingleElementOperations() {
		BasicLinkedList<Integer> list = new BasicLinkedList<>();
		list.addToFront(10);
		// Only one element: head and tail should be the same
		assertEquals(Integer.valueOf(10), list.getFirst());
		assertEquals(Integer.valueOf(10), list.getLast());
		// Remove the only element
		Integer removed = list.retrieveFirstElement();
		assertEquals(Integer.valueOf(10), removed);
		// List should now be empty
		assertNull(list.getFirst());
		assertNull(list.getLast());
		assertEquals(0, list.getSize());
	}

	@Test
	public void testDuplicateRemovals() {
		BasicLinkedList<Integer> list = new BasicLinkedList<>();
		// Create a list with duplicates: order depends on your add methods
		list.addToFront(5).addToEnd(7).addToFront(5).addToEnd(9);
		// List size should be 4 before removal
		assertEquals(4, list.getSize());
		// Remove all nodes equal to 5 using a simple comparator
		Comparator<Integer> comp = (a, b) -> a.compareTo(b);
		list.remove(5, comp);
		// Expect the two nodes with value 5 to be removed
		assertEquals(2, list.getSize());
		assertEquals(Integer.valueOf(7), list.getFirst());
		assertEquals(Integer.valueOf(9), list.getLast());
	}

	@Test
	public void testReversalMethods() {
		BasicLinkedList<String> list = new BasicLinkedList<>();
		// Add elements so that the list order (head to tail) is: First, Middle, Last
		list.addToFront("Middle").addToEnd("Last").addToFront("First");

		// Test getReverseArrayList: expect reverse order [Last, Middle, First]
		ArrayList<String> expectedArray = new ArrayList<>();
		expectedArray.add("Last");
		expectedArray.add("Middle");
		expectedArray.add("First");
		assertEquals(expectedArray, list.getReverseArrayList());

		// Test getReverseList: the reversed linked list should have
		// first element "Last" and last element "First"
		BasicLinkedList<String> reversedList = list.getReverseList();
		assertEquals("Last", reversedList.getFirst());
		assertEquals("First", reversedList.getLast());
	}

}
