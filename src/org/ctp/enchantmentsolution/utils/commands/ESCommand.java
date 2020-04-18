package org.ctp.enchantmentsolution.utils.commands;

import java.util.HashMap;
import java.util.List;

import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.Configurations;

public class ESCommand {

	private final String command, descriptionPath, usagePath, aliasesPath, permission;

	public ESCommand(String command, String aliasesPath, String descriptionPath, String usagePath, String permission) {
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
		return Configurations.getLanguage().getStringList(aliasesPath);
	}

	public String getAliasesString() {
		StringBuilder sb = new StringBuilder();

		List<String> aliases = getAliases();
		if (aliases == null) {
			ChatUtils.sendWarning("Error with command " + command + ": " + aliasesPath);
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

	public String getUsage() {
		HashMap<String, Object> codes = ChatUtils.getCodes();
		String usage = "";
		usage += "/es " + getCommand() + ": " + ChatUtils.getMessage(codes, getDescriptionPath()) + "\n";
		codes.put("%usage%", ChatUtils.getMessage(codes, getUsagePath() + ".string"));
		codes.put("%aliases%", getAliasesString());
		usage += ChatUtils.getMessage(codes, getUsagePath() + ".main");
		return usage;
	}

}
