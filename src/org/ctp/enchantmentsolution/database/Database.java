package org.ctp.enchantmentsolution.database;

import java.sql.*;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.EnchantmentSolution;

abstract class Database {
	EnchantmentSolution plugin;
	Connection connection;
	

	Database(EnchantmentSolution instance) {
		plugin = instance;
	}

	public abstract Connection getSQLConnection();

	public abstract void load();

	void initialize() {
		connection = getSQLConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + getTable());
			ResultSet rs = ps.executeQuery();
			ps.close();
			rs.close();

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, "Unable to retreive connection", ex);
		}
	}
	
	protected abstract String getTable();
}