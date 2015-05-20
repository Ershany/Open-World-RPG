package io;

import java.io.Serializable;

import entity.Player;

public class LevelData implements Serializable {

	private static final long serialVersionUID = 3152674545426627041L;
	
	transient private Player player;
	public int level;
	public int xp;
	
	public LevelData(Player player) {
		this.player = player;
	}
	
	public void initPlayer(Player player) {
		this.player = player;
	}
	
	public void update() {
		try {
			level = player.getLevel();
			xp = player.getCurrentXp();
		} catch(NullPointerException e) {
			//do nothing, it is just because LevelData could initialized one tick earlier then the player
		}
	}
	
}
