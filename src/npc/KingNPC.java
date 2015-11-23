package npc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import tilemap.Tilemap;
import animate.NPCAnimate;
import entity.Mob;
import gamestatemanager.LevelState;
import gfx.Sprite;

public class KingNPC extends Mob {

	public float[] xVals, yVals;
	
	private Random random = new Random();
	
	private NPCAnimate animate;
	
	public KingNPC(float x, float y, int level, LevelState currentState,
			Tilemap currentTilemap) {
		super(x, y, level, currentState, currentTilemap);
	}

	@Override
	public void init() {
		health = level * 50;
		currentHealth = health;
		damage = level * 5;
		rangedDamage = 0;
		speed = 0.8f;
		width = 32;
		height = 48;
		name = "King";
		//info = new String[] {"I am the king, bow down to me."};
		info = new String[] {"You sir you need to do something about these raiders!",
						     "I believe I know what we need to do!",
						     "You need to find the beast and slay him!",
						     "Ask the villagers information about where to find this beast",
						     "You might also need to train first! Fight things on the island."};
		
		hitbox = new Rectangle(width, height);
		hitbox.x = (int)x; hitbox.y = (int)y;
		
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
		
		BufferedImage[] temp = {Sprite.kingWalkUp1.getImage(), Sprite.kingIdleUp.getImage(), Sprite.kingWalkUp2.getImage(),
							    Sprite.kingWalkDown1.getImage(), Sprite.kingIdleDown.getImage(), Sprite.kingWalkDown2.getImage(),
							    Sprite.kingWalkRight1.getImage(), Sprite.kingIdleRight.getImage(), Sprite.kingWalkRight2.getImage(),
							    Sprite.kingWalkLeft1.getImage(), Sprite.kingIdleLeft.getImage(), Sprite.kingWalkLeft2.getImage()};
		animate = new NPCAnimate(this, temp);
	}

	@Override
	public void update() {
		checkMovement();
		
		doMovement();
		
		updateAnimate();
	}

	@Override
	public void render(Graphics2D g) {
		animate.render(g);
	}
	
	private int decide;
	private void checkMovement() {
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
	
	private void doMovement() {
		if(moveUp) {
			move(0, -speed);
		}
		if(moveDown) {
			move(0, speed);
		}
		if(moveRight) {
			move(speed, 0);
		}
		if(moveLeft) {
			move(-speed, 0);
		}
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
	
	private void updateAnimate() {
		animate.update();
	}
	
}
