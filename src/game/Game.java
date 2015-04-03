package game;

import gamestatemanager.GameStateManager;
import input.KeyMaster;
import input.MouseMaster;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import util.CursorManager;

public class Game extends Canvas implements Runnable {

	//TODO : Cleanup. So. Much Cleanup.
	public static int WIDTH, HEIGHT;
	
	public static JFrame frame;
	public volatile boolean running;
	private Thread gameThread;

	private BufferedImage image;
	private BufferStrategy bs;
	private Graphics2D g2;
	private GameStateManager gsm;
	public static Game currentGame;
	public boolean toRestart = false;
	private int xOffset, yOffset;
	
	private CursorManager cm;

	public Game() {
		running = true;
		// cursor
		cm = new CursorManager(this);
		CursorManager.setCursor(1);
		
		frame = new JFrame(Settings.NAME);
		if (Settings.FULLSCREEN) {
			frame.setSize((int) Settings.FULLSCREEN_RESOLUTION.getWidth(),
					(int) Settings.FULLSCREEN_RESOLUTION.getHeight());
			frame.setUndecorated(true);
			frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			xOffset = (int) ((Settings.FULLSCREEN_RESOLUTION.getWidth() - Settings.CURRENT_RESOLUTION
					.getWidth()) / 2);
			yOffset = (int) ((Settings.FULLSCREEN_RESOLUTION.getHeight() - Settings.CURRENT_RESOLUTION
					.getHeight()) / 2);

		} else {

			frame.setSize((int) Settings.CURRENT_RESOLUTION.getWidth(),
					(int) Settings.CURRENT_RESOLUTION.getHeight());
			xOffset = 0;
			yOffset = 0;

		}
	
		
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		frame.toFront();

		start();
	}

	public synchronized void start() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager(g2);

		addKeyListener(new KeyMaster(gsm));
		MouseMaster mm = new MouseMaster();
		addMouseListener(mm);
		addMouseMotionListener(mm);

		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
	}

	public synchronized void stop() {
		try {
			gameThread.join();
			running = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		running = true;
		double nsPerTick = 1000000000 / Settings.FPSUPS;
		double delta = 0;

		int frames = 0;
		int updates = 0;

		long timer = System.currentTimeMillis();
		long before = System.nanoTime();
		long after;

		while (running) {
			after = System.nanoTime();
			delta += (after - before) / nsPerTick;
			before = after;

			if (delta >= 1) {
				delta--;
				//frames++;
				updates++;

				update();
				//render();
				//renderToScreen();
			}
			frames++; // temp for testing FPS
			render(); // temp for testing FPS
			renderToScreen(); // temp for testing FPS
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames + " UPS: " + updates);
				updates = 0;
				frames = 0;
				timer += 1000;
			}
		}


		if (toRestart == true) {
			launch();
		}
	}

	public void update() {
		// updates the GameStateManager which will update the current game state
		gsm.update();
	}

	public void render() {
		// tell the GameStateManager to render the current game state
		gsm.render();
	}

	public void renderToScreen() {
		bs = getBufferStrategy();
		if (bs == null) {
			requestFocus();
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.clearRect(0, 0, (int) Settings.FULLSCREEN_RESOLUTION.getWidth(),
				(int) Settings.FULLSCREEN_RESOLUTION.getHeight());
		g.fillRect(0, 0, (int) Settings.FULLSCREEN_RESOLUTION.getWidth(),
				(int) Settings.FULLSCREEN_RESOLUTION.getHeight());
		g.drawImage(image, xOffset, yOffset, null);

		g.dispose();
		bs.show();

	}

	public static void main(String[] args) {
		launch();
	}

	public static void launch() {
		Settings.build();

		WIDTH = (int) Settings.CURRENT_RESOLUTION.getWidth();
		HEIGHT = (int) Settings.CURRENT_RESOLUTION.getHeight();

		if (frame != null) {
			frame.dispose();
		}

		currentGame = new Game();
	}

}