package org.ctp.enchantmentsolution.utils.yaml;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlConfigBackup extends YamlConfig {

	private Map<String, YamlInfo> configInventoryData;

	public YamlConfigBackup(File configFile, String[] header) {
		super(configFile, header);
		configInventoryData = new LinkedHashMap<String, YamlInfo>();
	}

	public void revert() {
		configInventoryData = new LinkedHashMap<String, YamlInfo>();
	}

	public void setConfigPath(String path, Object value) {
		configInventoryData.put(path, new YamlInfo(path, value));
	}

	public List<String> getConfigInventoryEntryKeys() {
		List<String> values = new ArrayList<String>();
		for(Iterator<java.util.Map.Entry<String, YamlInfo>> it = configInventoryData.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			List<String> entryKeys = getEntryKeys(e.getKey());
			for(int i = 0; i < entryKeys.size(); i++)
				if (!values.contains(entryKeys.get(i))) values.add(entryKeys.get(i));
		}
		return values;
	}

	public void getFromBackup(List<YamlInfo> info) {
		for(YamlInfo i: info)
			if (i.getValue() != null) set(i.getPath(), i.getValue());
	}

	public void setFromBackup(String configString) {
		Reader configReader = new StringReader(configString);
		YamlConfiguration c = YamlConfiguration.loadConfiguration(configReader);
		for(String s: c.getKeys(true)) {
			Object o = c.get(s);
			if (o instanceof MemorySection) continue;
			YamlInfo info = new YamlInfo(s, o);
			setInfo(info.getPath(), info);
		}
		saveConfig();
	}

	public void setFromBackup(YamlConfig config) {
		for(Iterator<java.util.Map.Entry<String, YamlInfo>> it = config.getAllInfo().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if (e.getValue().getValue() != null) if (contains(e.getKey())) {
				YamlInfo data = getInfo(e.getKey());
				data.setValue(e.getValue().getValue());
				setInfo(e.getKey(), data);
			} else
				setInfo(e.getKey(), e.getValue());
		}

		saveConfig();
	}

	private void update() {
		for(Iterator<java.util.Map.Entry<String, YamlInfo>> it = configInventoryData.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if (contains(e.getKey())) {
				YamlInfo data = getAllInfo().get(e.getKey());
				data.setValue(e.getValue().getValue());
				setInfo(e.getKey(), data);
			} else
				setInfo(e.getKey(), e.getValue());
		}

		configInventoryData = new LinkedHashMap<String, YamlInfo>();
	}

	@Override
	public String getStringValue(String path) {
		YamlInfo info = configInventoryData.get(path);
		if (info == null) info = getInfo(path);

		if (info == null) return "Click for more.";

		if (info.getBooleanValue() != null) return info.getBooleanValue().toString();

		if (info.getInteger() != null) return info.getInteger().toString();

		if (info.getDoubleValue() != null) return info.getDoubleValue().toString();

		if (info.getStringList() != null) return "Click for more.";

		if (info.getString() != null) return info.getString();

		return null;
	}

	public Object getCombined(String path) {
		if (configInventoryData.containsKey(path)) return configInventoryData.get(path).getValue();
		if (getAllInfo().containsKey(path)) return get(path);
		return null;
	}

	public List<String> getStringListCombined(String path) {
		if (configInventoryData.containsKey(path)) return configInventoryData.get(path).getStringList();
		YamlInfo info = getInfo(path);
		if (info == null) return null;
		return info.getStringList();
	}
	
	public boolean matches(String otherConfig) {
		return otherConfig.equals(encode(true));
	}

	@Override
	public void saveConfig() {
		update();
		if (getFile() == null) return;
		super.saveConfig();
	}

	public String encode(boolean includeChanges) {
		StringBuilder config = new StringBuilder("");
		ArrayList<YamlChild> keyList = new ArrayList<YamlChild>();

		Map<String, YamlInfo> updatedInfo = new HashMap<String, YamlInfo>();
		
		for(Iterator<java.util.Map.Entry<String, YamlInfo>> it = getDefaults().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if (updatedInfo.containsKey(e.getKey())) {
				YamlInfo data = updatedInfo.get(e.getKey());
				data.setComments(e.getValue().getComments());
				updatedInfo.put(e.getKey(), data);
			} else
				updatedInfo.put(e.getKey(), e.getValue());
		}
		
		for(Iterator<java.util.Map.Entry<String, YamlInfo>> it = getAllInfo().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if (updatedInfo.containsKey(e.getKey())) {
				YamlInfo data = getAllInfo().get(e.getKey());
				data.setValue(e.getValue().getValue());
				updatedInfo.put(e.getKey(), data);
			} else
				updatedInfo.put(e.getKey(), e.getValue());
		}
		
		for(Iterator<java.util.Map.Entry<String, YamlInfo>> it = configInventoryData.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if (updatedInfo.containsKey(e.getKey())) {
				YamlInfo data = getAllInfo().get(e.getKey());
				data.setValue(e.getValue().getValue());
				updatedInfo.put(e.getKey(), data);
			} else
				updatedInfo.put(e.getKey(), e.getValue());
		}

		for(Iterator<java.util.Map.Entry<String, YamlInfo>> it = updatedInfo.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			List<String> entryKeys = getEntryKeys(e.getKey());
			for(int i = 0; i < entryKeys.size(); i++) {
				boolean added = false;
				for(YamlChild child: keyList)
					if (child.addChild(entryKeys.get(i))) {
						added = true;
						break;
					}
				if (!added) keyList.add(new YamlChild(entryKeys.get(i)));
			}
		}

		for(YamlChild child: keyList)
			config.append(getLevel(child, false));
		
		return new String(Base64.getEncoder().encode(config.toString().getBytes()));
	}
}
