package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	//objects (we can keep them here or init them in the states we use them)
	//or just keep universal stuff like player and such and have rare
	//sprites get initialized in the states initialization 
	
	//tiles
	public static Sprite grass = new Sprite(0, 0, Spritesheet.sheet1);
	public static Sprite stone = new Sprite(1, 0, Spritesheet.sheet1);
	public static Sprite sand = new Sprite(2, 0, Spritesheet.sheet1);
	public static Sprite redBrick = new Sprite(3, 0, Spritesheet.sheet1);
	public static Sprite woodFloor = new Sprite(4, 0, Spritesheet.sheet1);
	
	public static Sprite water1 = new Sprite(0, 1, Spritesheet.sheet1);
	public static Sprite water2 = new Sprite(1, 1, Spritesheet.sheet1);
	public static Sprite water3 = new Sprite(2, 1, Spritesheet.sheet1);
	
	public static Sprite roof1 = new Sprite(0, 2, Spritesheet.sheet1);
	public static Sprite wall1 = new Sprite(1, 2, Spritesheet.sheet1);
	public static Sprite door1 = new Sprite(2, 2, Spritesheet.sheet1);
	
	public static Sprite rockHillTop = new Sprite(0, 3, Spritesheet.sheet1);
	public static Sprite rockHill = new Sprite(1, 3, Spritesheet.sheet1);
	public static Sprite rockHillBottom = new Sprite(2, 3, Spritesheet.sheet1);
	
	//HUD
	public static Sprite healthHUD = new Sprite("/hudStuff/healthBar.bmp");
	public static Sprite minimapOutline = new Sprite("/hudStuff/minimapOutline.bmp");
	
	//portraits
	public static Sprite playerPortrait = new Sprite("/hudStuff/portraits/playerPortrait.bmp");
	public static Sprite slimePortrait = new Sprite("/hudStuff/portraits/slimePortrait.bmp");
	
	//slime
	public static Sprite slime1 = new Sprite(0, 0, Spritesheet.enemySheet);
	public static Sprite slime2 = new Sprite(1, 0, Spritesheet.enemySheet);
	public static Sprite slime3 = new Sprite(2, 0, Spritesheet.enemySheet);
	public static Sprite deathSlime1 = new Sprite(3, 0, Spritesheet.enemySheet);
	public static Sprite deathSlime2 = new Sprite(4, 0, Spritesheet.enemySheet);
	public static Sprite deathSlime3 = new Sprite(5, 0, Spritesheet.enemySheet);
	
	private Spritesheet sheet;
	
	private BufferedImage sprite;
	
	private String path;
	private int x, y;
	
	public Sprite(int x, int y, Spritesheet sheet) {
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		
		load();
	}
	
	public Sprite(String path) {
		try {
			sprite = ImageIO.read(Sprite.class.getResourceAsStream(path));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void load() {
		sprite = sheet.getImage().getSubimage(x * sheet.getSize(), y * sheet.getSize(), sheet.getSize(), sheet.getSize());
	}
	
	//getters
	public BufferedImage getImage() {
		return sprite;
	}
}
