package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.soul.SoulboundEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KilledIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.SaveContentsEffect;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class SoulboundDeath extends SaveContentsEffect {

	public SoulboundDeath() {
		super(RegisterEnchantments.SOULBOUND, EnchantmentMultipleType.ALL, EnchantmentItemLocation.IN_INVENTORY, EventPriority.HIGHEST, new DeathCondition[] { new KilledIsTypeCondition(false, new MobData("PLAYER")) });
	}

	@Override
	public SaveContentsResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		SaveContentsResult result = super.run(killer, killed, items, drops, event);

		if (result.getLevel() == 0) return null;

		Player player = (Player) killed;
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		if (((PlayerDeathEvent) event).getKeepInventory()) return null;

		SoulboundEvent soulboundEvent = new SoulboundEvent(player, killer, Arrays.asList(items));
		Bukkit.getPluginManager().callEvent(soulboundEvent);

		if (!soulboundEvent.isCancelled()) {
			List<ItemStack> savedItems = soulboundEvent.getSavedItems();

			esPlayer.setSoulItems(savedItems);
			return result;
		}
		return null;
	}

}
