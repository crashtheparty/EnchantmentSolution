package org.ctp.enchantmentsolution.utils.loot;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.StorageMinecart;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.Lootable;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class LootUtils {

	public static boolean isActiveLootChest(Block block) {
		return block.getState() instanceof Lootable && ((Lootable) block.getState()).getLootTable() != null;
	}

	public static void populateLootChest(Player player, Block block) {
		if (!isActiveLootChest(block)) return;
		Lootable loot = (Lootable) block.getState();
		String type = loot.getLootTable().getKey().getKey();
		loot.getLootTable().populateLoot(new Random(), new LootContext.Builder(block.getLocation()).build());
		Inventory inv = ((Container) block.getState()).getInventory();
		for(int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);
			if (item == null) continue;

			boolean soulSpeed = EnchantmentUtils.hasEnchantment(item, Enchantment.SOUL_SPEED);
			if (!ConfigString.USE_ENCHANTED_BOOKS.getBoolean() && item.getType() == Material.ENCHANTED_BOOK) {
				item.setType(Material.BOOK);
				item = GenerateUtils.generateChestLoot(player, item, type);
			} else if (item.getEnchantments().size() > 0) item = GenerateUtils.generateChestLoot(player, item, type, soulSpeed ? EnchantmentLocation.PIGLIN_TRADES : EnchantmentLocation.CHEST_LOOT);
			inv.setItem(i, item);
		}
	}

	public static boolean isActiveLootCart(Entity entity) {
		return entity instanceof StorageMinecart && ((StorageMinecart) entity).getLootTable() != null;
	}

	public static void populateLootCart(Player player, Entity entity) {
		if (!isActiveLootCart(entity)) return;
		Lootable loot = (Lootable) entity;
		String type = loot.getLootTable().getKey().getKey();
		loot.getLootTable().populateLoot(new Random(), new LootContext.Builder(entity.getLocation()).build());
		Inventory inv = ((InventoryHolder) entity).getInventory();
		for(int i = 0; i < inv.getSize(); i++) {
			ItemStack item = inv.getItem(i);

			boolean soulSpeed = EnchantmentUtils.hasEnchantment(item, Enchantment.SOUL_SPEED);
			if (!ConfigString.USE_ENCHANTED_BOOKS.getBoolean() && item.getType() == Material.ENCHANTED_BOOK) {
				item.setType(Material.BOOK);
				item = GenerateUtils.generateChestLoot(player, item, type);
			} else if (item.getEnchantments().size() > 0) item = GenerateUtils.generateChestLoot(player, item, type, soulSpeed ? EnchantmentLocation.PIGLIN_TRADES : EnchantmentLocation.CHEST_LOOT);
		}
	}
}
