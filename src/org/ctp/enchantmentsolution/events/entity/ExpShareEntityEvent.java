package org.ctp.enchantmentsolution.events.entity;

import org.bukkit.entity.LivingEntity;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ExpShareType;

public class ExpShareEntityEvent extends ExperienceEntityEvent {

	private ExpShareType type;

	public ExpShareEntityEvent(LivingEntity who, int level, ExpShareType type, int oldExp, int newExp) {
		super(who, new EnchantmentLevel(CERegister.EXP_SHARE, level), oldExp, newExp);
		this.type = type;
	}

	public ExpShareType getType() {
		return type;
	}

}
