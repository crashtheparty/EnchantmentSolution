package org.ctp.enchantmentsolution.interfaces.effects.projectile;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileLaunchCondition;
import org.ctp.enchantmentsolution.interfaces.effects.ProjectileLaunchEffect;

public abstract class ProjectileMetadataEffect extends ProjectileLaunchEffect {

	private String metadata;
	
	public ProjectileMetadataEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	String metadata, ProjectileLaunchCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.setMetadata(metadata);
	}

	@Override
	public MetadataResult run(LivingEntity entity, Projectile projectile, ItemStack[] items, ProjectileLaunchEvent event) {
		return new MetadataResult(getLevel(items));
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public class MetadataResult extends EffectResult {

		public MetadataResult(int level) {
			super(level);
		}
	}

}
