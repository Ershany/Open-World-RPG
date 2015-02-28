package gfx;

import java.awt.image.BufferedImage;

public class Sprite {

	//objects (we can keep them here or init them in the states we use them)
	//or just keep universal stuff like player and such and have rare
	//sprites get initialized in the states initialization 
	public static Sprite sprite1 = new Sprite(0, 0, Spritesheet.sheet1);
	public static Sprite sprite2 = new Sprite(1, 0, Spritesheet.sheet1);
	public static Sprite sprite3 = new Sprite(0, 1, Spritesheet.sheet1);
	public static Sprite sprite4 = new Sprite(1, 1, Spritesheet.sheet1);
	
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
