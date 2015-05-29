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
	
	public static Spritesheet playerSheet = new Spritesheet("/sprites/player/player.png", 32, 48);
	
	public static Spritesheet kingSheet = new Spritesheet("/sprites/king/king.png", 32, 48);
	
	public static Spritesheet wisemanSheet = new Spritesheet("/sprites/wiseman/wiseman.png", 32, 48);
	
	public static Spritesheet knightSheet = new Spritesheet("/sprites/knight/knight.png", 32, 32);
	public static Spritesheet knightDeathSheet = new Spritesheet("/sprites/knight/knightDeath.png", 64, 64);
	
	//boss list
	public static Spritesheet kronosSheet = new Spritesheet("/sprites/boss/Kronos/Kronos.png", 96, 48);
	
	private String path;
	private int width, height;
	private int spriteWidth, spriteHeight;
	
	private BufferedImage sheet;
	
	public Spritesheet(String path, int spriteSize) {
		this.path = path;
		this.spriteWidth = spriteSize;
		this.spriteHeight = spriteSize;
		
		load();
	}
	
	public Spritesheet(String path, int spriteWidth, int spriteHeight) {
		this.path = path;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		
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
	public int getSpriteWidth() {
		return spriteWidth;
	}
	public int getSpriteHeight() {
		return spriteHeight;
	}
	public BufferedImage getImage() {
		return sheet;
	}
}
