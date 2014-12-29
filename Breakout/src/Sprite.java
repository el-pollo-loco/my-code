import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class Sprite {
	BufferedImage image;
	double x;
	double y;
	double width;
	double height;
	double dx;
	double dy;
	public Sprite(String path){
		try {
			image = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setX(double x){
		this.x = x;
	}
	public void setY(double y){
		this.y = y;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public void setWidth(double width){
		this.width = width;
	}
	public void setHeight(double height){
		this.height = height;
	}
	public double getWidht(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	public Image getImage(){
		return image;
	}
	public Rectangle getRect(){
		return new Rectangle((int)x, (int)y, (int)width, (int)height);
	}
	public void move(){
		x+=dx;
		y+=dy;
	}
	public void setDX(double dx){
		this.dx = dx;
	}
	public void setDY(double dy){
		this.dy = dy;
	}
	public boolean intersects(Sprite s){
		return getRect().intersects(s.getRect());
	}
}
