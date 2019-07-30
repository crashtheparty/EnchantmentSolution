package org.ctp.enchantmentsolution.advancements;

import org.ctp.enchantmentsolution.api.Language;

public class ESLocalization {

	private String name, description;
	private Language language;
	
	ESLocalization(Language language, String name, String description) {
		this.language = language;
		this.name = name;
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Language getLanguage() {
		return language;
	}
	
	public void setLanguage(Language language) {
		this.language = language;
	}
}
