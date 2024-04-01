package org.ctp.enchantmentsolution.enchantments.generate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.crashapi.data.items.ItemData;
import org.ctp.crashapi.data.items.ItemType;
import org.ctp.crashapi.data.items.MatData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.persistence.PersistenceUtils;
import org.ctp.enchantmentsolution.utils.PermissionUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class AnvilEnchantments extends GenerateEnchantments {

	private int repairCost;
	private ItemStack itemTwo, combinedItem, itemTwoLeftover;
	private List<EnchantmentLevel> enchantments;
	private boolean canCombine;
	private String name;
	private final boolean allowInvalidEnchantments; // allows for enchantments that are not allowed on certain items to be added
	// despite this
	private final boolean allowHigherLevels; // allows for higher than normal level enchantments when using anvil
	private final boolean combineHigherLevels; // combining levels above normal will set level to one higher - false if
												// allowHigherLevels is false
	private final boolean allowConflictingEnchantments; // i.e. Protection and Projectile Protection on same armor
	private final boolean ignoreAnvilPermissions; // ignore all permissions found in enchantments.yml/enchantments files
	private final boolean combineLevelsAll; // if true, will set below permissions to true - Combining Protection I and
	// Protection I will create Protection II
	private final boolean combineLevelsBooks; // Combining Protection I and Protection I will produce Protection II on books
	private final boolean combineLevelsItems; // Combining Protection I and Protection I will produce Protection II on items
	// that aren't books

	public AnvilEnchantments(Player player, ItemStack item, ItemStack itemTwo) {
		super(player, item, EnchantmentLocation.NONE);
		this.itemTwo = itemTwo;
		allowInvalidEnchantments = PermissionUtils.check(player, "enchantmentsolution.anvil.allow-invalid-enchantments");
		allowHigherLevels = PermissionUtils.check(player, "enchantmentsolution.anvil.allow-higher-levels");
		combineHigherLevels = PermissionUtils.check(player, "enchantmentsolution.anvil.combine-higher-levels");
		allowConflictingEnchantments = PermissionUtils.check(player, "enchantmentsolution.anvil.allow-conflicting-enchantments");
		ignoreAnvilPermissions = PermissionUtils.check(player, "enchantmentsolution.anvil.ignore-anvil-permissions");
		combineLevelsAll = PermissionUtils.check(player, "enchantmentsolution.anvil.combine-levels-all");
		combineLevelsBooks = PermissionUtils.check(player, "enchantmentsolution.anvil.combine-levels-books") || combineLevelsAll;
		combineLevelsItems = PermissionUtils.check(player, "enchantmentsolution.anvil.combine-levels-items") || combineLevelsAll;
		setCanCombine();
		if (canCombine) setCombinedItem();
		else
			setRenamedItem();
	}

	public AnvilEnchantments(Player player, ItemStack item, ItemStack itemTwo, String name) {
		super(player, item, EnchantmentLocation.NONE);
		this.itemTwo = itemTwo;
		allowInvalidEnchantments = PermissionUtils.check(player, "enchantmentsolution.anvil.allow-invalid-enchantments");
		allowHigherLevels = PermissionUtils.check(player, "enchantmentsolution.anvil.allow-higher-levels");
		combineHigherLevels = PermissionUtils.check(player, "enchantmentsolution.anvil.combine-higher-levels");
		allowConflictingEnchantments = PermissionUtils.check(player, "enchantmentsolution.anvil.allow-conflicting-enchantments");
		ignoreAnvilPermissions = PermissionUtils.check(player, "enchantmentsolution.anvil.ignore-anvil-permissions");
		combineLevelsAll = PermissionUtils.check(player, "enchantmentsolution.anvil.combine-levels-all");
		combineLevelsBooks = PermissionUtils.check(player, "enchantmentsolution.anvil.combine-levels-books") || combineLevelsAll;
		combineLevelsItems = PermissionUtils.check(player, "enchantmentsolution.anvil.combine-levels-items") || combineLevelsAll;

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
		RENAME, REPAIR, COMBINE, STICKY_REPAIR;

		public static RepairType getRepairType(AnvilEnchantments enchant) {
			if (enchant == null) return null;
			if (enchant.getItemTwo() == null) return RepairType.RENAME;
			if (enchant.canCombine() || enchant.getItem() != null && enchant.getItem().getType() == Material.STICK && EnchantmentUtils.hasEnchantment(enchant.getItem(), RegisterEnchantments.STICKY_HOLD)) {
				for(Material data: ItemType.getRepairMaterials())
					if (data == enchant.getItemTwo().getType() && ItemData.contains(ItemType.getAnvilType(new ItemData(enchant.getItem())).getAnvilMaterials(), data)) return RepairType.REPAIR;
				if (enchant.getItem().getType() == Material.STICK && EnchantmentUtils.hasEnchantment(enchant.getItem(), RegisterEnchantments.STICKY_HOLD) && PersistenceUtils.isStickyHold(enchant.getItem())) for(Material data: ItemType.getRepairMaterials())
					if (data == enchant.getItemTwo().getType() && ItemData.contains(ItemType.getAnvilType(new ItemData(new ItemStack(PersistenceUtils.stickyItemType(enchant.getItem())))).getAnvilMaterials(true), data)) return RepairType.STICKY_REPAIR;
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
		if (RepairType.getRepairType(this) == RepairType.STICKY_REPAIR) {
			canCombine = true;
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
				if (!MatData.isBook(itemTwo.getType()) || DamageUtils.getDamage(item) > 0 && willRepair) {
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
		if (allowConflictingEnchantments || ignoreAnvilPermissions) return true;
		for(EnchantmentLevel level: enchantments)
			if (EnchantmentUtils.canAddEnchantment(level.getEnchant(), first)) return true;
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

		if (MatData.isBook(itemOne.getType())) if (ConfigString.USE_ENCHANTED_BOOKS.getBoolean()) combinedItem = new ItemStack(Material.ENCHANTED_BOOK);
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
		if (repairType == RepairType.STICKY_REPAIR) {
			int amount = itemTwo.getAmount();
			if (amount <= 4) return;
			amount -= 4;
			if (amount > 0) {
				itemTwoLeftover = itemTwo.clone();
				itemTwoLeftover.setAmount(amount);
			} else
				itemTwoLeftover = new ItemStack(Material.AIR);
			repairCost = 20;
			combinedItem = DamageUtils.setDamage(PersistenceUtils.repairStickyHold(combinedItem), 0);
			List<EnchantmentLevel> levels = EnchantmentUtils.getEnchantmentLevels(itemOne);

			Player player = getPlayer().getPlayer();
			List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();
			for(EnchantmentLevel enchantOne: levels) {
				boolean added = false;
				int newLevel = 0;
				int maxLevel = enchantOne.getEnchant().getMaxLevel();
				for(EnchantmentLevel enchantment: enchantments)
					if (enchantOne.getEnchant().getRelativeEnchantment().equals(enchantment.getEnchant().getRelativeEnchantment())) {
						added = true;
						break;
					}
				boolean conflict = PersistenceUtils.hasConflictingEnchantment(enchantments, enchantOne.getEnchant());
				// Okay, it wasn't added and it wasn't failed, so let's add it to the final item
				// as long as it can be!
				if (!added) {
					newLevel = enchantOne.getLevel();
					// Should check if can be added
					if (newLevel > 0 && !(enchantOne.getEnchant().canAnvilItem(new ItemData(combinedItem)) || allowInvalidEnchantments)) newLevel = 0;
					// Are there conflicting enchantments on either item?
					if (newLevel > 0 && (conflict && !allowConflictingEnchantments)) newLevel = 0;
					// Should the level be lowered?
					if (newLevel > 0 && (newLevel > maxLevel && !allowHigherLevels)) newLevel = maxLevel;
					// Do they have permission to use this level?
					if (newLevel > 0 && !(enchantOne.getEnchant().canAnvil(player, newLevel) || ignoreAnvilPermissions)) newLevel = enchantOne.getEnchant().getAnvilLevel(player, newLevel);

					if (newLevel > 0) // Add enchantment to the enchantments array list
						enchantments.add(new EnchantmentLevel(enchantOne.getEnchant(), newLevel));
				}
			}
			int maxEnchants = ConfigString.MAX_ENCHANTMENTS.getInt();
			if (maxEnchants > 0) for(int i = enchantments.size() - 1; i > maxEnchants; i--)
				enchantments.remove(i);

			this.enchantments = enchantments;
			combinedItem = AnvilNMS.setRepairCost(combinedItem, 0);
			if (this.enchantments != null) combinedItem = EnchantmentUtils.addEnchantmentsToItem(combinedItem, this.enchantments);

			return;
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
		if (!ConfigString.REPAIR_FROM_FINAL.getBoolean()) {
			int itemOneRepair = AnvilNMS.getRepairCost(itemOne);
			int itemTwoRepair = AnvilNMS.getRepairCost(itemTwo);
			if (itemOneRepair > itemTwoRepair) combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
			else
				combinedItem = AnvilNMS.setRepairCost(combinedItem, itemTwoRepair * 2 + 1);
			repairCost += itemOneRepair + itemTwoRepair;
		}
		if (enchantments != null) combinedItem = EnchantmentUtils.addEnchantmentsToItem(combinedItem, enchantments);

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

		List<EnchantmentLevel> firstEnchants = PersistenceUtils.getEnchantments(item);
		List<EnchantmentLevel> secondEnchants = PersistenceUtils.getEnchantments(itemTwo);
		boolean containsStagnancyOne = PersistenceUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_STAGNANCY);
		boolean containsStagnancyTwo = PersistenceUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_STAGNANCY);

		List<EnchantmentLevel> enchantments = new ArrayList<EnchantmentLevel>();
		List<EnchantmentLevel> failedEnchantments = new ArrayList<EnchantmentLevel>();

		if (containsStagnancyOne) {
			cost += secondEnchants.size();
			enchantments = firstEnchants;
		} else if (containsStagnancyTwo) {
			cost += firstEnchants.size();
			enchantments = secondEnchants;
		} else {
			for(EnchantmentLevel enchantOne: firstEnchants) {
				int newLevel = 0;
				int maxLevel = enchantOne.getEnchant().getMaxLevel();
				// If not added, did it fail to be added?
				boolean conflict = PersistenceUtils.hasConflictingEnchantment(enchantments, enchantOne.getEnchant());
				// Okay, it wasn't added and it wasn't failed, so let's add it to the final item
				// as long as it can be!
				newLevel = enchantOne.getLevel();
				// Should check if can be added
				if (newLevel > 0 && !(enchantOne.getEnchant().canAnvilItem(new ItemData(item)) || allowInvalidEnchantments)) newLevel = 0;
				// Are there conflicting enchantments on either item?
				if (newLevel > 0 && (conflict && !allowConflictingEnchantments)) newLevel = 0;
				// Should the level be lowered?
				if (newLevel > 0 && (newLevel > maxLevel && !allowHigherLevels)) newLevel = maxLevel;
				// Do they have permission to use this level?
				if (newLevel > 0 && !(enchantOne.getEnchant().canAnvil(player, newLevel) || ignoreAnvilPermissions)) newLevel = enchantOne.getEnchant().getAnvilLevel(player, newLevel);

				if (newLevel > 0) // Add enchantment to the enchantments array list
					enchantments.add(new EnchantmentLevel(enchantOne.getEnchant(), newLevel));
			}
			for(EnchantmentLevel enchantTwo: secondEnchants) {
				int maxLevel = enchantTwo.getEnchant().getMaxLevel();
				EnchantmentLevel onOtherItem = null;
				for (EnchantmentLevel e : enchantments)
					if (enchantTwo.getEnchant().getRelativeEnchantment().equals(e.getEnchant().getRelativeEnchantment())) onOtherItem = e;
				boolean conflict = PersistenceUtils.hasConflictingEnchantment(enchantments, enchantTwo.getEnchant());
				int level = enchantTwo.getLevel();
				int originalLevel = onOtherItem == null ? 0 : onOtherItem.getLevel();
				int newLevel = 0;
				// Let's get what the level should be
				if (originalLevel == 0 || originalLevel < level) newLevel = level;
				else if (originalLevel > level) newLevel = originalLevel;
				else {
					newLevel = originalLevel;
					// We can combine the levels, so let's try!
					if (((newLevel < maxLevel) || (combineHigherLevels && newLevel >= maxLevel)) && ((bothBooks(item, itemTwo) && combineLevelsBooks) || (bothSame(item, itemTwo) && combineLevelsItems) || (!bothSame(item, itemTwo) && !bothBooks(item, itemTwo) && combineLevelsAll))) newLevel += 1;
					// If we combined the books, was it a mistake?
				}
				// Should check if can be added
				if (newLevel > 0 && !(enchantTwo.getEnchant().canAnvilItem(new ItemData(item)) || allowInvalidEnchantments)) newLevel = 0;
				// Are there conflicting enchantments on either item?
				if (newLevel > 0 && (conflict && !allowConflictingEnchantments)) newLevel = 0;
				// Should the level be lowered?
				if (newLevel > 0 && (newLevel > maxLevel && !allowHigherLevels)) newLevel = maxLevel;
				// Do they have permission to use this level?
				if (newLevel > 0 && !(enchantTwo.getEnchant().canAnvil(player, newLevel) || ignoreAnvilPermissions)) newLevel = enchantTwo.getEnchant().getAnvilLevel(player, newLevel);

				if (newLevel > 0) {
					// Add enchantment to the enchantments array list
					if (onOtherItem != null) {
						Iterator<EnchantmentLevel> levels = enchantments.iterator();
						while (levels.hasNext()) {
							EnchantmentLevel next = levels.next();
							if (next.getEnchant().getRelativeEnchantment().equals(enchantTwo.getEnchant().getRelativeEnchantment())) levels.remove();
						}
					}
					enchantments.add(new EnchantmentLevel(enchantTwo.getEnchant(), newLevel));
					cost += newLevel * enchantTwo.getEnchant().multiplier(itemTwo.getType());
				} else {
					// Adding enchantment failed, add to the failed enchantments list
					if (onOtherItem != null) {
						Iterator<EnchantmentLevel> levels = enchantments.iterator();
						while (levels.hasNext()) {
							EnchantmentLevel next = levels.next();
							if (next.getEnchant().getRelativeEnchantment().equals(enchantTwo.getEnchant().getRelativeEnchantment())) levels.remove();
						}
					} else
						failedEnchantments.add(new EnchantmentLevel(enchantTwo.getEnchant(), newLevel));
					cost += 1;
				}
			}
		}
		int maxEnchants = ConfigString.MAX_ENCHANTMENTS.getInt();
		if (!containsStagnancyOne && !containsStagnancyTwo && maxEnchants > 0) for(int i = enchantments.size() - 1; i > maxEnchants; i--)
			enchantments.remove(i);

		this.enchantments = enchantments;
		if (enchantments.size() == 0) canCombine = false;

		if (ConfigString.REPAIR_FROM_FINAL.getBoolean()) {
			cost = 0;
			for(EnchantmentLevel enchant: enchantments)
				cost += enchant.getEnchant().getWeightedCost(enchant.getLevel());
		}

		return cost;
	}

	private boolean bothBooks(ItemStack itemOne, ItemStack itemTwo) {
		return MatData.isBook(itemOne.getType()) && MatData.isBook(itemTwo.getType());
	}

	private boolean bothSame(ItemStack itemOne, ItemStack itemTwo) {
		return !bothBooks(itemOne, itemTwo) && itemOne.getType() == itemTwo.getType();
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
