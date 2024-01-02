package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.Arrays;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.Configurations;

public class LootTypesConfiguration extends Configuration {

	public LootTypesConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/loot_types.yml"), db, header);
	}

	@Override
	public void migrateVersion() {}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing loot types configuration...");

		YamlConfigBackup config = getConfig();
		config.getFromConfig();

		if (config.getKeys().size() < 1) {
			config.addDefault("testing_random.type", "random", new String[] { "How the loot will generate.", "Each type of generation is previewed in the default config.", "Random selects a random enchantment and level." });
			config.addDefault("testing_random.blacklist_enchantments", Arrays.asList(), new String[] { "Deny these specific enchantments from this loot." });
			config.addDefault("testing_random.use_weights", true, new String[] { "Whether to use the weighting system.", "If false, enchantments will have an equal chance of being picked no matter its weight/rarity." });
			config.addDefault("testing_random.multiple.allow", true, new String[] { "Whether multiple enchantments can be added with this loot type." });
			config.addDefault("testing_random.multiple.chance_from_default", false, new String[] { "Whether the chance of obtaining multiple enchantments is based on default chances or the chances from this file." });
			config.addDefault("testing_random.multiple.chance", 1.0, new String[] { "The chance of a second enchantment being added.", "A value of 1 indicates a 100% chance.", "Chances can be above 1." });
			config.addDefault("testing_random.multiple.lower_chances", true, new String[] { "Whether the chance value will be decreased for each consecutive enchantment after the second." });
			config.addDefault("testing_random.multiple.lower_by", 0.50, new String[] { "How much the chance will lower for each level.", "0.5 will lower chances by half.", "Can not be negative or above 1." });
			config.addDefault("testing_random.level_normalize", 1, new String[] { "The level, between 0-2 inclusive in level 30 and between 0-5 inclusive in level 50 system, and how close to the middle values it would be.", "1 and -1 will be normal ranges.", "Above 1 will weight the number towards the center.", "Below -1 will weight the number towards the extremes." });

			config.addDefault("testing_from_selection.type", "from_selection", new String[] { "Like random, but only from a certain set of enchantments." });
			config.addDefault("testing_from_selection.enchantments", Arrays.asList());
			config.addDefault("testing_from_selection.use_weights", true);
			config.addDefault("testing_from_selection.multiple.allow", true);
			config.addDefault("testing_from_selection.multiple.chance_from_default", false);
			config.addDefault("testing_from_selection.multiple.chance", 1.0);
			config.addDefault("testing_from_selection.multiple.lower_chances", true);
			config.addDefault("testing_from_selection.multiple.lower_by", 0.50);
			config.addDefault("testing_from_selection.level_normalize", 1);

			config.addDefault("testing_enchanting_table.type", "enchanting_table", new String[] { "Generates using Enchanting Table values." });
			config.addDefault("testing_enchanting_table.use_weights", false);
			config.addDefault("testing_enchanting_table.multiple.allow", true);
			config.addDefault("testing_enchanting_table.multiple.chance_from_default", false);
			config.addDefault("testing_enchanting_table.multiple.chance", 0.75);
			config.addDefault("testing_enchanting_table.multiple.lower_chances", false);
			config.addDefault("testing_enchanting_table.multiple.lower_by", 0.60);
			config.addDefault("testing_enchanting_table.enchanting.bookshelves.min", -10, new String[] { "The minimum amount of bookshelves to use when calculating enchantments.", "Numbers lower than 0 will weight the bookshelves towards 0." });
			config.addDefault("testing_enchanting_table.enchanting.bookshelves.max", 30, new String[] { "The maximum amount of bookshelves to use when calculation enchantments.", "Values above 16 (24 in the Level 50 system) will weight the bookshelves towards the maximum normal value." });
			config.addDefault("testing_enchanting_table.enchanting.bookshelves.normalize", 1);
			config.addDefault("testing_enchanting_table.enchanting.slot.min", 0, new String[] { "The minimum slot to select from.", "Slot 0 is the first slot. Slot 2 is the level 30 slot. Slot 5 is the Level 50 slot." });
			config.addDefault("testing_enchanting_table.enchanting.slot.max", 6);
			config.addDefault("testing_enchanting_table.enchanting.slot.normalize", 1);

			config.addDefault("testing_enchanting_table_location.type", "enchanting_table_location", new String[] { "Generates using Enchanting Table values but bases bookshelves off of the location." });
			config.addDefault("testing_enchanting_table_location.use_weights", true);
			config.addDefault("testing_enchanting_table_location.multiple.allow", true);
			config.addDefault("testing_enchanting_table_location.multiple.chance_from_default", false);
			config.addDefault("testing_enchanting_table_location.multiple.chance", 1.20);
			config.addDefault("testing_enchanting_table_location.multiple.lower_chances", false);
			config.addDefault("testing_enchanting_table_location.multiple.lower_by", 0.30);
			config.addDefault("testing_enchanting_table_location.enchanting.slot.min", -2);
			config.addDefault("testing_enchanting_table_location.enchanting.slot.max", 4);
			config.addDefault("testing_enchanting_table_location.enchanting.slot.normalize", -1);

			config.addDefault("testing_enchantability.type", "enchantability", new String[] { "Generates enchantments based upon enchantability." });
			config.addDefault("testing_enchantability.use_weights", true);
			config.addDefault("testing_enchantability.multiple.allow", true);
			config.addDefault("testing_enchantability.multiple.chance_from_default", true);
			config.addDefault("testing_enchantability.multiple.chance", 1.0);
			config.addDefault("testing_enchantability.multiple.lower_chances", true);
			config.addDefault("testing_enchantability.multiple.lower_by", 0.50);
			config.addDefault("testing_enchantability.enchantability.min", 1, new String[] { "The lowest enchantability.", "Enchantability less than 1 will produce no enchantments unless modified." });
			config.addDefault("testing_enchantability.enchantability.max", 80, new String[] { "The highest enchantability.", "Highest Level 50 value is 75 and highest level 30 value is 45." });
			config.addDefault("testing_enchantability.enchantability.normalize", 3);

			config.addDefault("testing_enchanting_levels.type", "enchanting_levels", new String[] { "Generates enchantments based upon the level." });
			config.addDefault("testing_enchanting_levels.use_weights", true);
			config.addDefault("testing_enchanting_levels.multiple.allow", true);
			config.addDefault("testing_enchanting_levels.multiple.chance_from_default", false);
			config.addDefault("testing_enchanting_levels.multiple.chance", 1.0);
			config.addDefault("testing_enchanting_levels.multiple.lower_chances", true);
			config.addDefault("testing_enchanting_levels.multiple.lower_by", 0.50);
			config.addDefault("testing_enchanting_levels.levels.min", 1, new String[] { "The lowest level." });
			config.addDefault("testing_enchanting_levels.levels.max", 50, new String[] { "The highest level." });
			config.addDefault("testing_enchanting_levels.levels.normalize", 1);
		}

		if (Configurations.isInitializing()) Chatable.get().sendInfo("Loot types configuration initialized!");
	}

}
