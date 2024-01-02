package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobHeads;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.drops.BeheadingEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerExistsCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.AddDropsEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Beheading extends AddDropsEffect {

	public Beheading() {
		super(RegisterEnchantments.BEHEADING, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGH, new DeathCondition[] { new KillerExistsCondition(false) });
	}

	@Override
	public AddDropsResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		ItemStack skull = MobHeads.getMobHead((LivingEntity) killed, killer.hasPermission("enchantmentsolution.abilities.player-skulls"), killer.hasPermission("enchantmentsolution.abilities.custom-skulls"));
		setExtraDrops(skull);
		AddDropsResult result = super.run(killer, killed, items, drops, event);

		int level = result.getLevel();
		if (level == 0) return null;
		double chance = level * .05;
		double random = Math.random();
		if (chance > random) {
			BeheadingEvent beheading = new BeheadingEvent((LivingEntity) killer, (LivingEntity) killed, level, result.getDrops(), drops, true);
			Bukkit.getPluginManager().callEvent(beheading);

			if (!beheading.isCancelled()) {
				Collection<ItemStack> newDrops = beheading.getDrops();
				if (killer instanceof Player && heads(Material.WITHER_SKELETON_SKULL, newDrops) >= 2) AdvancementUtils.awardCriteria((Player) killer, ESAdvancement.DOUBLE_HEADER, "wither_skull");
				if (event instanceof PlayerDeathEvent && ((PlayerDeathEvent) event).getKeepInventory() && !beheading.isKeepInventoryOverride()) return null;
				if (killer instanceof Player) AdvancementUtils.awardCriteria((Player) killer, ESAdvancement.HEADHUNTER, "player_head");

				event.getDrops().clear();
				for(ItemStack drop: newDrops)
					event.getDrops().add(drop);
				return new AddDropsResult(level, newDrops);
			}
		}
		return null;
	}

	private int heads(Material type, Collection<ItemStack> drops) {
		int skulls = 0;
		if (drops != null) for(ItemStack i: drops)
			if (i.getType() == type) skulls += i.getAmount();
		return skulls;
	}

}
