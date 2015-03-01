package gamestatemanager;

import game.Game;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Stack;

import javax.swing.event.MouseInputListener;

public class GameStateManager implements MouseMotionListener, MouseInputListener {

	
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
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO fill these?
		// Up to you where it goes from here
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	
	
	//getters
	public Stack<GameState> getStates() {
		return states;
	}

	
}