package gameplaystates;

import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import input.MouseMaster;
import npc.KingNPC;
import npc.WisemanNPC;
import tiles.InterchangeableDoorTile;
import tiles.Tile;

public class DevTestState extends LevelState {
	
	public DevTestState(GameStateManager gsm, String map, int xSpawn, int ySpawn) {
		super(gsm, map, xSpawn, ySpawn);
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
					gsm.getStates().push(new DevTestHouseState(gsm, "/maps/devtestHouse.bmp", 48 * 32, 36 * 32));
				}
			}
		}
	}
	
	@Override
	public void initSpawn() {
		npcs.add(new KingNPC(32 * 138, 32 * 50, 10, this, tilemap));
	}
	
}
