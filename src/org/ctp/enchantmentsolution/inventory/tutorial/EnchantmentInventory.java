package org.ctp.enchantmentsolution.inventory.tutorial;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.inventory.InventoryData;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class EnchantmentInventory implements InventoryData{
	private Player player;
	private Inventory inventory;
	private boolean opening;
	private CustomEnchantment enchantment;
	
	public EnchantmentInventory(Player player, CustomEnchantment enchantment) {
		this.player = player;
		this.enchantment = enchantment;
	}
	
	public void setInventory() {		
		Inventory inv = Bukkit.createInventory(null, 54, ChatUtils.getMessage(getCodes(), "tutorial.enchantment.name"));
		inv = open(inv);
		
		ItemStack enchantment = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta enchantmentMeta = enchantment.getItemMeta();
		enchantmentMeta.setDisplayName(ChatUtils.getMessage(getCodes("%name%", this.enchantment.getDisplayName()), "tutorial.enchantment.enchantment_name"));
		enchantment.setItemMeta(enchantmentMeta);
		enchantment = Enchantments.addEnchantmentToItem(enchantment, this.enchantment, 1);
		inv.setItem(4, enchantment);
				
		ItemStack name = new ItemStack(Material.NAME_TAG);
		ItemMeta nameMeta = name.getItemMeta();
		nameMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.name") + this.enchantment.getDisplayName());
		name.setItemMeta(nameMeta);
		inv.setItem(19, name);
		
		ItemStack description = new ItemStack(Material.BOOK);
		ItemMeta descriptionMeta = description.getItemMeta();
		descriptionMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.description"));
		descriptionMeta.setLore(ChatUtils.splitForLore(this.enchantment.getDescription()));
		description.setItemMeta(descriptionMeta);
		inv.setItem(20, description);
		
		ItemStack maxLevel = new ItemStack(Material.SUNFLOWER);
		ItemMeta maxLevelMeta = maxLevel.getItemMeta();
		maxLevelMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.max-level") + this.enchantment.getMaxLevel() + ".");
		maxLevel.setItemMeta(maxLevelMeta);
		inv.setItem(21, maxLevel);
		
		ItemStack weight = new ItemStack(Material.GOLD_BLOCK);
		ItemMeta weightMeta = weight.getItemMeta();
		weightMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.weight") + this.enchantment.getWeightName() + ".");
		weight.setItemMeta(weightMeta);
		inv.setItem(22, weight);
		
		ItemStack startLevel = new ItemStack(Material.SUNFLOWER);
		ItemMeta startLevelMeta = startLevel.getItemMeta();
		startLevelMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.start-level") + this.enchantment.getStartLevel() + ".");
		startLevel.setItemMeta(startLevelMeta);
		inv.setItem(23, startLevel);
		
		ItemStack enabled = new ItemStack(Material.BARRIER);
		ItemMeta enabledMeta = enabled.getItemMeta();
		enabledMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.enabled") + this.enchantment.isEnabled() + ".");
		enabled.setItemMeta(enabledMeta);
		inv.setItem(24, enabled);
		
		ItemStack treasure = new ItemStack(Material.EMERALD);
		ItemMeta treasureMeta = treasure.getItemMeta();
		treasureMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.treasure") + this.enchantment.isTreasure() + ".");
		treasure.setItemMeta(treasureMeta);
		inv.setItem(25, treasure);
		
		ItemStack enchantableItems = new ItemStack(Material.ENCHANTING_TABLE);
		ItemMeta enchantableItemsMeta = enchantableItems.getItemMeta();
		enchantableItemsMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.enchantable-items"));
		enchantableItemsMeta.setLore(this.enchantment.getEnchantmentItemTypesLore());
		enchantableItems.setItemMeta(enchantableItemsMeta);
		inv.setItem(29, enchantableItems);
		
		ItemStack anvilableItems = new ItemStack(Material.ANVIL);
		ItemMeta anvilableItemsMeta = anvilableItems.getItemMeta();
		anvilableItemsMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.anvilable-items"));
		anvilableItemsMeta.setLore(this.enchantment.getAnvilItemTypesLore());
		anvilableItems.setItemMeta(anvilableItemsMeta);
		inv.setItem(30, anvilableItems);
		
		ItemStack disabledItems = new ItemStack(Material.BARRIER);
		ItemMeta disabledItemsMeta = disabledItems.getItemMeta();
		disabledItemsMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.disabled-items"));
		disabledItemsMeta.setLore(this.enchantment.getDisabledItemsLore());
		disabledItems.setItemMeta(disabledItemsMeta);
		inv.setItem(32, disabledItems);
		
		ItemStack conflictingEnchantments = new ItemStack(Material.BARRIER);
		ItemMeta conflictingEnchantmentsMeta = conflictingEnchantments.getItemMeta();
		conflictingEnchantmentsMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "enchantment.conflicting-enchantments"));
		conflictingEnchantmentsMeta.setLore(this.enchantment.getConflictingEnchantmentsLore());
		conflictingEnchantments.setItemMeta(conflictingEnchantmentsMeta);
		inv.setItem(33, conflictingEnchantments);
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "tutorial.pagination.go_back"));
		goBack.setItemMeta(goBackMeta);
		inv.setItem(45, goBack);
	}
	
	@Override
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@Override
	public Block getBlock() {
		return null;
	}

	@Override
	public void close(boolean external) {
		if(!external) {
			player.closeInventory();
		}
		EnchantmentSolution.getPlugin().removeInventory(this);
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	@Override
	public void setInventory(List<ItemStack> items) {
		setInventory();
	}
	
	public HashMap<String, Object> getCodes() {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		return codes;
	}
	
	public HashMap<String, Object> getCodes(String key, Object value) {
		HashMap<String, Object> codes = new HashMap<String, Object>();
		codes.put("%player%", player.getName());
		codes.put(key, value);
		return codes;
	}

	@Override
	public void setItemName(String name) {
		
	}

	@Override
	public Inventory open(Inventory inv) {
		opening = true;
		if(inventory == null) {
			inventory = inv;
			player.openInventory(inv);
		} else {
			if(inv.getSize() == inventory.getSize()) {
				inv = player.getOpenInventory().getTopInventory();
				inventory = inv;
			} else {
				inventory = inv;
				player.openInventory(inv);
			}
		}
		for(int i = 0; i < inventory.getSize(); i++) {
			inventory.setItem(i, new ItemStack(Material.AIR));
		}
		if(opening) {
			opening = false;
		}
		return inv;
	}

	public void openEnchantmentList() {
		this.close(false);
		
		EnchantmentListInventory inv = new EnchantmentListInventory(player);
		EnchantmentSolution.getPlugin().addInventory(inv);
		inv.setInventory();
	}
}
