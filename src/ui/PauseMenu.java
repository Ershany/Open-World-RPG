package ui;

import game.Game;
import gamestatemanager.DevTestState;
import gamestatemanager.GameState;
import gamestatemanager.GameStateManager;

import java.awt.Color;
import java.awt.Font;

public class PauseMenu extends Menu {

	GameState gameState;

	public PauseMenu(GameStateManager gsm, GameState gameState) {
		super(Game.WIDTH / 3, Game.HEIGHT / 3, Game.WIDTH / 3, Game.HEIGHT / 3,
				gsm);
		this.gameState = gameState;
	}

	@Override
	protected void buildButtons() {
		Button play = new Button(gsm, "Resume") {
			@Override
			public void doAction() {
				gameState.setPaused(false);
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

		buttons = new Button[] { play, save, load, quit };
	}

	@Override
	protected void init() {
		setFillColor(new Color(0, 0, 0, 100));
		setDefaultColor(Color.WHITE);
		setFont(new Font("Algerian", Font.PLAIN, 20));
		setButtonGap(30);
	}
}
