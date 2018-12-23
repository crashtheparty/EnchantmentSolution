package org.ctp.enchantmentsolution.database;

import java.util.logging.Level;

import org.ctp.enchantmentsolution.EnchantmentSolution;

public class Error {
	public static void execute(EnchantmentSolution plugin, Exception ex) {
		plugin.getLogger().log(Level.SEVERE,
				"Couldn't execute MySQL statement: ", ex);
	}

	public static void close(EnchantmentSolution plugin, Exception ex) {
		plugin.getLogger().log(Level.SEVERE,
				"Failed to close MySQL connection: ", ex);
	}
}