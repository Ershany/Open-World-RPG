package input;


import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.event.MouseInputListener;

public class MouseMaster implements MouseMotionListener, MouseInputListener, MouseWheelListener {

	private static int mouseX, mouseY, mouseB;
	private static int currentNotches;
	private static Rectangle hitbox = new Rectangle(0, 0, 1, 1);
	
	public MouseMaster() {
		mouseX = -1;
		mouseY = -1;
		mouseB = -1;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		hitbox.x = mouseX;
		hitbox.y = mouseY;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		hitbox.x = mouseX;
		hitbox.y = mouseY;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		currentNotches = e.getWheelRotation();
	}
	
	//setters
	public static void resetCurrentNotches() {
		currentNotches = 0;
	}
	
	//getters
	public static int getMouseB() {
		return mouseB;
	}
	public static int getMouseX() {
		return mouseX;
	}
	public static int getMouseY() {
		return mouseY;
	}
	public static Rectangle getHitbox() {
		return hitbox;
	}
	public static int getCurrentNotches() {
		return currentNotches;
	}
	
}
