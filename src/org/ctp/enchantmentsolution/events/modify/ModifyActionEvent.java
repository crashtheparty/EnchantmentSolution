package org.ctp.enchantmentsolution.events.modify;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public abstract class ModifyActionEvent extends ESPlayerEvent {

	public ModifyActionEvent(Player who, EnchantmentLevel enchantment) {
		super(who, enchantment);
	}

}
