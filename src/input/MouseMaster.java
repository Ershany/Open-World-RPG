package input;

import gamestatemanager.GameStateManager;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

public class MouseMaster implements MouseMotionListener, MouseInputListener {

	private GameStateManager gsm;

	public MouseMaster(GameStateManager gsm) {
		this.gsm = gsm;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		gsm.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		gsm.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		gsm.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		gsm.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		gsm.mouseReleased(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		gsm.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

}
