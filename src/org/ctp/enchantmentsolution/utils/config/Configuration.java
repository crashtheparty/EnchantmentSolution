package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ctp.enchantmentsolution.utils.DBUtils;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;
import org.ctp.enchantmentsolution.utils.yaml.YamlInfo;

public abstract class Configuration implements Configurable{
	
	private File file;
	private YamlConfigBackup config;
	private boolean comments = true;
	
	public Configuration(File file) {
		this(file, true);
	}
	
	public Configuration(File file, boolean setDefault) {
		this.file = file;
		try {
			YamlConfiguration.loadConfiguration(file);
			
			String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };
			config = new YamlConfigBackup(file, header);
			config.getFromConfig();
			if(setDefault) {
				setDefaults();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public File getFile() {
		return file;
	}

	@Override
	public YamlConfigBackup getConfig() {
		return config;
	}

	public abstract void setDefaults();

	@Override
	public abstract void migrateVersion();

	@Override
	public void save() {
		config.setComments(comments);
		config.saveConfig();
		
		DBUtils.updateConfig(this);
	}

	@Override
	public void revert() {
		config.revert();
	}

	@Override
	public void revert(int backup) {
		config.revert();
		
		List<YamlInfo> info = DBUtils.getBackup(this, backup);
		
		for(YamlInfo i: info) {
			if (i.getValue() != null) {
				config.set(i.getPath(), i.getValue());
			}
		}

		save();
	}
	
	@Override
	public void reload() {
		revert();
		setDefaults();
		save();
	}

	@Override
	public void setComments(boolean comments) {
		this.comments = comments;
	}

	@Override
	public String getString(String s) {
		return config.getString(s);
	}

	@Override
	public int getInt(String s) {
		return config.getInt(s);
	}

	@Override
	public boolean getBoolean(String s) {
		return config.getBoolean(s);
	}

	@Override
	public double getDouble(String s) {
		return config.getDouble(s);
	}
	
	@Override
	public void updatePath(String s, Object value) {
		config.set(s, value);
	}

	public List<String> getStringList(String s) {
		return config.getStringList(s);
	}
}
