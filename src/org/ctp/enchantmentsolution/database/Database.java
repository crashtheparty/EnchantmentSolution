package org.ctp.enchantmentsolution.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.EnchantmentSolution;

public abstract class Database {
	public EnchantmentSolution plugin;
	Connection connection;
	// The name of the table we created back in SQLite class.
	public String table = "enchantment_solution";
	public int tokens = 0;

	public Database(EnchantmentSolution instance) {
		plugin = instance;
	}

	public abstract Connection getSQLConnection();

	public abstract void load();

	public void initialize() {
		connection = getSQLConnection();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM "
					+ table);
			ResultSet rs = ps.executeQuery();
			close(ps, rs);

		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE,
					"Unable to retreive connection", ex);
		}
	}
	
	public boolean hasRecord(String key, String table){
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean found = false;
		try {
			String query = "SELECT (count(*) > 0) as found FROM " + table + " WHERE player LIKE ?";
			conn = getSQLConnection();
			ps = conn.prepareStatement(query);
			ps.setString(1, key);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				found = rs.getBoolean(1); // "found" column
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE,
						Errors.sqlConnectionClose(), ex);
			}
		}
		return found;
	}
	
	public Integer getInteger(String table, String key, String field){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer integer = 0;
		try {
			conn = getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + table
					+ " WHERE player = '" + key + "';");

			rs = ps.executeQuery();
			while (rs.next()) {
				if (rs.getString("player").equals(
						key)) { 
					integer = rs.getInt(field);
					break;
				}
			}
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(),
					ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE,
						Errors.sqlConnectionClose(), ex);
			}
		}
		return integer;
	}
	
	public void setInteger(String table, String key, String field, Integer integer){
		Connection conn = null;
		PreparedStatement ps = null;
		boolean hasRecord = hasRecord(key, table);
		try {
			conn = getSQLConnection();
			if(hasRecord){
				ps = conn.prepareStatement("UPDATE " + table + " SET " + field + " = ? WHERE player = ?");
	
				ps.setInt(1, integer); 
	
				ps.setString(2, key);
			}else{
				ps = conn.prepareStatement("INSERT INTO " + table + " (player, " + field + ") VALUES (?, ?)");
				
				ps.setInt(2, integer); 
	
				ps.setString(1, key);
			}
			ps.executeUpdate();
			return;
		} catch (SQLException ex) {
			plugin.getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(),
					ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				plugin.getLogger().log(Level.SEVERE,
						Errors.sqlConnectionClose(), ex);
			}
		}
		return;
	}
	
	public void close(PreparedStatement ps, ResultSet rs) {
		try {
			if (ps != null)
				ps.close();
			if (rs != null)
				rs.close();
		} catch (SQLException ex) {
			Error.close(plugin, ex);
		}
	}
}