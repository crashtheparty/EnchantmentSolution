package org.ctp.enchantmentsolution.enums;

public enum CustomItemType {
	
	VANILLA(false), TYPE(true), TYPE_SET(true);

	private final boolean mmo;
	
	CustomItemType(boolean mmo){
		this.mmo = mmo;
	}
	
	public boolean isMMO() {
		return mmo;
	}
	
	public static CustomItemType get(String s) {
		String[] split = s.split(":");
		if(split[0].equalsIgnoreCase("minecraft")) return VANILLA;
		if(split.length > 1) {
			String type = split[1];
			switch (type.toLowerCase()) {
				case "type":
					return TYPE;
				case "type_set":
					return TYPE_SET;
			}
		}
		
		return null;
	}
}
