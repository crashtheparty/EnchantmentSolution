package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ModifyVectorEvent extends ModifyPlayerActionEvent {

	private final Vector vector;
	private Vector newVector;
	private final LivingEntity entity;

	public ModifyVectorEvent(Player who, EnchantmentLevel enchantment, Vector vector, Vector newVector, LivingEntity entity) {
		super(who, enchantment);
		this.vector = vector;
		setNewVector(newVector);
		this.entity = entity;
	}

	public Vector getVector() {
		return vector;
	}

	public Vector getNewVector() {
		return newVector;
	}

	public void setNewVector(Vector newVector) {
		this.newVector = newVector;
	}

	public LivingEntity getEntity() {
		return entity;
	}

}
