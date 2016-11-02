import java.awt.Color;
import java.awt.Graphics;

/**
 * One Square on our Tetris Grid or one square in our Tetris game piece
 * 
 * @author CSC 143
 */
public class Square {
	private Grid grid; // the environment where this Square is

	private int row, col; // the grid location of this Square

	private boolean ableToMove; // true if this Square can move

	private Color color; // the color of this Square

	// possible move directions are defined by the Game class

	// dimensions of a Square
	public static final int WIDTH = 20;

	public static final int HEIGHT = 20;

	/**
	 * Creates a square
	 * 
	 * @param g
	 *            the Grid for this Square
	 * @param row
	 *            the row of this Square in the Grid
	 * @param col
	 *            the column of this Square in the Grid
	 * @param c
	 *            the Color of this Square
	 * @param mobile
	 *            true if this Square can move
	 * 
	 * @throws IllegalArgumentException
	 *             if row and col not within the Grid
	 */
	public Square(Grid g, int row, int col, Color c, boolean mobile) {
		if (row < 0 || row > Grid.HEIGHT - 1)
			throw new IllegalArgumentException("Invalid row =" + row);
		if (col < 0 || col > Grid.WIDTH - 1)
			throw new IllegalArgumentException("Invalid column  = " + col);

		// initialize instance variables
		grid = g;
		this.row = row;
		this.col = col;
		color = c;
		ableToMove = mobile;
	}

	/**
	 * Returns the row for this Square
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Returns the column for this Square
	 */
	public int getCol() {
		return col;
	}

	/**
	 * Returns true if this Square can move 1 spot in direction d
	 * 
	 * @param direction
	 *            the direction to test for possible move
	 */
	public boolean canMove(Direction direction) {
		if (!ableToMove)
			return false;

		boolean move = true;
		// if the given direction is blocked, we can't move
		// remember to check the edges of the grid
		switch (direction) {
		case DOWN:
			if (row == (Grid.HEIGHT - 1) || grid.isSet(row + 1, col))
				move = false;
			break;

		// currently doesn't support checking LEFT or RIGHT
		// MODIFY so that it correctly returns if it can move left or right
			
		// Same checks as above, but horizontal rather than vertical
		case LEFT:
			if (col == (0) || grid.isSet(row, col - 1))
				move = false;
			break;
		case RIGHT:
			if (col == (Grid.WIDTH - 1) || grid.isSet(row, col + 1))
			move = false;
			break;
		case UP:
			if (row == (0) || grid.isSet(row - 1, col))
				move = false;
			break;
		}
		return move;
	}

	/**
	 * moves this square in the given direction if possible.
	 * 
	 * The square will not move if the direction is blocked, or if the square is
	 * unable to move.
	 * 
	 * If it attempts to move DOWN and it can't, the square is frozen and cannot
	 * move anymore
	 * 
	 * @param direction
	 *            the direction to move
	 */
	public void move(Direction direction) {
		if (canMove(direction)) {
			switch (direction) {
			case DOWN:
				row = row + 1;
				break;

			// currently doesn't support moving LEFT or RIGHT
			// MODIFY so that the Square moves appropriately
			case LEFT:
				col = col - 1;
				break;
			case RIGHT:
				col = col + 1;
				break;
			case UP:
				row = row - 1;
				break;
			}
		}
	}

	/**
	 * Changes the color of this square
	 * 
	 * @param c
	 *            the new color
	 */
	public void setColor(Color c) {
		color = c;
	}

	/**
	 * Gets the color of this square
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Draws this square on the given graphics context
	 */
	public void draw(Graphics g) {

		// calculate the upper left (x,y) coordinate of this square
		int actualX = Grid.LEFT + col * WIDTH;
		int actualY = Grid.TOP + row * HEIGHT;
		g.setColor(color);
		g.fillRect(actualX, actualY, WIDTH, HEIGHT);
		// black border (if not empty)
		if (!color.equals(Grid.EMPTY)) {
			g.setColor(Color.BLACK);
			g.drawRect(actualX, actualY, WIDTH, HEIGHT);
		}
	}
	
	public void rotateAbout(Square s1) {
		
		int newRow = s1.row + (col - s1.col);
		int newCol = s1.col + (s1.row - row);
		int diffRow = newRow - row;
		int diffCol = newCol - col;
		
		for(int i = 0; i < Math.abs(diffRow); i++){
			if (diffRow < 0){
				this.move(Direction.UP);
			}else {
				this.move(Direction.DOWN);
			}
		}
		for(int i = 0; i < Math.abs(diffCol); i++){
			if (diffCol < 0){
				this.move(Direction.LEFT);
			} else {
				this.move(Direction.RIGHT);
			}
		}
	}
	
	/**
	 * Returns true if this Square can rotate around the piece's center square
	 * 
	 * @param center
	 *            the center square, around which the present square rotates
	 */
	public boolean canRotate(Square s1) {
		if (!ableToMove)
			return false;

//		boolean rotate = true;
		
		// destination
		int newRow = s1.row + (col - s1.col);
		int newCol = s1.col + (s1.row - row);
		
		// difference between current square and destination square
		int diffRow =(newRow - row);
		int diffCol =(newCol - col);
		
//		int centerRow = s1.row;
//		int centerCol = s1.col;
//		int centerRowDiff = row - centerRow;
//		int centerColDiff = col - centerCol;

		// check that the destination square is in the grid
		
		if (newRow > grid.HEIGHT - 1 || newRow < 0 || newCol > grid.WIDTH - 1 || newCol < 0){
			return false;
		}
		
		// check the row from (row,col) to (row,newCol)
		// check from (row,newCol) to (newRow,newCol)
		
		// first quadrant, move up then right
	if (newRow <= row && newCol >= col){
		for (int i = 0; i <= Math.abs(diffRow); i++){
			if(grid.isSet(row - i, col)){
				return false;
			}
		}
		for (int i = 0; i <= Math.abs(diffCol); i++){
			if(grid.isSet(newRow, col + i)){
				return false;
			}
		}
	}
	
	// second quadrant, move left then up
	if (newRow <= row && newCol <= col){
		for (int i = 0; i <= Math.abs(diffCol); i++){
			if(grid.isSet(row, col - i)){
				return false;
			}
		}
		for (int i = 0; i <= Math.abs(diffRow); i++){
			if(grid.isSet(row - i, newCol)){
				return false;
			}
		}
	}
	
	// third quadrant, move down then left
	if (newRow >= row && newCol <= col){
		for (int i = 0; i <= Math.abs(diffRow); i++){
			if(grid.isSet(row + i, col)){
				return false;
			}
		}
		for (int i = 0; i <= Math.abs(diffCol); i++){
			if(grid.isSet(newRow, col - i)){
				return false;
			}
		}

	}
	
	// fourth quadrant, move right then down
	if (newRow >= row && newCol >= col){
		for (int i = 0; i <= Math.abs(diffCol); i++){
			if(grid.isSet(row, col + i)){
				return false;
			}
		}
		for (int i = 0; i <= Math.abs(diffRow); i++){
			if(grid.isSet(row + i, newCol)){
				return false;
			}
		}
	}
		
//		// path from (row,col) to (newRow,newCol) is free
//		if(row > 0 && col > 0){
//			System.out.println(row);
//		System.out.println(col);
//		System.out.println(diffRow);
//		System.out.println(diffCol);
//		System.out.println(centerRowDiff);
//		System.out.println(centerColDiff);
//		
//		// first quadrant
//			// moves right, then down
//		if(centerColDiff > 0 && centerRowDiff > 0){
//			for(int i = 1; i <= Math.abs(diffCol); i++){
//				if(grid.isSet(row, col + i)){
//					System.out.println("Quadrant I is false");
//					return false;
//				}
//			}
//			for(int i = 1; i <= Math.abs(diffRow); i++){
//				if(grid.isSet(row + i, col)){
//					System.out.println("Quadrant I is false");
//					return false;
//				}
//			}
//		}
//
//		// second quadrant
//		// moves up, then right
//		if(centerColDiff > 0 && centerRowDiff < 0){
//			for(int i = 1; i <= Math.abs(diffRow); i++){
//				if(grid.isSet(row - i, col)){
//					System.out.println("Quadrant II is false");
//					return false;
//				}
//			}
//			for(int i = 1; i <= Math.abs(diffCol); i++){
//				if(grid.isSet(row, col + i)){
//					System.out.println("Quadrant II is false");
//					return false;
//				}
//			}
//		}
//		// third quadrant
//		// moves left, then up
//		if(centerColDiff < 0 && centerRowDiff < 0){
//			for(int i = 1; i <= Math.abs(diffCol); i++){
//				if(grid.isSet(row, col - i)){
//					System.out.println("Quadrant III is false");
//					return false;
//				}
//			}
//			for(int i = 1; i <= Math.abs(diffRow); i++){
//				if(grid.isSet(row - i, col)){
//					System.out.println("Quadrant III is false");
//					return false;
//				}
//			}
//		}
//		// fourth quadrant
//		// moves down, then left
//		if(centerColDiff < 0 && centerRowDiff > 0){
//			for(int i = 1; i <= Math.abs(diffRow); i++){
//				if(grid.isSet(row + i, col)){
//					System.out.println("Quadrant IV is false");
//					return false;
//				}
//			}
//			for(int i = 1; i <= Math.abs(diffCol); i++){
//				if(grid.isSet(row, col - i)){
//					System.out.println("Quadrant IV is false");
//					return false;
//				}
//			}
//		}
//		}
		
		
//		// check if square is at center
//		if (row == s1.getRow() && col == s1.getCol()){
//			rotate = true;
//		}
		
//		int localRow = this.getRow() - s1.getRow();
//		int localCol = this.getCol() - s1.getCol();
//		
//		int newLocalRow = localCol;
//		int newLocalCol = -localRow;
//		
//		int rowDiff = newLocalRow - localRow;
//		int colDiff = newLocalCol - localCol;
//		
//		int newRow = this.getRow() + rowDiff;
//		int newCol = this.getCol() + colDiff;
//		
//		if(newRow > 19 || newRow < 0 || newCol > 9 || newCol < 0 || grid.isSet(newRow, newCol)){
//			return false;
//		}
//		


		return true;
	}
}
