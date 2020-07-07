package org.ctp.enchantmentsolution.inventory.snapshot;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.ctp.enchantmentsolution.nms.PersistenceNMS;
import org.ctp.enchantmentsolution.utils.ChatUtils;

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
					items[i] = PersistenceNMS.checkItem(item, items[i]);
				} catch (Exception ex) {
					ChatUtils.sendWarning("There was a problem trying to save item " + item + " in slot " + i + ": ");
					ex.printStackTrace();
				}
			}
			ItemStack offhand = inv.getItemInOffHand();
			try {
				items[36] = PersistenceNMS.checkItem(offhand, items[36]);
			} catch (Exception ex) {
				ChatUtils.sendWarning("There was a problem trying to save item " + offhand + " in offhand slot: ");
				ex.printStackTrace();
			}
			for(int i = 0; i < 4; i++) {
				ItemStack item = inv.getArmorContents()[i];
				try {
					armor[i] = PersistenceNMS.checkItem(item, armor[i]);
				} catch (Exception ex) {
					ChatUtils.sendWarning("There was a problem trying to save item " + item + " in armor slot " + i + ": ");
					ex.printStackTrace();
				}
			}

		}
	}

}
