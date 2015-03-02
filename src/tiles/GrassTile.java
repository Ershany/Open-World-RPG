package tiles;

import gfx.Sprite;

import java.awt.Graphics2D;

public class GrassTile extends Tile {

	public GrassTile(int x, int y) {
		super(x, y, Sprite.grass.getImage());
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.drawImage(tileImage, (x * Tile.TILESIZE) - xOffset, (y * TILESIZE) - yOffset, null);
	}

}