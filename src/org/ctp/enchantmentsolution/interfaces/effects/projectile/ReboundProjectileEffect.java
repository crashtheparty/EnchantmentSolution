package org.ctp.enchantmentsolution.interfaces.effects.projectile;

import java.util.HashMap;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.ChatUtils;
import org.ctp.enchantmentsolution.Chatable;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.ProjectileHitCondition;
import org.ctp.enchantmentsolution.interfaces.effects.ProjectileHitDamagedEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class ReboundProjectileEffect extends ProjectileHitDamagedEffect {

	private double reboundSpeed;
	private String reboundSpeedLevel;
	private boolean useReboundSpeed, useReboundSpeedLevel, flipVelocity;

	public ReboundProjectileEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double reboundSpeed, String reboundSpeedLevel, boolean useReboundSpeed, boolean useReboundSpeedLevel, boolean flipVelocity,
	ProjectileHitCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.reboundSpeed = reboundSpeed;
		this.reboundSpeedLevel = reboundSpeedLevel;
		this.useReboundSpeed = useReboundSpeed;
		this.useReboundSpeedLevel = useReboundSpeedLevel;
		this.flipVelocity = flipVelocity;
	}

	@Override
	public ReboundProjectileResult run(LivingEntity shooter, LivingEntity damaged, Projectile projectile, ItemStack[] items, ProjectileHitEvent event) {
		int level = getLevel(items);

		double speed = 0;
		if (willUseReboundSpeed()) speed += getReboundSpeed();
		if (willUseReboundSpeedLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getReboundSpeedLevel(), codes);
			speed += MathUtils.eval(percent);
		}
		speed = Math.max(speed, 0);
		return new ReboundProjectileResult(level, speed);
	}

	public double getReboundSpeed() {
		return reboundSpeed;
	}

	public void setReboundSpeed(double reboundSpeed) {
		this.reboundSpeed = reboundSpeed;
	}

	public String getReboundSpeedLevel() {
		return reboundSpeedLevel;
	}

	public void setReboundSpeedLevel(String reboundSpeedLevel) {
		this.reboundSpeedLevel = reboundSpeedLevel;
	}

	public boolean willUseReboundSpeed() {
		return useReboundSpeed;
	}

	public void setUseReboundSpeed(boolean useReboundSpeed) {
		this.useReboundSpeed = useReboundSpeed;
	}

	public boolean willUseReboundSpeedLevel() {
		return useReboundSpeedLevel;
	}

	public void setUseReboundSpeedLevel(boolean useReboundSpeedLevel) {
		this.useReboundSpeedLevel = useReboundSpeedLevel;
	}

	public boolean willFlipVelocity() {
		return flipVelocity;
	}

	public void setFlipVelocity(boolean flipVelocity) {
		this.flipVelocity = flipVelocity;
	}

	public class ReboundProjectileResult extends EffectResult {

		private final double reboundSpeed;

		public ReboundProjectileResult(int level, double reboundSpeed) {
			super(level);
			this.reboundSpeed = reboundSpeed;
		}

		public double getReboundSpeed() {
			return reboundSpeed;
		}
	}

}
