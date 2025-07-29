package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import implementation.BinarySearchTree;
import implementation.Callback;
import implementation.KeyValuePair;
import implementation.TreeIsEmptyException;
import implementation.TreeIsFullException;

/* The following directive executes tests in sorted order */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class StudentTests {


	private static final Comparator<Integer> INT_COMPARATOR = Integer::compareTo;
	@Test
	public void testAdd() throws TreeIsFullException{
		BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(INT_COMPARATOR, 5);
		tree.add(31, "Sicko Mode");
		tree.add(2, "Till Furthur Notice");
		tree.add(5, "No Bystanders");
		String correct = "{2:Till Furthur Notice}{5:No Bystanders}{31:Sicko Mode}";
		assertEquals(correct, tree.toString());
		assertEquals(tree.size(), 3);
		assertFalse(tree.isFull());
		assertFalse(tree.isEmpty());	
	}
	
	@Test
	public void MinMax() throws Exception {
		BinarySearchTree<Integer,String> tree = new BinarySearchTree<>(INT_COMPARATOR, 10);
		tree.add(20, "twenty").add(10, "ten").add(30, "thirty");
		KeyValuePair<Integer,String> min = tree.getMinimumKeyValue();
		KeyValuePair<Integer,String> max = tree.getMaximumKeyValue();
		assertEquals(Integer.valueOf(10), min.getKey());
		assertEquals("ten", min.getValue());
		assertEquals(Integer.valueOf(30), max.getKey());
		assertEquals("thirty", max.getValue());
	}
	
	@Test
	public void testDelete() throws Exception{
		BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(INT_COMPARATOR, 5);
		tree.add(89, "Never Sleep");
		tree.add(9, "Some Way");
		tree.add(56, "Minute");
		tree.delete(15);
		assertEquals(tree.size(), 3);
		tree.delete(89);
		assertNull(tree.find(89));
		assertEquals(tree.size(), 2);
	}
	
	@Test 
	public void testProcessInOrder() throws TreeIsFullException {
		BinarySearchTree<Integer, String> arsenal = new BinarySearchTree<>(INT_COMPARATOR, 5);
		arsenal.add(23, "Merino").add(7, "Saka").add(8, "Odegaard");
		List<Integer> visit = new ArrayList<>();
		Callback<Integer,String> cb = (k,v) -> visit.add(k);
		arsenal.processInorder(cb);
		assertEquals(List.of(7,8,23), visit);
	}
	
	@Test
	public void TestSubTree() throws Exception {
		BinarySearchTree<Integer,String> tree = new BinarySearchTree<>(INT_COMPARATOR, 10);
		tree.add(1,"one").add(3,"three").add(5,"five").add(7,"seven");
		BinarySearchTree<Integer,String> sub = tree.subTree(3,6);
		assertEquals("{3:three}{5:five}", sub.toString());
		assertEquals(2, sub.size());
	}
	
	@Test
	public void testGetLeavesValues() throws Exception{
		BinarySearchTree<Integer, String> tree = new BinarySearchTree<>(INT_COMPARATOR, 10);
		tree.add(3, "root").add(1, "LL").add(6, "left").add(4, "RL").add(9, "RR");
        TreeSet<String> leaves = tree.getLeavesValues();
        TreeSet<String> expected = new TreeSet<>(List.of("LL","RL","RR"));
        assertEquals(expected, leaves);
	}
	
    @Test
    public void testIsFull() throws TreeIsFullException {
        BinarySearchTree<Integer,String> tree = new BinarySearchTree<>(INT_COMPARATOR, 3);
        tree.add(1,"a").add(2,"b").add(3,"c");
        assertTrue(tree.isFull());
        try {
            tree.add(4,"d");
            fail("Expected TreeIsFullException");
        } catch (TreeIsFullException e) {
            // expected
        }
    }
    
    @Test
    public void testEmpty() throws Exception{
    	BinarySearchTree<Integer, String> carti = new BinarySearchTree<>(INT_COMPARATOR, 5);
    	carti.add(1, "Shoota").add(75, "Cocaine Nose");
    	assertEquals(carti.size(), 2);
    	carti.delete(1).delete(75);
    	assertTrue(carti.isEmpty());
    }
    
    @Test
    public void testEmptyTreeExceptions() {
        BinarySearchTree<Integer,String> empty = new BinarySearchTree<>(INT_COMPARATOR, 5);
        try {
            empty.getMinimumKeyValue();
            fail("Expected TreeIsEmptyException when calling getMinimumKeyValue on empty tree");
        } catch (TreeIsEmptyException e) {
            // expected
        } catch (Exception e) {
            fail("Wrong exception type: " + e);
        }
    }
}


