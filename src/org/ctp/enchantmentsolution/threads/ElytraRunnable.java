package org.ctp.enchantmentsolution.threads;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.ctp.enchantmentsolution.utils.Reflectionable;
import org.ctp.enchantmentsolution.utils.abillityhelpers.FrequentFlyerPlayer;

@SuppressWarnings("unused")
public class ElytraRunnable implements Runnable, Reflectionable {

	private Map<UUID, FrequentFlyerPlayer> PLAYERS = new HashMap<UUID, FrequentFlyerPlayer>();
	private int run;

	@Override
	public void run() {
		runMethod(this, "frequentFlyer");
		if (run % 20 == 0) {
			runMethod(this, "icarus");
			run = 0;
		}
		run++;
	}
}
