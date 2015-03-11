package projectile;

import java.awt.Color;
import java.awt.Graphics2D;

import spawners.ParticleSpawner;
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
	
	//spawner for particles
	private ParticleSpawner particleSpawner;
	
	public Projectile(float xOrig, float yOrig, float xDest, float yDest, float speed, int projectileLife, LevelState currentState) {
		super(xOrig, yOrig);
		this.projectileLife = projectileLife;
		
		angle = Math.atan2(yDest - y, xDest - x);
		xa = (float)Math.cos(angle) * speed;
		ya = (float)Math.sin(angle) * speed;
		
		this.currentState = currentState;
		tilemap = currentState.getTilemap();
		
		particleSpawner = new ParticleSpawner(currentState);
	}
	
	public void update() {
		if(ticksPassed < projectileLife) ticksPassed++;
		else removed = true;
		
		//check for collision
		if(!tilemap.getTile((int)x, (int)y).getProjectileSolid()) {
			x += xa;
			y += ya;
		} else {
			particleSpawner.spawn(x, y, 20, 1.1f, Color.BLACK, 10);
			removed = true;
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)x - tilemap.getXOffset(), (int)y - tilemap.getYOffset(), 10, 10);
	}
	
}
