package tilemap;

import game.Game;
import input.MouseMaster;

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
	}
	
	public void render(Graphics2D g) {
		//basic rendering
		//for(int i = 0; i < tiles.length; i++) {
			//tiles[i].render(xOffset, yOffset, g);
		//}
		
		
		//optimized rendering
		//it does not iterate through the whole array, instead it closer to your position
		//it also will not render anything off of the screen
		for(int i = (xOffset / Tile.TILESIZE) + (yOffset / Tile.TILESIZE) * width; 
			i < ((xOffset + Game.WIDTH + 50) / Tile.TILESIZE + (yOffset + Game.HEIGHT + 50) / Tile.TILESIZE * width);
			i++) {
				try {
				if(tiles[i].getX() * Tile.TILESIZE < xOffset - 50) continue;
				if(tiles[i].getX() * Tile.TILESIZE > xOffset + Game.WIDTH + 50) continue;
				if(tiles[i].getY() * Tile.TILESIZE < yOffset - 50) continue;
				if(tiles[i].getY() * Tile.TILESIZE > yOffset + Game.HEIGHT + 50) continue;
				} catch(ArrayIndexOutOfBoundsException e) {
					continue;
				}
				
				//if it made it to this point, render the tile as it is on the screen
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
	public void addOffset(int xOffset, int yOffset) {
		this.xOffset += xOffset;
		this.yOffset += yOffset;
	}
}
