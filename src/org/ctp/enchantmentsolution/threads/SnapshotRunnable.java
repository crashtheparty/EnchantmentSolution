package org.ctp.enchantmentsolution.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.inventory.snapshot.SnapshotInventory;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class SnapshotRunnable implements Runnable {

	private static Map<UUID, SnapshotInventory> INVENTORIES = new HashMap<UUID, SnapshotInventory>();
	private Player[] players;
	private int runnable = 0, current = 0;

	public SnapshotRunnable() {
		setRunnable();
	}

	private void setRunnable() {
		runnable = ConfigString.ENCHANTMENT_CHECK.getInt() * 20;
	}

	@Override
	public void run() {
		if (runnable > 0) {
			runnable--;
			return;
		}
		if (players == null) {
			current = 0;
			players = Bukkit.getOnlinePlayers().toArray(new Player[] {});
		}

		if (current < players.length) {
			int index = current;
			for(int i = index; i < index + 10; i++) {
				current = i;
				if (players.length <= current) return;
				Player player = players[i];
				SnapshotInventory inv = INVENTORIES.get(player.getUniqueId());
				if (inv == null) inv = new SnapshotInventory(player);
				else
					inv.setInventory();
				INVENTORIES.put(player.getUniqueId(), inv);
			}
		} else {
			current = 0;
			players = null;
			setRunnable();
		}
	}

	public static void updateInventory(Player player) {
		SnapshotInventory inv = INVENTORIES.get(player.getUniqueId());
		if (inv == null) inv = new SnapshotInventory(player);
		else
			inv.setInventory();
		INVENTORIES.put(player.getUniqueId(), inv);
	}
}
