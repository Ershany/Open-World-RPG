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
	protected float rangedDamage;
	protected float speed;
	protected boolean invincible;
	protected String name;
	
	protected int width, height;
	protected int anim;
	
	protected boolean dying;
	
	protected Rectangle hitbox; //used for intersection of weapons and such (or picking up things) not for solid collision detection
	
	protected Tilemap currentTilemap;
	protected LevelState currentState;
	
	protected boolean moveUp, moveDown, moveRight, moveLeft;
	
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
		if(!invincible) {
			currentHealth -= damage;
			if(currentHealth < 0) currentHealth = 0;
		}
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
	public float getCurrentHealth() {
		return currentHealth;
	}
	public float getDamage() {
		return damage;
	}
	public float getRangedDamage() {
		return rangedDamage;
	}
	public float getSpeed() {
		return speed;
	}
	public Tilemap getTileMap() {
		return currentTilemap;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public boolean getInvincible() {
		return invincible;
	}
	public String getName() {
		return name;
	}
	public boolean getDying() {
		return dying;
	}
	public LevelState getCurrentState() {
		return currentState;
	}
	public boolean getMoveUp() {
		return moveUp;
	}
	public boolean getMoveDown() {
		return moveDown;
	}
	public boolean getMoveRight() {
		return moveRight;
	}
	public boolean getMoveLeft() {
		return moveLeft;
	}
}
