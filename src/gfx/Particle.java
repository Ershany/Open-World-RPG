package gfx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

import entity.Entity;

public class Particle extends Entity {

	public enum Type {
		NORTH, SOUTH, EAST, WEST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST;
	}
	
	private Random random = new Random();
	
	private float xa, ya;
	private int particleLife;
	private Color color;
	
	private int updatesGoneBy;
	
	//ensure to pass in the x + xOffset, y + yOffset. 
	public Particle(float x, float y, int particleLife, float speed, Color color) {
		super(x, y);
		this.particleLife = particleLife;
		this.color = color;
		
		xa = (float)random.nextGaussian() * speed;
		ya = (float)random.nextGaussian() * speed;
		updatesGoneBy = 0;
	}
	
	//ensure to pass in the x + xOffset, y + yOffset. 
	//direction orientated particles
	public Particle(float x, float y, int particleLife, float speed, Color color, Type type) {
		super(x, y);
		this.particleLife = particleLife;
		this.color = color;
		
		xa = (float)random.nextGaussian() * speed;
		ya = (float)random.nextGaussian() * speed;
		
		switch(type) {
		case NORTH: 	if(ya > 0) ya *= -1; 							break;
		case EAST: 		if(xa < 0) xa *= -1; 							break;
		case SOUTH: 	if(ya < 0) ya *= -1; 							break;
		case WEST: 		if(xa > 0) xa *= -1; 							break;
		case NORTHEAST: if(ya > 0) ya *= -1;    if(xa < 0) xa *= -1;	break;
		case NORTHWEST: if(ya > 0) ya *= -1;	if(xa > 0) xa *= -1; 	break;
		case SOUTHEAST: if(ya < 0) ya *= -1;    if(xa < 0) xa *= -1;	break;
		case SOUTHWEST: if(ya < 0) ya *= -1;    if(xa > 0) xa *= -1;	break;
		}
		
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
