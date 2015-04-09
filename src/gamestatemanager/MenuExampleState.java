package gamestatemanager;

import java.awt.Graphics2D;

import tilemap.Tilemap;
import ui.MainMenu;
import ui.Menu;


public class MenuExampleState extends GameState {

	Menu menuExample;
	Tilemap map;
	
	int time = 0;

	public MenuExampleState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		menuExample = new MainMenu(gsm);
		menuExample.setShowFill(false);
		
		map = new Tilemap("/maps/menuMap.bmp");
		map.setXOffset(1400);
		map.setYOffset(1050);
	}

	@Override
	public void update() {
		if(time < 10000) time = 0;
		else time++;
		
		map.update();
		if(map.getYOffset() >= 5900) map.setYOffset(1050);
		
		if(time % 3 == 0)
			map.setYOffset(map.getYOffset() + 1);
	}

	@Override
	public void render(Graphics2D g) {
		map.render(g);
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
