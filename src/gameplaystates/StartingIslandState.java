package gameplaystates;

import enemies.Knight;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import input.MouseMaster;
import npc.KingNPC;
import tiles.InterchangeableDoorTile;
import tiles.Tile;

public class StartingIslandState extends LevelState {
	
	public StartingIslandState(GameStateManager gsm, int xSpawn, int ySpawn) {
		super(gsm, "/maps/StartingIsland.bmp", xSpawn, ySpawn);
	}
	
	private Tile tile;
	//checks for any right click interactions, like right clicking to enter a door
	public void checkRightClickInteractions() {
		if(MouseMaster.getMouseB() == 3) {
			//check what was right clicked and act accordingly
			tile = tilemap.getTile(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster.getMouseY() + tilemap.getYOffset());
			
			//this algorithm checks if you right clicked the door, and if you did it will check the position from the center of your body and to the center of the door, if you are close it will execute
			if(tile instanceof InterchangeableDoorTile && tile.getX() == 154 && tile.getY() == 48) {
				if(Math.abs((player.getX() + player.getWidth() / 2) - ((tile.getX() << 5) + 16)) <= 48 &&
				   Math.abs((player.getY() + player.getHeight() / 2) - ((tile.getY() << 5) + 16)) <= 48) {
					//inform the player class that the state is changing
					player.changeInstance();
					gsm.getStates().pop();
					gsm.getStates().push(new WiseManHouseState(gsm));
				}
			}
			else if(tile instanceof InterchangeableDoorTile && tile.getX() == 270 && tile.getY() == 29) {
				if(Math.abs((player.getX() + player.getWidth() / 2) - ((tile.getX() << 5) + 16)) <= 48 &&
					Math.abs((player.getY() + player.getHeight() / 2) - ((tile.getY() << 5) + 16)) <= 48) {
					//inform the player class that the state is changing
					player.changeInstance();
					gsm.getStates().pop();
					gsm.getStates().push(new KronosLairState(gsm));
				}
			}
		}
	}
	
	@Override
	public void initSpawn() {
		npcs.add(new KingNPC(32 * 138, 32 * 50, 10, this, tilemap));
		enemies.add(new Knight(32 * 130, 32 * 50, 5, this, tilemap));
	}
	
}
