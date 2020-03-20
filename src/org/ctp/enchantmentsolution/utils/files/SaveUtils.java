package org.ctp.enchantmentsolution.utils.files;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.advancements.ESAdvancementProgress;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.generate.TableEnchantments;
import org.ctp.enchantmentsolution.events.blocks.DamageState;
import org.ctp.enchantmentsolution.nms.AnimalMobNMS;
import org.ctp.enchantmentsolution.nms.animalmob.AnimalMob;
import org.ctp.enchantmentsolution.utils.Configurations;
import org.ctp.enchantmentsolution.utils.abillityhelpers.WalkerBlock;
import org.ctp.enchantmentsolution.utils.abillityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;

public class SaveUtils {

	public static void getData() {
		if (Configurations.getDataFile() == null) return;
		YamlConfig config = Configurations.getDataFile().getConfig();
		if (config.containsElements("advancement_progress")) {
			int i = 0;
			while (config.containsElements("advancement_progress." + i)) {
				try {
					ESAdvancementProgress progress = EnchantmentSolution.getAdvancementProgress(Bukkit.getOfflinePlayer(UUID.fromString(config.getString("advancement_progress." + i + ".player"))), ESAdvancement.valueOf(config.getString("advancement_progress." + i + ".advancement")), config.getString("advancement_progress." + i + ".criteria"));
					progress.setCurrentAmount(config.getInt("advancement_progress." + i + ".current_amount"));
					config.removeKey("advancement_progress." + i);
				} catch (Exception ex) {
					ex.printStackTrace();
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
					Enchantment enchantment = RegisterEnchantments.getByName(arrayBlock[0]).getRelativeEnchantment();
					WalkerBlock walkerBlock = new WalkerBlock(enchantment, block, Material.valueOf(arrayBlock[5]), Integer.parseInt(arrayBlock[6]), DamageState.valueOf(arrayBlock[7]));
					blocks.add(walkerBlock);
				} catch (Exception ex) {
					Bukkit.getLogger().info("Block at position " + i + " was invalid, skipping.");
				}
				i++;
			}
			WalkerUtils.addBlocks(blocks);
			config.removeKeys("blocks");
		}
		if (config.containsElements("animals")) {
			int i = 0;
			while (config.getString("animals." + i + ".entity_type") != null) {
				AnimalMobNMS.getFromConfig(config, i);
				i++;
			}
			config.removeKeys("animals");
		}

		if (!ConfigString.RESET_ON_RELOAD.getBoolean() && config.containsElements("enchanting_table")) {
			int i = 0;
			while (config.getString("enchanting_table." + i + ".player") != null) {
				TableEnchantments.getFromConfig(config, i);
				i++;
			}
		}
		config.removeKeys("enchanting_table");
		Configurations.getDataFile().save();
	}

	public static void setData() {
		if (Configurations.getDataFile() == null) return;
		int i = 0;
		YamlConfig config = Configurations.getDataFile().getConfig();
		for(ESAdvancementProgress progress: EnchantmentSolution.getAdvancementProgress()) {
			config.set("advancement_progress." + i + ".advancement", progress.getAdvancement().name());
			config.set("advancement_progress." + i + ".player", progress.getPlayer().getUniqueId());
			config.set("advancement_progress." + i + ".criteria", progress.getCriteria());
			config.set("advancement_progress." + i + ".current_amount", progress.getCurrentAmount());
			i++;
		}
		i = 0;
		List<WalkerBlock> blocks = WalkerUtils.getBlocks();
		if (blocks != null) for(WalkerBlock block: blocks) {
			Block loc = block.getBlock();
			CustomEnchantment enchantment = RegisterEnchantments.getCustomEnchantment(block.getEnchantment());
			config.set("blocks." + i, enchantment.getName() + " " + loc.getWorld().getName() + " " + loc.getX() + " " + loc.getY() + " " + loc.getZ() + " " + block.getReplaceType().name() + " " + block.getTick() + " " + block.getDamage().name());
			i++;
		}
		i = 0;
		try {
			for(AnimalMob animal: EnchantmentSolution.getAnimals()) {
				animal.setConfig(config, i);
				i++;
			}
		} catch (NoClassDefFoundError ex) {
			ex.printStackTrace();
		}

		if (!ConfigString.RESET_ON_RELOAD.getBoolean()) {
			i = 0;
			try {
				for(TableEnchantments table: TableEnchantments.getAllTableEnchantments()) {
					table.setConfig(config, i);
					i++;
				}
			} catch (NoClassDefFoundError ex) {
				ex.printStackTrace();
			}
		}

		Configurations.getDataFile().save();
	}
}
