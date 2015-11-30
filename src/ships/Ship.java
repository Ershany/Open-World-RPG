package ships;

import java.awt.Graphics2D;

import animate.ShipAnimate;
import entity.Player;
import tiles.WaterTile;

public abstract class Ship {

	//Ship stats
	protected float xSpeed, ySpeed;
	public boolean active;
	
	protected ShipAnimate anim;
	protected Player player;
	
	public Ship(Player player) {
		this.player = player;
	} 
	
	public void move(float testSpeedX, float testSpeedY) {
		float newX = player.getX();
		float newY = player.getY();
		float[] newXVals = player.getXVals();
		float[] newYVals = player.getYVals();
		
		if (!(player.getTileMap().getTile((int) (newX +  testSpeedX), (int)(newY + testSpeedY)) instanceof WaterTile)) {
			return;
		}
		for (int i = 0; i<= newXVals.length-1; i++) {
			if (!(player.getTileMap().getTile((int) (newXVals[i] +  testSpeedX), (int)(newYVals[i] + testSpeedY)) instanceof WaterTile)) {
				return;
			}
		}
		
		newX += testSpeedX;
		newY += testSpeedY;
		player.setX(newX);
		player.setY(newY);
		
		for (int i = 0; i<= newXVals.length-1; i++) {
				newXVals[i]  += testSpeedX;
				newYVals[i]  += testSpeedY;	
		}
		player.setXVals(newXVals);
		player.setYVals(newYVals);
		
		player.setXHitBox((int)newX);
		player.setYHitBox((int)newY);
	}
	
	public abstract void update();
	public abstract void shoot();
	
	public abstract void render(Graphics2D g);
	
}