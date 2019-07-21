package org.ctp.enchantmentsolution.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.inventory.tutorial.EnchantmentInventory;
import org.ctp.enchantmentsolution.inventory.tutorial.EnchantmentListInventory;
import org.ctp.enchantmentsolution.inventory.tutorial.TutorialInventory;

public class TutorialClickUtils {

	public static void setTutorialDetails(TutorialInventory tutorial, Player player, Inventory inv, Inventory clickedInv, int slot) {
		ItemStack item = clickedInv.getItem(slot);
		if(item == null) return;
		tutorial.open(slot);
	}
	
	public static void setEnchantmentListDetails(EnchantmentListInventory enchantmentList, Player player, Inventory inv, Inventory clickedInv, int slot) {
		ItemStack item = clickedInv.getItem(slot);
		if(item == null) return;
		if(slot >= 36) {
			switch(slot) {
			case 45:
				if(clickedInv.getItem(slot) != null) {
					enchantmentList.setPage(enchantmentList.getPage() - 1);
					enchantmentList.setInventory();
				}
				break;
			case 49:
				if(clickedInv.getItem(slot) != null) {
					enchantmentList.openTutorial();
				}
				break;
			case 53:
				if(clickedInv.getItem(slot) != null) {
					enchantmentList.setPage(enchantmentList.getPage() + 1);
					enchantmentList.setInventory();
				}
				break;
			}
		} else {
			enchantmentList.openEnchantment(slot);
		}
	}
	
	public static void setEnchantmentDetails(EnchantmentInventory enchantment, Player player, Inventory inv, Inventory clickedInv, int slot) {
		ItemStack item = clickedInv.getItem(slot);
		if(item == null) return;
		switch(slot) {
		case 45:
			if(clickedInv.getItem(slot) != null) {
				enchantment.openEnchantmentList();
			}
			break;
		}
	}
}
