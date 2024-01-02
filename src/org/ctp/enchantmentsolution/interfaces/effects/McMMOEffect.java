package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.McMMOCondition;

public abstract class McMMOEffect extends EnchantmentEffect {

	private final McMMOCondition[] conditions;

	public McMMOEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	McMMOCondition[] conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Player player, ItemStack item, Event event) {
		for(McMMOCondition condition: conditions)
			if (!condition.metCondition(player, item, event)) return false;
		return true;
	}

	public abstract EffectResult run(Player player, ItemStack item, Event event);

}
