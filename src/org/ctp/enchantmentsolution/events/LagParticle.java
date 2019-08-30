package org.ctp.enchantmentsolution.events;

import org.bukkit.Particle;

public class LagParticle {

	private Particle particle;
	private int numParticles;
	
	public LagParticle(Particle particle, int numParticles) {
		this.setParticle(particle);
		this.setNumParticles(numParticles);
	}

	public Particle getParticle() {
		return particle;
	}

	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	public int getNumParticles() {
		return numParticles;
	}

	public void setNumParticles(int numParticles) {
		this.numParticles = numParticles;
	}

}
