package org.ctp.enchantmentsolution.listeners.fishing.mcmmo;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.listeners.fishing.McMMOFishingThread;

public class McMMOFishingListener {

	private static List<McMMOFishingThread> PLAYER_ITEMS = new ArrayList<McMMOFishingThread>();
	
	public void add(Player player, ItemStack treasure, int treasureXp) {
		McMMOFishingThread thread = new McMMOFishingThread(player, treasure, treasureXp, this);
		int scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(EnchantmentSolution.getPlugin(), thread, 20l, 20l);
		thread.setScheduler(scheduler);
		PLAYER_ITEMS.add(thread);
	}
	
	public void remove(McMMOFishingThread thread) {
		if(PLAYER_ITEMS.contains(thread)) {
			Bukkit.getScheduler().cancelTask(thread.getScheduler());
			PLAYER_ITEMS.remove(thread);
		}
	}
	
	public List<McMMOFishingThread> getPlayerItems(){
		return PLAYER_ITEMS;
	}
}
