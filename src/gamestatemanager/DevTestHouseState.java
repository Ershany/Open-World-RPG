package gamestatemanager;

import input.MouseMaster;
import tiles.InterchangeableDoorTile;
import tiles.NullTile;
import tiles.Tile;

public class DevTestHouseState extends LevelState {

	public DevTestHouseState(GameStateManager gsm, String mapName, int xSpawn, int ySpawn) {
		super(gsm, mapName, xSpawn, ySpawn);
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
					gsm.getStates().pop();
					gsm.getStates().push(new DevTestState(gsm, "/maps/devtest.bmp", 154 * 32, (49 * 32) + 16));
				}
			}
		}
	}

}
