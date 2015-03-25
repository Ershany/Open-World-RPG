package gfx;

import java.awt.image.BufferedImage;

public class Sprite {

	//objects (we can keep them here or init them in the states we use them)
	//or just keep universal stuff like player and such and have rare
	//sprites get initialized in the states initialization 
	public static Sprite grass = new Sprite(0, 0, Spritesheet.sheet1);
	public static Sprite stone = new Sprite(1, 0, Spritesheet.sheet1);
	public static Sprite sand = new Sprite(2, 0, Spritesheet.sheet1);
	public static Sprite redBrick = new Sprite(3, 0, Spritesheet.sheet1);
	
	public static Sprite water1 = new Sprite(0, 1, Spritesheet.sheet1);
	public static Sprite water2 = new Sprite(1, 1, Spritesheet.sheet1);
	public static Sprite water3 = new Sprite(2, 1, Spritesheet.sheet1);
	
	private Spritesheet sheet;
	
	private BufferedImage sprite;
	
	private int x, y;
	
	public Sprite(int x, int y, Spritesheet sheet) {
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		
		load();
	}
	
	private void load() {
		sprite = sheet.getImage().getSubimage(x * sheet.getSize(), y * sheet.getSize(), sheet.getSize(), sheet.getSize());
	}
	
	//getters
	public BufferedImage getImage() {
		return sprite;
	}
}
