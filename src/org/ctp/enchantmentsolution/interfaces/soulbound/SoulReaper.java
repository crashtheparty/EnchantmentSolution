package org.ctp.enchantmentsolution.interfaces.soulbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.soul.SoulReaperEvent;
import org.ctp.enchantmentsolution.events.soul.SoulboundEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.SoulboundCondition;
import org.ctp.enchantmentsolution.interfaces.effects.soulbound.StealSoulItemEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class SoulReaper extends StealSoulItemEffect {

	public SoulReaper() {
		super(RegisterEnchantments.SOUL_REAPER, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, "%level%", false, true, new SoulboundCondition[0]);
	}

	@Override
	public StealSoulItemResult run(Entity killer, Player killed, ItemStack[] items, Collection<ItemStack> soulDrops, SoulboundEvent event) {
		StealSoulItemResult result = super.run(killer, killed, items, soulDrops, event);

		Player pKiller = (Player) killer;

		int level = result.getLevel();
		if (level == 0) return null;

		List<ItemStack> savedItems = new ArrayList<ItemStack>();
		savedItems.addAll(soulDrops);

		List<ItemStack> soulReaper = new ArrayList<ItemStack>();
		if (level > savedItems.size()) soulReaper.addAll(savedItems);
		else {
			while (soulReaper.size() < level) {
				int random = (int) (Math.random() * savedItems.size());
				soulReaper.add(savedItems.remove(random));
			}
			savedItems = new ArrayList<ItemStack>();
			savedItems.addAll(event.getSavedItems());
		}

		if (soulReaper.size() > 0) {
			SoulReaperEvent soulReaperEvent = new SoulReaperEvent(killed, pKiller, level, soulReaper);
			Bukkit.getPluginManager().callEvent(soulReaperEvent);

			if (!soulReaperEvent.isCancelled()) {
				List<ItemStack> reaped = soulReaperEvent.getReapedItems();
				if (reaped.size() > 0) {
					AdvancementUtils.awardCriteria(pKiller, ESAdvancement.FEAR_THE_REAPER, "reaper");
					killed.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, killed.getLocation(), 50, 0.2, 2, 0.2);
					killed.getWorld().playSound(killed.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.6f, 0.6f);
					for(ItemStack item: reaped)
						if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.SOUL_REAPER)) AdvancementUtils.awardCriteria(pKiller, ESAdvancement.REAPED_THE_REAPER, "reaper");
				}
				for(ItemStack i: reaped)
					savedItems.remove(i);
				event.getSavedItems().clear();
				for(ItemStack i: savedItems)
					event.getSavedItems().add(i);
				return new StealSoulItemResult(level, reaped.size());
			}
		}
		return null;
	}

}
