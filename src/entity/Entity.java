package entity;

public abstract class Entity {

	protected float x, y;
	protected boolean removed;
	
	public Entity(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	
	//getters
	public boolean getRemoved() {
		return removed;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
}
