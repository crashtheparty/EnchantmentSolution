package org.ctp.enchantmentsolution.listeners.effects;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.InterfaceRegistry;
import org.ctp.enchantmentsolution.interfaces.InterfaceUtils;
import org.ctp.enchantmentsolution.interfaces.effects.block.*;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;

public class BlockListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(BlockDropItemEvent event) {
		handleBlockDrop(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(BlockDropItemEvent event) {
		handleBlockDrop(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(BlockDropItemEvent event) {
		handleBlockDrop(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(BlockDropItemEvent event) {
		handleBlockDrop(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(BlockDropItemEvent event) {
		handleBlockDrop(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(BlockDropItemEvent event) {
		handleBlockDrop(event, EventPriority.MONITOR);
	}

	private void handleBlockDrop(BlockDropItemEvent event, EventPriority priority) {
		Player player = event.getPlayer();
		HashMap<EnchantmentWrapper, List<BlockDropItemEffect>> effects = InterfaceRegistry.getBlockDropItemEffects();
		Iterator<Entry<EnchantmentWrapper, List<BlockDropItemEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<BlockDropItemEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), event) || isDisabled(player, entry.getKey())) continue;
			List<BlockDropItemEffect> effs = entry.getValue();
			for(BlockDropItemEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(player, event.getBlockState().getBlockData(), event)) effect.run(player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType()), event.getBlockState().getBlockData(), event);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(BlockBreakEvent event) {
		handleBlockBreak(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(BlockBreakEvent event) {
		handleBlockBreak(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(BlockBreakEvent event) {
		handleBlockBreak(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(BlockBreakEvent event) {
		handleBlockBreak(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(BlockBreakEvent event) {
		handleBlockBreak(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(BlockBreakEvent event) {
		handleBlockBreak(event, EventPriority.MONITOR);
	}

	private void handleBlockBreak(BlockBreakEvent event, EventPriority priority) {
		Player player = event.getPlayer();
		HashMap<EnchantmentWrapper, List<BlockBreakEffect>> effects = InterfaceRegistry.getBlockBreakEffects();
		Iterator<Entry<EnchantmentWrapper, List<BlockBreakEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<BlockBreakEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), event) || isDisabled(player, entry.getKey())) continue;
			List<BlockBreakEffect> effs = entry.getValue();
			for(BlockBreakEffect effect: effs)
				if (effect.getPriority() == priority && effect.willRun(player, event.getBlock().getBlockData(), event)) effect.run(player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType()), event.getBlock().getBlockData(), event);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(BlockExpEvent event) {
		handleBlockExp(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(BlockExpEvent event) {
		handleBlockExp(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(BlockExpEvent event) {
		handleBlockExp(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(BlockExpEvent event) {
		handleBlockExp(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(BlockExpEvent event) {
		handleBlockExp(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(BlockExpEvent event) {
		handleBlockExp(event, EventPriority.MONITOR);
	}

	private void handleBlockExp(BlockExpEvent event, EventPriority priority) {
		Player player = null;
		if (event instanceof BlockBreakEvent) player = ((BlockBreakEvent) event).getPlayer();
		else if (event instanceof FurnaceExtractEvent) player = ((FurnaceExtractEvent) event).getPlayer();
		if (player == null) return;
		HashMap<EnchantmentWrapper, List<BlockExpEffect>> effects = InterfaceRegistry.getBlockExpEffects();
		Iterator<Entry<EnchantmentWrapper, List<BlockExpEffect>>> iter = effects.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<EnchantmentWrapper, List<BlockExpEffect>> entry = iter.next();
			if (!canRun(entry.getKey(), event) || isDisabled(player, entry.getKey())) continue;
			List<BlockExpEffect> effs = entry.getValue();
			for (BlockExpEffect effect : effs) if (effect.getPriority() == priority && effect.willRun(player, event.getBlock().getBlockData(), event)) effect.run(player, InterfaceUtils.getItems(player, effect.getEnchantment(), effect.getLocation(), effect.getType()), event.getBlock().getBlockData(), event);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL)
	public void normal(EntityChangeBlockEvent event) {
		handleEntityChangeBlock(event, EventPriority.NORMAL);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void high(EntityChangeBlockEvent event) {
		handleEntityChangeBlock(event, EventPriority.HIGH);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void highest(EntityChangeBlockEvent event) {
		handleEntityChangeBlock(event, EventPriority.HIGHEST);
	}

	@EventHandler(priority = EventPriority.LOW)
	public void low(EntityChangeBlockEvent event) {
		handleEntityChangeBlock(event, EventPriority.LOW);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void lowest(EntityChangeBlockEvent event) {
		handleEntityChangeBlock(event, EventPriority.LOWEST);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void monitor(EntityChangeBlockEvent event) {
		handleEntityChangeBlock(event, EventPriority.MONITOR);
	}

	private void handleEntityChangeBlock(EntityChangeBlockEvent event, EventPriority priority) {
		Entity entity = event.getEntity();
		if (entity instanceof HumanEntity) {
			HumanEntity human = (HumanEntity) entity;
			HashMap<EnchantmentWrapper, List<EntityChangeBlockEffect>> effects = InterfaceRegistry.getChangeBlockEffects();
			Iterator<Entry<EnchantmentWrapper, List<EntityChangeBlockEffect>>> iter = effects.entrySet().iterator();
			while (iter.hasNext()) {
				Entry<EnchantmentWrapper, List<EntityChangeBlockEffect>> entry = iter.next();
				if (!canRun(entry.getKey(), event) || human instanceof Player && isDisabled((Player) human, entry.getKey())) continue;
				List<EntityChangeBlockEffect> effs = entry.getValue();
				for(EntityChangeBlockEffect effect: effs)
					if (effect.getPriority() == priority && effect.willRun(human, event.getBlock(), event)) effect.run(human, event.getBlock(), InterfaceUtils.getItems(human, effect.getEnchantment(), effect.getLocation(), effect.getType()), event);
			}
		}
	}
}
