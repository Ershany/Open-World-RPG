package tilemap;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.GrassTile;
import tiles.StoneTile;
import tiles.Tile;

public class Tilemap {

	
	private BufferedImage map;
	private int width, height;
	private String path;
	private Tile[] tiles;
	private int[] pixels;
	
	private int xOffset; 
	private int yOffset;
	
	//will read an image for its pixel data
	public Tilemap(String path) {
		this.path = path;
		xOffset = 0;
		yOffset = 0;
		
		load();
	}
	
	private void load() {
		try {
			map = ImageIO.read(Tilemap.class.getResource(path));
			width = map.getWidth();
			height = map.getHeight();
			tiles = new Tile[width * height];
			pixels = map.getRGB(0, 0, width, height, null, 0, width);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//now lets load the tiles
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(pixels[x + y * width] == 0xFF008000) {
					tiles[x + y * width] = new GrassTile(x, y);
				}
				if(pixels[x + y * width] == 0xFF7F7F7F) {
					tiles[x + y * width] = new StoneTile(x, y);
				}
			}
		}
	}
	
	public void update() {
		for(int i = 0; i < tiles.length; i++) {
			tiles[i].update();
		}
		
		//temp for testing
		xOffset++;
		yOffset++;
	}
	
	public void render(Graphics2D g) {
		for(int i = 0; i < tiles.length; i++) {
			tiles[i].render(xOffset, yOffset, g);
		}
	}
	
	
	//getters
	public int getXOffset() {
		return xOffset;
	}
	public int getYOffset() {
		return yOffset;
	}
	//pass in pixel precision x and y
	public Tile getTile(int x, int y) {
		return tiles[(x / Tile.TILESIZE) + (y / Tile.TILESIZE) * width];
	}
	
	//setters
	public void setXOffset(int newOffset) {
		xOffset = newOffset;
	}
	public void setYOffset(int newOffset) {
		yOffset = newOffset;
	}
}
