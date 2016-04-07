package projectile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import game.Game;
import gamestatemanager.LevelState;
import spawners.ParticleSpawner;
import tilemap.Tilemap;
import util.AngleMaster;

public class Projectile extends Entity {

	private int ticksPassed;
	private int projectileLife;
	
	private float xa, ya;
	private float speed;
	private double angle;
	
	private LevelState currentState;
	private Tilemap tilemap;
	
	private float damage;
	private int width, height;
	private Rectangle hitbox;
	
	private BufferedImage image;
	
	//spawner for particles
	private ParticleSpawner particleSpawner;
	
	// networkProjectile is a boolean that you set to true if you want it to be sent accross the network
	public Projectile(float xOrig, float yOrig, float xDest, float yDest, int width, int height, float speed, int projectileLife, float damage, LevelState currentState, boolean networkProjectile) {
		super(xOrig, yOrig);
		this.projectileLife = projectileLife;
		this.damage = damage;
		this.width = width;
		this.height = height;
		this.speed = speed;
		hitbox = new Rectangle(width, height);
		
		angle = AngleMaster.calculateAngle((int)xOrig, (int)yOrig, (int)xDest, (int)yDest);
		xa = (float)Math.cos(angle) * speed;
		ya = (float)Math.sin(angle) * speed;
		
		this.currentState = currentState;
		tilemap = currentState.getTilemap();
		
		particleSpawner = new ParticleSpawner(currentState);
		
		// Send data across server (check if player is host or server)
		if(networkProjectile && Game.multiplayer && Game.hosting) {
			currentState.getGSM().server.sendData(("projectile-" + xOrig + "-" + yOrig + "-" + xDest + "-" + yDest + "-" + width + "-" + height + "-" + speed + "-" + projectileLife + "-" + currentState.levelName).getBytes());
		} else if(networkProjectile && Game.multiplayer) {
			currentState.getGSM().client.sendData(("projectile-" + xOrig + "-" + yOrig + "-" + xDest + "-" + yDest + "-" + width + "-" + height + "-" + speed + "-" + projectileLife + "-" + currentState.levelName).getBytes());
		}
	}
	
	public Projectile(float xOrig, float yOrig, float xDest, float yDest, float speed, int projectileLife, float damage, LevelState currentState, BufferedImage image) {
		super(xOrig, yOrig);
		this.projectileLife = projectileLife;
		this.damage = damage;
		this.speed = speed;
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		hitbox = new Rectangle(width, height);
		
		angle = AngleMaster.calculateAngle((int)xOrig, (int)yOrig, (int)xDest, (int)yDest);
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
			particleSpawner.spawn(x, y, 10, 1.3f, Color.WHITE, 10);
			removed = true;
		}
	}
	
	public void resetDirection(double angle) {
		xa = (float)Math.cos(angle) * speed;
		ya = (float)Math.sin(angle) * speed;
	}
	
	public void render(Graphics2D g) {
		if(image == null) {
			g.setColor(Color.WHITE);
			g.fillRect((int)x - tilemap.getXOffset(), (int)y - tilemap.getYOffset(), width, height);
		} else {
			g.drawImage(image, (int)x - tilemap.getXOffset(), (int)y - tilemap.getYOffset(), null);
		}
	}
	
	
	//getters
	public float getDamage() {
		return damage;
	}
	public double getAngle() {
		return angle;
	}
	public Rectangle getHitbox() {
		return hitbox;
	}	
}
