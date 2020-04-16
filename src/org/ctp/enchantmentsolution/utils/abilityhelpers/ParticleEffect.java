package org.ctp.enchantmentsolution.utils.abilityhelpers;

import org.bukkit.Particle;

public class ParticleEffect {

	private Particle particle;
	private int num;
	private double varX, varY, varZ;

	public ParticleEffect(Particle particle, int num) {
		this(particle, num, 0.5, 2, 0.5);
	}

	public ParticleEffect(Particle particle, int num, double varX, double varY, double varZ) {
		setParticle(particle);
		setNum(num);
		setVarX(varX);
		setVarY(varY);
		setVarZ(varZ);
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

	public double getVarX() {
		return varX;
	}

	public void setVarX(double varX) {
		this.varX = varX;
	}

	public double getVarY() {
		return varY;
	}

	public void setVarY(double varY) {
		this.varY = varY;
	}

	public double getVarZ() {
		return varZ;
	}

	public void setVarZ(double varZ) {
		this.varZ = varZ;
	}

}
