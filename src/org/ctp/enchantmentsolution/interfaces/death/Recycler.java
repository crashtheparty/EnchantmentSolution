package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Arrays;
import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.player.RecyclerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KilledIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerExistsCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.LootToExpEffect;
import org.ctp.enchantmentsolution.interfaces.effects.death.RecycleLoot;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Recycler extends LootToExpEffect {

	public static final Collection<RecycleLoot> RECYCLER = Arrays.asList(new RecycleLoot("LEATHER", 0, 2), new RecycleLoot("BEEF", -1, 1), new RecycleLoot("COOKED_BEEF", 0, 2), new RecycleLoot("STRING", 0, 1), new RecycleLoot("FEATHER", 0, 1), new RecycleLoot("CHICKEN", -1, 1), new RecycleLoot("COOKED_CHICKEN", 0, 2), new RecycleLoot("COD", -2, 1), new RecycleLoot("COOKED_COD", -1, 1), new RecycleLoot("BAMBOO", -1, 1), new RecycleLoot("PORKCHOP", -1, 1), new RecycleLoot("COOKED_PORKCHOP", 0, 2), new RecycleLoot("SALMON", -2, 1), new RecycleLoot("COOKED_SALMON", -1, 1), new RecycleLoot("RABBIT", -1, 1), new RecycleLoot("COOKED_RABBIT", 0, 2), new RecycleLoot("PUFFERFISH", -1, 1), new RecycleLoot("RABBIT_HIDE", 0, 1), new RecycleLoot("MUTTON", -1, 1), new RecycleLoot("COOKED_MUTTON", 0, 2), new RecycleLoot("BONE", 0, 2), new RecycleLoot("INK_SAC", 0, 3), new RecycleLoot("TROPICAL_FISH", -1, 1), new RecycleLoot("ROTTEN_FLESH", 0, 2), new RecycleLoot("BLAZE_ROD", 1, 3), new RecycleLoot("SPIDER_EYE", 0, 2), new RecycleLoot("GUNPOWDER", 0, 2), new RecycleLoot("PRISMARINE_SHARD", -1, 2), new RecycleLoot("PRISMARINE_CRYSTALS", -1, 2), new RecycleLoot("ENDER_PEARL", 1, 3), new RecycleLoot("GHAST_TEAR", 1, 4), new RecycleLoot("MAGMA_CREAM", 1, 3), new RecycleLoot("PHANTOM_MEMBRANE", 1, 3), new RecycleLoot("ARROW", -1, 1), new RecycleLoot("SHULKER_SHELL", 2, 4), new RecycleLoot("SLIME_BALL", 0, 1), new RecycleLoot("EMERALD", 0, 3), new RecycleLoot("GLASS_BOTTLE", -2, 1), new RecycleLoot("GLOWSTONE_DUST", -1, 2), new RecycleLoot("REDSTONE", 0, 2), new RecycleLoot("SUGAR", 0, 1), new RecycleLoot("COAL", 0, 2), new RecycleLoot("GOLD_NUGGET", 0, 1));

	public Recycler() {
		super(RegisterEnchantments.RECYCLER, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, RECYCLER, new DeathCondition[] { new KillerExistsCondition(false), new KilledIsTypeCondition(true, new MobData("PLAYER")), new KillerIsTypeCondition(false, new MobData("PLAYER")) });
	}

	@Override
	public LootToExpResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		int exp = event.getDroppedExp();
		LootToExpResult result = super.run(killer, killed, items, drops, event);
		if (result.getLevel() == 0) return null;
		if (result.willRecycle()) {
			Player player = (Player) killer;
			RecyclerEvent recyclerEvent = new RecyclerEvent(player, exp, exp + result.getExp());
			Bukkit.getPluginManager().callEvent(recyclerEvent);

			if (!recyclerEvent.isCancelled() && recyclerEvent.getNewExp() >= 0) {
				event.setDroppedExp(recyclerEvent.getNewExp());
				int finalExp = recyclerEvent.getNewExp() - recyclerEvent.getOldExp();
				AdvancementUtils.awardCriteria(player, ESAdvancement.ENVIRONMENTAL_PROTECTION, "experience", finalExp);
				event.getDrops().clear();
				event.getDrops().addAll(result.getRemainingLoot());
				return new LootToExpResult(result.getLevel(), finalExp, result.getRemainingLoot());
			}
		}
		return null;
	}

}
