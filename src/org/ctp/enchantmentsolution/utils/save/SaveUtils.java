package org.ctp.enchantmentsolution.utils.save;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.listeners.abilities.MagmaWalkerListener;

public class SaveUtils {

	public static void getData() {
		YamlConfiguration config = ConfigFiles.MAGMA_WALKER_CONFIG;
		if (config.getConfigurationSection("blocks") != null) {
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
			try {
				config.save(ConfigFiles.MAGMA_WALKER_FILE);
			} catch (IOException e) {
				// Handle any IO exception here
				e.printStackTrace();
			}
		}
	}

	public static void setMagmaWalkerData() {
		if(ConfigFiles.MAIN_FILE == null) {
			return;
		}
		int i = 0;
		YamlConfiguration config = ConfigFiles.MAGMA_WALKER_CONFIG;
		for (Block block : MagmaWalkerListener.BLOCKS) {
			for(MetadataValue value : block.getMetadata("MagmaWalker")){
				config.set("blocks." + i,
						(block.getWorld().getName() + " " + block.getX() + " "
								+ block.getY() + " " + block.getZ() + " " + value.asInt()));
			}
			i++;
		}
		try {
			config.save(ConfigFiles.MAGMA_WALKER_FILE);
		} catch (IOException e) {
			// Handle any IO exception here
			e.printStackTrace();
		}
	}
}
