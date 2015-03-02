package gamestatemanager;

import java.awt.Graphics2D;

import tilemap.Tilemap;

public class DevTestState extends GameState {

	private Tilemap tilemap;
	
	public DevTestState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		tilemap = new Tilemap("/maps/devtest.bmp");
	}

	public void update() {
		tilemap.update();
	}

	public void render(Graphics2D g) {
		tilemap.render(g);
	}

	public void keyPressed(int k) {
		
	}

	public void keyReleased(int k) {
		
	}

	public void keyTyped(int k) {
		
	}
}
