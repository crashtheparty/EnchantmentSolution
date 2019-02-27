package org.ctp.enchantmentsolution.listeners;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.version.PluginVersion;
import org.ctp.enchantmentsolution.version.Version;
import org.ctp.enchantmentsolution.version.Version.VersionType;

public class VersionCheck implements Listener, Runnable {

	@Override
	public void run() {
		if (EnchantmentSolution.getConfigFiles().getDefaultConfig().getBoolean("get_latest_version")) {
			List<Version> versionHistory = new ArrayList<Version>();
			try {
				URL urlv = new URL(
						"https://raw.githubusercontent.com/crashtheparty/EnchantmentSolution/master/VersionHistory");
				BufferedReader in = new BufferedReader(new InputStreamReader(urlv.openStream()));
				String line = in.readLine();
				while (line != null) {
					String[] strings = line.split(" ");
					if (strings.length > 0) {
						VersionType type;
						if (strings.length > 1) {
							try {
								type = VersionType.valueOf(strings[1].toUpperCase());
							} catch(IllegalArgumentException ex) {
								ex.printStackTrace();
								type = VersionType.UNKNOWN;
							}
						} else {
							type = VersionType.UNKNOWN;
						}
						Version version = new Version(strings[0], type);
						versionHistory.add(version);
					}
					line = in.readLine();
				}
				in.close();
			} catch (IOException e) {
				ChatUtils.sendToConsole(Level.WARNING, "Issue with finding newest version.");
			}
			EnchantmentSolution.getPluginVersion().setPluginVersions(versionHistory);
			if (!EnchantmentSolution.getPluginVersion().isOfficialVersion()) {
				ChatUtils.sendToConsole(Level.WARNING,
						"Uh oh! Plugin author forgot to update version history. Go tell them: https://www.spigotmc.org/resources/enchantment-solution.59556/");
			} else if (!EnchantmentSolution.getPluginVersion().hasNewerVersion()) {
				ChatUtils.sendToConsole(Level.INFO, "Your version is up-to-date.");
			} else {
				String version = EnchantmentSolution.getPluginVersion().getNewestVersion();
				ChatUtils.sendToConsole(Level.WARNING, (version == null ? "New Version" : "Version " + version)
						+ " of EnchantmentSolution is available! Download it here: https://www.spigotmc.org/resources/enchantment-solution.59556/");
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("enchantmentsolution.version-updater")) {
			PluginVersion version = EnchantmentSolution.getPluginVersion();
			if (!EnchantmentSolution.getPluginVersion().isOfficialVersion()) {
				ChatUtils.sendMessage(player, "Uh oh! Plugin author forgot to update version history. Go tell them: ",
						"https://www.spigotmc.org/resources/enchantment-solution.59556/");
			} else if (version.hasNewerVersion()) {
				String newest = version.getNewestVersion();
				ChatUtils.sendMessage(player,
						"Version " + newest + " of EnchantmentSolution is available! Download it here: ",
						"https://www.spigotmc.org/resources/enchantment-solution.59556/");

			} else if (version.isExperimentalVersion()) {
				ChatUtils.sendMessage(player,
						"Thank you for using an experimental version of EnchantmentSolution! Please report any bugs you find to github.");
				ChatUtils.sendMessage(player, "Link: ", "https://github.com/crashtheparty/EnchantmentSolution");
				ChatUtils.sendMessage(player, "Version: " + version.getCurrent());
			} else if (version.isUpcomingVersion()) {
				ChatUtils.sendMessage(player,
						"Thank you for using an upcoming version of EnchantmentSolution! Please report any bugs you find to github.");
				ChatUtils.sendMessage(player, "Link: ", "https://github.com/crashtheparty/EnchantmentSolution");
				ChatUtils.sendMessage(player, "Version: " + version.getCurrent());
			}
		}
	}

}
