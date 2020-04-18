package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.ItemData;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class AnvilEnchantments extends GenerateEnchantments {

	private int repairCost;
	private ItemStack itemTwo, combinedItem, itemTwoLeftover;
	private List<EnchantmentLevel> enchantments;
	private boolean canCombine;

	public AnvilEnchantments(Player player, ItemStack item, ItemStack itemTwo) {
		super(player, item, EnchantmentLocation.NONE);
		this.itemTwo = itemTwo;

		setCanCombine();
		if (canCombine) {
			setEnchantments();
			setCombinedItem();
		}
	}

	public static AnvilEnchantments getAnvilEnchantments(Player player, ItemStack item, ItemStack itemTwo) {
		return new AnvilEnchantments(player, item, itemTwo);
	}

	public enum RepairType {
		RENAME, REPAIR, COMBINE;

		public static RepairType getRepairType(AnvilEnchantments enchant) {
			if (enchant.getItemTwo() == null) return RepairType.RENAME;
			if (enchant.canCombine()) {
				for(Material data: ItemType.getRepairMaterials())
					if (data == enchant.getItemTwo().getType()) return RepairType.REPAIR;
				return RepairType.COMBINE;
			}
			return null;
		}
	}

	public boolean canCombine() {
		return canCombine;
	}

	private void setCanCombine() {
		ItemStack item = getItem();
		ItemData dataOne = new ItemData(item);
		ItemType typeOne = ItemType.getAnvilType(dataOne);
		if (item == null || itemTwo == null || typeOne == null) {
			canCombine = false;
			return;
		}
		ItemData dataTwo = new ItemData(itemTwo);
		ItemType typeTwo = ItemType.getAnvilType(dataTwo);
		List<ItemData> items = typeOne.getAnvilMaterials();
		if (items == null) items = new ArrayList<ItemData>();
		if (dataOne.getMMOType() != null && dataOne.getMMOTypeSet() != null) items.add(new ItemData(dataOne.getItem()));
		if (items.size() == 0) {
			canCombine = false;
			return;
		}
		Map<Enchantment, Integer> enchantments = itemTwo.getItemMeta().getEnchants();
		if (itemTwo.getType() == Material.ENCHANTED_BOOK) enchantments = ((EnchantmentStorageMeta) itemTwo.getItemMeta()).getStoredEnchants();

		if (enchantments.size() > 0 || DamageUtils.getDamage(item.getItemMeta()) > 0) {
			boolean willRepair = false;
			for(ItemData d: items)
				if (d.equals(dataTwo)) willRepair = true;
			if (typeOne == typeTwo || willRepair) {
				if (dataOne.getMaterial() == dataTwo.getMaterial() && !dataOne.equals(dataTwo)) {
					canCombine = false;
					return;
				}
				if (!itemTwo.getType().equals(Material.BOOK) && !itemTwo.getType().equals(Material.ENCHANTED_BOOK) || DamageUtils.getDamage(item.getItemMeta()) > 0 || !itemTwo.getType().equals(item.getType())) {
					canCombine = true;
					return;
				}
				canCombine = checkEnchantments(enchantments, item);
				return;
			}
		} else if (itemTwo.getType().equals(Material.ENCHANTED_BOOK) && enchantments.size() > 0) {
			canCombine = checkEnchantments(enchantments, item);
			return;
		}
		canCombine = false;
	}

	private boolean checkEnchantments(Map<Enchantment, Integer> enchantments, ItemStack first) {
		for(Iterator<java.util.Map.Entry<Enchantment, Integer>> it = enchantments.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			for(CustomEnchantment customEnchant: RegisterEnchantments.getEnchantments())
				if (customEnchant.getRelativeEnchantment().equals(enchant)) if (ItemUtils.canAddEnchantment(customEnchant, first)) return true;
		}
		return false;
	}

	private void setCombinedItem() {
		ItemStack itemOne = getItem();
		combinedItem = itemOne.clone();
		combinedItem = ItemUtils.removeAllEnchantments(combinedItem, true);
		if (itemOne.getType() == Material.BOOK || itemOne.getType() == Material.ENCHANTED_BOOK) if (ConfigString.USE_ENCHANTED_BOOKS.getBoolean()) combinedItem = new ItemStack(Material.ENCHANTED_BOOK);
		else
			combinedItem = new ItemStack(Material.BOOK);
		DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(itemOne.getItemMeta()));
		RepairType repairType = RepairType.getRepairType(this);
		if (repairType == null) return;
		if (repairType == RepairType.COMBINE && DamageUtils.getDamage(itemOne.getItemMeta()) != 0) repairCost += 2;

		if (repairType == RepairType.REPAIR) {
			int amount = itemTwo.getAmount();
			int durPerItem = combinedItem.getType().getMaxDurability() / 4;
			while (DamageUtils.getDamage(combinedItem.getItemMeta()) > 0 && amount > 0) {
				amount--;
				repairCost++;
				DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(combinedItem.getItemMeta()) - durPerItem);
			}

			if (DamageUtils.getDamage(combinedItem.getItemMeta()) < 0) DamageUtils.setDamage(combinedItem, 0);
			if (amount > 0) {
				itemTwoLeftover = itemTwo.clone();
				itemTwoLeftover.setAmount(amount);
			} else
				itemTwoLeftover = new ItemStack(Material.AIR);
		} else if (repairType == RepairType.COMBINE && itemTwo.getType() == Material.BOOK) {
			int amount = itemTwo.getAmount() - 1;
			if (amount > 0) {
				itemTwoLeftover = itemTwo.clone();
				itemTwoLeftover.setAmount(amount);
			} else
				itemTwoLeftover = new ItemStack(Material.AIR);
		} else if (itemTwo.getType() == itemOne.getType()) {
			int extraDurability = itemTwo.getType().getMaxDurability() - DamageUtils.getDamage(itemTwo.getItemMeta()) + (int) (itemTwo.getType().getMaxDurability() * .12);
			DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(combinedItem.getItemMeta()) - extraDurability);
			if (DamageUtils.getDamage(combinedItem.getItemMeta()) < 0) DamageUtils.setDamage(combinedItem, 0);
		}
		setEnchantments();
		int itemOneRepair = AnvilNMS.getRepairCost(itemOne);
		int itemTwoRepair = AnvilNMS.getRepairCost(itemTwo);
		if (itemOneRepair > itemTwoRepair) combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
		else
			combinedItem = AnvilNMS.setRepairCost(combinedItem, itemTwoRepair * 2 + 1);
		if (enchantments != null) combinedItem = ItemUtils.addEnchantmentsToItem(combinedItem, enchantments);
		repairCost += itemOneRepair + itemTwoRepair;

		repairCost = Math.max(repairCost / ConfigString.LEVEL_DIVISOR.getInt(), 1);
	}

	public ItemStack getCombinedItem() {
		return combinedItem;
	}

	public ItemStack getItemLeftover() {
		return itemTwoLeftover;
	}

	public int getRepairCost() {
		return repairCost;
	}

	public List<EnchantmentLevel> getEnchantments() {
		return enchantments;
	}

	private int setEnchantments() {
		int cost = 0;
		ItemStack item = getItem();
		Player player = getPlayer().getPlayer();

		ItemMeta firstMeta = item.clone().getItemMeta();
		Map<Enchantment, Integer> firstEnchants = firstMeta.getEnchants();
		if (item.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) firstMeta;
			firstEnchants = meta.getStoredEnchants();
		}
		ItemMeta secondMeta = itemTwo.clone().getItemMeta();
		Map<Enchantment, Integer> secondEnchants = secondMeta.getEnchants();
		if (itemTwo.getType().equals(Material.ENCHANTED_BOOK)) {
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) secondMeta;
			secondEnchants = meta.getStoredEnchants();
		}
		List<EnchantmentLevel> secondLevels = new ArrayList<EnchantmentLevel>();
		List<EnchantmentLevel> firstLevels = new ArrayList<EnchantmentLevel>();

		List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();
		List<CustomEnchantment> registeredEnchantments = RegisterEnchantments.getEnchantments();
		for(Iterator<java.util.Map.Entry<Enchantment, Integer>> it = secondEnchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant: registeredEnchantments)
				if (ConfigUtils.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant)) secondLevels.add(new EnchantmentLevel(customEnchant, level));
		}

		for(Iterator<java.util.Map.Entry<Enchantment, Integer>> it = firstEnchants.entrySet().iterator(); it.hasNext();) {
			java.util.Map.Entry<Enchantment, Integer> e = it.next();
			Enchantment enchant = e.getKey();
			int level = e.getValue();
			for(CustomEnchantment customEnchant: registeredEnchantments)
				if (ConfigUtils.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant)) firstLevels.add(new EnchantmentLevel(customEnchant, level));
		}

		boolean godAnvil = player.hasPermission("enchantmentsolution.god-anvil");
		boolean demiGodAnvil = player.hasPermission("enchantmentsolution.demigod-anvil");
		if (godAnvil) demiGodAnvil = true;
		boolean demiGodBooks = demiGodAnvil && player.hasPermission("enchantmentsolution.demigod-books");

		for(EnchantmentLevel enchantTwo: secondLevels) {
			boolean conflict = false;
			boolean same = false;
			boolean canAdd = true;
			int levelCost = enchantTwo.getLevel();
			int originalLevel = -1;
			for(EnchantmentLevel enchantOne: firstLevels)
				if (enchantTwo.getEnchant().getRelativeEnchantment().equals(enchantOne.getEnchant().getRelativeEnchantment())) {
					same = true;
					originalLevel = enchantOne.getLevel();
					if (!godAnvil) {
						if (enchantOne.getLevel() > enchantOne.getEnchant().getMaxLevel()) enchantOne.setLevel(enchantOne.getEnchant().getMaxLevel());
						if (enchantTwo.getLevel() > enchantTwo.getEnchant().getMaxLevel()) enchantTwo.setLevel(enchantTwo.getEnchant().getMaxLevel());
					}
					if (enchantTwo.getLevel() == enchantOne.getLevel()) {
						if (enchantTwo.getLevel() >= enchantTwo.getEnchant().getMaxLevel()) levelCost = enchantTwo.getLevel();
						else
							levelCost = enchantTwo.getLevel() + 1;
					} else if (enchantTwo.getLevel() > enchantOne.getLevel()) levelCost = enchantTwo.getLevel();
					else
						levelCost = enchantOne.getLevel();
				} else if (CustomEnchantment.conflictsWith(enchantOne.getEnchant(), enchantTwo.getEnchant())) conflict = true;
			if (demiGodAnvil) {
				if (demiGodBooks && (itemTwo.getType() == Material.BOOK || itemTwo.getType() == Material.ENCHANTED_BOOK)) {
					// nothing needs to change
				} else if (!enchantTwo.getEnchant().canAnvil(player, levelCost) && originalLevel >= levelCost) {
					levelCost = originalLevel;
					if (!godAnvil && levelCost > enchantTwo.getEnchant().getMaxLevel()) levelCost = enchantTwo.getEnchant().getMaxLevel();
				} else if (!enchantTwo.getEnchant().canAnvil(player, levelCost)) {
					int level = enchantTwo.getEnchant().getAnvilLevel(player, levelCost);
					if (level <= 0) canAdd = false;
					levelCost = level;
				}
			} else if (!enchantTwo.getEnchant().canAnvil(player, levelCost)) {
				int level = enchantTwo.getEnchant().getAnvilLevel(player, levelCost);
				if (level <= 0) canAdd = false;
				levelCost = level;
			}

			if (canAdd && (same || !conflict)) {
				if (enchantTwo.getEnchant().canAnvilItem(new ItemData(item)) || godAnvil) {
					enchantments.add(new EnchantmentLevel(enchantTwo.getEnchant(), levelCost));
					cost += levelCost * enchantTwo.getEnchant().multiplier(itemTwo.getType());
				} else
					cost += 1;
			} else if (godAnvil) {
				enchantments.add(new EnchantmentLevel(enchantTwo.getEnchant(), levelCost));
				cost += levelCost * enchantTwo.getEnchant().multiplier(itemTwo.getType());
			} else
				cost += 1;
		}

		for(EnchantmentLevel enchantOne: firstLevels) {
			boolean added = false;
			for(EnchantmentLevel enchantment: enchantments)
				if (enchantOne.getEnchant().getRelativeEnchantment().equals(enchantment.getEnchant().getRelativeEnchantment())) {
					added = true;
					break;
				}
			if (!added && (enchantOne.getEnchant().canAnvilItem(new ItemData(item)) || godAnvil)) {
				if (demiGodAnvil) {
					if (!godAnvil) if (enchantOne.getLevel() > enchantOne.getEnchant().getMaxLevel()) enchantOne.setLevel(enchantOne.getEnchant().getMaxLevel());
				} else if (!enchantOne.getEnchant().canAnvil(player, enchantOne.getLevel())) {
					int level = enchantOne.getEnchant().getAnvilLevel(player, enchantOne.getLevel());
					if (level <= 0) continue;
				}
				enchantments.add(enchantOne);
			}
		}
		int maxEnchants = ConfigString.MAX_ENCHANTMENTS.getInt();
		if (maxEnchants > 0) for(int i = enchantments.size() - 1; i > maxEnchants; i--)
			enchantments.remove(i);

		this.enchantments = enchantments;

		return cost;
	}

	public ItemStack getItemTwo() {
		return itemTwo;
	}

	public void setItemTwo(ItemStack itemTwo) {
		this.itemTwo = itemTwo;
	}

	public RepairType getRepairType() {
		return RepairType.getRepairType(this);
	}

}
