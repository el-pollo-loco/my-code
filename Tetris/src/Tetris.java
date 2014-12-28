import javax.swing.JFrame;


public class Tetris{
	/**
	 * @param args
	 */
	public static final int WIDTH = 200;
	public static final int HEIGHT = 400;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame("Tetris");
		window.setSize(WIDTH + 15, HEIGHT + 15);
		Board board = new Board();
		window.setContentPane(board);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
