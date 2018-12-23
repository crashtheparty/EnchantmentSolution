package org.ctp.enchantmentsolution.utils.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<String> getConfigInventoryEntryKeys(){
		List<String> values = new ArrayList<String>();
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = configInventoryData.entrySet().iterator(); it.hasNext();) {
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
	
	public void getFromBackup(List<YamlInfo> info) {
		for(YamlInfo i : info) {
			if(i.getValue() != null) {
				set(i.getPath(), i.getValue());
			}
		}
	}
	
	public void setFromBackup(List<YamlInfo> backupInfo) {
		for (YamlInfo info : backupInfo) {
			if(info.getValue() != null) {
				if(contains(info.getPath())) {
					YamlInfo data = getInfo(info.getPath());
					data.setValue(info.getValue());
					setInfo(info.getPath(), data);
				} else {
					setInfo(info.getPath(), info);
				}
			}
		}
		
		saveConfig();
	}
	
	public void setFromBackup(YamlConfig config) {
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = config.getAllInfo().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if(e.getValue().getValue() != null) {
				if(contains(e.getKey())) {
					YamlInfo data = getInfo(e.getKey());
					data.setValue(e.getValue().getValue());
					setInfo(e.getKey(), data);
				} else {
					setInfo(e.getKey(), e.getValue());
				}
			}
		}
		
		saveConfig();
	}
	
	private void update() {
		for (Iterator<java.util.Map.Entry<String, YamlInfo>> it = configInventoryData.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<String, YamlInfo> e = it.next();
			if(contains(e.getKey())) {
				YamlInfo data = getAllInfo().get(e.getKey());
				data.setValue(e.getValue().getValue());
				setInfo(e.getKey(), data);
			} else {
				setInfo(e.getKey(), e.getValue());
			}
		}
		
		configInventoryData = new LinkedHashMap<String, YamlInfo>();
	}
	
	public String getStringValue(String path) {
		YamlInfo info = configInventoryData.get(path);
		if(info == null) {
			info = getInfo(path);
		}
		
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
	
	public boolean matchConfig(String path, Object value) {
		YamlInfo info = configInventoryData.get(path);
		if(value == null && info == null) {
			return true;
		} else if (info == null) {
			//no changes to info, so return true
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
		case "list":
			LinkedHashMap<String, Boolean> keySame = new LinkedHashMap<String, Boolean>();
			String[] values = replaceLast((value.toString().replaceFirst("\\[", "")), "]", "").split(", ");
			for(Object key : values) {
				keySame.put(key.toString(), false);
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
	
	public Object getCombined(String path) {
		if(configInventoryData.containsKey(path)) {
			return configInventoryData.get(path).getValue();
		}
		if(getAllInfo().containsKey(path)) {
			return get(path);
		}
		return null;
	}

	public List<String> getStringListCombined(String path) {
		if(configInventoryData.containsKey(path)) {
			return configInventoryData.get(path).getStringList();
		}
		YamlInfo info = getInfo(path);
		if(info == null) {
			return null;
		}
		return info.getStringList();
	}

	public void saveConfig() {
		update();
		if(getFile() == null) return;
		String configuration = prepareConfigString();
		try {
			BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(
					getFile()));
			writer.write(configuration);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
