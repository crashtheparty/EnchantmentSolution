package org.ctp.enchantmentsolution.external_api.hu.trigary.advancementcreator.shared;

/**
 * Specifies an entity type.
 * @see EntityObject
 */
@SuppressWarnings("unused")
public enum MobType implements SharedEnum {
	ITEM,
	XP_ORB,
	AREA_EFFECT_CLOUD,
	LEASH_KNOT,
	PAINTING,
	ITEM_FRAME,
	ARMOR_STAND,
	EVOCATION_FANGS,
	ENDER_CRYSTAL,
	EGG,
	ARROW,
	SNOWBALL,
	FIREBALL,
	SMALL_FIREBALL,
	ENDER_PEARL,
	EYE_OF_ENDER_SIGNAL,
	POTION,
	XP_BOTTLE,
	WITHER_SKULL,
	FIREWORKS_ROCKET,
	SPECTRAL_ARROW,
	SHULKER_BULLET,
	DRAGON_FIREBALL,
	LLAMA_SPIT,
	TNT,
	FALLING_BLOCK,
	COMMANDBLOCK_MINECART,
	BOAT,
	MINECART,
	CHEST_MINECART,
	FURNACE_MINECART,
	TNT_MINECART,
	HOPPER_MINECART,
	SPAWNER_MINECART,
	ELDER_GUARDIAN,
	WITHER_SKELETON,
	STRAY,
	HUSK,
	ZOMBIE_VILLAGER,
	EVOCATION_ILLAGER,
	VEX,
	VINDICATION_ILLAGER,
	ILLUSION_ILLAGER,
	CREEPER,
	SKELETON,
	SPIDER,
	GIANT,
	ZOMBIE,
	SLIME,
	GHAST,
	ZOMBIE_PIGMAN,
	ENDERMAN,
	CAVE_SPIDER,
	SILVERFISH,
	BLAZE,
	MAGMA_CUBE,
	ENDER_DRAGON,
	WITHER,
	WITCH,
	ENDERMITE,
	GUARDIAN,
	SHULKER,
	SKELETON_HORSE,
	ZOMBIE_HORSE,
	DONKEY,
	MULE,
	BAT,
	PIG,
	SHEEP,
	COW,
	CHICKEN,
	SQUID,
	WOLF,
	MOOSHROOM,
	SNOWMAN,
	OCELOT,
	VILLAGER_GOLEM,
	HORSE,
	RABBIT,
	POLAR_BEAR,
	LLAMA,
	PARROT,
	VILLAGER,
	PLAYER,
	LIGHTNING_BOLT;
	
	@Override
	public String getValue() {
		return "minecraft:" + name().toLowerCase();
	}
}
