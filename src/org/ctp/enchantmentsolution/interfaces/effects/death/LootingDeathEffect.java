package org.ctp.enchantmentsolution.interfaces.effects.death;

import java.util.*;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.Lootable;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public abstract class LootingDeathEffect extends EntityKillerEffect {

	private boolean override;

	public LootingDeathEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority, boolean override,
	DeathCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.override = override;
	}

	@Override
	public LootingDeathResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		Collection<ItemStack> populated = new ArrayList<ItemStack>();
		int level = getLevel(items);
		if (items.length == 0 || items[0] == null) return new LootingDeathResult(level, populated);
		ItemStack item = items[0];
		if (EnchantmentUtils.hasEnchantment(item, Enchantment.LOOT_BONUS_MOBS)) return new LootingDeathResult(level, populated);

		List<EnchantmentLevel> levels = EnchantmentUtils.getEnchantmentLevels(item);
		EnchantmentUtils.addEnchantmentToItem(item, CERegister.FORTUNE, level);
		LootContext.Builder contextBuilder = new LootContext.Builder(killed.getLocation());
		contextBuilder.killer((HumanEntity) killer);
		contextBuilder.lootedEntity(killed);
		contextBuilder.lootingModifier(level);
		LootContext context = contextBuilder.build();
		populated.addAll(((Lootable) killed).getLootTable().populateLoot(new Random(), context));
		EnchantmentUtils.removeAllEnchantments(item, true);
		EnchantmentUtils.addEnchantmentsToItem(item, levels);

		return new LootingDeathResult(level, populated);
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public class LootingDeathResult extends EffectResult {

		private final Collection<ItemStack> drops;

		public LootingDeathResult(int level, Collection<ItemStack> drops) {
			super(level);
			this.drops = drops;
		}

		public Collection<ItemStack> getDrops() {
			return drops;
		}

	}

}
