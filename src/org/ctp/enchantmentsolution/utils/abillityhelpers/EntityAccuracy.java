package org.ctp.enchantmentsolution.utils.abillityhelpers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EntityAccuracy {
	private UUID attacker;
	private UUID entity;
	private double accuracy;
	private int ticks, level;

	public EntityAccuracy(Player attacker, LivingEntity entity, double accuracy, int ticks, int level) {
		setAttacker(attacker);
		setEntity(entity);
		setAccuracy(accuracy);
		setTicks(ticks);
		setLevel(level);
	}

	public LivingEntity getEntity() {
		return (LivingEntity) Bukkit.getEntity(entity);
	}

	public void setEntity(LivingEntity entity) {
		this.entity = entity.getUniqueId();
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public void setTicks(int ticks) {
		if (ticks > this.ticks) {
			this.ticks = ticks;
		}
	}

	public Player getAttacker() {
		return (Player) Bukkit.getEntity(attacker);
	}

	public void setAttacker(Player attacker) {
		this.attacker = attacker.getUniqueId();
	}

	public int getTicks() {
		return ticks;
	}

	public void minus() {
		ticks -= 1;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}