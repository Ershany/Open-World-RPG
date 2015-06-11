package boss;

import gamestatemanager.LevelState;
import gfx.Sprite;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import projectile.Projectile;
import tilemap.Tilemap;
import animate.EnemyAnimate;

public class Kronos extends Boss {

	private int xpWorth = 300;
	private EnemyAnimate animate;
	
	public float[] xVals, yVals;
	private Random random = new Random();
	
	public Kronos(float x, float y, LevelState currentState,
			Tilemap currentTilemap) {
		super(x, y, 10, currentState, currentTilemap);
	}

	@Override
	public void init() {
		health = 250;
		currentHealth = health;
		damage = 5;
		rangedDamage = 10;
		speed = 4.6f;
		width = 96;
		height = 48;
		name = "Kronos";
		
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
		
		BufferedImage[] temp = {Sprite.kronosWalkUp1.getImage(), Sprite.kronosIdleUp.getImage(), Sprite.kronosWalkUp2.getImage(),
							    Sprite.kronosWalkDown1.getImage(), Sprite.kronosIdleDown.getImage(), Sprite.kronosWalkDown2.getImage(),
							    Sprite.kronosWalkRight1.getImage(), Sprite.kronosIdleRight.getImage(), Sprite.kronosWalkRight2.getImage(),
							    Sprite.kronosWalkLeft1.getImage(), Sprite.kronosIdleLeft.getImage(), Sprite.kronosWalkLeft2.getImage()};
		BufferedImage[] temp2 = {Sprite.knightDeath1.getImage(), Sprite.knightDeath2.getImage(), Sprite.knightDeath3.getImage(), Sprite.knightDeath4.getImage(), 
									Sprite.knightDeath5.getImage(), Sprite.knightDeath6.getImage(), Sprite.knightDeath7.getImage() };
		
		animate = new EnemyAnimate(this, temp, - 16, -16, temp2);
		animate.setAnimSpeed(20);
		animate.deathSpeed = 7;
	}

	@Override
	public void update() {
		animate.update();
		moveRight = true;
	}

	@Override
	public void render(Graphics2D g) {
		animate.render(g);
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
	
	//boss methods (only get called if the entity is in the boss list)
	private int reflectionValue = 90;
	@Override
	public void projectileHit(Projectile p) {
		//change the projectiles direction by 45 degrees + or - depending on chance
		if(random.nextBoolean()) {
			p.resetDirection(p.getAngle() - reflectionValue);
		} else {
			p.resetDirection(p.getAngle() + reflectionValue);
		}
	}
	
}
