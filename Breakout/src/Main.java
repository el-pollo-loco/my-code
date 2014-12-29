import javax.swing.JFrame;


public class Main {

	/**
	 * @param args
	 */
	public static final int WIDTH = 750;
	public static final int HEIGHT = 550;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame("Breakout");
		window.setSize(WIDTH, HEIGHT);
		Board board =  new Board();
		window.setContentPane(board);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
