package gamestatemanager;

import game.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import ui.ControlsMenu;

public class ControlsState extends GameState {

	private ControlsMenu controlsMenu;
	
	private Font font;
	
	public ControlsState(GameStateManager gsm) {
		super(gsm);
		controlsMenu = new ControlsMenu(gsm);
	}

	@Override
	public void init() {
		font = new Font("Algerian", Font.PLAIN, 30);
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g) {
		controlsMenu.render(g);
		
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("Move Up:       W", Game.WIDTH / 2 - 200, 260);
		g.drawString("Move Down:  S", Game.WIDTH / 2 - 200, 285);
		g.drawString("Move Right: D", Game.WIDTH / 2 - 200, 310);
		g.drawString("Move Left:   A", Game.WIDTH / 2 - 200, 335);
		g.drawString("Attack:  Left Click", Game.WIDTH / 2 - 200, 360);
		g.drawString("Focus Target: Right Click", Game.WIDTH / 2 - 200, 385);
		g.drawString("Opening Doors: Right Click", Game.WIDTH / 2 - 200, 410);
		g.drawString("Menu: Escape Key", Game.WIDTH / 2 - 200, 435);
		g.drawString("Form Swap: MouseWheel", Game.WIDTH / 2 - 200, 460);
	}

	@Override
	public void keyPressed(int k) {
		controlsMenu.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void keyTyped(int k) {
		
	}

}
