package org.ctp.enchantmentsolution.interfaces.interact;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemMoisturizeType;
import org.ctp.enchantmentsolution.events.interact.MoisturizeEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.effects.interact.ChangeBlockTypeEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentDrops;
import org.ctp.enchantmentsolution.utils.items.Interactions;
import org.ctp.enchantmentsolution.utils.items.ItemDrop;

public class MoisturizeInteract extends ChangeBlockTypeEffect {

	public MoisturizeInteract() {
		super(RegisterEnchantments.MOISTURIZE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, new EnchantmentDrops("/resources/drops/moisturize.yml", false), new InteractCondition[0]);
	}

	@Override
	public ChangeBlockTypeResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		ChangeBlockTypeResult result = super.run(player, items, event);
		Block block = event.getClickedBlock();
		ItemStack item = items[0];
		int level = result.getLevel();

		if (level == 0 || result.getInteractions() == null) return null;

		Interactions interactions = result.getInteractions();
		List<ItemDrop> drops = new ArrayList<ItemDrop>();
		if (interactions.isPoolChance()) {
			int numDrops = interactions.getNumDrops(level);
			if (numDrops > 0) {
				List<ItemDrop> usedDrops = new ArrayList<ItemDrop>();
				do {
					double totalChance = 0;
					for(ItemDrop drop: interactions.getDrops()) {
						boolean used = false;
						for(ItemDrop d: usedDrops)
							if (d.equals(drop)) used = true;
						if (!used && drop.getType().hasMaterial()) totalChance += drop.getChance(level, 0, 0);
					}
					if (totalChance == 0) break;
					double random = Math.random() * totalChance;
					ItemDrop finalDrop = null;
					for(ItemDrop drop: interactions.getDrops()) {
						boolean used = false;
						for(ItemDrop d: usedDrops)
							if (d.equals(drop)) used = true;
						if (!used && drop.getType().hasMaterial()) {
							random -= drop.getChance(level, 0, 0);
							if (random <= 0) {
								finalDrop = drop;
								break;
							}
						}
					}
					if (finalDrop != null) {
						drops.add(finalDrop);
						usedDrops.add(finalDrop);
						numDrops--;
					} else
						break;
				} while (numDrops > 0);
			} else { /* placeholder */ }
		} else
			for(ItemDrop drop: interactions.getDrops())
				if (drop.getType().hasMaterial()) {
					double chance = drop.getChance(level, 0, 0);
					double random = Math.random();
					if (chance > random) drops.add(drop);
				}

		ItemStack change = null;
		for(ItemDrop d: drops) {
			ItemStack i = new ItemStack(d.getType().getMaterial(), d.getAmount(level, 0, 0, 1));
			if (i.getType() != Material.AIR && i.getAmount() > 0) {
				change = i;
				break;
			}
		}
		if (change == null) return null;

		MoisturizeEvent moisturize = new MoisturizeEvent(player, item, block, ItemMoisturizeType.CHANGE_TYPE, Sound.ENTITY_SHEEP_SHEAR);
		Bukkit.getPluginManager().callEvent(moisturize);

		if (!moisturize.isCancelled()) {
			if (block.getType() == Material.CRACKED_STONE_BRICKS) AdvancementUtils.awardCriteria(player, ESAdvancement.REPAIRED, "broken_bricks");
			if (block.getType().name().contains("CONCRETE")) AdvancementUtils.awardCriteria(player, ESAdvancement.JUST_ADD_WATER, "concrete");
			block.setType(change.getType());
			DamageUtils.damageItem(player, item);
			player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
			player.incrementStatistic(Statistic.USE_ITEM, item.getType());
			event.setCancelled(true);

			return new ChangeBlockTypeResult(level, interactions);
		}

		return null;
	}

}
