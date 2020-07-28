package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.inventory.LegacyAnvil;

public class AnvilUtils {

	private static List<Player> OPEN_LEGACY = new ArrayList<Player>();

	public static void addLegacyAnvil(Player player) {
		OPEN_LEGACY.add(player);
	}

	public static boolean hasLegacyAnvil(Player player) {
		for(Player p: OPEN_LEGACY)
			if (p.equals(player)) return true;
		return false;
	}

	public static void removeLegacyAnvil(LegacyAnvil anvil) {
		OPEN_LEGACY.remove(anvil.getPlayer());
	}
}
