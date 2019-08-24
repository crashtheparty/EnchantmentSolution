package org.ctp.enchantmentsolution.enchantments.helper;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.AnvilNMS;
import org.ctp.enchantmentsolution.utils.ConfigUtils;
import org.ctp.enchantmentsolution.utils.AnvilUtils.RepairType;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class UpdateItem {

	private ItemStack itemOne, itemTwo, combinedItem, itemTwoLeftover;
	private CombineEnchantments enchantments;
	private Player player;
	private int repairCost;
	
	public UpdateItem(Player player, ItemStack itemOne) {
		this.setPlayer(player);
		this.setItemOne(itemOne);
		update();
	}
	
	public UpdateItem(Player player, ItemStack itemOne, ItemStack itemTwo) {
		this.setPlayer(player);
		this.setItemOne(itemOne);
		this.setItemTwo(itemTwo);
		update();
	}
	
	public void update() {
		repairCost = 0;
		combinedItem = itemOne.clone();
		combinedItem = Enchantments.removeAllEnchantments(combinedItem);
		if(itemOne.getType() == Material.BOOK || itemOne.getType() == Material.ENCHANTED_BOOK) {
			if(ConfigUtils.getEnchantedBook()) {
				combinedItem = new ItemStack(Material.ENCHANTED_BOOK);
			} else {
				combinedItem = new ItemStack(Material.BOOK);
			}
		}
		DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(itemOne.getItemMeta()));
		RepairType repairType = RepairType.getRepairType(itemOne, itemTwo);
		if(repairType == null) {
			return;
		}
		if(repairType == RepairType.COMBINE && DamageUtils.getDamage(itemOne.getItemMeta()) != 0) {
			repairCost += 2;
		}

		if(repairType == RepairType.REPAIR) {
			int amount = itemTwo.getAmount();
			int durPerItem = combinedItem.getType().getMaxDurability() / 4;
			while(DamageUtils.getDamage(combinedItem.getItemMeta()) > 0 && amount > 0) {
				amount--;
				repairCost++;
				DamageUtils.setDamage(combinedItem, (DamageUtils.getDamage(combinedItem.getItemMeta()) - durPerItem));
			}
			
			if(DamageUtils.getDamage(combinedItem.getItemMeta()) < 0) {
				DamageUtils.setDamage(combinedItem, 0);
			}
			if(amount > 0) {
				itemTwoLeftover = itemTwo.clone();
				itemTwoLeftover.setAmount(amount);
			} else {
				itemTwoLeftover = new ItemStack(Material.AIR);
			}
		}else if(itemTwo.getType() == itemOne.getType()) {
			int extraDurability = itemTwo.getType().getMaxDurability() - DamageUtils.getDamage(itemTwo.getItemMeta()) + (int) (itemTwo.getType().getMaxDurability() * .12);
			DamageUtils.setDamage(combinedItem, DamageUtils.getDamage(combinedItem.getItemMeta()) - extraDurability);
			if(DamageUtils.getDamage(combinedItem.getItemMeta()) < 0) {
				DamageUtils.setDamage(combinedItem, 0);
			}
		}
		enchantments = Enchantments.combineEnchantments(player, itemOne, itemTwo);
		repairCost += enchantments.getCost();
		int itemOneRepair = AnvilNMS.getRepairCost(itemOne);
		int itemTwoRepair = AnvilNMS.getRepairCost(itemTwo);
		if(itemOneRepair > itemTwoRepair) {
			combinedItem = AnvilNMS.setRepairCost(combinedItem, itemOneRepair * 2 + 1);
		}else {
			combinedItem = AnvilNMS.setRepairCost(combinedItem, itemTwoRepair * 2 + 1);
		}
		if(enchantments != null) {
			combinedItem = Enchantments.addEnchantmentsToItem(combinedItem, enchantments.getEnchantments());
		}
		repairCost += itemOneRepair + itemTwoRepair;
		
		repairCost = Math.max(repairCost / ConfigUtils.getLevelDivisor(), 1);
	}

	public ItemStack getItemOne() {
		return itemOne;
	}

	public void setItemOne(ItemStack itemOne) {
		this.itemOne = itemOne;
	}

	public ItemStack getItemTwo() {
		return itemTwo;
	}

	public void setItemTwo(ItemStack itemTwo) {
		this.itemTwo = itemTwo;
	}

	public ItemStack getCombinedItem() {
		return combinedItem;
	}

	public void setCombinedItem(ItemStack combinedItem) {
		this.combinedItem = combinedItem;
	}

	public List<EnchantmentLevel> getLevels() {
		return enchantments.getEnchantments();
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getRepairCost() {
		return repairCost;
	}
	
	public RepairType getRepairType() {
		return RepairType.getRepairType(itemOne, itemTwo);
	}
	
	public ItemStack getItemTwoLeftover(){
		return itemTwoLeftover;
	}

	public boolean isDifferent() {
		return checkEnchantments(itemOne, combinedItem) || checkEnchantments(combinedItem, itemOne) ||
				DamageUtils.getDamage(itemOne.getItemMeta()) != DamageUtils.getDamage(combinedItem.getItemMeta());
	}
	
	private boolean checkEnchantments(ItemStack one, ItemStack two) {
		Iterator<Entry<Enchantment, Integer>> i = one.getItemMeta().getEnchants().entrySet().iterator();
		
		while(i.hasNext()) {
			Entry<Enchantment, Integer> entry = i.next();
			
			if(two.getItemMeta().getEnchantLevel(entry.getKey()) != entry.getValue()) {
				return true;
			}
		}
		
		return false;
	}
}
