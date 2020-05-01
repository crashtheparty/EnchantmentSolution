package org.ctp.enchantmentsolution.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.ctp.enchantmentsolution.enchantments.generate.AnvilEnchantments;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class MinigameUtils {

	public static boolean isEnabled() {
		return ConfigString.GAMETYPES.getStringList().contains("MINIGAME");
	}

	public static boolean quickAnvil() {
		return ConfigString.MINIGAME_QUICK_ANVIL.getBoolean();
	}

	public static void setAnvil(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack cursor = event.getCursor();
		ItemStack current = event.getCurrentItem();

		if (!(event.getInventory().getType() == InventoryType.CRAFTING && event.getClickedInventory().getType() == InventoryType.PLAYER && cursor != null && cursor.hasItemMeta())) return;
		if (cursor.getItemMeta() instanceof EnchantmentStorageMeta && ((EnchantmentStorageMeta) cursor.getItemMeta()).hasStoredEnchants() || cursor.getItemMeta().hasEnchants()) {
			List<Material> materials = Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK);
			if (materials.contains(cursor.getType()) || cursor.getType() == current.getType()) {
				if (current == null || current.getType() == Material.AIR || current.getAmount() > 1) return;
				event.setCancelled(true);
				AnvilEnchantments anvil = AnvilEnchantments.getAnvilEnchantments(player, current, cursor);
				if (anvil.canCombine()) {
					player.getInventory().setItem(event.getSlot(), anvil.getCombinedItem());
					player.setItemOnCursor(anvil.getItemLeftover());
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
				}
			}
		}
	}

	public static int getTableCost(int normal) {
		switch (ConfigString.MINIGAME_TYPE.getString()) {
			case "FAST":
				if (ConfigString.MINIGAME_FAST_ENCHANTING_OVERRIDE.getBoolean()) return ConfigString.MINIGAME_FAST_ENCHANTING_COST.getInt();
		}
		return normal;
	}

	public static int getAnvilCost(int normal) {
		switch (ConfigString.MINIGAME_TYPE.getString()) {
			case "FAST":
				if (ConfigString.MINIGAME_FAST_ANVIL_OVERRIDE.getBoolean()) return ConfigString.MINIGAME_FAST_ANVIL_COST.getInt();
			case "MONDAYS":
				if (ConfigString.MINIGAME_MONDAYS_ANVIL_OVERRIDE.getBoolean()) return ConfigString.MINIGAME_MONDAYS_ANVIL_COST.getInt();
		}
		return normal;
	}
}
