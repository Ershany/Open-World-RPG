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
	private boolean upPressed, downPressed, rightPressed, leftPressed;
	
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
			if (MouseMaster.getMouseX() > Game.WIDTH - 5)
				tilemap.addOffset(offsetSpeed, 0);
			else if (MouseMaster.getMouseX() < 5)
				tilemap.addOffset(-offsetSpeed, 0);
			if (MouseMaster.getMouseY() < 5)
				tilemap.addOffset(0, -offsetSpeed);
			else if (MouseMaster.getMouseY() > Game.HEIGHT -5)
				tilemap.addOffset(0, offsetSpeed);

			// particle testing
			if (MouseMaster.getMouseB() == 1) {
				if(upPressed && rightPressed) {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED, Particle.Type.NORTHEAST));
				}
				else if(upPressed && leftPressed) {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED, Particle.Type.NORTHWEST));
				}
				else if(downPressed && leftPressed) {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED, Particle.Type.SOUTHWEST));
				}
				else if(downPressed && rightPressed) {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED, Particle.Type.SOUTHEAST));
				}
				else if(upPressed) {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED, Particle.Type.NORTH));
				}
				else if(downPressed) {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED, Particle.Type.SOUTH));
				}
				else if(rightPressed) {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED, Particle.Type.EAST));
				}
				else if(leftPressed) {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED, Particle.Type.WEST));
				}
				else {
					particles.add(new Particle(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster
							.getMouseY() + tilemap.getYOffset(), 20, 1.3f, Color.RED));
				}
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
		
		//check to see if arrow keys are held
		if(k == KeyEvent.VK_W) upPressed = true;
		if(k == KeyEvent.VK_S) downPressed = true;
		if(k == KeyEvent.VK_D) rightPressed = true;
		if(k == KeyEvent.VK_A) leftPressed = true; 
	}

	public void keyReleased(int k) {
		//check to see if arrow keys are released
		if(k == KeyEvent.VK_W) upPressed = false;
		if(k == KeyEvent.VK_S) downPressed = false;
		if(k == KeyEvent.VK_D) rightPressed = false;
		if(k == KeyEvent.VK_A) leftPressed = false; 
	}

	public void keyTyped(int k) {

	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
