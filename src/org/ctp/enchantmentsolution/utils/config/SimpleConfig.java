package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SimpleConfig {
	private int comments;
	private SimpleConfigManager manager;
	private File file;
	private FileConfiguration config;

	public SimpleConfig(File configFile,
			int comments, JavaPlugin plugin) {
		this.comments = comments;
		manager = new SimpleConfigManager(plugin);

		file = configFile;
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public Object get(String path) {
		return config.get(path);
	}

	public Location getLocation(String path) {
		String[] loc = config.getString(path).split("\\,");
		World w = Bukkit.getWorld(loc[0]);
		Double x = Double.valueOf(Double.parseDouble(loc[1]));
		Double y = Double.valueOf(Double.parseDouble(loc[2]));
		Double z = Double.valueOf(Double.parseDouble(loc[3]));
		float yaw = Float.parseFloat(loc[4]);
		float pitch = Float.parseFloat(loc[5]);
		Location location = new Location(w, x.doubleValue(), y.doubleValue(),
				z.doubleValue(), yaw, pitch);
		return location;
	}

	public void saveDefaultLocation(String path, Location loc) {
		String location = loc.getWorld().getName() + "," + loc.getX() + ","
				+ loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + ","
				+ loc.getPitch();
		config.addDefault(path, location);
	}

	public void saveLocation(String path, Location loc) {
		String location = loc.getWorld().getName() + "," + loc.getX() + ","
				+ loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + ","
				+ loc.getPitch();
		config.set(path, location);
		saveConfig();
	}

	public Object get(String path, Object def) {
		return config.get(path, def);
	}

	public String getString(String path) {
		return config.getString(path);
	}

	public String getString(String path, String def) {
		return config.getString(path, def);
	}

	public int getInt(String path) {
		return config.getInt(path);
	}

	public int getInt(String path, int def) {
		return config.getInt(path, def);
	}

	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	public boolean getBoolean(String path, boolean def) {
		return config.getBoolean(path, def);
	}

	public void createSection(String path) {
		config.createSection(path);
	}

	public ConfigurationSection getConfigurationSection(String path) {
		return config.getConfigurationSection(path);
	}

	public double getDouble(String path) {
		return config.getDouble(path);
	}

	public double getDouble(String path, double def) {
		return config.getDouble(path, def);
	}

	public List<?> getList(String path) {
		return config.getList(path);
	}

	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}

	public List<?> getList(String path, List<?> def) {
		return config.getList(path, def);
	}

	public boolean contains(String path) {
		return config.contains(path);
	}

	public void removeKey(String path) {
		config.set(path, null);
	}

	public void set(String path, Object value) {
		config.set(path, value);
	}

	public void set(String path, Object value, String comment) {
		if (!config.contains(path)) {
			config.set(manager.getPluginName() + "_COMMENT_" + comments, " "
					+ comment);
			comments += 1;
		}

		config.set(path, value);
	}

	public void set(String path, Object value, String[] comment) {
		for (String comm : comment) {
			if (!config.contains(path)) {
				config.set(manager.getPluginName() + "_COMMENT_" + comments,
						" " + comm);
				comments += 1;
			}
		}

		config.set(path, value);
	}

	public void addDefault(String path, Object value) {
		if (!config.isSet(path))
			config.set(path, value);
	}

	public void addDefault(String path, Object value, String[] comment) {
		if (!config.isSet(path)) {
			for (String comm : comment) {
				if (!config.contains(path)) {
					config.set(
							manager.getPluginName() + "_COMMENT_" + comments,
							" " + comm);
					comments += 1;
				}
			}

			config.set(path, value);
		}
	}

	public void setHeader(String[] header) {
		manager.setHeader(file, header);
		comments = (header.length + 2);
		reloadConfig();
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}

	public void saveConfig() {
		String config = this.config.saveToString();
		manager.saveConfig(config, file);
	}

	public Set<String> getKeys() {
		return config.getKeys(false);
	}
}