package tiles;

import gfx.Sprite;

import java.awt.Graphics2D;

public class WaterTile extends Tile {
	
	private int anim;
	
	public WaterTile(int x, int y) {
		super(x, y, Sprite.water1.getImage());
		anim = 0;
	}

	public void update() {
		anim++;
		if(anim % 720 == 0) {
			tileImage = Sprite.water1.getImage();
			
			//reset anim
			anim = 0;
		}
		else if(anim % 480 == 0) {
			tileImage = Sprite.water2.getImage();
		}
		else if(anim % 240 == 0) {
			tileImage = Sprite.water3.getImage();
		}
	}

	@Override
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.drawImage(tileImage, (x << 5) - xOffset, (y << 5) - yOffset, null);
	}

}
