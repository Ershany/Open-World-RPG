package boss;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import animate.EnemyAnimate;
import enemies.Knight;
import gamestatemanager.LevelState;
import gfx.Particle;
import gfx.Sprite;
import projectile.Projectile;
import sfx.AudioPlayer;
import spawners.ParticleSpawner;
import tilemap.Tilemap;
import tiles.InterchangeableFloorTile;
import tiles.InterchangeableWallTile;
import util.Vector2f;

public class Kronos extends Boss {

	public enum Phase {
		ONE, TWO, THREE;
	}
	private Phase currentPhase;
	
	private int xpWorth = 100000;
	private EnemyAnimate animate;
	
	ParticleSpawner particleSpawner;
	private int stunTimer = 0;
	private boolean dialogueOver;
	
	
	public float[] xVals, yVals;
	private Random random = new Random();
	
	public Kronos(float x, float y, LevelState currentState,
			Tilemap currentTilemap) {
		super(x, y, 10, currentState, currentTilemap);
	}

	@Override
	public void init() {
		health = 500;
		currentHealth = health;
		damage = 5;
		rangedDamage = 2;
		speed = 4.6f;
		width = 96;
		height = 48;
		name = "Kronos";
		particleSpawner = new ParticleSpawner(currentState);
		
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
		checkPhase();
		actUponPhase();
		checkDeath();
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
	
	//this method is kind of like the move method, however it just tells the entity if they are running into something
	private boolean solid(float testSpeedX, float testSpeedY) {
		if (currentTilemap.getTile((int) (x +  testSpeedX), (int)(y + testSpeedY)).getSolid()) {
			return true;
		}
		for (int i = 0; i<= xVals.length-1; i++) {
			if (currentTilemap.getTile((int) (xVals[i] +  testSpeedX), (int)(yVals[i] + testSpeedY)).getSolid()) {
				return true;
			}
		}
		
		//if it got to this point, no solid so return false
		return false;
	}
	
	private boolean trapped;
	private int trappedTimer = 600;
	//phase changing logic
	private void checkPhase() {
		//prefight
		if(currentPhase == null) {
			if(Vector2f.getDistance(new Vector2f(currentState.getPlayer().getX(), currentState.getPlayer().getY()), new Vector2f(x, y)) < 200) {
				currentPhase = Phase.ONE;
				AudioPlayer.kronosGreeting.play();
			}
		}
		
		if(currentPhase == Phase.ONE) {
			if(currentHealth <= 100) {
				//spawn particles
				particleSpawner.spawn(x + (width / 2), y + (height / 2), 120, speed, Color.WHITE, 300);
				
				//reset animation
				moveRight = false; moveLeft = false; moveDown = false; moveUp = false;
				
				//reset timers
				counter = 0;
				timer = 0;
				
				//change position and update hitbox
				x = 32 * 59 - 16;
				y = 32 * 85;
				hitbox.x = (int)x; hitbox.y = (int)y;
				for (int i = 0; i<= xVals.length-1; i++) {
					xVals [i] = x + width*((i+1)%2);			
				}
				yVals [0] = y;
				yVals [1] = y + height;
				yVals [2] = y + height;
				yVals [3] = y + (height / 2);
				yVals [4] = y + (height / 2);
				
				//change phase
				currentPhase = Phase.TWO;
				
				//block player in
				currentState.getTilemap().changeTile(56, 111, new InterchangeableWallTile(56, 111, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(57, 111, new InterchangeableWallTile(57, 111, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(62, 111, new InterchangeableWallTile(62, 111, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(63, 111, new InterchangeableWallTile(63, 111, Sprite.dungeonWall.getImage()));
				
				//spawn mob
				currentState.addMob(new Knight(32 * 59 + 16, 32 * 104, 5, currentState, currentState.getTilemap()));
			}
		}
		else if(currentPhase == Phase.TWO) {
			if(timer >= 1200 && currentState.getEnemies().size() <= 1) {
				//spawn particles
				particleSpawner.spawn(x + (width / 2), y + (height / 2), 120, speed, Color.WHITE, 300);
				
				//reset timers
				counter = 0;
				timer = 0;
				
				//change position and update hitbox
				x = 32 * 59 - 16;
				y = 32 * 47;
				hitbox.x = (int)x; hitbox.y = (int)y;
				for (int i = 0; i<= xVals.length-1; i++) {
					xVals [i] = x + width*((i+1)%2);			
				}
				yVals [0] = y;
				yVals [1] = y + height;
				yVals [2] = y + height;
				yVals [3] = y + (height / 2);
				yVals [4] = y + (height / 2);
				
				//change phase
				currentPhase = Phase.THREE;
				
				//release player
				currentState.getTilemap().changeTile(47, 97, new InterchangeableFloorTile(47, 97, Sprite.dungeonDirt.getImage()));
				currentState.getTilemap().changeTile(47, 98, new InterchangeableFloorTile(47, 98, Sprite.dungeonDirt.getImage()));
				currentState.getTilemap().changeTile(72, 97, new InterchangeableFloorTile(72, 97, Sprite.dungeonDirt.getImage()));
				currentState.getTilemap().changeTile(72, 98, new InterchangeableFloorTile(72, 98, Sprite.dungeonDirt.getImage()));
				
				//now make the boss's speed increase
				speed *= 2.5f;
			}
		}
		else if(currentPhase == Phase.THREE) {
			//trap the player at the right time
			if(Vector2f.getDistance(new Vector2f(currentState.getPlayer().getX(), currentState.getPlayer().getY()), new Vector2f(x, y)) <= 354 && !trapped) {
				currentState.getTilemap().changeTile(59, 60, new InterchangeableWallTile(59, 60, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(60, 60, new InterchangeableWallTile(60, 60, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(56, 59, new InterchangeableWallTile(56, 59, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(56, 58, new InterchangeableWallTile(56, 58, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(56, 57, new InterchangeableWallTile(56, 57, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(56, 56, new InterchangeableWallTile(56, 56, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(56, 55, new InterchangeableWallTile(56, 55, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(56, 54, new InterchangeableWallTile(56, 54, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(63, 59, new InterchangeableWallTile(63, 59, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(63, 58, new InterchangeableWallTile(63, 58, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(63, 57, new InterchangeableWallTile(63, 57, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(63, 56, new InterchangeableWallTile(63, 56, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(63, 55, new InterchangeableWallTile(63, 55, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(63, 54, new InterchangeableWallTile(63, 54, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(57, 54, new InterchangeableWallTile(57, 54, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(58, 54, new InterchangeableWallTile(58, 54, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(61, 54, new InterchangeableWallTile(61, 54, Sprite.dungeonWall.getImage()));
				currentState.getTilemap().changeTile(62, 54, new InterchangeableWallTile(62, 54, Sprite.dungeonWall.getImage()));
				
				trapped = true;
				
				AudioPlayer.kronosExplain.play();
			}
			if(trapped) {
				trappedTimer--;
				
				if(trappedTimer <= 0) {
					currentState.getTilemap().changeTile(56, 59, new InterchangeableFloorTile(56, 59, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(56, 58, new InterchangeableFloorTile(56, 58, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(56, 57, new InterchangeableFloorTile(56, 57, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(56, 56, new InterchangeableFloorTile(56, 56, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(56, 55, new InterchangeableFloorTile(56, 55, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(56, 54, new InterchangeableFloorTile(56, 54, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(63, 59, new InterchangeableFloorTile(63, 59, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(63, 58, new InterchangeableFloorTile(63, 58, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(63, 57, new InterchangeableFloorTile(63, 57, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(63, 56, new InterchangeableFloorTile(63, 56, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(63, 55, new InterchangeableFloorTile(63, 55, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(63, 54, new InterchangeableFloorTile(63, 54, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(57, 54, new InterchangeableFloorTile(57, 54, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(58, 54, new InterchangeableFloorTile(58, 54, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(61, 54, new InterchangeableFloorTile(61, 54, Sprite.dungeonDirt.getImage()));
					currentState.getTilemap().changeTile(62, 54, new InterchangeableFloorTile(62, 54, Sprite.dungeonDirt.getImage()));
					
					dialogueOver = true;
				}
			}
		}
	}
	
	int timer = 0;
	int counter = 0;
	//phase logic
	private void actUponPhase() {
		if(timer > 999999) timer = 0;
		else timer++;
		
		if(stunTimer > 0) stunTimer--;
		
		if(stunTimer <= 0) {
			if(currentPhase == Phase.ONE) {
				//projectile spawning
				if(timer % 10 == 0)
					currentState.addEnemyProjectile(new Projectile(x + (width / 2), y + (height / 2), currentState.getPlayer().getX() + 16, currentState.getPlayer().getY() + 24, 3.2f, 300, rangedDamage, currentState, Sprite.kronosProjectile.getImage()));
			
				//movement logic
				if(counter == 0) {
					move(-speed, 0);
					moveLeft = true;
					moveUp = false; moveRight = false; moveDown = false;
					if(solid(-speed, 0)) 
						counter = 1;
				}
				else if(counter == 1) {
					move(0, speed);
					moveDown = true;
					moveLeft = false; moveRight = false; moveUp = false;
					if(solid(0, speed)) 
						counter = 2;
				}
				else if(counter == 2) {
					move(speed, 0);
					moveRight = true;
					moveDown = false; moveLeft = false; moveUp = false;
					if(solid(speed, 0)) 
						counter = 3;
				}
				else if(counter == 3) {
					move(0, -speed);
					moveUp = true;
					moveRight = false; moveDown = false; moveLeft = false;
					if(solid(0, -speed)) 
						counter = 0;
				}
			}
			else if(currentPhase == Phase.TWO) {
				//particles
				if(timer % 5 == 0) {
					particleSpawner.spawn(x + (width / 2), y + (height / 2), 25, speed / 2, 5, Sprite.heart.getImage(), Particle.Type.NORTH);
				}
			
				//movement
				if(solid(0, speed)) 
					moveDown = false;
				else {
					moveDown = true;
					move(0, speed);
				}
			
				//heal
				if(timer % 4 == 0)
					heal(1);
			
				if(timer % 330 == 0) {
					currentState.addMob(new Knight(32 * 59 + 16, 32 * 104, 5, currentState, currentState.getTilemap()));
				}
			}
			else if(currentPhase == Phase.THREE && dialogueOver) {
				//projectile spawning
				if(timer % 10 == 0)
					currentState.addEnemyProjectile(new Projectile(x + (width / 2), y + (height / 2), currentState.getPlayer().getX() + 16, currentState.getPlayer().getY() + 24, 3.2f, 300, rangedDamage, currentState, Sprite.kronosProjectile.getImage()));
			
				//movement logic
				if(counter == 0) {
					move(-speed, 0);
					moveLeft = true;
					moveUp = false; moveRight = false; moveDown = false;
					if(solid(-speed, 0)) 
						counter = 1;
				}
				else if(counter == 1) {
					move(0, speed);
					moveDown = true;
					moveLeft = false; moveRight = false; moveUp = false;
					if(solid(0, speed)) 
						counter = 2;
				}
				else if(counter == 2) {
					move(speed, 0);
					moveRight = true;
					moveDown = false; moveLeft = false; moveUp = false;
					if(solid(speed, 0)) 
						counter = 3;
				}
				else if(counter == 3) {
					move(0, -speed);
					moveUp = true;
					moveRight = false; moveDown = false; moveLeft = false;
					if(solid(0, -speed)) 
						counter = 0;
				}
				
				if(timer % 1000 == 0) {
					currentState.addMob(new Knight(32 * 59 + 16, 32 * 48, 5, currentState, currentState.getTilemap()));
				}
			}
		}
		
	}
	
	private void heal(int amount) {
		currentHealth += amount;
		
		if(currentHealth > health) 
			currentHealth = health;
	}
	
	private void checkDeath() {
		if(currentHealth <= 0) {
			removed = true;
			currentState.getPlayer().giveXp(xpWorth);
			particleSpawner.spawn(x + (width / 2), y + (height / 2), 120, speed, Color.WHITE, 500);
			
			//clear mobs
			currentState.getEnemies().clear();
			
			//clear cliff
			currentState.getTilemap().changeTile(72, 34, new InterchangeableFloorTile(72, 34, Sprite.dungeonDirt.getImage()));
			currentState.getTilemap().changeTile(72, 35, new InterchangeableFloorTile(72, 35, Sprite.dungeonDirt.getImage()));
			currentState.getTilemap().changeTile(72, 36, new InterchangeableFloorTile(72, 36, Sprite.dungeonDirt.getImage()));
			currentState.getTilemap().changeTile(71, 34, new InterchangeableFloorTile(71, 34, Sprite.dungeonDirt.getImage()));
			currentState.getTilemap().changeTile(71, 35, new InterchangeableFloorTile(71, 35, Sprite.dungeonDirt.getImage()));
			currentState.getTilemap().changeTile(71, 36, new InterchangeableFloorTile(71, 36, Sprite.dungeonDirt.getImage()));
			currentState.getTilemap().changeTile(73, 34, new InterchangeableFloorTile(73, 34, Sprite.dungeonDirt.getImage()));
			currentState.getTilemap().changeTile(73, 35, new InterchangeableFloorTile(73, 35, Sprite.dungeonDirt.getImage()));
			currentState.getTilemap().changeTile(73, 36, new InterchangeableFloorTile(73, 36, Sprite.dungeonDirt.getImage()));
		}
	}
	
	//boss methods (only get called if the entity is in the boss list)
	private int reflectionRange = 90;
	@Override
	public void projectileHit(Projectile p) {
		//change the projectiles direction by 45 degrees + or - depending on chance
		if(random.nextBoolean()) {
			p.resetDirection(p.getAngle() - random.nextInt(reflectionRange - (reflectionRange / 2)));
		} else {
			p.resetDirection(p.getAngle() + random.nextInt(reflectionRange - (reflectionRange / 2)));
		}
	}
	
	//boss method for stunning the boss
	public void stun(int stunTime) {
		if(stunTimer > 60) return;
		stunTimer += stunTime;
	}
	
}
