package org.ctp.enchantmentsolution.listeners.abilities.support;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.datatypes.skills.AbilityType;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityDeactivateEvent;

public class McMMOClassicAbility implements Listener{

	private static List<Player> IGNORE_PLAYERS = new ArrayList<Player>();
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOAbilityActivated(McMMOPlayerAbilityActivateEvent event) {
		if(event.getAbility().equals(AbilityType.TREE_FELLER) && !IGNORE_PLAYERS.contains(event.getPlayer())) {
			IGNORE_PLAYERS.add(event.getPlayer());
		}
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onMcMMOAbilityDeactivated(McMMOPlayerAbilityDeactivateEvent event) {
		if(event.getAbility().equals(AbilityType.TREE_FELLER)) {
			IGNORE_PLAYERS.remove(event.getPlayer());
		}
	}
	
	public static List<Player> getIgnored(){
		return IGNORE_PLAYERS;
	}
}
