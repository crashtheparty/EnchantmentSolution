package org.ctp.enchantmentsolution.utils;

import java.util.List;
import java.util.Locale;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.generate.*;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.Loots;
import org.ctp.enchantmentsolution.inventory.minigame.MinigameItem;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class GenerateUtils {

	public static ItemStack generatePiglinLoot(ItemStack item) {
		if (!ConfigString.USE_PIGLIN_TRADES.getBoolean()) return item;
		List<String> types = ConfigString.PIGLIN_TYPES.getStringList();
		String type = getType(types);
		Loots defaultLoot = Loots.getLoot("default_piglin_trades");
		ChestEnchantments enchantments = ChestEnchantments.getChestEnchantment(null, item, type, defaultLoot, EnchantmentLocation.PIGLIN);

		List<EnchantmentLevel> levels = enchantments.getEnchantments();
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "PiglinTrades", EnchantmentLocation.PIGLIN);
			if (!ConfigString.ALLOW_NO_ENCHANTMENTS.getBoolean()) return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static VillagerEnchantments generateVillagerEnchantments(ItemStack item, MerchantRecipe current) {
		String itemType = item.getType().name().toLowerCase(Locale.ROOT);
		String extra = "";
		if (item.getType() == Material.ENCHANTED_BOOK || item.getType() == Material.BOOK) extra = "." + (ConfigString.LEVEL_FIFTY.getBoolean() ? "fifty" : "thirty");

		Loots defaultLoot = Loots.getLoot("default_villager_other");
		List<String> types = null;
		String price = "";
		if (ConfigString.USE_VILLAGER_TRADES.exists(itemType + extra + ".use")) {
			if (!ConfigString.USE_VILLAGER_TRADES.getBoolean(itemType + extra + ".use")) return null;
			defaultLoot = Loots.getLoot("default_" + itemType);
			price = ConfigString.VILLAGER_PRICE.getString(itemType + extra + ".price");
			types = ConfigString.VILLAGER_TYPES.getStringList(itemType + extra + ".types");
		} else if (ConfigString.USE_VILLAGER_TRADES.getBoolean("other.use")) {
			types = ConfigString.VILLAGER_TYPES.getStringList("other.types");
			price = ConfigString.VILLAGER_TYPES.getString("other.price");
		}
		else
			return null;

		String type = getType(types);

		return VillagerEnchantments.getVillagerEnchantments(item, current, type, defaultLoot, price);
	}

	public static ItemStack generateChestLoot(Player player, ItemStack item, String lootType) {
		return generateChestLoot(player, item, lootType, EnchantmentLocation.CHEST_LOOT, true);
	}

	public static ItemStack generateChestLoot(Player player, ItemStack item, String lootType, EnchantmentLocation location) {
		return generateChestLoot(player, item, lootType, location, true);
	}

	public static ItemStack generateChestLoot(Player player, ItemStack item, String lootType, EnchantmentLocation location, boolean checkUse) {
		Loots defaultLoot = Loots.getLoot("default_chest_treasure");
		List<String> types = null;
		if (ConfigString.USE_CHEST_LOOT.exists(lootType + ".use")) {
			if (checkUse && !ConfigString.USE_CHEST_LOOT.getBoolean(lootType + ".use")) return item;
			defaultLoot = Loots.getLoot("default_" + lootType);
			types = ConfigString.CHEST_TYPES.getStringList(lootType + ".types");
		} else if (!checkUse || ConfigString.USE_CHEST_LOOT.getBoolean("other.use")) types = ConfigString.CHEST_TYPES.getStringList("other.types");
		else
			return item;

		if (location == EnchantmentLocation.PIGLIN && !ConfigString.USE_PIGLIN_TRADES.getBoolean()) return item;

		String type = getType(types);

		ChestEnchantments enchantments = ChestEnchantments.getChestEnchantment(player, item, type, defaultLoot, location);

		List<EnchantmentLevel> levels = enchantments.getEnchantments();
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "ChestLoot", location);
			if (!ConfigString.ALLOW_NO_ENCHANTMENTS.getBoolean()) return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static MinigameEnchantments generateMinigameEnchants(Player player, ItemStack enchant, Location loc, MinigameItem item) {
		List<String> types = item.getLootTypes();
		String type = getType(types);
		Loots defaultLoot = item.getDefaultLoots();

		return MinigameEnchantments.generateMinigameLoot(player, enchant, loc, type, defaultLoot, item);
	}

	public static ItemStack generateMinigameLoot(Player player, ItemStack enchant, Location loc, MinigameItem item) {
		List<String> types = item.getLootTypes();
		String type = getType(types);
		Loots defaultLoot = item.getDefaultLoots();

		MinigameEnchantments enchantments = MinigameEnchantments.generateMinigameLoot(player, enchant, loc, type, defaultLoot, item);

		List<EnchantmentLevel> levels = enchantments.getEnchantments();
		if (levels == null || levels.size() == 0) {
			warningMessages(enchant, "MinigameLoot", EnchantmentLocation.NONE);
			if (!ConfigString.ALLOW_NO_ENCHANTMENTS.getBoolean()) return enchant;
		}

		if (!item.getType().isMultiple()) while (levels.size() > 1)
			levels.remove(levels.size() - 1);

		return EnchantmentUtils.addEnchantmentsToItem(enchant, levels);
	}

	public static List<EnchantmentLevel> generateBookLoot(Player player, ItemStack item) {
		return generateBookLoot(player, item, "default_chest_treasure", EnchantmentLocation.CHEST_LOOT);
	}

	public static List<EnchantmentLevel> generateBookLoot(Player player, ItemStack item, EnchantmentLocation location) {
		return generateBookLoot(player, item, "default_chest_treasure", location);
	}

	public static List<EnchantmentLevel> generateBookLoot(Player player, ItemStack item, String type) {
		return generateBookLoot(player, item, type, EnchantmentLocation.CHEST_LOOT);
	}

	public static List<EnchantmentLevel> generateBookLoot(Player player, ItemStack item, String type, EnchantmentLocation location) {
		ItemStack itemClone = item.clone();
		itemClone = generateChestLoot(player, itemClone, type, location, false);

		return EnchantmentUtils.getEnchantmentLevels(itemClone);
	}

	public static ItemStack generateFishingLoot(Player player, ItemStack item) {
		if (!ConfigString.USE_FISHING_LOOT.getBoolean()) return item;
		List<String> types = ConfigString.FISHING_TYPES.getStringList();
		String type = getType(types);
		Loots defaultLoot = Loots.getLoot("default_fishing_loot");
		FishingEnchantments enchantments = FishingEnchantments.getFishingEnchantments(player, item, type, defaultLoot);

		List<EnchantmentLevel> levels = enchantments.getEnchantments();
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "FishingLoot", EnchantmentLocation.FISHING_LOOT);
			if (!ConfigString.ALLOW_NO_ENCHANTMENTS.getBoolean()) return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static ItemStack generateMobSpawnLoot(ItemStack item, Entity entity) {
		String mobType = entity.getType().name().toLowerCase(Locale.ROOT);

		Loots defaultLoot = Loots.getLoot("default_mob_loot");
		List<String> types = null;
		if (ConfigString.USE_MOB_LOOT.exists(mobType + ".use")) {
			if (!ConfigString.USE_MOB_LOOT.getBoolean(mobType + ".use")) return item;
			defaultLoot = Loots.getLoot("default_" + mobType);
			types = ConfigString.MOB_TYPES.getStringList(mobType + ".types");
		} else if (ConfigString.USE_MOB_LOOT.getBoolean("other.use")) types = ConfigString.MOB_TYPES.getStringList("other.types");
		else
			return item;

		String type = getType(types);

		MobLootEnchantments enchantments = MobLootEnchantments.generateMobLoot(item, type, defaultLoot);

		List<EnchantmentLevel> levels = enchantments.getEnchantments();
		if (levels == null || levels.size() == 0) {
			warningMessages(item, "MobLoot", EnchantmentLocation.MOB_LOOT);
			if (!ConfigString.ALLOW_NO_ENCHANTMENTS.getBoolean()) return item;
		}

		item = EnchantmentUtils.removeAllEnchantments(item, true);
		return EnchantmentUtils.addEnchantmentsToItem(item, levels);
	}

	public static void warningMessages(ItemStack item, String type, EnchantmentLocation location) {
		String message = "Item couldn't find EnchantmentSolution enchantments.";
		if (!ConfigString.ALLOW_NO_ENCHANTMENTS.getBoolean()) message += " Keeping default enchantments on the item.";
		Chatable.sendDebug(message, Level.WARNING);
		Chatable.sendDebug("This occurs when items do not have valid enchantments based on the item being spawned (as well as the player spawning them, if applicable). THIS IS ONLY A BUG IF THE ITEM HAS VALID ENCHANTMENTS SPECIFIED FOR IT.", Level.WARNING);
		Chatable.sendDebug("Item: " + item.toString() + " Type: " + type + " Location: " + location);
	}

	private static String getType(List<String> types) {
		String type = "";
		int weight = 0;
		for(String s: types) {
			String[] split = s.split(" ");
			if (split.length == 2) {
				try {
					weight += Integer.parseInt(split[1]);
					continue;
				} catch (Exception ex) {}
				weight++;
			}
		}
		int getWeight = (int) (weight * Math.random());
		for(String s: types) {
			if (getWeight <= 0) {
				type = s;
				break;
			}
			String[] split = s.split(" ");
			if (split.length == 2) {
				try {
					getWeight -= Integer.parseInt(split[1]);
					continue;
				} catch (Exception ex) {}
				weight--;
			}
		}
		return type.substring(0, type.indexOf(" "));
	}
}
