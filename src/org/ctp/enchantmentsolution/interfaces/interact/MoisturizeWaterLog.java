package org.ctp.enchantmentsolution.interfaces.interact;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemMoisturizeType;
import org.ctp.enchantmentsolution.events.interact.MoisturizeEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.effects.interact.WaterLogEffect;

public class MoisturizeWaterLog extends WaterLogEffect {

	public MoisturizeWaterLog() {
		super(RegisterEnchantments.MOISTURIZE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, true, false, new InteractCondition[0]);
	}
	
	@Override
	public WaterLogResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		WaterLogResult result = super.run(player, items, event);
		
		if (result.getLevel() == 0) return null;
		ItemStack item = result.getItem();
		Block block = event.getClickedBlock();
		Sound sound = result.getSound();
		PlayerBucketEmptyEvent blockEvent = new PlayerBucketEmptyEvent(player, block, block, event.getBlockFace(), Material.WATER_BUCKET, item, event.getHand());
		Bukkit.getPluginManager().callEvent(blockEvent);
		if (((Cancellable) blockEvent).isCancelled()) return null;
		MoisturizeEvent moisturize = new MoisturizeEvent(player, item, block, ItemMoisturizeType.WATERLOG, sound);
		Bukkit.getPluginManager().callEvent(moisturize);
		
		if (!moisturize.isCancelled()) {
			Waterlogged water = (Waterlogged) block.getBlockData();
			water.setWaterlogged(true);

			block.setBlockData(water);
			DamageUtils.damageItem(player, item);
			player.playSound(player.getLocation(), moisturize.getSound(), 1, 1);
			player.incrementStatistic(Statistic.USE_ITEM, item.getType());
			event.setCancelled(true);
			return result;
		}
		
		return null;
	}

}
