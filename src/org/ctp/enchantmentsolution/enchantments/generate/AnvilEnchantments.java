package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.persistence.PersistenceUtils;
import org.ctp.enchantmentsolution.utils.PermissionUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.config.ConfigUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class AnvilEnchantments extends GenerateEnchantments {

	private int repairCost;
	private ItemStack itemTwo, combinedItem, itemTwoLeftover;
	private List<EnchantmentLevel> enchantments;
	private boolean canCombine;
	private String name;

	public AnvilEnchantments(Player player, ItemStack item, ItemStack itemTwo) {
		super(player, item, EnchantmentLocation.NONE);
		this.itemTwo = itemTwo;

		setCanCombine();
		if (canCombine) setCombinedItem();
		else
			setRenamedItem();
	}

	public AnvilEnchantments(Player player, ItemStack item, ItemStack itemTwo, String name) {
		super(player, item, EnchantmentLocation.NONE);
		this.itemTwo = itemTwo;

		setCanCombine();
		if (name != null) this.name = name;
		if (canCombine) setCombinedItem();
		else
			setRenamedItem();
	}

	public static AnvilEnchantments getAnvilEnchantments(Player player, ItemStack item, ItemStack itemTwo) {
		return new AnvilEnchantments(player, item, itemTwo);
	}

	public static AnvilEnchantments getAnvilEnchantments(Player player, ItemStack item, ItemStack itemTwo, String name) {
		return new AnvilEnchantments(player, item, itemTwo, name);
	}

	public enum RepairType {
		RENAME, REPAIR, COMBINE;

		public static RepairType getRepairType(AnvilEnchantments enchant) {
			if (enchant == null) return null;
			if (enchant.getItemTwo() == null) return RepairType.RENAME;
			if (enchant.canCombine()) {
				for(Material data: ItemType.getRepairMaterials())
					if (data == enchant.getItemTwo().getType() && ItemData.contains(ItemType.getAnvilType(new ItemData(enchant.getItem())).getAnvilMaterials(), data)) return RepairType.REPAIR;
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
		List<EnchantmentLevel> enchantments = PersistenceUtils.getEnchantments(itemTwo);

		if (enchantments.size() > 0 || DamageUtils.getDamage(item) > 0) {
			boolean willRepair = false;
			for(ItemData d: items)
				if (d.equals(dataTwo)) willRepair = true;
			if (typeOne == typeTwo || willRepair) {
				if (dataOne.getMaterial() == dataTwo.getMaterial() && !dataOne.equals(dataTwo)) {
					canCombine = false;
					return;
				}
				if (!itemTwo.getType().equals(Material.BOOK) && !itemTwo.getType().equals(Material.ENCHANTED_BOOK) || DamageUtils.getDamage(item) > 0 || !itemTwo.getType().equals(item.getType())) {
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

	private boolean checkEnchantments(List<EnchantmentLevel> enchantments, ItemStack first) {
		for(EnchantmentLevel level : enchantments)
			for(CustomEnchantment customEnchant: RegisterEnchantments.getRegisteredEnchantments())
				if (customEnchant.getRelativeEnchantment().equals(level.getEnchant().getRelativeEnchantment()) && EnchantmentUtils.canAddEnchantment(customEnchant, first)) return true;
		return false;
	}

	private void setRenamedItem() {
		if (name != null && !name.isEmpty()) {
			ItemStack itemOne = getItem();
			if (itemOne == null) return;
			combinedItem = itemOne.clone();
			int itemOneRepair = AnvilNMS.getRepairCost(itemOne);
			combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
			repairCost += itemOneRepair;
			ItemMeta meta = combinedItem.getItemMeta();
			meta.setDisplayName(name);
			combinedItem.setItemMeta(meta);
			repairCost += 1;
			repairCost = Math.max(repairCost / ConfigString.LEVEL_DIVISOR.getInt(), 1);
		}
	}

	private void setCombinedItem() {
		ItemStack itemOne = getItem();
		combinedItem = itemOne.clone();
		combinedItem = EnchantmentUtils.removeAllEnchantments(combinedItem, true);
		if (itemOne.getType() == Material.BOOK || itemOne.getType() == Material.ENCHANTED_BOOK) if (ConfigString.USE_ENCHANTED_BOOKS.getBoolean()) combinedItem = new ItemStack(Material.ENCHANTED_BOOK);
		else
			combinedItem = new ItemStack(Material.BOOK);
		else {}
		DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(itemOne));
		RepairType repairType = RepairType.getRepairType(this);
		if (repairType == null) return;
		if (repairType == RepairType.COMBINE && DamageUtils.getDamage(itemOne) != 0) repairCost += 2;
		if (name != null) {
			ItemMeta meta = combinedItem.getItemMeta();
			meta.setDisplayName(name);
			combinedItem.setItemMeta(meta);
			repairCost += 1;
		}

		if (repairType == RepairType.REPAIR) {
			int amount = itemTwo.getAmount();
			int durPerItem = DamageUtils.getMaxDamage(combinedItem) / 4;
			while (DamageUtils.getDamage(combinedItem) > 0 && amount > 0) {
				amount--;
				repairCost++;
				DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(combinedItem) - durPerItem);
			}

			if (DamageUtils.getDamage(combinedItem) < 0) DamageUtils.setDamage(combinedItem, 0);
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
			int extraDurability = DamageUtils.getMaxDamage(itemTwo) - DamageUtils.getDamage(itemTwo) + (int) (DamageUtils.getMaxDamage(itemTwo) * .12);
			DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(combinedItem) - extraDurability);
			if (DamageUtils.getDamage(combinedItem) < 0) DamageUtils.setDamage(combinedItem, 0);
		}
		repairCost += setEnchantments();
		int itemOneRepair = AnvilNMS.getRepairCost(itemOne);
		int itemTwoRepair = AnvilNMS.getRepairCost(itemTwo);
		if (itemOneRepair > itemTwoRepair) combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
		else
			combinedItem = AnvilNMS.setRepairCost(combinedItem, itemTwoRepair * 2 + 1);
		if (enchantments != null) combinedItem = EnchantmentUtils.addEnchantmentsToItem(combinedItem, enchantments);
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
		boolean noUpgrade = false;
		if (itemTwo.getType() == Material.BOOK || itemTwo.getType() == Material.ENCHANTED_BOOK) {
			if (item.getType() == Material.BOOK || item.getType() == Material.ENCHANTED_BOOK) noUpgrade = ConfigString.NO_UPGRADE_BOOKS.getBoolean();
			else
				noUpgrade = ConfigString.NO_UPGRADE_BOOKS_TO_NON_BOOKS.getBoolean();
		} else
			noUpgrade = ConfigString.NO_UPGRADE_NON_BOOKS.getBoolean();
		Player player = getPlayer().getPlayer();

		List<EnchantmentLevel> firstEnchants = PersistenceUtils.getEnchantments(item);
		List<EnchantmentLevel> secondEnchants = PersistenceUtils.getEnchantments(itemTwo);
		List<EnchantmentLevel> secondLevels = new ArrayList<EnchantmentLevel>();
		List<EnchantmentLevel> firstLevels = new ArrayList<EnchantmentLevel>();
		boolean containsStagnancyOne = false;
		boolean containsStagnancyTwo = false;

		List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();
		List<CustomEnchantment> registeredEnchantments = RegisterEnchantments.getRegisteredEnchantments();
		for(EnchantmentLevel enchant : secondEnchants) {
			int level = enchant.getLevel();
			for(CustomEnchantment customEnchant: registeredEnchantments)
				if (ConfigUtils.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant.getEnchant().getRelativeEnchantment())) {
					if (RegisterEnchantments.CURSE_OF_STAGNANCY.equals(enchant.getEnchant().getRelativeEnchantment())) containsStagnancyTwo = true;
					secondLevels.add(new EnchantmentLevel(customEnchant, level));
				}
		}

		for(EnchantmentLevel enchant : firstEnchants) {
			int level = enchant.getLevel();
			for(CustomEnchantment customEnchant: registeredEnchantments)
				if (ConfigUtils.isRepairable(customEnchant) && customEnchant.getRelativeEnchantment().equals(enchant.getEnchant().getRelativeEnchantment())) {
					if (RegisterEnchantments.CURSE_OF_STAGNANCY.equals(enchant.getEnchant().getRelativeEnchantment())) containsStagnancyOne = true;
					firstLevels.add(new EnchantmentLevel(customEnchant, level));
				}
		}

		boolean godAnvil = PermissionUtils.check(player, "enchantmentsolution.anvil.god");
		boolean demiGodAnvil = PermissionUtils.check(player, "enchantmentsolution.anvil.demigod");
		if (godAnvil) demiGodAnvil = true;
		boolean demiGodBooks = PermissionUtils.check(player, "enchantmentsolution.anvil.demigod-books");

		for(EnchantmentLevel enchantTwo: secondLevels) {
			boolean conflict = false;
			boolean same = false;
			boolean canAdd = !containsStagnancyOne;
			int levelCost = enchantTwo.getLevel();
			int originalLevel = -1;
			for(EnchantmentLevel enchantOne: firstLevels)
				if (enchantTwo.getEnchant().getRelativeEnchantment().equals(enchantOne.getEnchant().getRelativeEnchantment())) {
					same = true;
					originalLevel = enchantOne.getLevel();
					if (containsStagnancyTwo) {
						levelCost = enchantTwo.getLevel();
						break;
					}
					if (!godAnvil) {
						if (enchantOne.getLevel() > enchantOne.getEnchant().getMaxLevel()) enchantOne.setLevel(enchantOne.getEnchant().getMaxLevel());
						if (enchantTwo.getLevel() > enchantTwo.getEnchant().getMaxLevel()) enchantTwo.setLevel(enchantTwo.getEnchant().getMaxLevel());
					}
					if (enchantTwo.getLevel() == enchantOne.getLevel()) {
						if (!noUpgrade && (godAnvil || demiGodBooks)) {
							if (demiGodBooks && (itemTwo.getType() == Material.BOOK || itemTwo.getType() == Material.ENCHANTED_BOOK)) levelCost = enchantTwo.getLevel() + 1;
							else if (godAnvil) levelCost = enchantTwo.getLevel() + 1;
						}
						else if (enchantTwo.getLevel() >= enchantTwo.getEnchant().getMaxLevel()) levelCost = enchantTwo.getLevel();
						else if (!noUpgrade) levelCost = enchantTwo.getLevel() + 1;
						else
							levelCost = enchantTwo.getLevel();
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
			if ((!containsStagnancyTwo || godAnvil) && !added && (enchantOne.getEnchant().canAnvilItem(new ItemData(item)) || godAnvil)) {
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
		if (!containsStagnancyOne && !containsStagnancyTwo && maxEnchants > 0) for(int i = enchantments.size() - 1; i > maxEnchants; i--)
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

	public boolean dropLeftovers() {
		return itemTwoLeftover != null && !MatData.isAir(itemTwoLeftover.getType()) && itemTwoLeftover.getAmount() > 0;
	}

}
