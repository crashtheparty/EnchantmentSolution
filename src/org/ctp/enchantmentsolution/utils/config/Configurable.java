package org.ctp.enchantmentsolution.utils.config;

import java.io.File;
import java.util.List;

import org.ctp.enchantmentsolution.utils.yaml.YamlConfig;

public interface Configurable {

	public File getFile();

	public YamlConfig getConfig();

	public void save();

	public void reload();

	public String getString(String s);

	public int getInt(String s);

	public boolean getBoolean(String s);

	public double getDouble(String s);

	public List<String> getStringList(String s);

	public void updatePath(String s, Object value);
}
