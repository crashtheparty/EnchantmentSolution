package org.ctp.enchantmentsolution.listeners.fishing;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.nms.FishingNMS;
import org.ctp.enchantmentsolution.utils.ConfigUtils;

public class EnchantsFishingListener implements Listener{
	
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerFishMonitor(PlayerFishEvent event) {
		if(!ConfigUtils.getFishingLoot()) return;
        switch (event.getState()) {
            case CAUGHT_FISH:
                handleFishing((Item) event.getCaught());
                return;

            default:
                return;
        }
    }
	
	private void handleFishing(Item item) {
		ItemStack itemStack = item.getItemStack().clone();
		
		itemStack = FishingNMS.replaceLoot(itemStack);
		
		item.setItemStack(itemStack);
	}
}
