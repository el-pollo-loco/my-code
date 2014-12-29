import java.awt.Image;


public class Paddle extends Sprite {
	public static final int PADDLE_WIDTH = 80;
	public static final int PADDLE_HEIGHT = 16;
	public static final int START_X = 320;
	public static final int START_Y = 480;
	public Paddle(String path) {
		super(path);
		width = PADDLE_WIDTH;
		height = PADDLE_HEIGHT;
		x = START_X;
		y = START_Y;
	}
	public void move(){
		if( (dx == Board.PADDLE_SPEED && x + width < Main.WIDTH) || (dx == -Board.PADDLE_SPEED && x > 0)){
			super.move();
		}
	}
}
