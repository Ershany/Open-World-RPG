package gameplaystates;

import gamestatemanager.GameStateManager;
import gamestatemanager.LevelState;
import boss.Kronos;

public class KronosLairState extends LevelState {

	public KronosLairState(GameStateManager gsm) {
		super(gsm, "/maps/KronosLair.bmp", 45 * 32, 128 * 32);
	}

	@Override
	public void checkRightClickInteractions() {
		
	}

	@Override
	public void initSpawn() {
		addBoss(new Kronos(32 * 60, 32 * 100, this, tilemap));
	}

}
