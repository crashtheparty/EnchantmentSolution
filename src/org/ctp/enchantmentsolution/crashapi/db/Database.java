package org.ctp.enchantmentsolution.crashapi.db;

import java.sql.*;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.crashapi.CrashAPIPlugin;

abstract class Database {
	CrashAPIPlugin plugin;
	Connection connection;

	Database(CrashAPIPlugin instance) {
		plugin = instance;
	}
	
	public CrashAPIPlugin getInstance() {
		return plugin;
	}

	public abstract Connection getSQLConnection();

	public abstract void load();

	void initialize() {
		connection = getSQLConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + getDBName());
			ResultSet rs = ps.executeQuery();
			ps.close();
			rs.close();

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		}
	}

	protected abstract String getDBName();
}