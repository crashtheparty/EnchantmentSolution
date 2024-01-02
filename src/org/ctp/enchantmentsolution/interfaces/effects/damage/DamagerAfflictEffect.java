package org.ctp.enchantmentsolution.interfaces.effects.damage;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public abstract class DamagerAfflictEffect extends EntityDamageEffect {
	private ConfigPotionEffect[] effects;
	private boolean override;

	public DamagerAfflictEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	boolean override, ConfigPotionEffect[] effects, DamageCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		setOverride(override);
		setEffects(effects);
	}

	@Override
	public DamagerAfflictResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);
		PotionEffect[] effects = new PotionEffect[1];

		for(ConfigPotionEffect custom: getEffects())
			addPotionEffect(effects, new PotionEffect(custom.getType(), custom.getAmplifier(level), custom.getDuration(level)));
		return new DamagerAfflictResult(level, effects);
	}

	public PotionEffect[] addPotionEffect(PotionEffect[] effects, PotionEffect effect) {
		if (effects.length == 1 && effects[0] == null) {
			effects[0] = effect;
			return effects;
		}

		PotionEffect[] newEffects = new PotionEffect[effects.length + 1];
		for(int i = 0; i < effects.length; i++)
			newEffects[i] = effects[i];
		newEffects[newEffects.length - 1] = effect;
		return newEffects;
	}

	public ConfigPotionEffect[] getEffects() {
		return effects;
	}

	public void setEffects(ConfigPotionEffect[] effects) {
		this.effects = effects;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public class DamagerAfflictResult extends EffectResult {

		private final PotionEffect[] effects;

		public DamagerAfflictResult(int level, PotionEffect[] effects) {
			super(level);
			this.effects = effects;
		}

		public PotionEffect[] getEffects() {
			return effects;
		}
	}
}
