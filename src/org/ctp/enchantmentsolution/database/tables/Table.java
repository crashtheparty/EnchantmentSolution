package org.ctp.enchantmentsolution.database.tables;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import org.ctp.enchantmentsolution.database.Errors;
import org.ctp.enchantmentsolution.database.SQLite;
import org.ctp.enchantmentsolution.database.columns.Column;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class Table {
	
	private String name;
	private List<String> primary;
	private ArrayList<Column> columns = new ArrayList<Column>();
	private HashMap<String, String> conversions = new HashMap<String, String>();
	private SQLite db;

	public Table(SQLite db, String name){
		this.db = db;
		this.name = name;
		addConversions();
	}
	
	public Table(SQLite db, String name, List<String> primary){
		this.db = db;
		this.name = name;
		addConversions();
		this.primary = primary;
	}
	
	private void addConversions() {
		conversions.put("int", "int(11) NOT NULL");
		conversions.put("varchar", "varchar(255) NOT NULL");
		conversions.put("real", "real NOT NULL");
		conversions.put("varchar null", "varchar(255) NULL");
	}
	
	public HashMap<String, String> getConversions(){
		return conversions;
	}

	public ArrayList<Column> getColumns() {
		return columns;
	}

	public void addColumn(String name, String type, String defaultValue) {
		columns.add(new Column(name, type, defaultValue));
	}

	public List<String> getPrimaryKeys() {
		return primary;
	}

	public String getName() {
		return name;
	}
	
	private boolean hasPrimaryKeys() {
		for(String s : primary) {
			boolean hasKey = false;
			for(Column c : getColumns()) {
				if(s.equals(c.getName())) {
					hasKey = true;
				}
			}
			if(!hasKey) {
				return false;
			}
		}
		return true;
	}
	
	public <E> boolean hasRecord(String tableName, String key, E value) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		boolean found = false;
		try {
			String query = "SELECT (count(*) > 0) as found FROM " + tableName + " WHERE " + key + " LIKE ?";
			conn = db.getSQLConnection();
			ps = conn.prepareStatement(query);
			ps.setObject(1, value);
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
				db.getPlugin().getLogger().log(Level.SEVERE,
						Errors.sqlConnectionClose(), ex);
			}
		}
		return found;
	}
	
	public Integer getInteger(String player, String fieldName){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Integer integer = 0;
		try {
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + getName()
					+ " WHERE player = '" + player + "';");

			rs = ps.executeQuery();
			while (rs.next()) {
				integer = rs.getInt(fieldName);
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
		return integer;
	}
	
	public void setInteger(String player, String fieldName, Integer integer){
		Connection conn = null;
		PreparedStatement ps = null;
		boolean hasRecord = hasRecord(this.getName(), "player", player);
		try {
			conn = getDb().getSQLConnection();
			if(hasRecord){
				ps = conn.prepareStatement("UPDATE " + this.getName() + " SET " + fieldName + " = ? WHERE player = ?");
	
				ps.setInt(1, integer); 
	
				ps.setString(2, player);
			}else{
				ps = conn.prepareStatement("INSERT INTO " + this.getName() + " (player, " + integer + ") VALUES (?, ?)");
				
				ps.setInt(2, integer); 
	
				ps.setString(1, player);
			}
			ps.executeUpdate();
			return;
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
		return;
	}
	
	public String getString(String player, String fieldName){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String string = "";
		try {
			conn = getDb().getSQLConnection();
			ps = conn.prepareStatement("SELECT * FROM " + getName()
					+ " WHERE player = '" + player + "';");

			rs = ps.executeQuery();
			while (rs.next()) {
				string = rs.getString(fieldName);
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
		return string;
	}
	
	public void setString(String player, String fieldName, String string){
		Connection conn = null;
		PreparedStatement ps = null;
		boolean hasRecord = hasRecord(this.getName(), "player", player);
		try {
			conn = getDb().getSQLConnection();
			if(hasRecord){
				ps = conn.prepareStatement("UPDATE " + this.getName() + " SET " + fieldName + " = ? WHERE player = ?");
	
				ps.setString(1, string); 
	
				ps.setString(2, player);
			}else{
				ps = conn.prepareStatement("INSERT INTO " + this.getName() + " (player, " + string + ") VALUES (?, ?)");
				
				ps.setString(2, string); 
	
				ps.setString(1, player);
			}
			ps.executeUpdate();
			return;
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
		return;
	}
	
	public boolean tableExists(Connection connection) {
		try {
			DatabaseMetaData md = connection.getMetaData();
			ResultSet rs = md.getTables(null, null, name, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException ex) {

		}
		return false;
	}
	
	public void createTable(Connection connection){
		try{
			PreparedStatement s = connection.prepareStatement("PRAGMA table_info(" + name + ")");
			ResultSet rs = s.executeQuery();
			ArrayList<String> columnsInTable = new ArrayList<String>();
			boolean has_table = tableExists(connection);
			while(rs.next()){
				for(Column column : columns){
					if(column.getName().equalsIgnoreCase(rs.getString(2))){
						columnsInTable.add(rs.getString(2));
					}
				}
			}
			if(has_table) {
				for (Column column : columns) {
					if(!columnsInTable.contains(column.getName())){
						String statement = "ALTER TABLE " + name + " ADD COLUMN `" + column.getName() + "` " + conversions.get(column.getType()) + " DEFAULT " + column.getDefaultValue();
						if(column.getType().equals("autoint")) {
							ChatUtils.sendToConsole(Level.INFO, "Can't add auto increment value to existing table: skipping.");
							continue;
						}
						ChatUtils.sendToConsole(Level.INFO, statement);
						Statement st = connection.createStatement();
						st.executeUpdate(statement);
						st.close();
					}
				}
			}else {
				if(hasPrimaryKeys()) {
					String statement = 
							"CREATE TABLE IF NOT EXISTS " + name + " (";
					for (Column column : columns) {
						statement += "`" + column.getName() + "` " + conversions.get(column.getType()) + " DEFAULT " + column.getDefaultValue() + ",";
					}
					String primaryString = "";
					for(int i = 0; i < primary.size(); i++) {
						primaryString += "`" + primary.get(i) + "`";
						if(i != primary.size() - 1) {
							primaryString += ", ";
						}
					}
					if(primaryString.length() > 0) {
						statement += "PRIMARY KEY (" + primaryString + "))";
					}else {
						statement = statement.substring(0, statement.length() - 1) + ")";
					}
					ChatUtils.sendToConsole(Level.INFO, statement);
					try{
						Statement st = connection.createStatement();
						st.executeUpdate(statement);
						st.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}else {
					ChatUtils.sendToConsole(Level.WARNING, "Failed to add table " + name + ": primary keys undefined.");
				}
			}
		}catch(SQLException ex){
			if(ex.getMessage().equalsIgnoreCase("query does not return results")){
				if(hasPrimaryKeys()) {
					String statement = 
							"CREATE TABLE IF NOT EXISTS " + name + " (";
					for (Column column : columns) {
						statement += "`" + column.getName() + "` " + conversions.get(column.getType()) + " DEFAULT " + column.getDefaultValue() + ",";
					}
					String primaryString = "";
					for(int i = 0; i < primary.size(); i++) {
						primaryString += "`" + primary.get(i) + "`";
						if(i != primary.size() - 1) {
							primaryString += ", ";
						}
					}
					if(primaryString.length() > 0) {
						statement += "PRIMARY KEY (" + primaryString + "))";
					}else {
						statement = statement.substring(0, statement.length() - 1) + ")";
					}
					ChatUtils.sendToConsole(Level.INFO, statement);
					try{
						Statement st = connection.createStatement();
						st.executeUpdate(statement);
						st.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}else {
					ChatUtils.sendToConsole(Level.WARNING, "Failed to add table " + name + ": primary keys undefined.");
				}
			}else{
				ex.printStackTrace();
			}
		}
	}

	public SQLite getDb() {
		return db;
	}
}
