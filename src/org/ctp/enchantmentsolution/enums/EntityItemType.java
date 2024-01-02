package org.ctp.enchantmentsolution.enums;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

public enum EntityItemType {
	
	ARROW(Material.ARROW, EntityType.ARROW, Arrow.class, Sound.ENTITY_ARROW_SHOOT), 
	TIPPED_ARROW(Material.TIPPED_ARROW, EntityType.ARROW, Arrow.class, Sound.ENTITY_ARROW_SHOOT), 
	SPECTRAL_ARROW(Material.SPECTRAL_ARROW, EntityType.SPECTRAL_ARROW, SpectralArrow.class, Sound.ENTITY_ARROW_SHOOT), 
	SNOWBALL(Material.SNOWBALL, EntityType.SNOWBALL, Snowball.class, Sound.ENTITY_SNOWBALL_THROW), 
	EGG(Material.EGG, EntityType.EGG, Egg.class, Sound.ENTITY_EGG_THROW), 
	ENDER_PEARL(Material.ENDER_PEARL, EntityType.ENDER_PEARL, EnderPearl.class, Sound.ENTITY_ENDER_PEARL_THROW), 
	FIRE_CHARGE(Material.FIRE_CHARGE, EntityType.FIREBALL, Fireball.class, Sound.ITEM_FIRECHARGE_USE);
	
	private final Material material;
	private final EntityType entityType;
	private final Class<? extends Projectile> clazz;
	private final Sound sound;
	
	EntityItemType(Material material, EntityType entityType, Class<? extends Projectile> clazz, Sound sound){
		this.material = material;
		this.entityType = entityType;
		this.clazz = clazz;
		this.sound = sound;
	}

	public Material getMaterial() {
		return material;
	}

	public Class<? extends Projectile> getClazz() {
		return clazz;
	}

	public EntityType getEntityType() {
		return entityType;
	}
	
	public static EntityItemType fromItem(ItemStack item) {
		for(EntityItemType type: EntityItemType.values())
			if (item != null && item.getType() == type.getMaterial()) return type;
		return null;
	}

	public static EntityItemType fromEntityType(EntityType entityType) {
		for(EntityItemType type: EntityItemType.values())
			if (entityType == type.getEntityType()) return type;
		return null;
	}

	public Sound getSound() {
		return sound;
	}
}
