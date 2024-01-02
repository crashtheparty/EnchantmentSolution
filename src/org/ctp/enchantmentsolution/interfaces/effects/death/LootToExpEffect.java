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

public abstract class LootToExpEffect extends EntityKillerEffect {

	private Collection<RecycleLoot> loot;

	public LootToExpEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	Collection<RecycleLoot> loot, DeathCondition[] conditions) {
		super(enchantment, type, location, priority, conditions);
		this.loot = loot;
	}

	@Override
	public LootToExpResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		int level = getLevel(items);

		int recyclerExp = 0;
		Collection<ItemStack> remainingLoot = new ArrayList<ItemStack>();
		for(ItemStack it: drops) {
			boolean recycled = false;
			for(RecycleLoot r: loot)
				if (r.getMaterial() == it.getType()) {
					recyclerExp += r.getRandomExp(it.getAmount());
					recycled = true;
					break;
				}
			if (!recycled) remainingLoot.add(it);
		}

		return new LootToExpResult(level, recyclerExp, remainingLoot);
	}

	public Collection<RecycleLoot> getLoot() {
		return loot;
	}

	public class LootToExpResult extends EffectResult {

		private final int exp;
		private final Collection<ItemStack> remainingLoot;

		public LootToExpResult(int level, int exp, Collection<ItemStack> remainingLoot) {
			super(level);
			this.exp = exp;
			this.remainingLoot = remainingLoot;
		}

		public int getExp() {
			return exp;
		}

		public boolean willRecycle() {
			return exp > 0;
		}

		public Collection<ItemStack> getRemainingLoot() {
			return remainingLoot;
		}
	}

}