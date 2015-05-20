package spawners;

import enemies.Slime;
import game.Game;
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
	
	int decide; //decides direction
	int levelDecide; //decides level
	public void update() {
		if(currentTilemap == null) return;
		
		//get random values either above/below or east/west of the player
		int randomX = random.nextInt(Game.WIDTH * 2) - Game.WIDTH;
		int randomY = random.nextInt(Game.HEIGHT * 2) - Game.HEIGHT;
		
		//now add on the player position
		randomX += currentState.getPlayer().getX();
		randomY += currentState.getPlayer().getY();
		
		//adding on additionals (checking where the slime is and adding additional onto it) (this is done in a random way, so additionals will be random)
		int decide = random.nextInt(3);
		//additional x only
		if(decide == 0) {
			if(currentState.getPlayer().getX() > randomX) {
				randomX += -Game.WIDTH / 2 - 100;
			}
			else {
				randomX += Game.WIDTH / 2 + 100;
			}
		} //additional y only
		else if(decide == 1) {
			if(currentState.getPlayer().getY() > randomY) {
				randomY += -Game.HEIGHT / 2 - 100;
			}
			else {
				randomY += Game.HEIGHT / 2 + 100;
			}
		} //additional x and y
		else {
			if(currentState.getPlayer().getX() > randomX) {
				randomX += -Game.WIDTH / 2 - 100;
			}
			else {
				randomX += Game.WIDTH / 2 + 100;
			}
			if(currentState.getPlayer().getY() > randomY) {
				randomY += -Game.HEIGHT / 2 - 100;
			}
			else {
				randomY += Game.HEIGHT / 2 + 100;
			}
		}
		
		//spawn the slime, only if it is on a grass tile (and grass around the slime spawn)
		try { 
			if(currentTilemap.getTile(randomX, randomY).getType().equals("Grass") && 
			   currentTilemap.getTile(randomX + 32, randomY).getType().equals("Grass") &&
			   currentTilemap.getTile(randomX + 32, randomY + 32).getType().equals("Grass") &&
			   currentTilemap.getTile(randomX, randomY + 32).getType().equals("Grass")) {
				//spawn either a level 1 or 2 slime (temp, will change in the future to scale with player)
				levelDecide = random.nextInt(3);
				if(levelDecide == 0) {
					currentState.addMob(new Slime(randomX, randomY, 1, currentState, currentTilemap));
				}
				else if(levelDecide == 1){
					currentState.addMob(new Slime(randomX, randomY, 2, currentState, currentTilemap));
				}
				else if(levelDecide == 2) {
					currentState.addMob(new Slime(randomX, randomY, 3, currentState, currentTilemap));
				}
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			return;
		}
	}
}