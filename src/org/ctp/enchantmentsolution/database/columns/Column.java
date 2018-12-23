package org.ctp.enchantmentsolution.database.columns;

public class Column {

	private String name;
	private String type;
	private String defaultValue;

	public Column(String name, String type, String defaultValue) {
		this.setName(name);
		this.setType(type);
		this.setDefaultValue(defaultValue);
	}

	public String getType() {
		return type;
	}

	private void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	private void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
