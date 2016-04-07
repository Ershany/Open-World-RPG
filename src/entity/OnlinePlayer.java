package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gfx.Sprite;

public class OnlinePlayer {

	private float prevX, prevY;
	public float x, y;
	public float xTarg, yTarg;
	private float speed = 3.3f;
	
	private int animSpeed = 15; // Lower the faster animation
	private int counter = 0;
	
	private Sprite[][] images = {{Sprite.playerWalkDown1, Sprite.playerIdleDown, Sprite.playerWalkDown2},
								  {Sprite.playerWalkUp1, Sprite.playerIdleUp, Sprite.playerWalkUp2},
								  {Sprite.playerWalkRight1, Sprite.playerIdleRight, Sprite.playerWalkRight2},
								  {Sprite.playerWalkLeft1, Sprite.playerIdleLeft, Sprite.playerWalkLeft2}};
	private int currentSet = 0;    // 0 - down, 1 - up, 2 - right, 3 - left
	private int currentSprite = 1; // 0 - walk1, 1 - idle, 2 - walk2
	private boolean moving;
	public boolean hide;
	
	public OnlinePlayer(float x, float y) {
		this.x = x;
		this.y = y;
		xTarg = x;
		yTarg = y;
	}

	public void update() {
		// Snapping if target is too far away (lag is happening)
		if(Math.abs(yTarg - y) > 64 || Math.abs(xTarg - x) > 64) {
			x = xTarg;
			y = yTarg;
		}
		
		// Get the old values
		prevX = x;
		prevY = y;
		
		// Move towards target
		if(xTarg > x) {
			x += speed;
			currentSet = 2;
		}
		else if(xTarg < x) {
			x -= speed;
			currentSet = 3;
		}
		if(yTarg > y) {
			y += speed;
			currentSet = 0;
		}
		else if(yTarg < y) {
			y -= speed;
			currentSet = 1;
		}
		
		// Check to see if they moved
		if(prevX != x || prevY != y) {
			if(!moving)
				counter = animSpeed;
			moving = true;
		} else {
			moving = false;
			currentSprite = 1;
		}
		
		if(moving) {
			counter++;
			if(counter > animSpeed) {
				counter = 0;
				if(currentSprite == 0) 
					currentSprite = 2;
				else 
					currentSprite = 0;
			}
		}
	}
	
	
	public void render(int xOffset, int yOffset, Graphics2D g) {
		if(!hide) 
			g.drawImage(images[currentSet][currentSprite].getImage(), (int)(x - xOffset), (int)(y - yOffset), null);
	}
	
}
