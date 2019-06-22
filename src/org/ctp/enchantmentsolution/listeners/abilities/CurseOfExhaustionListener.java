package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class CurseOfExhaustionListener implements Runnable {

	private static List<ExhaustionPlayer> EXHAUSTION = new ArrayList<ExhaustionPlayer>();
	
	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.CURSE_OF_EXHAUSTION)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!contains(player)) {
				try {
					ExhaustionPlayer exhaustionPlayer = new ExhaustionPlayer(player);
					EXHAUSTION.add(exhaustionPlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = EXHAUSTION.size() - 1; i >= 0; i--) {
			ExhaustionPlayer player = EXHAUSTION.get(i);
			Player p = player.getPlayer();
			if(p != null && Bukkit.getOnlinePlayers().contains(p)) {
				int exhaustionCurse = player.getExhaustionCurse();
				float change = player.getExhaustionInTick(p) * exhaustionCurse;
				if(change > 0) {
					p.setExhaustion(p.getExhaustion() + change);
					if(exhaustionCurse > 0) {
						AdvancementUtils.awardCriteria(p, ESAdvancement.HIGH_METABOLISM, "exhaustion");
					}
				}
				player.setPlayer(p);
			} else {
				EXHAUSTION.remove(player);
			}
		}
	}
	
	private boolean contains(Player player) {
		for(ExhaustionPlayer exPlayer : EXHAUSTION) {
			if(exPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	private class ExhaustionPlayer{
		
		private int foodLevel, exhaustionCurse;
		private float exhaustionLevel, saturationLevel;
		private Player player;
		
		private ExhaustionPlayer(Player player) {
			setPlayer(player);
		}

		public int getFoodLevel() {
			return foodLevel;
		}

		public void setFoodLevel(int foodLevel) {
			this.foodLevel = foodLevel;
		}

		public float getSaturationLevel() {
			return saturationLevel;
		}

		public void setSaturationLevel(float saturationLevel) {
			this.saturationLevel = saturationLevel;
		}

		public float getExhaustionLevel() {
			return exhaustionLevel;
		}

		public void setExhaustionLevel(float exhaustionLevel) {
			this.exhaustionLevel = exhaustionLevel;
		}

		public Player getPlayer() {
			return player;
		}

		public void setPlayer(Player player) {
			this.player = player;
			setFoodLevel(player.getFoodLevel());
			setSaturationLevel(player.getSaturation());
			setExhaustionLevel(player.getExhaustion());
			setExhaustionCurse();
		}

		public int getExhaustionCurse() {
			return exhaustionCurse;
		}
		
		public float getExhaustionInTick(Player player) {
			float oldBase = getFoodLevel() * 4 + getSaturationLevel() * 4 - getExhaustionLevel();
			float base = player.getFoodLevel() * 4 + player.getSaturation() * 4 - player.getExhaustion();
			return oldBase - base >= 0 ? oldBase - base : 0;
		}

		private void setExhaustionCurse() {
			exhaustionCurse = 0;
			for(ItemStack item : player.getInventory().getArmorContents()) {
				if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.CURSE_OF_EXHAUSTION)){
					exhaustionCurse += Enchantments.getLevel(item, DefaultEnchantments.CURSE_OF_EXHAUSTION);
				}
			}
			ItemStack mainHand = player.getInventory().getItemInMainHand();
			if(mainHand != null && Enchantments.hasEnchantment(mainHand, DefaultEnchantments.CURSE_OF_EXHAUSTION)){
				exhaustionCurse += Enchantments.getLevel(mainHand, DefaultEnchantments.CURSE_OF_EXHAUSTION);
			}
			ItemStack offHand = player.getInventory().getItemInOffHand();
			if(offHand != null && Enchantments.hasEnchantment(offHand, DefaultEnchantments.CURSE_OF_EXHAUSTION)){
				exhaustionCurse += Enchantments.getLevel(offHand, DefaultEnchantments.CURSE_OF_EXHAUSTION);
			}
		}
	}
}
