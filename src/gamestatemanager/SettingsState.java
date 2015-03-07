package gamestatemanager;

import game.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import ui.SettingsMenu;

public class SettingsState extends GameState {



	SettingsMenu settingsMenu;
	
	
	public SettingsState(GameStateManager gsm) {
		super(gsm);
		settingsMenu = new SettingsMenu(gsm);
	}

	public void init() {
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
	settingsMenu.render(g);
	}
	
	
	/*---------------------Key Listener Methods--------------------------*/
	public void keyPressed(int k) {
		settingsMenu.keyPressed(k);;
	}
	public void keyReleased(int k) {
		
	}
	public void keyTyped(int k) {
		
	}
	
}