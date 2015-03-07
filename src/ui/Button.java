package ui;

import gamestatemanager.GameStateManager;

public abstract class Button {

	private String name;

	private GameStateManager gsm;

	public Button(GameStateManager gsm, String name) {
		this.gsm = gsm;
		this.name = name;
	}

	public abstract void doAction();

	public String getName() {
		return name;

	}

	public void setName(String name) {
		this.name = name;

	}

}
