package gamestatemanager;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import projectile.Projectile;
import spawners.SlimeSpawner;
import tilemap.Tilemap;
import tiles.Tile;
import ui.PauseMenu;
import entity.Mob;
import entity.Player;
import gfx.Particle;

public abstract class LevelState extends GameState{

	//container
	private PauseMenu pauseMenu;
	
	private String mapName;
	
	private Player player;
	private Tilemap tilemap;
	
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Mob> enemies = new ArrayList<Mob>();
	
	//temp
	private SlimeSpawner slimeSpawner;
	
	public LevelState(GameStateManager gsm, String mapName) {
		super(gsm);
		this.mapName = mapName;
		tilemap = new Tilemap(mapName);
		
		init();
	}
	
	@Override
	public void init() {
		//temp init for testing
		player = new Player(88 * Tile.TILESIZE, 20 * Tile.TILESIZE, 1, this, tilemap);
		slimeSpawner = new SlimeSpawner(this);
		
		pauseMenu = new PauseMenu(gsm, this);
	}
	
	@Override
	public void update() {
		if(!paused) {
			tilemap.update();
			player.update();
			
			checkHit();
			updateLists();
			checkRemoved();
			spawners();
		}
	}
	
	@Override
	public void render(Graphics2D g) {
		tilemap.render(g);
		player.render(g);
		
		renderLists(g);
		
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
	}

	private void renderLists(Graphics2D g) {
		//loop through and render all of the entities in the lists
		for(int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(g);
		}
		for(int i = 0; i < particles.size(); i++) {
			particles.get(i).render(tilemap.getXOffset(), tilemap.getYOffset(), g);
		}
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(g);
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
	}
	
	private void spawners() {
		slimeSpawner.update();
	}
	
	//checks for things like bullet collision
	private void checkHit() {
		//if there are no projectiles, do not bother checking for collision
		if(projectiles.size() == 0) return;
		for(int i = 0; i < projectiles.size(); i++) {
			for(int j = 0; j < enemies.size(); j++) {
				Projectile tempP = projectiles.get(i);
				Mob tempM = enemies.get(j);
				
				//if the projectile hits the enemy
				if(tempP.getHitbox().intersects(tempM.getHitbox())) {
					tempM.hit(tempP.getDamage());
					projectiles.remove(i);
					
					//if the list of projecitles is now empty, get out of this method
					if(projectiles.size() == 0) return;
				}
			}
		}
	}
	
	@Override
	public void keyPressed(int k) {
		player.keyPressed(k);
		
		if (k == KeyEvent.VK_P || k == KeyEvent.VK_ESCAPE) {
			paused = !paused;

		}
		if (paused) {
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
	
	//adding
	public void addParticle(Particle p) {
		particles.add(p);
	}
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	public void addMob(Mob b) {
		enemies.add(b);
	}
}
