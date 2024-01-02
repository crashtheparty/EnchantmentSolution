package org.ctp.enchantmentsolution.interfaces.fishing;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.fishing.AnglerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.FishingCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.fishing.FishingStateCondition;
import org.ctp.enchantmentsolution.interfaces.effects.fishing.FishingChangeDropEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentDrops;
import org.ctp.enchantmentsolution.utils.items.Interactions;
import org.ctp.enchantmentsolution.utils.items.ItemDrop;

public class Angler extends FishingChangeDropEffect {

	public Angler() {
		super(RegisterEnchantments.ANGLER, EnchantmentMultipleType.ALL, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, new EnchantmentDrops("/resources/drops/angler.yml", false), new FishingCondition[] { new FishingStateCondition(false, PlayerFishEvent.State.CAUGHT_FISH)});
	}

	@Override
	public FishingChangeDropResult run(Player player, ItemStack[] items, PlayerFishEvent event) {
		FishingChangeDropResult result = super.run(player, items, event);
		int level = result.getLevel();

		if (level == 0 || result.getInteractions() == null) return null;

		Interactions interactions = result.getInteractions();
		ItemDrop dropItem = null;
		if (interactions.isPoolChance()) {
			int numDrops = interactions.getNumDrops(level) > 1 ? 1 : 0;
			if (numDrops > 0) {
				double totalChance = 0;
				for(ItemDrop drop: interactions.getDrops())
					if (drop.getType().hasMaterial()) totalChance += drop.getChance(level, 0, 0);
				if (totalChance > 0) {
					double random = Math.random() * totalChance;
					for(ItemDrop drop: interactions.getDrops())
						if (drop.getType().hasMaterial()) {
							random -= drop.getChance(level, 0, 0);
							if (random <= 0) {
								dropItem = drop;
								break;
							}
						}
				}
			}
		} else
			for(ItemDrop drop: interactions.getDrops())
				if (drop.getType().hasMaterial()) {
					double chance = drop.getChance(level, 0, 0);
					double random = Math.random();
					if (chance > random) {
						dropItem = drop;
						break;
					}
				}
		if (dropItem != null) {
			ItemStack drop = new ItemStack(dropItem.getType().getMaterial(), dropItem.getAmount(level, 0, 0, result.getOldDrop().getAmount()));

			AnglerEvent angler = new AnglerEvent(player, level, drop, result.getOldDrop(), interactions.isOverride(), 0);
			Bukkit.getPluginManager().callEvent(angler);

			if (!angler.isCancelled()) {
				if (angler.getExp() > 0) event.setExpToDrop(event.getExpToDrop() + angler.getExp());
				ItemStack caught = angler.getDrop();
				Material type = caught.getType();
				((Item) event.getCaught()).setItemStack(caught);
				if (type == Material.COOKED_COD) type = Material.COD;
				if (type == Material.COOKED_SALMON) type = Material.SALMON;
				AdvancementUtils.awardCriteria(player, ESAdvancement.FED_FOR_A_LIFETIME, type.name().toLowerCase(Locale.ROOT));
				return result;
			}
		}

		return null;
	}

}
