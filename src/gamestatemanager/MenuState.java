package gamestatemanager;

import game.Game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MenuState extends GameState {

	/*(potential)TODO : I think we should look into an abstract MenuState super class? I feel we're going to run into
	plenty of menus in the future, and I bet most of them are going to be reusing this code anyway.
	Or (I like this one better) we could just have a Menu class we make and modify, adding buttons and their on-click instructions
	I suppose it'd require a custom button class, too, but I believe that it'd be much cleaner code.
	Tomorrow I'll write up a branch and you can see how you feel about it and whether or not we should do it
	
	*/
	
	
	private final String PLAY = "Play";
	private final String SETTINGS = "Settings";
	private final String INFORMATION = "Information";
	private final String CREDITS = "Credits";
	private final String QUIT = "Quit";

	
	private String[] options = {PLAY, INFORMATION, SETTINGS, CREDITS, QUIT};
	private int currentOption;

	private Font font;

	public MenuState(GameStateManager gsm) {
		super(gsm);
	}

	public void init() {
		currentOption = 0;
		font = new Font("Algerian", Font.PLAIN, 60);
	}

	public void update() {
		
	}

	public void render(Graphics2D g) {
		g.setFont(font);
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		//print out the options
		for(int i = 0; i < options.length; i++) {
			if(currentOption == i) {
				g.setColor(Color.RED);
			}
			else {
				g.setColor(Color.BLACK);
			}

			g.drawString(options[i], (Game.WIDTH / 2) - (options[i].length() * 17), (i *100) + Game.HEIGHT / 5);
		}
	}


	/*---------------------Key Listener Methods--------------------------*/
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
			if(currentOption == 0) currentOption = options.length - 1;
			else currentOption--;
		}
		else if(k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
			if(currentOption == options.length - 1) currentOption = 0;
			else currentOption++;
		}

		if(k == KeyEvent.VK_ENTER) {select();}
	}
	public void keyReleased(int k) {
		
	}
	public void keyTyped(int k) {
		
	}
	
	private void select() {
		
		switch (options[currentOption]) {
		case PLAY: 
			//example of what will be done if the user selects play
			//gsm.getStates().push(new WorldState(gsm));
			break;
		case SETTINGS:
			gsm.getStates().push(new SettingsState(gsm,this));
			break;
		case QUIT:
			System.exit(0);
			break;
		default:
			break;
		}
		
	
	}
}