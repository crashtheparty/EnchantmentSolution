package org.ctp.enchantmentsolution.utils.abillityhelpers;

import org.bukkit.entity.Player;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;

public class ExhaustionPlayer {

	private final Player player;
	private float currentExhaustion, pastExhaustion;

	public ExhaustionPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public int getExhaustion() {
		return AbilityUtils.getExhaustionCurse(player);
	}

	public void setCurrentExhaustion() {
		pastExhaustion = currentExhaustion;
		currentExhaustion = AbilityUtils.getExhaustion(player);
	}

	public float getPastExhaustion() {
		return pastExhaustion;
	}

	public float getCurrentExhaustion() {
		return currentExhaustion;
	}

}
