package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public abstract class ModifyVectorEvent extends ModifyActionEvent {

	private final Vector vector;
	private Vector newVector;
	
	public ModifyVectorEvent(Player who, EnchantmentLevel enchantment, Vector vector, Vector newVector) {
		super(who, enchantment);
		this.vector = vector;
		this.setNewVector(newVector);
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

}
