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
	private int width, height;
	
	private boolean upHeld, downHeld, rightHeld, leftHeld;
	
	//corners  x2/y2 top right, x3/y3 bottom left, x4/y4 bottom right, x5/y5 middle left, x6/y6 middle right
	//private float x2, x3, x4, x5, x6, y2, y3, y4, y5, y6;
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
		
		hitbox = new Rectangle(width, height);
		
		xVals = new float[5];
		yVals = new float[5];
		
		for (int i = 0; i<= xVals.length-1; i++) {
			xVals [i] = x + width*((i+1)%2);			
		}
		System.out.println("new player created");
		
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
	
	private boolean move (float testSpeedX, float testSpeedY) {
		if (currentTilemap.getTile((int) (x +  testSpeedX), (int)(y + testSpeedY)).getSolid()) {
			return false;
		}
		for (int i = 0; i<= xVals.length-1; i++) {
			if (currentTilemap.getTile((int) (xVals[i] +  testSpeedX), (int)(yVals[i] + testSpeedY)).getSolid()) {
				return false;
			}
		}

		x += testSpeedX;
		y += testSpeedY;
		for (int i = 0; i<= xVals.length-1; i++) {
				xVals[i]  += testSpeedX;
				yVals[i]  += testSpeedY;	
		}
		
		
		return true;
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
		/*int xMod = 0;
		int yMod = 0;
		if(upHeld) {
			if(!currentTilemap.getTile((int)x, (int)(y - speed)).getSolid() &&
			!currentTilemap.getTile((int)x2, (int)(y2 - speed)).getSolid() &&
			!currentTilemap.getTile((int)x3, (int)(y3 - speed)).getSolid() &&
			!currentTilemap.getTile((int)x4, (int)(y4 - speed)).getSolid() &&
			!currentTilemap.getTile((int)x5, (int)(y5 - speed)).getSolid() && 
			!currentTilemap.getTile((int)x6, (int)(y6 - speed)).getSolid()) 
			
			{
				y -= speed;
				y2 -= speed;
				y3 -= speed;
				y4 -= speed;
				y5 -= speed;
				y6 -= speed;
			}
		}
		if(downHeld) {
			if(!currentTilemap.getTile((int)x, (int)(y + speed)).getSolid() &&
			!currentTilemap.getTile((int)x2, (int)(y2 + speed)).getSolid() &&
			!currentTilemap.getTile((int)x3, (int)(y3 + speed)).getSolid() &&
			!currentTilemap.getTile((int)x4, (int)(y4 + speed)).getSolid() &&
			!currentTilemap.getTile((int)x5, (int)(y5 + speed)).getSolid() &&
			!currentTilemap.getTile((int)x6, (int)(y6 + speed)).getSolid()){
				y += speed;
				y2 += speed;
				y3 += speed;
				y4 += speed;
				y5 += speed;
				y6 += speed;
			}
		}
		if(leftHeld) {
			if(!currentTilemap.getTile((int)(x - speed), (int)y).getSolid() &&
			!currentTilemap.getTile((int)(x2 - speed), (int)y2).getSolid() &&
			!currentTilemap.getTile((int)(x3 - speed), (int)y3).getSolid() &&
			!currentTilemap.getTile((int)(x4 - speed), (int)y4).getSolid() &&
			!currentTilemap.getTile((int)(x5 - speed), (int)y5).getSolid() && 
			!currentTilemap.getTile((int)(x6 - speed), (int)y6).getSolid()) {
				x -= speed;
				x2 -= speed;
				x3 -= speed;
				x4 -= speed;
				x5 -= speed;
				x6 -= speed;
			}
		}
		if(rightHeld) {
			if(!currentTilemap.getTile((int)(x + speed), (int)y).getSolid() &&
			!currentTilemap.getTile((int)(x2 + speed), (int)y2).getSolid() &&
			!currentTilemap.getTile((int)(x3 + speed), (int)y3).getSolid() &&
			!currentTilemap.getTile((int)(x4 + speed), (int)y4).getSolid() &&
			!currentTilemap.getTile((int)(x5 + speed), (int)y5).getSolid() &&
			!currentTilemap.getTile((int)(x6 + speed), (int)y6).getSolid()) {
				x += speed;
				x2 += speed;
				x3 += speed;
				x4 += speed;
				x5 += speed;
				x6 += speed;
			}
		}*/
	
	private void updateOffset() {
		currentTilemap.setXOffset((int)x - (Game.WIDTH / 2) + (width / 2));
		currentTilemap.setYOffset((int)y - (Game.HEIGHT / 2) + (height / 2)); 
	}
	
	private void checkShooting() {
		if(MouseMaster.getMouseB() == 1) {
			//pass in the xDest and yDest with the xOffsets and yOffsets
			currentState.addProjectile(new Projectile(x, y, MouseMaster.getMouseX() + currentState.getTilemap().getXOffset(), MouseMaster.getMouseY() + currentState.getTilemap().getYOffset(), 7.2f, 60, currentState));
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
}
