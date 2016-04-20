package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

import game.Game;
import gamestatemanager.LevelState;
import projectile.Projectile;
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
	
	//boss methods
	public void projectileHit(Projectile p) {
			
	}

	public void hit(float damage) {
		if(!invincible) {
			currentHealth -= damage;
			if(currentHealth < 0) currentHealth = 0;
		}
	}
	
	private int decide;
	protected void setRandomMovement() {
		long seed = System.currentTimeMillis();
		
		// If you are playing multiplayer and you are the host, send the seed to the other client
		if(Game.hosting || !Game.multiplayer) {
			
		}
		
		Random random = new Random(seed);
		if(random.nextInt(200) == 0) {
			moveUp = false; moveDown = false; moveRight = false; moveLeft = false;
			decide = random.nextInt(16) + 1;
			if(decide % 2 == 0) return;
			else {
				switch(decide) {
				case 1: moveUp = true; break;
				case 3: moveDown = true; break;
				case 5: moveRight = true; break;
				case 7: moveLeft = true; break;
				case 9: moveUp = true; moveRight = true; break;
				case 11: moveUp = true; moveLeft = true; break;
				case 13: moveDown = true; moveRight = true; break;
				case 15: moveDown = true; moveLeft = true; break;
				}
			}
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
	
	//setters
	public void setHitBox(Rectangle hitbox) {
		this.hitbox = hitbox;
	}
	public void setXHitBox(int x) {
		hitbox.x = x;
	}
	public void setYHitBox(int y) {
		hitbox.y = y;
	}
	public void setMoveUp(boolean decide) {
		this.moveUp = decide;
	}
	public void setMoveDown(boolean decide) {
		this.moveDown = decide;
	}
	public void setMoveRight(boolean decide) {
		this.moveRight = decide;
	}
	public void setMoveLeft(boolean decide) {
		this.moveLeft = decide;
	}
}
