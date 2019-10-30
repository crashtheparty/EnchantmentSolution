package org.ctp.enchantmentsolution.utils.abillityhelpers;

import org.bukkit.Particle;

public class ParticleEffect {

	private Particle particle;
	private int num;
	
	public ParticleEffect(Particle particle, int num) {
		this.setParticle(particle);
		this.setNum(num);
	}

	public Particle getParticle() {
		return particle;
	}

	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
}
