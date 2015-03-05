package gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import entity.Entity;

public class Particle extends Entity {

	private Random random = new Random();
	
	private float xa, ya;
	private int particleLife;
	private Color color;
	
	private int updatesGoneBy;
	
	//ensure to pass in the x + xOffset, y + yOffset. 
	public Particle(float x, float y, int particleLife, Color color) {
		super(x, y);
		this.particleLife = particleLife;
		this.color = color;
		
		xa = (float)random.nextGaussian();
		ya = (float)random.nextGaussian();
		updatesGoneBy = 0;
	}
	
	public void update() {
		x += xa;
		y += ya;
		
		updatesGoneBy++;
		if(updatesGoneBy >= particleLife) removed = true;
	}
	
	public void render(int xOffset, int yOffset, Graphics2D g) {
		g.setColor(color);
		g.fillRect((int) x - xOffset, (int) y - yOffset, 2, 2);
	}
}
