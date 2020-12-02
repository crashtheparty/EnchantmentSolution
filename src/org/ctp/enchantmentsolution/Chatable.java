package org.ctp.enchantmentsolution;

import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.debug.ESChatUtils;

public class Chatable {

	public static ChatUtils get() {
		return EnchantmentSolution.getPlugin().getChat();
	}
	
	public static void sendDebug(String s) {
		ESChatUtils utils = EnchantmentSolution.getPlugin().getChat();
		utils.sendDebug(s);
	}
}
