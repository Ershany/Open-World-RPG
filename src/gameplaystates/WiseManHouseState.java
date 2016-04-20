package gameplaystates;

import game.Game;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import input.MouseMaster;
import npc.WisemanNPC;
import tiles.InterchangeableDoorTile;
import tiles.Tile;

public class WiseManHouseState extends LevelState {

	public WiseManHouseState(GameStateManager gsm) {
		super(gsm, "/maps/WiseManHouse.bmp", 48 * 32, 36 * 32);
	}

	private Tile tile;
	//checks for any right click interactions, like right clicking to enter a door
	public void checkRightClickInteractions() {
		if(MouseMaster.getMouseB() == 3) {
			//check what was right clicked and act accordingly
			tile = tilemap.getTile(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster.getMouseY() + tilemap.getYOffset());
			
			//this algorithm checks if you right clicked the door, and if you did it will check the position from the center of your body and to the center of the door, if you are close it will execute
			if(tile instanceof InterchangeableDoorTile) {
				if(Math.abs((player.getX() + player.getWidth() / 2) - ((tile.getX() << 5) + 16)) <= 48 &&
				   Math.abs((player.getY() + player.getHeight() / 2) - ((tile.getY() << 5) + 16)) <= 48) {
					//inform the player class that the state is changing
					player.changeInstance();
					gsm.getStates().pop();
					gsm.getStates().push(new StartingIslandState(gsm, 154 * 32, (49 * 32) + 16));
				}
			}
		}
	}
	
	@Override
	public void initSpawn() {
		npcs.add(new WisemanNPC(32 * 48, 32 * 32, 1, this, tilemap));
	}

}
