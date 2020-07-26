package org.ctp.enchantmentsolution.utils.commands;

import java.util.HashMap;

import org.ctp.crashapi.commands.CrashCommand;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.EnchantmentSolution;

public class ESCommand extends CrashCommand {

	public ESCommand(String command, String aliasesPath, String descriptionPath, String usagePath, String permission) {
		super(EnchantmentSolution.getPlugin(), command, aliasesPath, descriptionPath, usagePath, permission);
	}

	@Override
	public String getFullUsage() {
		HashMap<String, Object> codes = ChatUtils.getCodes();
		String usage = "";
		usage += "/es " + getCommand() + ": " + Chatable.get().getMessage(codes, getDescriptionPath()) + "\n";
		codes.put("%usage%", Chatable.get().getMessage(codes, getUsagePath() + ".string"));
		codes.put("%aliases%", getAliasesString());
		usage += Chatable.get().getMessage(codes, getUsagePath() + ".main");
		return usage;
	}

}
