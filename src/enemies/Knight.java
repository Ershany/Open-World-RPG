package enemies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import tilemap.Node;
import tilemap.Tilemap;
import util.Vector2f;
import animate.EnemyAnimate;
import entity.Mob;
import gamestatemanager.LevelState;
import gfx.Sprite;

public class Knight extends Mob {

	public static final int AGGRO_RANGE = 10;
	
	private int xpWorth = 50;
	private EnemyAnimate animate;
	
	public float[] xVals, yVals;
	
	//A* node array
	private List<Node> path;
	
	public Knight(float x, float y, int level, LevelState currentState,
			Tilemap currentTilemap) {
		super(x, y, level, currentState, currentTilemap);
	}

	@Override
	public void init() {
		health = level * 10;
		currentHealth = health;
		damage = level * 2 + 1;
		rangedDamage = 0;
		speed = 2.3f; //should be 3.0f
		width = 32;
		height = 32;
		name = "Knight";
		
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
		
		BufferedImage[] temp = {Sprite.knightWalkUp1.getImage(), Sprite.knightIdleUp.getImage(), Sprite.knightWalkUp2.getImage(),
							    Sprite.knightWalkDown1.getImage(), Sprite.knightIdleDown.getImage(), Sprite.knightWalkDown2.getImage(),
							    Sprite.knightWalkRight1.getImage(), Sprite.knightIdleRight.getImage(), Sprite.knightWalkRight2.getImage(),
							    Sprite.knightWalkLeft1.getImage(), Sprite.knightIdleLeft.getImage(), Sprite.knightWalkLeft2.getImage()};
		BufferedImage[] temp2 = {Sprite.knightDeath1.getImage(), Sprite.knightDeath2.getImage(), Sprite.knightDeath3.getImage(), Sprite.knightDeath4.getImage(), 
									Sprite.knightDeath5.getImage(), Sprite.knightDeath6.getImage(), Sprite.knightDeath7.getImage() };
		
		animate = new EnemyAnimate(this, temp, - 16, -16, temp2);
		animate.setAnimSpeed(18);
		animate.deathSpeed = 7;
	}

	@Override
	public void update() {
		checkDeath();
		
		if(!dying) {
			checkPath();
			
			checkMovement();
			
			animate.update();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		if(path != null) {
			for(int i = 0; i < path.size(); i++) {
				g.setColor(Color.red);
				Vector2f tempo = path.get(i).tile;
				g.fillRect((int)(tempo.getX() * 32) - currentTilemap.getXOffset(), (int)(tempo.getY() * 32) - currentTilemap.getYOffset(), 32, 32);
			}
		}
		
		animate.render(g);
	}
	
	private int currentTick = 0;
	private void checkPath() {
		currentTick++;
		if(currentTick > 100000) currentTick = 0;
		
		if(currentTick % 30 == 0) {
			int px = (int)currentState.getPlayer().getX() / 32;
			int py = (int)currentState.getPlayer().getY() / 32; 
			
			Vector2f mobPos = new Vector2f(x / 32, y / 32);
			Vector2f playerPos = new Vector2f(px, py);
			if(Vector2f.getDistance(mobPos, playerPos) > AGGRO_RANGE) return; //aggro range
		
			path = currentState.findPath(mobPos, playerPos);
			currentNode = 1;
		}
	}
	
	private int currentNode = 1;
	private void checkMovement() {
		if(path != null) {
			if(path.size() == 0) return;
			
			if(currentNode > path.size()) currentNode = path.size();
			Vector2f placeToMove = path.get(path.size() - currentNode).tile;
			
			//if the mob makes it close to the node, move to the next node
			if(Math.abs((placeToMove.getX() * 32)  - x) <= 5 && Math.abs((placeToMove.getY() * 32) - y) <= 5) {
				//only move to the next node, if currentNode doesnt surpass or equal the size of the array
				if(path.size() > currentNode)
					currentNode++;
			}
			
			if(placeToMove.getX() * 32 > x) {
				move(speed, 0);
				moveRight = true;
				moveLeft = false;
			} 
			if(placeToMove.getX() * 32 < x) {
				move(-speed, 0);
				moveLeft = true;
				moveRight = false;
			}
			if(placeToMove.getY() * 32 > y) {
				move(0, speed);
				moveDown = true;
				moveUp = false;
			} 
			if(placeToMove.getY() * 32 < y) {
				move(0, -speed);
				moveUp = true;
				moveDown = false;
			}
		}
	}
	
	//does not need collision, because it does not path onto solid blocks
	private void move(float testSpeedX, float testSpeedY) {
		x += testSpeedX;
		y += testSpeedY;
		for (int i = 0; i<= xVals.length-1; i++) {
				xVals[i]  += testSpeedX;
				yVals[i]  += testSpeedY;	
		}
		hitbox.x = (int)x; hitbox.y = (int)y;
	}
	
	private int lifeTime = 180;
	private void checkDeath() {		
		if(dying) {
			lifeTime--;
			if(lifeTime <= 0) {
				removed = true;
			}
		}
		
		if(currentHealth <= 0 && !dying) {
			dying = true;
			
			currentState.getPlayer().giveXp(xpWorth);
		}
	}

}
