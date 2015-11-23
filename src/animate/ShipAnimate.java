package animate;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Player;

public class ShipAnimate {
	
	//order: 
	//move up     up idle      move up
	//move down   down idle    move down
	//move right  right idle   move right
	//move left   left idle    move left
	private BufferedImage[] images; //Should contain 12 images
	private BufferedImage currentSprite;
	private Player player;
	
	public ShipAnimate(Player player, BufferedImage[] images) {
		this.player = player;
		this.images = images; 
		currentSprite = images[8];
	}
	
	public void update() {
		if(player.getMoveUp()) {
			
		}
		if(player.getMoveDown()) {
			
		}
		if(player.getMoveRight()) {
			
		}
		if(player.getMoveLeft()) {
			
		}
	}
	
	public void render(Graphics2D g, int xOffset, int yOffset) {
		g.drawImage(currentSprite, (int)player.getX() - xOffset, (int)player.getY() - yOffset, null);
	}
	
}
