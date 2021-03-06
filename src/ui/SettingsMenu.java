package ui;

import game.Game;
import game.Settings;
import game.SettingsPlaceholder;
import gamestatemanager.GameStateManager;

import java.awt.Color;

public class SettingsMenu extends Menu {

	SettingsPlaceholder settingsPlaceholder;

	public SettingsMenu(GameStateManager gsm) {
		super(0, 0, Game.WIDTH, Game.HEIGHT, gsm);
	}

	@Override
	protected void buildButtons() {
		settingsPlaceholder = new SettingsPlaceholder();
		Button fullscreen = new Button(gsm, "Fullscreen : "
				+ settingsPlaceholder.FULLSCREEN) {
			@Override
			public void doAction() {
				settingsPlaceholder.FULLSCREEN = !settingsPlaceholder.FULLSCREEN;
				this.setName("Fullscreen : " + settingsPlaceholder.FULLSCREEN);
			}
		};

		Button res = new Button(gsm, "Resolution : "
				+ ((int) settingsPlaceholder.CURRENT_RESOLUTION.getWidth())
				+ " x "
				+ ((int) settingsPlaceholder.CURRENT_RESOLUTION.getHeight())) {
			@Override
			public void doAction() {
				settingsPlaceholder.RES_INDEX++;
				if (settingsPlaceholder.RES_INDEX == Settings.RESOLUTIONS.length) {
					settingsPlaceholder.RES_INDEX = 0;
				}
				settingsPlaceholder.CURRENT_RESOLUTION = Settings.RESOLUTIONS[settingsPlaceholder.RES_INDEX];
				this.setName("Resolution : "
						+ ((int) settingsPlaceholder.CURRENT_RESOLUTION
								.getWidth())
						+ " x "
						+ ((int) settingsPlaceholder.CURRENT_RESOLUTION
								.getHeight()));
			}
		};

		Button apply = new Button(gsm, "Apply") {
			@Override
			public void doAction() {
					settingsPlaceholder.commit();

					Game.frame.setVisible(false);
					Game.currentGame.toRestart = true;
					Game.currentGame.running = false;

			}
		};
		Button back = new Button(gsm, "Back") {
			@Override
			public void doAction() {
				gsm.getStates().pop();
			}
		};

		buttons = new Button[] { fullscreen, res, apply, back };

	}

	@Override
	protected void init() {
		setFillColor(Color.lightGray);
	}

}
