import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class is responsible for interfacing between the command processor and
 * the SkipList. The responsibility of this class is to further interpret
 * variations of commands and do some error checking of those commands. This
 * class further interpreting the command means that the two types of remove
 * will be overloaded methods for if we are removing by name or by coordinates.
 * Many of these methods will simply call the appropriate version of the
 * SkipList method after some preparation.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class Database {

	// this is the SkipList object that we are using
	// a string for the name of the rectangle and then
	// a rectangle object, these are stored in a KVPair,
	// see the KVPair class for more information
	private SkipList<String, Rectangle> list;

	/**
	 * The constructor for this class initializes a SkipList object with String and
	 * Rectangle a its parameters.
	 */
	public Database() {
		list = new SkipList<String, Rectangle>();
	}

	/**
	 * Inserts the KVPair in the SkipList if the rectangle has valid coordinates and
	 * dimensions, that is that the coordinates are non-negative and that the
	 * rectangle object has some area (not 0, 0, 0, 0). This insert will insert the
	 * KVPair specified into the sorted SkipList appropriately
	 * 
	 * @param pair the KVPair to be inserted
	 */
	public void insert(KVPair<String, Rectangle> pair) {

		if(!check_name(pair.getKey())) {
			System.out.println("rejected name :" + pair.getKey());
			return;
		}
		if (!pair.getValue().Is_Valid()) {
			System.out.println("Rectangle rejected: (" + pair.getKey() + ',' + pair.getValue().getX() + ','
					+ pair.getValue().getY() + ',' + pair.getValue().getWidth() + ',' + pair.getValue().getHeight()
					+ ')');
			return;
		}
		System.out.println("Rectangle inserted: (" + pair.getKey() + ',' + pair.getValue().getX() + ','
				+ pair.getValue().getY() + ',' + pair.getValue().getWidth() + ',' + pair.getValue().getHeight() + ')');
		list.insert(pair);
	}

	/**
	 * Removes a rectangle with the name "name" if available. If not an error
	 * message is printed to the console.
	 * 
	 * @param name the name of the rectangle to be removed
	 */
	public boolean check_name(String name) {
		// Get the first character of the string and check if it is a letter
		char firstChar = name.charAt(0);
		if (Character.isLetter(firstChar)) {
			return true; // Return true if the first character is a letter
		}
		return false; // Return false if the first character is not a letter
	}

	public void remove(String name) {

		KVPair<String, Rectangle> result = list.remove(name);
		if (!check_name(name)) {
			System.out.println("Rectangle rejected: (" + name + ')');
			return;
		}
		if (result == null) {
			System.out.println("Rectangle not removed: (" + name + ')');
		} else {
			System.out.println("Rectangle removed: " + result.toString());
		}
		/*
		 * Iterator<KVPair<String, Rectangle>> itr; boolean check = false;
		 * 
		 * for (itr = list.iterator(); itr.hasNext();) { KVPair<String, Rectangle> p =
		 * itr.next(); String n = p.getKey(); Rectangle R = p.getValue();
		 * 
		 * if (n == name) { System.out.println("Rectangle removed: (" + name + ',' +
		 * R.getX() + ',' + R.getY() + ',' + R.getWidth() + ',' + R.getHeight() + ')');
		 * check = true; SkipList skip = new SkipList();
		 * 
		 * skip.remove(name);
		 * 
		 * break; } } if (check == false) { // Rectangle not found: (b)
		 * System.out.println("Rectangle not found: (" + name + ')'); }
		 */

	}

	/**
	 * Removes a rectangle with the specified coordinates if available. If not an
	 * error message is printed to the console.
	 * 
	 * @param x x-coordinate of the rectangle to be removed
	 * @param y x-coordinate of the rectangle to be removed
	 * @param w width of the rectangle to be removed
	 * @param h height of the rectangle to be removed
	 */
	public void remove(int x, int y, int w, int h) {

		Rectangle R = new Rectangle(x, y, w, h);

		if (!R.Is_Valid()) {
			System.out.println("Rectangle rejected: (" + R.toString() + ')');
			return;
		}
		KVPair<String, Rectangle> result = list.removeByValue(R);
		if (result == null) {
			System.out.println("Rectangle not removed: (" + R.toString() + ')');
		} else {
			System.out.println("Rectangle removed: " + result.toString());
		}

		/*
		 * Iterator<KVPair<String, Rectangle>> itr;
		 * 
		 * boolean check = false;
		 * 
		 * Rectangle R1 = new Rectangle(x, y, w, h);
		 * 
		 * for (itr = list.iterator(); itr.hasNext();) {
		 * 
		 * KVPair<String, Rectangle> p = itr.next(); String n = p.getKey(); Rectangle R
		 * = p.getValue();
		 * 
		 * if (R == R1) { System.out.println("Rectangle removed: (" + n + ',' + R.getX()
		 * + ',' + R.getY() + ',' + R.getWidth() + ',' + R.getHeight() + ')'); check =
		 * true; SkipList skip = new SkipList();
		 * 
		 * skip.removeByValue(R);
		 * 
		 * break; } } if (check == false) { // Rectangle not found: (2, 0, 4, 8)
		 * System.out.println("Rectangle not found: (" + R1.getX() + ',' + R1.getY() +
		 * ',' + R1.getWidth() + ',' + R1.getHeight() + ')'); }
		 */

	}

	/**
	 * Displays all the rectangles inside the specified region. The rectangle must
	 * have some area inside the area that is created by the region, meaning,
	 * Rectangles that only touch a side or corner of the region specified will not
	 * be said to be in the region. You will need a SkipList Iterator for this
	 * 
	 * @param x x-Coordinate of the region
	 * @param y y-Coordinate of the region
	 * @param w width of the region
	 * @param h height of the region
	 */
	public void regionsearch(int x, int y, int w, int h) {
		Rectangle R = new Rectangle(x, y, w, h);

		if (!R.Is_Valid()) {
			System.out.println("Rectangle rejected: (" + R.toString() + ')');
			return;
		}
		Iterator<KVPair<String, Rectangle>> itr;

		System.out.println("Rectangles intersecting region (" +  R.toString()+ "):");

		for (itr = list.iterator(); itr.hasNext();) {
			KVPair<String, Rectangle> p = itr.next();
			String name = p.getKey();
			Rectangle R1 = p.getValue();

			if (R1.intersecting(R) && R1 != R) {
				System.out.println(R1.toString());
			}

		}
	}

	/**
	 * Prints out all the rectangles that Intersect each other by calling the
	 * SkipList method for intersections. You will need to use two SkipList
	 * Iterators for this
	 */
	public void intersections() {

		 Iterator<KVPair<String, Rectangle>> itr1;
		 Iterator<KVPair<String, Rectangle>> itr2;
		System.out.println("Intersections pairs:");
		
		for (itr1 = list.iterator(); itr1.hasNext();) {
			KVPair<String, Rectangle> p1 = itr1.next();
			String name1 = p1.getKey();
			Rectangle R1 = p1.getValue();

			for (itr2 = list.iterator(); itr2.hasNext();) {
				KVPair<String, Rectangle> p2 = itr2.next();
				String name2 = p2.getKey();
				Rectangle R2 = p2.getValue();
				if (R1.intersecting(R2) && R1 != R2) {
					System.out.println(p1.toString()+'|'+p2.toString());
					//System.out.println(name1 + ',' + R1.getX() + ',' + R1.getY() + ',' + R1.getHeight() + ','
							//+ R1.getWidth() + ',' + " | " + name2 + ',' + R2.getX() + ',' + R2.getY() + ','
						//	+ R2.getHeight() + ',' + R2.getWidth());

				}
			}
		}

	}

	/**
	 * Prints out all the rectangles with the specified name in the SkipList. This
	 * method will delegate the searching to the SkipList class completely.
	 * 
	 * @param name name of the Rectangle to be searched for
	 */
	public void search(String name) {

		ArrayList<KVPair<String, Rectangle>> result = list.search(name);
		if(!check_name(name)) {
			System.out.println("rejected name :" + name);
			return ;
		}
		//System.out.println(list.search(name).size());
		 if (result == null || result.isEmpty()) {
			System.out.println("Rectangle not found: " + name);
		} 
		else {
			System.out.println("Rectangles found:");
			for (int i = 0; i < result.size(); i++) {
				KVPair<String, Rectangle> pair = result.get(i);
				String rectName = pair.getKey();
				Rectangle rect = pair.getValue();

				System.out.println('(' + rectName + ',' + rect.getX() + ',' + rect.getY() + ',' + rect.getWidth() + ','
						+ rect.getHeight() + ')');

			}
		}

	}

	/**
	 * Prints out a dump of the SkipList which includes information about the size
	 * of the SkipList and shows all of the contents of the SkipList. This will all
	 * be delegated to the SkipList.
	 */
	public void dump() {
		list.dump();
	}

}
