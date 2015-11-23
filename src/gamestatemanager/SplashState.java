package gamestatemanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.Game;
import gfx.Sprite;
import sfx.AudioPlayer;

public class SplashState extends GameState {

	public SplashState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		AudioPlayer.splashSound.play();
	}

	int timer = 0;
	@Override
	public void update() {
		timer++;
		if(timer >= 180) {
			gsm.getStates().push(new MenuExampleState(gsm));
		}
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.WHITE);
		g.drawImage(Sprite.splashScreen.getImage(), Game.WIDTH / 2 - 160, Game.HEIGHT / 2 - 250, null);
		g.setFont(new Font("Arial", Font.BOLD, 60));
		g.drawString("Game Developed By: Brady Jessup", Game.WIDTH / 2 - 550, Game.HEIGHT - (Game.HEIGHT / 5));
	}

	
	
	@Override
	public void keyPressed(int k) {
		
	}

	@Override
	public void keyReleased(int k) {
		
	}

	@Override
	public void keyTyped(int k) {
		
	}

}
