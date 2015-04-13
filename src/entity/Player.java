package entity;

import game.Game;
import gamestatemanager.LevelState;
import gfx.Sprite;
import hud.HUD;
import input.MouseMaster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import projectile.Projectile;
import tilemap.Tilemap;
import animate.PlayerAnimate;

public class Player extends Mob {

	//TODO:
	//updateStats() method needs to be complete when the inventory is finished
	
	
	//to be added in the future
	//private Inventory inventory;
	private HUD hud;
	private Mob focusedMob;
	
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
	
	private float[] xVals, yVals;
	private PlayerAnimate playerAnim;
	
	
	public Player(int x, int y, int level, LevelState state, Tilemap tilemap) {
		super(x, y, level, state, tilemap);
	}

	@Override
	public void init() {
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
	}

	@Override	
	public void update() {
		//movement
		checkMovement();
		
		//melee
		checkMelee();
		
		//shooting
		checkShooting();
		
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
	}

	@Override
	public void render(Graphics2D g) {
		//render player
		drawPlayer(g);
		
		//render reload bar
		drawReloadBar(g);
		
		//render HUD
		hud.render(g);
	}
	
	private void drawPlayer(Graphics2D g) {
		playerAnim.render(g);
	}
	
	private void drawReloadBar(Graphics2D g) {
		//firstly check if we are in rangedForm to render the bar and if it is on cooldown
		if(!rangedForm || currentShootCooldown <= 0) return;
		
		g.setColor(Color.RED);
		g.fillRect(MouseMaster.getMouseX() + 30, MouseMaster.getMouseY(), 3, (int)(0.25 * (shootCooldown - currentShootCooldown)));
	}
	
	private void move (float testSpeedX, float testSpeedY) {
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
			}
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
			//enemies
			for(int i = 0; i < currentState.getEnemies().size(); i++) {
				//first iteration get the mouse coords
				if(i == 0) {
					mouse = MouseMaster.getHitbox();
					mouse.x += currentState.getTilemap().getXOffset();
					mouse.y += currentState.getTilemap().getYOffset();
				}	
				tempMob = currentState.getEnemies().get(i);
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
}
