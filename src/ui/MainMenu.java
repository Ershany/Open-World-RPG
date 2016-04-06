package ui;

import game.Game;
import gameplaystates.StartingIslandState;
import gamestatemanager.ControlsState;
import gamestatemanager.GameStateManager;
import gamestatemanager.SettingsState;

import java.awt.Color;

import javax.swing.JOptionPane;

import sfx.AudioPlayer;
import tiles.Tile;

public class MainMenu extends Menu {
	
	public MainMenu(GameStateManager gsm) {
		super(0, 0, Game.WIDTH, Game.HEIGHT, gsm);
		
		AudioPlayer.menuMusic.stop();
		AudioPlayer.menuMusic.play();
	}

	@Override
	protected void buildButtons() {
		Button play = new Button(gsm, "Play") {
			@Override
			public void doAction() {	
				AudioPlayer.menuMusic.stop();
				AudioPlayer.gameTheme.play();
				
				gsm.getStates().push(new StartingIslandState(gsm, 144 * Tile.TILESIZE, 58 * Tile.TILESIZE));
			}
		};
		Button multiplayer = new Button(gsm, "Multiplayer") {
			@Override
			public void doAction() {
				Button host = new Button(gsm, "Host") {
					@Override
					public void doAction() {
						Game.multiplayer = true;
						Game.hosting = true;
						gsm.instantiateMultiplayer("");
						
						AudioPlayer.menuMusic.stop();
						AudioPlayer.gameTheme.play();
						gsm.getStates().push(new StartingIslandState(gsm, 144 * Tile.TILESIZE, 58 * Tile.TILESIZE));
					}
				};
				Button join = new Button(gsm, "Join") {
					@Override
					public void doAction() {
						Game.multiplayer = true;
						String ip = JOptionPane.showInputDialog("Enter the IP of the server you want to connect to");
						gsm.instantiateMultiplayer(ip);
						
						AudioPlayer.menuMusic.stop();
						AudioPlayer.gameTheme.play();
						gsm.getStates().push(new StartingIslandState(gsm, 144 * Tile.TILESIZE, 58 * Tile.TILESIZE));
					}
				};
				buttons = new Button[] { host, join };
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
				AudioPlayer.menuMusic.stop();
				Game.frame.setVisible(false);
				Game.currentGame.toRestart = true;
				Game.currentGame.running = false;
			}
		};
		Button quit = new Button(gsm, "Quit") {
			@Override
			public void doAction() {
				System.exit(0);
			}
		};

		buttons = new Button[] { play, multiplayer, settings, controls, credits, quit };

	}

	@Override
	protected void init() {
		setFillColor(Color.lightGray);
	}

}
