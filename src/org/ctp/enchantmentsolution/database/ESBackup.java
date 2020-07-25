package org.ctp.enchantmentsolution.database;

import org.ctp.enchantmentsolution.crashapi.CrashAPIPlugin;
import org.ctp.enchantmentsolution.crashapi.db.BackupDB;

public class ESBackup extends BackupDB {

	public ESBackup(CrashAPIPlugin instance) {
		super(instance, "backups");
	}

}
