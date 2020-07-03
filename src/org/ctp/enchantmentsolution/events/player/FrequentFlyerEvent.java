package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.ESPlayerToggleFlightEvent;

public class FrequentFlyerEvent extends ESPlayerToggleFlightEvent {

	private final FFType type;

	public FrequentFlyerEvent(Player who, int level, FFType type) {
		super(who, type.getChangeFlight(), new EnchantmentLevel(CERegister.FREQUENT_FLYER, level));
		this.type = type;
	}

	public FFType getType() {
		return type;
	}

	public enum FFType {
		ALLOW_FLIGHT(true, true), REMOVE_FLIGHT(false, true), FLIGHT(true, false), NONE(false, false);

		private final boolean changeFlight, allowFlight;

		FFType(boolean allowFlight, boolean changeFlight) {
			this.allowFlight = allowFlight;
			this.changeFlight = changeFlight;
		}

		public boolean getChangeFlight() {
			return changeFlight;
		}

		public boolean doesAllowFlight() {
			return allowFlight;
		}
	}
}
