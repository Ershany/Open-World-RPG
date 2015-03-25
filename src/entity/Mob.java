package entity;

import gamestatemanager.GameState;
import gamestatemanager.LevelState;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import tilemap.Tilemap;

public abstract class Mob extends Entity {

	//your level will influence stats. Some mobs more so then others... Example:
	
	//slime level 4:
	//health = level;
	//damage = level / 2;
	
	//guard level 4:
	//health = level * 4;
	//damage = level * 2;
	
	//statistics
	protected int level;
	protected float health; 
	protected float currentHealth; 
	protected float damage;
	protected float speed;
	
	protected int width, height;
	protected int anim;
	
	protected Rectangle hitbox; //used for intersection of weapons and such (or picking up things) not for solid collision detection
	
	protected Tilemap currentTilemap;
	protected LevelState currentState;
	
	public Mob(float x, float y, int level, LevelState currentState, Tilemap currentTilemap) {
		super(x, y);
		this.level = level;
		this.currentState = currentState;
		this.currentTilemap = currentTilemap;
		anim = 0;
		
		init();
	}
	
	public abstract void init(); //initialize stats, hitbox, and more
	public abstract void update();
	public abstract void render(Graphics2D g);

	public void hit(float damage) {
		currentHealth -= damage;
	}
	
	//getters
	public Rectangle getHitbox() {
		return hitbox;
	}
	public int getLevel() {
		return level;
	}
	public float getHealth() {
		return health;
	}
	public float getDamage() {
		return damage;
	}
	public float getSpeed() {
		return speed;
	}
	public Tilemap getTileMap() {
		return currentTilemap;
	}
}
