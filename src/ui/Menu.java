package ui;

import gamestatemanager.GameStateManager;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

public abstract class Menu {
	private Color fillColor, selectedColor, defaultColor;
	private Font font;
	private Rectangle2D background;
	private int selected;
	private int buttonGap;
	protected Button[] buttons;
	protected GameStateManager gsm;

	public Menu(float x, float y, float width, float height,
			GameStateManager gsm) {
		background = new Rectangle2D.Float(x, y, width, height);
		setDefaults();
		init();
		buildButtons();
		this.gsm = gsm;
	}

	protected abstract void buildButtons();

	protected abstract void init();

	private void setDefaults() {
		selected = 0;
		setFont(new Font("Algerian", Font.PLAIN, 60));
		setFillColor(Color.WHITE);
		setSelectedColor(Color.RED);
		setDefaultColor(Color.BLACK);
		setButtonGap(100);
	}

	public void render(Graphics2D g) {
		g.setFont(getFont());
		g.setColor(getFillColor());
		g.fill(background);

		for (int i = 0; i < buttons.length; i++) {
			if (selected == i) {
				g.setColor(getSelectedColor());
			} else {
				g.setColor(getDefaultColor());
			}

			g.drawString(buttons[i].getName(),
					(int) (background.getX() + background.getWidth() / 2
							-  g.getFontMetrics().stringWidth(buttons[i].getName())/2),
					(int) (background.getY() + (i * buttonGap) + background
							.getHeight() / 6));
		}
	}

	public void keyPressed(int k) {
		if (k == KeyEvent.VK_UP || k == KeyEvent.VK_W) {
			if (selected == 0) {
				selected = buttons.length - 1;
			} else {
				selected--;
			}
		} else if (k == KeyEvent.VK_DOWN || k == KeyEvent.VK_S) {
			if (selected == buttons.length - 1) {
				selected = 0;
			} else {
				selected++;
			}
		}

		if (k == KeyEvent.VK_ENTER) {
			select();
		}
	}

	private void select() {
		buttons[selected].doAction();
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Color getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(Color defaultColor) {
		this.defaultColor = defaultColor;
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public int getButtonGap() {
		return buttonGap;
	}

	public void setButtonGap(int buttonGap) {
		this.buttonGap = buttonGap;
	}

}
