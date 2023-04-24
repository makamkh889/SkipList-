import java.util.Arrays;

import javax.xml.crypto.Data;

/**
 * The purpose of this class is to parse a text file into its appropriate, line
 * by line commands for the format specified in the project spec.
 * 
 * @author CS Staff
 * 
 * @version 2021-08-23
 */
public class CommandProcessor {

	// the database object to manipulate the
	// commands that the command processor
	// feeds to it
	private Database data;

	/**
	 * The constructor for the command processor requires a database instance to
	 * exist, so the only constructor takes a database class object to feed commands
	 * to.
	 * 
	 * @param dataIn the database object to manipulate
	 */
	public CommandProcessor() {
		data = new Database();
	}

	/**
	 * This method identifies keywords in the line and calls methods in the database
	 * as required. Each line command will be specified by one of the keywords to
	 * perform the actions within the database required. These actions are performed
	 * on specified objects and include insert, remove, regionsearch, search,
	 * intersections, and dump. If the command in the file line is not one of these,
	 * an appropriate message will be written in the console. This processor method
	 * is called for each line in the file. Note that the methods called will
	 * themselves write to the console, this method does not, only calling methods
	 * that do.
	 * 
	 * @param line a single line from the text file
	 */
	public void processor(String line) {
		// int intArray[];
		String[] arr = line.split("\\s+");
		Arrays.toString(arr);

		if (arr[0].equals("search")) {

			if (arr.length != 2)
				System.out.println("rejected values");
			else
				data.search(arr[1]);
		} else if (arr[0].equals("insert")) {

			if (arr.length != 6) {
				System.out.println("rejected values");
			} else {
				Rectangle R = new Rectangle(Integer.parseInt(arr[2]), Integer.parseInt(arr[3]),
						Integer.parseInt(arr[4]), Integer.parseInt(arr[5]));
				KVPair<String, Rectangle> result = new KVPair<>(arr[1], R);
				data.insert(result);
			}
		}

		else if (arr[0].equals("remove") && arr[1].length() > 0 && Character.isLetter(arr[1].charAt(0))) {
			if (arr.length != 2) {
				System.out.println("rejected values");
			} else {
				data.remove(arr[1]);
			}
		}

		else if (arr[0].equals("remove")) {
			
			if (arr.length != 5) {
				System.out.println("rejected values");
			} else {

				data.remove(Integer.parseInt(arr[1]), Integer.parseInt(arr[2], 10), Integer.parseInt(arr[3], 10),
						Integer.parseInt(arr[4]));
			}
		}

		else if (arr[0].equals("regionsearch")) {
			if (arr.length != 5) {
				System.out.println("rejected values");
			} else {
				data.regionsearch(Integer.parseInt(arr[1]), Integer.parseInt(arr[2]), Integer.parseInt(arr[3]),
						Integer.parseInt(arr[4]));
			}
		}

		else if (arr[0].equals("intersections")) {
			if (arr.length != 1) {
				System.out.println("rejected values");
			} else {

				data.intersections();
			}
		}

		else if (arr[0].equals("dump")) {

			if (arr.length != 1) {
				System.out.println("rejected values");
			} else {
				data.dump();
			}
		}

	}
}
