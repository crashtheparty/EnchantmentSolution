package org.ctp.enchantmentsolution.inventory.snapshot;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
			List<EnchantmentLevel> enchants = ItemUtils.getEnchantmentLevels(item);
			if (enchants.size() > 0) {
				if (item.hasItemMeta() && item.getItemMeta().hasLore()) for(String s: item.getItemMeta().getLore())
					if (StringUtils.isEnchantment(s)) {
						EnchantmentLevel enchant = StringUtils.getEnchantment(s);
						if (enchant != null) enchants.add(enchant);
					}
				for(EnchantmentLevel ench: enchants) {
					ItemUtils.removeEnchantmentFromItem(item, ench.getEnchant());
					if (ench.getLevel() > 0) ItemUtils.addEnchantmentToItem(item, ench.getEnchant(), ench.getLevel());
				}
			}
			return item.clone();
		}
		return null;
	}
}
