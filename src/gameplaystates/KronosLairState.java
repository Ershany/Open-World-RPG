package gameplaystates;

import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import sfx.AudioPlayer;
import boss.Kronos;

public class KronosLairState extends LevelState {

	public AudioPlayer music;
	
	public KronosLairState(GameStateManager gsm) {
		super(gsm, "/maps/KronosLair.bmp", 45 * 32, 128 * 32);
		music = new AudioPlayer("/sfx/songs/Kronos Battle.mp3", true);
		music.play();
	}

	@Override
	public void checkRightClickInteractions() {
		
	}

	@Override
	public void initSpawn() {
		addBoss(new Kronos(32 * 60, 32 * 100, this, tilemap));
	}

}
