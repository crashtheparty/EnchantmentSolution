package org.ctp.enchantmentsolution.interfaces;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemSlot;
import org.ctp.crashapi.item.ItemSlotType;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.ESEntity;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class InterfaceUtils {

	public static ItemStack[] getItems(LivingEntity entity, EnchantmentWrapper enchantment, EnchantmentItemLocation location, EnchantmentMultipleType type) {
		ItemSlot[] inv = null;

		if (entity instanceof Player) {
			ESPlayer esPlayer = EnchantmentSolution.getESPlayer((Player) entity);
			inv = esPlayer.getInventoryItemsAndType();
		} else if (entity instanceof LivingEntity) {
			ESEntity esEntity = EnchantmentSolution.getESEntity(entity);
			inv = esEntity.getInventoryItemsAndType();
		} else
			return new ItemStack[0];

		ItemStack[] items = new ItemStack[1];
		for(ItemSlot slot: inv) {
			if (slot == null) continue;
			ItemStack item = slot.getItem();
			if (contains(location, slot.getType()) && EnchantmentUtils.hasEnchantment(item, enchantment)) items = addItem(items, item, enchantment, type);
		}

		return items;
	}

	public static ItemStack[] getItems(LivingEntity entity, EnchantmentWrapper enchantment, EnchantmentItemLocation location, EnchantmentMultipleType type,
	ItemSlot item) {
		if (item == null) return getItems(entity, enchantment, location, type);
		ItemSlot[] inv = null;

		if (entity instanceof Player) {
			ESPlayer esPlayer = EnchantmentSolution.getESPlayer((Player) entity);
			inv = esPlayer.getInventoryItemsAndType(item);
		} else if (entity instanceof LivingEntity) {
			ESEntity esEntity = EnchantmentSolution.getESEntity(entity);
			inv = esEntity.getInventoryItemsAndType(item);
		} else
			return new ItemStack[0];

		ItemStack[] items = new ItemStack[1];
		for(ItemSlot slot: inv) {
			if (slot == null) continue;
			ItemStack i = slot.getItem();
			if (contains(location, slot.getType()) && EnchantmentUtils.hasEnchantment(i, enchantment)) items = addItem(items, i, enchantment, type);
		}

		return items;
	}

	public static ItemStack[] getItems(LivingEntity entity, EnchantmentWrapper enchantment, EnchantmentItemLocation location, EnchantmentMultipleType type,
	ItemStack item, ItemSlotType slotType) {
		ItemSlot[] inv = null;

		if (entity instanceof Player) {
			ESPlayer esPlayer = EnchantmentSolution.getESPlayer((Player) entity);
			inv = esPlayer.getInventoryItemsAndType();
		} else if (entity instanceof LivingEntity) {
			ESEntity esEntity = EnchantmentSolution.getESEntity(entity);
			inv = esEntity.getInventoryItemsAndType();
		} else
			return new ItemStack[0];
		ItemStack[] items = new ItemStack[1];
		for(int j = 0; j < inv.length; j++) {
			ItemSlot slot = inv[j];
			if (slot == null) continue;
			ItemStack i = slot.getItem();
			if (slot.getType() == slotType) i = item;
			if (contains(location, slot.getType()) && EnchantmentUtils.hasEnchantment(i, enchantment)) items = addItem(items, i, enchantment, type);
		}

		return items;
	}

	public static int getEnchantmentLevel(EnchantmentWrapper enchantment, ItemStack[] items) {
		int level = 0;
		for(ItemStack item: items)
			level += EnchantmentUtils.getLevel(item, enchantment);

		return level;
	}

	private static boolean contains(EnchantmentItemLocation location, ItemSlotType type) {
		for(ItemSlotType t: location.getTypes())
			if (t == type) return true;
		return false;
	}

	private static ItemStack[] addItem(ItemStack[] items, ItemStack item, EnchantmentWrapper enchantment, EnchantmentMultipleType type) {
		if (items.length == 1 && items[0] == null) {
			items[0] = item;
			return items;
		}
		if (type == EnchantmentMultipleType.HIGHEST) {
			if (EnchantmentUtils.getLevel(items[0], enchantment) < EnchantmentUtils.getLevel(item, enchantment)) items[0] = item;
			return items;
		}
		ItemStack[] newItems = new ItemStack[items.length + 1];
		for(int i = 0; i < items.length; i++)
			newItems[i] = items[i];
		newItems[newItems.length - 1] = item;
		return newItems;
	}

}
