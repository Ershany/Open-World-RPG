package tiles;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Tile {

	//tile size
	public static final int TILESIZE = 32;
	
	protected boolean solid;
	protected boolean projectileSolid;
	protected String type = "";
	
	protected BufferedImage tileImage;
	protected int x, y;
	
	public Tile(int x, int y, BufferedImage tileImage) {
		this.x = x;
		this.y = y;
		this.tileImage = tileImage;
	}
	//used for interchangeable tiles, so we know what kind of tile it is (used for spawning mobs like how slimes only spawn on grass tiles)
	public Tile(int x, int y, BufferedImage tileImage, String type) {
		this.x = x;
		this.y = y;
		this.tileImage = tileImage;
		this.type = type;
	}
	
	public abstract void update();
	
	public abstract void render(int xOffset, int yOffset, Graphics2D g);
	
	//getters
	public BufferedImage getTileImage() {
		return tileImage;
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public boolean getSolid() {
		return solid;
	}
	public boolean getProjectileSolid() {
		return projectileSolid;
	}
	public String getType() {
		return type;
	}
}
