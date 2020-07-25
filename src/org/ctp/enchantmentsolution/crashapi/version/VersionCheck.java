package org.ctp.enchantmentsolution.crashapi.version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.crashapi.version.Version.VersionType;

public class VersionCheck implements Listener, Runnable {

	private PluginVersion version;
	private String history, spigot, github;
	private boolean latestVersion, experimentalVersion, checked;

	public VersionCheck(PluginVersion version, String historyURL, String spigotURL, String githubURL,
	boolean getLatestVersion, boolean getExperimentalVersions) {
		setPluginVersion(version);
		setHistory(historyURL);
		setSpigot(spigotURL);
		setGithub(githubURL);
		setLatestVersion(getLatestVersion);
		setExperimentalVersion(getExperimentalVersions);
	}

	@Override
	public void run() {
		ChatUtils chat = version.getPlugin().getChat();
		if (latestVersion) chat.sendInfo("Checking for latest version.");
		List<Version> versionHistory = new ArrayList<Version>();
		try {
			URL urlv = new URL(history);
			BufferedReader in = new BufferedReader(new InputStreamReader(urlv.openStream()));
			String line = in.readLine();
			while (line != null) {
				String[] strings = line.split(" ");
				if (strings.length > 0) {
					VersionType type;
					if (strings.length > 1) try {
						type = VersionType.valueOf(strings[1].toUpperCase());
					} catch (IllegalArgumentException ex) {
						ex.printStackTrace();
						type = VersionType.UNKNOWN;
					}
					else
						type = VersionType.UNKNOWN;
					Version version = null;
					if (strings.length > 2) version = new Version(strings[0], type, strings[2]);
					else
						version = new Version(strings[0], type);
					versionHistory.add(version);
				}
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
			chat.sendWarning("Issue with finding newest version.");
		}
		version.setPluginVersions(versionHistory);
		if (!version.isOfficialVersion() && latestVersion) chat.sendWarning("Uh oh! Plugin author forgot to update version history. Go tell them: " + spigot);
		else if (!version.hasNewerVersion(experimentalVersion) && latestVersion) chat.sendInfo("Your version is up-to-date.");
		else if (latestVersion) {
			Version pluginVersion = version.getNewestVersion(experimentalVersion);
			String versionString = pluginVersion.getVersionName();
			if (pluginVersion.getType() == VersionType.EXPERIMENTAL) chat.sendWarning("Experimental Version " + versionString + " of " + version.getPlugin().getName() + " is ready for testing! Download it here: " + github);
			else
				chat.sendWarning("Version " + versionString + " of " + version.getPlugin().getName() + " is available! Download it here: " + spigot);
		}
		if (!checked) Bukkit.getScheduler().runTaskLater(version.getPlugin(), (Runnable) () -> {
			for(Player player: Bukkit.getOnlinePlayers())
				if (player.hasPermission(version.getPlugin().getName().toLowerCase() + ".version-updater")) sendPlayerMessage(player);
		}, 1l);
		checked = true;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission(version.getPlugin().getName().toLowerCase() + ".version-updater")) sendPlayerMessage(player);
	}

	private void sendPlayerMessage(Player player) {
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
			ChatUtils chat = version.getPlugin().getChat();
			if (!version.isOfficialVersion()) chat.sendMessage(player, "Uh oh! Plugin author forgot to update version history. Go tell them: ", github);
			else if (version.hasNewerVersion(experimentalVersion)) {
				if (version.isExperimentalVersion()) {
					chat.sendMessage(player, "Thank you for using an experimental version of " + version.getPlugin().getName() + "! Please report any bugs you find to github.");
					chat.sendMessage(player, "Link: ", github);
					chat.sendMessage(player, "Version: " + version.getCurrent().getVersionName());
				}
				if (!latestVersion) return;
				Version pluginVersion = version.getNewestVersion(experimentalVersion);
				String versionString = pluginVersion.getVersionName();
				if (pluginVersion.getType() == VersionType.EXPERIMENTAL) chat.sendMessage(player, "Experimental Version " + versionString + " of " + version.getPlugin().getName() + " is ready for testing! Download it here: " + github);
				else
					chat.sendMessage(player, "Version " + versionString + " of " + version.getPlugin().getName() + " is available! Download it here: " + spigot);
			} else if (version.isExperimentalVersion()) {
				chat.sendMessage(player, "Thank you for using an experimental version of " + version.getPlugin().getName() + "! Please report any bugs you find to github.");
				chat.sendMessage(player, "Link: ", github);
				chat.sendMessage(player, "Version: " + version.getCurrent().getVersionName());
			} else if (version.isUpcomingVersion()) {
				chat.sendMessage(player, "Thank you for using an upcoming version of " + version.getPlugin().getName() + "! Please report any bugs you find to github.");
				chat.sendMessage(player, "Link: ", github);
				chat.sendMessage(player, "Version: " + version.getCurrent().getVersionName());
			}
		}, 2l);
	}

	public PluginVersion getPluginVersion() {
		return version;
	}

	public void setPluginVersion(PluginVersion version) {
		this.version = version;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getSpigot() {
		return spigot;
	}

	public void setSpigot(String spigot) {
		this.spigot = spigot;
	}

	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public boolean isLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(boolean latestVersion) {
		this.latestVersion = latestVersion;
	}

	public boolean isExperimentalVersion() {
		return experimentalVersion;
	}

	public void setExperimentalVersion(boolean experimentalVersion) {
		this.experimentalVersion = experimentalVersion;
	}

}
