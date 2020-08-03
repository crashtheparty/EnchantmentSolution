package org.ctp.enchantmentsolution.utils.commands;

import org.bukkit.command.CommandSender;
import org.ctp.crashapi.commands.CrashCommandCallable;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class ESCommandCallable implements CrashCommandCallable {

	private final ESCommand command;
	private final String[] args;
	private final CommandSender sender;

	public ESCommandCallable(ESCommand command, CommandSender sender, String[] args) {
		this.command = command;
		this.sender = sender;
		this.args = args;
	}

	@Override
	public ESCommand getCommand() {
		return command;
	}

	@Override
	public String[] getArgs() {
		return args;
	}

	@Override
	public CommandSender getSender() {
		return sender;
	}

	@Override
	public Boolean call() throws Exception {
		boolean run = false;
		try {
			run = fromCommand();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!run && sender.hasPermission(command.getPermission()) && ConfigString.PRINT_USAGE.getBoolean()) CommandUtils.printHelp(sender, command.getCommand());
		return true;
	}

	@Override
	public Boolean fromCommand() {
		switch (command.getCommand()) {
			case "esanvil":
				return CommandUtils.anvil(sender, command, args);
			case "escalc":
				return CommandUtils.calc(sender, command, args);
			case "esconfig":
				return CommandUtils.config(sender, command, args);
			case "esdebug":
				return CommandUtils.debug(sender, command, args);
			case "esfix":
				return CommandUtils.fix(sender, command, args);
			case "esgrindstone":
				return CommandUtils.grindstone(sender, command, args);
			case "configlore":
				return CommandUtils.lore(sender, command, args);
			case "esreload":
				return CommandUtils.reload(sender, command, args);
			case "esreset":
				return CommandUtils.reset(sender, command, args);
			case "esbook":
				return EnchantCommandUtils.book(sender, command, args);
			case "enchant":
				return EnchantCommandUtils.enchant(sender, command, args, false);
			case "enchantunsafe":
				return EnchantCommandUtils.enchant(sender, command, args, true);
			case "info":
				return EnchantCommandUtils.enchantInfo(sender, command, args);
			case "removeenchant":
				return EnchantCommandUtils.removeEnchant(sender, command, args);
			case "esrpg":
				return CommandUtils.rpg(sender, command, args);
			case "rpgstats":
				return CommandUtils.rpgStats(sender, command, args);
			case "rpgtop":
				return CommandUtils.rpgTop(sender, command, args);
			case "rpgedit":
				return CommandUtils.rpgEdit(sender, command, args);
			case "estest":
				return CommandUtils.test(sender, command, args);
		}
		return null;
	}

}
