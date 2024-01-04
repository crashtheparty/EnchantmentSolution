package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class PushbackEvent extends ModifyVectorEvent {

	public PushbackEvent(Player who, int level, Vector vector, Vector newVector, LivingEntity entity) {
		super(who, new EnchantmentLevel(CERegister.PUSHBACK, level), vector, newVector, entity);
	}

}
