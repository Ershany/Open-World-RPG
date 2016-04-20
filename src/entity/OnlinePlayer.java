package entity;

import java.awt.Graphics2D;

import gamestatemanager.LevelState;
import gfx.Sprite;
import tiles.WaterTile;

public class OnlinePlayer {

	private LevelState state;

	private boolean inShip;

	private float prevX, prevY;
	public float x, y;
	public float xTarg, yTarg;
	private float speed = 3.3f;

	private int animSpeed = 15; // Lower the faster animation
	private int counter = 0;

	private Sprite[][] images = { { Sprite.onlineWalkDown1, Sprite.onlineIdleDown, Sprite.onlineWalkDown2 },
			{ Sprite.onlineWalkUp1, Sprite.onlineIdleUp, Sprite.onlineWalkUp2 },
			{ Sprite.onlineWalkRight1, Sprite.onlineIdleRight, Sprite.onlineWalkRight2 },
			{ Sprite.onlineWalkLeft1, Sprite.onlineIdleLeft, Sprite.onlineWalkLeft2 } };
	private Sprite[] ships = { Sprite.basicShipIdleDown, Sprite.basicShipIdleUp, Sprite.basicShipIdleRight,
			Sprite.basicShipIdleLeft };
	private int currentSet = 0; // 0 - down, 1 - up, 2 - right, 3 - left
	private int currentSprite = 1; // 0 - walk1, 1 - idle, 2 - walk2
	private boolean moving;
	public boolean hide;

	public OnlinePlayer(float x, float y, LevelState state) {
		this.x = x;
		this.y = y;
		this.state = state;
		xTarg = x;
		yTarg = y;
	}

	public void update() {
		move();
		checkWater();
	}

	public void render(int xOffset, int yOffset, Graphics2D g) {
		if (!hide) {
			if (inShip) {
				g.drawImage(ships[currentSet].getImage(), (int) (x - xOffset), (int) (y - yOffset), null);
			} else {
				g.drawImage(images[currentSet][currentSprite].getImage(), (int) (x - xOffset), (int) (y - yOffset),
						null);
			}
		}
	}

	private void move() {
		// Snapping if target is too far away (lag is happening)
		if (Math.abs(yTarg - y) > 128 || Math.abs(xTarg - x) > 128) {
			x = xTarg;
			y = yTarg;
		}

		// Get the old values
		prevX = x;
		prevY = y;

		// Move towards target
		if (xTarg > x) {
			if(xTarg - x < speed) {
				x += xTarg - x;
			}
			else {
				x += speed;
			}
			currentSet = 2;
		} else if (xTarg < x) {
			if(x - xTarg < speed) {
				x -= x - xTarg;
			}
			else {
				x -= speed;
			}
			currentSet = 3;
		}
		if (yTarg > y) {
			if(yTarg - y < speed) {
				y += yTarg - y;
			}
			else {
				y += speed;
			}
			currentSet = 0;
		} else if (yTarg < y) {
			if(y - yTarg < speed) {
				y -= y - yTarg;
			}
			else {
				y -= speed;
			}
			currentSet = 1;
		}

		// Check to see if they moved
		if (prevX != x || prevY != y) {
			if (!moving)
				counter = animSpeed;
			moving = true;
		} else {
			moving = false;
			currentSprite = 1;
		}

		if (moving) {
			counter++;
			if (counter > animSpeed) {
				counter = 0;
				if (currentSprite == 0)
					currentSprite = 2;
				else
					currentSprite = 0;
			}
		}
	}

	private void checkWater() {
		if (x > 0 && y > 0) {
			if (state.getTilemap().getTile((int) x + 16, (int) y + 24) instanceof WaterTile) {
				inShip = true;
			} else {
				inShip = false;
			}
		}
	}

}
