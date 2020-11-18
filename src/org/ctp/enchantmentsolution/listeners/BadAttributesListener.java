package org.ctp.enchantmentsolution.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.Attributable;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ItemEquippedSlot;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class BadAttributesListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerInteract(PlayerInteractEvent event) {
		checkAttributes(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) checkAttributes((Player) event.getWhoClicked());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onInventoryClose(InventoryCloseEvent event) {
		if (event.getPlayer() instanceof Player) checkAttributes((Player) event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onPlayerItemHeld(PlayerItemHeldEvent event) {
		checkAttributes(event.getPlayer());
	}

	private void checkAttributes(Player player) {
		for(Attributable a: Attributable.values())
			for(ItemEquippedSlot type: a.getTypes())
				if (a.hasAttribute(player, type.getType())) removeAttribute(player, a, type, false);
				else if (a.getLegacyUUID() != null && a.hasAttribute(player, type.getType(), true)) removeAttribute(player, a, type, false);
	}

	private void removeAttribute(Player player, Attributable a, ItemEquippedSlot type, boolean legacy) {
		ItemStack check = null;
		switch (type.getType()) {
			case BOOTS:
				check = player.getInventory().getBoots();
				break;
			case CHESTPLATE:
				check = player.getInventory().getChestplate();
				break;
			case HELMET:
				check = player.getInventory().getHelmet();
				break;
			case LEGGINGS:
				check = player.getInventory().getLeggings();
				break;
			case MAIN_HAND:
				check = player.getInventory().getItemInMainHand();
				break;
			case OFF_HAND:
				check = player.getInventory().getItemInOffHand();
				break;
		}
		if (EnchantmentUtils.hasEnchantment(check, a.getEnchantment())) return;
		Attributable.removeAttribute(player, new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(a.getEnchantment()), 0), a, type, legacy);
	}
}
