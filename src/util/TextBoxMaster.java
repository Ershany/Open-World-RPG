package util;

import game.Game;
import gfx.Sprite;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TextBoxMaster {

	private String[] message;
	
	public TextBoxMaster(String[] message) {
		this.message = message;
	}
	
	public void render(Graphics2D g) {
		g.drawImage(Sprite.textBox.getImage(), (Game.WIDTH / 2) - 362, Game.HEIGHT - 400, null);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Arial", Font.BOLD, 14));
		for(int i = 0; i < message.length; i++) {
			g.drawString(message[i], (Game.WIDTH / 2) - 295, Game.HEIGHT - 300 + (i * 20));
		}
		g.drawString("Press Space to Exit", (Game.WIDTH / 2) - 75, Game.HEIGHT - 120);
	}
	
}
