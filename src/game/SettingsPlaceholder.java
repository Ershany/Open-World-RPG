package game;

import java.awt.Dimension;

public class SettingsPlaceholder {
	public Dimension CURRENT_RESOLUTION;
	public boolean FULLSCREEN;
	public int RES_INDEX;

	public SettingsPlaceholder() {
		CURRENT_RESOLUTION = Settings.CURRENT_RESOLUTION;
		FULLSCREEN = Settings.FULLSCREEN;
		RES_INDEX = Settings.RES_INDEX;
	}

	public void commit () {
		Settings.CURRENT_RESOLUTION = CURRENT_RESOLUTION;
		Settings.FULLSCREEN = FULLSCREEN;
		Settings.RES_INDEX = RES_INDEX;
		Settings.edited = true;
	}
	
}
