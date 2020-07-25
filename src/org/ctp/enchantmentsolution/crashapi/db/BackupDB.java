package org.ctp.enchantmentsolution.crashapi.db;

import java.util.ArrayList;
import java.util.List;

import org.ctp.enchantmentsolution.crashapi.CrashAPIPlugin;
import org.ctp.enchantmentsolution.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.enchantmentsolution.crashapi.db.tables.BackupTable;
import org.ctp.enchantmentsolution.crashapi.db.tables.Table;

public abstract class BackupDB extends SQLite{

	public BackupDB(CrashAPIPlugin instance, String dbname) {
		super(instance, dbname);
		addTable(new BackupTable(this));
	}

	public boolean isConfigDifferent(YamlConfigBackup config) {
		Table table = getTable(BackupTable.class);
		if (table instanceof BackupTable) {
			BackupTable bTable = (BackupTable) table;
			return bTable.isConfigDifferent(config, bTable.getBackupNum(config) - 1, true);
		}
		return false;
	}

	@Override
	public void updateConfig(YamlConfigBackup config) {
		Table table = getTable(BackupTable.class);
		if (table instanceof BackupTable) {
			BackupTable bTable = (BackupTable) table;
			bTable.setBackup(config);
		}
	}

	public List<Integer> getBackups(YamlConfigBackup config) {
		Table table = getTable(BackupTable.class);
		if (table instanceof BackupTable) {
			BackupTable bTable = (BackupTable) table;
			int backup = bTable.getBackupNum(config);
			List<Integer> backups = new ArrayList<Integer>();
			for(int i = 1; i < backup; i++)
				backups.add(i);
			return backups;
		}
		return null;
	}

	public String getBackup(YamlConfigBackup config, int backup) {
		Table table = getTable(BackupTable.class);
		if (table instanceof BackupTable) {
			BackupTable bTable = (BackupTable) table;
			return bTable.getBackup(config, backup);
		}
		return "";
	}

}
