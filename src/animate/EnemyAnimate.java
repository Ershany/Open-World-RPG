package animate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Mob;

public class EnemyAnimate extends Animate {

	private int anim;
	private int animSpeed = 20; //lower the value, faster the animation
	
	private int deathAnim = 0;
	private int deathAnimXDiff, deathAnimYDiff;
	public int deathSpeed = 5;
	private int currentDeath = 0;
	
	//integer used determine which direciton the NPC was last walking when he/she stopped, so he/she can idle in the correct position
	private int lastHeld = 0; //0 down, 1 left, 2 up, 3 right
	
	//order: 
	//move up     up idle      move up
	//move down   down idle    move down
	//move right  right idle   move right
	//move left   left idle    move left
	private BufferedImage[] images = new BufferedImage[12]; 
	
	//death anim (logical order)
	private BufferedImage[] deathImages;
	
	public EnemyAnimate(Mob mob, BufferedImage[] images, int deathAnimXDiff, int deathAnimYDiff, BufferedImage[] deathImages) {
		super(mob);
		this.deathAnimXDiff = deathAnimXDiff;
		this.deathAnimYDiff = deathAnimYDiff;
		this.images = images;
		this.deathImages = deathImages;
		
		currentSprite = images[4];
	}

	@Override
	public void init() {
		anim = 0;
	}

	@Override
	public void render(Graphics2D g) {
		if(mob.getDying()) {
			deathAnim++;
			if(deathAnim % deathSpeed == 0) currentDeath++;
			if(currentDeath == deathImages.length) {
				currentDeath--;
			}
			
			g.drawImage(deathImages[currentDeath], (int)mob.getX() - mob.getTileMap().getXOffset() + deathAnimXDiff, (int)mob.getY() - mob.getTileMap().getYOffset() + deathAnimYDiff, null);
		}
		else {
			g.drawImage(currentSprite, (int)mob.getX() - mob.getTileMap().getXOffset(), (int)mob.getY() - mob.getTileMap().getYOffset(), null);
		}
	}

	@Override
	public void update() {
		if(anim >= 100000) anim = 0;
		
		if(mob.getMoveUp() || mob.getMoveDown() || mob.getMoveRight() || mob.getMoveLeft()) {
			anim++;
			
			if(mob.getMoveUp()) {
				if(anim % animSpeed == 0 || anim == 1) {
					currentSprite = images[0];
				}
				else if(anim % (animSpeed/2) == 0) {
					currentSprite = images[2];
				}
				lastHeld = 2;
			}
			else if(mob.getMoveDown()) {
				if(anim % animSpeed == 0 || anim == 1) {
					currentSprite = images[3];
				}
				else if(anim % (animSpeed/2) == 0) {
					currentSprite = images[5];
				}
				lastHeld = 0;
			}
			else if(mob.getMoveRight()) {
				if(anim % animSpeed == 0 || anim == 1) {
					currentSprite = images[6];
				}
				else if(anim % (animSpeed/2) == 0) {
					currentSprite = images[8];
				}
				lastHeld = 3;
			} 
			else if(mob.getMoveLeft()) {
				if(anim % animSpeed == 0 || anim == 1) {
					currentSprite = images[9];
				}
				else if(anim % (animSpeed/2) == 0) {
					currentSprite = images[11];
				}
				lastHeld = 1;
			}
			
		} else {
			switch(lastHeld) {
			case 0: currentSprite = images[4]; break;
			case 1: currentSprite = images[10]; break;
			case 2: currentSprite = images[1]; break;
			case 3: currentSprite = images[7]; break;
			}
			anim = 0;
		}
	}

	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}
	
	//setters
	public void setAnimSpeed(int speed) {
		animSpeed = speed;
	}
	
}
