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
import org.ctp.enchantmentsolution.interfaces.effects.ProjectileHitDamagerEffect;
import org.ctp.enchantmentsolution.utils.MathUtils;

public abstract class CreateExplosionEffect extends ProjectileHitDamagerEffect {

	private double explosionSize;
	private String explosionSizeLevel;
	private boolean setFire, setBlocks, delayExplosion, useExplosionSize, useExplosionSizeLevel;

	public CreateExplosionEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	double explosionSize, String explosionSizeLevel, boolean useExplosionSize, boolean useExplosionSizeLevel, boolean setFire, boolean setBlocks,
	boolean delayExplosion, ProjectileHitCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.explosionSize = explosionSize;
		this.explosionSizeLevel = explosionSizeLevel;
		this.useExplosionSize = useExplosionSize;
		this.useExplosionSizeLevel = useExplosionSizeLevel;
		this.setFire = setFire;
		this.setBlocks = setBlocks;
		this.delayExplosion = delayExplosion;
	}

	@Override
	public CreateExplosionResult run(LivingEntity shooter, LivingEntity damaged, Projectile projectile, ItemStack[] items, ProjectileHitEvent event) {
		int level = getLevel(items);

		double size = 0;
		if (willUseExplosionSize()) size += getExplosionSize();
		if (willUseExplosionSizeLevel()) {
			HashMap<String, Object> codes = ChatUtils.getCodes();
			codes.put("%level%", level);
			String percent = Chatable.get().getMessage(getExplosionSizeLevel(), codes);
			size += MathUtils.eval(percent);
		}
		size = Math.max(size, 0);
		return new CreateExplosionResult(level, size);
	}

	public double getExplosionSize() {
		return explosionSize;
	}

	public void setExplosionSize(double explosionSize) {
		this.explosionSize = explosionSize;
	}

	public String getExplosionSizeLevel() {
		return explosionSizeLevel;
	}

	public void setExplosionSizeLevel(String explosionSizeLevel) {
		this.explosionSizeLevel = explosionSizeLevel;
	}

	public boolean willSetFire() {
		return setFire;
	}

	public void setSetFire(boolean setFire) {
		this.setFire = setFire;
	}

	public boolean willSetBlocks() {
		return setBlocks;
	}

	public void setSetBlocks(boolean setBlocks) {
		this.setBlocks = setBlocks;
	}

	public boolean willDelayExplosion() {
		return delayExplosion;
	}

	public void setDelayExplosion(boolean delayExplosion) {
		this.delayExplosion = delayExplosion;
	}

	public boolean willUseExplosionSize() {
		return useExplosionSize;
	}

	public void setUseExplosionSize(boolean useExplosionSize) {
		this.useExplosionSize = useExplosionSize;
	}

	public boolean willUseExplosionSizeLevel() {
		return useExplosionSizeLevel;
	}

	public void setUseExplosionSizeLevel(boolean useExplosionSizeLevel) {
		this.useExplosionSizeLevel = useExplosionSizeLevel;
	}

	public class CreateExplosionResult extends EffectResult {

		private final double explosionSize;

		public CreateExplosionResult(int level, double explosionSize) {
			super(level);
			this.explosionSize = explosionSize;
		}

		public double getExplosionSize() {
			return explosionSize;
		}
	}

}
