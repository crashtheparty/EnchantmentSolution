package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class IcarusListener extends EnchantmentListener implements Runnable{

	public static List<IcarusDelay> ICARUS_DELAY = new ArrayList<IcarusDelay>();
	
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent event) {
		if(!canRun(DefaultEnchantments.ICARUS, event)) return;
		Player player = event.getPlayer();
		ItemStack chestplate = player.getInventory().getChestplate();
		if(chestplate != null && chestplate.getType().equals(Material.ELYTRA) && player.isGliding() && 
				Enchantments.hasEnchantment(chestplate, DefaultEnchantments.ICARUS)) {
			int level = Enchantments.getLevel(player.getInventory().getChestplate(), DefaultEnchantments.ICARUS);
			double additional = Math.log((2 * level + 8) / 5) + 1.5;
			if(player.getLocation().getPitch() < -10) {
				for(IcarusDelay icarus : ICARUS_DELAY) {
					if(icarus.getPlayer().equals(player)) {
						return;
					}
				}
				int num_breaks = 0;
				if(!(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))) {
					int unbreaking = Enchantments.getLevel(chestplate, Enchantment.DURABILITY);
					int chances = level * 5;
					for(int i = 0; i < chances; i++) {
						double chance = (1.0D) / (unbreaking + 1.0D);
						double random = Math.random();
						if(chance > random) {
							num_breaks ++;
						}
					}
				}
				if(num_breaks > 0) {
					if(DamageUtils.getDamage(chestplate.getItemMeta()) + num_breaks > chestplate.getType().getMaxDurability()) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.TOO_CLOSE, "failure"); 
						player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation(), 5, 2, 2, 2);
						return;
					}
					DamageUtils.setDamage(chestplate, DamageUtils.getDamage(chestplate.getItemMeta()) + num_breaks);
				}
				Vector v = player.getVelocity().clone();
				v.add(new Vector(0, additional, 0));
				v.multiply(new Vector(additional / 2, 1, additional / 2));
				player.setVelocity(v);
				player.getWorld().spawnParticle(Particle.CLOUD, player.getLocation(), 250, 2, 2, 2);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
				ICARUS_DELAY.add(new IcarusDelay(player));
			}
		}
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.ICARUS)) return;
		for(int i = ICARUS_DELAY.size() - 1; i >= 0; i--) {
			IcarusDelay icarus = ICARUS_DELAY.get(i);
			icarus.minusDelay();
			if(icarus.getDelay() <= 0) {
				ICARUS_DELAY.remove(icarus);
				icarus.getPlayer().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, icarus.getPlayer().getLocation(), 250, 2, 2, 2);
				icarus.getPlayer().getWorld().playSound(icarus.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
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
