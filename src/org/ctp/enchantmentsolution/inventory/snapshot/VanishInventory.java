package org.ctp.enchantmentsolution.inventory.snapshot;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.ctp.enchantmentsolution.Chatable;

public class VanishInventory implements SavedInventory {
	private ItemStack[] items = new ItemStack[37];
	private ItemStack[] armor = new ItemStack[4];
	private OfflinePlayer player;

	public VanishInventory(Player player) {
		this.player = player;

		setInventory();
	}

	@Override
	public OfflinePlayer getPlayer() {
		return player;
	}

	@Override
	public void setInventory() {
		if (player.isOnline()) {
			Player p = (Player) player;
			PlayerInventory inv = p.getInventory();
			for(int i = 0; i < 36; i++) {
				ItemStack item = inv.getItem(i);
				try {
					items[i] = item;
				} catch (Exception ex) {
					Chatable.get().sendWarning("There was a problem trying to save item " + item + " in slot " + i + ": ");
					ex.printStackTrace();
				}
			}
			ItemStack offhand = inv.getItemInOffHand();
			try {
				items[36] = offhand;
			} catch (Exception ex) {
				Chatable.get().sendWarning("There was a problem trying to save item " + offhand + " in offhand slot: ");
				ex.printStackTrace();
			}
			for(int i = 0; i < 4; i++) {
				ItemStack item = inv.getArmorContents()[i];
				try {
					armor[i] = item;
				} catch (Exception ex) {
					Chatable.get().sendWarning("There was a problem trying to save item " + item + " in armor slot " + i + ": ");
					ex.printStackTrace();
				}
			}

		}
	}

	public ItemStack[] getItems() {
		ItemStack[] items = new ItemStack[41];
		int i = 0;
		for(ItemStack item: this.items) {
			items[i] = item;
			i++;
		}
		for(ItemStack item: armor) {
			items[i] = item;
			i++;
		}
		return items;
	}
}
