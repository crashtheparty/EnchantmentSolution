package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESEntityEvent;

public class DetonatorExplosionEvent extends ESEntityEvent {

	private final Location loc;
	private float size;
	private boolean setFire, setBlocks, delayExplosion;
	private Entity detonator;

	public DetonatorExplosionEvent(LivingEntity entity, int level, Location loc, Entity detonator, float size, boolean setFire,
	boolean setBlocks, boolean delayExplosion) {
		super(entity, new EnchantmentLevel(CERegister.DETONATOR, level));
		this.loc = loc;
		setSize(size);
		setFire(setFire);
		setBlocks(setBlocks);
		setDelayExplosion(delayExplosion);
		setDetonator(detonator);
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

	public void setFire(boolean setFire) {
		this.setFire = setFire;
	}

	public boolean willSetBlocks() {
		return setBlocks;
	}

	public void setBlocks(boolean setBlocks) {
		this.setBlocks = setBlocks;
	}

	public boolean willDelayExplosion() {
		return delayExplosion;
	}

	public void setDelayExplosion(boolean delayExplosion) {
		this.delayExplosion = delayExplosion;
	}

	public Entity getDetonator() {
		return detonator;
	}

	public void setDetonator(Entity detonator) {
		this.detonator = detonator;
	}

}
