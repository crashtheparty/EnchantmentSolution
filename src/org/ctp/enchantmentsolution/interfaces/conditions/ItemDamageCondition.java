package org.ctp.enchantmentsolution.interfaces.conditions;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public interface ItemDamageCondition extends Condition {
	public boolean metCondition(Player player, ItemStack item, PlayerItemDamageEvent event);

}
