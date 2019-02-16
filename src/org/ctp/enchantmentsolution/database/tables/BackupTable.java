package org.ctp.enchantmentsolution.database.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.database.Errors;
import org.ctp.enchantmentsolution.database.SQLite;
import org.ctp.enchantmentsolution.utils.config.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.config.YamlInfo;

public class BackupTable extends Table{
	
	public BackupTable(SQLite db) {
		super(db, "enchantment_solution", Arrays.asList("info"));
		addColumn("info", "varchar", "\"\"");
		addColumn("file_name", "varchar", "\"\"");
		addColumn("backup_num", "int", "0");
		addColumn("field", "varchar", "\"\"");
		addColumn("value", "varchar null", "\"\"");
		addColumn("type", "varchar", "\"\"");
		addColumn("created_at", "varchar", "\"\"");
	}
	
	public boolean isConfigDifferent(YamlConfigBackup config, int backupNum, boolean includeConfigInv){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		LinkedHashMap<String, Boolean> keySame = new LinkedHashMap<String, Boolean>();
		if(!includeConfigInv) {
			for(String key : config.getAllEntryKeys()) {
				keySame.put(key, false);
			}
		} else {
			for(String key : config.getConfigInventoryEntryKeys()) {
				keySame.put(key, false);
			}
		}
		
		try {
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + getName()
					+ " WHERE file_name = '" + config.getFileName() + "' AND backup_num = " + backupNum + " ORDER BY created_at asc;");

			rs = ps.executeQuery();
			while (rs.next()) {
				String field = rs.getString("field");
				if(includeConfigInv) {
					if(config.matchConfig(field, rs.getObject("value"))) {
						keySame.put(field, true);
					} else {
						keySame.put(field, false);
					}
				} else {
					if(keySame.containsKey(field)) {
						if(config.match(field, rs.getObject("value"))) {
							keySame.put(field, true);
						}
					} else {
						keySame.put(field, false);
					}
				}
			}
		} catch (SQLException ex) {
			getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(),
					ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				getDb().getPlugin().getLogger().log(Level.SEVERE,
						Errors.sqlConnectionClose(), ex);
			}
		}
		if(keySame.containsValue(false)) {
			return true;
		}
		return false;
	}
	
	public int getBackupNum(YamlConfigBackup config) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		int backupNum = 0;
		
		try {
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + getName()
					+ " WHERE file_name = '" + config.getFileName() + "' ORDER BY backup_num desc LIMIT 0,1;");

			rs = ps.executeQuery();
			while (rs.next()) {
				backupNum = rs.getInt("backup_num");
			}
		} catch (SQLException ex) {
			getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(),
					ex);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				getDb().getPlugin().getLogger().log(Level.SEVERE,
						Errors.sqlConnectionClose(), ex);
			}
		}
		return backupNum + 1;
	}
	
	public void setBackup(YamlConfigBackup config) {
		Connection conn = null;
		PreparedStatement ps = null;
		int backupNum = getBackupNum(config);
		boolean isDifferent = isConfigDifferent(config, backupNum - 1, false);
		if(isDifferent) {
			try {
				LocalDateTime date = LocalDateTime.now();
				String dateString = date.toString();
				conn = getDb().getSQLConnection();
				conn.setAutoCommit(false);
				ps = conn.prepareStatement("INSERT INTO " + this.getName() + " (info, file_name, backup_num, field, value, type, created_at) "
						+ "VALUES (?, ?, ?, ?, ?, ?, ?)");
				for(String key : config.getAllEntryKeys()) {
					ps.setString(1, backupNum + " " + config.getFileName() + " " + key);
					ps.setString(2, config.getFileName());
					ps.setInt(3, backupNum); 
					ps.setString(4, key);
					if(config.getBooleanValue(key) != null) {
						ps.setString(5, Boolean.toString(config.getBoolean(key)));
					} else {
						ps.setObject(5, config.get(key));
					}
					ps.setString(6, config.getType(key));
					ps.setString(7, dateString);
					ps.addBatch();
				}
				ps.executeBatch();
				conn.commit();
			} catch (SQLException ex) {
				getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(),
						ex);
			}
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				getDb().getPlugin().getLogger().log(Level.SEVERE,
						Errors.sqlConnectionClose(), ex);
			}
		}
		return;
	}
	
	public List<YamlInfo> getBackup(YamlConfigBackup config, int backup){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String file = config.getFileName();
		
		List<YamlInfo> infos = new ArrayList<YamlInfo>();
		
		try {
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + this.getName() + " WHERE backup_num = ? AND file_name = ?");
			ps.setInt(1, backup);
			ps.setString(2, file);
			rs = ps.executeQuery();
			while (rs.next()) {
				Object obj = null;
				switch(rs.getString("type")) {
				case "nested value":
					obj = null;
					break;
				case "enum":
				case "string":
					obj = rs.getString("value");
					break;
				case "integer":
					obj = rs.getInt("value");
					break;
				case "double":
					obj = rs.getDouble("value");
					break;
				case "boolean":
					switch(rs.getString("value")) {
					case "true":
						obj =  new Boolean(true);
						break;
					default:
						obj =  new Boolean(false);
						break;
					}
					break;
				case "enum_list":
				case "list":
					String[] values = config.replaceLast((rs.getString("value").replaceFirst("\\[", "")), "]", "").split(", ");
					obj = Arrays.asList(values);
					break;
				}
				YamlInfo info = new YamlInfo(rs.getString("field"), obj);
				infos.add(info);
			}
		} catch (SQLException ex) {
			getDb().getPlugin().getLogger().log(Level.SEVERE, Errors.sqlConnectionExecute(),
					ex);
		}
		try {
			if (ps != null)
				ps.close();
			if (conn != null)
				conn.close();
		} catch (SQLException ex) {
			getDb().getPlugin().getLogger().log(Level.SEVERE,
					Errors.sqlConnectionClose(), ex);
		}
		return infos;
	}
}
