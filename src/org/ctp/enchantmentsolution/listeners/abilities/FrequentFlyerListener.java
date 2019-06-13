package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class FrequentFlyerListener implements Runnable{
	
	public static List<FrequentFlyerPlayer> CAN_FLY = new ArrayList<FrequentFlyerPlayer>();
	
	private boolean canFly(Player player) {
		for(FrequentFlyerPlayer ffPlayer : CAN_FLY) {
			if(ffPlayer.getPlayer().getUniqueId().equals(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.FREQUENT_FLYER)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!canFly(player)) {
				try {
					ItemStack elytra = player.getInventory().getChestplate();
					if(elytra != null && !elytra.getType().equals(Material.ELYTRA)) {
						elytra = null;
					}
					FrequentFlyerPlayer ffPlayer = new FrequentFlyerPlayer(player, elytra);
					if(ffPlayer.canFly() && !player.isOnGround() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
						player.setFlying(true);
					}
					CAN_FLY.add(ffPlayer);
				} catch(Exception ex) { }
			}
		}
		for(int i = CAN_FLY.size() - 1; i >= 0; i--) {
			FrequentFlyerPlayer ffPlayer = CAN_FLY.get(i);
			Player player = ffPlayer.getPlayer();
			if (player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack elytra = player.getInventory().getChestplate();
				if (elytra != null && elytra.getType().equals(Material.ELYTRA)) {
					ffPlayer.setElytra(elytra);
					if (player.isFlying() && !player.getGameMode().equals(GameMode.CREATIVE) && !player.getGameMode().equals(GameMode.SPECTATOR)) {
						ffPlayer.minus();
					}
				} else {
					if((elytra == null || !elytra.getType().equals(Material.ELYTRA)) && !player.getGameMode().equals(GameMode.CREATIVE) 
							&& !player.getGameMode().equals(GameMode.SPECTATOR)) {
						ffPlayer.setElytra(null);
						ffPlayer.setCanFly(false);
					}
				}
			} else {
				CAN_FLY.remove(ffPlayer);
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
			
			if(elytra != null && previousElytra != null && !elytra.toString().equalsIgnoreCase(previousElytra.toString())) {
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
				underLimit = level * 4 * 20;
				aboveLimit = level * 20;
				if(DamageUtils.getDamage(elytra.getItemMeta()) < 400) {
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
			this.canFly = canFly || player.getGameMode().equals(GameMode.CREATIVE) 
					|| player.getGameMode().equals(GameMode.SPECTATOR);
			if(this.canFly || !player.hasPermission("enchantmentsolution.enable-flight")) {
				player.setAllowFlight(this.canFly);
				if(player.isFlying() && !this.canFly) {
					player.setFlying(false);
				}
			}
		}

		public int getUnder() {
			return under;
		}
		
		public void minus() {
			if(player.getLocation().getY() >= 12000) {
				AdvancementUtils.awardCriteria(player, ESAdvancement.CRUISING_ALTITUDE, "elytra"); 
			}
			if(player.getLocation().getY() > 255) {
				above = above - 1;
				if(above <= 0) {
					double random = Math.random();
					double chance = (1.0D/(Enchantments.getLevel(elytra, Enchantment.DURABILITY)+1));
					int durabilityChange = 1;
					if(chance < random) {
						durabilityChange = 0;
					}
					DamageUtils.setDamage(elytra, (DamageUtils.getDamage(elytra.getItemMeta()) + durabilityChange));
					if(DamageUtils.getDamage(elytra.getItemMeta()) >= 400) {
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
					DamageUtils.setDamage(elytra, (DamageUtils.getDamage(elytra.getItemMeta()) + durabilityChange));
					if(DamageUtils.getDamage(elytra.getItemMeta()) >= 400) {
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
