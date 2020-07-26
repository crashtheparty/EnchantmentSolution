package org.ctp.enchantmentsolution.enchantments.helper;

import org.ctp.crashapi.config.Language;

public class EnchantmentDescription {

	private Language language;
	private String description;

	public EnchantmentDescription(Language lang, String desc) {
		setLanguage(lang);
		setDescription(desc);
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
