package org.ctp.enchantmentsolution.utils;

import java.util.HashMap;
import java.util.Map;

import org.ctp.crashapi.config.Configuration;
import org.ctp.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class DBUtils {

	public static void updateConfig(Configuration configuration) {
		EnchantmentSolution.getPlugin().getDb().updateConfig(configuration.getConfig());
	}

	public static String getBackup(Configuration configuration, int backup) {
		return EnchantmentSolution.getPlugin().getDb().getBackup(configuration.getConfig(), backup);
	}

	public static Map<? extends YamlConfigBackup, ? extends Boolean> getDifferent(Configuration config) {
		HashMap<YamlConfigBackup, Boolean> map = new HashMap<YamlConfigBackup, Boolean>();

		map.put(config.getConfig(), EnchantmentSolution.getPlugin().getDb().isConfigDifferent(config.getConfig()));

		return map;
	}

}
