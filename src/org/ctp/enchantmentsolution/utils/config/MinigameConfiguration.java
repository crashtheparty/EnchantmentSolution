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
import org.ctp.enchantmentsolution.utils.Configurations;

public class MinigameConfiguration extends Configuration {

	public MinigameConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/minigame.yml"), db, header);

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing Minigame configuration...");

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
			config.addDefault("custom.items.first.costs.use", Arrays.asList("level", "lapis", "economy"), new String[] { "Which costs will be used by ES for a player to buy the item.", "level: The player's experience level.", "lapis: The lapis lazuli a player has.", "economy: The money the player has using a Vault Economy plugin." });
			config.addDefault("custom.items.first.costs.level", 5, "The experience level cost of enchanting an item.");
			config.addDefault("custom.items.first.costs.use_level_cost_increase", false, "Whether the experience level cost should increase with each use (resets on '/esreset', '/esreload', and server restart.");
			config.addDefault("custom.items.first.costs.extra_level_cost_per_use", 1, "The extra experience level cost of enchanting an item.");
			config.addDefault("custom.items.first.costs.max_level_cost", 0, "The maximum experience level cost of enchanting an item. 0 means no max.");
			config.addDefault("custom.items.first.costs.lapis", 5, "The lapis lazuli a player must have in their inventory to enchant an item.");
			config.addDefault("custom.items.first.costs.use_lapis_cost_increase", false, "Whether the lapis lazuli cost should increase with each use (resets on '/esreset', '/esreload', and server restart.");
			config.addDefault("custom.items.first.costs.extra_lapis_cost_per_use", 1, "The extra lapis lazuli cost of enchanting an item.");
			config.addDefault("custom.items.first.costs.max_lapis_cost", 0, "The maximum lapis lazuli cost of enchanting an item. 0 means no max.");
			config.addDefault("custom.items.first.costs.economy", 0, "The Vault economy cost of enchanting an item.");
			config.addDefault("custom.items.first.costs.use_economy_cost_increase", false, "Whether the economy cost should increase with each use (resets on '/esreset', '/esreload', and server restart.");
			config.addDefault("custom.items.first.costs.extra_economy_cost_per_use", 0, "The extra economy cost of enchanting an item.");
			config.addDefault("custom.items.first.costs.max_economy_cost", 0, "The maximum economy cost of enchanting an item. 0 means no max.");
			config.addDefault("custom.items.first.books.min", 0, "The min number of books randomized around an enchanting table.");
			config.addDefault("custom.items.first.books.max", 23, "The max number of books randomized around an enchanting table.");
			config.addDefault("custom.items.first.levels.min", 0, "The min level of randomized enchantments selected.");
			config.addDefault("custom.items.first.levels.max", 5, "The max level of randomized enchantments selected.");
			config.addDefault("custom.items.first.enchantments", Arrays.asList(new EnchantmentLevel(CERegister.SHARPNESS, 1).toString()), "The enchantments to be added to the item if not randomized.");
			config.addDefault("custom.items.first.slot", 1, "The slot the item will appear in.");

			config.addDefault("custom.items.second.material.show", "enchanted_book");
			config.addDefault("custom.items.second.material.enchant", "book");
			config.addDefault("custom.items.second.type", "random");
			config.addDefault("custom.items.second.costs.use", Arrays.asList("level", "lapis", "economy"));
			config.addDefault("custom.items.second.costs.level", 5);
			config.addDefault("custom.items.second.costs.use_level_cost_increase", true);
			config.addDefault("custom.items.second.costs.extra_level_cost_per_use", 1);
			config.addDefault("custom.items.second.costs.max_level_cost", 0);
			config.addDefault("custom.items.second.costs.lapis", 5);
			config.addDefault("custom.items.second.costs.use_lapis_cost_increase", false);
			config.addDefault("custom.items.second.costs.extra_lapis_cost_per_use", 1);
			config.addDefault("custom.items.second.costs.max_lapis_cost", 0);
			config.addDefault("custom.items.second.costs.economy", 0);
			config.addDefault("custom.items.second.costs.use_economy_cost_increase", false);
			config.addDefault("custom.items.second.costs.extra_economy_cost_per_use", 0);
			config.addDefault("custom.items.second.costs.max_economy_cost", 0);
			config.addDefault("custom.items.second.books.min", 23);
			config.addDefault("custom.items.second.books.max", 23);
			config.addDefault("custom.items.second.levels.min", 5);
			config.addDefault("custom.items.second.levels.max", 5);
			config.addDefault("custom.items.second.enchantments", Arrays.asList());
			config.addDefault("custom.items.second.slot", 3);

			config.addDefault("custom.items.third.material.show", "enchanted_book");
			config.addDefault("custom.items.third.material.enchant", "book");
			config.addDefault("custom.items.third.type", "random_multi_bookshelf");
			config.addDefault("custom.items.third.costs.use", Arrays.asList("level", "lapis", "economy"));
			config.addDefault("custom.items.third.costs.level", 5);
			config.addDefault("custom.items.third.costs.use_level_cost_increase", true);
			config.addDefault("custom.items.third.costs.extra_level_cost_per_use", 1);
			config.addDefault("custom.items.third.costs.max_level_cost", 10);
			config.addDefault("custom.items.third.costs.lapis", 5);
			config.addDefault("custom.items.third.costs.use_lapis_cost_increase", false);
			config.addDefault("custom.items.third.costs.extra_lapis_cost_per_use", 1);
			config.addDefault("custom.items.third.costs.max_lapis_cost", 0);
			config.addDefault("custom.items.third.costs.economy", 0);
			config.addDefault("custom.items.third.costs.use_economy_cost_increase", false);
			config.addDefault("custom.items.third.costs.extra_economy_cost_per_use", 0);
			config.addDefault("custom.items.third.costs.max_economy_cost", 0);
			config.addDefault("custom.items.third.books.min", 0);
			config.addDefault("custom.items.third.books.max", 23);
			config.addDefault("custom.items.third.levels.min", 0);
			config.addDefault("custom.items.third.levels.max", 5);
			config.addDefault("custom.items.third.enchantments", Arrays.asList());
			config.addDefault("custom.items.third.slot", 5);
		}

		config.writeDefaults();

		if (Configurations.isInitializing()) Chatable.get().sendInfo("Minigame configuration initialized!");

		file.delete();
	}

	@Override
	public void migrateVersion() {}

	@Override
	public void repairConfig() {}

}
