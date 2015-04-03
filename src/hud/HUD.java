package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import tiles.NullTile;
import tiles.Tile;
import entity.Player;
import game.Game;
import gfx.Sprite;

public class HUD {

	private Player player;
	
	private BufferedImage healthBar;
	private int healthBarX, healthBarY;
	
	private BufferedImage minimap;
	private BufferedImage minimapOutline;
	private int minimapX, minimapY;
	private Tile[] minimapTiles;
	private Tile[] currentTiles;
	private int minimapDepth = 5;
	private int widthAndHeight = 40;
	
	private Font font;
	
	public HUD(Player player) {
		this.player = player;
		
		init();
	}
	
	private void init() {
		font = new Font("Arial", Font.PLAIN, 20);
		
		//init health bar
		healthBar = Sprite.healthHUD.getImage();
		healthBarX = Game.WIDTH / 30;
		healthBarY = Game.HEIGHT / 30;
		
		//init minimap
		minimap = Sprite.minimap.getImage();
		minimapOutline = Sprite.minimapOutline.getImage();
		minimapX = Game.WIDTH - 220;
		minimapY = 30;
		minimapTiles = new Tile[widthAndHeight * widthAndHeight];
		currentTiles = player.getTileMap().getTiles();
		tilemapWidth = player.getTileMap().getWidth();
	}
	
	private int currentHealthWidth;
	private int currentPlayerHealth;
	private int playerHealth;
	
	private int tilemapWidth;
	
	public void update() {
		//player healthbar updating
		currentHealthWidth = (int)(150 * (player.getCurrentHealth() / player.getHealth()));
		currentPlayerHealth = (int)player.getCurrentHealth();
		playerHealth = (int)player.getHealth();
		
		
		if(currentTiles != player.getTileMap().getTiles()) currentTiles = player.getTileMap().getTiles();
		if(tilemapWidth != player.getTileMap().getWidth()) tilemapWidth = player.getTileMap().getWidth();
		for(int y = 0; y < widthAndHeight; y++) {
			for(int x = 0; x < widthAndHeight; x++) {
				minimapTiles[x + y * widthAndHeight] = currentTiles[(int)((player.getX() / 32) + x - (widthAndHeight / 2)) + (int)((player.getY() / 32) + y - (widthAndHeight / 2)) * tilemapWidth];
			}
		}
	}
	
	public void render(Graphics2D g) {
		//render health bar
		drawHealthBar(g);
		
		//render mini map
		drawMinimap(g);
	}
	
	
	private void drawHealthBar(Graphics2D g) {
		g.setColor(Color.RED);
		
		g.fillRect(healthBarX + 20, healthBarY + 57, currentHealthWidth, 30);
		
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(currentPlayerHealth + "/" + playerHealth, healthBarX + 60, healthBarY + 81);
		
		g.drawImage(healthBar, healthBarX, healthBarY, null);
	}

	private void drawMinimap(Graphics2D g) {
		g.drawImage(minimapOutline, minimapX - 15, minimapY - 15, null);
		g.drawImage(minimap, minimapX, minimapY, null);
		
		for(int y = 0; y < widthAndHeight; y++) {
			for(int x = 0; x < widthAndHeight; x++) {
				try {
					g.drawImage(minimapTiles[x + y * widthAndHeight].getTileImage(), minimapX + x * minimapDepth, minimapY + y * minimapDepth, minimapDepth, minimapDepth, null);
					//g.setColor(Color.RED);
					//g.fillRect(minimapX + x * minimapDepth, minimapY + y * minimapDepth, minimapDepth, minimapDepth);
				} catch(NullPointerException e) {
					//do nothing, it is just because on the first render call the players offset might not be initialized correctly
				}
			}
		}
	}
	
}
