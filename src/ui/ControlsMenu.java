package ui;

import java.awt.Color;

import game.Game;
import gamestatemanager.GameStateManager;

public class ControlsMenu extends Menu {

	public ControlsMenu(GameStateManager gsm) {
		super(0, 0, Game.WIDTH, Game.HEIGHT, gsm);
	}

	@Override
	protected void buildButtons() {
		Button back = new Button(gsm, "Back") {
			@Override
			public void doAction() {
				gsm.getStates().pop();
			}
		};
		
		buttons = new Button[] { back };
	}

	@Override
	protected void init() {
		setFillColor(Color.lightGray);
	}

}
