package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.*;
import java.util.Map.Entry;

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

	public static Iterator<Entry<UUID, List<OverkillDeath>>> getIterator() {
		return DEATHS.entrySet().iterator();
	}
}
