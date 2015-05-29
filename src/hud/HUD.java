package hud;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import npc.KingNPC;
import npc.WisemanNPC;
import tiles.Tile;
import boss.Kronos;
import enemies.Knight;
import enemies.Slime;
import entity.Mob;
import entity.Player;
import game.Game;
import gfx.Sprite;

public class HUD {

	private Player player;
	private Mob focusedMob;
	
	//player health bar info
	private BufferedImage healthBar;
	private BufferedImage portrait;
	private int healthBarX, healthBarY;
	private String playerName;
	
	//xp bar
	private BufferedImage xpBar;
	private int xp;
	private int currentXp;
	private int xpWidth;
	private boolean showXpBar = true;
	
	//focused mob info
	private BufferedImage focusedPortrait;
	private int focusedHealthBarX, focusedHealthBarY;
	private String focusedName;
	
	//minimap info
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
		portrait = Sprite.playerPortrait.getImage();
		healthBarX = 5;
		healthBarY = 5;
		playerName = player.getName();
		
		//focused init
		if(Game.WIDTH < 1000) focusedHealthBarX = 280;
		else focusedHealthBarX = 350;
		focusedHealthBarY = 5;
		
		//init minimap
		minimapOutline = Sprite.minimapOutline.getImage();
		minimapX = Game.WIDTH - 220;
		minimapY = 30;
		minimapTiles = new Tile[widthAndHeight * widthAndHeight];
		currentTiles = player.getTileMap().getTiles();
		tilemapWidth = player.getTileMap().getWidth();
		
		//init xpBar
		xpBar = Sprite.xpBar.getImage();
		currentXp = player.getCurrentXp();
		xp = player.getXp();
		xpWidth = (currentXp * 381) / xp;
		if(player.getLevel() == Player.MAX_LEVEL) {
			showXpBar = false;
		}
	}
	
	private int currentHealthWidth;
	private int currentPlayerHealth;
	private int playerHealth;
	
	private int currentFocusedHealthWidth;
	private int currentFocusedHealth;
	private int focusedHealth;
	
	private int tilemapWidth;
	
	public void update() {
		//player healthbar updating
		playerHealth = (int)player.getHealth();
		currentPlayerHealth = (int)player.getCurrentHealth();
		currentHealthWidth = (int)(148 * (player.getCurrentHealth() / player.getHealth()));
		
		//focused mob updating (if there is a focused mob get its info if we don't already have it
		if(focusedMob != player.getFocusedMob()) { 
			if(player.getFocusedMob() == null) {
				focusedMob = null;
			}
			else {
				focusedMob = player.getFocusedMob();
				focusedName = focusedMob.getName();
				focusedPortrait = getFocusedMobPortrait();
				currentFocusedHealthWidth = (int)(148 * (focusedMob.getCurrentHealth() / focusedMob.getHealth()));
				currentFocusedHealth = (int)focusedMob.getCurrentHealth();
				focusedHealth = (int)focusedMob.getHealth();
			}
		} 
		else if (focusedMob != null) {
			currentFocusedHealthWidth = (int)(148 * (focusedMob.getCurrentHealth() / focusedMob.getHealth()));
			currentFocusedHealth = (int)focusedMob.getCurrentHealth();
			focusedHealth = (int)focusedMob.getHealth();
		}
		
		//minimap updating
		if(currentTiles != player.getTileMap().getTiles()) currentTiles = player.getTileMap().getTiles();
		if(tilemapWidth != player.getTileMap().getWidth()) tilemapWidth = player.getTileMap().getWidth();
		for(int y = 0; y < widthAndHeight; y++) {
			for(int x = 0; x < widthAndHeight; x++) {
				minimapTiles[x + y * widthAndHeight] = currentTiles[(int)((player.getX() / 32) + x - (widthAndHeight / 2)) + (int)((player.getY() / 32) + y - (widthAndHeight / 2)) * tilemapWidth];
			}
		}
		
		//xp bar updating
		if(currentXp != player.getCurrentXp() || xp != player.getXp()) {
			currentXp = player.getCurrentXp();
			xp = player.getXp();
			xpWidth = (currentXp * 381) / xp;
			if(xpWidth > 381) {
				xpWidth = 381;
			}
			if(player.getLevel() == Player.MAX_LEVEL) {
				showXpBar = false;
			}
		}
	}
	
	public void render(Graphics2D g) {
		//render health bar
		drawHealthBar(g);
		
		//render mini map
		drawMinimap(g);
		
		drawXpBar(g);
	}
	
	private void drawXpBar(Graphics2D g) {
		if(!showXpBar) return;
		
		g.drawImage(xpBar, Game.WIDTH / 2 - 200, Game.HEIGHT - 60, null);
		
		//bar located at     x:Game.WIDTH / 2 - 190   y:Game.HEIGHT - 57    width:381     height:14
		g.setColor(Color.RED);
		g.fillRect(Game.WIDTH / 2 - 190, Game.HEIGHT - 57, xpWidth, 14);
	}
	
	private void drawHealthBar(Graphics2D g) {
		g.setColor(Color.RED);
		g.setFont(font);
		
		//player health bar drawing
		g.drawImage(healthBar, healthBarX, healthBarY, null);
		g.drawImage(portrait, healthBarX + 2, healthBarY + 2, null);
		g.drawString(playerName, healthBarX + 120, healthBarX + 30);
		g.drawString(Integer.toString(player.getLevel()), healthBarX + 3, healthBarY + 95);
		g.fillRect(healthBarX + 100, healthBarY + 50, currentHealthWidth, 48);
		
		//focused mob health bar drawing
		if(focusedMob != null) {
			g.drawImage(healthBar, focusedHealthBarX, focusedHealthBarY, null);
			g.drawImage(focusedPortrait, focusedHealthBarX + 2, focusedHealthBarY + 2, null);
			g.drawString(focusedName, focusedHealthBarX + 120, focusedHealthBarY + 30);
			g.drawString(Integer.toString(focusedMob.getLevel()), focusedHealthBarX + 3, focusedHealthBarY + 95);
			g.fillRect(focusedHealthBarX + 100, focusedHealthBarY + 50, currentFocusedHealthWidth, 48);
		}
		
		//drawing on health bar numbers
		g.setColor(Color.BLACK);
		g.drawString(currentPlayerHealth + "/" + playerHealth, healthBarX + 145, healthBarY + 81);
		if(focusedMob != null) {
			g.drawString(currentFocusedHealth + "/" + focusedHealth, focusedHealthBarX + 145, focusedHealthBarY + 81);
		}
	}

	private void drawMinimap(Graphics2D g) {
		g.drawImage(minimapOutline, minimapX - 15, minimapY - 15, null);
		
		for(int y = 0; y < widthAndHeight; y++) {
			for(int x = 0; x < widthAndHeight; x++) {
				try {
					g.drawImage(minimapTiles[x + y * widthAndHeight].getTileImage(), minimapX + x * minimapDepth, minimapY + y * minimapDepth, minimapDepth, minimapDepth, null);
				} catch(NullPointerException e) {
					//do nothing, it is just because on the first render call the players offset might not be initialized correctly
				}
			}
		}
	}
	
	private BufferedImage getFocusedMobPortrait() {
		if(focusedMob instanceof Slime) {
			return Sprite.slimePortrait.getImage();
		}
		else if(focusedMob instanceof Knight) {
			return Sprite.knightPortrait.getImage();
		}
		else if(focusedMob instanceof KingNPC) {
			return Sprite.kingPortrait.getImage();
		}
		else if(focusedMob instanceof WisemanNPC) {
			return Sprite.wisemanPortrait.getImage();
		}
		else if(focusedMob instanceof Kronos) {
			return Sprite.kronosPortrait.getImage();
		}
		else return null;
	}
	
}
