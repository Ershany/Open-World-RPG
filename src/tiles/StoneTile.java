package tiles;

import gfx.Sprite;

import java.awt.Graphics2D;

public class StoneTile extends Tile {

	public StoneTile(int x, int y) {
		super(x, y, Sprite.stone.getImage());
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.drawImage(tileImage, (x * Tile.TILESIZE) - xOffset, (y * Tile.TILESIZE) - yOffset, null);
	}

}
