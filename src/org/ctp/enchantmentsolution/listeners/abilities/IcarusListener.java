package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class IcarusListener implements Listener, Runnable{

	public static List<IcarusDelay> ICARUS_DELAY = new ArrayList<IcarusDelay>();
	
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.FREQUENT_FLYER)) return;
		Player player = event.getPlayer();
		ItemStack chestplate = player.getInventory().getChestplate();
		if(chestplate != null && chestplate.getType().equals(Material.ELYTRA) && player.isGliding() && 
				Enchantments.hasEnchantment(chestplate, DefaultEnchantments.FREQUENT_FLYER)) {
			int level = Enchantments.getLevel(player.getInventory().getChestplate(), DefaultEnchantments.FREQUENT_FLYER);
			double additional = Math.log((2 * level + 8) / 5) + 1.5;
			if(player.getLocation().getPitch() < -10) {
				for(IcarusDelay icarus : ICARUS_DELAY) {
					if(icarus.getPlayer().equals(player)) {
						return;
						
					}
				}
				Vector v = player.getVelocity().clone();
				v.add(new Vector(0, additional, 0));
				v.multiply(new Vector(additional / 2, 1, additional / 2));
				player.setVelocity(v);
				int unbreaking = Enchantments.getLevel(chestplate, Enchantment.DURABILITY);
				int num_breaks = 0;
				int chances = level * 5;
				for(int i = 0; i < chances; i++) {
					double chance = (1.0D) / (unbreaking + 1.0D);
					double random = Math.random();
					if(chance > random) {
						num_breaks ++;
					}
				}
				if(num_breaks > 0) {
					if(DamageUtils.getDamage(chestplate.getItemMeta()) + num_breaks > chestplate.getType().getMaxDurability()) {
						return;
					}
					DamageUtils.setDamage(chestplate, DamageUtils.getDamage(chestplate.getItemMeta()) + num_breaks);
				}
				ICARUS_DELAY.add(new IcarusDelay(player));
			}
		}
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.FREQUENT_FLYER)) return;
		for(int i = ICARUS_DELAY.size() - 1; i >= 0; i--) {
			IcarusDelay icarus = ICARUS_DELAY.get(i);
			icarus.minusDelay();
			if(icarus.getDelay() <= 0) {
				ICARUS_DELAY.remove(icarus);
			}
		}
	}
	

	
	protected class IcarusDelay{
		
		private Player player;
		private int delay;
		
		public IcarusDelay(Player player) {
			this.player = player;
			this.delay = 30;
		}

		public Player getPlayer() {
			return player;
		}

		public int getDelay() {
			return delay;
		}

		public void minusDelay() {
			delay --;
		}
		
	}
	
}
