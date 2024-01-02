package org.ctp.enchantmentsolution.events.modify.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ModifyPlayerActionEvent extends ESPlayerEvent {

	public ModifyPlayerActionEvent(Player who, EnchantmentLevel enchantment) {
		super(who, enchantment);
	}

}
