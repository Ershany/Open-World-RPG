package util;

public class Vector2f {

	private float x, y;
	
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void add(Vector2f vec) {
		x += vec.getX();
		y += vec.getY();
	}
	
	public float dotProduct(Vector2f vec) {
		return ((vec.getX() * x) + (vec.getY() * y));
	}
	
	//getters
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	
}
