package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class RecyclerEvent extends ExperiencePlayerEvent {

	public RecyclerEvent(Player who, int oldExp, int newExp) {
		super(who, new EnchantmentLevel(CERegister.RECYCLER, 1), oldExp, newExp);
	}

}
