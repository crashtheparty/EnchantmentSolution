package org.ctp.enchantmentsolution.utils.save;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.listeners.abilities.MagmaWalkerListener;
import org.ctp.enchantmentsolution.utils.config.YamlConfig;

public class SaveUtils {

	public static void getData() {
		if(ConfigFiles.getMagmaWalkerConfig() == null) {
			return;
		}
		YamlConfig config = ConfigFiles.getMagmaWalkerConfig();
		if (config.containsElements("blocks")) {
			int i = 0;
			while (config.getString("blocks." + i) != null) {
				String stringBlock = config.getString("blocks." + i);
				String[] arrayBlock = stringBlock.split(" ");
				try {
					Block block = (new Location(Bukkit.getWorld(arrayBlock[0]),
							Integer.parseInt(arrayBlock[1]),
							Integer.parseInt(arrayBlock[2]),
							Integer.parseInt(arrayBlock[3]))).getBlock();
					block.setMetadata("MagmaWalker", new FixedMetadataValue(EnchantmentSolution.PLUGIN, Integer.parseInt(arrayBlock[4])));
					MagmaWalkerListener.BLOCKS.add(block);
				} catch (Exception ex) {
					Bukkit.getLogger().info(
							"Block at position " + i
									+ " was invalid, skipping.");
				}
				i++;
			}
			config.set("blocks", null);
			config.saveConfig();
		}
	}

	public static void setMagmaWalkerData() {
		if(ConfigFiles.getMagmaWalkerConfig() == null) {
			return;
		}
		int i = 0;
		YamlConfig config = ConfigFiles.getMagmaWalkerConfig();
		for (Block block : MagmaWalkerListener.BLOCKS) {
			for(MetadataValue value : block.getMetadata("MagmaWalker")){
				config.set("blocks." + i,
						(block.getWorld().getName() + " " + block.getX() + " "
								+ block.getY() + " " + block.getZ() + " " + value.asInt()));
			}
			i++;
		}
		config.saveConfig();
	}
}
