package org.ctp.enchantmentsolution.commands;

import java.util.*;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.commands.CommandUtils;
import org.ctp.enchantmentsolution.utils.commands.ESCommand;
import org.ctp.enchantmentsolution.utils.commands.ESCommandCallable;

public class EnchantmentSolutionCommand implements CommandExecutor, TabCompleter {

	private static List<ESCommand> commands;
	private final ESCommand anvil = new ESCommand("esanvil", "commands.aliases.esanvil", "commands.descriptions.esanvil", "commands.usage.esanvil", "enchantmentsolution.command.anvil");
	private final ESCommand calc = new ESCommand("escalc", "commands.aliases.escalc", "commands.descriptions.escalc", "commands.usage.escalc", "enchantmentsolution.command.calc");
	private final ESCommand config = new ESCommand("esconfig", "commands.aliases.esconfig", "commands.descriptions.esconfig", "commands.usage.esconfig", "enchantmentsolution.command.config");
	private final ESCommand debug = new ESCommand("esdebug", "commands.aliases.esdebug", "commands.descriptions.esdebug", "commands.usage.esdebug", "enchantmentsolution.command.debug");
	private final ESCommand fix = new ESCommand("esfix", "commands.aliases.esfix", "commands.descriptions.esfix", "commands.usage.esfix", "enchantmentsolution.command.fix");
	private final ESCommand grindstone = new ESCommand("esgrindstone", "commands.aliases.esgrindstone", "commands.descriptions.esgrindstone", "commands.usage.esgrindstone", "enchantmentsolution.command.grindstone");
	private final ESCommand help = new ESCommand("eshelp", "commands.aliases.eshelp", "commands.descriptions.eshelp", "commands.usage.eshelp", "enchantmentsolution.command.help");
	private final ESCommand lore = new ESCommand("configlore", "commands.aliases.configlore", "commands.descriptions.configlore", "commands.usage.configlore", "enchantmentsolution.command.lore");
	private final ESCommand reload = new ESCommand("esreload", "commands.aliases.esreload", "commands.descriptions.esreload", "commands.usage.esreload", "enchantmentsolution.command.reload");
	private final ESCommand reset = new ESCommand("esreset", "commands.aliases.esreset", "commands.descriptions.esreset", "commands.usage.esreset", "enchantmentsolution.command.reset");
	private final ESCommand test = new ESCommand("estest", "commands.aliases.estest", "commands.descriptions.estest", "commands.usage.estest", "enchantmentsolution.command.test");
	private final ESCommand book = new ESCommand("esbook", "commands.aliases.esbook", "commands.descriptions.esbook", "commands.usage.esbook", "enchantmentsolution.command.book");
	private final ESCommand enchant = new ESCommand("enchant", "commands.aliases.enchant", "commands.descriptions.enchant", "commands.usage.enchant", "enchantmentsolution.command.enchant");
	private final ESCommand enchantUnsafe = new ESCommand("enchantunsafe", "commands.aliases.enchantunsafe", "commands.descriptions.enchantunsafe", "commands.usage.enchantunsafe", "enchantmentsolution.command.enchantunsafe");
	private final ESCommand enchantInfo = new ESCommand("info", "commands.aliases.info", "commands.descriptions.info", "commands.usage.info", "enchantmentsolution.command.info");
	private final ESCommand removeEnchant = new ESCommand("removeenchant", "commands.aliases.removeenchant", "commands.descriptions.removeenchant", "commands.usage.removeenchant", "enchantmentsolution.command.removeenchant");
	private final ESCommand rpg = new ESCommand("esrpg", "commands.aliases.esrpg", "commands.descriptions.esrpg", "commands.usage.esrpg", "enchantmentsolution.command.rpg");
	private final ESCommand rpgStats = new ESCommand("rpgstats", "commands.aliases.rpgstats", "commands.descriptions.rpgstats", "commands.usage.rpgstats", "enchantmentsolution.command.rpgstats");
	private final ESCommand rpgTop = new ESCommand("rpgtop", "commands.aliases.rpgtop", "commands.descriptions.rpgtop", "commands.usage.rpgtop", "enchantmentsolution.command.rpgtop");
	private final ESCommand rpgEdit = new ESCommand("rpgedit", "commands.aliases.rpgedit", "commands.descriptions.rpgedit", "commands.usage.rpgedit", "enchantmentsolution.command.rpgedit");

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
		commands.add(rpg);
		commands.add(rpgStats);
		commands.add(rpgTop);
		commands.add(rpgEdit);
		commands.add(test);
		commands.add(book);
		commands.add(enchant);
		commands.add(enchantInfo);
		commands.add(enchantUnsafe);
		commands.add(removeEnchant);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		label = label.substring(label.indexOf(':') + 1);
		for(ESCommand c: commands) {
			String[] check;
			if (containsCommand(c, label)) {
				check = new String[args.length + 1];
				check[0] = label;
				for(int i = 0; i < args.length; i++)
					check[i + 1] = args[i];
				args = check;
				break;
			}
		}
		if (args.length == 0 || args.length == 1 && containsCommand(help, args[0])) return CommandUtils.printHelp(sender, 1);
		else if (args.length == 2 && containsCommand(help, args[0])) {
			int page = 0;
			try {
				page = Integer.parseInt(args[1]);
			} catch (NumberFormatException ex) {

			}
			if (page > 0) return CommandUtils.printHelp(sender, page);
			else
				return CommandUtils.printHelp(sender, args[1]);
		}
		final String[] finalArgs = args;
		Player player = null;
		if (sender instanceof Player) player = (Player) sender;
		for(ESCommand command: commands) {
			if (command == help) continue;
			if (containsCommand(command, args[0])) try {
				return new ESCommandCallable(command, sender, finalArgs).call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%command%", args[0]);
		Chatable.get().sendMessage(sender, player, Chatable.get().getMessage(codes, "commands.no-command"), Level.WARNING);
		return true;
	}

	public static boolean containsCommand(ESCommand details, String s) {
		if (details == null) return false;
		return details.getCommand() != null && details.getCommand().equals(s) || details.getAliases() != null && details.getAliases().contains(s);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> all = new ArrayList<String>();

		label = label.substring(label.indexOf(':') + 1);
		for(ESCommand c: commands) {
			String[] check;
			if (containsCommand(c, label)) {
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
		if (i == 1 && containsCommand(help, args[0]) && sender.hasPermission(help.getPermission())) all.addAll(help(sender, args[i]));
		if (i == 1 && containsCommand(lore, args[0]) && sender.hasPermission(lore.getPermission())) all.addAll(lore(args[i]));
		if (i == 2 && containsCommand(lore, args[0]) && sender.hasPermission(lore.getPermission())) all.addAll(lore(args[i], args[1]));
		if (i == 3 && containsCommand(lore, args[0]) && sender.hasPermission(lore.getPermission())) all.addAll(lore(args[i], args[1], args[2]));
		if (i == 1 && containsCommand(book, args[0]) && sender.hasPermission(book.getPermission())) all.addAll(players(args[i]));
		if (i == 2 && containsCommand(book, args[0]) && sender.hasPermission(book.getPermission())) all.addAll(book(args[i]));
		if (i == 3 && containsCommand(book, args[0]) && sender.hasPermission(book.getPermission())) all.addAll(book(args[i], args[2]));
		if (i == 4 && containsCommand(book, args[0]) && sender.hasPermission(book.getPermission())) all.addAll(level());
		if (i == 1 && containsCommand(enchant, args[0]) && sender.hasPermission(enchant.getPermission())) all.addAll(enchant(args[i]));
		if (i == 2 && containsCommand(enchant, args[0]) && sender.hasPermission(enchant.getPermission())) all.addAll(level());
		if (i == 3 && containsCommand(enchant, args[0]) && sender.hasPermission(enchant.getPermission())) all.addAll(players(args[i]));
		if (i == 4 && containsCommand(enchant, args[0]) && sender.hasPermission(enchant.getPermission())) all.addAll(slots(args[i]));
		if (i == 1 && containsCommand(enchantUnsafe, args[0]) && sender.hasPermission(enchantUnsafe.getPermission())) all.addAll(enchant(args[i]));
		if (i == 2 && containsCommand(enchantUnsafe, args[0]) && sender.hasPermission(enchantUnsafe.getPermission())) all.addAll(level());
		if (i == 3 && containsCommand(enchantUnsafe, args[0]) && sender.hasPermission(enchantUnsafe.getPermission())) all.addAll(players(args[i]));
		if (i == 4 && containsCommand(enchantUnsafe, args[0]) && sender.hasPermission(enchantUnsafe.getPermission())) all.addAll(slots(args[i]));
		if (i == 1 && containsCommand(removeEnchant, args[0]) && sender.hasPermission(removeEnchant.getPermission())) all.addAll(enchantAll(args[i]));
		if (i == 2 && containsCommand(removeEnchant, args[0]) && sender.hasPermission(removeEnchant.getPermission())) all.addAll(players(args[i]));
		if (i == 3 && containsCommand(removeEnchant, args[0]) && sender.hasPermission(removeEnchant.getPermission())) all.addAll(slots(args[i]));
		if (i == 1 && containsCommand(enchantInfo, args[0]) && sender.hasPermission(enchantInfo.getPermission())) all.addAll(enchant(args[i]));
		if (i == 1 && containsCommand(rpgStats, args[0]) && sender.hasPermission(rpgStats.getPermission())) all.addAll(players(args[i], sender, rpgStats));
		if (i == 1 && containsCommand(rpgTop, args[0]) && sender.hasPermission(rpgTop.getPermission())) all.addAll(level());
		if (i == 1 && containsCommand(rpgEdit, args[0]) && sender.hasPermission(rpgEdit.getPermission())) all.addAll(players(args[i]));
		if (i == 2 && containsCommand(rpgEdit, args[0]) && sender.hasPermission(rpgEdit.getPermission())) all.addAll(rpgEdit(args[i]));
		if (i == 3 && containsCommand(rpgEdit, args[0]) && sender.hasPermission(rpgEdit.getPermission())) all.addAll(rpgEdit(args[i], args[2]));
		if (i == 4 && containsCommand(rpgEdit, args[0]) && sender.hasPermission(rpgEdit.getPermission())) all.addAll(rpgEdit(args[i], args[2], args[3]));
		if (i == 1 && containsCommand(test, args[0]) && sender.hasPermission(test.getPermission())) all.addAll(test(args[i]));

		return all;
	}

	private List<String> removeComplete(List<String> strings, String startsWith) {
		Iterator<String> iter = strings.iterator();
		while (iter.hasNext()) {
			String entry = iter.next();
			boolean remove = true;
			if (entry.startsWith(startsWith)) remove = false;// is fine
			if (entry.indexOf('_') > -1) {
				String split = entry.substring(entry.indexOf('_') + 1);
				while (split.length() > 0) {
					if (split.startsWith(startsWith)) remove = false;// is fine
					if (split.indexOf('_') > -1) split = split.substring(split.indexOf('_') + 1);
					else
						split = "";
				}
			}
			if (remove) iter.remove();
		}
		return strings;
	}

	private List<String> rpgEdit(String startsWith) {
		List<String> strings = new ArrayList<String>();
		strings.addAll(Arrays.asList("add_level", "set_level", "remove_level", "add_experience", "set_experience", "remove_experience", "set_enchantment_level"));
		return removeComplete(strings, startsWith);
	}

	private List<String> rpgEdit(String startsWith, String editType) {
		List<String> strings = new ArrayList<String>();
		switch (editType) {
			case "add_level":
			case "set_level":
			case "remove_level":
				return level();
			case "add_experience":
			case "set_experience":
			case "remove_experience":
				strings.add("[<double>]");
				return strings;
			case "set_enchantment_level":
				return enchant(startsWith);
		}
		return removeComplete(strings, startsWith);
	}

	private List<String> rpgEdit(String startsWith, String editType, String enchantment) {
		if (editType.equals("set_enchantment_level")) return level();
		return new ArrayList<String>();
	}

	private List<String> enchant(String startsWith) {
		List<String> strings = new ArrayList<String>();
		strings.addAll(RegisterEnchantments.getEnchantmentNames());
		return removeComplete(strings, startsWith);
	}

	private List<String> enchantAll(String startsWith) {
		List<String> strings = new ArrayList<String>();
		strings.addAll(RegisterEnchantments.getEnchantmentNames());
		strings.add("All");
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
		return removeComplete(strings, startsWith);
	}

	private List<String> players(String startsWith, CommandSender sender, ESCommand command) {
		if (sender.hasPermission(command.getPermission() + ".others")) return players(startsWith);
		return new ArrayList<String>();
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
			strings.add(rpg.getCommand());
			strings.addAll(rpg.getAliases());
		}
		return strings;
	}

	private List<String> test(String startsWith) {
		List<String> strings = new ArrayList<String>();
		strings.add("item");
		return removeComplete(strings, startsWith);
	}

	public static List<ESCommand> getCommands() {
		return commands;
	}
}
