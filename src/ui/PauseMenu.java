package ui;

import java.awt.Color;
import java.awt.Font;

import game.Game;
import gamestatemanager.DevTestState;
import gamestatemanager.GameStateManager;
import gamestatemanager.SettingsState;

public class PauseMenu extends Menu {

	DevTestState dts;

	public PauseMenu(GameStateManager gsm, DevTestState dts) {
		super(Game.WIDTH / 3, Game.HEIGHT / 3, Game.WIDTH / 3, Game.HEIGHT / 3,
				gsm);
		this.dts = dts;
	}

	@Override
	protected void buildButtons() {
		Button play = new Button(gsm, "Resume") {
			@Override
			public void doAction() {
				dts.setPaused(false);
			}
		};
		Button settings = new Button(gsm, "Settings") {
			@Override
			public void doAction() {
				gsm.getStates().push(new SettingsState(gsm));
			}
		};
		Button save = new Button(gsm, "Save") {
			@Override
			public void doAction() {
			}
		};
		Button load = new Button(gsm, "Load") {
			@Override
			public void doAction() {
			}
		};
		Button quit = new Button(gsm, "Quit") {
			@Override
			public void doAction() {
				System.exit(0);
			}
		};

		buttons = new Button[] { play, settings, save, load, quit };
	}

	@Override
	protected void init() {
		setFillColor(new Color(0, 0, 0, 100));
		setDefaultColor(Color.WHITE);
		setFont(new Font("Algerian", Font.PLAIN, 20));
		setButtonGap(30);
	}
}
