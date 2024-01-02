package org.ctp.enchantmentsolution.interfaces.projectile;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.modify.entity.SniperLaunchEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileLaunchCondition;
import org.ctp.enchantmentsolution.interfaces.effects.projectile.ChangeVelocityEffect;

public class Sniper extends ChangeVelocityEffect {

	public Sniper() {
		super(RegisterEnchantments.SNIPER, EnchantmentMultipleType.ALL, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, "0", "0", "0", "1 + 0.1 * %level% * %level%", "1 + 0.1 * %level% * %level%", "1 + 0.1 * %level% * %level%", true, true, true, true, true, true, new ProjectileLaunchCondition[0]);
	}

	@Override
	public ChangeVelocityResult run(LivingEntity entity, Projectile projectile, ItemStack[] items, ProjectileLaunchEvent event) {
		ChangeVelocityResult result = super.run(entity, projectile, items, event);

		int level = result.getLevel();
		if (level == 0) return null;

		double x = result.getX();
		double y = result.getY();
		double z = result.getZ();

		SniperLaunchEvent sniper = new SniperLaunchEvent(entity, level, x, y, z);
		Bukkit.getPluginManager().callEvent(sniper);

		if (!sniper.isCancelled()) {
			double speedX = sniper.getSpeedX();
			double speedY = sniper.getSpeedY();
			double speedZ = sniper.getSpeedZ();
			projectile.setMetadata("sniper", new FixedMetadataValue(EnchantmentSolution.getPlugin(), LocationUtils.locationToString(entity.getLocation())));
			Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), (Runnable) () -> {
				Vector v = new Vector(speedX, speedY, speedZ);
				projectile.setVelocity(projectile.getVelocity().multiply(v));
			}, 0l);
			return new ChangeVelocityResult(level, sniper.getSpeedX(), sniper.getSpeedY(), sniper.getSpeedZ());
		}
		return null;
	}

}
