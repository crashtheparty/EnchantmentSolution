package org.ctp.enchantmentsolution.database;

import org.ctp.crashapi.CrashAPIPlugin;
import org.ctp.crashapi.db.BackupDB;

public class ESBackup extends BackupDB {

	public ESBackup(CrashAPIPlugin instance) {
		super(instance, "backups");
	}

}
