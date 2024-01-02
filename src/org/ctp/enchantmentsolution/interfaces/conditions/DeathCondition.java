package org.ctp.enchantmentsolution.interfaces.conditions;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public interface DeathCondition extends Condition {
	public boolean metCondition(Entity killer, Entity killed, Collection<ItemStack> drops);
}
