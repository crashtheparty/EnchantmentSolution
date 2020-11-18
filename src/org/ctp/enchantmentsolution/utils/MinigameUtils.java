package org.ctp.enchantmentsolution.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.ctp.crashapi.CrashAPI;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.generate.AnvilEnchantments;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

import net.milkbowl.vault.economy.Economy;

public class MinigameUtils {

	public static boolean isEnabled() {
		return ConfigString.GAMETYPES.getStringList().contains("MINIGAME");
	}

	public static boolean quickAnvil() {
		return ConfigString.MINIGAME_QUICK_ANVIL.getBoolean();
	}

	public static double getPlayerMoney(Player player) {
		if (CrashAPI.hasEconomy()) {
			Economy e = CrashAPI.getEconomy();
			return e.getBalance(player);
		}
		return 0;
	}

	public static String getMoneyName() {
		if (CrashAPI.hasEconomy()) {
			Economy e = CrashAPI.getEconomy();
			if (e.currencyNamePlural() == null || e.currencyNamePlural().isEmpty()) return Chatable.get().getMessage(ChatUtils.getCodes(), "minigame.default_money_name");
			return e.currencyNamePlural();
		}
		return "";
	}

	public static String getMoneySign() {
		if (CrashAPI.hasEconomy()) return Chatable.get().getMessage(ChatUtils.getCodes(), "minigame.default_money_sign");
		return "";
	}

	public static String format(double money) {
		if (CrashAPI.hasEconomy()) {
			Economy e = CrashAPI.getEconomy();
			return e.format(money);
		}
		return money + "";
	}

	public static void removePlayerMoney(Player player, double amount) {
		if (CrashAPI.hasEconomy()) {
			Economy e = CrashAPI.getEconomy();
			e.withdrawPlayer(player, amount);
		}
	}

	public static void setAnvil(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack cursor = event.getCursor();
		ItemStack current = event.getCurrentItem();

		if (!(event.getInventory().getType() == InventoryType.CRAFTING && event.getClickedInventory().getType() == InventoryType.PLAYER && cursor != null && cursor.hasItemMeta())) return;
		if (cursor.getItemMeta() instanceof EnchantmentStorageMeta && ((EnchantmentStorageMeta) cursor.getItemMeta()).hasStoredEnchants() || cursor.getItemMeta().hasEnchants()) {
			List<Material> materials = Arrays.asList(Material.BOOK, Material.ENCHANTED_BOOK);
			if (materials.contains(cursor.getType()) || cursor.getType() == current.getType()) {
				if (current == null || MatData.isAir(current.getType()) || current.getAmount() > 1) return;
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

	public static int getTableLevelCost(int normal) {
		switch (ConfigString.MINIGAME_TYPE.getString().toUpperCase(Locale.ROOT)) {
			case "FAST":
				if (ConfigString.MINIGAME_FAST_ENCHANTING_OVERRIDE.getBoolean()) {
					if (ConfigString.MINIGAME_FAST_ENCHANTING_COSTS.listContains("level")) return ConfigString.MINIGAME_FAST_ENCHANTING_LEVEL_COST.getInt();
					else
						return 0;
				} else {}
		}
		return normal;
	}

	public static int getTableLapisCost(int normal) {
		switch (ConfigString.MINIGAME_TYPE.getString().toUpperCase(Locale.ROOT)) {
			case "FAST":
				if (ConfigString.MINIGAME_FAST_ENCHANTING_OVERRIDE.getBoolean()) {
					if (ConfigString.MINIGAME_FAST_ENCHANTING_COSTS.listContains("lapis")) return ConfigString.MINIGAME_FAST_ENCHANTING_LAPIS_COST.getInt();
					else
						return 0;
				} else {}
		}
		return normal;
	}

	public static int getLapis(Player player) {
		int amount = 0;
		for(int i = 0; i < player.getInventory().getSize(); i++) {
			ItemStack item = player.getInventory().getItem(i);
			if (item != null && item.getType() == Material.LAPIS_LAZULI) amount += item.getAmount();
		}
		return amount;
	}

	public static void removeLapis(Player player, int amount) {
		for(int i = 0; i < player.getInventory().getSize(); i++) {
			if (amount == 0) break;
			ItemStack item = player.getInventory().getItem(i);
			if (item != null && item.getType() == Material.LAPIS_LAZULI) {
				if (item.getAmount() - amount <= 0) {
					amount -= item.getAmount();
					player.getInventory().setItem(i, new ItemStack(Material.AIR));
				} else {
					item.setAmount(item.getAmount() - amount);
					amount = 0;
					break;
				}
			} else {}
		}
	}

	public static double getTableEconomyCost(double normal) {
		switch (ConfigString.MINIGAME_TYPE.getString()) {
			case "FAST":
				if (ConfigString.MINIGAME_FAST_ENCHANTING_OVERRIDE.getBoolean()) {
					if (ConfigString.MINIGAME_FAST_ENCHANTING_COSTS.listContains("economy")) return ConfigString.MINIGAME_FAST_ENCHANTING_ECONOMY_COST.getDouble();
					else
						return 0;
				} else {}
		}
		return 0;
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
