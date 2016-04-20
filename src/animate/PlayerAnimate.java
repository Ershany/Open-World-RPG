package animate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entity.Mob;
import gfx.Sprite;

public class PlayerAnimate extends Animate {

	private float prevX, prevY;
	private Sprite[][] images = { { Sprite.playerWalkDown1, Sprite.playerIdleDown, Sprite.playerWalkDown2 },
			{ Sprite.playerWalkUp1, Sprite.playerIdleUp, Sprite.playerWalkUp2 },
			{ Sprite.playerWalkRight1, Sprite.playerIdleRight, Sprite.playerWalkRight2 },
			{ Sprite.playerWalkLeft1, Sprite.playerIdleLeft, Sprite.playerWalkLeft2 } };
	private int currentSet = 0; // 0 - down, 1 - up, 2 - right, 3 - left
	private int currentSprite = 1; // 0 - walk1, 1 - idle, 2 - walk2
	private boolean moving;
	
	private int animSpeed = 15; //lower the value, faster the animation
	
	public PlayerAnimate(Mob mob) {
		super(mob);
	}

	@Override
	public void init() {
		prevX = mob.getX();
		prevY = mob.getY();
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(images[currentSet][currentSprite].getImage(), (int)mob.getX() - mob.getTileMap().getXOffset(), (int)mob.getY() - mob.getTileMap().getYOffset(), null);
	}

	private int counter = 0;
	@Override
	public void update() {
		moving = false;
		
		// Check x movement
		if(mob.getX() > prevX) {
			currentSet = 2;
			moving = true;
		}
		else if(mob.getX() < prevX) {
			currentSet = 3;
			moving = true;
		}
		
		// Check y movement
		if(mob.getY() > prevY) {
			currentSet = 0;
			moving = true;
		}
		else if(mob.getY() < prevY) {
			currentSet = 1;
			moving = true;
		}
		
		// Check if the player is moving and change their sprite accordingly
		if(moving) {
			if(counter == 0) 
				currentSprite = 0;
			
			counter++;
			if(counter >= animSpeed) {
				counter = 1;
				
				if(currentSprite == 0)
					currentSprite = 2;
				else 
					currentSprite = 0;
			}
		} else {
			currentSprite = 1;
			counter = 0;
		}
		
		prevX = mob.getX();
		prevY = mob.getY();
	}

	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}
	
}
