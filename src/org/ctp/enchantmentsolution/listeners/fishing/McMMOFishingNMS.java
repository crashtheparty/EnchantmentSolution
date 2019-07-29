package org.ctp.enchantmentsolution.listeners.fishing;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.listeners.fishing.mcmmo.McMMOClassicFishingListener;
import org.ctp.enchantmentsolution.listeners.fishing.mcmmo.McMMOOverhaulFishingListener;

import com.gmail.nossr50.events.skills.fishing.McMMOPlayerFishingTreasureEvent;

public class McMMOFishingNMS implements Listener{

	@EventHandler
	public void onMcMMOPlayerFishingTreasure(McMMOPlayerFishingTreasureEvent event) {
		switch(EnchantmentSolution.getPlugin().getMcMMOType()) {
		case "Overhaul":
			McMMOOverhaulFishingListener overhaul = new McMMOOverhaulFishingListener();
			overhaul.onMcMMOPlayerFishingTreasure(event);
			break;
		case "Classic":
			McMMOClassicFishingListener classic = new McMMOClassicFishingListener();
			classic.onMcMMOPlayerFishingTreasure(event);
			break;
		case "Disabled":
			break;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerFish(PlayerFishEvent event) {
		switch(EnchantmentSolution.getPlugin().getMcMMOType()) {
		case "Overhaul":
			McMMOOverhaulFishingListener overhaul = new McMMOOverhaulFishingListener();
			overhaul.onPlayerFish(event);
			break;
		case "Classic":
			McMMOClassicFishingListener classic = new McMMOClassicFishingListener();
			classic.onPlayerFish(event);
			break;
		case "Disabled":
			break;
		}
	}
}
