package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ItemDamageCondition;

public abstract class ItemDamageEffect extends EnchantmentEffect {
	
	private final ItemDamageCondition[] conditions;

	public ItemDamageEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority, ItemDamageCondition[] conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Player player, ItemStack item, PlayerItemDamageEvent event) {
		for(ItemDamageCondition condition: conditions)
			if (!condition.metCondition(player, item, event)) return false;
		return true;
	}

	public abstract EffectResult run(Player player, ItemStack item, PlayerItemDamageEvent event);

}
