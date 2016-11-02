import java.awt.Graphics;

public interface Piece {
	public static final int PIECE_COUNT = 4;
	public void draw(Graphics g);
	public void rotate();
}
