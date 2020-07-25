package org.ctp.enchantmentsolution.crashapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.crashapi.config.CrashConfigurations;
import org.ctp.enchantmentsolution.crashapi.config.yaml.YamlConfig;
import org.ctp.enchantmentsolution.crashapi.item.ItemSerialization;
import org.ctp.enchantmentsolution.crashapi.utils.ChatUtils;

public abstract class CrashAPIPlugin extends JavaPlugin {

	private boolean mmoItems = false;

	public abstract String getStarter();

	public abstract ChatUtils getChat();

	public abstract ItemSerialization getItemSerial();

	public abstract CrashConfigurations getConfigurations();

	public void sendInfo(String message) {
		getChat().sendInfo(message);
	}

	public void sendWarning(String message) {
		getChat().sendWarning(message);
	}

	public void sendSevere(String message) {
		getChat().sendSevere(message);
	}

	public abstract YamlConfig getLanguageFile();

	public void addCompatibility() {
		if (Bukkit.getPluginManager().isPluginEnabled("MMOItems")) {
			mmoItems = true;
			getChat().sendInfo("MMOItems compatibility enabled!");
		}
	}

	public boolean getMMOItems() {
		return mmoItems;
	}
	
	public abstract boolean isInitializing();
}
