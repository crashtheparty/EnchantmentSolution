package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ExpShareType;

public class ExpSharePlayerEvent extends ExperiencePlayerEvent {

	private ExpShareType type;

	public ExpSharePlayerEvent(Player who, int level, ExpShareType type, int oldExp, int newExp) {
		super(who, new EnchantmentLevel(CERegister.EXP_SHARE, level), oldExp, newExp);
		this.type = type;
	}

	public ExpShareType getType() {
		return type;
	}

}
