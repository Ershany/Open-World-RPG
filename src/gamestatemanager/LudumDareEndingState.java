package gamestatemanager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import game.Game;
import gfx.Sprite;

public class LudumDareEndingState extends GameState {

	public LudumDareEndingState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		
	}

	int timer = 800;
	@Override
	public void update() {
		timer--;
		if(timer <= 0) System.exit(0);
	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 30));
		g.drawString("Your poor little island is now doomed....", Game.WIDTH / 2 - 295, Game.HEIGHT / 2 - 200);
		g.drawString("This game was made for Ludum Dare 33", Game.WIDTH / 2 - 290, Game.HEIGHT / 2);
		g.drawString("Everything was programmed from scratch in Java", Game.WIDTH / 2 - 350, Game.HEIGHT / 2 + 100);
		g.drawString("Game Made By: Brady Jessup", Game.WIDTH / 2 - 230, Game.HEIGHT / 2 + 200);
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
