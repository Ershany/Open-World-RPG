package gameplaystates;

import boss.Kronos;
import game.Game;
import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import gamestatemanager.LudumDareEndingState;
import input.MouseMaster;
import sfx.AudioPlayer;
import tiles.InterchangeableDoorTile;
import tiles.Tile;

public class KronosLairState extends LevelState {
	
	public KronosLairState(GameStateManager gsm) {
		super(gsm, "/maps/KronosLair.bmp", 45 * 32, 128 * 32);
		AudioPlayer.kronosBattle.play();
	}

	private Tile tile;
	@Override
	public void checkRightClickInteractions() {
		if(MouseMaster.getMouseB() == 3) {
			//check what was right clicked and act accordingly
			tile = tilemap.getTile(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster.getMouseY() + tilemap.getYOffset());
			
			//this algorithm checks if you right clicked the door, and if you did it will check the position from the center of your body and to the center of the door, if you are close it will execute
			if(tile instanceof InterchangeableDoorTile && tile.getX() == 72 && tile.getY() == 29) {
				if(Math.abs((player.getX() + player.getWidth() / 2) - ((tile.getX() << 5) + 16)) <= 48 &&
				   Math.abs((player.getY() + player.getHeight() / 2) - ((tile.getY() << 5) + 16)) <= 48) {
					//inform the player class that the state is changing
					player.changeInstance();
					gsm.getStates().pop();
					gsm.getStates().push(new LudumDareEndingState(gsm));
				}
			}
		}
	}

	@Override
	public void initSpawn() {
		if(Game.hosting || !Game.multiplayer) {
			addBoss(new Kronos(32 * 59 - 16, 32 * 97, this, tilemap));
		}
	}

}
