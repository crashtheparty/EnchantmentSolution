package org.ctp.enchantmentsolution.utils.config;

import java.util.List;

public enum ConfigString {

	LANGUAGE_FILE(Type.MAIN, "language_file", String.class), LANGUAGE(Type.MAIN, "language", String.class),
	DROP_ITEMS_NATURALLY(Type.MAIN, "drop_items_naturally", boolean.class),
	USE_COMMENTS(Type.MAIN, "use_comments", boolean.class),
	LATEST_VERSION(Type.MAIN, "version.get_latest", boolean.class),
	EXPERIMENTAL_VERSION(Type.MAIN, "version.get_experimental", boolean.class),
	WIKI_ON_LOGIN(Type.MAIN, "wiki.on_login", boolean.class),
	WIKI_ON_TIMER(Type.MAIN, "wiki.on_timer", boolean.class),
	WIKI_TIMER(Type.MAIN, "wiki.timer_seconds", int.class),
	WIKI_URL(Type.MAIN, "wiki.url", String.class),
	GAMETYPES(Type.MAIN, "gametypes", List.class),
	PRINT_USAGE(Type.MAIN, "print_usage", boolean.class),
	DEBUG(Type.MAIN, "debug.send", boolean.class),
	LEVEL_FIFTY(Type.ENCHANTING_TABLE, "level_fifty", boolean.class),
	CUSTOM_TABLE(Type.ENCHANTING_TABLE, "custom_gui", boolean.class),
	LAPIS_IN_TABLE(Type.ENCHANTING_TABLE, "lapis_in_table", boolean.class),
	USE_UPGRADES(Type.ENCHANTING_TABLE, "enchanting_upgrades", boolean.class),
	RESET_ON_RELOAD(Type.ENCHANTING_TABLE, "reset_on_reload", boolean.class),
	EXTRA_ENCHANTING_MATERIALS(Type.ENCHANTING_TABLE, "extra_enchantables", List.class),
	DECAY(Type.ENCHANTING_TABLE, "enchantability_decay", boolean.class),
	MINIMUM_LEVEL(Type.ENCHANTING_TABLE, "use_minimum_level", boolean.class),
	MULTI_ENCHANT_DIVISOR_FIFTY(Type.ENCHANTING_TABLE, "multi_enchant_divisor.level_fifty", double.class),
	MULTI_ENCHANT_DIVISOR_THIRTY(Type.ENCHANTING_TABLE, "multi_enchant_divisor.level_thirty", double.class),
	CUSTOM_ANVIL(Type.ANVIL, "custom_gui", boolean.class),
	LEVEL_DIVISOR(Type.ANVIL, "level_divisor", int.class),
	DEFAULT_ANVIL(Type.ANVIL, "default_use", boolean.class),
	DAMAGE_ANVIL(Type.ANVIL, "damage", boolean.class),
	RENAME_FROM_ANVIL(Type.ANVIL, "rename", boolean.class),
	MAX_REPAIR_LEVEL(Type.ANVIL, "max_repair_level", int.class),
	REPAIR_COST_LIMIT(Type.ANVIL, "repair_cost_limit", int.class),
	NO_UPGRADE_BOOKS(Type.ANVIL, "no_upgrade_books", boolean.class),
	NO_UPGRADE_NON_BOOKS(Type.ANVIL, "no_upgrade_non_books", boolean.class),
	NO_UPGRADE_BOOKS_TO_NON_BOOKS(Type.ANVIL, "no_upgrade_books_to_non_books", boolean.class),
	CUSTOM_GRINDSTONE(Type.GRINDSTONE, "custom_gui", boolean.class),
	TAKE_ENCHANTMENTS(Type.GRINDSTONE, "take_enchantments", boolean.class),
	SET_REPAIR_COST(Type.GRINDSTONE, "set_repair_cost", boolean.class),
	DESTROY_TAKE_ITEM(Type.GRINDSTONE, "destroy_take_item", boolean.class),
	MAX_ENCHANTMENTS(Type.ENCHANTMENTS, "max_enchantments", int.class),
	USE_ENCHANTED_BOOKS(Type.ENCHANTMENTS, "use_enchanted_books", boolean.class),
	DISABLE_ENCHANT_METHOD(Type.ENCHANTMENTS, "disable_enchant_method", String.class),
	PROTECTION_CONFLICTS(Type.ENCHANTMENTS, "protection_conflicts", boolean.class),
	LORE_LOCATION(Type.ENCHANTMENTS, "lore_location", String.class),
	USE_PARTICLES(Type.ENCHANTMENTS, "block_enchants.use_particles", boolean.class),
	PLAY_SOUND(Type.ENCHANTMENTS, "block_enchants.play_sounds", boolean.class),
	ASYNC_BLOCK_DELAY(Type.ENCHANTMENTS, "block_enchants.async.delay_on_timeout", int.class),
	ASYNC_BLOCKS_GLOBAL(Type.ENCHANTMENTS, "block_enchants.async.global_blocks_per_tick", int.class),
	ASYNC_BLOCKS_PLAYER(Type.ENCHANTMENTS, "block_enchants.async.player_blocks_per_tick", int.class),
	LEGACY_FF(Type.ENCHANTMENTS, "legacy_frequent_flyer", boolean.class),
	ADVANCED_OPTIONS(Type.ENCHANTMENTS, "use_advanced_options", boolean.class),
	USE_PERMISSIONS(Type.ENCHANTMENTS, "use_permissions", boolean.class),
	DISCOVERY_ADVANCEMENTS(Type.ADVANCEMENTS, "discovery_advancements", boolean.class),
	RPG_BASE(Type.RPG, "experience.base", double.class),
	RPG_MULTIPLY(Type.RPG, "experience.multiply", double.class),
	RPG_LEVELS_0(Type.RPG, "levels.points.level_0", double.class),
	RPG_LEVELS_BASE(Type.RPG, "levels.points.base", double.class),
	RPG_LEVELS_ADD(Type.RPG, "levels.points.add_per_level", double.class),
	RPG_LEVELS_POWER(Type.RPG, "levels.points.add_power", double.class),
	RPG_LEVELS_DIVISOR(Type.RPG, "levels.points.divisor", double.class),
	RPG_ENCHANTMENT_LEVELONE(Type.RPG, "enchantments.", int.class, true),
	RPG_ENCHANTMENT_INCREASE(Type.RPG, "enchantments.", int.class, true),
	RPG_EXPERIENCE_LOCKED_ENCHANTMENT(Type.RPG, "experience.no_exp.enchantment_locked", boolean.class),
	RPG_EXPERIENCE_LOCKED_LEVEL(Type.RPG, "experience.no_exp.level_locked", boolean.class),
	MINIGAME_TYPE(Type.MINIGAME, "type", String.class),
	MINIGAME_FAST_ENCHANTING_OVERRIDE(Type.MINIGAME, "fast.override_enchanting_cost", boolean.class),
	MINIGAME_FAST_ENCHANTING_COSTS(Type.MINIGAME, "fast.enchant_costs.use", List.class),
	MINIGAME_FAST_ENCHANTING_LEVEL_COST(Type.MINIGAME, "fast.enchant_costs.level", int.class),
	MINIGAME_FAST_ENCHANTING_LAPIS_COST(Type.MINIGAME, "fast.enchant_costs.lapis", int.class),
	MINIGAME_FAST_ENCHANTING_ECONOMY_COST(Type.MINIGAME, "fast.enchant_costs.economy", double.class),
	MINIGAME_FAST_ANVIL_OVERRIDE(Type.MINIGAME, "fast.override_anvil_cost", boolean.class),
	MINIGAME_FAST_ANVIL_COST(Type.MINIGAME, "fast.anvil_cost", int.class),
	MINIGAME_FAST_RANDOM_BOOKSHELVES(Type.MINIGAME, "fast.random_bookshelves", boolean.class),
	MINIGAME_MONDAYS_ANVIL_OVERRIDE(Type.MINIGAME, "mondays.override_anvil_cost", boolean.class),
	MINIGAME_MONDAYS_ANVIL_COST(Type.MINIGAME, "mondays.anvil_cost", int.class),
	MINIGAME_QUICK_ANVIL(Type.MINIGAME, "quick_anvil", boolean.class),
	MINIGAME_CUSTOM_PAGING(Type.MINIGAME, "custom.paging", int.class),
	HARD_HEALTH_INCREASE(Type.HARD_MODE, "increase_health", boolean.class),
	USE_ALL_VILLAGER_TRADES(Type.LOOTS, "villager_trades.use", boolean.class),
	USE_VILLAGER_TRADES(Type.LOOTS, "villager_trades.", boolean.class, true),
	VILLAGER_TYPES(Type.LOOTS, "villager_trades.", List.class, true),
	VILLAGER_PRICE(Type.LOOTS, "villager_trades.", String.class, true),
	USE_PIGLIN_TRADES(Type.LOOTS, "piglin_trades.use", boolean.class),
	PIGLIN_TYPES(Type.LOOTS, "piglin_trades.types", List.class),
	USE_FISHING_LOOT(Type.LOOTS, "fishing_loot.use", boolean.class),
	FISHING_TYPES(Type.LOOTS, "fishing_loot.types", List.class),
	USE_MOB_LOOT(Type.LOOTS, "mobs.", boolean.class, true),
	USE_ALL_MOB_LOOT(Type.LOOTS, "mobs.use", boolean.class),
	MOB_TYPES(Type.LOOTS, "mobs.", List.class, true),
	USE_ALL_CHEST_LOOT(Type.LOOTS, "chests.use", boolean.class),
	USE_CHEST_LOOT(Type.LOOTS, "chests.", boolean.class, true),
	CHEST_TYPES(Type.LOOTS, "chests.", List.class, true), 
	ALLOW_NO_ENCHANTMENTS(Type.LOOTS, "allow_no_enchantments", boolean.class);

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

	public boolean exists() {
		if (!complete && value.isAssignableFrom(String.class)) return type.getConfig().getConfig().getStringValue(location) != null;
		if (!complete && value.isAssignableFrom(boolean.class)) return type.getConfig().getConfig().getBooleanValue(location) != null;
		if (!complete && value.isAssignableFrom(int.class)) return type.getConfig().getConfig().getInteger(location) != null;
		if (!complete && value.isAssignableFrom(double.class)) return type.getConfig().getConfig().getDoubleValue(location) != null;
		if (!complete && value.isAssignableFrom(List.class)) return type.getConfig().getConfig().getStringList(location) != null;
		return false;
	}

	public boolean exists(String addedLocation) {
		if (complete && value.isAssignableFrom(String.class)) return type.getConfig().getConfig().getStringValue(location + addedLocation) != null;
		if (complete && value.isAssignableFrom(boolean.class)) return type.getConfig().getConfig().getBooleanValue(location + addedLocation) != null;
		if (complete && value.isAssignableFrom(int.class)) return type.getConfig().getConfig().getInteger(location + addedLocation) != null;
		if (complete && value.isAssignableFrom(double.class)) return type.getConfig().getConfig().getDoubleValue(location + addedLocation) != null;
		if (complete && value.isAssignableFrom(List.class)) return type.getConfig().getConfig().getStringList(location + addedLocation) != null;
		return false;
	}

	public String getString() {
		if (!complete && value.isAssignableFrom(String.class)) return type.getConfig().getString(location);
		return null;
	}

	public boolean getBoolean() {
		if (!complete && value.isAssignableFrom(boolean.class)) return type.getConfig().getBoolean(location);
		return false;
	}

	public int getInt() {
		if (!complete && value.isAssignableFrom(int.class)) return type.getConfig().getInt(location);
		return 0;
	}

	public double getDouble() {
		if (!complete && value.isAssignableFrom(double.class)) return type.getConfig().getDouble(location);
		return 0;
	}

	public List<String> getStringList() {
		if (!complete && value.isAssignableFrom(List.class)) return type.getConfig().getStringList(location);
		return null;
	}

	public boolean listContains(String search) {
		if (!complete && value.isAssignableFrom(List.class)) for(Object obj: type.getConfig().getStringList(location))
			if (obj.toString() != null && search != null && obj.toString().equalsIgnoreCase(search)) return true;
		return false;
	}

	public String getString(String addedLocation) {
		if (complete && value.isAssignableFrom(String.class)) return type.getConfig().getString(location + addedLocation);
		return null;
	}

	public boolean getBoolean(String addedLocation) {
		if (complete && value.isAssignableFrom(boolean.class)) return type.getConfig().getBoolean(location + addedLocation);
		return false;
	}

	public int getInt(String addedLocation) {
		if (complete && value.isAssignableFrom(int.class)) return type.getConfig().getInt(location + addedLocation);
		return 0;
	}

	public double getDouble(String addedLocation) {
		if (complete && value.isAssignableFrom(double.class)) return type.getConfig().getDouble(location + addedLocation);
		return 0;
	}

	public List<String> getStringList(String addedLocation) {
		if (complete && value.isAssignableFrom(List.class)) return type.getConfig().getStringList(location + addedLocation);
		return null;
	}

	public boolean listContains(String search, String addedLocation) {
		if (!complete && value.isAssignableFrom(List.class)) for(Object obj: type.getConfig().getStringList(location + addedLocation))
			if (obj.toString() != null && search != null && obj.toString().equalsIgnoreCase(search)) return true;
		return false;
	}
}
