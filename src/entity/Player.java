package entity;

import game.Game;
import gamestatemanager.LevelState;
import input.MouseMaster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import projectile.Projectile;
import tilemap.Tilemap;

public class Player extends Mob {

	//to be added in the future
	//private Inventory inventory;
	
	private boolean upHeld, downHeld, rightHeld, leftHeld;
	private boolean shouldShoot;
	
	private int shootCooldown = 120;
	private int currentShootCooldown = 0;
	
	private float[] xVals, yVals;
	
	
	public Player(int x, int y, int level, LevelState state, Tilemap tilemap) {
		super(x, y, level, state, tilemap);
	}

	@Override
	public void init() {
		health = level * 5; //it should be:    health = (level * 5) + inventory.getArmor();
		currentHealth = health;
		damage = level * 2;  //it should be:   damage = (level * 2) + inventory.getWeapon().getDamage();
		speed = 2.9f;
		width = 32;
		height = 48;
		shouldShoot = true;
		
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
	}

	@Override	
	public void update() {
		//movement
		checkMovement();
		
		//shooting
		checkShooting();
		
		//set offsets
		updateOffset();
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)x - currentTilemap.getXOffset(), (int)y - currentTilemap.getYOffset(), width, height);
	}
	
	private void move (float testSpeedX, float testSpeedY) {
		if (currentTilemap.getTile((int) (x +  testSpeedX), (int)(y + testSpeedY)).getSolid()) {
			return;
		}
		for (int i = 0; i <= xVals.length-1; i++) {
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
		if(!shouldShoot) {
			currentShootCooldown++;
			if(currentShootCooldown == shootCooldown) {
				shouldShoot = true;
				currentShootCooldown = 0;
			}
		}
		
		if(MouseMaster.getMouseB() == 1 && shouldShoot) {
			//pass in the xDest and yDest with the xOffsets and yOffsets
			currentState.addProjectile(new Projectile(x, y, MouseMaster.getMouseX() + currentState.getTilemap().getXOffset(), MouseMaster.getMouseY() + currentState.getTilemap().getYOffset(), 5, 5, 7.2f, 60, damage, currentState));
			shouldShoot = false;
		}
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
}
