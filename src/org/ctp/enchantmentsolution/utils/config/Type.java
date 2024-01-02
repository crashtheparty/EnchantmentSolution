package org.ctp.enchantmentsolution.utils.config;

import org.ctp.crashapi.config.Configuration;
import org.ctp.enchantmentsolution.utils.Configurations;

public enum Type {
	MAIN(), ENCHANTING_TABLE(), ANVIL(), GRINDSTONE(), FISHING(), ENCHANTMENTS(), LANGUAGE(), ADVANCEMENTS(), RPG(), MINIGAME(), HARD_MODE(), LOOTS();

	Type() {}

	public Configuration getConfig() {
		Configurations c = Configurations.getConfigurations();
		switch (name()) {
			case "MAIN":
				return c.getConfig();
			case "ENCHANTING_TABLE":
				return c.getEnchantingTable();
			case "ANVIL":
				return c.getAnvil();
			case "GRINDSTONE":
				return c.getGrindstone();
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
			case "LOOTS":
				return c.getLoots();
		}
		return c.getConfig();
	}

}