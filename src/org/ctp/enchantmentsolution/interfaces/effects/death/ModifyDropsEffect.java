package org.ctp.enchantmentsolution.interfaces.effects.death;

import java.util.ArrayList;
import java.util.Collection;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.ModifyLoot.LootResult;

public abstract class ModifyDropsEffect extends EntityKillerEffect {

	private ModifyLoot loot;
	private boolean enchant;
	private String lootKind;

	public ModifyDropsEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	ModifyLoot loot, boolean enchant, String lootKind, DeathCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		setLoot(loot);
		setEnchant(enchant);
		setLootKind(lootKind);
	}

	@Override
	public ModifyDropsResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		int level = getLevel(items);
		Collection<ItemStack> newDrops = new ArrayList<ItemStack>();
		boolean changed = false;
		for(ItemStack item: drops) {
			LootResult result = loot.getRandomLoot(item, enchant, lootKind);
			if (result.isChanged()) {
				newDrops.add(result.getModifiedItem());
				changed = true;
			} else
				newDrops.add(result.getOriginalItem());
		}

		return new ModifyDropsResult(level, newDrops, drops, changed);
	}

	public ModifyLoot getLoot() {
		return loot;
	}

	public void setLoot(ModifyLoot loot) {
		this.loot = loot;
	}

	public boolean isEnchant() {
		return enchant;
	}

	public void setEnchant(boolean enchant) {
		this.enchant = enchant;
	}

	public String getLootKind() {
		return lootKind;
	}

	public void setLootKind(String lootKind) {
		this.lootKind = lootKind;
	}

	public class ModifyDropsResult extends EffectResult {

		private final Collection<ItemStack> newDrops, originalDrops;
		private final boolean changed;

		public ModifyDropsResult(int level, Collection<ItemStack> newDrops, Collection<ItemStack> originalDrops, boolean changed) {
			super(level);
			this.newDrops = newDrops;
			this.originalDrops = originalDrops;
			this.changed = changed;
		}

		public Collection<ItemStack> getNewDrops() {
			return newDrops;
		}

		public Collection<ItemStack> getOriginalDrops() {
			return originalDrops;
		}

		public boolean isChanged() {
			return changed;
		}
	}

}
