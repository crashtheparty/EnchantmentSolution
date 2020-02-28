package org.ctp.enchantmentsolution.utils;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.threads.MiscRunnable;

public class MiscUtils {

	public static void updateAbilities(Player player) {
		MiscRunnable.addContagion(player);
		MiscRunnable.addExhaustion(player);
	}

}
