package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.drops.PillageEntityEvent;
import org.ctp.enchantmentsolution.events.drops.PillagePlayerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KilledIsLootableCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerExistsCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.LootingDeathEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Pillage extends LootingDeathEffect {

	public Pillage() {
		super(RegisterEnchantments.PILLAGE, EnchantmentMultipleType.ALL, EnchantmentItemLocation.ATTACKED, EventPriority.HIGH, true, new DeathCondition[] { new KillerExistsCondition(false), new KilledIsLootableCondition(false) });
	}

	@Override
	public LootingDeathResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		LootingDeathResult result = super.run(killer, killed, items, drops, event);
		int level = result.getLevel();
		if (level == 0) return null;
		Collection<ItemStack> resultDrops = result.getDrops();

		if (killer instanceof Player) {
			Player player = (Player) killer;
			PillagePlayerEvent pillage = new PillagePlayerEvent(player, level, resultDrops, isOverride());
			Bukkit.getPluginManager().callEvent(pillage);
			if (!pillage.isCancelled()) {
				if (pillage.isOverride()) event.getDrops().clear();
				event.getDrops().addAll(pillage.getDrops());
				if (event.getEntity().getType() == EntityType.PILLAGER) AdvancementUtils.awardCriteria(player, ESAdvancement.LOOK_WHAT_YOU_MADE_ME_DO, "pillage");
				return new LootingDeathResult(level, resultDrops);
			}
		} else {
			PillageEntityEvent pillage = new PillageEntityEvent(killer, level, resultDrops, isOverride());
			Bukkit.getPluginManager().callEvent(pillage);
			if (!pillage.isCancelled()) {
				if (pillage.isOverride()) event.getDrops().clear();
				event.getDrops().addAll(pillage.getDrops());
				return new LootingDeathResult(level, resultDrops);
			}
		}
		return null;
	}

}
