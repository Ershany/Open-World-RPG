package gamestatemanager;

import game.Game;
import gfx.Particle;
import input.MouseMaster;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import tilemap.Tilemap;

public class DevTestState extends GameState {

	private Tilemap tilemap;
	
	private List<Particle> particles = new ArrayList<Particle>();
	
	public DevTestState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		tilemap = new Tilemap("/maps/devtest.bmp");
	}

	public void update() {
		tilemap.update();
		
		//update particles and check if they should be removed, if so remove them
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
			if(particles.get(i).getRemoved()) particles.remove(i);
		}
		
		//offset with mouse
		if(MouseMaster.getMouseX() > Game.WIDTH - (Game.WIDTH / 5)) tilemap.addOffset(5, 0);
		else if(MouseMaster.getMouseX() < Game.WIDTH / 5) tilemap.addOffset(-5, 0);
		if(MouseMaster.getMouseY() < Game.HEIGHT / 5) tilemap.addOffset(0, -5);
		else if(MouseMaster.getMouseY() > Game.HEIGHT - (Game.HEIGHT / 5)) tilemap.addOffset(0, 5);
		
		//particles
		if(MouseMaster.getMouseB() == 1) {
			particles.add(new Particle(MouseMaster.getMouseX(), MouseMaster.getMouseY(), 120, Color.RED));
		}
	}

	public void render(Graphics2D g) {
		tilemap.render(g);
		
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(g);
		}
	}

	public void keyPressed(int k) {
		
	}

	public void keyReleased(int k) {
		
	}

	public void keyTyped(int k) {
		
	}
}
