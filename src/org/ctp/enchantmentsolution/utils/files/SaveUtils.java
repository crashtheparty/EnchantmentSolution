package org.ctp.enchantmentsolution.utils.files;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.ctp.crashapi.config.DataFile;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.resources.advancements.CrashAdvancementProgress;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.helper.Seed;
import org.ctp.enchantmentsolution.events.blocks.DamageState;
import org.ctp.enchantmentsolution.interfaces.WalkerInterface;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.abilityhelpers.LassoMob;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerBlock;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.items.LassoUtils;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class SaveUtils {

	public static void getData() {
		Configurations c = Configurations.getConfigurations();
		if (c.getDataFile() == null) return;
		DataFile file = c.getDataFile();
		YamlConfig config = file.getConfig();
		if (config.containsElements("advancement_progress")) {
			int i = 0;
			while (config.containsElements("advancement_progress." + i)) {
				try {
					CrashAdvancementProgress progress = EnchantmentSolution.getAdvancementProgress(Bukkit.getOfflinePlayer(UUID.fromString(config.getString("advancement_progress." + i + ".player"))), ESAdvancement.valueOf(config.getString("advancement_progress." + i + ".advancement")), config.getString("advancement_progress." + i + ".criteria"));
					progress.setCurrentAmount(config.getInt("advancement_progress." + i + ".current_amount"));
					config.removeKey("advancement_progress." + i);
				} catch (Exception ex) {
					ex.printStackTrace();
					config.removeKey("advancement_progress." + i);
				}
				i++;
			}
			config.removeKeys("advancement_progress");
		}
		if (config.containsElements("blocks")) {
			int i = 0;
			List<WalkerBlock> blocks = new ArrayList<WalkerBlock>();
			while (config.getString("blocks." + i) != null) {
				String stringBlock = config.getString("blocks." + i);
				String[] arrayBlock = stringBlock.split(" ");
				try {
					Block block = new Location(Bukkit.getWorld(arrayBlock[1]), Integer.parseInt(arrayBlock[2]), Integer.parseInt(arrayBlock[3]), Integer.parseInt(arrayBlock[4])).getBlock();
					// tests if the world is valid; if not, it will cause an exception and skip
					block.getWorld().getName();
					WalkerInterface inter = WalkerInterface.getFromMetadata(arrayBlock[5]);
					if (inter == null) try {
						inter = WalkerInterface.getFromMaterial(Material.valueOf(arrayBlock[5]));
					} catch (Exception ex) {
						continue;
					}

					WalkerBlock walkerBlock = new WalkerBlock(inter, block, Integer.parseInt(arrayBlock[6]), DamageState.valueOf(arrayBlock[7]));
					blocks.add(walkerBlock);
				} catch (Exception ex) {
					Chatable.get().sendInfo("Block at position " + i + " was invalid, skipping.");
				}
				i++;
			}
			WalkerUtils.addBlocks(blocks);
			config.removeKeys("blocks");
		}
		if (config.containsElements("gaia")) {
			int i = 0;
			while (config.getString("gaia." + i) != null) {
				String stringBlock = config.getString("gaia." + i);
				String[] arrayBlock = stringBlock.split(" ");
				try {
					Location loc = new Location(Bukkit.getWorld(arrayBlock[0]), Integer.parseInt(arrayBlock[1]), Integer.parseInt(arrayBlock[2]), Integer.parseInt(arrayBlock[3]));
					// tests if the world is valid; if not, it will cause an exception and skip
					loc.getWorld().getName();
					EnchantmentSolution.gaiaAddLocation(loc);
				} catch (Exception ex) {
					Chatable.get().sendInfo("Block at position " + i + " was invalid, skipping.");
				}
				i++;
			}
			config.removeKeys("gaia");
		}
		if (config.containsElements("lasso")) {
			int i = 0;
			List<String> keys = config.getLevelEntryKeys("lasso");
			for(String key: keys) {
				String location = "lasso." + key + "." + i;
				while (config.getString(location + ".enchantment") != null) {
					CustomEnchantment enchantment = RegisterEnchantments.getByName(config.getString(location + ".enchantment"));
					if (enchantment != null) LassoUtils.createFromConfig(file, i, enchantment.getRelativeEnchantment());
					i++;
				}
			}
			config.removeKeys("lasso");
		}
		// old format, put in new format
		if (config.containsElements("animals")) {
			int i = 0;
			while (config.getString("animals." + i + ".entity_type") != null) {
				LassoUtils.createFromConfig(file, i, RegisterEnchantments.IRENES_LASSO);
				i++;
			}
			config.removeKeys("animals");
		}

		if (config.containsElements("es_player")) {
			int i = 0;
			while (config.getString("es_player." + i + ".uuid") != null) {
				OfflinePlayer offline = Bukkit.getOfflinePlayer(UUID.fromString(config.getString("es_player." + i + ".uuid")));
				if (offline != null) {
					ESPlayer player = EnchantmentSolution.getESPlayer(offline);
					player.setCanSeeEnchantments(config.getBoolean("es_player." + i + ".can_see_enchantments"));
					player.setWillSeeEnchantments(config.getBoolean("es_player." + i + ".will_see_enchantments"));
					player.setCurrentEchoShards(config.getInt("es_player." + i + ".current_echo_shards"));
					player.setNextEchoShards(config.getInt("es_player." + i + ".next_echo_shards"));
					player.setLevelSeed(config.getLong("es_player." + i + ".level_seed"));
					int j = 0;
					while (config.getString("es_player." + i + ".seeds." + j + ".seed") != null) {
						ItemData data = ItemData.fromString(config.getString("es_player." + i + ".seeds." + j + ".itemdata"));
						if (data != null) player.setEnchantmentSeed(data, config.getInt("es_player." + i + ".seeds." + j + ".seed"));
						j++;
					}
				}
				i++;
			}
		}
		config.removeKeys("es_player");

		// legacy - remove the old values
		config.removeKeys("enchanting_table");
		if (config.containsElements("rpg")) {
			int i = 0;
			while (config.getString("rpg." + i + ".player") != null) {
				String uuid = config.getString("rpg." + i + ".player");
				int level = config.getInt("rpg." + i + ".level");
				String experience = config.getString("rpg." + i + ".experience");
				RPGPlayer player = RPGUtils.addRPGPlayer(uuid, level, experience);
				List<String> data = config.getStringList("rpg." + i + ".enchants");
				if (data != null) for(String s: config.getStringList("rpg." + i + ".enchants"))
					player.giveEnchantment(s, config);
				i++;
			}
		}

		file.saveOnLoad();
	}

	public static void setData() {
		Configurations c = Configurations.getConfigurations();
		if (c.getDataFile() == null) return;
		DataFile file = c.getDataFile();
		YamlConfig config = file.getConfig();
		int i = 0;
		for(CrashAdvancementProgress progress: EnchantmentSolution.getAdvancementProgress()) {
			config.set("advancement_progress." + i + ".advancement", progress.getAdvancement().name());
			config.set("advancement_progress." + i + ".player", progress.getPlayer().getUniqueId());
			config.set("advancement_progress." + i + ".criteria", progress.getCriteria());
			config.set("advancement_progress." + i + ".current_amount", progress.getCurrentAmount());
			i++;
		}
		i = 0;
		List<WalkerBlock> blocks = WalkerUtils.getBlocks();
		if (blocks != null) for(WalkerBlock block: blocks) {
			try {
				Block loc = block.getBlock();
				CustomEnchantment enchantment = RegisterEnchantments.getCustomEnchantment(block.getEnchantment());
				config.set("blocks." + i, enchantment.getName() + " " + loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + block.getMeta() + " " + block.getTick() + " " + block.getDamage().name());
			} catch (Exception ex) {

			}
			i++;
		}
		i = 0;
		List<Location> locs = EnchantmentSolution.gaiaGetLocations();
		if (locs != null) for(Location loc: locs) {
			try {
				config.set("gaia." + i, loc.getWorld().getName() + " " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
			} catch (Exception ex) {

			}
			i++;
		}
		i = 0;
		for(EnchantmentWrapper enchantment: EnchantmentSolution.getLassoEnchantments())
			try {
				for(LassoMob animal: EnchantmentSolution.getLassoMobs(enchantment)) {
					animal.setConfig(file, animal.getLocation(i), i);
					i++;
				}
			} catch (NoClassDefFoundError ex) {
				ex.printStackTrace();
			}

		i = 0;
		try {
			for(ESPlayer player: EnchantmentSolution.getAllESPlayers(false)) {
				config.set("es_player." + i + ".uuid", player.getPlayer().getUniqueId());
				config.set("es_player." + i + ".can_see_enchantments", player.canSeeEnchantments());
				config.set("es_player." + i + ".will_see_enchantments", player.willSeeEnchantments());
				config.set("es_player." + i + ".current_echo_shards", player.getCurrentEchoShards());
				config.set("es_player." + i + ".next_echo_shards", player.getNextEchoShards());
				config.set("es_player." + i + ".level_seed", player.getLevelSeed());
				Iterator<Seed> iter = player.getEnchantmentSeeds().iterator();
				int j = 0;
				while (iter.hasNext()) {
					Seed seed = iter.next();
					config.set("es_player." + i + ".seeds." + j + ".itemdata", seed.getData().toString());
					config.set("es_player." + i + ".seeds." + j + ".seed", seed.getSeed());
					j++;
				}
				i++;
			}
		} catch (NoClassDefFoundError ex) {
			ex.printStackTrace();
		}
		i = 0;
		List<RPGPlayer> players = RPGUtils.getPlayers();
		if (players != null) for(RPGPlayer player: players) {
			config.set("rpg." + i + ".player", player.getPlayer().getUniqueId().toString());
			config.set("rpg." + i + ".level", player.getLevel());
			config.set("rpg." + i + ".experience", player.getExperience().toString());
			List<String> enchants = new ArrayList<String>();
			Iterator<Entry<EnchantmentWrapper, Integer>> iterator = player.getEnchantmentList().entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<EnchantmentWrapper, Integer> entry = iterator.next();
				EnchantmentLevel level = new EnchantmentLevel(RegisterEnchantments.getCustomEnchantment(entry.getKey()), entry.getValue());
				enchants.add(level.toString());
			}
			config.set("rpg." + i + ".enchants", enchants);
			i++;
		}

		file.save();
	}
}
