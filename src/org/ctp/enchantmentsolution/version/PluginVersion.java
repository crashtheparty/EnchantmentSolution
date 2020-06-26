package org.ctp.enchantmentsolution.version;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.version.Version.VersionType;

public class PluginVersion {

	private List<Version> pluginVersions = new ArrayList<Version>();
	private Version current;
	private JavaPlugin plugin;

	public PluginVersion(JavaPlugin plugin, Version version) {
		current = version;
		setPlugin(plugin);
	}

	public Version getCurrent() {
		if (pluginVersions.size() > 0) for(int i = pluginVersions.size() - 1; i >= 0; i--)
			if (pluginVersions.get(i).getVersionName().equals(current.getVersionName())) return pluginVersions.get(i);
		return current;
	}

	public Version getNewestVersion(boolean experimental) {
		for(int i = pluginVersions.size() - 1; i >= 0; i--)
			if (pluginVersions.get(i).getType() == VersionType.LIVE) return pluginVersions.get(i);
			else if (pluginVersions.get(i).getType() == VersionType.EXPERIMENTAL && experimental) return pluginVersions.get(i);
		return null;
	}

	public boolean isOfficialVersion() {
		for(int i = pluginVersions.size() - 1; i >= 0; i--)
			if (pluginVersions.get(i).getVersionName().equalsIgnoreCase(current.getVersionName())) return true;
		return false;
	}

	public boolean hasNewerVersion(boolean experimental) {
		for(int i = pluginVersions.size() - 1; i >= 0; i--) {
			Version v = pluginVersions.get(i);
			if (v.getVersionName().equalsIgnoreCase(current.getVersionName())) return false;
			if (v.getType() == VersionType.LIVE) return true;
			if (experimental && (v.getType() == VersionType.EXPERIMENTAL)) return true;
		}
		return true;
	}

	public boolean isExperimentalVersion() {
		for(int i = pluginVersions.size() - 1; i >= 0; i--)
			if (pluginVersions.get(i).getVersionName().equalsIgnoreCase(current.getVersionName())) return pluginVersions.get(i).getType().equals(VersionType.EXPERIMENTAL);
		return false;
	}

	public boolean isUpcomingVersion() {
		for(int i = pluginVersions.size() - 1; i >= 0; i--)
			if (pluginVersions.get(i).getVersionName().equalsIgnoreCase(current.getVersionName())) return pluginVersions.get(i).getType().equals(VersionType.UPCOMING);
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
