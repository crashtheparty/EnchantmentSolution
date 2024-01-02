package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.Arrays;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.Configurations;

public class EnchantmentsConfiguration extends Configuration {

	private boolean firstLoad = true;

	public EnchantmentsConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/enchantments.yml"), db, header);

		migrateVersion();
		save();
	}

	@Override
	public void setDefaults() {
		if (firstLoad) Chatable.get().sendInfo("Initializing enchantment configuration...");

		YamlConfigBackup config = getConfig();

		config.getFromConfig();

		config.addDefault("max_enchantments", 0, new String[] { "Max enchantments on each item. 0 allows infinite" });
		config.addDefault("use_advanced_options", false, new String[] { "Use the advanced customization options for the plugin", "** Only modify if you understand what you're doing! **" });
		config.addDefault("use_permissions", false, new String[] { "Use the permission system per player for all enchantments.", "Permissions use the system \"enchantmentsolution.<enchant_name>.<type>.level<int>\"", "enchant_name: Enchantment name as used below", "type: either table (for enchanting items) or anvil (for combining items)", "int: the enchantment level", "Override permission: enchantmentsolution.permissions.ignore" });
		config.addDefault("use_enchanted_books", false, new String[] { "Uses the vanilla Enchanted Books rather than Books to store enchantments" });
		config.addDefault("disable_enchant_method", "visible", new String[] { "How disabling an enchantment in its file will work", "Options:", "vanish - removes enchantment from items", "visible - keeps enchantment on item, but custom effects will not work and anvil will remove enchant", "repairable - same as above but anvil will not remove enchant" });
		config.addDefault("lore_location", "top", new String[] { "Modify where custom enchantments will be in the item's lore", "- top: At the top of the lore right under normal enchantments", "- bottom: At the very bottom of the lore", "- unset: Enchantments will be placed whereever they are (can change randomly when trying to fix them)" });
		config.addDefault("legacy_frequent_flyer", false, new String[] { "Level of Frequent Flyer increases relative durability instead of the speed." });
		config.addDefault("protection_conflicts", true, new String[] { "All protection types conflict with each other" });

		config.addDefault("block_enchants.play_sounds", true, new String[] { "Whether enchantments like Telepathy and Height++ will play sounds for broken blocks." });
		config.addDefault("block_enchants.use_particles", true, new String[] { "Whether enchantments like Telepathy and Height++ will spawn particles around broken blocks.", "May cause large amounts of lag with heigher levels of Height++ and Width++." });
		config.addDefault("block_enchants.async.delay_on_timeout", 5, new String[] { "When HWD is running and may cause lag, HWD will not run for this amount of ticks", "This is to keep the server running as smooth as possible" });
		config.addDefault("block_enchants.async.global_blocks_per_tick", 250, new String[] { "The maximum blocks broken globally using Height/Width/Depth++" });
		config.addDefault("block_enchants.async.player_blocks_per_tick", 30, new String[] { "The maximum blocks broken per tick per player using Height/Width/Depth++" });

		config.addDefault("mewl.level_thirty_equation", false, new String[] { "" });

		config.addEnum("disable_enchant_method", Arrays.asList("vanish", "visible", "repairable"));
		config.addEnum("lore_location", Arrays.asList("top", "bottom", "unset"));

		config.writeDefaults();

		if (firstLoad) Chatable.get().sendInfo("Enchantment configuration initialized!");
		firstLoad = false;
	}

	@Override
	public void migrateVersion() {
		YamlConfig config = getConfig();
		YamlConfig main = Configurations.getConfigurations().getConfig().getConfig();

		if (main.contains("enchanting_table.enchanting_type")) {
			config.set("advanced_options.use", main.getString("enchanting_table.enchanting_type").contains("custom"));
			main.removeKey("enchanting_table.enchanting_type");
		}

		if (main.contains("enchantability_decay")) {
			config.set("advanced_options.decay", main.getBoolean("enchantability_decay"));
			main.removeKey("enchantability_decay");
		}

		if (main.contains("max_enchantments")) {
			config.set("max_enchantments", main.get("max_enchantments"));
			main.removeKey("max_enchantments");
		}

		if (config.contains("advanced_options.use")) {
			config.set("use_advanced_options", config.get("advanced_options.use"));
			config.removeKey("advanced_options.use");
		}

		if (config.contains("advanced_options.use_permissions")) {
			config.set("use_permissions", config.get("advanced_options.use_permissions"));
			config.removeKey("advanced_options.use_permissions");
		}

		if (main.contains("use_enchanted_books")) {
			config.set("use_enchanted_books", main.get("use_enchanted_books"));
			main.removeKey("use_enchanted_books");
		}

		if (main.contains("disable_enchant_method")) {
			config.set("disable_enchant_method", main.get("disable_enchant_method"));
			main.removeKey("disable_enchant_method");
		}

		if (main.contains("protection_conflicts")) {
			config.set("protection_conflicts", main.get("protection_conflicts"));
			main.removeKey("protection_conflicts");
		}

		if (main.contains("lore_location")) {
			config.set("lore_location", main.get("lore_location"));
			main.removeKey("lore_location");
		}

		main.saveConfig();
	}

	@Override
	public void repairConfig() {}

}
