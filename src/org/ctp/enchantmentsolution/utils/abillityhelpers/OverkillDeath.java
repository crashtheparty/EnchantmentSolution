package org.ctp.enchantmentsolution.utils.abillityhelpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OverkillDeath {

	private static HashMap<UUID, List<OverkillDeath>> DEATHS = new HashMap<UUID, List<OverkillDeath>>();

	private int ticks;

	public OverkillDeath() {
		ticks = 90;
	}

	public static List<OverkillDeath> getDeaths(UUID uuid) {
		return DEATHS.get(uuid);
	}

	public static void addDeath(UUID uuid) {
		List<OverkillDeath> deaths = getDeaths(uuid);
		if (deaths == null) deaths = new ArrayList<OverkillDeath>();
		deaths.add(new OverkillDeath());
		DEATHS.put(uuid, deaths);
	}

	public int getTicks() {
		return ticks;
	}

	public void minus() {
		ticks--;
	}
}
