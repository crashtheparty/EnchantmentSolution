package org.ctp.enchantmentsolution.utils.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.ctp.enchantmentsolution.utils.config.Configurable;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;
import org.ctp.enchantmentsolution.utils.yaml.YamlConfigBackup;

public class DataFile implements Configurable {

	private File file;
	private YamlConfig config;

	public DataFile(File dataFolder, String fileName) {
		file = new File(dataFolder + "/extras/" + fileName);
		try {
			YamlConfiguration.loadConfiguration(file);

			String[] header = { "Enchantment Solution", "Plugin by", "crashtheparty" };
			config = new YamlConfigBackup(file, header);
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
		if (Files.exists(source, LinkOption.NOFOLLOW_LINKS)) try {
			Files.copy(source, target);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
