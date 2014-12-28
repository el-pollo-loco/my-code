import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;


import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener, KeyListener{
	public static final int COLS = 10;
	public static final int ROWS = 20;
	public static final int CELL_SIZE = Tetris.WIDTH / COLS;
	private Graphics2D g = null;
	private Timer timer = null;
	private Shape curShape = null;
	private int[][] shape = null;
	private int[][] map = null;
	private boolean transform = false;
	private boolean k = false;
	private int[][] inv = null;
	private Random random;
	private int cur;
	private int next;
	private Vector<Integer> linesForRemove;
	private int score;
	boolean loose;
	public Board(){
		super();
		init();
		timer.start();
	}
	public void init(){
		loose = false;
		linesForRemove = new Vector<Integer>();
		random = new Random();
		cur = (int) (Math.random() * 6) ;
		next = (int) (Math.random() * 6) ;
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
		timer = new Timer(500, this);
		map = new int[ROWS][COLS]; 
		clearMap();
		setSize(Tetris.WIDTH, Tetris.HEIGHT);
		curShape = new Shape();
		curShape.setShape(cur);
		shape = curShape.getShape();
		inv = curShape.getInvShape();
		mapShape();
	}
	public void mapShape(){
		if(!transform){
			for(int i = 0 ; i < curShape.getHeight() ; i++){
				for(int j = 0; j < curShape.getWidth(); j++){
					if(map[curShape.getY() + i][curShape.getX() + j] != 2)
						map[curShape.getY() + i][curShape.getX() + j] = shape[i][j]; 
				}	
			}
		} else {
			
				for(int i = 0 ; i < curShape.getHeight() ; i++){
					for(int j = 0; j < curShape.getWidth(); j++){
						if(map[curShape.getY() + i][curShape.getX() + j] != 2)
							map[curShape.getY() + i][curShape.getX()+ j] = inv[i][j];
					}	
				}
		}
	}
	public void clearMap(){
		for(int i = 0; i < ROWS; i++){
			for(int j = 0; j < COLS; j++){
				if(map[i][j] == 2) continue; 
				map[i][j] = 0;
			}
		}
	}
	public void paint(Graphics g) {
		Font lbl = new Font("Serif", Font.BOLD, 24);
		Font looseFont = new Font("Serif", Font.BOLD, 32);
		g.setColor(Color.WHITE);
        g.fillRect(0, 0, Tetris.WIDTH + 150, Tetris.HEIGHT + 50);
        
        g.setColor(Color.BLACK);
        g.setFont(lbl);
        g.drawString("Scores",220, 30);
        
        g.drawString(score + "",245, 70);
        for(int i = 0; i <= COLS; i++){
        	g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, Tetris.HEIGHT);
        }
        for(int i = 0; i <= ROWS; i++){
        	g.drawLine(0, i * CELL_SIZE, Tetris.WIDTH, i * CELL_SIZE);
        }
        
        g.setColor(Color.BLACK);
        for(int i = 0; i < ROWS; i++ ){
        	for(int j = 0; j < COLS; j++){
        		if( map[i][j] == 0) continue;
        		g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        	}
        }
       
        for(int i = 0; i < 2; i++ ){
        	for(int j = 0; j < 4; j++){
        		if(  Shape.SHAPES[next][i][j] == 0) continue;
        		g.fillRect(j * CELL_SIZE + 220, i * CELL_SIZE + 150, CELL_SIZE, CELL_SIZE);
        	}
        }
        
        if(loose){
        	g.setColor(Color.RED);
        	g.setFont(looseFont);
        	g.drawString("LOOSE", 60, 220);
        	timer.stop();
        }
     }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
		if(curShape.tryMoveDown(map)){
			curShape.moveDown();
		}else{
			fixShape();
			checkForRemove();
			curShape = new Shape();
			cur = next;
			next = (int) (Math.random() * 6) ;
			curShape.setShape(cur);
			shape = curShape.getShape();
			inv = curShape.getInvShape();
			transform = false;
			k = false;
			if(checkForLoose()){
				loose = true;
			}
		}
		clearMap();
		mapShape();
	}
	public boolean checkForLoose(){
		for(int j=4; j < 8; j++){
			if(map[0][j] == 2) return true; 
		}
		return false;
	}
	public void fixShape(){
		for(int i=0; i < ROWS; i++){ 
			for(int j = 0; j < COLS; j++){
				if(map[i][j] == 1) map[i][j] = 2;
			}
		}
	}
	public void checkForRemove(){
		boolean isRemove;
		for (int i = 19; i >= 0; i-- ){
			isRemove = true;
			for(int j = 0; j < 10; j++ ){
				if(map[i][j] == 0) {
					isRemove = false;
					break;
				}
			}
			if(isRemove){
				linesForRemove.add(i);
				score+=10;
			}
		}
		
		boolean ok = false;
		while(!linesForRemove.isEmpty()){
			int num=linesForRemove.firstElement();
			System.out.println(num);
			linesForRemove.removeElementAt(0);
			for(int i = 0; i < linesForRemove.size(); i++){
				 linesForRemove.set(i, linesForRemove.get(i) + 1);
			}
			for(int i=num; i > 0 ; i--){
				ok = false;
				for(int j = 0; j < 10; j++ ){
					map[i][j] = map[i - 1][j];
					if(map[i][j] == 2) ok = true; 
				}
				if(!ok) break;
			}
			
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT){
			if((curShape.getX()+curShape.getWidth() ) == COLS ) return;
			if(map[curShape.getY()][curShape.getX() + curShape.getWidth() ] != 0) return;
			for(int i = curShape.getY(); i < curShape.getY() + curShape.getHeight(); i++){
				for(int j = curShape.getX(); j < curShape.getX() + curShape.getWidth(); j++){
					if(map[i][j] == 1 && map[i][j + 1] == 2) return;
				}
			}
			curShape.moveRight(); 
		}
		if(key == KeyEvent.VK_LEFT){
			if((curShape.getX() - 1) == -1) return;
			if(map[curShape.getY()][curShape.getX() - 1] != 0) return;
			for(int i = curShape.getY(); i < curShape.getY() + curShape.getHeight(); i++){
				for(int j = curShape.getX(); j < curShape.getX() + curShape.getWidth(); j++){
					if(map[i][j] == 1 && map[i][j - 1] == 2) return;
				}
			}
			curShape.moveLeft();
		}
		if(key == KeyEvent.VK_UP){
			transform = !transform;
			int newY = curShape.getY() - 1;
			int newX = curShape.getX() + 1;
			if( !transform ){
				newY = curShape.getY() + 1;
				newX = curShape.getX() - 1;
				
			}
			int width = curShape.getWidth();
			//check
			for(int i = 0; i < curShape.getWidth() ; i++){
				for(int j = 0; j < curShape.getHeight(); j++){
					if(newY + i < 0|| newX + j < 0 || newY + i > 19 || newX + j > 9|| map[newY + i][newX + j] == 2){
						transform = !transform;
						return;
					}
				}
			}
			curShape.topY = newY;
			curShape.topX = newX;
			curShape.botY = newY + curShape.getWidth() - 1;
			curShape.setWidth(curShape.getHeight());
			curShape.setHeight(width);
			
		}
		if( key == KeyEvent.VK_DOWN){
			timer.setDelay(50);
		}
		clearMap();
		mapShape();
		repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		timer.setDelay(500);
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
