package org.ctp.enchantmentsolution.api;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class ApiESPlayerUtils {

	/**
	 * 
	 * @param player
	 * @param enchant
	 * @param ticks
	 */
	public static void addTimedDisableEnchant(Player player, Enchantment enchant, int ticks) {
		getPlayer(player).addTimedDisableEnchant(enchant, ticks);
	}

	public static void addTimeToDisableEnchant(Player player, Enchantment enchant, int moreTicks) {
		getPlayer(player).addTimeToDisableEnchant(enchant, moreTicks);
	}

	public static void setTimeDisableEnchant(Player player, Enchantment enchant, int ticks) {
		getPlayer(player).setTimeDisableEnchant(enchant, ticks);
	}

	public static void removeTimedDisableEnchant(Player player, Enchantment enchant) {
		getPlayer(player).removeTimedDisableEnchant(enchant);
	}

	public static void removeTimeFromDisableEnchant(Player player, Enchantment enchant, int lessTicks) {
		getPlayer(player).removeTimeFromDisableEnchant(enchant, lessTicks);
	}

	public static boolean isTimedDisableEnchant(Player player, Enchantment enchant) {
		return getPlayer(player).isTimedDisableEnchant(enchant);
	}

	public static void setDisabledEnchant(Player player, Enchantment enchant) {
		getPlayer(player).setDisabledEnchant(enchant);
	}

	public static boolean isDisabledEnchant(Player player, Enchantment enchant) {
		return getPlayer(player).isDisabledEnchant(enchant);
	}

	public static void removeDisabledEnchant(Player player, Enchantment enchant) {
		getPlayer(player).removeDisabledEnchant(enchant);
	}
	
	public static boolean isAnyDisabledEnchant(Player player, Enchantment enchant) {
		return isTimedDisableEnchant(player, enchant) || isDisabledEnchant(player, enchant);
	}
	
	private static ESPlayer getPlayer(Player player) {
		return EnchantmentSolution.getESPlayer(player);
	}
}
