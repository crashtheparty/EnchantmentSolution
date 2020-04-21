package org.ctp.enchantmentsolution.database.tables;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.database.Errors;
import org.ctp.enchantmentsolution.database.SQLite;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class BackupTable extends Table {

	public BackupTable(SQLite db) {
		super(db, Arrays.asList("info"));
		addColumn("info", "varchar", "\"\"");
		addColumn("file_name", "varchar", "\"\"");
		addColumn("backup_num", "int", "0");
		addColumn("config_string", "varchar", "\"\"");
		addColumn("created_at", "varchar", "\"\"");
	}

	public boolean isConfigDifferent(YamlConfigBackup config, int backupNum, boolean includeConfigInv) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean matches = false;

		try {
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + getName() + " WHERE file_name = '" + config.getFileName() + "' AND backup_num = " + backupNum + " ORDER BY created_at asc;");

			rs = ps.executeQuery();
			while (rs.next())
				if(rs.getString("config_string").equals(encode(config))) matches = true;
		} catch (SQLException ex) {
			ChatUtils.sendSevere("ISSUE in \"SELECT * FROM " + getName() + " WHERE file_name = '" + config.getFileName() + "' AND backup_num = " + backupNum + " ORDER BY created_at asc;\"");
			getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
		} finally {
			try {
				if (ps != null) ps.close();
				if (rs != null) rs.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
			}
		}
		return !matches;
	}
	
	private String encode(YamlConfigBackup config) {
		return new String(Base64.getEncoder().encode(config.prepareConfigString(false).getBytes()));
	}
	
	private String decode(String configString) {
		return new String(Base64.getDecoder().decode(configString));
	}

	public int getBackupNum(YamlConfigBackup config) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int backupNum = 0;

		try {
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + getName() + " WHERE file_name = '" + config.getFileName() + "' ORDER BY backup_num desc LIMIT 0,1;");

			rs = ps.executeQuery();
			while (rs.next())
				backupNum = rs.getInt("backup_num");
		} catch (SQLException ex) {
			ChatUtils.sendSevere("ISSUE in \"SELECT * FROM " + getName() + " WHERE file_name = '" + config.getFileName() + "' ORDER BY backup_num desc LIMIT 0,1;\"");
			getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
		} finally {
			try {
				if (ps != null) ps.close();
				if (rs != null) rs.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
			}
		}
		return backupNum + 1;
	}

	public void setBackup(YamlConfigBackup config) {
		Connection conn = null;
		PreparedStatement ps = null;
		int backupNum = getBackupNum(config);
		boolean isDifferent = isConfigDifferent(config, backupNum - 1, false);
		if (isDifferent) try {
			LocalDateTime date = LocalDateTime.now();
			String dateString = date.toString();
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("INSERT INTO " + getName() + " (info, file_name, backup_num, config_string, created_at) " + "VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, backupNum + " " + config.getFileName());
			ps.setString(2, config.getFileName());
			ps.setInt(3, backupNum);
			ps.setString(4, encode(config));
			ps.setString(5, dateString);
			ps.execute();
		} catch (SQLException ex) {
			ChatUtils.sendSevere("ISSUE on inserting backup into " + config.getFileName());
			getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
		} finally {
			try {
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return;
	}

	public String getBackup(YamlConfigBackup config, int backup) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String file = config.getFileName();

		String backupString = "";

		try {
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + getName() + " WHERE backup_num = ? AND file_name = ?");
			ps.setInt(1, backup);
			ps.setString(2, file);
			rs = ps.executeQuery();
			while (rs.next()) {
				backupString = rs.getString("config_string");
				break;
			}
		} catch (SQLException ex) {
			ChatUtils.sendSevere("ISSUE in \"SELECT * FROM " + getName() + " WHERE backup_num = ? AND file_name = ?\"");
			getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(), ex);
		} finally {
			try {
				if (ps != null) ps.close();
				if (rs != null) rs.close();
				if (conn != null) conn.close();
			} catch (SQLException ex) {
				getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionClose(), ex);
			}
		}
		
		return decode(backupString);
	}
}
