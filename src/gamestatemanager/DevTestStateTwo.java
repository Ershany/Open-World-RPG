package gamestatemanager;

import java.awt.Graphics2D;

import tilemap.Tilemap;
import tiles.Tile;
import entity.Player;

public class DevTestStateTwo extends GameState {

	//TO DO:
	//-need to read the player from a file, and initialize that.
	//-also make a universal interface for game states, so all of them will have a tilemap, a player, list of mobs, list of particles, and
	//player controls
	
	private Player player;
	private Tilemap tilemap;
	
	public DevTestStateTwo(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		tilemap = new Tilemap("/maps/devtest.bmp");
		player = new Player(88 * Tile.TILESIZE, 20 * Tile.TILESIZE, 1, tilemap);
	}

	@Override
	public void update() {
		tilemap.update();
		player.update();
	}

	@Override
	public void render(Graphics2D g) {
		tilemap.render(g);
		player.render(g);
	}

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
	}

	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
	}

	@Override
	public void keyTyped(int k) {
		player.keyTyped(k);
	}

}
