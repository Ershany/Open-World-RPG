package util;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.net.URL;

import game.Game;

public class CursorManager {
	
	private static Game game;
	
	private static URL url1 = Game.class.getResource("/cursors/normal.png");   //option 1
	private static URL url2 = Game.class.getResource("/cursors/interact.png"); //option 2
	private static int currentCursor;
	
	//Make an object out of this class and then your other classes can reference this class's method statically to change the cursor
	public CursorManager(Game game) {
		this.game = game;
	}
	
	public static void setCursor(int cursorOption) {
		if(cursorOption == 1) {
			Image image = Toolkit.getDefaultToolkit().getImage(url1);
			Point hotSpot = new Point(0, 0);
			game.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, hotSpot, "Default Cursor"));
			currentCursor = 1;
		}
		else if(cursorOption == 2) {
			Image image = Toolkit.getDefaultToolkit().getImage(url2);
			Point hotSpot = new Point(0, 0);
			game.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, hotSpot, "Default Cursor"));
			currentCursor = 2;
		}
	}
	
	public static int getCursor() {
		return currentCursor;
	}
	
}
