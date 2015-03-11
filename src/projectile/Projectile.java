package projectile;

import java.awt.Color;
import java.awt.Graphics2D;

import tilemap.Tilemap;
import entity.Entity;
import gamestatemanager.LevelState;

public class Projectile extends Entity {

	private int ticksPassed;
	private int projectileLife;
	
	private float xa, ya;
	private double angle;
	
	private LevelState currentState;
	private Tilemap tilemap;
	
	public Projectile(float xOrig, float yOrig, float xDest, float yDest, float speed, int projectileLife, LevelState currentState) {
		super(xOrig, yOrig);
		this.projectileLife = projectileLife;
		
		angle = Math.atan2(xDest - x, yDest - y);
		xa = (float)Math.sin(angle) * speed;
		ya = (float)Math.cos(angle) * speed;
		
		this.currentState = currentState;
		tilemap = currentState.getTilemap();
	}
	
	public void update() {
		if(ticksPassed < projectileLife) ticksPassed++;
		else removed = true;
		
		x += xa;
		y += ya;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)x - tilemap.getXOffset(), (int)y - tilemap.getYOffset(), 10, 10);
	}
	
}
