package ui;

import game.Game;
import gameplaystates.StartingIslandState;
import gamestatemanager.GameStateManager;
import gamestatemanager.ControlsState;
import gamestatemanager.SettingsState;

import java.awt.Color;

import tiles.Tile;

public class MainMenu extends Menu {

	public MainMenu(GameStateManager gsm) {
		super(0, 0, Game.WIDTH, Game.HEIGHT, gsm);
	}

	@Override
	protected void buildButtons() {
		Button play = new Button(gsm, "Play") {
			@Override
			public void doAction() {
				//gsm.getStates().push(new DevTestState(gsm));
				gsm.getStates().push(new StartingIslandState(gsm, 134 * Tile.TILESIZE, 58 * Tile.TILESIZE));
			}
		};
		Button settings = new Button(gsm, "Settings") {
			@Override
			public void doAction() {
				gsm.getStates().push(new SettingsState(gsm));
			}
		};
		Button controls = new Button(gsm, "Controls") {
			@Override
			public void doAction() {
				gsm.getStates().push(new ControlsState(gsm));
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

		buttons = new Button[] { play, settings, controls, credits, quit };

	}

	@Override
	protected void init() {
		setFillColor(Color.lightGray);
	}

}
