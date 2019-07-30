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
	
	private boolean tableExists(Connection connection) {
		ResultSet rs = null;
		boolean exists = false;
		try {
			DatabaseMetaData md = connection.getMetaData();
			rs = md.getTables(null, null, name, null);
			if (rs.next()) {
				if (rs != null)
					rs.close();
				exists = true;
			}
		} catch (SQLException ex) {
			
		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return exists;
	}
	
	public void createTable(Connection connection){
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			ps = connection.prepareStatement("PRAGMA table_info(" + name + ")");
			rs = ps.executeQuery();
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
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	public SQLite getDb() {
		return db;
	}
}
