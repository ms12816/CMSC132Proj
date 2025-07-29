package implementation;

import java.util.Comparator;
import java.util.TreeSet;

public class BinarySearchTree<K, V> {
	/*
	 * You may not modify the Node class and may not add any instance nor static
	 * variables to the BinarySearchTree class.
	 */
	private class Node {
		private K key;
		private V value;
		private Node left, right;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}

	private Node root;
	private int treeSize, maxEntries;
	private Comparator<K> comparator;

	public BinarySearchTree(Comparator<K> comparator, int maxEntries) {
		this.comparator = comparator;
		this.maxEntries = maxEntries;
		treeSize = 0;
		root = null;
	}

	public BinarySearchTree<K, V> add(K key, V value) throws TreeIsFullException {
		//Call helper method which will add the new Node according to the key.
		root = addRecursive(root, key, value);
		return this;
	}
	//Private recursive helper method
	private Node addRecursive(Node curr, K key, V value) throws TreeIsFullException{
		//Check if tree is already at max capacity 
		//Check if there is a node, if not create new node and make it the root (through the original method)
		if(curr == null) {
			if(treeSize >= maxEntries) {
				throw new TreeIsFullException("Maximum Size");
			}
			treeSize++;
			return new Node(key, value);
		}
		//Compare current key with new key
		int compare = comparator.compare(key, curr.key);
		//If the new key is smaller then go left
		if(compare < 0) {
			curr.left = addRecursive(curr.left, key, value);
		//If key is larger go right
		} else if (compare > 0) {
			curr.right = addRecursive(curr.right, key, value);
		//If key matches then replace with new value
		} else {
			curr.value = value;
		}
		//Return current node to keep iterating through recursion 
		return curr;
		
	}

	public String toString() {
		if(root == null) {
			return "EMPTY TREE";
		}
		//Start recursive method at root with empty string
		return toStringRecursive(root, "");
	}
	//Private helper method 
	private String toStringRecursive(Node curr, String bst) {
		//End recursion once null node is reached
		if(curr == null) {
			return bst;
		}
		//Go through left side recursively first, in order to correctly add key/value in increasing order
		//Technically non tail recursive as it has operations to complete between and after recursion 
		//When going back up the left side, it will check the right nodes in correct order, following the
		//Requirement of increasing order of a traditional BST
		bst = toStringRecursive(curr.left, bst) + "{" + curr.key + ":" + curr.value + "}" +
				toStringRecursive(curr.right, bst);
		return bst;
	}
	

	public boolean isEmpty() {
		return root == null;
	}


	public int size() {
		return treeSize;
	}


	public boolean isFull() {
		return treeSize == maxEntries;
	}

	public KeyValuePair<K, V> getMinimumKeyValue() throws TreeIsEmptyException {
		//Check if tree is empty
		if(root == null) throw new TreeIsEmptyException("EMPTY TREE ");
		//New node defined using recursively finding smallest value 
		Node min = getMinRecursive(root);
		//Return a KeyValuePair object using the smallest node's key and value
        return new KeyValuePair<>(min.key, min.value);

	}
	//Private helper method
	private Node getMinRecursive(Node curr) {
		//Essentially keeps going along the leftmost side of the tree until it reaches null case 
		//Smallest value will have no left node
		if(curr.left == null) return curr;
		return getMinRecursive(curr.left);
	}

	public KeyValuePair<K, V> getMaximumKeyValue() throws TreeIsEmptyException {
		//Check if tree is empty
		if(root == null) throw new TreeIsEmptyException("EMPTY TREE ");
		//New node defined by recursively finding largest value
		Node max = getMaxRecursive(root);
		//Return a KeyValuePair object using the max node's key and value
        return new KeyValuePair<>(max.key, max.value);
	}
	//Private helper method
	private Node getMaxRecursive(Node curr) {
		//Keep going right through the tree until there is no more right node, which means the largest has 
		//been found
		if(curr.right == null) return curr;
		return getMaxRecursive(curr.right);
	}
	public KeyValuePair<K, V> find(K key) {
		return findRecursive(root, key);
	}
	//Private helper method
    private KeyValuePair<K, V> findRecursive(Node curr, K key) {
    	//Return null if tree is empty or if end of list has been reached without finding target
        if (curr == null) {
            return null;
        }
        //Determine which way to go (if target key is less than current go left)
        int cmp = comparator.compare(key, curr.key);
        if (cmp < 0) {
            return findRecursive(curr.left, key);
        } else if (cmp > 0) {
            return findRecursive(curr.right, key);
        } else {
        	//Else statement will occur when target is found as cmp will be 0, return appropriate reference
            return new KeyValuePair<>(curr.key, curr.value);
        }
    }
	
	public BinarySearchTree<K, V> delete(K key) throws TreeIsEmptyException {
		//Check if empty tree
		if(root == null) throw new TreeIsEmptyException("EMPTY TREE");
		//Delete Node and retrieve it. 
		root = deleteRecursive(root, key);

		return this;
		
	}
	//Private helper method
	private Node deleteRecursive(Node curr, K key) {
		//Check null
		if(curr == null) return null;
		//Compare target key and current key
		int cmp = comparator.compare(key, curr.key);
		//If key < curr.key go left along tree
		if(cmp < 0) {
			curr.left = deleteRecursive(curr.left, key);
			
		//If key > curr.key go right along tree 
		} else if(cmp > 0) {
			curr.right = deleteRecursive(curr.right, key);
			
		//Final case, when cmp = 0 the target node has been found
		} else {
			//Reduce tree size and check if it has one or two children
			treeSize --;
			if(curr.left == null) return curr.right;
			if(curr.right == null) return curr.left;
			//If there are two children, proceed
			//Find smallest in order successor: minimum of right subtree
			Node successor = getMinRecursive(curr.right);
			//Tie the tree back by going around the node to be deleted
			curr.key = successor.key;
			curr.value = successor.value;
			//Delete successor duplicate 
			curr.right = deleteRecursive(curr.right, successor.key);
		}
		
		return curr;
		
	}

	public void processInorder(Callback<K, V> callback) {
		processRecursive(root, callback);
	}
	//Private helper method 
	private void processRecursive(Node curr, Callback<K, V> callback) {
		if(curr == null) return;
		
		if(curr.left != null) this.processRecursive(curr.left, callback);
		
		callback.process(curr.key, curr.value);
		
		if(curr.right != null) this.processRecursive(curr.right, callback);
		
		
	}
	

	public BinarySearchTree<K, V> subTree(K lowerLimit, K upperLimit) {
		if(comparator.compare(lowerLimit, upperLimit) > 0 || lowerLimit == null
				|| upperLimit == null) throw new IllegalArgumentException("Invalid Limits");
		
		BinarySearchTree<K, V> sub = new BinarySearchTree<K, V>(comparator, maxEntries);
		subTreeRecursive(lowerLimit, upperLimit, root, sub);
		
		return sub;
	}
	
	private void subTreeRecursive(K lowerLimit, K upperLimit, Node curr, BinarySearchTree<K, V> sub){
		if(curr == null) return;
		int compareUpper = comparator.compare(curr.key, upperLimit);
		int compareLower = comparator.compare(curr.key, lowerLimit);
		if(compareLower > 0) {
			subTreeRecursive(lowerLimit, upperLimit, curr.left, sub);
		}
		if(compareUpper <= 0 && compareLower >= 0) {
			try {
				sub.add(curr.key, curr.value);
			} catch (Exception e) {
				System.out.print("Error");
			}
		}
		
		if(compareUpper < 0) {
			subTreeRecursive(lowerLimit, upperLimit, curr.right, sub);
		}
		
		
	}

	
	public TreeSet<V> getLeavesValues() {
		TreeSet<V> tree = new TreeSet<V>();
		getValuesRecursive(root, tree);
		return tree;
				
	}
	//Private helper method
	private void getValuesRecursive(Node curr, TreeSet<V> tree) {
		if(curr == null) return;
		if(curr.right == null && curr.left == null) {
			tree.add(curr.value);
			return;
		} 
		if(curr.left != null) getValuesRecursive(curr.left, tree);
		if(curr.right != null) getValuesRecursive(curr.right, tree);
		
	}
}
