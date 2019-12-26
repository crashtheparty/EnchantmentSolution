package org.ctp.enchantmentsolution.utils;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.compatibility.Metrics;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class MetricsUtils {

	public static void init() {
		Metrics metrics = new Metrics(EnchantmentSolution.getPlugin());

		metrics.addCustomChart(new Metrics.SingleLineChart("level_fifty", () -> {
			if (ConfigString.LEVEL_FIFTY.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("level_thirty", () -> {
			if (!ConfigString.LEVEL_FIFTY.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("advanced_file", () -> {
			if (ConfigString.ADVANCED_OPTIONS.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("basic_file", () -> {
			if (!ConfigString.ADVANCED_OPTIONS.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("enhanced_gui", () -> {
			if (ConfigString.CUSTOM_TABLE.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("vanilla_gui", () -> {
			if (!ConfigString.CUSTOM_TABLE.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("enhanced_anvil", () -> {
			if (ConfigString.CUSTOM_ANVIL.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("vanilla_anvil", () -> {
			if (!ConfigString.CUSTOM_ANVIL.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("enhanced_grindstone", () -> {
			if (ConfigString.CUSTOM_GRINDSTONE.getBoolean()) return 1;
			return 0;
		}));

		metrics.addCustomChart(new Metrics.SingleLineChart("vanilla_grindstone", () -> {
			if (!ConfigString.CUSTOM_GRINDSTONE.getBoolean()) return 1;
			return 0;
		}));
	}
}
