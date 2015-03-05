package ui;

import game.Game;
import gamestatemanager.DevTestState;
import gamestatemanager.GameStateManager;
import gamestatemanager.SettingsState;

import java.awt.Color;

public class MainMenu extends Menu {

	public MainMenu(GameStateManager gsm) {
		super(0, 0, Game.WIDTH, Game.HEIGHT, gsm);
	}

	@Override
	protected void buildButtons() {
		Button play = new Button(gsm, "Play") {
			@Override
			public void doAction() {
				gsm.getStates().push(new DevTestState(gsm));
			}
		};
		Button settings = new Button(gsm, "Settings") {
			@Override
			public void doAction() {
				gsm.getStates().push(new SettingsState(gsm));
			}
		};
		Button info = new Button(gsm, "Information") {
			@Override
			public void doAction() {
			}
		};
		Button credits = new Button(gsm, "Credits") {
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

		buttons = new Button[] { play, settings, info, credits, quit };

	}

	@Override
	protected void init() {
		setFillColor(Color.lightGray);
	}

}