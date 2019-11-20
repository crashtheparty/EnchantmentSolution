package org.ctp.enchantmentsolution.events.player;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.events.ESPlayerEvent;

public class FrequentFlyerEvent extends ESPlayerEvent {

	private final FFType type;

	public FrequentFlyerEvent(Player who, FFType type) {
		super(who);
		this.type = type;
	}

	public FFType getType() {
		return type;
	}

	public enum FFType {
		ALLOW_FLIGHT(), BREAK_ELYTRA(), REMOVE_FLIGHT();
	}
}
