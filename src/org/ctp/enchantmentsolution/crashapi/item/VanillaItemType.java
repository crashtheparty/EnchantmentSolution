package org.ctp.enchantmentsolution.crashapi.item;

public enum VanillaItemType {

	VANILLA(false), TYPE(true), TYPE_SET(true);

	private final boolean mmo;

	VanillaItemType(boolean mmo) {
		this.mmo = mmo;
	}

	public boolean isMMO() {
		return mmo;
	}

	public static VanillaItemType get(String s) {
		String[] split = s.split(":");
		if (split[0].equalsIgnoreCase("minecraft")) return VANILLA;
		if (split.length > 1) {
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
