import java.awt.image.BufferedImage;


public class Brick extends Sprite{
	public static final double WIDTH = 80;
	public static final double HEIGHT = 15;
	private boolean isDestroyed = false;
    public static BufferedImage[] bricks;
	public Brick(String path) {
		super(path);
		bricks = new BufferedImage[5];
		for(int i = 0; i < 5; i++){
			bricks[i] = ((BufferedImage) getImage()).getSubimage(i*35, 150, 35, 17);
		}
		width = WIDTH;
		height = HEIGHT;
	}
	public boolean getDestroyed(){
		return isDestroyed;
	}
	public void setDestroyed(boolean isDestroyed){
		this.isDestroyed = isDestroyed;
	}
	public void setImage(BufferedImage image){
		this.image = image;
	}
	
}
