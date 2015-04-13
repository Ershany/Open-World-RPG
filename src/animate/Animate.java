package animate;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Mob;


public abstract class Animate {

	protected BufferedImage currentSprite;
	protected Mob mob;
	
	public Animate(Mob mob) {
		this.mob = mob;
		
		init();
	}
	
	public abstract void init();
	public abstract void render(Graphics2D g);
	public abstract void update();
	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	
}
