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
		ALLOW_FLIGHT(true), BREAK_ELYTRA(false), REMOVE_FLIGHT(false);

		private final boolean changeFlight;

		FFType(boolean changeFlight) {
			this.changeFlight = changeFlight;
		}

		public boolean getChangeFlight() {
			return changeFlight;
		}
	}
}
