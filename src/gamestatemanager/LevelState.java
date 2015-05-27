package gamestatemanager;

import input.MouseMaster;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import projectile.Projectile;
import spawners.SlimeSpawner;
import tilemap.Node;
import tilemap.Tilemap;
import tiles.InterchangeableDoorTile;
import tiles.Tile;
import ui.PauseMenu;
import util.CursorManager;
import util.TextBoxMaster;
import util.Vector2f;
import enemies.Slime;
import entity.Entity;
import entity.Mob;
import entity.Player;
import gfx.Particle;

public abstract class LevelState extends GameState{

	//container
	protected PauseMenu pauseMenu;
	protected TextBoxMaster currentTextBox; //used to output text onto the screen
	
	protected String mapName;
	
	protected Player player;
	protected Tilemap tilemap;
	
	protected List<Particle> particles = new ArrayList<Particle>();
	protected List<Projectile> projectiles = new ArrayList<Projectile>();
	protected List<Mob> enemies = new ArrayList<Mob>();
	protected List<Mob> npcs = new ArrayList<Mob>();
	
	//spawners
	private Random random = new Random();
	private SlimeSpawner slimeSpawner;
	
	//used for sorting nodes
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n1, Node n2) {
			if(n1.fCost < n2.fCost) return -1; 
			if(n1.fCost > n2.fCost) return +1; 
			
			return 0;
		}
	};
	
	public LevelState(GameStateManager gsm, String mapName, int xSpawn, int ySpawn) {
		super(gsm);
		this.mapName = mapName;
		tilemap = new Tilemap(mapName);
		player = new Player(xSpawn, ySpawn, this, tilemap);
		pauseMenu = new PauseMenu(gsm, this, player.getLevelData());
		
		init();
		initSpawn();
	}
	
	@Override
	public void init() {
		slimeSpawner = new SlimeSpawner(this);
	}
	
	@Override
	public void update() {
		if(!paused && currentTextBox == null) {
			tilemap.update();
			player.update();
			
			checkHit();
			checkBulletHit();
			updateLists();
			checkRemoved();
			spawners();
			checkCursor();
			checkRightClickInteractions();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		tilemap.render(g);
		renderLists(g);
		player.render(g);
		
		if(currentTextBox != null) {
			currentTextBox.render(g);
		}
		if (paused) {
			pauseMenu.render(g);
		}
	}
	
	private void updateLists() {
		//loop through and update all of the entities in the lists
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).update();
		}
		for(int i = 0; i < npcs.size(); i++) {
			npcs.get(i).update();
		}
	}

	private void renderLists(Graphics2D g) {
		//loop through and render all of the entities in the lists
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(tilemap.getXOffset(), tilemap.getYOffset(), g);
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g);
		}
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(g);
		}
		for(int i = 0; i < npcs.size(); i++) {
			npcs.get(i).render(g);
		}
	}
	
	private void checkRemoved() {
		//loop through lists and check if the entities should be removed
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).getRemoved()) projectiles.remove(i);
		}
		for(int i = 0; i < particles.size(); i++) {
			if(particles.get(i).getRemoved()) particles.remove(i);
		}
		for(int i = 0; i < enemies.size(); i++) {
			if(enemies.get(i).getRemoved()) enemies.remove(i);
		}
		for(int i = 0; i < npcs.size(); i++) {
			if(npcs.get(i).getRemoved()) npcs.remove(i);
		}
	}
	
	//random chance of spawning a mob
	private void spawners() {
		if(random.nextInt(120) == 0) 
			slimeSpawner.update();
	}
	
	private Projectile tempP;
	private Mob tempM;
	//checks for things like bullet collision
	private void checkBulletHit() {
		//if there are no projectiles, do not bother checking for collision
		if(projectiles.size() == 0) return;
		
		for(int i = 0; i < projectiles.size(); i++) {
			for(int j = 0; j < enemies.size(); j++) {
				tempP = projectiles.get(i);
				tempM = enemies.get(j);
					
				//if the projectile hits the enemy
				if((tempP.getHitbox().intersects(tempM.getHitbox()) && !tempM.getDying()) || (tempM.getHitbox().contains(tempP.getHitbox()) && !tempM.getDying())) {
					tempM.hit(tempP.getDamage());
					tempP.setRemoved(true);
				}
			}
		}
	}
	
	//melee collision
	private void checkHit() {
		//check if an enemy hits you
		for(int i = 0; i < enemies.size(); i++) {
			//check if there is a player
			if(player == null) return;
			
			Mob temp = enemies.get(i);
			
			//slime
			if(temp instanceof Slime) {
				if(temp.getHitbox().intersects(player.getHitbox()) && !temp.getDying()) player.hit(temp.getDamage());
			}
		}
	}
	
	//checks to see if the mouse is over anything that should change the cursor
	private void checkCursor() {
		//check for doors
		if(tilemap.getTile(MouseMaster.getMouseX() + tilemap.getXOffset(), MouseMaster.getMouseY() + tilemap.getYOffset()) instanceof InterchangeableDoorTile) {
			if(CursorManager.getCursor() != 2) {
				CursorManager.setCursor(2);
			}
		}
		else {
			//if the player is in melee form (check cursor then)
			if(CursorManager.getCursor() != 1 && player.getRangedForm() == false) {
				CursorManager.setCursor(1);
			}
			//if the player is ranged form (check cursor then)
			else if (CursorManager.getCursor() != 3 && player.getRangedForm()){
				CursorManager.setCursor(3);
			}
		}
	}
	
	//when the player hits 'f', this method gets called and checks for the closest NPC, and if that NPC is close enough to talk to, it will talk to that NPC
	private void checkInteraction() {
		//stats for the for loop
		Entity closestNPC = null;
		double closestDistance = 51;
		
		for(int i = 0; i < npcs.size(); i++) {
			//use trig to get the hypot for the distance (can use pythagoreoms theroem because it doesn't matter if it is positive or negative, we just want the smallest value)
			double xDiff = (npcs.get(i).getX() + npcs.get(i).getWidth() / 2) - (player.getX() + player.getWidth() / 2); 
			double yDiff = (npcs.get(i).getY() + npcs.get(i).getHeight() / 2) - (player.getY() + player.getHeight() / 2);
			double distance = Math.sqrt(((xDiff * xDiff) + (yDiff * yDiff))); 
			
			//now check if the distance is less then the closest distance
			if(closestDistance > distance) {
				closestDistance = distance;
				closestNPC = npcs.get(i);
			}
		}
		
		if(closestNPC == null) return;
		
		//make the player focus that NPC, so the player knows which NPC he is talking to
		player.setFocusedMob((Mob) closestNPC);
		player.hud.update(); //update the hud to focus the NPC
		
		//now interact with the closest NPC, if it got to this point, a close NPC was found
		currentTextBox = new TextBoxMaster(closestNPC.getInfo());
	}
	
	//used in A* (nodes are on tiles)
	public List<Node> findPath(Vector2f start, Vector2f goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, Vector2f.getDistance(start, goal));
		openList.add(current);
		
		while(openList.size() > 0) {
			Collections.sort(openList, nodeSorter); //sorts the nodes from shortest to smaller
			current = openList.get(0);
			if(current.tile.equals(goal)) { //if the current node is the goal, return the path
				//return the goal path
				List<Node> path = new ArrayList<Node>();
				while(current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				//Java's GC might not collect this if we don't clear the list
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			//now lets consider the adjacent tiles (dont add previous/parent nodes or solid tiles)
			for(int i = 0; i < 9; i++) { //8 adjacent tiles
				if(i == 4) continue; //tile were on (don't add it)
				int x = (int)current.tile.getX();
				int y = (int)current.tile.getY();
				int xi = (i % 3) - 1; //will result in -1 0 or 1
				int yi = (i / 3) - 1; //will result in -1 0 or 1 (% would mess with order) 
				Tile at = tilemap.getTile((x + xi) * Tile.TILESIZE, (y + yi) * Tile.TILESIZE);
				if(at == null) continue;
				if(at.getSolid()) continue;
				Vector2f atVec = new Vector2f(x + xi, y + yi);
				double gCost = current.gCost + Vector2f.getDistance(current.tile, atVec);
				double hCost = Vector2f.getDistance(start, goal);
				Node node = new Node(atVec, current, gCost, hCost);
				if(vecInList(closedList, atVec) && gCost >= node.gCost) continue; //if the vector is in the closed list, and the gCost is greater or equal to the current gCost, don't bother with it
				if(!vecInList(openList, atVec) || gCost < node.gCost) openList.add(node); //make sure there is not a duplicate before adding
			}
		}
		//if no path is available, return null and the mob can deal with it
		closedList.clear();
		return null;
	}
	
	//check if the vector is in the list
	private boolean vecInList(List<Node> list, Vector2f vector) {
		for(Node n: list) {
			if(n.tile.equals(vector)) return true;
		}
		return false; 
	}
	
	//absract methods
	public abstract void checkRightClickInteractions();
	public abstract void initSpawn();
	

	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
		
		//interact
		if(k == KeyEvent.VK_F) {
			checkInteraction();
		}
		if(k == KeyEvent.VK_P || k == KeyEvent.VK_ESCAPE) {
			paused = !paused;
		}
		if(k == KeyEvent.VK_SPACE) {
			currentTextBox = null;
		}
		if(paused) {
			pauseMenu.keyPressed(k);
		}
	}
	
	@Override
	public void keyReleased(int k) {
		player.keyReleased(k);
	}

	@Override
	public void keyTyped(int k) {
		player.keyTyped(k);
	}
	
	//getters
	public Tilemap getTilemap() {
		return tilemap;
	}
	public Player getPlayer() {
		return player;
	}
	public List<Mob> getEnemies() {
		return enemies;
	}
	public List<Mob> getNPCs() {
		return npcs;
	}
	public TextBoxMaster getCurrentTextBox() {
		return currentTextBox;
	}
	public boolean getPaused() {
		return paused;
	}
	public GameStateManager getGSM() {
		return gsm;
	}
	
	//adding
	public void addParticle(Particle p) {
		particles.add(p);
	}
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	public void addMob(Mob m) {
		enemies.add(m);
	}
	public void addNPC(Mob m) {
		npcs.add(m);
	}
}
