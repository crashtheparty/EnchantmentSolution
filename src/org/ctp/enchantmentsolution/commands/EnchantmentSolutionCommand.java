package org.ctp.enchantmentsolution.commands;

import java.util.*;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.commands.CommandUtils;
import org.ctp.enchantmentsolution.utils.commands.ESCommand;
import org.ctp.enchantmentsolution.utils.commands.EnchantCommandUtils;

public class EnchantmentSolutionCommand implements CommandExecutor, TabCompleter {

	private final List<ESCommand> commands;
	private final ESCommand anvil = new ESCommand("anvil", "commands.aliases.anvil", "commands.descriptions.anvil", "commands.usage.anvil", "enchantmentsolution.command.anvil");
	private final ESCommand calc = new ESCommand("calc", "commands.aliases.calc", "commands.descriptions.calc", "commands.usage.calc", "enchantmentsolution.command.calc");
	private final ESCommand config = new ESCommand("config", "commands.aliases.config", "commands.descriptions.config", "commands.usage.config", "enchantmentsolution.command.config");
	private final ESCommand debug = new ESCommand("debug", "commands.aliases.debug", "commands.descriptions.debug", "commands.usage.debug", "enchantmentsolution.command.debug");
	private final ESCommand fix = new ESCommand("fix", "commands.aliases.fix", "commands.descriptions.fix", "commands.usage.fix", "enchantmentsolution.command.fix");
	private final ESCommand grindstone = new ESCommand("grindstone", "commands.aliases.grindstone", "commands.descriptions.grindstone", "commands.usage.grindstone", "enchantmentsolution.command.grindstone");
	private final ESCommand help = new ESCommand("help", "commands.aliases.help", "commands.descriptions.help", "commands.usage.help", "enchantmentsolution.command.help");
	private final ESCommand lore = new ESCommand("lore", "commands.aliases.lore", "commands.descriptions.lore", "commands.usage.lore", "enchantmentsolution.command.lore");
	private final ESCommand reload = new ESCommand("reload", "commands.aliases.reload", "commands.descriptions.reload", "commands.usage.reload", "enchantmentsolution.command.reload");
	private final ESCommand reset = new ESCommand("reset", "commands.aliases.reset", "commands.descriptions.reset", "commands.usage.reset", "enchantmentsolution.command.reset");
	private final ESCommand book = new ESCommand("book", "commands.aliases.book", "commands.descriptions.book", "commands.usage.book", "enchantmentsolution.commands.book");
	private final ESCommand enchant = new ESCommand("enchant", "commands.aliases.enchant", "commands.descriptions.enchant", "commands.usage.enchant", "enchantmentsolution.command.enchant");
	private final ESCommand enchantUnsafe = new ESCommand("enchantunsafe", "commands.aliases.enchantunsafe", "commands.descriptions.enchantunsafe", "commands.usage.enchantunsafe", "enchantmentsolution.command.enchantunsafe");
	private final ESCommand enchantInfo = new ESCommand("info", "commands.aliases.info", "commands.descriptions.info", "commands.usage.info", "enchantmentsolution.command.info");
	private final ESCommand removeEnchant = new ESCommand("removeenchant", "commands.aliases.removeenchant", "commands.descriptions.removeenchant", "commands.usage.removeenchant", "enchantmentsolution.command.removeenchant");

	public EnchantmentSolutionCommand() {
		commands = new ArrayList<ESCommand>();
		commands.add(anvil);
		commands.add(calc);
		commands.add(config);
		commands.add(debug);
		commands.add(fix);
		commands.add(grindstone);
		commands.add(help);
		commands.add(lore);
		commands.add(reload);
		commands.add(reset);
		commands.add(book);
		commands.add(enchant);
		commands.add(enchantInfo);
		commands.add(enchantUnsafe);
		commands.add(removeEnchant);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0 || (args.length == 1 && args[0].equals("help"))) return printHelp(sender);

		for (ESCommand c : commands) {
			String[] check;
			if(containsCommand(c, label)) {
				check = new String[args.length + 1];
				check[0] = label;
				for(int i = 0; i < args.length; i++)
					check[i + 1] = args[i];
				args = check;
				break;
			}
		}
		
		if (containsCommand(help, args[0]) && args.length > 0) return printHelp(sender, args[1]);
		if (containsCommand(anvil, args[0])) return CommandUtils.anvil(sender, anvil, args);
		if (containsCommand(calc, args[0])) return CommandUtils.calc(sender, calc, args);
		if (containsCommand(config, args[0])) return CommandUtils.config(sender, config, args);
		if (containsCommand(debug, args[0])) return CommandUtils.debug(sender, debug, args);
		if (containsCommand(fix, args[0])) return CommandUtils.fix(sender, fix, args);
		if (containsCommand(grindstone, args[0])) return CommandUtils.grindstone(sender, grindstone, args);
		if (containsCommand(lore, args[0])) return CommandUtils.lore(sender, lore, args);
		if (containsCommand(reload, args[0])) return CommandUtils.reload(sender, reload, args);
		if (containsCommand(reset, args[0])) return CommandUtils.reset(sender, reset, args);
		if (containsCommand(book, args[0])) return EnchantCommandUtils.book(sender, book, args);
		if (containsCommand(enchant, args[0])) return EnchantCommandUtils.enchant(sender, enchant, args, false);
		if (containsCommand(enchantInfo, args[0])) return EnchantCommandUtils.enchantInfo(sender, enchantInfo, args);
		if (containsCommand(enchantUnsafe, args[0])) return EnchantCommandUtils.enchant(sender, enchantUnsafe, args, true);
		if (containsCommand(removeEnchant, args[0])) return EnchantCommandUtils.removeEnchant(sender, removeEnchant, args);

		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%command%", args[0]);
		ChatUtils.sendMessage(sender, player, ChatUtils.getMessage(codes, "commands.no-command"), Level.WARNING);
		return true;
	}

	private boolean containsCommand(ESCommand details, String s) {
		return s.equals(details.getCommand()) || details.getAliases().contains(s);
	}

	public boolean printHelp(CommandSender sender, String label) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		for(ESCommand command: commands)
			if (sender.hasPermission(command.getPermission()) && containsCommand(command, label)) {
				ChatUtils.sendMessage(sender, player, StringUtils.decodeString("\n" + command.getUsage()), Level.INFO);
				return true;
			}
		return printHelp(sender);
	}

	public boolean printHelp(CommandSender sender) {
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		List<String> strings = new ArrayList<String>();
		for(ESCommand command: commands)
			if (sender.hasPermission(command.getPermission())) strings.add(command.getUsage());

		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < strings.size(); i++)
			sb.append("\n" + strings.get(i));
		ChatUtils.sendMessage(sender, player, StringUtils.decodeString(sb.toString()), Level.INFO);

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> all = new ArrayList<String>();

		for (ESCommand c : commands) {
			String[] check;
			if(containsCommand(c, label)) {
				check = new String[args.length + 1];
				check[0] = label;
				for(int i = 0; i < args.length; i++)
					check[i + 1] = args[i];
				args = check;
				break;
			}
		}
		int i = args.length - 1;

		if (i == 0) all.addAll(help(sender, args[i]));
		if (i > 0 && noArgCommands(0).contains(args[0])) return all;
		if (i == 1 && (containsCommand(help, args[0])) && sender.hasPermission(help.getPermission())) all.addAll(help(sender, args[i]));
		if (i == 1 && (containsCommand(lore, args[0])) && sender.hasPermission(lore.getPermission())) all.addAll(lore(args[i]));
		if (i == 2 && (containsCommand(lore, args[0])) && sender.hasPermission(lore.getPermission())) all.addAll(lore(args[i], args[1]));
		if (i == 3 && (containsCommand(lore, args[0])) && sender.hasPermission(lore.getPermission())) all.addAll(lore(args[i], args[1], args[2]));
		if (i == 1 && (containsCommand(book, args[0])) && sender.hasPermission(book.getPermission())) all.addAll(players(args[i]));
		if (i == 2 && (containsCommand(book, args[0])) && sender.hasPermission(book.getPermission())) all.addAll(book(args[i]));
		if (i == 3 && (containsCommand(book, args[0])) && sender.hasPermission(book.getPermission())) all.addAll(book(args[i], args[2]));
		if (i == 4 && (containsCommand(book, args[0])) && sender.hasPermission(book.getPermission())) all.addAll(level());
		if (i == 1 && (containsCommand(enchant, args[0])) && sender.hasPermission(enchant.getPermission())) all.addAll(enchant(args[i]));
		if (i == 2 && (containsCommand(enchant, args[0])) && sender.hasPermission(enchant.getPermission())) all.addAll(level());
		if (i == 3 && (containsCommand(enchant, args[0])) && sender.hasPermission(enchant.getPermission())) all.addAll(players(args[i]));
		if (i == 4 && (containsCommand(enchant, args[0])) && sender.hasPermission(enchant.getPermission())) all.addAll(slots(args[i]));
		if (i == 1 && (containsCommand(enchantUnsafe, args[0])) && sender.hasPermission(enchantUnsafe.getPermission())) all.addAll(enchant(args[i]));
		if (i == 2 && (containsCommand(enchantUnsafe, args[0])) && sender.hasPermission(enchantUnsafe.getPermission())) all.addAll(level());
		if (i == 3 && (containsCommand(enchantUnsafe, args[0])) && sender.hasPermission(enchantUnsafe.getPermission())) all.addAll(players(args[i]));
		if (i == 4 && (containsCommand(enchantUnsafe, args[0])) && sender.hasPermission(enchantUnsafe.getPermission())) all.addAll(slots(args[i]));
		if (i == 1 && (containsCommand(removeEnchant, args[0])) && sender.hasPermission(removeEnchant.getPermission())) all.addAll(enchant(args[i]));
		if (i == 2 && (containsCommand(removeEnchant, args[0])) && sender.hasPermission(removeEnchant.getPermission())) all.addAll(players(args[i]));
		if (i == 3 && (containsCommand(removeEnchant, args[0])) && sender.hasPermission(removeEnchant.getPermission())) all.addAll(slots(args[i]));
		if (i == 1 && (containsCommand(enchantInfo, args[0])) && sender.hasPermission(enchantInfo.getPermission())) all.addAll(enchant(args[i]));

		return all;
	}

	private List<String> removeComplete(List<String> strings, String startsWith) {
		Iterator<String> iter = strings.iterator();
		while (iter.hasNext()) {
			String entry = iter.next();
			if (!entry.startsWith(startsWith)) iter.remove();
		}
		return strings;
	}

	private List<String> enchant(String startsWith) {
		List<String> strings = new ArrayList<String>();
		strings.addAll(RegisterEnchantments.getEnchantmentNames());
		return removeComplete(strings, startsWith);
	}

	private List<String> level() {
		List<String> strings = new ArrayList<String>();
		strings.add("[<int>]");
		return strings;
	}

	private List<String> players(String startsWith) {
		List<String> strings = new ArrayList<String>();
		for(Player player: Bukkit.getOnlinePlayers())
			strings.add(player.getName());
		return strings;
	}

	private List<String> book(String startsWith) {
		List<String> strings = new ArrayList<String>();
		strings.addAll(RegisterEnchantments.getEnchantmentNames());
		strings.add("RandomEnchant");
		strings.add("RandomMultiEnchant");
		return removeComplete(strings, startsWith);
	}

	private List<String> book(String startsWith, String type) {
		List<String> strings = new ArrayList<String>();
		if (type.equals("RandomEnchant") || type.equals("RandomMultiEnchant")) {
			strings.add("true");
			strings.add("false");
		} else if (RegisterEnchantments.getEnchantmentNames().contains(type)) {
			strings.add("[<int>]");
			return strings;
		}
		return removeComplete(strings, startsWith);
	}

	private List<String> lore(String startsWith) {
		List<String> strings = new ArrayList<String>();
		strings.addAll(Arrays.asList("enchant", "enchantment", "string"));
		return removeComplete(strings, startsWith);
	}

	private List<String> lore(String startsWith, String type) {
		List<String> strings = new ArrayList<String>();
		switch (type) {
			case "enchant":
			case "enchantment":
				strings.addAll(RegisterEnchantments.getEnchantmentNames());
				break;
			case "string":
			default:
				break;
		}
		return removeComplete(strings, startsWith);
	}

	private List<String> lore(String startsWith, String type, String enchant) {
		List<String> strings = new ArrayList<String>();
		switch (type) {
			case "enchant":
			case "enchantment":
				if (RegisterEnchantments.getEnchantmentNames().contains(enchant)) {
					strings.add("[<int>]");
					return strings;
				}
			case "string":
			default:
				break;
		}
		return removeComplete(strings, startsWith);
	}

	private List<String> slots(String startsWith) {
		List<String> strings = new ArrayList<String>();
		for(int i = 0; i < 45; i++)
			strings.add("" + i);
		return removeComplete(strings, startsWith);
	}

	private List<String> help(CommandSender sender, String startsWith) {
		List<String> strings = new ArrayList<String>();
		for(ESCommand command: commands)
			if (sender.hasPermission(command.getPermission())) {
				strings.add(command.getCommand());
				strings.addAll(command.getAliases());
			}
		return removeComplete(strings, startsWith);
	}

	private List<String> noArgCommands(int i) {
		List<String> strings = new ArrayList<String>();
		if (i == 0) {
			strings.add(anvil.getCommand());
			strings.addAll(anvil.getAliases());
			strings.add(calc.getCommand());
			strings.addAll(calc.getAliases());
			strings.add(config.getCommand());
			strings.addAll(config.getAliases());
			strings.add(debug.getCommand());
			strings.addAll(debug.getAliases());
			strings.add(grindstone.getCommand());
			strings.addAll(grindstone.getAliases());
			strings.add(reload.getCommand());
			strings.addAll(reload.getAliases());
			strings.add(reset.getCommand());
			strings.addAll(reset.getAliases());
		}
		return strings;
	}
}
