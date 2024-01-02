package org.ctp.enchantmentsolution.interfaces.projectile;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileLaunchCondition;
import org.ctp.enchantmentsolution.interfaces.effects.projectile.ProjectileMetadataEffect;

public class DetonatorMetadata extends ProjectileMetadataEffect {

	public DetonatorMetadata() {
		super(RegisterEnchantments.DETONATOR, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, "detonator", new ProjectileLaunchCondition[0]);
	}
	
	@Override
	public MetadataResult run(LivingEntity entity, Projectile projectile, ItemStack[] items, ProjectileLaunchEvent event) {
		MetadataResult result = super.run(entity, projectile, items, event);
		if (result.getLevel() == 0) return null;
		
		projectile.setMetadata(getMetadata(), new FixedMetadataValue(EnchantmentSolution.getPlugin(), result.getLevel()));
		return result;
	}
}
