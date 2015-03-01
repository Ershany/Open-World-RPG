package game;

import gamestatemanager.GameStateManager;
import input.KeyMaster;
import input.MouseMaster;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	public static String NAME = "RPG";
	public static int WIDTH;
	public static int HEIGHT;
	public static double FPSUPS = 60;

	public static JFrame frame;
	private volatile boolean running;
	private Thread gameThread;

	private BufferedImage image;
	private BufferStrategy bs;
	private Graphics2D g2;
	private GameStateManager gsm;

	
	
	public Game() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = (int) dimension.getWidth();
		HEIGHT = (int) dimension.getHeight();

		
		frame = new JFrame(NAME);
		frame.setSize(WIDTH, HEIGHT);
		frame.setUndecorated(true);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.toFront();

		
		start();
	}

	public synchronized void start() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) image.getGraphics();
		gsm = new GameStateManager(g2);

		addKeyListener(new KeyMaster(gsm));
		MouseMaster mm = new MouseMaster(gsm);
		addMouseListener(mm);
		addMouseMotionListener(mm);
		
		gameThread = new Thread(this, "Game Thread");
		gameThread.start();
	}

	public synchronized void stop() {
		try {
			gameThread.join();
			running = false;
		} catch(InterruptedException e) {
			e.printStackTrace(); }
	}

	public void run() {
		running = true;
		double nsPerTick = 1000000000 / FPSUPS;
		double delta = 0;

		int frames = 0;
		int updates = 0;
		
		long timer = System.currentTimeMillis();
		long before = System.nanoTime();
		long after;

		while(running) {
			after = System.nanoTime();
			delta += (after - before) / nsPerTick;
			before = after;

			if(delta >= 1) {
				delta--;
				frames++;
				updates++;

				update();
				render();
				renderToScreen();
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				frame.setTitle(NAME + " FPS: " + frames + " UPS: " + updates);
				updates = 0;
				frames = 0;
				timer += 1000;
			}
		}
	}

	public void update() {
		//updates the GameStateManager which will update the current game state
		gsm.update();
	}

	public void render() {
		//tell the GameStateManager to render the current game state
		gsm.render();
	}

	public void renderToScreen() {
		bs = getBufferStrategy();
		if(bs == null) {
			requestFocus();
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(image, 0, 0, null);

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game();
	}

	

}