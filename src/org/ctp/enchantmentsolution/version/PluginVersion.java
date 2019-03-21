package org.ctp.enchantmentsolution.version;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.version.Version.VersionType;

public class PluginVersion {
	
	private List<Version> pluginVersions = new ArrayList<Version>();
	private String current;
	private JavaPlugin plugin;

	public PluginVersion(JavaPlugin plugin, String version) {
		current = version;
		this.setPlugin(plugin);
	}
	
	public String getCurrent() {
		return current;
	}
	
	public String getNewestVersion() {
		for(int i = pluginVersions.size() - 1; i >= 0; i--) {
			if(pluginVersions.get(i).getType().equals(VersionType.LIVE)) {
				return pluginVersions.get(i).getVersionName();
			}
		}
		return null;
	}
	
	public boolean isOfficialVersion() {
		for(int i = pluginVersions.size() - 1; i >= 0; i--) {
			if(pluginVersions.get(i).getVersionName().equalsIgnoreCase(current)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasNewerVersion() {
		for(int i = pluginVersions.size() - 1; i >= 0; i--) {
			Version v = pluginVersions.get(i);
			if(v.getVersionName().equalsIgnoreCase(current)) {
				return false;
			}
			if(v.getType().equals(VersionType.LIVE)) {
				return true;
			}
		}
		return true;
	}
	
	public boolean isExperimentalVersion() {
		for(int i = pluginVersions.size() - 1; i >= 0; i--) {
			if(pluginVersions.get(i).getVersionName().equalsIgnoreCase(current)) {
				return pluginVersions.get(i).getType().equals(VersionType.EXPERIMENTAL);
			}
		}
		return false;
	}
	
	public boolean isUpcomingVersion() {
		for(int i = pluginVersions.size() - 1; i >= 0; i--) {
			if(pluginVersions.get(i).getVersionName().equalsIgnoreCase(current)) {
				return pluginVersions.get(i).getType().equals(VersionType.UPCOMING);
			}
		}
		return false;
	}

	public List<Version> getPluginVersions() {
		return pluginVersions;
	}

	public void setPluginVersions(List<Version> pluginVersions) {
		this.pluginVersions = pluginVersions;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public void setPlugin(JavaPlugin plugin) {
		this.plugin = plugin;
	}

}
