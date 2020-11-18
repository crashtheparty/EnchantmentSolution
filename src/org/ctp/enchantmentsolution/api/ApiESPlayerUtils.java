package org.ctp.enchantmentsolution.api;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class ApiESPlayerUtils {

	/**
	 * 
	 * @param player
	 * @param enchant
	 * @param ticks
	 */
	public static void addTimedDisableEnchant(JavaPlugin plugin, Player player, Enchantment enchant, int ticks) {
		getPlayer(player).addTimedDisableEnchant(plugin, enchant, ticks);
	}

	public static void addTimeToDisableEnchant(JavaPlugin plugin, Player player, Enchantment enchant, int moreTicks) {
		getPlayer(player).addTimeToDisableEnchant(plugin, enchant, moreTicks);
	}

	public static void setTimeDisableEnchant(JavaPlugin plugin, Player player, Enchantment enchant, int ticks) {
		getPlayer(player).setTimeDisableEnchant(plugin, enchant, ticks);
	}

	public static void removeTimedDisableEnchant(JavaPlugin plugin, Player player, Enchantment enchant) {
		getPlayer(player).removeTimedDisableEnchant(plugin, enchant);
	}

	public static void removeTimeFromDisableEnchant(JavaPlugin plugin, Player player, Enchantment enchant, int lessTicks) {
		getPlayer(player).removeTimeFromDisableEnchant(plugin, enchant, lessTicks);
	}

	public static boolean isTimedDisableEnchant(JavaPlugin plugin, Player player, Enchantment enchant) {
		return getPlayer(player).isTimedDisableEnchant(plugin, enchant);
	}

	public static void setDisabledEnchant(JavaPlugin plugin, Player player, Enchantment enchant) {
		getPlayer(player).setDisabledEnchant(plugin, enchant);
	}

	public static boolean isDisabledEnchant(JavaPlugin plugin, Player player, Enchantment enchant) {
		return getPlayer(player).isDisabledEnchant(plugin, enchant);
	}

	public static void removeDisabledEnchant(JavaPlugin plugin, Player player, Enchantment enchant) {
		getPlayer(player).removeDisabledEnchant(plugin, enchant);
	}

	public static boolean isAnyDisabledEnchant(Player player, Enchantment enchant) {
		return getPlayer(player).hasTimedDisable(player, enchant) || getPlayer(player).hasDisabled(player, enchant);
	}

	private static ESPlayer getPlayer(Player player) {
		return EnchantmentSolution.getESPlayer(player);
	}
}
