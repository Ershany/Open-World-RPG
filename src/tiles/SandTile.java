package tiles;

import gfx.Sprite;

import java.awt.Graphics2D;

public class SandTile extends Tile {

	public SandTile(int x, int y) {
		super(x, y, Sprite.sand.getImage());
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.drawImage(tileImage, (x << 5) - xOffset, (y << 5) - yOffset, null);
	}

}
