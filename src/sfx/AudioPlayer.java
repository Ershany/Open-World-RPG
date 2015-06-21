package sfx;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {
	
	//Options menu allows users to tune down the decibel levels of the sounds. The sounds will be organized into categorys
	public enum Type {
		Music, Effect;
	}
	
	private Clip clip;
	private boolean loop;
	
	public AudioPlayer(String path, boolean loop) {
		this.loop = loop;
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(AudioPlayer.class.getResourceAsStream(path));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16, baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
			
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(0.0f); //audio level
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
