package animate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import entity.Mob;
import gfx.Sprite;

public class PlayerAnimate extends Animate {

	private boolean up, down, left, right;
	private int anim;
	private int animSpeed = 20; //lower the value, faster the animation
	
	public PlayerAnimate(Mob mob) {
		super(mob);
	}

	@Override
	public void init() {
		currentSprite = Sprite.playerIdleDown.getImage();
		anim = 0;
	}

	@Override
	public void render(Graphics2D g) {
		g.drawImage(currentSprite, (int)mob.getX() - mob.getTileMap().getXOffset(), (int)mob.getY() - mob.getTileMap().getYOffset(), null);
	}

	@Override
	public void update() {
		//if the user is moving, up the anim variable, if they are not moving, reset it!
		if(up || down || right || left) {
			anim++;
			
			if(up) {
				if(anim % animSpeed == 0 || anim == 1) {
					currentSprite = Sprite.playerWalkUp1.getImage();
				}
				else if(anim % (animSpeed/2) == 0) {
					currentSprite = Sprite.playerWalkUp2.getImage();
				}
			}
			else if(down) {
				if(anim % animSpeed == 0 || anim == 1) {
					currentSprite = Sprite.playerWalkDown1.getImage();
				}
				else if(anim % (animSpeed/2) == 0) {
					currentSprite = Sprite.playerWalkDown2.getImage();
				}
			}
			else if(right) {
				if(anim % animSpeed == 0 || anim == 1) {
					currentSprite = Sprite.playerWalkRight1.getImage();
				}
				else if(anim % (animSpeed/2) == 0) {
					currentSprite = Sprite.playerWalkRight2.getImage();
				}
			} 
			else if(left) {
				if(anim % animSpeed == 0 || anim == 1) {
					currentSprite = Sprite.playerWalkLeft1.getImage();
				}
				else if(anim % (animSpeed/2) == 0) {
					currentSprite = Sprite.playerWalkLeft2.getImage();
				}
			}
			
		} else {
			anim = 0;
		}
	}

	@Override
	public void keyPressed(int k) {
		switch(k) {
		case KeyEvent.VK_W: up = true; break;
		case KeyEvent.VK_S: down = true; break;
		case KeyEvent.VK_D: right = true; break;
		case KeyEvent.VK_A: left = true; break;
		}
	}

	@Override
	public void keyReleased(int k) {
		switch(k) {
		case KeyEvent.VK_W: up = false; currentSprite = Sprite.playerIdleUp.getImage(); break;
		case KeyEvent.VK_S: down = false; currentSprite = Sprite.playerIdleDown.getImage(); break;
		case KeyEvent.VK_D: right = false; currentSprite = Sprite.playerIdleRight.getImage(); break;
		case KeyEvent.VK_A: left = false; currentSprite = Sprite.playerIdleLeft.getImage(); break;
		}
	}
	
}
