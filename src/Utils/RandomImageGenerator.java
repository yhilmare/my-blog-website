package Utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class RandomImageGenerator {
	
	private Color[] colors= {Color.WHITE, Color.CYAN, Color.YELLOW};
	private String info = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	private String tokenStr;
	public String getTokenStr() {
		return tokenStr;
	}
	public BufferedImage genetatorImage(int width, int height){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics photo = image.getGraphics();
		setBackGroundColor(photo, width, height);
		setBorderLine(photo, width, height);
		setRandomLine(photo, width, height);
		setRandomCharacter((Graphics2D)photo, width, height);
		return image;
	}
	
	private void setBackGroundColor(Graphics photo, int width, int height){
		int ran = new Random().nextInt(2);
		Color color = colors[ran];
		photo.setColor(color);
		photo.fillRect(0, 0, width, height);
	}
	
	private void setRandomLine(Graphics photo, int width, int height){
		
		for(int i = 0; i < 4; i++){
			int x1 = new Random().nextInt(width);
			int x2 = new Random().nextInt(width);
			int y1 = new Random().nextInt(height);
			int y2 = new Random().nextInt(height);
			photo.setColor(Color.blue);
			photo.drawLine(x1, y1, x2, y2);
		}
	}
	
	private void setBorderLine(Graphics photo, int width, int height){
		
		int ran = new Random().nextInt(2);
		Color color = colors[ran];
		photo.setColor(color);
		photo.drawRect(0, 0, width - 2, height - 2);
	}
	
	private void setRandomCharacter(Graphics2D photo, int width, int height){
		
		StringBuffer buffer = new StringBuffer();
		int x = width / 4;
		int y = height / 2 + 7;
		photo.setFont(new Font("ËÎÌו", Font.BOLD, 20));
		for(int i = 0; i < 4; i++){
			int random = new Random().nextInt(info.length());
			String msg = info.substring(random, random + 1);
			buffer.append(msg);
			photo.drawString(msg, x, y);
			double theta = (new Random().nextInt(5) / 180.0) * 3.1415926; 
			x += 20;
			photo.rotate(theta, x, y);
		}
		this.tokenStr = new String(buffer.toString());
	}
}
