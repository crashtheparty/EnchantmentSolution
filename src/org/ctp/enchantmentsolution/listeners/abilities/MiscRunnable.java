package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.DrownedEntity;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.EntityAccuracy;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.IcarusDelay;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;

public class MiscRunnable implements Runnable{

	private static Map<UUID, Float> EXHAUSTION = new HashMap<UUID, Float>();
	private int run = 0;
	
	@Override
	public void run() {
		curseOfExhaustion();
		drowned();
		if(run == 0) {
			icarus();
		}
		magicGuard();
		sandVeil();
		run++;
		if(run / 20 > 0) run = 0;
	}
	
	private void curseOfExhaustion() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.CURSE_OF_EXHAUSTION)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(!EXHAUSTION.containsKey(player.getUniqueId())) {
				try {
					EXHAUSTION.put(player.getUniqueId(), AbilityUtils.getExhaustion(player));
				} catch(Exception ex) { }
			}
		}
		Iterator<Entry<UUID, Float>> iterator = EXHAUSTION.entrySet().iterator();
		while(iterator.hasNext()) {
			Entry<UUID, Float> entry = iterator.next();
			Player player = Bukkit.getPlayer(entry.getKey());
			if(player != null && player.isOnline()) {
				int exhaustionCurse = AbilityUtils.getExhaustionCurse(player);
				float change = entry.getValue() - AbilityUtils.getExhaustion(player);
				if (change > 0 && exhaustionCurse > 0) {
					player.setExhaustion(player.getExhaustion() + change * exhaustionCurse);
					if(exhaustionCurse > 2) {
						AdvancementUtils.awardCriteria(player, ESAdvancement.HIGH_METABOLISM, "exhaustion");
					}
				}
				EXHAUSTION.put(player.getUniqueId(), AbilityUtils.getExhaustion(player));
			} else {
				iterator.remove();
			}
		}
	}
	
	private void drowned() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.DROWNED)) return;
		List<DrownedEntity> entities = DrownedEntity.ENTITIES;
		Map<UUID, Integer> attackers = new HashMap<UUID, Integer>();
		for(int i = entities.size() - 1; i >= 0; i--) {
			DrownedEntity entity = entities.get(i);
			entity.inflictDamage();
			if(entity.getDamageTime() <= 0) {
				entities.remove(entity);
			} else {
				if(attackers.containsKey(entity.getAttackerEntity().getUniqueId())) {
					attackers.put(entity.getAttackerEntity().getUniqueId(), attackers.get(entity.getAttackerEntity().getUniqueId()) + 1);
					if(attackers.get(entity.getAttackerEntity().getUniqueId()) >= 3 && entity.getAttackerEntity() instanceof Player) {
						AdvancementUtils.awardCriteria(((Player) entity.getAttackerEntity()), ESAdvancement.SEVEN_POINT_EIGHT, "drowning");
					}
				} else {
					attackers.put(entity.getAttackerEntity().getUniqueId(), 1);
				}
			}
		}
	}
	
	private void icarus() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.ICARUS)) return;
		List<IcarusDelay> icarusDelay = IcarusDelay.getIcarusDelay();
		for(int i = icarusDelay.size() - 1; i >= 0; i--) {
			IcarusDelay icarus = icarusDelay.get(i);
			icarus.minusDelay();
			if(icarus.getDelay() <= 0) {
				icarusDelay.remove(icarus);
				icarus.getPlayer().getWorld().spawnParticle(Particle.FIREWORKS_SPARK, icarus.getPlayer().getLocation(), 250, 2, 2, 2);
				icarus.getPlayer().getWorld().playSound(icarus.getPlayer().getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
			}
		}
	}
	
	private void magicGuard() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.MAGIC_GUARD)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			ItemStack shield = player.getInventory().getItemInOffHand();
			if(shield.getType().equals(Material.SHIELD)) {
				if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
					for(PotionEffect effect : player.getActivePotionEffects()) {
						if(ItemUtils.getBadPotions().contains(effect.getType())) {
							player.removePotionEffect(effect.getType());
						}
					}
				}
			}
		}
	}
	
	private void sandVeil() {
		List<EntityAccuracy> entities = EntityAccuracy.getEntities();
		for(int i = entities.size() - 1; i >= 0; i--) {
			EntityAccuracy entity = entities.get(i);
			entity.minus();
			if(entity.getRun() <= 0) {
				entities.remove(entity);
			}
		}
	}
}
