package gamestatemanager;

import game.Game;

import java.awt.Graphics2D;
import java.util.Stack;

public class GameStateManager {

	private Graphics2D g;

	private Stack<GameState> states;

	public GameStateManager(Graphics2D g) {
		this.g = g;
		states = new Stack<GameState>();
		states.push(new MenuState(this));
	}

	public void update() {
		//update the current game state
		states.peek().update();
	}

	public void render() {
		//clear the image and then render the new pixel data
		g.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
		states.peek().render(g);
	}

	/*-------------------------------Key Listener Methods----------------------------*/
	public void keyPressed(int k) {
		states.peek().keyPressed(k);
	}
	public void keyReleased(int k) {
		states.peek().keyReleased(k);
	}
	public void keyTyped(int k) {
		states.peek().keyTyped(k);
	}

	//getters
	public Stack<GameState> getStates() {
		return states;
	}

}