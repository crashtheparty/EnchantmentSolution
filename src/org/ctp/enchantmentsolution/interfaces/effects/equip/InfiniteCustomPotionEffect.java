package org.ctp.enchantmentsolution.interfaces.effects.equip;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityEquipEffect;
import org.ctp.enchantmentsolution.utils.potion.ConfigCustomPotionEffect;
import org.ctp.enchantmentsolution.utils.potion.CustomPotionEffectType;

public abstract class InfiniteCustomPotionEffect extends EntityEquipEffect {

	private CustomPotionEffectType effectType;
	private boolean useLevel;

	public InfiniteCustomPotionEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	CustomPotionEffectType effectType, boolean useLevel, EquipCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.effectType = effectType;
		this.useLevel = useLevel;
	}

	@Override
	public InfiniteCustomPotionResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		int level = getLevel(items);

		return new InfiniteCustomPotionResult(level, new ConfigCustomPotionEffect(effectType, useLevel ? "%level% - 1" : "0", "20000000"));
	}

	public CustomPotionEffectType getEffectType() {
		return effectType;
	}

	public void setEffectType(CustomPotionEffectType effectType) {
		this.effectType = effectType;
	}

	public class InfiniteCustomPotionResult extends EffectResult {

		private final ConfigCustomPotionEffect effect;

		public InfiniteCustomPotionResult(int level, ConfigCustomPotionEffect effect) {
			super(level);
			this.effect = effect;
		}

		public ConfigCustomPotionEffect getEffect() {
			return effect;
		}
	}

}
