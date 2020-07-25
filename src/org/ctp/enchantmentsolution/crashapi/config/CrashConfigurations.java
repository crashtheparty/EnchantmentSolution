package org.ctp.enchantmentsolution.crashapi.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public interface CrashConfigurations {

	public void onEnable();

	public void save();
	
	public static ItemStack getItemStack(Configurable config, String location) {
		YamlConfiguration temp = YamlConfiguration.loadConfiguration(config.getFile());
		return temp.getItemStack(location);
	}
}
