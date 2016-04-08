package tiles;

import gfx.Sprite;

import java.awt.Graphics2D;

public class WaterTile extends Tile {
	
	private int anim;
	
	public WaterTile(int x, int y) {
		super(x, y, Sprite.water1.getImage());
		anim = 0;
		
		solid = true; 
	}

	public void update() {
		
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.drawImage(tileImage, (x << 5) - xOffset, (y << 5) - yOffset, null);
	}

	public String toString() {
		return "Water";
	}
	
}
