
public class Shape {
	enum Shapes {O, I, S, Z, L, J};
	public static final int START_X = 4;
	public static final int START_Y = 0;
	private int width;
	private int height;
	static int[] stateNums = {1, 2, 2, 2, 2, 2};
	public int maxState;
	public int curState = 1;
	static int[][] Oshape = {{1, 1, 0, 0}, 
					 		 {1, 1, 0, 0}};
	
	static int[][] Ishape = {{1, 1, 1, 1}, 
			 		  		 {0, 0, 0, 0}};
	
	static int[][] Sshape = {{0, 1, 1, 0}, 
	 		          		 {1, 1, 0, 0}};
	
	static int[][] Zshape = {{1, 1, 0, 0}, 
	 		          		 {0, 1, 1, 0}};
	
	static int[][] Lshape = {{0, 0, 1, 0}, 
	 		          		 {1, 1, 1, 0}};
	
	static int[][] Jshape = {{1, 0, 0, 0}, 
	 		          		 {1, 1, 1, 0}};
	
	static int[][] invI={{1,0},
						 {1,0},
						 {1,0},
						 {1,0}};
	
	static int[][] invS={{1,0},
						 {1,1},
						 {0,1},};
	
	static int[][] invZ={{0,1},
						 {1,1},
						 {1,0}};
	
	static int[][] invL={{1,0},
						 {1,0},
						 {1,1}};
	
	static int[][] invJ={{0,1},
						 {0,1},
						 {1,1}};
	
	public static final int[][][] inverse = {invI, invS, invZ, invL, invJ};
	public static final int[][][] SHAPES = {Oshape, Ishape, Sshape, Zshape, Lshape, Jshape};
	
	private int[][] shape;
	private int[][] inv;
	private int[][] sizes = {{2,2},{1,4},{2,3},{2,3},{2,3},{2,3}};
	public Shape(){
		shape = new int[2][4];
		inv = new int[4][2];
		topX = START_X;
		topY = START_Y;
		botY = topY + 1;
	}
	int topX;
	int topY;
	int botX;
	int botY;
	
	public int getX(){
		return topX;
	}
	public int getY(){
		return topY;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	public void setPosition(int x, int y){
		this.topX = x;
		this.topY = y;
	}
	public void moveDown(){
		topY++;
		botY++;
	}
	public void moveRight(){
		topX++;
	}
	public void moveLeft(){
		topX--;
	}
	public void setShape(int shapeNum){
		if(shapeNum == 1) botY--; //costyl
		height = sizes[shapeNum][0];
		width = sizes[shapeNum][1];
		//current state set from array of enum Shapes
		maxState = stateNums[shapeNum];
		for(int i = 0; i < height ; i++){
			for(int j = 0; j < width; j++){
				shape[i][j] = SHAPES[shapeNum][i][j];
			}
		}
		
		if(shapeNum != 0){
			for(int i = 0; i < width ; i++){
				for(int j = 0; j < height; j++){
					inv[i][j] = inverse[shapeNum - 1][i][j];
				}
			}
		}
	}
	public void setWidth(int width){
		this.width = width;
	}
	public void setHeight(int height){
		this.height = height;
	}
	public int[][] getInvShape(){
		return inv;
	}
	public int[][] getShape(){
		return shape;
	}
	public boolean tryMoveDown(int[][] map){
		if(botY == 19) return false;
		
		for(int i= topY; i < topY + height; i++){
			for(int j = topX; j < topX + width; j++ ){
				if(map[i][j] == 1 && map[i + 1][j] == 2) return false;
			}
		}
		return true;
	}
	
}
