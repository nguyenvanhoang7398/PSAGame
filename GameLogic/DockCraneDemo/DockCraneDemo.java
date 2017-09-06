import java.util.*;

class DockCrane {

	// initial values for non-param constructor
	private static final int INIT_POSX = 0;
	private static final int INIT_POSY = 0;
	private static final int INIT_SIZE = 1;
	private static final boolean INIT_ISHOLDING = false;

	// default string
	private static final String INFORM_POSITION = "Crane is currently at";
	private static final String INFORM_SIZE = "with size";
	private static final String INFORM_ISHOLDING_TRUE = "is holding an item";
	private static final String INFORM_ISHOLDING_FALSE = "is not holding any item";

	// attributes
	private int _posX, _posY, _size; // vertical position, horizonal position and size of the crane, assume it's a square
	private boolean _isHolding;

	/** 
	 * constructors
	 * @param NONE
	 */
	DockCrane() {
		this(INIT_POSY, INIT_POSX, INIT_SIZE);
	}

	/** 
	 * constructors
	 * @param 
	 * posY: initialize integer for posY
	 * posX: initialize integer for posX
	 * posY: initialize integer for size
	 */
	DockCrane(int posY, int posX, int size) {
		_posY = posY;
		_posX = posX;
		_size = size;
		_isHolding = false;
	}

	/** 
	 * get horizontal position of the crane
	 * @param NONE
	 * @return integer representing the horizontal position of the crane
	 */
	public int getX() {
		return _posX;
	}

	/** 
	 * get vertical position of the crane
	 * @param NONE
	 * @return integer representing the vertical position of the crane
	 */
	public int getY() {
		return _posY;
	}

	/** 
	 * get position of the crane
	 * @param NONE
	 * @return integer array representing the position of the crane as [Y, X]
	 */
	public int[] getPos() {
		return new int[] {_posY, _posX};
	}

	/** 
	 * get size of the crane
	 * @param NONE
	 * @return integer representing the size of the crane
	 */
	public int getSize() {
		return _size;
	}

	/** 
	 * check if the crane is holding any item
	 * @param NONE
	 * @return boolean representing if the crane is holding any item
	 */
	public boolean isHolding() {
		return _isHolding;
	}

	/** 
	 * set new position of the crane
	 * @param 
	 * pos: integer array representing the position of the crane as [Y, X]
	 * @return NONE
	 */
	public void setPos(int[] pos) {
		_posY = pos[0];
		_posX = pos[1];
	}

	/** 
	 * set horizontal position of the crane
	 * @param 
	 * posX: integer array representing the new horizontal position of the crane
	 * @return NONE
	 */
	public void setX(int posX) {
		_posX = posX;
	}

	/** 
	 * set vertical position of the crane
	 * @param 
	 * posX: integer array representing the new vertical position of the crane
	 * @return NONE
	 */
	public void setY(int posY) {
		_posY = posY;
	}

	/** 
	 * set size of the crane
	 * @param 
	 * posX: integer representing the newsize of the crane
	 * @return NONE
	 */
	public void setSize(int size) {
		_size = size;
	}

	/** 
	 * set if the crane is holding
	 * @param 
	 * posX: boolean updating if the crane is holding
	 * @return NONE
	 */
	public void setIsHolding(boolean isHolding) {
		_isHolding = isHolding;
	}

	/** 
	 * toggle if the crane is holding
	 * @param 
	 * posX: boolean updating if the crane is holding
	 * @return NONE
	 */
	public void toggleIsHolding() {
		_isHolding = !_isHolding;
	}	

	/** 
	 * constructors
	 * @param NONE
	 * @return string representation of the dock crane
	 */
	public String toString() {
		String isHoldingString = _isHolding ? INFORM_ISHOLDING_TRUE : INFORM_ISHOLDING_FALSE;
		return INFORM_POSITION + " " + _posY + ", " + _posX + " " + INFORM_SIZE + " " 
		+ _size + ", " + isHoldingString;
	}
} 

public class DockCraneDemo {
	public static void main(String[] args) {
		DockCrane myDockCrane = new DockCrane();
		System.out.println("Initial position: " + myDockCrane.getPos()[0] + " " + myDockCrane.getPos()[1]); // this should print 0 0
		System.out.println("Initial y: " + myDockCrane.getY()); // this should print 0
		System.out.println("Initial x: " + myDockCrane.getX()); // this should print 0
		System.out.println("Initial size: " + myDockCrane.getSize()); //  this should print 1
		myDockCrane.setPos(new int[] {2, 5});
		myDockCrane.setSize(4);
		System.out.println("Current position: " + myDockCrane.getPos()[0] + " " + myDockCrane.getPos()[1]); // this should print 2 5
		System.out.println("Current y: " + myDockCrane.getY()); // this should print 2
		System.out.println("Current x: " + myDockCrane.getX()); // this should print 5
		System.out.println("Current size: " + myDockCrane.getSize()); //  this should print 4
		System.out.println(myDockCrane); // this should print "Crane is currently at 2, 5 with size 4 is not holding any item"
	}
}