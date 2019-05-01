package org.ctp.enchantmentsolution.utils.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
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

public class YamlConfig {
	private File file;
	private Map<String, YamlInfo> defaults;
	private Map<String, YamlInfo> info;
	private Map<String, List<String>> enums;
	private String[] header;
	private YamlConfiguration config;
	private boolean comments = true;
	
	public YamlConfig(File configFile, String[] header) {
		this.header = header;
		file = configFile;
		if(configFile != null) {
			config = YamlConfiguration.loadConfiguration(configFile);
		}
		info = new LinkedHashMap<String, YamlInfo>();
		defaults = new LinkedHashMap<String, YamlInfo>();
		enums = new LinkedHashMap<String, List<String>>();
	}
	
	protected File getFile() {
		return file;
	}
	
	protected void setInfo(String key, YamlInfo info) {
		this.info.put(key, info);
	}
	
	protected Map<String, YamlInfo> getAllInfo(){
		return info;
	}
	
	public void addEnum(String path, List<String> values) {
		enums.put(path, values);
	}
	
	public List<String> getEnums(String path){
		if(enums.containsKey(path)) {
			return enums.get(path);
		}
		return null;
	}
	
	public void setComments(boolean comments) {
		this.comments = comments;
	}
	
	public List<String> getConfigurationInfo(String path){
		if(path == null) {
			path = "";
		}
		List<String> keys = new ArrayList<String>();
		
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = this.info.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			List<String> entryKeys = getEntryKeys(e.getKey());
			for(String key : entryKeys) {
				if(!keys.contains(key)) {
					if(key.startsWith(path)) {
						if(StringUtils.countMatches(path, ".") + 1 == StringUtils.countMatches(key, ".")) {
							keys.add(key);
						}
					}
				}
			}
		}
		
		return keys;
	}

	protected YamlInfo getInfo(String path) {
		return info.get(path);
	}
	
	public String getStringValue(String path) {
		YamlInfo info = getInfo(path);
		
		if(info == null) {
			return "Click for more.";
		}
		
		if(info.getBooleanValue() != null) {
			return info.getBooleanValue().toString();
		}
		
		if(info.getInteger() != null) {
			return info.getInteger().toString();
		}
		
		if(info.getDoubleValue() != null) {
			return info.getDoubleValue().toString();
		}
		
		if(info.getStringList() != null) {
			return "Click for more.";
		}
		
		if(info.getString() != null) {
			return info.getString();
		}
		
		return null;
	}
	
	public String getType(String path) {
		YamlInfo info = getInfo(path);
		
		if(info == null) {
			return "nested value";
		}

		if(getEnums(path) != null) {
			if(info.getStringList() != null) {
				return "enum_list";
			}
			return "enum";
		}	
		
		if(info.getBooleanValue() != null) {
			return "boolean";
		}
		
		if(info.getInteger() != null) {
			return "integer";
		}
		
		if(info.getDoubleValue() != null) {
			return "double";
		}
		
		if(info.getStringList() != null) {
			return "list";
		}
		
		if(info.getString() != null) {
			return "string";
		}
		
		return null;
	}
	
	public boolean match(String path, Object value) {
		YamlInfo info = getInfo(path);
		if(value == null && info == null) {
			return true;
		} else if(value == null && info.getValue() == null) {
			return true;
		} else if(value == null) {
			return false;
		}
		
		switch(getType(path)) {
		case "boolean":
			if(value.toString().equals("true") && info.getBoolean()) {
				return true;
			}
			if(value.toString().equals("false") && !info.getBoolean()) {
				return true;
			}
			break;
		case "integer":
			if((info.getInt() + "").equals(value.toString())) {
				return true;
			}
			break;
		case "double":
			if((info.getDouble() + "").equals(value.toString())) {
				return true;
			}
			break;
		case "enum_list":
		case "list":
			LinkedHashMap<String, Boolean> keySame = new LinkedHashMap<String, Boolean>();
			String[] values = replaceLast((value.toString().replaceFirst("\\[", "")), "]", "").split(", ");
			for(Object key : values) {
				if(!key.toString().trim().equals("")) {
					keySame.put(key.toString(), false);
				}
			}
			for(String key : info.getStringList()) {
				if(keySame.containsKey(key)) {
					keySame.put(key, true);
				} else {
					keySame.put(key, false);
				}
			}
			
			return !keySame.containsValue(false);
		case "enum":
		case "string":
			if (info.getString().equals(value.toString())){
				return true;
			}
			break;
		}
		return false;
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
	
	public Integer getInteger(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return null;
		}
		return info.getInteger();
	}
	
	public boolean getBoolean(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return false;
		}
		return info.getBoolean();
	}
	
	public Boolean getBooleanValue(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return null;
		}
		return info.getBooleanValue();
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
	
	public Double getDoubleValue(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return null;
		}
		return info.getDoubleValue();
	}

	public List<String> getStringList(String path) {
		YamlInfo info = getInfo(path);
		if(info == null) {
			return null;
		}
		return info.getStringList();
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
		if(header != null && header.length > 0) {
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

	protected String prepareConfigString() {
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
	
	public List<String> getLevelEntryKeys(String level){
		List<String> values = new ArrayList<String>();
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = info.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			List<String> entryKeys = getEntryKeys(e.getKey());
			if(level == null || level.equals("")) {
				for(int i = 0; i < entryKeys.size(); i++) {
					if(!entryKeys.get(i).contains(".") && !values.contains(entryKeys.get(i))) {
						values.add(entryKeys.get(i));
					}
				}
			} else {
				for(int i = 0; i < entryKeys.size(); i++) {
					if(entryKeys.get(i).startsWith(level + ".")) {
						if(entryKeys.get(i).length() > level.length() + 1) {
							String find = entryKeys.get(i).substring(level.length() + 1);
							if(!find.contains(".") && !values.contains(entryKeys.get(i))) {
								values.add(entryKeys.get(i));
							}
						}
					}
				}
			}
		}
		return values;
	}
	
	public List<String> getAllEntryKeys(){
		List<String> values = new ArrayList<String>();
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = info.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			List<String> entryKeys = getEntryKeys(e.getKey());
			for(int i = 0; i < entryKeys.size(); i++) {
				if(!values.contains(entryKeys.get(i))) {
					values.add(entryKeys.get(i));
				}
			}
		}
		return values;
	}
	
	protected List<String> getEntryKeys(String keys) {
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
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
			writer.write(configuration);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getLevel(YamlChild child) {
		StringBuilder config = new StringBuilder("");
		String key = child.getPath();
		int deep = StringUtils.countMatches(key, ".") * 4;
		
		if(comments) {
			if(contains(key)) {
				YamlInfo info = getInfo(key);
				if(info.getComments().length > 0) {
					StringBuilder line = new StringBuilder("\n");
					for(String comment : info.getComments()) {
						for(int i = 0; i < deep; i++) {
							line.append(" ");
						}
						line.append("# " + comment + "\n");
					}
					config.append(line);
				}
			}
		}
		StringBuilder line = new StringBuilder("");
		for(int i = 0; i < deep; i++) {
			line.append(" ");
		}
		line.append(key.substring(key.lastIndexOf('.') + 1) + ": ");
		if(contains(key)) {
			List<String> objects = getStringList(key);
			if(objects != null) {
				if(objects.size() == 0) {
					line.append("[]\n");
				} else {
					line.append("\n");
					for(String s : objects) {
						for(int i = 0; i < deep; i++) {
							line.append(" ");
						}
						line.append("- ");
						line.append("'" + s.replaceAll("'", "''") + "'");
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
	
	public String getFileName() {
		return file.getName();
	}
	
	public String replaceLast(String string, String toReplace, String replacement) {
	    int pos = string.lastIndexOf(toReplace);
	    if (pos > -1) {
	        return string.substring(0, pos)
	             + replacement
	             + string.substring(pos + toReplace.length(), string.length());
	    } else {
	        return string;
	    }
	}

	public void addMinMax(String path, int i, int j) {
		if(getInfo(path) == null) {
			defaults.get(path).setMinMax(i, j);
		} else {
			getInfo(path).setMinMax(i, j);
		}
	}
	
	public void copyDefaults(YamlConfig config) {
		this.defaults = config.defaults;
	}
}
