package org.ctp.enchantmentsolution.utils.config;

import org.ctp.crashapi.config.Configuration;
import org.ctp.enchantmentsolution.utils.Configurations;

public enum Type {
	MAIN(), FISHING(), ENCHANTMENTS(), LANGUAGE(), ADVANCEMENTS(), RPG(), MINIGAME(), HARD_MODE();

	Type() {}

	public Configuration getConfig() {
		Configurations c = Configurations.getConfigurations();
		switch (name()) {
			case "MAIN":
				return c.getConfig();
			case "FISHING":
				return c.getFishing();
			case "ENCHANTMENTS":
				return c.getEnchantments();
			case "LANGUAGE":
				return c.getLanguage();
			case "ADVANCEMENTS":
				return c.getAdvancements();
			case "RPG":
				return c.getRPG();
			case "MINIGAME":
				return c.getMinigames();
			case "HARD_MODE":
				return c.getHardMode();
		}
		return c.getConfig();
	}

}