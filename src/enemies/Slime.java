package enemies;

import entity.Mob;
import gamestatemanager.LevelState;
import gfx.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import spawners.ParticleSpawner;
import tilemap.Tilemap;

public class Slime extends Mob {

	private Random random = new Random();
	private int xpWorth;
	
	public float[] xVals, yVals;
	private BufferedImage slimeSprite;
	
	public Slime(float x, float y, int level, LevelState currentState,
			Tilemap currentTilemap) {
		super(x, y, level, currentState, currentTilemap);
	}

	@Override
	public void init() {
		health = 1 + (level * 2);
		currentHealth = health;
		damage = level;
		rangedDamage = 0;
		speed = 0.5f + (level * 0.1f);
		width = 32;
		height = 32;
		name = "Slime";
		xpWorth = 75 + ((level - 1) * 25);
		
		hitbox = new Rectangle(width, height);
		hitbox.x = (int)x; hitbox.y = (int)y;
		
		xVals = new float[3];
		yVals = new float[3];
		
		for (int i = 0; i <= xVals.length-1; i++) {
			xVals [i] = x + width*((i+1)%2);			
		}
		
		yVals [0] = y;
		yVals [1] = y + height;
		yVals [2] = y + height;
		
		slimeSprite = Sprite.slime1.getImage();
	}

	@Override
	public void update() {
		if(!dying) {
			//checkDeath
			checkDeath();
		
			//anim
			animate();
		
			//move
			move();
		}
		else {
			animateDeath();
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(slimeSprite, (int)x - currentTilemap.getXOffset(), (int)y - currentTilemap.getYOffset(), null);
	}
	
	private void animate() {
		//ensure anim doesnt get too high
		if(anim > 10000) anim = 0;
		else anim++;

		if(anim % 180 == 0) slimeSprite = Sprite.slime1.getImage();
		else if(anim % 120 == 0) slimeSprite = Sprite.slime2.getImage();
		else if(anim % 60 == 0) slimeSprite = Sprite.slime3.getImage();
	}
	
	private void animateDeath() {
		//anim was reset with the slime died
		anim++;
		
		if(anim == 480) removed = true;
		
		if(anim == 21) slimeSprite = Sprite.deathSlime3.getImage();
		else if(anim == 14) slimeSprite = Sprite.deathSlime2.getImage();
		else if(anim == 7) slimeSprite = Sprite.deathSlime1.getImage();
	}
	
	private void move() {
		//picks a direction to move every so often
		if(anim % 120 == 0) {
			moveUp = false; moveDown = false; moveRight = false; moveLeft = false;
			int randomNum = random.nextInt(16) + 1;
			if(randomNum % 2 == 0) return;
			else {
				switch(randomNum) {
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
		
		//move the slime
		if(moveUp && checkCollision(0, -speed)) {
			y -= speed;
			for (int i = 0; i<= yVals.length-1; i++) 
				yVals[i]  -= speed;	
		}
		if(moveDown && checkCollision(0, speed)) {
			y += speed;
			for (int i = 0; i<= yVals.length-1; i++) 
				yVals[i] += speed;	
		}
		if(moveRight && checkCollision(speed, 0)) {
			x += speed;
			for (int i = 0; i<= xVals.length-1; i++) 
				xVals[i]  += speed;	
		}
		if(moveLeft && checkCollision(-speed, 0)) {
			x -= speed;
			for (int i = 0; i<= xVals.length-1; i++) 
				xVals[i]  -= speed;	
		}
		hitbox.x = (int)x; hitbox.y = (int)y;
	}
	
	private boolean checkCollision(float xSpeed, float ySpeed) {
		if (currentTilemap.getTile((int) (x +  xSpeed), (int)(y + ySpeed)).getSolid()) {
			return false;
		}
		for (int i = 0; i<= xVals.length-1; i++) {
			if (currentTilemap.getTile((int) (xVals[i] +  xSpeed), (int)(yVals[i] + ySpeed)).getSolid()) {
				return false;
			}
		}
		
		//if it got to this point, return true
		return true;
	}
	
	
	private void checkDeath() {
		if(currentHealth <= 0 && !dying) {
			dying = true;
			anim = 0;
			currentState.getPlayer().giveXp(xpWorth);
			new ParticleSpawner(currentState).spawn(x + (width / 2), y + (height / 2), 15, 1.4f, Color.GREEN, 20);
		}
	}

}