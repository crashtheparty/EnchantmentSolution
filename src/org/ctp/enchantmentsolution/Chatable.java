package org.ctp.enchantmentsolution;

import java.util.logging.Level;

import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.debug.ESChatUtils;

public class Chatable {

	public static ChatUtils get() {
		return EnchantmentSolution.getPlugin().getChat();
	}

	public static void sendDebug(String s) {
		ESChatUtils utils = EnchantmentSolution.getPlugin().getChat();
		utils.sendDebug(s, Level.INFO);
	}

	public static void sendDebug(String s, Level level) {
		ESChatUtils utils = EnchantmentSolution.getPlugin().getChat();
		utils.sendDebug(s, level);
	}

	public static void sendStackTrace(Exception ex) {
		ESChatUtils utils = EnchantmentSolution.getPlugin().getChat();
		utils.sendStackTrace(ex);
	}

	public static void sendStackTrace(Error ex) {
		ESChatUtils utils = EnchantmentSolution.getPlugin().getChat();
		utils.sendStackTrace(ex);
	}
}
