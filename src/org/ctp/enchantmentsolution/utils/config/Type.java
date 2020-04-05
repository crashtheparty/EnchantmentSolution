package org.ctp.enchantmentsolution.utils.config;

import org.ctp.enchantmentsolution.utils.Configurations;

public enum Type {
	MAIN(), FISHING(), ENCHANTMENTS(), LANGUAGE(), ADVANCEMENTS(), RPG();

	Type() {}

	public Configuration getConfig() {
		switch (name()) {
			case "MAIN":
				return Configurations.getConfig();
			case "FISHING":
				return Configurations.getFishing();
			case "ENCHANTMENTS":
				return Configurations.getEnchantments();
			case "LANGUAGE":
				return Configurations.getLanguage();
			case "ADVANCEMENTS":
				return Configurations.getAdvancements();
			case "RPG":
				return Configurations.getRPG();
		}
		return Configurations.getConfig();
	}

}