package org.ctp.enchantmentsolution.listeners.fishing;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.mcmmo.McMMOClassicFishing;
import org.ctp.enchantmentsolution.mcmmo.McMMOOverhaulFishing;

import com.gmail.nossr50.events.skills.fishing.McMMOPlayerFishingTreasureEvent;

public class McMMOFishingListener implements Listener {

	@EventHandler
	public void onMcMMOPlayerFishingTreasure(McMMOPlayerFishingTreasureEvent event) {
		switch (EnchantmentSolution.getPlugin().getMcMMOType()) {
			case "Overhaul":
				McMMOOverhaulFishing overhaul = new McMMOOverhaulFishing();
				overhaul.onMcMMOPlayerFishingTreasure(event);
				break;
			case "Classic":
				McMMOClassicFishing classic = new McMMOClassicFishing();
				classic.onMcMMOPlayerFishingTreasure(event);
				break;
			case "Disabled":
				break;
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerFish(PlayerFishEvent event) {
		switch (EnchantmentSolution.getPlugin().getMcMMOType()) {
			case "Overhaul":
				McMMOOverhaulFishing overhaul = new McMMOOverhaulFishing();
				overhaul.onPlayerFish(event);
				break;
			case "Classic":
				McMMOClassicFishing classic = new McMMOClassicFishing();
				classic.onPlayerFish(event);
				break;
			case "Disabled":
				break;
		}
	}
}
