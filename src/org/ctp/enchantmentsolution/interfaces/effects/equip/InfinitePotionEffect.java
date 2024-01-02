package org.ctp.enchantmentsolution.interfaces.effects.equip;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import org.ctp.crashapi.events.EquipEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EquipCondition;
import org.ctp.enchantmentsolution.interfaces.effects.EntityEquipEffect;
import org.ctp.enchantmentsolution.utils.potion.ConfigPotionEffect;

public abstract class InfinitePotionEffect extends EntityEquipEffect {

	private PotionEffectType effectType;
	private boolean useLevel;

	public InfinitePotionEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	PotionEffectType effectType, boolean useLevel, EquipCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.effectType = effectType;
		this.useLevel = useLevel;
	}

	@Override
	public InfinitePotionResult run(Entity entity, ItemStack[] items, int oldLevel, EquipEvent event) {
		int level = getLevel(items);

		return new InfinitePotionResult(level, new ConfigPotionEffect(effectType, useLevel ? "%level% - 1" : "0", "200000"));
	}

	public PotionEffectType getEffectType() {
		return effectType;
	}

	public void setEffectType(PotionEffectType effectType) {
		this.effectType = effectType;
	}

	public class InfinitePotionResult extends EffectResult {

		private final ConfigPotionEffect effect;

		public InfinitePotionResult(int level, ConfigPotionEffect effect) {
			super(level);
			this.effect = effect;
		}

		public ConfigPotionEffect getEffect() {
			return effect;
		}
	}

}
