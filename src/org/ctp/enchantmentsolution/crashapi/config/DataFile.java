package org.ctp.enchantmentsolution.crashapi.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ctp.enchantmentsolution.crashapi.CrashAPIPlugin;
import org.ctp.enchantmentsolution.crashapi.config.yaml.YamlConfig;
import org.ctp.enchantmentsolution.crashapi.utils.ChatUtils;

public class DataFile implements Configurable {

	private File file;
	private YamlConfig config;
	private final CrashAPIPlugin plugin;

	public DataFile(CrashAPIPlugin plugin, File dataFolder, String fileName, boolean extra) {
		this.plugin = plugin;
		file = extra ? new File(dataFolder + "/extras/" + fileName) : new File(dataFolder + "/" + fileName);
		try {
			YamlConfiguration.loadConfiguration(file);

			String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };
			config = new YamlConfig(file, header);
			config.getFromConfig();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public YamlConfig getConfig() {
		return config;
	}

	public void saveOnLoad() {
		createBackup();
		config.saveConfig();
	}

	@Override
	public void save() {
		config.saveConfig();
	}

	@Override
	public void reload() {
		config.getFromConfig();
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

	@Override
	public List<String> getStringList(String s) {
		return config.getStringList(s);
	}

	public void createBackup() {
		String absolutePath = file.getAbsolutePath();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		LocalDateTime now = LocalDateTime.now();
		String newPath = absolutePath.substring(0, absolutePath.lastIndexOf('.')) + dtf.format(now) + ".yml.gz";
		Path source = Paths.get(absolutePath);
		Path target = Paths.get(newPath);
		try {
			Files.copy(source, target);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public CrashAPIPlugin getPlugin() {
		return plugin;
	}

	@Override
	public ChatUtils getChat() {
		return ChatUtils.getUtils(plugin);
	}

	@Override
	public void repairConfig() {}

}
