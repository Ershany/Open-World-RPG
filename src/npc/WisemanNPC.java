package npc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import animate.NPCAnimate;
import tilemap.Tilemap;
import entity.Mob;
import game.Game;
import gamestatemanager.LevelState;
import gfx.Sprite;

public class WisemanNPC extends Mob {

	public float[] xVals, yVals;
	
	private Random random = new Random();
	
	private NPCAnimate animate;
	
	public WisemanNPC(float x, float y, int level, LevelState currentState,
			Tilemap currentTilemap) {
		super(x, y, level, currentState, currentTilemap);
	}

	@Override
	public void init() {
		health = level;
		currentHealth = health;
		damage = level * 1;
		rangedDamage = 0;
		speed = 0.5f;
		width = 32;
		height = 48;
		name = "Wiseman";
		//info = new String[] {"Be aware of the king and his henchmen!",
							 //"They can get away with anything they want..."};
		info = new String[] {"                            Our poor island is doomed because of that beast!",
							 "                            Some think he lives in a cave on our island.",
							 "                            I don't know what to believe.",
							 "                            Will you help us?",
							 "",
							 "                                              1: Yes I will slay that beast!",
							 "                                              2: No, goodluck.",
							 "                                              3: Maybe for a reward."};
		
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
		
		BufferedImage[] temp = {Sprite.wisemanWalkUp1.getImage(), Sprite.wisemanIdleUp.getImage(), Sprite.wisemanWalkUp2.getImage(),
							    Sprite.wisemanWalkDown1.getImage(), Sprite.wisemanIdleDown.getImage(), Sprite.wisemanWalkDown2.getImage(),
							    Sprite.wisemanWalkRight1.getImage(), Sprite.wisemanIdleRight.getImage(), Sprite.wisemanWalkRight2.getImage(),
							    Sprite.wisemanWalkLeft1.getImage(), Sprite.wisemanIdleLeft.getImage(), Sprite.wisemanWalkLeft2.getImage()};
		animate = new NPCAnimate(this, temp);
		animate.setAnimSpeed(40);
	}

	@Override
	public void update() {
		if(Game.hosting || !Game.multiplayer) {
			super.setRandomMovement();
		}
		
		doMovement();
		
		updateAnimate();
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

	@Override
	public void render(Graphics2D g) {
		animate.render(g);
	}

}
