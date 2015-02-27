package gamestatemanager;

import java.awt.Graphics2D;

public abstract class GameState {

	protected GameStateManager gsm;

	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}

	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics2D g);


	public abstract void keyPressed(int k);
	public abstract void keyReleased(int k);
	public abstract void keyTyped(int k);

}