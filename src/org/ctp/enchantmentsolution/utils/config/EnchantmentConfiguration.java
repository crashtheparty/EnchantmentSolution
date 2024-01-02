package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.*;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.item.ItemType;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.Weight;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.PermissionUtils;

public class EnchantmentConfiguration extends Configuration {

	private final CustomEnchantment enchantment;

	public EnchantmentConfiguration(CustomEnchantment enchantment, File file, BackupDB backup, String[] header) {
		super(EnchantmentSolution.getPlugin(), file, backup, header);

		this.enchantment = enchantment;
		migrateVersion();
		save();
	}

	public CustomEnchantment getEnchantment() {
		return enchantment;
	}

	@Override
	public void migrateVersion() {
		YamlConfig oldConfig = Configurations.getConfigurations().getEnchantments().getConfig();
		YamlConfigBackup config = getConfig();
		String location = (enchantment.getRelativeEnchantment() instanceof ApiEnchantmentWrapper ? ((ApiEnchantmentWrapper) enchantment.getRelativeEnchantment()).getPlugin().getName().toLowerCase(Locale.ROOT) : enchantment.getRelativeEnchantment() instanceof CustomEnchantmentWrapper ? "custom_enchantments" : "default_enchantments") + "." + enchantment.getName();

		if (oldConfig.contains(location + ".enabled")) {
			config.set("enabled", oldConfig.getBoolean(location + ".enabled"));
			List<String> locations = oldConfig.getStringList(location + ".enchantment_locations");
			if (locations == null) locations = new ArrayList<String>();
			config.set("locations.enchanting_table", locations.contains("TABLE"));
			config.set("locations.chests", locations.contains("CHEST_LOOT"));
			config.set("locations.mobs", locations.contains("MOB_LOOT"));
			config.set("locations.fishing", locations.contains("FISHING_LOOT"));
			config.set("locations.villager", locations.contains("VILLAGER_TRADES"));
			config.set("locations.piglin", locations.contains("PIGLIN_TRADES"));
			config.set("advanced.weight", oldConfig.getString(location + ".advanced.weight"));
			boolean fifty = ConfigString.LEVEL_FIFTY.getBoolean();
			config.set("advanced.enchantability." + (fifty ? "fifty" : "thirty") + ".constant", oldConfig.getInt(location + ".advanced.enchantability_constant"));
			config.set("advanced.enchantability." + (fifty ? "fifty" : "thirty") + ".modifier", oldConfig.getInt(location + ".advanced.enchantability_modifier"));
			config.set("advanced.enchantability." + (fifty ? "fifty" : "thirty") + ".start_level", oldConfig.getInt(location + ".advanced.enchantability_start_level"));
			config.set("advanced.enchantability." + (fifty ? "fifty" : "thirty") + ".max_level", oldConfig.getInt(location + ".advanced.enchantability_max_level"));
			config.set("advanced.conflicting_enchantments", oldConfig.getStringList(location + ".advanced.conflicting_enchantments"));
			oldConfig.removeKeys(location);
			oldConfig.saveConfig();
			config.saveConfig();
		}
	}

	@Override
	public void setDefaults() {
		YamlConfigBackup config = getConfig();
		config.getFromConfig();

		config.addDefault("enabled", true, new String[] { "Whether or not the enchantment is usable/obtainable on servers." });
		config.addDefault("locations.enchanting_table", Arrays.asList(enchantment.getDefaultEnchantmentLocations()).contains(EnchantmentLocation.TABLE), new String[] { "Whether enchantments will show up in the enchanting table." });
		config.addDefault("locations.chests", Arrays.asList(enchantment.getDefaultEnchantmentLocations()).contains(EnchantmentLocation.CHEST_LOOT), new String[] { "Whether enchantments will show up in CUSTOM chest loot (enabled in config.yml)." });
		config.addDefault("locations.mobs", Arrays.asList(enchantment.getDefaultEnchantmentLocations()).contains(EnchantmentLocation.MOB_LOOT), new String[] { "Whether enchantments will show up in CUSTOM mob loot (enabled in config.yml)." });
		config.addDefault("locations.fishing", Arrays.asList(enchantment.getDefaultEnchantmentLocations()).contains(EnchantmentLocation.FISHING_LOOT), new String[] { "Whether enchantments will show up in CUSTOM fishing loot (enabled in config.yml).", "mcMMO makes this value obsolete if enabled. If you have mcMMO, modify the mcmmo-fishing.yml file." });
		config.addDefault("locations.villager", Arrays.asList(enchantment.getDefaultEnchantmentLocations()).contains(EnchantmentLocation.VILLAGER), new String[] { "Whether enchantments will show up in CUSTOM villager trades (enabled in config.yml)." });
		config.addDefault("locations.piglin", Arrays.asList(enchantment.getDefaultEnchantmentLocations()).contains(EnchantmentLocation.PIGLIN), new String[] { "Whether enchantments will show up in CUSTOM piglin trades (enabled in config.yml) and bastion chest loot." });
		config.addDefault("locations.deep_dark", Arrays.asList(enchantment.getDefaultEnchantmentLocations()).contains(EnchantmentLocation.DEEP_DARK), new String[] { "Whether enchantments will show up in CUSTOM deep dark chest loot." });
		config.addDefault("locations.end_city", Arrays.asList(enchantment.getDefaultEnchantmentLocations()).contains(EnchantmentLocation.END_CITY), new String[] { "Whether enchantments will show up in CUSTOM end city chest loot." });
		config.addDefault("enchantment_item_types", ItemType.itemTypesToStrings(enchantment.getDefaultEnchantmentItemTypes()), new String[] { "What types of items may receive these enchantments through the enchanting table.", "Books will always allow enchanting regardless of these values." });
		config.addDefault("anvil_item_types", ItemType.itemTypesToStrings(enchantment.getDefaultAnvilItemTypes()), new String[] { "What types of items may receive these enchantments through the anvil.", "Books will always allow anviling regardless of these values." });
		config.addDefault("advanced.weight", enchantment.getDefaultWeightName(), new String[] { "The chance of getting the enchantment." });
		config.addEnum("advanced.weight", Arrays.asList(Weight.LEGENDARY.getName(), Weight.EPIC.getName(), Weight.VERY_RARE.getName(), Weight.RARE.getName(), Weight.UNCOMMON.getName(), Weight.COMMON.getName(), Weight.NULL.getName()));
		config.addDefault("advanced.enchantability.thirty.constant", enchantment.getDefaultThirtyConstant(), new String[] { "The modified enchantability constant." });
		config.addDefault("advanced.enchantability.thirty.modifier", enchantment.getDefaultThirtyModifier(), new String[] { "The modified enchantability modifier." });
		config.addDefault("advanced.enchantability.thirty.start_level", enchantment.getDefaultThirtyStartLevel(), new String[] { "Which level the enchanting table must require before allowing this enchantment to be shown." });
		config.addDefault("advanced.enchantability.thirty.max_level", enchantment.getDefaultThirtyMaxLevel(), new String[] { "The maximum level of the enchantment." });
		config.addDefault("advanced.enchantability.fifty.constant", enchantment.getDefaultFiftyConstant(), new String[] { "The formula works as follows: level * modifier + constant = minimum." });
		config.addDefault("advanced.enchantability.fifty.modifier", enchantment.getDefaultFiftyModifier(), new String[] { "The minimum is the lowest modified enchantability that is generated in order for this enchantment to be at a certain level." });
		config.addDefault("advanced.enchantability.fifty.start_level", enchantment.getDefaultFiftyStartLevel(), new String[] { "If level < 1, enchantment will not show up on items." });
		config.addDefault("advanced.enchantability.fifty.max_level", enchantment.getDefaultFiftyMaxLevel(), new String[] { "For more information about the enchantability section, see https://minecraft.fandom.com/wiki/Enchanting_mechanics#How_enchantments_are_chosen" });
		config.addDefault("advanced.conflicting_enchantments", enchantment.conflictingDefaultList(), new String[] { "Which enchantments may not be added together on an item through normal means." });
		config.addEnum("advanced.conflicting_enchantments", RegisterEnchantments.getEnchantmentNames());
		for(int i = 1; i <= enchantment.getMaxLevel(); i++) {
			PermissionUtils.removePermissions(new EnchantmentLevel(enchantment, i));
			config.addDefault("permissions.table.level" + i, false);
			config.addDefault("permissions.anvil.level" + i, false);
			PermissionUtils.addPermissions(new EnchantmentLevel(enchantment, i));
		}
	}

}
