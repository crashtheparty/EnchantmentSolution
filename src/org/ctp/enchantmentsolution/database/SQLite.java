package org.ctp.enchantmentsolution.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.database.tables.BackupTable;
import org.ctp.enchantmentsolution.database.tables.Table;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class SQLite extends Database {

	private String dbname;

	private ArrayList<Table> tables = new ArrayList<Table>();

	public SQLite(EnchantmentSolution instance) {
		super(instance);
		dbname = "backups"; // Set the table name here e.g player_kills

		tables.add(new BackupTable(this));
	}

	private <T> Table getTable(Class<T> cls) {
		for(Table table: tables)
			if (table.getClass().equals(cls)) return table;
		return null;
	}

	public boolean isConfigDifferent(YamlConfigBackup config) {
		Table table = getTable(BackupTable.class);
		if (table instanceof BackupTable) {
			BackupTable bTable = (BackupTable) table;
			return bTable.isConfigDifferent(config, bTable.getBackupNum(config) - 1, true);
		}
		return false;
	}

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

	// SQL creation stuff, You can leave the blow stuff untouched.
	@Override
	public Connection getSQLConnection() {
		File dataFolder = new File(plugin.getDataFolder(), dbname + ".db");
		if (!dataFolder.exists()) try {
			dataFolder.createNewFile();
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "File write error: " + dbname + ".db");
		}
		try {
			if (connection != null && !connection.isClosed()) return connection;
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder);
			return connection;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "SQLite exception on initialize", ex);
		} catch (ClassNotFoundException ex) {
			plugin.getLogger().log(Level.SEVERE, "You need the SQLite JBDC library. Google it. Put it in /lib folder.");
		}
		return null;
	}

	@Override
	public void load() {
		connection = getSQLConnection();
		for(Table t: tables)
			t.createTable(connection);
		initialize();
	}

	public EnchantmentSolution getPlugin() {
		return plugin;
	}

	@Override
	protected String getTable() {
		return dbname;
	}
}