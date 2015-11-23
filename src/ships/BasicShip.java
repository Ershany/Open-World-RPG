package ships;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import animate.ShipAnimate;
import entity.Player;
import gfx.Sprite;

public class BasicShip extends Ship {

	public BasicShip(Player player) {
		super(player);
		
		//init the ship
		xSpeed = 4.4f;
		ySpeed = 4.4f;
		
		//init the animation for the ship
		anim = new ShipAnimate(player, new BufferedImage[]{
				Sprite.basicShipMoveUp1.getImage(), Sprite.basicShipIdleUp.getImage(), Sprite.basicShipMoveUp2.getImage(),
				Sprite.basicShipMoveDown1.getImage(), Sprite.basicShipIdleDown.getImage(), Sprite.basicShipMoveDown2.getImage(),
				Sprite.basicShipMoveRight1.getImage(), Sprite.basicShipIdleRight.getImage(), Sprite.basicShipMoveRight2.getImage(),
				Sprite.basicShipMoveLeft1.getImage(), Sprite.basicShipIdleLeft.getImage(), Sprite.basicShipMoveLeft2.getImage()});
	}

	@Override
	public void update() {
		//movement
		checkMovement();
		
		//animate
		anim.update();
	}

	@Override
	public void shoot() {
		
	}

	@Override
	public void render(Graphics2D g) {
		anim.render(g, player.getTileMap().getXOffset(), player.getTileMap().getYOffset());
	}
	
	private void checkMovement() {
		if(player.getMoveUp()) {
			super.move(0, -ySpeed);
		} else if(player.getMoveDown()) {
			super.move(0, ySpeed);
		} else if(player.getMoveLeft()) {
			super.move(-xSpeed, 0);
		} else if(player.getMoveRight()) {
			super.move(xSpeed, 0);
		}
	}

}
