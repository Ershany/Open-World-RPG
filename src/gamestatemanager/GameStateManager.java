package gamestatemanager;

import java.awt.Graphics2D;
import java.util.Stack;

import game.Game;
import network.GameClient;
import network.GameServer;


public class GameStateManager {

	
	private Graphics2D g;

	private Stack<GameState> states;
	
	// The player will be only one of these
	public GameClient client;
	public GameServer server;

	public GameStateManager(Graphics2D g) {
		this.g = g;
		states = new Stack<GameState>();
		
		states.push(new SplashState(this));
		
	}
	
	public void instantiateMultiplayer(String serverIP) {
		if(Game.multiplayer && Game.hosting) {
			server = new GameServer(this);
			System.out.println("Host");
		}
		else if(Game.multiplayer) {
			client = new GameClient(this, serverIP);
			System.out.println("Client");
		}
	}

	public void update() {
		//update the current game state
		states.peek().update();
	}

	public void render() {
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