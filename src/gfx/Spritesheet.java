package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	//objects (we can keep them here or init them in the states we use them)
	//or just keep universal stuff like player and such and have rare
	//sprites get initialized in the states initialization 
	public static Spritesheet sheet1 = new Spritesheet("/sprites/tiles.bmp", 32);
	public static Spritesheet enemySheet = new Spritesheet("/sprites/enemies_1.png", 32);
	
	private String path;
	private int spriteSize;
	private int width, height;
	
	private BufferedImage sheet;
	
	
	public Spritesheet(String path, int spriteSize) {
		this.path = path;
		this.spriteSize = spriteSize;
		
		load();
	}
	
	private void load() {
		try {
			sheet = ImageIO.read(Spritesheet.class.getResourceAsStream(path));
			width = sheet.getWidth();
			height = sheet.getHeight();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//getters
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getSize() {
		return spriteSize;
	}
	public BufferedImage getImage() {
		return sheet;
	}
}
