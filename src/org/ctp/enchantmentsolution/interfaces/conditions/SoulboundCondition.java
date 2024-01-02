package org.ctp.enchantmentsolution.interfaces.conditions;

import java.util.Collection;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface SoulboundCondition extends Condition {
	public boolean metCondition(Entity killer, Player killed, Collection<ItemStack> soulDrops);

}
