package org.ctp.enchantmentsolution.events.blocks;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public class DetonatorExplosionEvent extends ESEntityEvent {

	private final Location loc;
	private float size;
	private boolean setFire, setBlocks, delayExplosion;

	public DetonatorExplosionEvent(LivingEntity entity, int level, Location loc, float size, boolean setFire,
	boolean setBlocks, boolean delayExplosion) {
		super(entity, new EnchantmentLevel(CERegister.DETONATOR, level));
		this.loc = loc;
		setSize(size);
		setSetFire(setFire);
		setSetBlocks(setBlocks);
		setDelayExplosion(delayExplosion);
	}

	public Location getLoc() {
		return loc;
	}

	public float getSize() {
		return size;
	}

	public void setSize(float size) {
		this.size = size;
	}

	public boolean willSetFire() {
		return setFire;
	}

	public void setSetFire(boolean setFire) {
		this.setFire = setFire;
	}

	public boolean willSetBlocks() {
		return setBlocks;
	}

	public void setSetBlocks(boolean setBlocks) {
		this.setBlocks = setBlocks;
	}

	public boolean willDelayExplosion() {
		return delayExplosion;
	}

	public void setDelayExplosion(boolean delayExplosion) {
		this.delayExplosion = delayExplosion;
	}

}
