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

public class EnchantmentListInventory implements InventoryData{

	private Player player;
	private Inventory inventory;
	private int page = 1;
	private boolean opening;
	private final static int PAGING = 36;
	
	public EnchantmentListInventory(Player player) {
		this.player = player;
	}
	
	public void setInventory() {		
		Inventory inv = Bukkit.createInventory(null, 54, ChatUtils.getMessage(getCodes(), "tutorial.enchantment_list.name"));
		inv = open(inv);
		
		if(page < 1) page = 1;
		if(PAGING * (page - 1) >= Enchantments.getEnchantmentsAlphabetical().size() && page != 1) {
			page -= 1;
			setInventory();
			return;
		}
		
		for(int i = 0; i < PAGING; i++) {
			int num = i + (page - 1) * PAGING;
			if(num >= Enchantments.getEnchantments().size()) {
				break;
			}
			
			CustomEnchantment enchantment = Enchantments.getEnchantmentsAlphabetical().get(num);
			
			ItemStack keyItem = new ItemStack(Material.ENCHANTED_BOOK);
			ItemMeta keyItemMeta = keyItem.getItemMeta();
			keyItemMeta.setDisplayName(ChatUtils.getMessage(getCodes("%name%", enchantment.getDisplayName()), "tutorial.enchantment_list.enchantment_name"));
			keyItem.setItemMeta(keyItemMeta);
			keyItem = Enchantments.addEnchantmentToItem(keyItem, enchantment, enchantment.getMaxLevel());
			inv.setItem(i, keyItem);
		}
		
		if(Enchantments.getEnchantmentsAlphabetical().size() > PAGING * page) {
			ItemStack nextPage = new ItemStack(Material.ARROW);
			ItemMeta nextPageMeta = nextPage.getItemMeta();
			nextPageMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.pagination.next_page"));
			nextPage.setItemMeta(nextPageMeta);
			inv.setItem(53, nextPage);
		}
		if(page != 1) {
			ItemStack prevPage = new ItemStack(Material.ARROW);
			ItemMeta prevPageMeta = prevPage.getItemMeta();
			prevPageMeta.setDisplayName(ChatUtils.getMessage(getCodes(), "tutorial.pagination.previous_page"));
			prevPage.setItemMeta(prevPageMeta);
			inv.setItem(45, prevPage);
		}
		
		ItemStack goBack = new ItemStack(Material.ARROW);
		ItemMeta goBackMeta = goBack.getItemMeta();
		goBackMeta.setDisplayName(ChatUtils.getMessage(ChatUtils.getCodes(), "tutorial.pagination.go_back"));
		goBack.setItemMeta(goBackMeta);
		inv.setItem(49, goBack);
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public void openEnchantment(int slot) {
		this.close(false);
		
		int num = PAGING * (page - 1) + slot;
		CustomEnchantment ench = Enchantments.getEnchantmentsAlphabetical().get(num);
		
		EnchantmentInventory inv = new EnchantmentInventory(player, ench);
		EnchantmentSolution.getPlugin().addInventory(inv);
		inv.setInventory();
	}

	public void openTutorial() {
		this.close(false);
		
		TutorialInventory inv = new TutorialInventory(player);
		EnchantmentSolution.getPlugin().addInventory(inv);
		inv.setInventory();
	}
}
