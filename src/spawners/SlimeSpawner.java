package spawners;

import entity.Slime;
import gamestatemanager.LevelState;

import java.util.Random;

import tilemap.Tilemap;

public class SlimeSpawner {

	private Random random = new Random();
	
	private LevelState currentState;
	private Tilemap currentTilemap;
	
	public SlimeSpawner(LevelState currentState) {
		this.currentState = currentState;
		currentTilemap = currentState.getTilemap();
	}
	
	public void update() {
		if(random.nextInt(180) == 0) 
			currentState.addMob(new Slime(currentState.getPlayer().getX() + (random.nextInt(1000) - 500), currentState.getPlayer().getY() - (random.nextInt(1000) - 500), 1, currentState, currentTilemap));
	}
	
}
