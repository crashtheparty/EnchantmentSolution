package org.ctp.enchantmentsolution.enchantments.helper;

import org.ctp.crashapi.config.Language;

public class EnchantmentDisplayName {

	private Language language;
	private String displayName;

	public EnchantmentDisplayName(Language lang, String displayName) {
		setLanguage(lang);
		setDescription(displayName);
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDescription(String displayName) {
		this.displayName = displayName;
	}
}
