package org.ctp.enchantmentsolution.crashapi.db;

import java.util.HashMap;
import java.util.Map;

import org.ctp.enchantmentsolution.crashapi.config.yaml.YamlConfigBackup;

public class DBUtils {

	public static void updateConfig(BackupDB db, YamlConfigBackup config) {
		db.updateConfig(config);
	}

	public static String getBackup(BackupDB db, YamlConfigBackup config, int backupNum) {
		return db.getBackup(config, backupNum);
	}

	public static Map<? extends YamlConfigBackup, ? extends Boolean> getDifferent(BackupDB db, YamlConfigBackup config) {
		HashMap<YamlConfigBackup, Boolean> map = new HashMap<YamlConfigBackup, Boolean>();

		map.put(config, db.isConfigDifferent(config));

		return map;
	}

}
