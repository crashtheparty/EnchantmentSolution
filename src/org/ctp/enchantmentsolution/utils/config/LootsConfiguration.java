package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.Arrays;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.Configurations;

public class LootsConfiguration extends Configuration {

	public LootsConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/loots.yml"), db, header);

		migrateVersion();
		save();
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing loots configuration...");

		YamlConfigBackup config = getConfig();
		config.getFromConfig();
		config.addDefault("allow_no_enchantments", false, new String[] { "If an item in a chest, villager trade, etc. spawns with no enchantments, put no enchantments on the item.", "If set to false, will put the original enchantments generated through vanilla Minecraft on the item." });
		config.addDefault("villager_trades.use", false, new String[] { "Enable villager trades", "Sub-categories will not work unless this is true" });
		config.addDefault("villager_trades.levels.fifty_book.min", 0, new String[] { "The levels influence the emerald price of villager trades (%level%)" });
		config.addDefault("villager_trades.levels.fifty_book.max", 6);
		config.addDefault("villager_trades.levels.fifty_book.normalize", 4);
		config.addDefault("villager_trades.levels.thirty_book.min", 1);
		config.addDefault("villager_trades.levels.thirty_book.max", 5);
		config.addDefault("villager_trades.levels.thirty_book.normalize", 1);
		config.addDefault("villager_trades.levels.fifty_nonbook.min", 0);
		config.addDefault("villager_trades.levels.fifty_nonbook.max", 3);
		config.addDefault("villager_trades.levels.fifty_nonbook.normalize", 4);
		config.addDefault("villager_trades.levels.thirty_nonbook.min", 0);
		config.addDefault("villager_trades.levels.thirty_nonbook.max", 2);
		config.addDefault("villager_trades.levels.thirty_nonbook.normalize", 1);
		config.addDefault("villager_trades.enchanted_book.fifty.use", true);
		config.addDefault("villager_trades.enchanted_book.fifty.price", "%level% * 2 * %level% + (%random% * (%level% * 3 + 7) + 3)");
		config.addDefault("villager_trades.enchanted_book.fifty.types", Arrays.asList("default_villager_book_fifty 1"));
		config.addDefault("villager_trades.enchanted_book.thirty.use", true);
		config.addDefault("villager_trades.enchanted_book.thirty.price", "(%level% + 1) * %level% * 2 * %level% + (%random% * (%level% * 3 + 15) + 3)");
		config.addDefault("villager_trades.enchanted_book.thirty.types", Arrays.asList("default_villager_book_thirty 1"));
		config.addDefault("villager_trades.iron_axe.use", true);
		config.addDefault("villager_trades.iron_axe.price", "%random% * 16 + 7");
		config.addDefault("villager_trades.iron_axe.types", Arrays.asList("default_villager_iron_axe 1"));
		config.addDefault("villager_trades.iron_pickaxe.use", true);
		config.addDefault("villager_trades.iron_pickaxe.price", "%random% * 16 + 7");
		config.addDefault("villager_trades.iron_pickaxe.types", Arrays.asList("default_villager_iron_pickaxe 1"));
		config.addDefault("villager_trades.iron_shovel.use", true);
		config.addDefault("villager_trades.iron_shovel.price", "%random% * 16 + 7");
		config.addDefault("villager_trades.iron_shovel.types", Arrays.asList("default_villager_iron_shovel 1"));
		config.addDefault("villager_trades.iron_sword.use", true);
		config.addDefault("villager_trades.iron_sword.price", "%random% * 16 + 7");
		config.addDefault("villager_trades.iron_sword.types", Arrays.asList("default_villager_iron_sword 1"));
		config.addDefault("villager_trades.fishing_rod.use", true);
		config.addDefault("villager_trades.fishing_rod.price", "%random% * 16 + 7");
		config.addDefault("villager_trades.fishing_rod.types", Arrays.asList("default_villager_fishing_rod 1"));
		config.addDefault("villager_trades.bow.use", true);
		config.addDefault("villager_trades.bow.price", "%random% * 16 + 7");
		config.addDefault("villager_trades.bow.types", Arrays.asList("default_villager_bow 1"));
		config.addDefault("villager_trades.crossbow.use", true);
		config.addDefault("villager_trades.crossbow.price", "%random% * 16 + 7");
		config.addDefault("villager_trades.crossbow.types", Arrays.asList("default_villager_crossbow 1"));
		config.addDefault("villager_trades.diamond_sword.use", true);
		config.addDefault("villager_trades.diamond_sword.price", "%random% * 17 + 11");
		config.addDefault("villager_trades.diamond_sword.types", Arrays.asList("default_villager_diamond_sword 1"));
		config.addDefault("villager_trades.diamond_shovel.use", true);
		config.addDefault("villager_trades.diamond_shovel.price", "%random% * 17 + 11");
		config.addDefault("villager_trades.diamond_shovel.types", Arrays.asList("default_villager_diamond_shovel 1"));
		config.addDefault("villager_trades.diamond_boots.use", true);
		config.addDefault("villager_trades.diamond_boots.price", "%random% * 17 + 11");
		config.addDefault("villager_trades.diamond_boots.types", Arrays.asList("default_villager_diamond_boots 1"));
		config.addDefault("villager_trades.diamond_helmet.use", true);
		config.addDefault("villager_trades.diamond_helmet.price", "%random% * 17 + 11");
		config.addDefault("villager_trades.diamond_helmet.types", Arrays.asList("default_villager_diamond_helmet 1"));
		config.addDefault("villager_trades.diamond_pickaxe.use", true);
		config.addDefault("villager_trades.diamond_pickaxe.price", "%random% * 18 + 18");
		config.addDefault("villager_trades.diamond_pickaxe.types", Arrays.asList("default_villager_diamond_pickaxe 1"));
		config.addDefault("villager_trades.diamond_axe.use", true);
		config.addDefault("villager_trades.diamond_axe.price", "%random% * 18 + 18");
		config.addDefault("villager_trades.diamond_axe.types", Arrays.asList("default_villager_diamond_axe 1"));
		config.addDefault("villager_trades.diamond_leggings.use", true);
		config.addDefault("villager_trades.diamond_leggings.price", "%random% * 18 + 18");
		config.addDefault("villager_trades.diamond_leggings.types", Arrays.asList("default_villager_diamond_leggings 1"));
		config.addDefault("villager_trades.diamond_chestplate.use", true);
		config.addDefault("villager_trades.diamond_chestplate.price", "%random% * 18 + 18");
		config.addDefault("villager_trades.diamond_chestplate.types", Arrays.asList("default_villager_diamond_chestplate 1"));
		config.addDefault("villager_trades.other.use", true);
		config.addDefault("villager_trades.other.price", "%level% * 2 * %level% + (%random% * (%level% * 3 + 7) + 3)");
		config.addDefault("villager_trades.other.types", Arrays.asList("default_villager_other 1"));
		config.addDefault("piglin_trades.use", false);
		config.addDefault("piglin_trades.types", Arrays.asList("default_piglin_trades 1"));
		config.addDefault("fishing.use", true);
		config.addDefault("fishing.types", Arrays.asList("default_fishing_loot 1"));
		config.addDefault("mobs.use", true, new String[] { "Enable mob loot", "Sub-categories will not work unless this is true" });
		config.addDefault("mobs.zombie.use", true);
		config.addDefault("mobs.zombie.types", Arrays.asList("default_zombie_loot 1"));
		config.addDefault("mobs.zombie_villager.use", true);
		config.addDefault("mobs.zombie_villager.types", Arrays.asList("default_zombie_villager_loot 1"));
		config.addDefault("mobs.drowned.use", true);
		config.addDefault("mobs.drowned.types", Arrays.asList("default_drowned_loot 1"));
		config.addDefault("mobs.husk.use", true);
		config.addDefault("mobs.husk.types", Arrays.asList("default_husk_loot 1"));
		config.addDefault("mobs.piglin.use", true);
		config.addDefault("mobs.piglin.types", Arrays.asList("default_piglin_loot 1"));
		config.addDefault("mobs.skeleton.use", true);
		config.addDefault("mobs.skeleton.types", Arrays.asList("default_skeleton_loot 1"));
		config.addDefault("mobs.stray.use", true);
		config.addDefault("mobs.stray.types", Arrays.asList("default_stray_loot 1"));
		config.addDefault("mobs.wither_skeleton.use", true);
		config.addDefault("mobs.wither_skeleton.types", Arrays.asList("default_wither_skeleton_loot 1"));
		config.addDefault("mobs.zombified_piglin.use", true);
		config.addDefault("mobs.zombified_piglin.types", Arrays.asList("default_zombified_piglin_loot 1"));
		config.addDefault("mobs.other.use", true);
		config.addDefault("mobs.other.types", Arrays.asList("default_mob_loot 1"));
		config.addDefault("chests.use", true, new String[] { "Enable chest loot", "Sub-categories will not work unless this is true" });
		config.addDefault("chests.end_city_treasure.use", true);
		config.addDefault("chests.end_city_treasure.types", Arrays.asList("default_end_city_treasure 1"));
		config.addDefault("chests.simple_dungeon.use", true);
		config.addDefault("chests.simple_dungeon.types", Arrays.asList("default_simple_dungeon 1"));
		config.addDefault("chests.shipwreck_supply.use", true);
		config.addDefault("chests.shipwreck_supply.types", Arrays.asList("default_shipwreck_supply 1"));
		config.addDefault("chests.woodland_mansion.use", true);
		config.addDefault("chests.woodland_mansion.types", Arrays.asList("default_woodland_mansion 1"));
		config.addDefault("chests.stronghold_library.use", true);
		config.addDefault("chests.stronghold_library.types", Arrays.asList("default_stronghold_library 1"));
		config.addDefault("chests.stronghold_crossing.use", true);
		config.addDefault("chests.stronghold_crossing.types", Arrays.asList("default_stronghold_crossing 1"));
		config.addDefault("chests.stronghold_corridor.use", true);
		config.addDefault("chests.stronghold_corridor.types", Arrays.asList("default_stronghold_corridor 1"));
		config.addDefault("chests.underwater_ruin_big.use", true);
		config.addDefault("chests.underwater_ruin_big.types", Arrays.asList("default_underwater_ruin_big 1"));
		config.addDefault("chests.underwater_ruin_small.use", true);
		config.addDefault("chests.underwater_ruin_small.types", Arrays.asList("default_underwater_ruin_small 1"));
		config.addDefault("chests.pillager_outpost.use", true);
		config.addDefault("chests.pillager_outpost.types", Arrays.asList("default_pillager_outpost 1"));
		config.addDefault("chests.bastion_bridge.use", true);
		config.addDefault("chests.bastion_bridge.types", Arrays.asList("default_bastion_bridge 1"));
		config.addDefault("chests.bastion_hoglin_stable.use", true);
		config.addDefault("chests.bastion_hoglin_stable.types", Arrays.asList("default_bastion_hoglin_stable 1"));
		config.addDefault("chests.bastion_other.use", true);
		config.addDefault("chests.bastion_other.type", Arrays.asList("default_bastion_other 1"));
		config.addDefault("chests.bastion_treasure.use", true);
		config.addDefault("chests.bastion_treasure.types", Arrays.asList("default_bastion_treasure 1"));
		config.addDefault("chests.ruined_portal.use", true);
		config.addDefault("chests.ruined_portal.types", Arrays.asList("default_ruined_portal 1"));
		config.addDefault("chests.other.use", true);
		config.addDefault("chests.other.types", Arrays.asList("default_chest_treasure 1"));

		if (Configurations.isInitializing()) Chatable.get().sendInfo("Loots configuration initialized!");
	}

	@Override
	public void migrateVersion() {}

}
