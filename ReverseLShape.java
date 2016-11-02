import java.awt.Color;
/**
 * An ReverseL-Shape piece in the Tetris Game.
 * 
 * This piece is made up of 4 squares in the following configuration:
 * 
 *    Sq<br>
 *    Sq<br>
 *    Sq<br>
 * Sq Sq<br>
 * 
 * The game piece "floats above" the Grid. The (row, col) coordinates are the
 * location of the middle Square on the side within the Grid
 * 
 * @author MO-Productions
 */

public class ReverseLShape extends AbstractPiece implements Piece {
	/**
	 * Creates an S-Shape piece. See class description for actual location of r
	 * and c
	 * 
	 * @param r
	 *            row location for this piece
	 * @param c
	 *            column location for this piece
	 * @param g
	 *            the grid for this game piece
	 * 
	 */
	public ReverseLShape(int r, int c, Grid g) {
		grid = g;
		square = new Square[PIECE_COUNT];
		ableToMove = true;

		// Create the squares
		// square[1] is center
		square[0] = new Square(g, r - 1, c, Color.BLUE, true);
		square[1] = new Square(g, r, c, Color.BLUE, true);
		square[2] = new Square(g, r + 1, c, Color.BLUE, true);
		square[3] = new Square(g, r + 1, c - 1, Color.BLUE, true);
	}


}

