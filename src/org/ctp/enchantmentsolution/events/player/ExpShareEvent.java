package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class ExpShareEvent extends ExperienceEvent {

	private ExpShareType type;

	public ExpShareEvent(Player who, int level, ExpShareType type, int oldExp, int newExp) {
		super(who, new EnchantmentLevel(CERegister.EXP_SHARE, level), oldExp, newExp);
		this.type = type;
	}

	public ExpShareType getType() {
		return type;
	}

	public enum ExpShareType {
		BLOCK(), MOB(), FISH();
	}

}
