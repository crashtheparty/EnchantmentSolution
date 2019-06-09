package org.ctp.enchantmentsolution.utils;

import org.bukkit.Material;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.Language;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.utils.items.nms.ItemType;

public class ConfigUtils {
	
	private static int LAPIS_CONSTANT = -1;
	private static int LAPIS_MODIFIER = 2;
	private static double MULTI_ENCHANT_DIVISOR = 75.0D;
	
	public static int getMaxEnchantments() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getInt("max_enchantments");
	}
	
	public static boolean customEnchantsDisabled() {
		return !EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants");
	}
	
	public static int getLevelDivisor() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getInt("level_divisor");
	}
	
	public static boolean getChestLoot(){
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("chest_loot");
	}
	
	public static boolean getMobLoot(){
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("mob_loot");
	}
	
	public static boolean getFishingLoot(){
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("fishing_loot");
	}
	
	public static boolean getEnchantabilityDecay(){
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("enchantability_decay");
	}
	
	public static boolean getProtectionConflicts(){
		if(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() > 4) {
			return false;
		}
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("protection_conflicts");
	}
	
	public static boolean getEnchantedBook(){
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_enchanted_books");
	}
	
	public static boolean useStartLevel() {
		if (EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getBoolean("use_starting_level");
		}
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants");
	}

	public static boolean useLevel50() {
		if (EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return true;
		}
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants");
	}

	public static boolean useThirtyEnchantability() {
		if (EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return !EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getBoolean("use_lapis_modifier");
		}
		return !EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants");
	}

	public static boolean usePermissions() {
		if (EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getBoolean("use_permissions");
		}
		return false;
	}

	public static boolean useDefaultAnvil() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("default_anvil_use");
	}

	public static boolean useLapisInTable() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("lapis_in_table");
	}

	public static boolean useLegacyGrindstone() {
		if (EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber() < 4) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("grindstone.use_legacy");
		}
		return false;
	}

	public static int getMaxRepairLevel() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getInt("max_repair_level");
	}

	public static boolean updateLegacyEnchantments() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("update_legacy_enchantments");
	}

	public static int getLevelFromType(String type) {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getInt("loots." + type + ".levels");
	}

	public static int getBookshelvesFromType(String type) {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getInt("loots." + type + ".bookshelves");
	}

	public static boolean includeTreasureFromType(String type) {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("loots." + type + ".treasure");
	}

	public static String getLocalizedName(Material material) {
		return EnchantmentSolution.getPlugin().getConfigFiles().getLanguageFile().getString("vanilla." + ItemType.getUnlocalizedName(material));
	}

	public static Language getLanguage() {
		return Language.getLanguage(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getString("language"));
	}

	public static boolean grindstoneTakeEnchantments() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("grindstone.take_enchantments");
	}

	public static boolean grindstoneTakeRepairCost() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("grindstone.set_repair_cost");
	}
	
	public static boolean grindstoneDestroyItem() {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("grindstone.destroy_take_item");
	}
	
	public static int getLapisConstant() {
		if(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getInt("lapis_modifiers.constant");
		}
		if(!EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants")) {
			return 0;
		}
		return LAPIS_CONSTANT;
	}
	
	public static int getLapisModifier() {
		if(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getInt("lapis_modifiers.modifier");
		}
		if(!EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants")) {
			return 0;
		}
		return LAPIS_MODIFIER;
	}
	
	public static double getMultiEnchantDivisor() {
		if(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("use_advanced_file")) {
			return EnchantmentSolution.getPlugin().getConfigFiles().getEnchantmentAdvancedConfig().getDouble("multi_enchant_divisor");
		}
		if(!EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("level_50_enchants")) {
			return 50.0D;
		}
		return MULTI_ENCHANT_DIVISOR;
	}
	
	public static boolean isRepairable(CustomEnchantment enchant) {
		if(EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getString("disable_enchant_method").equals("repairable")) {
			return true;
		}
		
		if(enchant.isEnabled()) {
			return true;
		}
		
		return false;
	}

	public static boolean isAdvancementActive(String string) {
		return EnchantmentSolution.getPlugin().getConfigFiles().getDefaultConfig().getBoolean("advancements." + string);
	}

	public static String getAdvancementName(String string) {
		return EnchantmentSolution.getPlugin().getConfigFiles().getLanguageFile().getString("advancements." + string + ".name");
	}

	public static String getAdvancementDescription(String string) {
		return EnchantmentSolution.getPlugin().getConfigFiles().getLanguageFile().getString("advancements." + string + ".description");
	}

}
