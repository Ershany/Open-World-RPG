package gamestatemanager;

import java.awt.Graphics2D;

import ui.MainMenu;
import ui.Menu;


public class MenuExampleState extends GameState {

	Menu menuExample;

	public MenuExampleState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		menuExample = new MainMenu(gsm);
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Graphics2D g) {
		menuExample.render(g);
	}

	@Override
	public void keyPressed(int k) {
		menuExample.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
	}

	@Override
	public void keyTyped(int k) {

	}

}
