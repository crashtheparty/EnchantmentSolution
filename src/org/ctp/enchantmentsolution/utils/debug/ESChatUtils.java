package org.ctp.enchantmentsolution.utils.debug;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class ESChatUtils extends ChatUtils {

	public static ESChatUtils getESChat() {
		for(ChatUtils cu: utils)
			if (cu instanceof ESChatUtils) return (ESChatUtils) cu;
		ESChatUtils cu = new ESChatUtils();
		utils.add(cu);
		return cu;
	}

	private ESChatUtils() {
		super(EnchantmentSolution.getPlugin());
	}

	@Override
	public void sendToConsole(JavaPlugin plugin, Level level, String message) {
		super.sendToConsole(plugin, level, message);
		DebugUtils.addDebug(plugin, message, level);
	}

	public void sendDebug(String message, Level level) {
		if (ConfigString.DEBUG.getBoolean()) super.sendToConsole(getPlugin(), level, message);
		DebugUtils.addDebug(EnchantmentSolution.getPlugin(), message, level);
	}

	public void sendStackTrace(Exception ex) {
		StackTraceElement[] exceptionStack = ex.getStackTrace();
		String exceptionMessage = ex.toString();

		sendDebug(exceptionMessage, Level.SEVERE);
		for(StackTraceElement element: exceptionStack)
			sendDebug(" at " + element.toString(), Level.SEVERE);

		Throwable cause = ex.getCause();
		while (cause != null) {
			StackTraceElement[] causeStack = cause.getStackTrace();
			String causeMessage = cause.toString();
			sendDebug(causeMessage, Level.SEVERE);
			for(StackTraceElement element: causeStack)
				sendDebug(" at " + element.toString(), Level.SEVERE);
			cause = cause.getCause();
		}
	}

	public void sendStackTrace(Error ex) {
		StackTraceElement[] exceptionStack = ex.getStackTrace();
		String exceptionMessage = ex.toString();

		sendDebug(exceptionMessage, Level.SEVERE);
		for(StackTraceElement element: exceptionStack)
			sendDebug(" at " + element.toString(), Level.SEVERE);

		Throwable cause = ex.getCause();
		while (cause != null) {
			StackTraceElement[] causeStack = cause.getStackTrace();
			String causeMessage = cause.toString();
			sendDebug(causeMessage, Level.SEVERE);
			for(StackTraceElement element: causeStack)
				sendDebug(" at " + element.toString(), Level.SEVERE);
			cause = cause.getCause();
		}
	}

}
