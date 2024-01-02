package org.ctp.enchantmentsolution.interfaces.effects;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.events.soul.SoulboundEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentEffect;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.SoulboundCondition;

public abstract class SoulboundEffect extends EnchantmentEffect {
	
	private final SoulboundCondition[] conditions;

	public SoulboundEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	SoulboundCondition... conditions) {
		super(enchantment, type, location, priority);
		this.conditions = conditions;
	}

	public boolean willRun(Entity killer, Player killed, Collection<ItemStack> soulDrops) {
		for(SoulboundCondition condition: conditions)
			if (!condition.metCondition(killer, killed, soulDrops)) return false;
		return true;
	}

	public abstract EffectResult run(Entity killer, Player killed, ItemStack[] items, Collection<ItemStack> soulDrops, SoulboundEvent event);
}
