package gamestatemanager;

import game.Game;
import gfx.Particle;
import input.MouseMaster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import tilemap.Tilemap;
import ui.PauseMenu;

public class DevTestState extends GameState {

	private Tilemap tilemap;
	private boolean paused;
	private PauseMenu pauseMenu;
	private List<Particle> particles = new ArrayList<Particle>();
	
	private int offsetSpeed = 8;

	public DevTestState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		tilemap = new Tilemap("/maps/devtest.bmp");
		pauseMenu = new PauseMenu(gsm, this);
	}

	public void update() {
		if (!paused) {
			tilemap.update();

			// update particles and check if they should be removed, if so
			// remove them
			for (int i = 0; i < particles.size(); i++) {
				particles.get(i).update();
				if (particles.get(i).getRemoved())
					particles.remove(i);
			}

			// offset with mouse
			if (MouseMaster.getMouseX() > Game.WIDTH - (Game.WIDTH / 5))
				tilemap.addOffset(offsetSpeed, 0);
			else if (MouseMaster.getMouseX() < Game.WIDTH / 5)
				tilemap.addOffset(-offsetSpeed, 0);
			if (MouseMaster.getMouseY() < Game.HEIGHT / 5)
				tilemap.addOffset(0, -offsetSpeed);
			else if (MouseMaster.getMouseY() > Game.HEIGHT - (Game.HEIGHT / 5))
				tilemap.addOffset(0, offsetSpeed);

			// particles
			if (MouseMaster.getMouseB() == 1) {
				particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
						.getMouseY() + tilemap.getYOffset(), 120, Color.RED));
			}
		}
	}

	public void render(Graphics2D g) {
		tilemap.render(g);

		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(tilemap.getXOffset(), tilemap.getYOffset(), g);
		}

		if (paused) {
			pauseMenu.render(g);
		}
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_P || k == KeyEvent.VK_ESCAPE) {
			paused = !paused;

		}
		if (paused) {
			pauseMenu.keyPressed(k);
		}
	}

	public void keyReleased(int k) {

	}

	public void keyTyped(int k) {

	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
