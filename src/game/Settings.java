package game;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Settings {
	public static boolean edited;
	public static Dimension CURRENT_RESOLUTION;
	public static Dimension FULLSCREEN_RESOLUTION;

	public static String NAME = "Project Harbinger";
	public static int WIDTH;
	public static int HEIGHT;
	public static double FPSUPS = 60;
	public static boolean FULLSCREEN;
	
	public static Dimension RESOLUTIONS[];
	
	public static int RES_INDEX;
	
	
	
	// TODO : MAKE PERSISTENT (Settings save file?)
	public static void build () {

		if (!edited) {
			
			FULLSCREEN_RESOLUTION= new Dimension();
			FULLSCREEN_RESOLUTION.setSize(Toolkit.getDefaultToolkit()
					.getScreenSize());
			CURRENT_RESOLUTION = FULLSCREEN_RESOLUTION;
			FULLSCREEN = true;

			RESOLUTIONS = new Dimension[] { 
					new Dimension(768, 1024),
					new Dimension(800, 600), 
					new Dimension(1024, 600),
					new Dimension(1024, 768),
					new Dimension(1093,614),
					new Dimension(1152, 864),
					new Dimension(1280,720),
					new Dimension(1280,768),
					new Dimension(1280,800 ), 
					new Dimension(1360, 768),
					new Dimension(1366, 768),
					new Dimension(1400,1050),
					new Dimension(1440,900),
					new Dimension(1536, 864),
					new Dimension(1600,900), 
					new Dimension(1600,1200),
					new Dimension(1680, 1050), 
					new Dimension(1920,1080),
					new Dimension(1920,1200),
					new Dimension(2048,1152),
					new Dimension(2560, 1440), 
					new Dimension(2560, 1600),
			
			};

		} else {
			
			CURRENT_RESOLUTION = RESOLUTIONS[RES_INDEX];
			
		}

	}
}
