package spawners;

import gamestatemanager.LevelState;
import gfx.Particle;

import java.awt.Color;

public class ParticleSpawner {
	
	private LevelState state;
	
	public ParticleSpawner(LevelState state) {
		this.state = state;
	}
	
	public void spawn(float x, float y, int particleLife, float speed, Color color, int amount) {
		for(int i = 0; i < amount; i++) {
			state.addParticle(new Particle(x, y, particleLife, speed, color));
		}
	}
	
}
