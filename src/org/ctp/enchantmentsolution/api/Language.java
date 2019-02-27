package org.ctp.enchantmentsolution.api;

public enum Language{
	US("en_us"), GERMAN("de_de");
	
	private String locale;
	
	Language(String l){
		locale = l;
	}

	public String getLocale() {
		return locale;
	}
	
	public static Language getLanguage(String value) {
		for(Language lang : values()) {
			if(lang.getLocale().equalsIgnoreCase(value)) {
				return lang;
			}
		}
		return US;
	}
}