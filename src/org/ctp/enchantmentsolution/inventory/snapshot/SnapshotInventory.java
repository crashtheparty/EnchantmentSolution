package org.ctp.enchantmentsolution.inventory.snapshot;

import java.util.*;
import java.util.Map.Entry;

import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.ctp.enchantmentsolution.enchantments.CustomEnchantment;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.StringUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class SnapshotInventory {

	private ItemStack[] items = new ItemStack[37];
	private ItemStack[] armor = new ItemStack[4];
	private OfflinePlayer player;

	public SnapshotInventory(Player player) {
		this.player = player;

		setInventory();
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public void setInventory() {
		if (player.isOnline()) {
			Player p = (Player) player;
			PlayerInventory inv = p.getInventory();
			for(int i = 0; i < 36; i++) {
				ItemStack item = inv.getItem(i);
				try {
					items[i] = checkItem(item, items[i]);
				} catch (Exception ex) {
					ChatUtils.sendWarning("There was a problem trying to save item " + item + " in slot " + i + ": ");
					ex.printStackTrace();
				}
			}
			ItemStack offhand = inv.getItemInOffHand();
			try {
				items[36] = checkItem(offhand, items[36]);
			} catch (Exception ex) {
				ChatUtils.sendWarning("There was a problem trying to save item " + offhand + " in offhand slot: ");
				ex.printStackTrace();
			}
			for(int i = 0; i < 4; i++) {
				ItemStack item = inv.getArmorContents()[i];
				try {
					armor[i] = checkItem(item, armor[i]);
				} catch (Exception ex) {
					ChatUtils.sendWarning("There was a problem trying to save item " + item + " in armor slot " + i + ": ");
					ex.printStackTrace();
				}
			}

		}
	}

	private ItemStack checkItem(ItemStack item, ItemStack previous) {
		if (item != null) {
			List<EnchantmentLevel> enchantMeta = ItemUtils.getEnchantmentLevels(item);
			List<EnchantmentLevel> enchantLore = new ArrayList<EnchantmentLevel>();
			if (item.hasItemMeta() && item.getItemMeta().hasLore()) for(String s: item.getItemMeta().getLore())
				if (StringUtils.isEnchantment(s)) {
					EnchantmentLevel enchant = StringUtils.getEnchantment(s);
					if (enchant != null) enchantLore.add(enchant);
				}
			boolean change = checkSimilar(enchantMeta, enchantLore) || checkSimilar(enchantLore, enchantMeta);
			if(change) {
				Map<Enchantment, Integer> enchants = new HashMap<Enchantment, Integer>();
				for(EnchantmentLevel enchant : enchantMeta) {
					int level = enchant.getLevel();
					Enchantment ench = enchant.getEnchant().getRelativeEnchantment();
					if(enchants.containsKey(ench)) level = (level > enchants.get(ench) ? level : enchants.get(ench));
					enchants.put(ench, level);
				}
				if (enchants.size() > 0) {
					Iterator<Entry<Enchantment, Integer>> iter = enchants.entrySet().iterator();
					while(iter.hasNext()) {
						Entry<Enchantment, Integer> entry = iter.next();
						CustomEnchantment custom = RegisterEnchantments.getCustomEnchantment(entry.getKey());
						ItemUtils.removeEnchantmentFromItem(item, custom);
						ItemUtils.addEnchantmentToItem(item, custom, entry.getValue());
					}
				}
			}
			return item.clone();
		}
		return null;
	}
	
	private boolean checkSimilar(List<EnchantmentLevel> levels, List<EnchantmentLevel> levelsTwo) {
		for(EnchantmentLevel level : levels) {
			EnchantmentLevel hasLevel = null;
			for(EnchantmentLevel levelTwo : levelsTwo)
				if(level.getEnchant().equals(levelTwo.getEnchant())) {
					hasLevel = level;
					break;
				}
			if(hasLevel == null) return true;
		}
		return false;
	}
}
