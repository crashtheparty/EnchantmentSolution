package org.ctp.enchantmentsolution.utils.config;

import java.util.List;

public enum ConfigString {

	LEVEL_FIFTY(Type.MAIN, "enchanting_table.level_fifty", boolean.class),
	CUSTOM_TABLE(Type.MAIN, "enchanting_table.custom_gui", boolean.class),
	LAPIS_IN_TABLE(Type.MAIN, "enchanting_table.lapis_in_table", boolean.class),
	CUSTOM_ANVIL(Type.MAIN, "anvil.custom_gui", boolean.class),
	LEVEL_DIVISOR(Type.MAIN, "anvil.level_divisor", int.class),
	DEFAULT_ANVIL(Type.MAIN, "anvil.default_use", boolean.class),
	DAMAGE_ANVIL(Type.MAIN, "anvil.damage", boolean.class),
	MAX_REPAIR_LEVEL(Type.MAIN, "anvil.max_repair_level", int.class),
	CUSTOM_GRINDSTONE(Type.MAIN, "grindstone.custom_gui", boolean.class),
	LEGACY_GRINDSTONE(Type.MAIN, "grindstone.use_legacy", boolean.class),
	TAKE_ENCHANTMENTS(Type.MAIN, "grindstone.take_enchantments", boolean.class),
	SET_REPAIR_COST(Type.MAIN, "grindstone.set_repair_cost", boolean.class),
	DESTROY_TAKE_ITEM(Type.MAIN, "grindstone.destroy_take_item", boolean.class),
	MAX_ENCHANTMENTS(Type.MAIN, "max_enchantments", int.class),
	UPDATE_LEGACY_ENCHANTMENTS(Type.MAIN, "update_legacy_enchantments", boolean.class),
	USE_ENCHANTED_BOOKS(Type.MAIN, "use_enchanted_books", boolean.class),
	DISABLE_ENCHANT_METHOD(Type.MAIN, "disable_enchant_method", String.class),
	ENCHANTMENT_CHECK(Type.MAIN, "enchantment_check", int.class),
	DROP_ITEMS_NATURALLY(Type.MAIN, "drop_items_naturally", boolean.class),
	USE_COMMENTS(Type.MAIN, "use_comments", boolean.class),
	PROTECTION_CONFLICTS(Type.MAIN, "protection_conflicts", boolean.class),
	LANGUAGE_FILE(Type.MAIN, "language_file", String.class), LANGUAGE(Type.MAIN, "language", String.class),
	VILLAGER_TRADES(Type.MAIN, "villager_trades", boolean.class), LORE_ON_TOP(Type.MAIN, "lore_on_top", boolean.class),
	LOOT_BOOKSHELVES(Type.MAIN, "loots.", int.class, true), LOOT_TREASURE(Type.MAIN, "loots.", boolean.class, true),
	LOOT_LEVELS(Type.MAIN, "loots.", int.class, true), USE_LOOT(Type.MAIN, "loots.", boolean.class, true),
	LATEST_VERSION(Type.MAIN, "version.get_latest", boolean.class),
	EXPERIMENTAL_VERSION(Type.MAIN, "version.get_experimental", boolean.class),
	ADVANCED_OPTIONS(Type.ENCHANTMENTS, "advanced_options.use", boolean.class),
	DECAY(Type.ENCHANTMENTS, "advanced_options.enchantability_decay", boolean.class),
	STARTING_LEVEL(Type.ENCHANTMENTS, "advanced_options.starting_level", boolean.class),
	USE_LAPIS_MODIFIERS(Type.ENCHANTMENTS, "advanced_options.lapis_modifiers.use", boolean.class),
	LAPIS_CONSTANT(Type.ENCHANTMENTS, "advanced_options.lapis_modifiers.constant", double.class),
	LAPIS_MULTIPLIER(Type.ENCHANTMENTS, "advanced_options.lapis_modifiers.multiplier", double.class),
	MULTI_ENCHANT_DIVISOR(Type.ENCHANTMENTS, "advanced_options.multi_enchant_divisor", double.class),
	USE_PERMISSIONS(Type.ENCHANTMENTS, "advanced_options.use_permissions", boolean.class);

	private final Type type;
	private final String location;
	private final Class<?> value;
	private final boolean complete;

	ConfigString(Type type, String location, Class<?> value) {
		this(type, location, value, false);
	}

	ConfigString(Type type, String location, Class<?> value, boolean complete) {
		this.type = type;
		this.location = location;
		this.value = value;
		this.complete = complete;
	}

	public Type getType() {
		return type;
	}

	public String getLocation() {
		return location;
	}

	public Class<?> getValue() {
		return value;
	}

	public String getString() {
		if (!complete && value.isAssignableFrom(String.class)) {
			return type.getConfig().getString(location);
		}
		return null;
	}

	public boolean getBoolean() {
		if (!complete && value.isAssignableFrom(boolean.class)) {
			return type.getConfig().getBoolean(location);
		}
		return false;
	}

	public int getInt() {
		if (!complete && value.isAssignableFrom(int.class)) {
			return type.getConfig().getInt(location);
		}
		return 0;
	}

	public double getDouble() {
		if (!complete && value.isAssignableFrom(double.class)) {
			return type.getConfig().getDouble(location);
		}
		return 0;
	}

	public List<String> getStringList() {
		if (!complete && value.isAssignableFrom(List.class)) {
			return type.getConfig().getStringList(location);
		}
		return null;
	}

	public String getString(String addedLocation) {
		if (complete && value.isAssignableFrom(String.class)) {
			return type.getConfig().getString(location + addedLocation);
		}
		return null;
	}

	public boolean getBoolean(String addedLocation) {
		if (complete && value.isAssignableFrom(boolean.class)) {
			return type.getConfig().getBoolean(location + addedLocation);
		}
		return false;
	}

	public int getInt(String addedLocation) {
		if (complete && value.isAssignableFrom(int.class)) {
			return type.getConfig().getInt(location + addedLocation);
		}
		return 0;
	}

	public double getDouble(String addedLocation) {
		if (complete && value.isAssignableFrom(double.class)) {
			return type.getConfig().getDouble(location + addedLocation);
		}
		return 0;
	}

	public List<String> getStringList(String addedLocation) {
		if (complete && value.isAssignableFrom(List.class)) {
			return type.getConfig().getStringList(location + addedLocation);
		}
		return null;
	}
}
