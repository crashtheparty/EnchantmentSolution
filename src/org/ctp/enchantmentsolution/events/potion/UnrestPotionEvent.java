package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.events.PotionEffectEvent;

public class UnrestPotionEvent extends PotionEffectEvent {

	private final PotionEventType unrest;

	public UnrestPotionEvent(Player who, PotionEventType unrest) {
		super(who, PotionEffectType.NIGHT_VISION);
		this.unrest = unrest;
	}

	public PotionEventType getUnrest() {
		return unrest;
	}
}
