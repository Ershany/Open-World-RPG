package tilemap;

import game.Game;
import gfx.Sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import tiles.InterchangeableDoorTile;
import tiles.InterchangeableFloorTile;
import tiles.InterchangeableHillTile;
import tiles.InterchangeableRoofTile;
import tiles.InterchangeableWallTile;
import tiles.NullTile;
import tiles.Tile;
import tiles.WaterTile;

public class Tilemap {

	private BufferedImage map;
	private int width, height;
	private String path;
	private Tile[] tiles;
	private int[] pixels;

	private int xOffset;
	private int yOffset;

	// will read an image for its pixel data
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		// now lets load the tiles
		int loc;
		int colorCode;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
			 loc = x + y * width;
				colorCode = pixels[loc];
				Tile toPlace = null;
				switch (colorCode) {
				case 0xFF008000:
					toPlace = new InterchangeableFloorTile(x, y, Sprite.grass.getImage(), "Grass");
					break;
				case 0xFFFF884F:
					toPlace = new InterchangeableFloorTile(x, y, Sprite.dirt.getImage());
					break;
				case 0xFF7F7F7F: 
					toPlace = new InterchangeableFloorTile(x, y, Sprite.stoneFloor.getImage());
					break;
				case 0xFF00A2E8:
					toPlace = new WaterTile(x, y);
					break;
				case 0xFFE0E040:
					toPlace = new InterchangeableFloorTile(x, y, Sprite.sand.getImage());
					break;
				case 0xFF802600:
					toPlace = new InterchangeableWallTile(x, y, Sprite.redBrick.getImage());
					break;
				case 0xFF512000:
					toPlace = new InterchangeableRoofTile(x, y, Sprite.roof1.getImage());
					break;
				case 0xFF893700:
					toPlace = new InterchangeableWallTile(x, y, Sprite.wall1.getImage());
					break;
				case 0xFFD35400:
					if(getTile((x << 5) - 32, (y << 5)).getType().equals("Rock") || getTile((x << 5), (y << 5) - 32).getType().equals("Rock")) {
						toPlace = new InterchangeableDoorTile(x, y, Sprite.rockCave.getImage());
					} else if(getTile((x << 5) - 32, (y << 5)).getType().equals("Dungeon Rock") || getTile((x << 5), (y << 5) - 32).getType().equals("Dungeon Rock")) {
						toPlace = new InterchangeableDoorTile(x, y, Sprite.dungeonCave.getImage());
					} else {
						toPlace = new InterchangeableDoorTile(x, y, Sprite.door1.getImage());
					}
					break;
				case 0xFFD89A70:
					toPlace = new InterchangeableFloorTile(x, y, Sprite.woodFloor.getImage());
					break;
				case 0xFF682900:
					toPlace = new InterchangeableHillTile(x, y, Sprite.rockHill.getImage(), "Rock");
					break;
				case 0xFF685900:
					toPlace = new InterchangeableHillTile(x, y, Sprite.rockHillTop.getImage(), "Rock");
					break;
				case 0xFF687C00:
					toPlace = new InterchangeableHillTile(x, y, Sprite.rockHillBottom.getImage(), "Rock");
					break;
				//dungeon tiles
				case 0xFFC4673C: 
					toPlace = new InterchangeableFloorTile(x, y, Sprite.dungeonDirt.getImage());
					break;
				case 0xFF3D3D3D:
					toPlace = new InterchangeableWallTile(x, y, Sprite.dungeonWall.getImage());
					break;
				case 0xFF260E00:
					toPlace = new InterchangeableHillTile(x, y, Sprite.dungeonRock.getImage(), "Dungeon Rock");
					break;
				default:
					toPlace = new NullTile(x, y, null);
					break;
				}
				tiles[loc] = toPlace;
			}
		}
	}

	public void update() {
		for (int i = 0; i < tiles.length; i++) {
			tiles[i].update();
		}
	}

	public void render(Graphics2D g) {
		// optimized rendering
		// it does not iterate through the whole array, instead it closer to
		// your position
		// it also will not render anything off of the screen
		for (int i = (xOffset / Tile.TILESIZE) + (yOffset / Tile.TILESIZE)
				* width; i < ((xOffset + Game.WIDTH + 50) / Tile.TILESIZE + (yOffset
				+ Game.HEIGHT + 50)
				/ Tile.TILESIZE * width); i++) {
			try {
				if (tiles[i].getX() * Tile.TILESIZE < xOffset - 32)
					continue;
				if (tiles[i].getX() * Tile.TILESIZE > xOffset + Game.WIDTH + 0)
					continue;
				if (tiles[i].getY() * Tile.TILESIZE < yOffset - 32)
					continue;
				if (tiles[i].getY() * Tile.TILESIZE > yOffset + Game.HEIGHT
						+ 0)
					continue;
			} catch (ArrayIndexOutOfBoundsException e) {
				continue;
			}

			// if it made it to this point, render the tile as it is on the
			// screen
			tiles[i].render(xOffset, yOffset, g);
		}
	}
	
	//tile precision
	public void changeTile(int x, int y, Tile tile) {
		tiles[x + y * width] = tile;
	}

	// getters
	public int getXOffset() {
		return xOffset;
	}
	public int getYOffset() {
		return yOffset;
	}
	public int[] getPixels() {
		return pixels;
	}
	public Tile[] getTiles() {
		return tiles;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}

	// pass in pixel precision x and y
	public Tile getTile(int x, int y) {
		return tiles[(x / Tile.TILESIZE) + (y / Tile.TILESIZE) * width];
	}

	// setters
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
