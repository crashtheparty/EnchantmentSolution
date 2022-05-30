package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class FlingEvent extends ModifyVectorEvent {

	public FlingEvent(Player who, int level, Vector vector, Vector newVector, LivingEntity entity) {
		super(who, new EnchantmentLevel(CERegister.FLING, level), vector, newVector, entity);
	}

}
