package org.ctp.enchantmentsolution.events.potion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.events.entity.PotionEffectEvent;

public class UnrestPotionEvent extends PotionEffectEvent {

	private final PotionEventType unrest;

	public UnrestPotionEvent(Player who, PotionEventType unrest) {
		super(who, new EnchantmentLevel(CERegister.UNREST, 1), PotionEffectType.NIGHT_VISION);
		this.unrest = unrest;
	}

	public PotionEventType getUnrest() {
		return unrest;
	}
}
