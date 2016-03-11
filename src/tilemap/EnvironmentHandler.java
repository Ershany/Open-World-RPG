package tilemap;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import entity.Player;
import game.Game;
import gfx.Sprite;
import util.Vector2f;

public class EnvironmentHandler {

	private Player player;
	private Vector2f[] trees;
	private String treePath;
	
	public EnvironmentHandler(Player p, String treePath) {
		this.player = p;
		this.treePath = treePath;
		
		init();
	}
	
	private void init() {
		//load trees (Make it read out of a file instead)
		trees = new Vector2f[6];
		trees[0] = new Vector2f(131 << 5, 62 << 5);
		trees[1] = new Vector2f(141 << 5, 49 << 5);
		trees[2] = new Vector2f(126 << 5, 48 << 5);
		trees[3] = new Vector2f(129 << 5, 67 << 5);
		trees[4] = new Vector2f(162 << 5, 65 << 5);
		trees[5] = new Vector2f(123 << 5, 32 << 5);
	}
	
	public void render(Graphics2D g, int xOffset, int yOffset) {
		for(int i = 0; i < trees.length; i++) {
			Vector2f vec = trees[i];
			if(Math.abs(vec.getX() - player.getX()) < Game.WIDTH && Math.abs(vec.getY() - player.getY()) < Game.HEIGHT) {
				g.drawImage(Sprite.tree1.getImage(), (int)(vec.getX() - xOffset), (int)(vec.getY() - yOffset), null);
			}
		}
	}
	
}
