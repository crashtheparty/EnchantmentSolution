package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class UnrestListener implements Runnable {

	private static List<UnrestPlayer> HAS_UNREST = new ArrayList<UnrestPlayer>();
	
	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.NO_REST)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!contains(player)) {
				try {
					ItemStack helmet = player.getInventory().getHelmet();
					UnrestPlayer unrestPlayer = new UnrestPlayer(player, helmet);
					HAS_UNREST.add(unrestPlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = HAS_UNREST.size() - 1; i >= 0; i--) {
			UnrestPlayer unrestPlayer = HAS_UNREST.get(i);
			Player player = unrestPlayer.getPlayer();
			if(player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack helmet = player.getInventory().getHelmet();
				unrestPlayer.setHelmet(helmet);
				if(unrestPlayer.hasUnrest()) {
					if(player.getStatistic(Statistic.TIME_SINCE_REST) < 96000) {
						player.setStatistic(Statistic.TIME_SINCE_REST, 96000);
					}
					player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 305, 0, false, false), true);
				}
			}else {
				HAS_UNREST.remove(unrestPlayer);
			}
		}
	}
	
	private boolean contains(Player player) {
		for(UnrestPlayer unrestPlayer : HAS_UNREST) {
			if(unrestPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}
	
	protected class UnrestPlayer{
		
		private Player player;
		private ItemStack helmet;
		
		public UnrestPlayer(Player player, ItemStack helmet) {
			this.player = player;
			this.setHelmet(helmet);
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack getHelmet() {
			return helmet;
		}
		
		public void setHelmet(ItemStack helmet) {
			this.helmet = helmet;
		}
		
		public boolean hasUnrest() {
			if(helmet == null) {
				return false;
			}
			return Enchantments.hasEnchantment(helmet, DefaultEnchantments.UNREST);
		}
		
	}
	
}
