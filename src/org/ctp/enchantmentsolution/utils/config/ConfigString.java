package org.ctp.enchantmentsolution.utils.config;

import java.util.List;

public enum ConfigString {

	LEVEL_FIFTY(Type.MAIN, "enchanting_table.level_fifty", boolean.class),
	CUSTOM_TABLE(Type.MAIN, "enchanting_table.custom_gui", boolean.class),
	LAPIS_IN_TABLE(Type.MAIN, "enchanting_table.lapis_in_table", boolean.class),
	RESET_ON_RELOAD(Type.MAIN, "enchanting_table.reset_on_reload", boolean.class),
	CUSTOM_ANVIL(Type.MAIN, "anvil.custom_gui", boolean.class),
	LEVEL_DIVISOR(Type.MAIN, "anvil.level_divisor", int.class),
	DEFAULT_ANVIL(Type.MAIN, "anvil.default_use", boolean.class),
	DAMAGE_ANVIL(Type.MAIN, "anvil.damage", boolean.class),
	RENAME_FROM_ANVIL(Type.MAIN, "anvil.rename", boolean.class),
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
	DROP_ITEMS_NATURALLY(Type.MAIN, "drop_items_naturally", boolean.class),
	USE_COMMENTS(Type.MAIN, "use_comments", boolean.class),
	PROTECTION_CONFLICTS(Type.MAIN, "protection_conflicts", boolean.class),
	LANGUAGE_FILE(Type.MAIN, "language_file", String.class), LANGUAGE(Type.MAIN, "language", String.class),
	VILLAGER_TRADES(Type.MAIN, "trades.villager", boolean.class),
	PIGLIN_TRADES(Type.MAIN, "trades.piglin", boolean.class),
	LORE_LOCATION(Type.MAIN, "lore_location", String.class),
	LOOT_BOOKSHELVES(Type.MAIN, "loots.", int.class, true),
	LOOT_LEVELS(Type.MAIN, "loots.", int.class, true), USE_LOOT(Type.MAIN, "loots.", boolean.class, true),
	LATEST_VERSION(Type.MAIN, "version.get_latest", boolean.class),
	EXPERIMENTAL_VERSION(Type.MAIN, "version.get_experimental", boolean.class),
	WIKI_ON_LOGIN(Type.MAIN, "wiki.on_login", boolean.class),
	WIKI_ON_TIMER(Type.MAIN, "wiki.on_timer", boolean.class),
	WIKI_TIMER(Type.MAIN, "wiki.timer_seconds", int.class),
	WIKI_URL(Type.MAIN, "wiki.url", String.class),
	GAMETYPES(Type.MAIN, "gametypes", List.class),
	USE_PARTICLES(Type.MAIN, "use_particles", boolean.class),
	PLAY_SOUND(Type.MAIN, "play_sounds", boolean.class),
	PRINT_USAGE(Type.MAIN, "print_usage", boolean.class),
	MULTI_BLOCK_ASYNC(Type.MAIN, "multi_block.async", boolean.class),
	MULTI_BLOCK_BLOCKS_GLOBAL(Type.MAIN, "multi_block.blocks_per_tick.global", int.class),
	MULTI_BLOCK_BLOCKS_PLAYER(Type.MAIN, "multi_block.blocks_per_tick.player", int.class),
	MULTI_BLOCK_ALL_FACES(Type.MAIN, "multi_block.all_faces", boolean.class),
	EXTRA_ENCHANTING_MATERIALS(Type.ENCHANTMENTS, "extra_enchantables", List.class),
	ADVANCED_OPTIONS(Type.ENCHANTMENTS, "advanced_options.use", boolean.class),
	DECAY(Type.ENCHANTMENTS, "advanced_options.enchantability_decay", boolean.class),
	STARTING_LEVEL(Type.ENCHANTMENTS, "advanced_options.starting_level", boolean.class),
	USE_LAPIS_MODIFIERS(Type.ENCHANTMENTS, "advanced_options.lapis_modifiers.use", boolean.class),
	LAPIS_CONSTANT(Type.ENCHANTMENTS, "advanced_options.lapis_modifiers.constant", double.class),
	LAPIS_MULTIPLIER(Type.ENCHANTMENTS, "advanced_options.lapis_modifiers.multiplier", double.class),
	MULTI_ENCHANT_DIVISOR(Type.ENCHANTMENTS, "advanced_options.multi_enchant_divisor", double.class),
	USE_PERMISSIONS(Type.ENCHANTMENTS, "advanced_options.use_permissions", boolean.class),
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
	HARD_HEALTH_INCREASE(Type.HARD_MODE, "increase_health", boolean.class);

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
