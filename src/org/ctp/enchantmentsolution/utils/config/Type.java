package org.ctp.enchantmentsolution.utils.config;

import org.ctp.enchantmentsolution.utils.Configurations;

public enum Type{
	MAIN(), FISHING(), ENCHANTMENTS(), LANGUAGE();

	Type(){
	}

	public Configuration getConfig() {
		switch(name()) {
			case "MAIN":
				return Configurations.getConfig();
			case "FISHING":
				return Configurations.getFishing();
			case "ENCHANTMENTS":
				return Configurations.getEnchantments();
			case "LANGUAGE":
				return Configurations.getLanguage();
		}
		return Configurations.getConfig();
	}

}