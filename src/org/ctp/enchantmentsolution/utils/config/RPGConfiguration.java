package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.List;
import java.util.Locale;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfig;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.crashapi.db.BackupDB;
import org.ctp.crashapi.utils.CrashConfigUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.api.ApiEnchantment;
import org.ctp.enchantmentsolution.api.ApiEnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.Configurations;

public class RPGConfiguration extends Configuration {

	public RPGConfiguration(File dataFolder, BackupDB db, String[] header) {
		super(EnchantmentSolution.getPlugin(), new File(dataFolder + "/rpg.yml"), db, header);

		migrateVersion();
		if (getConfig() != null) getConfig().writeDefaults();
	}

	@Override
	public void setDefaults() {
		if (Configurations.isInitializing()) Chatable.get().sendInfo("Initializing RPG configuration...");

		YamlConfigBackup config = getConfig();

		File file = CrashConfigUtils.getTempFile(getClass(), "/resources/rpg_defaults.yml");

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

		config.writeDefaults();

		if (Configurations.isInitializing()) Chatable.get().sendInfo("RPG configuration initialized!");

		file.delete();
	}

	@Override
	public void migrateVersion() {}

	public void updateExternal(JavaPlugin plugin) {
		YamlConfigBackup config = getConfig();
		@SuppressWarnings("unchecked")
		List<String> levels = (List<String>) config.getDefaults("free_enchantments");
		for(CustomEnchantment enchant: RegisterEnchantments.getEnchantments())
			if (enchant.getRelativeEnchantment() instanceof ApiEnchantmentWrapper) if (plugin.equals(((ApiEnchantmentWrapper) enchant.getRelativeEnchantment()).getPlugin())) {
				String namespace = "enchantments." + plugin.getName().toLowerCase(Locale.ROOT) + "." + enchant.getName().toLowerCase(Locale.ROOT);
				if (enchant instanceof ApiEnchantment) {
					ApiEnchantment api = (ApiEnchantment) enchant;
					config.addDefault(namespace + ".points_level_one", api.getPointsLevelOne());
					config.addDefault(namespace + ".points_increase", api.getPointsIncrease());
					config.addDefault(namespace + ".experience", api.getExperience());
					if (api.isFreeEnchantment()) levels.add(new EnchantmentLevel(api, api.getFreeLevel()).toString());
				}
			}
		config.addDefault("free_enchantments", levels);
		config.writeDefaults();
	}

	@Override
	public void repairConfig() {}
}
