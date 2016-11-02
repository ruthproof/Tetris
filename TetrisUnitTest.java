import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

//import org.junit.*;

public class TetrisUnitTest {

	@Test
	public void testMoveSquare() {
		Grid g = new Grid();
		Square s = new Square(g, 4, 4, Color.MAGENTA, true);
		// s is blocked on the right
		g.set(4, 5, Color.YELLOW);
		// s should not be able to move to the right
		boolean b = s.canMove(Direction.RIGHT);
		// System.out.println("b = " + b);
		assertFalse(b);
		// s should be able to move left and down
		assertTrue(s.canMove(Direction.LEFT));
		assertTrue(s.canMove(Direction.DOWN));
		// move s down
		s.move(Direction.DOWN);
		assertTrue(s.getRow() == 5 && s.getCol() == 4);
	}

	/**
	 * This tests whether the L-Shape can move left or right. It also tests the
	 * drop function.
	 */
	@Test
	public void testMoveLShape() {
		Grid g = new Grid();

		// generate an LShape in the top left of the grid
		LShape ls = new LShape(1, 0, g);
		// ls should not be able to move to the left
		boolean l = ls.canMove(Direction.LEFT);
		assertFalse(l);
		// ls should be able to move to the right
		boolean r = ls.canMove(Direction.RIGHT);
		assertTrue(r);

		// move ls right until it reaches the wall
		for (int i = 0; i < 8; i++) {
			ls.move(Direction.RIGHT);
		}
		// ls should not be able to move further to the right
		boolean r2 = ls.canMove(Direction.RIGHT);
		assertFalse(r2);

		// test drop function

		// this is an approximation of the drop function
		// (i.e., it's the guts of the drop function, ripped out of
		// the Game class
		while (ls.canMove(Direction.DOWN)) {
			ls.move(Direction.DOWN);
		}
		// ls should no longer be able to move down
		boolean d = ls.canMove(Direction.DOWN);
		assertFalse(d);

		// // ls should be able to move left and down
		// assertTrue(ls.canMove(Direction.LEFT));
		// assertTrue(ls.canMove(Direction.DOWN));
		// // move s down
		// ls.move(Direction.DOWN);
		// assertTrue(ls.getLocations()[0].getX() == 4 &&
		// ls.getLocations()[0].getY() == 4);

	}

	/**
	 * An example of a unit test for check rows Add your own grid example
	 */
	@Test
	public void testCheckRows() {
		// Create a grid with a few complete rows
		// e.g.
		/**
		 * <pre>
		 *             -> row = 0
		 *  XXXXXXXXXX -> row = 1
		 *    XXXX     -> row = 2
		 *     XX      -> row = 3
		 *     XX      -> row = 4
		 *  XXXXXXXXXX -> row = 5
		 *     XX      -> row = 6
		 *     XX      -> row = 7
		 *  XXXXXXXXXX -> row = 8
		 *     XX      -> row = 9
		 *  (empty rows from row = 10 to 19)
		 * </pre>
		 */
		// After calling checkRows, the grid should be
		/**
		 * <pre>
		 *             -> row = 0
		 *             -> row = 1
		 *             -> row = 2
		 *             -> row = 3
		 *    XXXX     -> row = 4
		 *     XX      -> row = 5
		 *     XX      -> row = 6
		 *     XX      -> row = 7
		 *     XX      -> row = 8
		 *     XX      -> row = 9
		 *  (empty rows from row = 10 to 19)
		 * </pre>
		 */
		Grid g = new Grid();
		// rows[r] = number of non empty squares on row r (the non empty squares
		// are centered)
		int[] rows = { 0, 10, 4, 2, 2, 10, 2, 2, 10, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int r = 0; r < Grid.HEIGHT; r++) {
			int cLeft = 0, cRight = Grid.WIDTH - 1;
			while (cLeft <= cRight) {
				if (cRight - cLeft < rows[r]) {
					g.set(r, cLeft, Color.MAGENTA);
					g.set(r, cRight, Color.MAGENTA);
				}
				cLeft++;
				cRight--;
			}
		}

		g.checkRows();

		rows = new int[] { 0, 0, 0, 0, 4, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		for (int r = 0; r < Grid.HEIGHT; r++) {
			int cLeft = 0, cRight = Grid.WIDTH - 1;
			while (cLeft <= cRight) {
				if (cRight - cLeft < rows[r]) {
					assertTrue(g.isSet(r, cLeft));
					assertTrue(g.isSet(r, cRight));
				} else {
					assertFalse(g.isSet(r, cLeft));
					assertFalse(g.isSet(r, cRight));
				}
				cLeft++;
				cRight--;
			}
		}
	}

	@Test
	public void checkCanRotate(){
		Grid g = new Grid();
		
		AbstractPiece testPiece = new LShape(1, Grid.WIDTH / 2 - 1, g);
		assertTrue(!testPiece.canRotate());
	}
	
	@Test
	public void checkRows2() {
		int currentRow = 0;
		Grid g = new Grid();
		for (int r = 0; r < Grid.HEIGHT; r++) {
			if (r == 10) {
				currentRow = r;
				for (int c = 6; c <= 7; c++) {
					g.set(r, c, Color.RED);
				}
			} else {
				for (int c = 0; c < Grid.WIDTH; c++) {
					g.set(r, c, Color.RED);
				}
			}
		}
		System.out.println("Before\n" + g);
		g.checkRows();
		System.out.println("\nAfter\n" + g);
		// check the grid
		for (int r = 0; r < Grid.HEIGHT - 1; r++) {
			for (int c = 0; c < Grid.WIDTH; c++) {
				assertFalse(g.isSet(r, c));
			}
		}
		// bottom row
		for (int c = 0; c < Grid.WIDTH; c++) {
			if (c != 6 && c != 7) {
				assertFalse(g.isSet(Grid.HEIGHT - 1, c));
			} else {
				assertTrue(g.isSet(Grid.HEIGHT - 1, c));
			}
		}

	}
	@Test
	public void testRotateSquare() {
		Grid g = new Grid();
		Square c = new Square(g, 5, 5, Color.BLUE, true); // center square
		//assertTrue(g.isSet(5, 5));
		// rotate a square j squares away from the center
		for (int j = 1; j <= 4; j++) {
			// location: 1 square up from the bottom left corner
			Square s = new Square(g, c.getRow() + j - 1, c.getCol() - j,
					Color.YELLOW, true);
			int row = s.getRow();
			int col = s.getCol();
			for (int i = 1; i <= 4; i++) {
				assertTrue(s.canRotate(c));
				s.rotateAbout(c);
				int dr = c.getRow() - row;
				int dc = c.getCol() - col;
				row = c.getRow() - dc;
				col = c.getCol() + dr;
				assertTrue(s.getRow() == row);
				assertTrue(s.getCol() == col);
			}

			// place a square in the way
			for (int k = 1; k <= j; k++) {
				int cornerRow = row + ((k < j) ? 1 : 0);
				int cornerCol = col + ((k < j) ? j - k - 1 : 0);
				for (int i = 1; i <= 4; i++) {
					int dr = c.getRow() - cornerRow;
					int dc = c.getCol() - cornerCol;
					cornerRow = c.getRow() - dc;
					cornerCol = c.getCol() + dr;
					g.set(cornerRow, cornerCol, Color.BLUE);
					assertTrue(!s.canRotate(c));
					g.set(cornerRow, cornerCol, Grid.EMPTY);
					assertTrue(s.canRotate(c));
					s.rotateAbout(c);
					dr = c.getRow() - row;
					dc = c.getCol() - col;
					row = c.getRow() - dc;
					col = c.getCol() + dr;
					assertTrue(s.getRow() == row);
					assertTrue(s.getCol() == col);
				}
			}
		}
	}

}
