package org.ctp.enchantmentsolution.interfaces.item;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.TankEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ItemDamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.item.ReduceItemDamageEffect;

public class Tank extends ReduceItemDamageEffect {

	public Tank() {
		super(RegisterEnchantments.TANK, EnchantmentMultipleType.ALL, EnchantmentItemLocation.EQUIPPED, EventPriority.HIGHEST, 1, "%level% / (%level% + 1)", false, true, new ItemDamageCondition[0]);
	}

	@Override
	public ReduceItemDamageResult run(Player player, ItemStack item, PlayerItemDamageEvent event) {
		ReduceItemDamageResult result = super.run(player, item, event);
		int level = result.getLevel();

		if (level == 0) return null;

		TankEvent tank = new TankEvent(event.getPlayer(), level, item, result.getOriginalDamage(), result.getFinalDamage());
		Bukkit.getPluginManager().callEvent(tank);

		if (!tank.isCancelled()) {
			event.setDamage(tank.getNewDamage());
			return new ReduceItemDamageResult(level, tank.getOldDamage(), tank.getNewDamage());
		}

		return null;
	}

}
