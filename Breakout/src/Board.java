import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener, KeyListener {
	public static final double PADDLE_SPEED = 2;
	public static final double BRICKS_START_X = 120;
	public static final double BRICKS_START_Y = 80;
	public static final double BALL_SPEED = 1;
	public static final double ANG_FACTOR = 0.3;
	public static final double MASS_FACTOR = 2;
	public static final double MAX_SPEED = 2;
	Timer t;
	Paddle p;
	Ball b;
	String paddleImg = "images/paddle.png";
	String ballImg = "images/ball.png";
	String brick = "images/brick.png";
	Brick [][] bricks;
	int score;
	int live;
	boolean win;
	boolean loose;
	String message;
	Font messageFont;
	Font scoreFont;
	public Board(){
		init();
	}
	public void init(){
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		t = new Timer(2, this);
		p = new Paddle(paddleImg);
		b = new Ball(ballImg);
		bricks = new Brick[5][6];
		for( int i = 0; i < 5; i++){
			for(int j = 0; j < 6; j++ ){
				bricks[i][j] = new Brick(brick);
				bricks[i][j].setX(BRICKS_START_X + j * bricks[i][j].getWidht());
				bricks[i][j].setY(BRICKS_START_Y + i * bricks[i][j].getHeight());
				bricks[i][j].setImage(Brick.bricks[i]);
			}
		}
		score = 0;
		live = 3;
		win = false;
		loose = false;
		messageFont = new Font("Times New Roman", Font.BOLD, 24);
		scoreFont = new Font ("Arial", Font.PLAIN, 16) ;
		t.start();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Point botPoint = new Point((int)( b.getX()), (int) (b.getRect().getMaxY() + 1));
		Point topPoint = new Point((int)( b.getX()), (int) (b.getRect().getMinY() - 1));
		Point leftPoint = new Point((int)( b.getX() - 1), (int) (b.getRect().getMaxY()));
		Point rightPoint = new Point((int)( b.getX() + b.getWidht() + 1), (int) (b.getRect().getMaxY()));
		//drawing bricks
		p.move();
		
		
		if(b.isReady){
			if(b.getY() >= Main.HEIGHT) {
				live--;
				b.isReady = false;
				b.setX(p.getX());
				b.setY(b.START_Y);
				if(live == 0){
					loose = true;
				}
			}
			if(p.getRect().contains(botPoint)){
				b.setDY(-1);
				double newSpeed = b.dx+=(1 - ANG_FACTOR) * p.dx * MASS_FACTOR * 0.1 ;
				if(Math.abs(newSpeed) >= MAX_SPEED) {
					if(newSpeed < 0) newSpeed = - MAX_SPEED;
						else
					newSpeed = MAX_SPEED;
				}
				b.setDX(newSpeed);
			}
			win = true;
			for(int i=0; i< 5; i++){
				for(int j=0; j < 6; j++){
					if(!bricks[i][j].getDestroyed()){
						win = false;
						if(bricks[i][j].getRect().contains(topPoint) 
								|| bricks[i][j].getRect().contains(botPoint) ||
								bricks[i][j].getRect().contains(leftPoint) || 
								bricks[i][j].getRect().contains(rightPoint)){
							bricks[i][j].setDestroyed(true);
							score+=10;
							if(bricks[i][j].getRect().contains(topPoint) || bricks[i][j].getRect().contains(botPoint)){
								b.dy*=-1;
							} else {
								b.dx *= -1;
							}
						}
					}
				}
			}
			b.move();
			if(win){
				message = "WIN!";
				t.stop();
			}
			if(loose){
				message = "LOOSE!";
				t.stop(); 
			}
		} else {
			b.setX( (b.START_X + p.getX() - p.START_X));
		}
		repaint();
	}
	public void paint(Graphics g){
		//drawing paddle
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		g.drawImage(p.getImage(),(int)p.getX(), (int)p.getY(), (int)p.getWidht(), (int)p.getHeight(), null);
		g.drawImage(b.getImage(),(int)b.getX(),(int)b.getY(), (int)b.getWidht(), (int)b.getHeight(), null);
		//drawing bricks
		for(int i=0; i<5; i++){
			for(int j=0; j<6; j++){
				if(!bricks[i][j].getDestroyed())
					g.drawImage(bricks[i][j].getImage(), (int)bricks[i][j].getX(), (int)bricks[i][j].getY(), (int)bricks[i][j].getWidht(),(int) bricks[i][j].getHeight(),null);
			}
		}
		g.setColor(Color.BLACK);
		g.setFont(scoreFont);
		g.drawString("Scores: "+score, 650, 25);
		g.drawString("Lives: "+live, 650, 55);
		if(win || loose){
			g.setFont(messageFont);
			g.drawString(message, 160, 200);
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_SPACE && !b.isReady){
			b.isReady = true;
			b.setDX(1);
			b.setDY(-1);
		}
		if( k == KeyEvent.VK_RIGHT){
			p.setDX(PADDLE_SPEED);
		}
		if( k == KeyEvent.VK_LEFT){
			p.setDX(-PADDLE_SPEED);
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		p.setDX(0);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
