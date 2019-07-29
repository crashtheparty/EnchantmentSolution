package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class IcarusDelay{

	private static List<IcarusDelay> ICARUS_DELAY = new ArrayList<IcarusDelay>();
	private Player player;
	private int delay;
	
	public IcarusDelay(Player player) {
		this.player = player;
		this.delay = 30;
	}
	
	public static List<IcarusDelay> getIcarusDelay(){
		return ICARUS_DELAY;
	}

	public Player getPlayer() {
		return player;
	}

	public int getDelay() {
		return delay;
	}

	public void minusDelay() {
		delay --;
	}
}