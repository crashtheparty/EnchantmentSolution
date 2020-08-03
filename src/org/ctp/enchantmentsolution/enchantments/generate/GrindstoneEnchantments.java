package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class GrindstoneEnchantments extends GenerateEnchantments {

	private int takeCost;
	private ItemStack itemTwo, combinedItem, takenItem;
	private boolean canCombine, takeEnchantments;

	private GrindstoneEnchantments(Player player, ItemStack item, ItemStack itemTwo, boolean takeEnchantments) {
		super(player, item, EnchantmentLocation.NONE);
		this.itemTwo = itemTwo;

		setCanCombine();
		if (canCombine) combineItems();
		if (takeEnchantments) {
			setTakeEnchantments();
			if (canTakeEnchantments()) takeEnchantments();
		}
	}

	public static GrindstoneEnchantments getGrindstoneEnchantments(Player player, ItemStack first, ItemStack second) {
		return new GrindstoneEnchantments(player, first, second, ConfigString.TAKE_ENCHANTMENTS.getBoolean());
	}

	private void setCanCombine() {
		if (getItem() != null && getItemTwo() != null && getItem().getType() == getItemTwo().getType()) canCombine = true;
		else if (getItem() != null && getItemTwo() == null) canCombine = true;
		else
			canCombine = false;
	}

	public boolean canCombine() {
		return canCombine;
	}

	private void setTakeEnchantments() {
		ItemStack item = getItem();
		ItemStack itemTwo = getItemTwo();
		if (item == null || itemTwo == null) {
			takeEnchantments = false;
			return;
		}
		if (item.getType() != Material.BOOK && item.getType() != Material.ENCHANTED_BOOK && item.hasItemMeta() && item.getItemMeta().hasEnchants() && itemTwo.getType() == Material.BOOK && (itemTwo.hasItemMeta() && !itemTwo.getItemMeta().hasEnchants() || !itemTwo.hasItemMeta())) takeEnchantments = true;
		else
			takeEnchantments = false;
	}

	public boolean canTakeEnchantments() {
		return takeEnchantments;
	}

	private void takeEnchantments() {
		takenItem = new ItemStack(itemTwo.getType());
		boolean book = ConfigString.USE_ENCHANTED_BOOKS.getBoolean();
		if (takenItem.getType() == Material.BOOK && book) takenItem = EnchantmentUtils.convertToEnchantedBook(takenItem);
		else if (takenItem.getType() == Material.ENCHANTED_BOOK && !book) takenItem = EnchantmentUtils.convertToRegularBook(takenItem);

		List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();
		for(Iterator<java.util.Map.Entry<Enchantment, Integer>> it = getItem().getEnchantments().entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			for(CustomEnchantment ench: RegisterEnchantments.getEnchantments())
				if (ench.getRelativeEnchantment().equals(e.getKey())) enchantments.add(new EnchantmentLevel(ench, e.getValue()));
		}

		if (combinedItem != null) if (ConfigString.SET_REPAIR_COST.getBoolean()) combinedItem = AnvilNMS.setRepairCost(combinedItem, AnvilNMS.getRepairCost(getItem()));
		else
			combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);

		takenItem = EnchantmentUtils.addEnchantmentsToItem(takenItem, enchantments);

		setTakeCost();
	}

	private void setTakeCost() {
		int cost = 0;
		ItemStack item = getItem();
		ItemMeta itemMeta = item.clone().getItemMeta();
		Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
		for(Iterator<java.util.Map.Entry<Enchantment, Integer>> it = enchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant: RegisterEnchantments.getEnchantments())
				if (ConfigUtils.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant)) cost += level * customEnchant.multiplier(item.getType());
		}
		takeCost = Math.max(cost / ConfigString.LEVEL_DIVISOR.getInt(), 1);
	}

	public int getExperience() {
		Random random = new Random();
		byte b0 = 0;
		int j = b0;

		if (getItem() != null) j += getEnchantmentExperience(getItem());
		if (itemTwo != null) j += getEnchantmentExperience(itemTwo);

		if (j > 0) {
			int k = (int) Math.ceil(j / 2.0D);
			return k + random.nextInt(k);
		} else
			return 0;
	}

	private int getEnchantmentExperience(ItemStack itemstack) {
		int j = 0;
		Map<Enchantment, Integer> map = itemstack.getItemMeta().getEnchants();
		if (itemstack.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemstack.getItemMeta();
			map = meta.getStoredEnchants();
		}
		Iterator<Entry<Enchantment, Integer>> iterator = map.entrySet().iterator();

		while (iterator.hasNext()) {
			Entry<Enchantment, Integer> entry = iterator.next();
			Enchantment enchantment = entry.getKey();
			Integer integer = entry.getValue();

			CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(enchantment);

			if (!custom.isCurse()) j += custom.enchantability(integer);
		}

		return j;
	}

	private void combineItems() {
		ItemStack item = getItem();
		combinedItem = item.clone();
		if (item.getType().equals(Material.ENCHANTED_BOOK)) combinedItem = new ItemStack(Material.BOOK);
		combinedItem = EnchantmentUtils.removeAllEnchantments(combinedItem, false);

		if (itemTwo != null) {
			if (item.getType() != Material.BOOK && item.getType() != Material.ENCHANTED_BOOK && ItemType.hasEnchantMaterial(new ItemData(item))) {
				DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(item));
				int extraDurability = DamageUtils.getMaxDamage(itemTwo) - DamageUtils.getDamage(itemTwo) + (int) (DamageUtils.getMaxDamage(itemTwo) * .05);
				DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(item) - extraDurability);
				if (DamageUtils.getDamage(combinedItem) < 0) DamageUtils.setDamage(combinedItem, 0);
			} else
				combinedItem.setAmount(1);
		} else
			DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(item));

		if (combinedItem != null) combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);

		combinedItem = EnchantmentUtils.addEnchantmentsToItem(combinedItem, combineEnchants());
	}

	private List<EnchantmentLevel> combineEnchants() {
		ItemStack item = getItem();
		ItemMeta itemMeta = item.clone().getItemMeta();
		Map<Enchantment, Integer> firstEnchants = itemMeta.getEnchants();
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemMeta;
			firstEnchants = meta.getStoredEnchants();
		}
		List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();

		Iterator<Entry<Enchantment, Integer>> firstIter = firstEnchants.entrySet().iterator();
		while (firstIter.hasNext()) {
			Entry<Enchantment, Integer> entry = firstIter.next();
			CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(entry.getKey());
			if (custom.isCurse()) {
				boolean contains = false;
				for(EnchantmentLevel enchantment: enchantments)
					if (enchantment.getEnchant().getRelativeEnchantment().equals(entry.getKey())) {
						contains = true;
						break;
					}
				if (!contains) enchantments.add(new EnchantmentLevel(custom, entry.getValue()));
			}
		}

		if (itemTwo != null) {
			ItemMeta itemTwoMeta = itemTwo.getItemMeta();
			Map<Enchantment, Integer> secondEnchants = itemTwoMeta.getEnchants();
			if (itemTwo.getType().equals(Material.ENCHANTED_BOOK)) {
				EnchantmentStorageMeta meta = (EnchantmentStorageMeta) itemTwoMeta;
				secondEnchants = meta.getStoredEnchants();
			}
			Iterator<Entry<Enchantment, Integer>> secondIter = secondEnchants.entrySet().iterator();
			while (secondIter.hasNext()) {
				Entry<Enchantment, Integer> entry = secondIter.next();
				CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(entry.getKey());
				if (custom.isCurse()) {
					boolean contains = false;
					for(EnchantmentLevel enchantment: enchantments)
						if (enchantment.getEnchant().getRelativeEnchantment().equals(entry.getKey())) {
							contains = true;
							break;
						}
					if (!contains) enchantments.add(new EnchantmentLevel(custom, entry.getValue()));
				}
			}
		}

		for(int i = enchantments.size() - 1; i >= 0; i--) {
			EnchantmentLevel enchant = enchantments.get(i);
			if (!enchant.getEnchant().canAnvil(getPlayer().getPlayer(), enchant.getLevel())) {
				int level = enchant.getEnchant().getAnvilLevel(getPlayer().getPlayer(), enchant.getLevel());
				if (level > 0) enchantments.get(i).setLevel(level);
				else
					enchantments.remove(i);
			}
		}

		return enchantments;
	}

	public int getTakeCost() {
		return takeCost;
	}

	public ItemStack getItemTwo() {
		return itemTwo;
	}

	public ItemStack getTakenItem() {
		return takenItem;
	}

	public ItemStack getCombinedItem() {
		return combinedItem;
	}

}
