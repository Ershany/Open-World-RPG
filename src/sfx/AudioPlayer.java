package sfx;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {
	
	public static AudioPlayer splashSound = new AudioPlayer("/sfx/Opening.mp3", false, Type.Effect);
	public static AudioPlayer kronosGreeting = new AudioPlayer("/sfx/Kronos/PhaseOneKronos.mp3", false, Type.Effect);
	public static AudioPlayer kronosExplain = new AudioPlayer("/sfx/Kronos/FinalPhaseKronos.mp3", false, Type.Effect);
	public static AudioPlayer meleeSound = new AudioPlayer("/sfx/player/MeleeSwing.mp3", false, AudioPlayer.Type.Effect);
	public static AudioPlayer rangedSound = new AudioPlayer("/sfx/player/Shoot.mp3", false, AudioPlayer.Type.Effect);
	public static AudioPlayer kronosBattle = new AudioPlayer("/sfx/songs/Kronos Battle.mp3", true, AudioPlayer.Type.Music);
	public static AudioPlayer menuMusic = new AudioPlayer("/sfx/songs/Menu.mp3", true, AudioPlayer.Type.Music);
	public static AudioPlayer gameTheme = new AudioPlayer("/sfx/songs/GameTheme.mp3", true, AudioPlayer.Type.Music);
	
	//Options menu allows users to tune down the decibel levels of the sounds. The sounds will be organized into categorys
	public enum Type {
		Music, Effect;
	}
	
	private Clip clip;
	private boolean loop;
	private Type type;
	
	public AudioPlayer(String path, boolean loop, Type type) {
		this.loop = loop;
		this.type = type;
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(AudioPlayer.class.getResourceAsStream(path));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			if(type == Type.Effect) gainControl.setValue(-5.0f);
			else if(type == Type.Music) gainControl.setValue(-28.0f);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if(clip == null) return;
		stop();
		
		clip.setFramePosition(0);
		if(loop) {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			clip.start();
		}
	}
	
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
}
