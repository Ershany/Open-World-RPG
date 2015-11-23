package util;

import tiles.Tile;

public class Vector2f {

	private float x, y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f vec) {
		this.x = vec.getX();
		this.y = vec.getY();
	}
	
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void add(Vector2f vec) {
		x += vec.getX();
		y += vec.getY();
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2f vec) {
		this.x = vec.getX();
		this.y = vec.getY();
	}
	
	//Not used very often
	public void changeToPixelPrecision() {
		x = Tile.TILESIZE * x; 
		y = Tile.TILESIZE * y;
	}
	
	public float dotProduct(Vector2f vec) {
		return ((vec.getX() * x) + (vec.getY() * y));
	}
	
	public static double getDistance(Vector2f start, Vector2f goal) {
		double dx = start.getX() - goal.getX();
		double dy = start.getY() - goal.getY();
		return Math.sqrt((dx * dx) + (dy * dy));
	}
	
	public float getLength() {
		return (float) Math.sqrt((this.x * this.x) + (this.y * this.y));
	}
	
	//getters
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
	//Overridden methods
	public boolean equals(Object object) {
		if(!(object instanceof Vector2f)) return false;
		Vector2f vector = (Vector2f)object;
		
		if(vector.getX() == this.x && vector.getY() == this.y) return true;
		return false;
	}
	
}
