package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.Language;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.VersionUtils;

public class MainConfiguration extends Configuration {

	private List<String> enchantingTypes = Arrays.asList("vanilla_30", "vanilla_30_custom", "enhanced_30", "enhanced_30_custom", "enhanced_50", "enhanced_50_custom");

	public MainConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/config.yml"), db, header);

		migrateVersion();
		save();
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing main configuration...");

		YamlConfigBackup config = getConfig();

		File file = CrashConfigUtils.getTempFile(getClass(), "/resources/config_defaults.yml");

		YamlConfig defaultConfig = new YamlConfig(file, new String[] {});
		defaultConfig.getFromConfig();
		for(String str: defaultConfig.getAllEntryKeys())
			if (defaultConfig.get(str) != null) if (str.startsWith("config_comments.")) try {
				config.addComments(str, defaultConfig.getStringList(str).toArray(new String[] {}));
			} catch (Exception ex) {
				Chatable.get().sendWarning("Config key " + str.replaceFirst("config_comments.", "") + " does not exist in the defaults file!");
			}
			else
				config.addDefault(str, defaultConfig.get(str));
		config.addEnum("language", Language.getValues());
		config.addEnum("disable_enchant_method", Arrays.asList("vanish", "visible", "repairable"));
		config.addEnum("enchanting_table.enchanting_type", enchantingTypes);
		config.addEnum("gametypes", Arrays.asList("MINIGAME", "RPG", "HARD"));
		config.addMinMax("anvil.max_repair_level", 40, 1000000);
		if (VersionUtils.getBukkitVersionNumber() > 3) {
			config.addDefault("loots.chests.pillager_outpost.bookshelves", 10);
			config.addDefault("loots.chests.pillager_outpost.levels", 1);
			config.addDefault("loots.chests.pillager_outpost.treasure", true);
		}

		config.writeDefaults();

		if (Configurations.isInitializing()) Chatable.get().sendInfo("Main configuration initialized!");

		file.delete();
	}

	@Override
	public void migrateVersion() {
		YamlConfigBackup config = getConfig();

		if (config.getBoolean("level_50_enchants")) {
			if (config.getBoolean("use_advanced_file")) config.set("enchanting_table.enchanting_type", "enhanced_50_custom");
			else
				config.set("enchanting_table.enchanting_type", "enhanced_50");
			config.removeKey("level_50_enchants");
			config.removeKey("use_advanced_file");
		} else if (config.getBooleanValue("level_50_enchants") != null) {
			if (config.getBoolean("use_advanced_file")) config.set("enchanting_table.enchanting_type", "enhanced_30_custom");
			else
				config.set("enchanting_table.enchanting_type", "enhanced_30");
			config.removeKey("level_50_enchants");
			config.removeKey("use_advanced_file");
		}
		if (config.getBooleanValue("lapis_in_table") != null) {
			config.set("enchanting_table.lapis_in_table", config.getBoolean("lapis_in_table"));
			config.removeKey("lapis_in_table");
		}
		if (config.getInteger("max_repair_level") != null) {
			config.set("anvil.max_repair_level", config.getInt("max_repair_level"));
			config.removeKey("max_repair_level");
		}
		if (config.getBooleanValue("get_latest_version") != null) {
			config.set("version.get_latest", config.getBoolean("get_latest_version"));
			config.removeKey("get_latest_version");
		}
		String setType = config.getString("enchanting_table.enchanting_type");
		if (setType != null && !enchantingTypes.contains(setType)) {
			config.set("enchanting_table.enchanting_type", "enhanced_50");
			setType = "enhanced_50";
		}

		if (setType != null) {
			config.set("enchanting_table.custom_gui", setType.contains("enhanced"));
			config.set("enchanting_table.level_fifty", setType.contains("50"));
			config.set("anvil.custom_gui", setType.contains("enhanced"));
			config.set("grindstone.custom_gui", setType.contains("enhanced"));
		}

		if (config.getBooleanValue("enchanting_table.use_enchanted_books") != null) {
			config.set("use_enchanted_books", config.getBoolean("enchanting_table.use_enchanted_books"));
			config.removeKey("enchanting_table.use_enchanted_books");
		}

		if (config.getBooleanValue("mob_loot") != null) {
			config.set("loots.mobs.use", config.getBoolean("mob_loot"));
			config.removeKey("mob_loot");
		}

		if (config.getBooleanValue("chest_loot") != null) {
			config.set("loots.chests.use", config.getBoolean("chest_loot"));
			config.removeKey("chest_loot");
		}

		if (config.getBooleanValue("fishing_loot") != null) {
			config.set("loots.fishing.use", config.getBoolean("fishing_loot"));
			config.removeKey("fishing_loot");
		}

		for(String s: config.getLevelEntryKeys("loots"))
			if (!Arrays.asList("fishing", "mobs", "chests").contains(s.split("\\.")[1])) {
				for(String t: config.getLevelEntryKeys(s))
					if (config.get(t) != null) {
						config.set("loots.chests." + t.substring(6), config.get(t));
						config.removeKey(t);
					}
				config.removeKey(s);
			}

		if (config.getBooleanValue("villager_trades") != null) {
			config.set("trades.villager", config.getBoolean("villager_trades"));
			config.removeKey("villager_trades");
		}
	}

	@Override
	public void repairConfig() {
		YamlConfigBackup config = getConfig();
		if (config.getInt("anvil.level_divisor") <= 0) config.set("anvil.level_divisor", 4);
	}

}
