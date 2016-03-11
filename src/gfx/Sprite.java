package gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	
	//TODO 
	//Develop an actual system to manage objects, so extra memory is not being used up when not needed

	//objects (we can keep them here or init them in the states we use them)
	//or just keep universal stuff like player and such and have rare
	//sprites get initialized in the states initialization 
	
	//logo
	public static Sprite logo = new Sprite("/icons/gameLogo.png");
	public static Sprite splashScreen = new Sprite("/hudStuff/SplashScreen.png");
	
	//player
	public static Sprite playerIdleDown = new Sprite(1, 0, Spritesheet.playerSheet);
	public static Sprite playerWalkDown1 = new Sprite(0, 0, Spritesheet.playerSheet);
	public static Sprite playerWalkDown2 = new Sprite(2, 0, Spritesheet.playerSheet);
	public static Sprite playerIdleUp = new Sprite(1, 1, Spritesheet.playerSheet);
	public static Sprite playerWalkUp1 = new Sprite(0, 1, Spritesheet.playerSheet);
	public static Sprite playerWalkUp2 = new Sprite(2, 1, Spritesheet.playerSheet);
	public static Sprite playerIdleRight = new Sprite(1, 2, Spritesheet.playerSheet);
	public static Sprite playerWalkRight1 = new Sprite(0, 2, Spritesheet.playerSheet);
	public static Sprite playerWalkRight2 = new Sprite(2, 2, Spritesheet.playerSheet);
	public static Sprite playerIdleLeft = new Sprite(1, 3, Spritesheet.playerSheet);
	public static Sprite playerWalkLeft1 = new Sprite(0, 3, Spritesheet.playerSheet);
	public static Sprite playerWalkLeft2 = new Sprite(2, 3, Spritesheet.playerSheet);
	public static Sprite playerHorizontalSwing = new Sprite("/sprites/melee attacks/horizontal.png"); 
	public static Sprite playerVerticalSwing = new Sprite("/sprites/melee attacks/vertical.png");
	
	public static Sprite wasted = new Sprite("/sprites/player/Wasted.png");
	
	//king
	public static Sprite kingIdleDown = new Sprite(1, 3, Spritesheet.kingSheet);
	public static Sprite kingWalkDown1 = new Sprite(0, 3, Spritesheet.kingSheet);
	public static Sprite kingWalkDown2 = new Sprite(2, 3, Spritesheet.kingSheet);
	public static Sprite kingIdleUp = new Sprite(1, 0, Spritesheet.kingSheet);
	public static Sprite kingWalkUp1 = new Sprite(0, 0, Spritesheet.kingSheet);
	public static Sprite kingWalkUp2 = new Sprite(2, 0, Spritesheet.kingSheet);
	public static Sprite kingIdleRight = new Sprite(1, 1, Spritesheet.kingSheet);
	public static Sprite kingWalkRight1 = new Sprite(0, 1, Spritesheet.kingSheet);
	public static Sprite kingWalkRight2 = new Sprite(2, 1, Spritesheet.kingSheet);
	public static Sprite kingIdleLeft = new Sprite(1, 2, Spritesheet.kingSheet);
	public static Sprite kingWalkLeft1 = new Sprite(0, 2, Spritesheet.kingSheet);
	public static Sprite kingWalkLeft2 = new Sprite(2, 2, Spritesheet.kingSheet);
	
	//wiseman
	public static Sprite wisemanIdleUp = new Sprite(1, 0, Spritesheet.wisemanSheet);
	public static Sprite wisemanWalkUp1 = new Sprite(0, 0, Spritesheet.wisemanSheet);
	public static Sprite wisemanWalkUp2 = new Sprite(2, 0, Spritesheet.wisemanSheet);
	public static Sprite wisemanIdleDown = new Sprite(1, 2, Spritesheet.wisemanSheet);
	public static Sprite wisemanWalkDown1 = new Sprite(0, 2, Spritesheet.wisemanSheet);
	public static Sprite wisemanWalkDown2 = new Sprite(2, 2, Spritesheet.wisemanSheet);
	public static Sprite wisemanIdleRight = new Sprite(1, 1, Spritesheet.wisemanSheet);
	public static Sprite wisemanWalkRight1 = new Sprite(0, 1, Spritesheet.wisemanSheet);
	public static Sprite wisemanWalkRight2 = new Sprite(2, 1, Spritesheet.wisemanSheet);
	public static Sprite wisemanIdleLeft = new Sprite(1, 3, Spritesheet.wisemanSheet);
	public static Sprite wisemanWalkLeft1 = new Sprite(0, 3, Spritesheet.wisemanSheet);
	public static Sprite wisemanWalkLeft2 = new Sprite(2, 3, Spritesheet.wisemanSheet);
	
	//knight
	public static Sprite knightIdleUp = new Sprite(1, 3, Spritesheet.knightSheet);
	public static Sprite knightWalkUp1 = new Sprite(0, 3, Spritesheet.knightSheet);
	public static Sprite knightWalkUp2 = new Sprite(2, 3, Spritesheet.knightSheet);
	public static Sprite knightIdleDown = new Sprite(1, 0, Spritesheet.knightSheet);
	public static Sprite knightWalkDown1 = new Sprite(0, 0, Spritesheet.knightSheet);
	public static Sprite knightWalkDown2 = new Sprite(2, 0, Spritesheet.knightSheet);
	public static Sprite knightIdleRight = new Sprite(1, 2, Spritesheet.knightSheet);
	public static Sprite knightWalkRight1 = new Sprite(0, 2, Spritesheet.knightSheet);
	public static Sprite knightWalkRight2 = new Sprite(2, 2, Spritesheet.knightSheet);
	public static Sprite knightIdleLeft = new Sprite(1, 1, Spritesheet.knightSheet);
	public static Sprite knightWalkLeft1 = new Sprite(0, 1, Spritesheet.knightSheet);
	public static Sprite knightWalkLeft2 = new Sprite(2, 1, Spritesheet.knightSheet);
	
	public static Sprite knightDeath1 = new Sprite(0, 0, Spritesheet.knightDeathSheet);
	public static Sprite knightDeath2 = new Sprite(1, 0, Spritesheet.knightDeathSheet);
	public static Sprite knightDeath3 = new Sprite(2, 0, Spritesheet.knightDeathSheet);
	public static Sprite knightDeath4 = new Sprite(3, 0, Spritesheet.knightDeathSheet);
	public static Sprite knightDeath5 = new Sprite(4, 0, Spritesheet.knightDeathSheet);
	public static Sprite knightDeath6 = new Sprite(5, 0, Spritesheet.knightDeathSheet);
	public static Sprite knightDeath7 = new Sprite(6, 0, Spritesheet.knightDeathSheet);
	
	//Kronos (Boss)
	public static Sprite kronosIdleUp = new Sprite(1, 3, Spritesheet.kronosSheet);
	public static Sprite kronosWalkUp1 = new Sprite(0, 3, Spritesheet.kronosSheet);
	public static Sprite kronosWalkUp2 = new Sprite(2, 3, Spritesheet.kronosSheet);
	public static Sprite kronosIdleDown = new Sprite(1, 0, Spritesheet.kronosSheet);
	public static Sprite kronosWalkDown1 = new Sprite(0, 0, Spritesheet.kronosSheet);
	public static Sprite kronosWalkDown2 = new Sprite(2, 0, Spritesheet.kronosSheet);
	public static Sprite kronosIdleRight = new Sprite(1, 2, Spritesheet.kronosSheet);
	public static Sprite kronosWalkRight1 = new Sprite(0, 2, Spritesheet.kronosSheet);
	public static Sprite kronosWalkRight2 = new Sprite(2, 2, Spritesheet.kronosSheet);
	public static Sprite kronosIdleLeft = new Sprite(1, 1, Spritesheet.kronosSheet);
	public static Sprite kronosWalkLeft1 = new Sprite(0, 1, Spritesheet.kronosSheet);
	public static Sprite kronosWalkLeft2 = new Sprite(2, 1, Spritesheet.kronosSheet);
	public static Sprite kronosProjectile = new Sprite("/sprites/boss/Kronos/ranged.bmp");
	
	//tiles
	public static Sprite grass = new Sprite(0, 0, Spritesheet.tileSheet);
	public static Sprite dirt = new Sprite(1, 0, Spritesheet.tileSheet);
	public static Sprite sand = new Sprite(2, 0, Spritesheet.tileSheet);
	public static Sprite redBrick = new Sprite(3, 0, Spritesheet.tileSheet);
	public static Sprite woodFloor = new Sprite(4, 0, Spritesheet.tileSheet);
	public static Sprite stoneFloor = new Sprite(5, 0, Spritesheet.tileSheet);
	public static Sprite woodFence = new Sprite(6, 0, Spritesheet.tileSheet);
	public static Sprite woodBridge = new Sprite(7, 0, Spritesheet.tileSheet);
	
	public static Sprite water1 = new Sprite(0, 1, Spritesheet.tileSheet);
	public static Sprite water2 = new Sprite(1, 1, Spritesheet.tileSheet);
	public static Sprite water3 = new Sprite(2, 1, Spritesheet.tileSheet);
	
	public static Sprite roof1 = new Sprite(0, 2, Spritesheet.tileSheet);
	public static Sprite wall1 = new Sprite(1, 2, Spritesheet.tileSheet);
	public static Sprite door1 = new Sprite(2, 2, Spritesheet.tileSheet);
	
	public static Sprite rockHillTop = new Sprite(0, 3, Spritesheet.tileSheet);
	public static Sprite rockHill = new Sprite(1, 3, Spritesheet.tileSheet);
	public static Sprite rockHillBottom = new Sprite(2, 3, Spritesheet.tileSheet);
	public static Sprite rockCave = new Sprite(3, 3, Spritesheet.tileSheet);
	
	//dungeon tiles 
	public static Sprite dungeonDirt = new Sprite(0, 4, Spritesheet.tileSheet);
	public static Sprite dungeonWall = new Sprite(1, 4, Spritesheet.tileSheet);
	public static Sprite dungeonRock = new Sprite(2, 4, Spritesheet.tileSheet);
	public static Sprite dungeonCave = new Sprite(3, 4, Spritesheet.tileSheet);
	
	//HUD
	public static Sprite healthHUD = new Sprite("/hudStuff/healthBar.bmp");
	public static Sprite minimapOutline = new Sprite("/hudStuff/minimapOutline.bmp");
	public static Sprite textBox = new Sprite("/hudStuff/textdisplay/textbar.png");
	public static Sprite xpBar = new Sprite("/hudStuff/bars/xpBar.png");
	
	//portraits
	public static Sprite playerPortrait = new Sprite("/hudStuff/portraits/playerPortrait.bmp");
	
	public static Sprite slimePortrait = new Sprite("/hudStuff/portraits/slimePortrait.bmp");
	public static Sprite kingPortrait = new Sprite("/hudStuff/portraits/kingPortrait.bmp");
	public static Sprite wisemanPortrait = new Sprite("/hudStuff/portraits/wisemanPortrait.bmp");
	public static Sprite knightPortrait = new Sprite("/hudStuff/portraits/knightPortrait.bmp");
	
	public static Sprite kronosPortrait = new Sprite("/hudStuff/portraits/kronosPortrait.bmp");
	
	//slime
	public static Sprite slime1 = new Sprite(0, 0, Spritesheet.enemySheet);
	public static Sprite slime2 = new Sprite(1, 0, Spritesheet.enemySheet);
	public static Sprite slime3 = new Sprite(2, 0, Spritesheet.enemySheet);
	public static Sprite deathSlime1 = new Sprite(3, 0, Spritesheet.enemySheet);
	public static Sprite deathSlime2 = new Sprite(4, 0, Spritesheet.enemySheet);
	public static Sprite deathSlime3 = new Sprite(5, 0, Spritesheet.enemySheet);
	
	//particles
	public static Sprite heart = new Sprite("/particles/healParticle.png");
	
	//ships
	public static Sprite basicShipIdleUp = new Sprite(1, 0, Spritesheet.basicShipSheet);
	public static Sprite basicShipMoveUp1 = new Sprite(0, 0, Spritesheet.basicShipSheet);
	public static Sprite basicShipMoveUp2 = new Sprite(2, 0, Spritesheet.basicShipSheet);
	public static Sprite basicShipIdleRight = new Sprite(1, 1, Spritesheet.basicShipSheet);
	public static Sprite basicShipMoveRight1 = new Sprite(0, 1, Spritesheet.basicShipSheet);
	public static Sprite basicShipMoveRight2 = new Sprite(2, 1, Spritesheet.basicShipSheet);
	public static Sprite basicShipIdleLeft = new Sprite(1, 2, Spritesheet.basicShipSheet);
	public static Sprite basicShipMoveLeft1 = new Sprite(0, 2, Spritesheet.basicShipSheet);
	public static Sprite basicShipMoveLeft2 = new Sprite(2, 2, Spritesheet.basicShipSheet);
	public static Sprite basicShipIdleDown = new Sprite(1, 3, Spritesheet.basicShipSheet);
	public static Sprite basicShipMoveDown1 = new Sprite(0, 3, Spritesheet.basicShipSheet);
	public static Sprite basicShipMoveDown2 = new Sprite(2, 3, Spritesheet.basicShipSheet);
	
	//environment
	public static Sprite tree1 = new Sprite("/sprites/environment/tree1.png");
	
	
	private Spritesheet sheet;
	private BufferedImage sprite;
	private String path;
	private int x, y;
	
	public Sprite(int x, int y, Spritesheet sheet) {
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		
		load();
	}
	
	public Sprite(String path) {
		try {
			sprite = ImageIO.read(Sprite.class.getResourceAsStream(path));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void load() {
		sprite = sheet.getImage().getSubimage(x * sheet.getSpriteWidth(), y * sheet.getSpriteHeight(), sheet.getSpriteWidth(), sheet.getSpriteHeight());
	}
	
	//getters
	public BufferedImage getImage() {
		return sprite;
	}
}
