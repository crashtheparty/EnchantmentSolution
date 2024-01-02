package org.ctp.enchantmentsolution.interfaces.interact;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.ExpNMS;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.crashapi.utils.LocationUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.interact.FlowerGiftEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.effects.interact.InteractCreateDropEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.*;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class FlowerGift extends InteractCreateDropEffect {

	public FlowerGift() {
		super(RegisterEnchantments.FLOWER_GIFT, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, new EnchantmentDrops("/resources/drops/flower_gift.yml", false), Particle.VILLAGER_HAPPY, null, Particle.VILLAGER_ANGRY, null, 30, new InteractCondition[0]);
	}

	@Override
	public CreateDropResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		CreateDropResult result = super.run(player, items, event);
		Block block = event.getClickedBlock();
		ItemStack item = items[0];
		int level = result.getLevel(), fortune = getLevel(items, Enchantment.LOOT_BONUS_BLOCKS), looting = getLevel(items, Enchantment.LOOT_BONUS_MOBS);

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
						if (!used && drop.getType().hasMaterial()) totalChance += drop.getChance(level, fortune, looting);
					}
					if (totalChance == 0) break;
					double random = Math.random() * totalChance;
					ItemDrop finalDrop = null;
					for(ItemDrop drop: interactions.getDrops()) {
						boolean used = false;
						for(ItemDrop d: usedDrops)
							if (d.equals(drop)) used = true;
						if (!used && drop.getType().hasMaterial()) {
							random -= drop.getChance(level, fortune, looting);
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
					double chance = drop.getChance(level, fortune, looting);
					double random = Math.random();
					if (chance > random) drops.add(drop);
				}

		List<ItemStack> dropItems = new ArrayList<ItemStack>();
		for(ItemDrop d: drops) {
			ItemStack i = new ItemStack(d.getType().getMaterial(), d.getAmount(level, fortune, looting, 1));
			if (i.getType() != Material.AIR) dropItems.add(i);
		}

		FlowerGiftEvent flowerGift = new FlowerGiftEvent(player, item, block, dropItems, interactions.getExp(level), LocationUtils.offset(block.getLocation()));
		Bukkit.getPluginManager().callEvent(flowerGift);

		if (!flowerGift.isCancelled()) {
			Location loc = flowerGift.getDropLocation();
			if (flowerGift.getDrops().size() > 0) {
				if (AbilityUtils.isDoubleFlower(flowerGift.getBlock().getType())) loc.add(0, 1, 0);
				if (getSuccess() != null && getNumParticles() > 0) player.getWorld().spawnParticle(getSuccess(), loc, getNumParticles(), 0.2, 0.5, 0.2);
				if (getSuccessSound() != null) player.getWorld().playSound(loc, getSuccessSound(), 1, 1);
				for(ItemStack drop: flowerGift.getDrops())
					if (AbilityUtils.isDoubleFlower(drop.getType())) AdvancementUtils.awardCriteria(player, ESAdvancement.BONEMEAL_PLUS, "bonemeal");
					else if (drop.getType() == Material.WITHER_ROSE) AdvancementUtils.awardCriteria(player, ESAdvancement.JUST_AS_SWEET, "wither_rose");

				ItemUtils.dropItems(flowerGift.getDrops(), flowerGift.getDropLocation());
				if (flowerGift.getExp() > 0) ExpNMS.dropExp(loc, flowerGift.getExp(), true, player);
			} else {
				if (getFailure() != null && getNumParticles() > 0) player.getWorld().spawnParticle(getFailure(), loc, getNumParticles(), 0.2, 0.5, 0.2);
				if (getFailureSound() != null) player.getWorld().playSound(loc, getFailureSound(), 1, 1);
			}
			player.incrementStatistic(Statistic.USE_ITEM, item.getType());
			ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
			esPlayer.setCooldown(RegisterEnchantments.FLOWER_GIFT, flowerGift.getCooldownTicks());
			DamageUtils.damageItem(player, item);
			return new CreateDropResult(level, interactions);
		}

		return null;
	}

}
