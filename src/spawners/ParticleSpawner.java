package spawners;

import java.awt.Color;
import java.awt.image.BufferedImage;

import gamestatemanager.LevelState;
import gfx.Particle;

public class ParticleSpawner {
	
	private LevelState state;
	
	public ParticleSpawner(LevelState state) {
		this.state = state;
	}
	
	//normal particles
	public void spawn(float x, float y, int particleLife, float speed, Color color, int amount) {
		for(int i = 0; i < amount; i++) {
			state.addParticle(new Particle(x, y, particleLife, speed, color));
		}
	}
	
	//image particles
	public void spawn(float x, float y, int particleLife, float speed, int amount, BufferedImage image) {
		for(int i = 0; i < amount; i++) {
			state.addParticle(new Particle(x, y, particleLife, speed, image));
		}
	}
	
	//normal direction orientated particles
	public void spawn(float x, float y, int particleLife, float speed, Color color, int amount, Particle.Type type) {
		for(int i = 0; i < amount; i++) {
			state.addParticle(new Particle(x, y, particleLife, speed, color, type));
		}
	}
	
	//image direction orientated particles
	public void spawn(float x, float y, int particleLife, float speed, int amount, BufferedImage image, Particle.Type type) {
		for(int i = 0; i < amount; i++) {
			state.addParticle(new Particle(x, y, particleLife, speed, image, type));
		}
	}
	
}
