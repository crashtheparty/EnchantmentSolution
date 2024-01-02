package org.ctp.enchantmentsolution.interfaces.effects.damage;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityDamageEffect;
import org.ctp.enchantmentsolution.utils.potion.ConfigCustomPotionEffect;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffect;

public abstract class DamagerAfflictCustomEffect extends EntityDamageEffect {

	private ConfigCustomPotionEffect[] effects;
	private boolean override;

	public DamagerAfflictCustomEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	boolean override, ConfigCustomPotionEffect[] effects, DamageCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		setOverride(override);
		setEffects(effects);
	}

	@Override
	public DamagerAfflictCustomResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		int level = getLevel(items);
		CustomPotionEffect[] effects = new CustomPotionEffect[1];

		for(ConfigCustomPotionEffect custom: getEffects())
			addPotionEffect(effects, new CustomPotionEffect((LivingEntity) damager, custom.getType(), custom.getAmplifier(level), custom.getDuration(level), new EnchantmentLevel(CERegister.DROWNED, level)));
		return new DamagerAfflictCustomResult(level, effects);
	}

	public CustomPotionEffect[] addPotionEffect(CustomPotionEffect[] effects, CustomPotionEffect effect) {
		if (effects.length == 1 && effects[0] == null) {
			effects[0] = effect;
			return effects;
		}

		CustomPotionEffect[] newEffects = new CustomPotionEffect[effects.length + 1];
		for(int i = 0; i < effects.length; i++)
			newEffects[i] = effects[i];
		newEffects[newEffects.length - 1] = effect;
		return newEffects;
	}

	public ConfigCustomPotionEffect[] getEffects() {
		return effects;
	}

	public void setEffects(ConfigCustomPotionEffect[] effects) {
		this.effects = effects;
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public class DamagerAfflictCustomResult extends EffectResult {

		private final CustomPotionEffect[] effects;

		public DamagerAfflictCustomResult(int level, CustomPotionEffect[] effects) {
			super(level);
			this.effects = effects;
		}

		public CustomPotionEffect[] getEffects() {
			return effects;
		}
	}

}
