package gamestatemanager;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import tilemap.Tilemap;
import tiles.Tile;
import ui.PauseMenu;
import entity.Player;

public class DevTestStateTwo extends LevelState {

	//TO DO:
	//-need to read the player from a file, and initialize that.
	//-also make a universal interface for game states, so all of them will have a tilemap, a player, list of mobs, list of particles, and
	//player controls

	public DevTestStateTwo(GameStateManager gsm, String map) {
		super(gsm, map);
	}
	
}
