package org.ctp.enchantmentsolution.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.EnchantmentSolution;

abstract class Database {
	EnchantmentSolution plugin;
	Connection connection;
	// The name of the table we created back in SQLite class.
	private String table = "enchantment_solution";

	Database(EnchantmentSolution instance) {
		plugin = instance;
	}

	public abstract Connection getSQLConnection();

	public abstract void load();

	void initialize() {
		connection = getSQLConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM "
					+ table);
			ResultSet rs = ps.executeQuery();
			ps.close();
			rs.close();

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE,
					"Unable to retreive connection", ex);
		}
	}
}