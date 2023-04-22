import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * This class implements SkipList data structure and contains an inner SkipNode
 * class which the SkipList will make an array of to store data.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 * @param <K> Key
 * @param <V> Value
 */
public class SkipList<K extends Comparable<? super K>, V>

		implements Iterable<KVPair<K, V>> {
	private SkipNode head; // First element of the top level
	private int size; // number of entries in the Skip List

	/**
	 * Initializes the fields head, size and level
	 */
	public SkipList() {
		head = new SkipNode(null, 0);
		size = 0;
	}

	/**
	 * Returns a random level number which is used as the depth of the SkipNode
	 * 
	 * @return a random level number
	 */
	int randomLevel() {
		int lev;
		Random value = new Random();
		for (lev = 0; Math.abs(value.nextInt()) % 2 == 0; lev++) {
			// Do nothing
		}
		return lev; // returns a random level
	}

	/**
	 * Searches for the KVPair using the key which is a Comparable object.
	 * 
	 * @param key key to be searched for
	 */
	public ArrayList<KVPair<K, V>> search(K key) {

		SkipNode x = head; // Dummy header node

		ArrayList<KVPair<K, V>> result = new ArrayList<KVPair<K, V>>();
		for (int i = head.level; i >= 0; i--) { // For each level...
			while ((x.forward[i] != null) && (x.forward[i].pair.getKey().compareTo(key)) < 0) { // go forward
				x = x.forward[i];
			}
		}
		x = x.forward[0];
		while (x != null && x.pair.getKey().compareTo(key) == 0) {
			result.add(x.element());
			x = x.forward[0];
		}

		return result;
	}

	/**
	 * @return the size of the SkipList
	 */
	  public int size() {
		return size;
	}

	/**
	 * Inserts the KVPair in the SkipList at its appropriate spot as designated by
	 * its lexicoragraphical order.
	 * 
	 * @param it the KVPair to be inserted
	 */
	@SuppressWarnings("unchecked")
	public void insert(KVPair<K, V> it) {

		int newLevel = randomLevel(); // New node's level
		if (newLevel > head.level) { // If new node is deeper
			adjustHead(newLevel); // adjust the header
		}

		// Track end of level
		SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, newLevel + 1);
		SkipNode x = head; // Start at header node
		for (int i = newLevel; i >= 0; i--) { // Find insert position
			while ((x.forward[i] != null) && (x.forward[i].element().getKey().compareTo(it.getKey()) < 0))
				x = x.forward[i];
			update[i] = x; // Track end at level i
		}

		x = new SkipNode(it, newLevel);
		for (int i = 0; i <= newLevel; i++) { // Splice into list
			x.forward[i] = update[i].forward[i]; // Who x points to
			update[i].forward[i] = x; // Who points to x
		}
		size++; // Increment dictionary size

	}

	// Increases the number of levels in head so that no element has more indices
	// than the head.
	// @param newLevel the number of levels to be added to head

	@SuppressWarnings("unchecked")
	private void adjustHead(int newLevel) {
		SkipNode temp = head;
		head = new SkipNode(null, newLevel);
		if (head.level == 0) {
			head.level = 1;
		}
		for (int i = 0; i < Math.min(head.level, temp.forward.length); i++) {
			head.forward[i] = temp.forward[i];
		}
		head.level = newLevel;
	}

	/**
	 * Removes the KVPair that is passed in as a parameter and returns true if the
	 * pair was valid and false if not.
	 * 
	 * @param pair the KVPair to be removed
	 * @return returns the removed pair if the pair was valid and null if not
	 */

	@SuppressWarnings("unchecked")
	public KVPair<K, V> remove(K key) {
		// Search for the key to remove
		SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, head.level + 1);
		Arrays.fill(update, head);
		boolean removed = false;
		SkipNode current = head;
		SkipNode x = head;
		// Traverse the SkipList starting from the head node at the highest level
		for (int i = head.level; i >= 0; i--) {
			// Move down the levels until the target key is found or a larger key is
			// encountered
			while (current.forward[i] != null && current.forward[i].pair.getKey().compareTo(key) < 0) {
				current = current.forward[i];
				update[i] = current;
			}
			// If the target key is found,
			if (current.forward[i] != null && current.forward[i].pair.getKey().compareTo(key) == 0) {
				removed = true;
				x = current.forward[i];
				for (int j = 0; j < Math.min(head.level, x.forward.length); j++) { // Splice into list
					update[j].forward[j] = x.forward[j]; // Who x points to
				}
				size--;
				break;
			}
		}
		// Return null if the target key is not found
		if (!removed) {
			return null;
		}
		// Return the removed KVPair object
		else {
			KVPair<K, V> myPair = new KVPair<>(key, x.pair.getValue());
			return myPair;
		}
	}

	/**
	 * Removes a KVPair with the specified value.
	 * 
	 * @param val the value of the KVPair to be removed
	 * @return returns true if the removal was successful
	 */
	public KVPair<K, V> removeByValue(V val) {

		SkipNode cur = head;
		KVPair<K, V> removedPair = null;
		for (int i = head.level; i >= 0; i--) {
			while (cur.forward[i] != null && cur.forward[i].element().getValue().equals(val) == false) {
				cur = cur.forward[i];
			}
		}
		cur = cur.forward[0];

		if (cur != null && cur.element().getValue().equals(val)==true) {
			removedPair = cur.element();
			for (int i = 0; i < head.level; i++) {
				if (head.forward[i] == cur) {
					head.forward[i] = cur.forward[i];
				}
			}
			while (size > 0 && head.forward[size] == null) {
				size--;
			}
		}

		return removedPair;
	}
	//
	/*
	 * public KVPair<K, V> removeByValue(V val) { // Search for the value to remove
	 * SkipNode[] update = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class,
	 * head.level + 1); boolean removed = false; SkipNode current = head; SkipNode x
	 * = head;
	 * 
	 * // Traverse the SkipList starting from the head node at the highest level for
	 * (int i = head.level; i >= 0; i--) { // Move down the levels until the target
	 * key is found or a larger key is // encountered while (current.forward[i] !=
	 * null && current.forward[i].pair.getValue().equals(val) == false) { current =
	 * current.forward[i]; update[i] = current; } // If the target key is found, if
	 * (current.forward[i] != null &&
	 * current.forward[i].pair.getValue().equals(val)) { removed = true; x =
	 * current.forward[i]; for (int j = 0; j < head.level; j++) { // Splice into
	 * list update[j].forward[j] = x.forward[j]; // Who x points to } size--; break;
	 * } } // Return null if the target key is not found if (!removed) { return
	 * null; } // Return the removed KVPair object else { KVPair<K, V> myPair = new
	 * KVPair<>(x.pair.getKey(), x.pair.getValue()); return myPair; } }
	 */

	/**
	 * Prints out the SkipList in a human readable format to the console.
	 */
	public void dump() {
		SkipNode current = head;
		System.out.println("SkipList dump:");
		if (current.forward[0] == null) {
			System.out.println("Node has depth 1, Value (null)");
			System.out.println("SkipList size is: 0");
		} else {
			System.out.println("Node has depth " + current.level + ", Value (null) ");
			current = current.forward[0];

			while (current != null) {
				System.out.println("Node has depth " + current.level + ", Value (" + current.pair.getKey() + ", "
						+ current.pair.getValue() + ")");
				current = current.forward[0];
			}

			System.out.println("SkipList size is: " + size);
		}
	}

	// This class implements a SkipNode for the SkipList data structure.
	private class SkipNode {

		// the KVPair to hold
		private KVPair<K, V> pair;
		// what is this
		private SkipNode[] forward;
		// the number of levels
		private int level;

		/**
		 * Initializes the fields with the required KVPair and the number of levels from
		 * the random level method in the SkipList.
		 * 
		 * @param tempPair the KVPair to be inserted
		 * @param level    the number of levels that the SkipNode should have
		 */
		@SuppressWarnings("unchecked")
		public SkipNode(KVPair<K, V> tempPair, int level) {
			pair = tempPair;
			forward = (SkipNode[]) Array.newInstance(SkipList.SkipNode.class, level + 1);
			this.level = level;
		}

		/**
		 * Returns the KVPair stored in the SkipList.
		 * 
		 * @return the KVPair
		 */
		public KVPair<K, V> element() {
			return pair;
		}

	}

	private class SkipListIterator implements Iterator<KVPair<K, V>> {
		private SkipNode current;

		public SkipListIterator() {
			current = head;
		}

		@Override
		public boolean hasNext() {
			if (current.forward[0] != null) {
				current = current.forward[0];
			}
			return false;
		}

		@Override
		public KVPair<K, V> next() {
			current = current.forward[0];
			return null;
		}
	}

	@Override
	public Iterator<KVPair<K, V>> iterator() {
		return new SkipListIterator();
	}
}