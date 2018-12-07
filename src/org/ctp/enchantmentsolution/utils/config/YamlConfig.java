package org.ctp.enchantmentsolution.utils.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class YamlConfig {
	private File file;
	private Map<String, YamlInfo> defaults;
	private Map<String, YamlInfo> info;
	private String[] header;
	private YamlConfiguration config;
	private JavaPlugin plugin;
	private static boolean COMMENTS = true;

	public YamlConfig(File configFile, JavaPlugin plugin, String[] header) {
		this.plugin = plugin;
		this.header = header;
		file = configFile;
		config = YamlConfiguration.loadConfiguration(configFile);
		info = new LinkedHashMap<String, YamlInfo>();
		defaults = new LinkedHashMap<String, YamlInfo>();
	}
	
	public static void setComments(boolean comments) {
		COMMENTS = comments;
	}
	
	public Map<String, YamlInfo> getConfigurationInfo(String path){
		if(path == null || path.equals("")) {
			return info;
		}
		Map<String, YamlInfo> info = new LinkedHashMap<String, YamlInfo>();
		List<String> keys = getEntryKeys(path);
		for(String key : keys) {
			if(this.info.containsKey(key)) {
				info.put(key, this.info.get(key));
			}
		}
		return info;
	}

	private YamlInfo getInfo(String path) {
		return info.get(path);
	}
	
	public Object get(String path) {
		if(info.containsKey(path)) {
			return info.get(path).getValue();
		}
		return null;
	}
	
	public Location getLocation(String path) {
		YamlInfo info = getInfo(path);
		if(info == null || info.getString() == null) {
			return null;
		}
		String[] loc = info.getString().split("\\,");
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
	
	public void setLocation(String path, Location loc) {
		String location = loc.getWorld().getName() + "," + loc.getX() + ","
				+ loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + ","
				+ loc.getPitch();
		set(path, location);
	}

	public void setDefaultLocation(String path, Location loc) {
		String location = loc.getWorld().getName() + "," + loc.getX() + ","
				+ loc.getY() + "," + loc.getZ() + "," + loc.getYaw() + ","
				+ loc.getPitch();
		addDefault(path, location);
	}

	public Object get(String path, Object def) {
		if(get(path) != null) {
			return get(path);
		}
		return def;
	}

	public String getString(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return null;
		}
		return info.getString();
	}

	public String getString(String path, String def) {
		YamlInfo info = getInfo(path);
		if(info == null || info.getString() == null) {
			return def;
		}
		return info.getString();
	}
	
	public int getInt(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return 0;
		}
		return info.getInt();
	}

	public int getInt(String path, int def) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return def;
		}
		return info.getInt();
	}
	
	public boolean getBoolean(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return false;
		}
		return info.getBoolean();
	}

	public boolean getBoolean(String path, boolean def) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return def;
		}
		return info.getBoolean();
	}

	public double getDouble(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return 0;
		}
		return info.getDouble();
	}

	public double getDouble(String path, double def) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return def;
		}
		return info.getDouble();
	}

	public List<?> getList(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return null;
		}
		return info.getList();
	}

	public List<String> getStringList(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return null;
		}
		return info.getStringList();
	}

	public List<?> getList(String path, List<?> def) {
		YamlInfo info = getInfo(path);
		if(info == null || info.getList() == null) {
			return def;
		}
		return info.getList();
	}

	public boolean contains(String path) {
		return getInfo(path) != null;
	}

	public boolean containsElements(String path) {
		if(getInfo(path) != null) {
			return true;
		}
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = info.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if(e.getKey().startsWith(path)) {
				return true;
			}
		}
		return false;
	}

	public void removeKey(String path) {
		info.remove(path);
	}

	public void set(String path, Object value) {
		info.put(path, new YamlInfo(path, value));
	}

	public void set(String path, Object value, String comment) {
		info.put(path, new YamlInfo(path, value, comment));
	}

	public void set(String path, Object value, String[] comment) {
		info.put(path, new YamlInfo(path, value, comment));
	}

	public void addDefault(String path, Object value) {
		defaults.put(path, new YamlInfo(path, value));
	}

	public void addDefault(String path, Object value, String comment) {
		defaults.put(path, new YamlInfo(path, value, comment));
	}

	public void addDefault(String path, Object value, String[] comment) {
		defaults.put(path, new YamlInfo(path, value, comment));
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public Set<String> getKeys() {
		return info.keySet();
	}
	
	public void getFromConfig() {
		info = new LinkedHashMap<String, YamlInfo>();
		for(String key : config.getKeys(true)) {
			if(!(config.get(key) instanceof MemorySection)) {
				YamlInfo info = new YamlInfo(key, config.get(key));
				this.info.put(key, info);
			}
		}
	}
	
	private String headerString() {
		StringBuilder config = new StringBuilder("");
		if(header.length > 0) {
			config.append("# +----------------------------------------------------+ #\n");
	
			for (String line : header) {
				if (line.length() <= 50) {
	
					int lenght = (50 - line.length()) / 2;
					StringBuilder finalLine = new StringBuilder(line);
	
					for (int i = 0; i < lenght; i++) {
						finalLine.append(" ");
						finalLine.reverse();
						finalLine.append(" ");
						finalLine.reverse();
					}
	
					if (line.length() % 2 != 0) {
						finalLine.append(" ");
					}
	
					config.append("# < " + finalLine.toString() + " > #\n");
				}
			}
	
			config.append("# +----------------------------------------------------+ #\n\n\n");
		}
		return config.toString();
	}

	private String prepareConfigString() {
		StringBuilder config = new StringBuilder("");
		ArrayList<YamlChild> keyList = new ArrayList<YamlChild>();
		
		config.append(headerString());
		
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = defaults.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if(contains(e.getKey())) {
				YamlInfo data = info.get(e.getKey());
				data.setComments(e.getValue().getComments());
				info.put(e.getKey(), data);
			} else {
				info.put(e.getKey(), e.getValue());
			}
		}
		
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = info.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			List<String> entryKeys = getEntryKeys(e.getKey());
			for(int i = 0; i < entryKeys.size(); i++) {
				boolean added = false;
				for(YamlChild child : keyList) {
					if(child.addChild(entryKeys.get(i))) {
						added = true;
						break;
					}
				}
				if(!added) {
					keyList.add(new YamlChild(entryKeys.get(i)));
				}
			}
		}
				
		for(YamlChild child : keyList) {
			config.append(getLevel(child));
		}
		
		
		return config.toString();
	}
	
	private List<String> getEntryKeys(String keys) {
		List<String> values = new ArrayList<String>();
		values.add(keys);
		while(keys.indexOf('.') > -1) {
			keys = keys.substring(0, keys.lastIndexOf('.'));
			values.add(0, keys);
		}
		return values;
	}

	public void saveConfig() {
		String configuration = prepareConfigString();
		try {
			BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(
					file));
			writer.write(configuration);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getPluginName() {
		return plugin.getDescription().getName();
	}
	
	private String getLevel(YamlChild child) {
		StringBuilder config = new StringBuilder("");
		String key = child.getPath();
		
		if(COMMENTS) {
			if(contains(key)) {
				YamlInfo info = getInfo(key);
				if(info.getComments().length > 0) {
					StringBuilder line = new StringBuilder("\n");
					for(String comment : info.getComments()) {
						line.append("# " + comment + "\n");
					}
					config.append(line);
				}
			}
		}
		StringBuilder line = new StringBuilder("");
		int deep = StringUtils.countMatches(key, ".") * 4;
		for(int i = 0; i < deep; i++) {
			line.append(" ");
		}
		line.append(key.substring(key.lastIndexOf('.') + 1) + ": ");
		if(contains(key)) {
			List<?> objects = getList(key);
			if(objects != null) {
				if(objects.size() == 0) {
					line.append("[]\n");
				} else {
					line.append("\n");
					for(Object o : objects) {
						for(int i = 0; i < deep; i++) {
							line.append(" ");
						}
						line.append("- ");
						if(o instanceof String) {
							line.append("'" + ((String) o).replaceAll("'", "''") + "'");
						} else {
							line.append(o);
						}
						line.append("\n");
					}
				}
			} else {
				Object o = get(key);
				if(o instanceof String) {
					line.append("'" + ((String) o).replaceAll("'", "''") + "'");
				} else {
					line.append(o);
				}
			}
		}
		line.append("\n");
		config.append(line);
		
		for(YamlChild c : child.getChildren()) {
			config.append(getLevel(c));
		}
		
		return config.toString();
	}
}
