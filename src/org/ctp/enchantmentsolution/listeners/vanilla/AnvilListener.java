package org.ctp.enchantmentsolution.listeners.vanilla;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.generate.AnvilEnchantments;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class AnvilListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPrepareAnvil(PrepareAnvilEvent event) {
		ItemStack first = event.getInventory().getItem(0);
		ItemStack second = event.getInventory().getItem(1);
		if (event.getView().getPlayer() instanceof Player) {
			String name = event.getInventory().getRenameText();
			AnvilEnchantments anvil = AnvilEnchantments.getAnvilEnchantments((Player) event.getView().getPlayer(), first, second, name);
			ItemStack combineItem = anvil.getCombinedItem();
			if (combineItem != null) {
				ItemStack combineFinal = combineItem;
				EnchantmentSolution.getPlugin().getServer().getScheduler().runTask(EnchantmentSolution.getPlugin(), () -> {
					if (anvil.getRepairCost() < ConfigString.MAX_REPAIR_LEVEL.getInt()) {
						event.getInventory().setMaximumRepairCost(ConfigString.MAX_REPAIR_LEVEL.getInt());
						event.getInventory().setItem(2, combineFinal);
						event.getInventory().setRepairCost(anvil.getRepairCost());
					} else
						event.getInventory().setItem(2, new ItemStack(Material.AIR));
				});
			}
		}
	}
}
