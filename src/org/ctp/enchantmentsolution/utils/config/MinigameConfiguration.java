package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.Arrays;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

public class MinigameConfiguration extends Configuration {

	public MinigameConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/minigame.yml"), db, header);

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (getPlugin().isInitializing()) Chatable.get().sendInfo("Loading Minigame configuration...");

		YamlConfigBackup config = getConfig();

		File file = CrashConfigUtils.getTempFile(getClass(), "/resources/minigame_defaults.yml");

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

		config.addEnum("type", Arrays.asList("FAST", "MONDAYS", "CUSTOM"));

		if (!config.contains("custom.paging")) {
			config.addDefault("custom.items.first.material.show", "diamond_sword", "The material that will appear in the GUI.");
			config.addDefault("custom.items.first.material.enchant", "diamond_sword", "The material that will be enchanted upon clicking.");
			config.addDefault("custom.items.first.type", "enchantment", new String[] { "The type that will generate from this item.", "ENCHANTMENT: Generates the enchantments specified under 'enchantments'.", "RANDOM: Uses the values under 'books' to generate a single random enchantment.", "RANDOM_MULTI: Uses the values under 'books' to generate random enchantments (with possibility of multiple enchantments).", "RANDOM_BOOKSHELF: Uses the number of bookshelves surrounding the enchanting table to generate a single random enchantment.", "RANDOM_MULTI_BOOKSHELF: Uses the bookshelves surrounding the enchanting table to generate random enchantments (with possibility of multiple enchantments)." });
			config.addDefault("custom.items.first.cost", 5, "The cost of enchanting an item.");
			config.addDefault("custom.items.first.books.min", 0, "The min number of books randomized around an enchanting table.");
			config.addDefault("custom.items.first.books.max", 23, "The max number of books randomized around an enchanting table.");
			config.addDefault("custom.items.first.levels.min", 0, "The min level of randomized enchantments selected.");
			config.addDefault("custom.items.first.levels.max", 5, "The max level of randomized enchantments selected.");
			config.addDefault("custom.items.first.enchantments", Arrays.asList(new EnchantmentLevel(CERegister.SHARPNESS, 1).toString()), "The enchantments to be added to the item if not randomized.");
			config.addDefault("custom.items.first.slot", 1, "The slot the item will appear in.");
			config.addDefault("custom.items.first.increase.use", false, "Whether the cost to enchant will go up upon use. Reset uses with /esreset or /esreload.");
			config.addDefault("custom.items.first.increase.extra_cost_per_use", 1, "The extra cost per use to enchant.");
			config.addDefault("custom.items.first.material.max_cost", 0, "The maximum cost to enchant. Setting to 0 will allow unlimited cost.");

			config.addDefault("custom.items.second.material.show", "enchanted_book");
			config.addDefault("custom.items.second.material.enchant", "book");
			config.addDefault("custom.items.second.type", "random");
			config.addDefault("custom.items.second.cost", 5);
			config.addDefault("custom.items.second.books.min", 23);
			config.addDefault("custom.items.second.books.max", 23);
			config.addDefault("custom.items.second.levels.min", 5);
			config.addDefault("custom.items.second.levels.max", 5);
			config.addDefault("custom.items.second.enchantments", Arrays.asList());
			config.addDefault("custom.items.second.slot", 3);
			config.addDefault("custom.items.second.increase.use", true);
			config.addDefault("custom.items.second.increase.extra_cost_per_use", 1);
			config.addDefault("custom.items.second.increase.max_cost", 0);

			config.addDefault("custom.items.third.material.show", "enchanted_book");
			config.addDefault("custom.items.third.material.enchant", "book");
			config.addDefault("custom.items.third.type", "random_multi_bookshelf");
			config.addDefault("custom.items.third.cost", 5);
			config.addDefault("custom.items.third.books.min", 0);
			config.addDefault("custom.items.third.books.max", 23);
			config.addDefault("custom.items.third.levels.min", 0);
			config.addDefault("custom.items.third.levels.max", 5);
			config.addDefault("custom.items.third.enchantments", Arrays.asList());
			config.addDefault("custom.items.third.slot", 5);
			config.addDefault("custom.items.third.increase.use", true);
			config.addDefault("custom.items.third.increase.extra_cost_per_use", 1);
			config.addDefault("custom.items.third.increase.max_cost", 10);
		}

		if (getPlugin().isInitializing()) Chatable.get().sendInfo("Minigame configuration initialized!");

		config.saveConfig();

		file.delete();
	}

	@Override
	public void migrateVersion() {}

	@Override
	public void repairConfig() {}

}
