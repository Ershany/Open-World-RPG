package boss;

import java.awt.Graphics2D;

import projectile.Projectile;
import tilemap.Tilemap;
import entity.Mob;
import gamestatemanager.LevelState;

public abstract class Boss extends Mob {

	public Boss(float x, float y, int level, LevelState currentState,
			Tilemap currentTilemap) {
		super(x, y, level, currentState, currentTilemap);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render(Graphics2D g) {
		
	}
	
	public abstract void projectileHit(Projectile p);

}
