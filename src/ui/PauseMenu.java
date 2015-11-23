package ui;

import game.Game;
import gamestatemanager.GameState;
import gamestatemanager.GameStateManager;
import io.FileIO;
import io.LevelData;

import java.awt.Color;
import java.awt.Font;

import javax.imageio.stream.FileImageOutputStream;

public class PauseMenu extends Menu {

	private GameState gameState;
	private LevelData data;

	public PauseMenu(GameStateManager gsm, GameState gameState, LevelData data) {
		super(Game.WIDTH / 3, Game.HEIGHT / 3, Game.WIDTH / 3, Game.HEIGHT / 3,
				gsm);
		this.gameState = gameState;
		this.data = data;
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
				FileIO.save(data);
			}
		};
		Button quit = new Button(gsm, "Save and Quit") {
			@Override
			public void doAction() {
				FileIO.save(data);
				System.exit(0);
			}
		};

		buttons = new Button[] { play, save, quit };
	}

	@Override
	protected void init() {
		setFillColor(new Color(0, 0, 0, 100));
		setDefaultColor(Color.WHITE);
		setFont(new Font("Algerian", Font.PLAIN, 20));
		setButtonGap(45);
	}
}
