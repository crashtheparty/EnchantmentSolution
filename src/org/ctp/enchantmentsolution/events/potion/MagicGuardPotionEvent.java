package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.events.PotionEffectEvent;

public class MagicGuardPotionEvent extends PotionEffectEvent {

	public MagicGuardPotionEvent(Player who, PotionEffectType type) {
		super(who, type);
	}

}
