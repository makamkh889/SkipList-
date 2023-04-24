import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

//Defining the Rectangle class
public class Rectangle {
	// Declaring variables for Rectangle dimensions
	private int x;
	private int y;
	private int width;
	private int height;

	public Rectangle(int x, int y, int w, int h) {

		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	// Getters for Rectangle properties
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int w) {
		this.width = w;
	}

	public void setHeight(int h) {
		this.height = h;
	}

	// Method to check whether the is valid or not
	public boolean Is_Valid() {
		try {
			if ((x >= 0 && x + width <= 1024) && (y >= 0 && y + height <= 1024) && (width > 0) && (height > 0)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// Printing stack trace in case of any exceptions
			e.printStackTrace();
		}
		// Returning false if the Rectangle is not valid
		return false;
	}

	// Method to check whether the Rectangle has a valid region or not
	public boolean regionsearch() {
		if (width > 0 && height > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean intersecting(Rectangle Rec2) {
		// Check if the rectangles intersect by checking if their x, y, width, and
		// height values overlap
		return x < Rec2.x + Rec2.width && x + width > Rec2.x && y < Rec2.y + Rec2.height && y + height > Rec2.y;
	}

	public String toString() {
		return Integer.toString(x) + ',' + Integer.toString(y) + ',' + Integer.toString(width) + ','
				+ Integer.toString(height);

	}

	public boolean equalto(Object o) {
		Rectangle R = (Rectangle) o;
		if (x == R.x && y == R.y && width == R.width && height == R.height)
			return true;
		return false;

	}

}
