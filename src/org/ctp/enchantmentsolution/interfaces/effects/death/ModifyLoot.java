package org.ctp.enchantmentsolution.interfaces.effects.death;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.enchantments.generate.MobLootEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.enums.EnchantmentLocation;
import org.ctp.enchantmentsolution.enums.Loots;
import org.ctp.enchantmentsolution.utils.GenerateUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class ModifyLoot {

	private MatData[] whitelist, blacklist;
	private MatOption[] options;

	public ModifyLoot(MatData[] whitelist, MatData[] blacklist, MatOption[] options) {
		setWhitelist(whitelist);
		setBlacklist(blacklist);
		setOptions(options);
	}

	public MatData[] getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(MatData[] whitelist) {
		this.whitelist = whitelist;
	}

	public MatData[] getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(MatData[] blacklist) {
		this.blacklist = blacklist;
	}

	public MatOption[] getOptions() {
		return options;
	}

	public void setOptions(MatOption[] options) {
		this.options = options;
	}

	public LootResult getLoot(boolean enchant, String lootKind) {
		LootResult result = getRandomLoot();
		if (!result.isChanged()) return result;
		ItemStack loot = result.getModifiedItem();
		if (loot.getType().getMaxStackSize() == 1) {
			int random = (int) (Math.random() * DamageUtils.getMaxDamage(loot));
			loot = DamageUtils.setDamage(loot, random);
		}
		boolean enchanted = false;
		if (enchant && EnchantmentUtils.isEnchantable(loot)) {
			Loots defaultLoot = Loots.getLoot("default_mob_loot");
			MobLootEnchantments enchantments = MobLootEnchantments.generateMobLoot(loot, lootKind, defaultLoot);
			List<EnchantmentLevel> levels = enchantments.getEnchantments();
			if (levels == null || levels.size() == 0) {
				GenerateUtils.warningMessages(loot, "MobModifyLoot", EnchantmentLocation.MOB_LOOT);
				return new LootResult(true, result.getOriginalItem(), loot, false);
			}
			loot = EnchantmentUtils.addEnchantmentsToItem(loot, levels);
			enchanted = true;
		}
		return new LootResult(true, result.getOriginalItem(), loot, enchanted);
	}

	public LootResult getRandomLoot(ItemStack original, boolean enchant, String lootKind) {
		LootResult result = getRandomLoot(original);
		if (!result.isChanged()) return result;
		ItemStack loot = result.getModifiedItem();
		if (loot.getType().getMaxStackSize() == 1) {
			int random = (int) (Math.random() * DamageUtils.getMaxDamage(loot));
			loot = DamageUtils.setDamage(loot, random);
		}
		boolean enchanted = false;
		if (enchant && EnchantmentUtils.isEnchantable(loot)) {
			Loots defaultLoot = Loots.getLoot("default_mob_loot");
			MobLootEnchantments enchantments = MobLootEnchantments.generateMobLoot(loot, lootKind, defaultLoot);
			List<EnchantmentLevel> levels = enchantments.getEnchantments();
			if (levels == null || levels.size() == 0) {
				GenerateUtils.warningMessages(loot, "MobModifyLoot", EnchantmentLocation.MOB_LOOT);
				return new LootResult(true, result.getOriginalItem(), loot, false);
			}
			loot = EnchantmentUtils.addEnchantmentsToItem(loot, levels);
			enchanted = true;
		}
		return new LootResult(true, result.getOriginalItem(), loot, enchanted);
	}

	private LootResult getRandomLoot() {
		LootResult def = new LootResult(false, new ItemStack(Material.AIR), new ItemStack(Material.AIR), false);

		int chance = 0;
		for(MatOption loot: options)
			if (loot.getMaterial() != null) chance += loot.getChance();

		int random = (int) (Math.random() * chance);

		for(MatOption loot: options) {
			random -= loot.getChance();
			if (random <= 0) return new LootResult(true, new ItemStack(Material.AIR), new ItemStack(loot.getMaterial(), (int) (Math.random() * (loot.getMax() - loot.getMin() + 1) + loot.getMin())), false);
		}
		return def;
	}

	private LootResult getRandomLoot(ItemStack original) {
		LootResult def = new LootResult(false, original, original, false);
		for(MatData data: blacklist)
			if (data.getMaterial() == original.getType()) return def;
		if (whitelist.length > 0) {
			boolean contained = false;
			for(MatData data: whitelist)
				if (data.getMaterial() == original.getType()) contained = true;
			if (!contained) return def;
		}
		int chance = 0;
		for(MatOption loot: options)
			if (loot.getMaterial() != null) chance += loot.getChance();

		int random = (int) (Math.random() * chance);

		for(MatOption loot: options) {
			random -= loot.getChance();
			if (random <= 0) return new LootResult(true, original, new ItemStack(loot.getMaterial(), (int) (Math.random() * (loot.getMax() - loot.getMin() + 1) + loot.getMin())), false);
		}
		return def;
	}

	public class LootResult {
		private final boolean changed, enchanted;
		private final ItemStack originalItem, modifiedItem;

		public LootResult(boolean changed, ItemStack originalItem, ItemStack modifiedItem, boolean enchanted) {
			this.changed = changed;
			this.originalItem = originalItem;
			this.modifiedItem = modifiedItem;
			this.enchanted = enchanted;
		}

		public boolean isChanged() {
			return changed;
		}

		public boolean isEnchanted() {
			return enchanted;
		}

		public ItemStack getOriginalItem() {
			return originalItem;
		}

		public ItemStack getModifiedItem() {
			return modifiedItem;
		}

	}
}