package org.ctp.enchantmentsolution.utils.files;

import java.util.List;

public class ItemBreakSubFile {

	private final String name;
	private final List<String> values;
	
	public ItemBreakSubFile(String name, List<String> values) {
		this.name = name;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public List<String> getValues() {
		return values;
	}
}
