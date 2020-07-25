package org.ctp.enchantmentsolution.crashapi.commands;

import java.util.HashMap;
import java.util.List;

import org.ctp.enchantmentsolution.crashapi.CrashAPIPlugin;
import org.ctp.enchantmentsolution.crashapi.utils.ChatUtils;

public class CrashCommand {

	private final CrashAPIPlugin plugin;
	private final String command, descriptionPath, usagePath, aliasesPath, permission;

	public CrashCommand(CrashAPIPlugin plugin, String command, String aliasesPath, String descriptionPath, String usagePath, String permission) {
		this.plugin = plugin;
		this.command = command;
		this.aliasesPath = aliasesPath;
		this.descriptionPath = descriptionPath;
		this.usagePath = usagePath;
		this.permission = permission;
	}

	public String getCommand() {
		return command;
	}

	public String getDescriptionPath() {
		return descriptionPath;
	}

	public String getUsagePath() {
		return usagePath;
	}

	public List<String> getAliases() {
		return plugin.getLanguageFile().getStringList(aliasesPath);
	}

	public String getAliasesString() {
		StringBuilder sb = new StringBuilder();

		List<String> aliases = getAliases();
		if (aliases == null) {
			plugin.getChat().sendWarning("Error with command " + command + ": " + aliasesPath);
			return "";
		}

		for(int i = 0; i < aliases.size(); i++) {
			sb.append(aliases.get(i));
			if (i + 1 < aliases.size()) sb.append(", ");
		}

		return sb.toString();
	}

	public String getPermission() {
		return permission;
	}

	public String getFullUsage() {
		HashMap<String, Object> codes = ChatUtils.getCodes();
		String usage = "";
		usage += "/" + getCommand() + ": " + plugin.getChat().getMessage(codes, getDescriptionPath()) + "\n";
		codes.put("%usage%", plugin.getChat().getMessage(codes, getUsagePath() + ".string"));
		codes.put("%aliases%", getAliasesString());
		usage += plugin.getChat().getMessage(codes, getUsagePath() + ".main");
		return usage;
	}

	public String getUsage() {
		return plugin.getChat().getMessage(ChatUtils.getCodes(), getUsagePath() + ".string");
	}

}