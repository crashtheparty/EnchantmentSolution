package org.ctp.enchantmentsolution.crashapi.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.crashapi.CrashAPIPlugin;
import org.ctp.enchantmentsolution.crashapi.config.yaml.YamlConfigBackup;
import org.ctp.enchantmentsolution.crashapi.db.tables.Table;

public abstract class SQLite extends Database {

	private String dbname;

	private ArrayList<Table> tables = new ArrayList<Table>();

	public SQLite(CrashAPIPlugin instance, String dbname) {
		super(instance);
		this.dbname = dbname;
	}
	
	public void addTable(Table t) {
		tables.add(t);
	}

	protected <T> Table getTable(Class<T> cls) {
		for(Table table: tables)
			if (table.getClass().equals(cls)) return table;
		return null;
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

	@Override
	public String getDBName() {
		return dbname;
	}

	public void updateConfig(YamlConfigBackup config) {
		
	}
}