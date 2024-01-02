package org.ctp.enchantmentsolution.interfaces.interact;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Campfire;
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
import org.ctp.enchantmentsolution.interfaces.effects.interact.ExtinguishEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class MoisturizeExtinguish extends ExtinguishEffect {

	public MoisturizeExtinguish() {
		super(RegisterEnchantments.MOISTURIZE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, new InteractCondition[0]);
	}

	@Override
	public ExtinguishResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		ExtinguishResult result = super.run(player, items, event);

		if (result.getLevel() == 0) return null;
		ItemStack item = items[0];
		Block block = event.getClickedBlock();
		Sound sound = result.getSound();

		MoisturizeEvent moisturize = new MoisturizeEvent(player, item, block, ItemMoisturizeType.EXTINGUISH, sound);
		Bukkit.getPluginManager().callEvent(moisturize);

		if (!moisturize.isCancelled()) {
			Campfire fire = (Campfire) block.getBlockData();
			fire.setLit(false);
			block.setBlockData(fire);
			DamageUtils.damageItem(player, item);
			player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
			player.incrementStatistic(Statistic.USE_ITEM, item.getType());
			AdvancementUtils.awardCriteria(player, ESAdvancement.EASY_OUT, "campfire");
			return result;
		}

		return null;
	}

}
