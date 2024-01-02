package org.ctp.enchantmentsolution.interfaces.effects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.FishingCondition;

public abstract class FishingEffect extends EnchantmentEffect {

	private final FishingCondition[] conditions;
	
	public FishingEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	FishingCondition[] conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Player player, PlayerFishEvent event) {
		for(FishingCondition condition: conditions)
			if (!condition.metCondition(player, event)) return false;
		return true;
	}

	public abstract EffectResult run(Player player, ItemStack[] items, PlayerFishEvent event);

}
