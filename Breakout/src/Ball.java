
public class Ball extends Sprite{
	public static final double START_X = 345;
	public static final double START_Y = Paddle.START_Y - 15 -1;
	public static final double BALL_SIZE = 15;
	public boolean isReady = false;
	public Ball(String path) {
		super(path);
		x = START_X;
		y = START_Y;
		width = BALL_SIZE;
		height = BALL_SIZE;
	}	
	public void move(){
		if(x + width >= Main.WIDTH || x <= 0 ){
			dx *= -1;
		}
		if( y <= 0){
			dy *= -1;
		}
		super.move();
	}
}
