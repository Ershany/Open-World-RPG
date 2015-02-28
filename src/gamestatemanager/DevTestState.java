package gamestatemanager;

import gfx.Sprite;

import java.awt.Graphics2D;

public class DevTestState extends GameState {

	public DevTestState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		
	}

	public void update() {
		
	}

	public void render(Graphics2D g) {
		g.drawImage(Sprite.sprite1.getImage(), 50, 50, null);
		g.drawImage(Sprite.sprite2.getImage(), 100, 50, null);
		g.drawImage(Sprite.sprite3.getImage(), 150, 50, null);
		g.drawImage(Sprite.sprite4.getImage(), 200, 50, null);
	}

	public void keyPressed(int k) {
		
	}

	public void keyReleased(int k) {
		
	}

	public void keyTyped(int k) {
		
	}
}
