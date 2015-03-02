package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Tile {

	//tile size
	public static final int TILESIZE = 32;
	
	protected boolean solid;
	protected BufferedImage tileImage;
	protected int x, y;
	
	public Tile(int x, int y, BufferedImage tileImage) {
		this.x = x;
		this.y = y;
		this.tileImage = tileImage;
	}
	
	public abstract void update();
	
	public abstract void render(int xOffset, int yOffset, Graphics2D g);
	
	//getters
	public BufferedImage getTileImage() {
		return tileImage;
	}
	
}
