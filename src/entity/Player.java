package entity;

import game.Game;
import gamestatemanager.LevelState;
import gfx.Sprite;
import hud.HUD;
import input.MouseMaster;
import io.FileIO;
import io.LevelData;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import projectile.Projectile;
import spawners.ParticleSpawner;
import tilemap.Tilemap;
import animate.PlayerAnimate;

public class Player extends Mob {

	//TODO:
	//updateStats() method needs to be complete when the inventory is finished
	
	
	//to be added in the future
	//private Inventory inventory;
	public HUD hud;
	private Mob focusedMob;
	
	//xp stats
	public static final int MAX_LEVEL = 10;
	private int xp;
	private int currentXp;
	
	private float projectileSpeed;
	
	private boolean upHeld, downHeld, rightHeld, leftHeld;
	
	private boolean shouldMelee;
	private boolean shouldShoot;
	private boolean rangedForm; 
	
	//cooldowns
	private int shootCooldown = 120; //it should be: shootCooldown = inventory.getRangedWeaponSpeed();
	private int currentShootCooldown = 0;
	
	private int invincibleCooldown = 60;
	private int currentInvincibleCooldown = 0;
	
	private int meleeCooldown = 60; //it should be: meleeCooldown = inventory.getWeaponSpeed();
	private int currentMeleeCooldown = 0;
	
	//melee hit box size (how big your melee is)
	private int meleeBase = 48;
	private int meleeHeight = 32;
	
	//melee display marker info
	private int markerX = 0; 
	private int markerY = 0;
	private BufferedImage markerImage;
	private boolean displayMarker;
	private int displayTime = 10;
	
	private float[] xVals, yVals;
	private PlayerAnimate playerAnim;
	
	//serialization items
	private LevelData levelData;
	
	
	public Player(int x, int y,  LevelState state, Tilemap tilemap) {
		super(x, y, 10, state, tilemap);
	}

	@Override
	public void init() {
		//get the levelData and if it exists, set the info and if not start fresh.
		levelData = FileIO.loadLevelData();
		if(levelData == null) {
			level = 1;
			xp = 100 + (100 * level);
			currentXp = 0;
		}
		else {
			level = levelData.level;
			xp = 100 + (100 * level);
			currentXp = levelData.xp;
			levelData.initPlayer(this);
		}
		
		health = (level * 5) + 10; //it should be:    health = (level * 5) + inventory.getArmor();
		currentHealth = health;
		damage = level * 3;  //it should be:   damage = (level * 3) + inventory.getWeapon().getDamage();
		rangedDamage = level * 2;
		projectileSpeed = 14.2f; //it should be: projectileSpeed = inventory.getWeapon().getProjectileSpeed();
		speed = 2.9f;
		
		width = 32;
		height = 48;
		
		shouldMelee = true;
		shouldShoot = true;
		
		name = "Player Name";
		
		hitbox = new Rectangle(width, height);
		
		
		xVals = new float[5];
		yVals = new float[5];
		
		for (int i = 0; i<= xVals.length-1; i++) {
			xVals [i] = x + width*((i+1)%2);			
		}
		
		yVals [0] = y;
		yVals [1] = y + height;
		yVals [2] = y + height;
		yVals [3] = y + (height / 2);
		yVals [4] = y + (height / 2);
		
		hud = new HUD(this);
		playerAnim = new PlayerAnimate(this);
		
		//if levelData was not initialized/loaded then make one
		if(levelData == null) {
			levelData = new LevelData(this);
		}
	}

	@Override	
	public void update() {
		//movement
		checkMovement();
		
		//melee
		checkMelee();
		
		//shooting
		checkShooting();
		
		//check xp
		checkXp();
		
		//check for focused mob
		checkFocus();
		
		//update the focused mob
		updateFocus();
		
		//check form
		checkForm();
		
		//cooldowns
		checkCooldowns();
		
		//update the stats of your player
		updateStats();
		
		//check death
		checkDeath();
		
		//set offsets
		updateOffset();
		
		//update player anim
		playerAnim.update();
		
		//update HUD
		hud.update(); 
		
		//update the levelData (used for serialization)
		levelData.update();
	}

	private boolean flicker;
	@Override
	public void render(Graphics2D g) {
		if(invincible) {
			if(flicker) {
				flicker = false;
			} else {
				drawPlayer(g);
				flicker = true;
			}
		} else {
			//render player
			drawPlayer(g);
		}
		
		//render reload bar
		drawReloadBar(g);
		
		//render HUD
		hud.render(g);
	}
	
	private void drawPlayer(Graphics2D g) {
		playerAnim.render(g);
		
		renderMarker(g);
	}
	
	private void renderMarker(Graphics2D g) {
		//if we shouldn't display the marker get out of the method
		if(!displayMarker) return;
		
		g.drawImage(markerImage, markerX - currentTilemap.getXOffset(), markerY - currentTilemap.getYOffset(), null);
		displayTime--;
		if(displayTime == 0) {
			displayTime = 10;
			displayMarker = false;
		}
	}
	
	private void drawReloadBar(Graphics2D g) {
		//firstly check if we are in rangedForm to render the bar and if it is on cooldown
		if(!rangedForm || currentShootCooldown <= 0) return;
		
		g.setColor(Color.RED);
		g.fillRect(MouseMaster.getMouseX() + 30, MouseMaster.getMouseY(), 3, (int)(0.25 * (shootCooldown - currentShootCooldown)));
	}
	
	private void move(float testSpeedX, float testSpeedY) {
		if (currentTilemap.getTile((int) (x +  testSpeedX), (int)(y + testSpeedY)).getSolid()) {
			return;
		}
		for (int i = 0; i<= xVals.length-1; i++) {
			if (currentTilemap.getTile((int) (xVals[i] +  testSpeedX), (int)(yVals[i] + testSpeedY)).getSolid()) {
				return;
			}
		}

		x += testSpeedX;
		y += testSpeedY;
		for (int i = 0; i<= xVals.length-1; i++) {
				xVals[i]  += testSpeedX;
				yVals[i]  += testSpeedY;	
		}
		hitbox.x = (int)x; hitbox.y = (int)y;
	}
	
	private void checkMovement() {
		//TODO : Fix diag
		if (upHeld) {
			move(0,-speed);
		}
		if (downHeld) {
			move(0,speed);
		}
		if (leftHeld) {
			move(-speed,0);
		}
		if (rightHeld) {
			move(speed,0);
		}
		
	}
	
	private void updateOffset() {
		currentTilemap.setXOffset((int)x - (Game.WIDTH / 2) + (width / 2));
		currentTilemap.setYOffset((int)y - (Game.HEIGHT / 2) + (height / 2));
	}
	
	private void checkShooting() {
		//firstly ensure we are in range form, if not get out of this method
		if(!rangedForm) return;
		
		if(MouseMaster.getMouseB() == 1 && shouldShoot) {
			//pass in the xDest and yDest with the xOffsets and yOffsets
			currentState.addProjectile(new Projectile(x, y, MouseMaster.getMouseX() + currentState.getTilemap().getXOffset(), MouseMaster.getMouseY() + currentState.getTilemap().getYOffset(), 3, 3, projectileSpeed, 240, rangedDamage, currentState));
			shouldShoot = false;
		}
	}
	
	private int xDest; private int yDest;
	private int xDiff; private int yDiff;
	private Rectangle meleeSwing = new Rectangle();
	private void checkMelee() {
		//firstly ensure we are in melee form, if not get out of this method
		if(rangedForm) return;
		
		if(MouseMaster.getMouseB() == 1 && shouldMelee) {
			xDest = MouseMaster.getMouseX() + currentTilemap.getXOffset();
			yDest = MouseMaster.getMouseY() + currentTilemap.getYOffset();
			xDiff = xDest - ((int)x + (width / 2));
			yDiff = yDest - ((int)y + (height / 2));
			
			if(Math.abs(xDiff) > Math.abs(yDiff)) { //left or right attack
				if(xDiff > 0) { //right attack
					meleeSwing.x = (int)x + meleeHeight; meleeSwing.y = (int)y;
					meleeSwing.width = meleeHeight; meleeSwing.height = meleeBase;
				} 
				else { //left attack
					meleeSwing.x = (int)x - meleeHeight; meleeSwing.y = (int)y;
					meleeSwing.width = meleeHeight; meleeSwing.height = meleeBase;
				}
				//set marker info
				markerX = meleeSwing.x;
				markerY = meleeSwing.y;
				markerImage = Sprite.playerHorizontalSwing.getImage(); //set the marker image 
			}
			else { //up or down attack
				if(yDiff > 0) { //down attack
					meleeSwing.x = (int)x - (meleeHeight / 4); meleeSwing.y = (int)y + meleeBase;
					meleeSwing.width = meleeBase; meleeSwing.height = meleeHeight;
				}
				else { //up attack
					meleeSwing.x = (int)x - (meleeHeight / 4); meleeSwing.y = (int)y - meleeHeight;
					meleeSwing.width = meleeBase; meleeSwing.height = meleeHeight;
				}
				//set marker info
				markerX = meleeSwing.x;
				markerY = meleeSwing.y;
				markerImage = Sprite.playerVerticalSwing.getImage(); //set the marker image
			}
			displayMarker = true; //display the visual swing
			
			//finally check meleeHit
			checkMeleeHit();
			//then clear the rectangle
			meleeSwing.x = -1; meleeSwing.y = -1; meleeSwing.width = 0; meleeSwing.height = 0;
			shouldMelee = false;
		}
	}
	
	private Mob tempEnemy;
	//helper method for checkMelee
	private void checkMeleeHit() {
		for(int i = 0; i < currentState.getEnemies().size(); i++) {
			tempEnemy = currentState.getEnemies().get(i);
			if(meleeSwing.intersects(tempEnemy.getHitbox()) || meleeSwing.contains(tempEnemy.getHitbox())) {
				tempEnemy.hit(damage);
			}
		}
	}
	
	private void checkDeath() {
		//casted to int to avoid HUD confusion, so if the HUD rounds and shows the player has 0 life, he won't die, but with me rounding the currentHealth, we can find out  what the HUD is displaying and act accordingly
		if((int)currentHealth <= 0) {
			//temp
			System.out.println("dead");
		}
	}
	
	//override the mob hit method
	public void hit(float damage) {
		if(!invincible) {
			currentHealth -= damage;
			if(currentHealth < 0) currentHealth = 0;
			invincible = true;
		}
	}
	
	private void checkCooldowns() {
		//melee cooldown
		if(!shouldMelee) {
			currentMeleeCooldown++;
			if(currentMeleeCooldown >= meleeCooldown) {
				shouldMelee = true;
				currentMeleeCooldown = 0;
			}
		}
		
		//shooting cooldown (ensure the player is in ranged form for the cooldown to count down) 
		if(!shouldShoot && rangedForm) {
			currentShootCooldown++;
			if(currentShootCooldown >= shootCooldown) {
				shouldShoot = true;
				currentShootCooldown = 0;
			}
		}
		
		//invincibility cooldown (after the player gets hit, they can't recieve damage for a short time)
		if(invincible) {
			currentInvincibleCooldown++;
			if(currentInvincibleCooldown >= invincibleCooldown) {
				invincible = false;
				currentInvincibleCooldown = 0;
			}
		}
	}
	
	private Rectangle mouse;
	private Mob tempMob;
	//When you focus a mob, it will 
	private void checkFocus() {
		//if the player right clicks, check to see if he clicked an enemy
		if(MouseMaster.getMouseB() == 3) {
			//set mouse cords
			mouse = MouseMaster.getHitbox();
			mouse.x += currentState.getTilemap().getXOffset();
			mouse.y += currentState.getTilemap().getYOffset();
			
			//enemies
			for(int i = 0; i < currentState.getEnemies().size(); i++) {
				tempMob = currentState.getEnemies().get(i);
				if(tempMob.getHitbox().contains(mouse)) {
					focusedMob = tempMob;
				}
			}
			for(int i = 0; i < currentState.getNPCs().size(); i++) {
				tempMob = currentState.getNPCs().get(i);
				if(tempMob.getHitbox().contains(mouse)) {
					focusedMob = tempMob;
				}
			}
		}
	}
	
	//will check if the mob has died or despawned, if so it will take it out of focus
	private void updateFocus() {
		//if there is no mob in focus, get out of this method
		if(focusedMob == null) return;
		
		//if the mob in focus is removed, set the focusedMob to null
		if(focusedMob.getRemoved() == true) {
			focusedMob = null;
		}
	}
	
	//checks to see if the player changes form (melee - ranged)
	private void checkForm() {
		if(MouseMaster.getCurrentNotches() < 0 || MouseMaster.getCurrentNotches() > 0) {
			//reset current notches
			MouseMaster.resetCurrentNotches();
			
			//change focus
			rangedForm = !rangedForm;
		}
	}
	
	//if the player changes gear, change the players stats too
	private void updateStats() {
		//health = (level * 5) + 10 + inventory.getArmor();
		//meleeCooldown = inventory.getWeaponSpeed();
		//shootCooldown = inventory.getRangedWeaponSpeed();
		//projectileSpeed = inventory.getWeapon().getProjectileSpeed();
	}
	
	private void checkXp() {
		if(level == MAX_LEVEL) return;
		
		if(currentXp >= xp) {
			levelUp();
			
			int remainder = currentXp - xp;
			currentXp = remainder;
			xp = 100 + (100 * level);
		}
	}
	
	private void levelUp() {
		level++;
		health = (level * 5) + 10;
		currentHealth = health;
		damage = level * 3;
		rangedDamage = level * 2;
		
		new ParticleSpawner(currentState).spawn(x, y, 240, 2.4f, Color.YELLOW, 2000);;
	}
	
	//used when the player kills a mob or does a quest
	public void giveXp(int xp) {
		if(level == MAX_LEVEL) return;
		currentXp += xp;
	}
	
	//used when changing an instance (will save data)
	public void changeInstance() {
		FileIO.save(levelData);
	}
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_W) {
			upHeld = true;
		}
		if(k == KeyEvent.VK_S) {
			downHeld = true;
		}
		if(k == KeyEvent.VK_D) {
			rightHeld = true;
		}
		if(k == KeyEvent.VK_A) {
			leftHeld = true;
		}
		playerAnim.keyPressed(k);
	}
	
	public void keyReleased(int k) {
		if(k == KeyEvent.VK_W) {
			upHeld = false;
		}
		if(k == KeyEvent.VK_S) {
			downHeld = false;
		}
		if(k == KeyEvent.VK_D) {
			rightHeld = false;
		}
		if(k == KeyEvent.VK_A) {
			leftHeld = false;
		}
		playerAnim.keyReleased(k);
	}
	public void keyTyped(int k) {
		
	}
	
	
	//getters
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public Mob getFocusedMob() {
		return focusedMob;
	}
	public boolean getRangedForm() {
		return rangedForm;
	}
	public int getXp() {
		return xp;
	}
	public int getCurrentXp() {
		return currentXp;
	}
	public LevelData getLevelData() {
		return levelData;
	}
	
	//setters
	public void setFocusedMob(Mob mob) {
		focusedMob = mob;
	}
}
