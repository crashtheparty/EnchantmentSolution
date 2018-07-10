package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class FrequentFlyerListener implements Listener, Runnable{
	
	public static List<FrequentFlyerPlayer> canFly = new ArrayList<FrequentFlyerPlayer>();
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.FREQUENT_FLYER)) return;
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.PLUGIN, new Runnable(){
			
			@Override
			public void run() {
				Player player = event.getPlayer();
				ItemStack elytra = player.getInventory().getChestplate();
				if(elytra != null && !elytra.getType().equals(Material.ELYTRA)) {
					elytra = null;
				}
				FrequentFlyerPlayer ffPlayer = new FrequentFlyerPlayer(player, elytra);
				if(ffPlayer.canFly() && !player.isOnGround() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
					player.setFlying(true);
				}
				canFly.add(ffPlayer);
			}
		}, 0l);
		
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.FREQUENT_FLYER)) return;
		FrequentFlyerPlayer remove = null;
		for(FrequentFlyerPlayer ffPlayer : canFly) {
			if(ffPlayer.getPlayer().getUniqueId().toString().equals(event.getPlayer().getUniqueId().toString())) {
				ffPlayer.setElytra(null);
				remove = ffPlayer;
				break;
			}
		}
		if(remove != null) {
			canFly.remove(remove);
		}
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.FREQUENT_FLYER)) return;
		for(FrequentFlyerPlayer ffPlayer : canFly) {
			Player player = ffPlayer.getPlayer();
			if(player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack elytra = player.getInventory().getChestplate();
				if(elytra != null && elytra.getType().equals(Material.ELYTRA)) {
					if(ffPlayer.getElytra() != null) {
						if(!elytra.toString().equalsIgnoreCase(ffPlayer.getElytra().toString())) {
							ffPlayer.setElytra(player.getInventory().getChestplate(), true);
						}
						if(player.isFlying() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
							ffPlayer.minus();
						}
					}else {
						ffPlayer.setElytra(player.getInventory().getChestplate());
					}
				}else {
					if((elytra == null || !elytra.getType().equals(Material.ELYTRA)) && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
						ffPlayer.setElytra(null);
						ffPlayer.setCanFly(false);
					}
				}
			}else {
				canFly.remove(ffPlayer);
			}
		}
	}
	
	protected class FrequentFlyerPlayer{
		
		private Player player;
		private ItemStack elytra;
		private ItemStack previousElytra;
		private boolean canFly;
		private int underLimit, aboveLimit, under, above;
		
		public FrequentFlyerPlayer(Player player, ItemStack elytra) {
			this.player = player;
			this.setElytra(elytra, true);
			
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack getElytra() {
			return elytra;
		}
		
		public void setElytra(ItemStack elytra) {
			boolean reset = false;
			if(elytra != null && previousElytra != null && elytra.toString().equalsIgnoreCase(previousElytra.toString())) {
				reset = true;
			}
			setElytra(elytra, reset);
		}

		public void setElytra(ItemStack elytra, boolean reset) {
			previousElytra = this.elytra;
			this.elytra = elytra;
			underLimit = 0;
			aboveLimit = 0;
			boolean fly = false;
			if(elytra != null && Enchantments.hasEnchantment(elytra, DefaultEnchantments.FREQUENT_FLYER)) {
				int level = Enchantments.getLevel(elytra, DefaultEnchantments.FREQUENT_FLYER);
				underLimit = level * 4;
				aboveLimit = level;
				if(elytra.getDurability() < 400) {
					fly = true;
				}
			}
			setCanFly(fly);
			if(reset) {
				under = underLimit;
				above = aboveLimit;
			}
		}

		public boolean canFly() {
			return canFly;
		}

		public void setCanFly(boolean canFly) {
			this.canFly = canFly;
			player.setAllowFlight(this.canFly || player.getGameMode() == null || player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR));
			if(player.isFlying() && !this.canFly) {
				player.setFlying(false);
			}
		}

		public int getUnder() {
			return under;
		}
		
		public void minus() {
			if(player.getLocation().getY() > 255) {
				above = above - 1;
				if(above <= 0) {
					double random = Math.random();
					double chance = (1.0D/(Enchantments.getLevel(elytra, Enchantment.DURABILITY)+1));
					int durabilityChange = 1;
					if(chance < random) {
						durabilityChange = 0;
					}
					elytra.setDurability((short) (elytra.getDurability() + durabilityChange));
					if(elytra.getDurability() >= 400) {
						setCanFly(false);
					}
					above = aboveLimit;
					player.getInventory().setChestplate(elytra);
				}
			}else{
				under = under - 1;
				if(under <= 0) {
					double random = Math.random();
					double chance = (1.0D/(Enchantments.getLevel(elytra, Enchantment.DURABILITY)+1));
					int durabilityChange = 1;
					if(chance < random) {
						durabilityChange = 0;
					}
					elytra.setDurability((short) (elytra.getDurability() + durabilityChange));
					if(elytra.getDurability() >= 400) {
						setCanFly(false);
					}
					player.getInventory().setChestplate(elytra);
					under = underLimit;
				}
			}
		}

		public int getAbove() {
			return above;
		}
		
	}

}
