package projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

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
	
	private float damage;
	private int width, height;
	private Rectangle hitbox;
	
	//spawner for particles
	private ParticleSpawner particleSpawner;
	
	public Projectile(float xOrig, float yOrig, float xDest, float yDest, int width, int height, float speed, int projectileLife, float damage, LevelState currentState) {
		super(xOrig, yOrig);
		this.projectileLife = projectileLife;
		this.damage = damage;
		this.width = width;
		this.height = height;
		hitbox = new Rectangle(width, height);
		
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
			hitbox.x = (int)x; hitbox.y = (int)y;
		} else {
			particleSpawner.spawn(x, y, 10, 1.3f, Color.BLACK, 10);
			removed = true;
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)x - tilemap.getXOffset(), (int)y - tilemap.getYOffset(), width, height);
	}
	
	
	//getters
	public float getDamage() {
		return damage;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}
}
