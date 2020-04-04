package org.ctp.enchantmentsolution.utils.abillityhelpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemData;
import org.ctp.enchantmentsolution.enums.ItemType;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class ContagionPlayer {

	private final Player player;
	private static double CHANCE = 0.0005;

	public ContagionPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public double getChance() {
		double playerChance = 0;
		for(ItemStack item: player.getInventory().getContents())
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_CONTAGION)) playerChance += CHANCE;
		return playerChance;
	}

	public List<ItemStack> getCurseableItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(ItemStack item: player.getInventory().getContents())
			if (item != null && ItemType.hasEnchantMaterial(new ItemData(item)) && canAddCurse(item) && !hasAllCurses(item)) items.add(item);
		for(ItemStack item: player.getInventory().getExtraContents())
			if (item != null && ItemType.hasEnchantMaterial(new ItemData(item)) && canAddCurse(item) && !hasAllCurses(item)) items.add(item);
		return items;
	}

	private boolean hasAllCurses(ItemStack item) {
		boolean noCurse = true;
		for(CustomEnchantment enchantment: RegisterEnchantments.getCurseEnchantments())
			if (enchantment.isCurse() && ItemUtils.canAddEnchantment(enchantment, item) && !ItemUtils.hasEnchantment(item, enchantment.getRelativeEnchantment())) {
				noCurse = false;
				break;
			}
		return noCurse;
	}

	private boolean canAddCurse(ItemStack item) {
		boolean addCurse = false;
		for(CustomEnchantment enchantment: RegisterEnchantments.getCurseEnchantments())
			if (enchantment.isCurse() && ItemUtils.canAddEnchantment(enchantment, item)) {
				addCurse = true;
				break;
			}
		return addCurse;
	}
}
