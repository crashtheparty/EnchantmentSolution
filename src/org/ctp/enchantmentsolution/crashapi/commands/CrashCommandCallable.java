package org.ctp.enchantmentsolution.crashapi.commands;

import java.util.concurrent.Callable;

import org.bukkit.command.CommandSender;

public interface CrashCommandCallable extends Callable<Boolean> {

	public CrashCommand getCommand();

	public String[] getArgs();

	public CommandSender getSender();

	@Override
	public Boolean call() throws Exception;

	public Boolean fromCommand();

}
