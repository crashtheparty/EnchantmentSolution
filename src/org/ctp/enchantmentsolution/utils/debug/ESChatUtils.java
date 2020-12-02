package org.ctp.enchantmentsolution.utils.debug;

import java.util.logging.Level;

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
	public void sendToConsole(Level level, String message) {
		super.sendToConsole(level, message);
		DebugUtils.addDebug(message, level);
	}

	public void sendDebug(String message) {
		if (ConfigString.DEBUG.getBoolean()) super.sendToConsole(getPlugin(), Level.INFO, message);
		DebugUtils.addDebug(message, Level.INFO);
	}
	
}
