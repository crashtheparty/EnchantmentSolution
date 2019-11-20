package org.ctp.enchantmentsolution.utils.abillityhelpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;

public class IcarusDelay {

	private static List<IcarusDelay> ICARUS_DELAY = new ArrayList<IcarusDelay>();
	private OfflinePlayer player;
	private int delay;

	public IcarusDelay(OfflinePlayer player) {
		this.player = player;
		delay = 30;
	}

	public static List<IcarusDelay> getIcarusDelay() {
		return ICARUS_DELAY;
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public int getDelay() {
		return delay;
	}

	public void minusDelay() {
		if (delay > 0) {
			delay--;
		}
	}
}