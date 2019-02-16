package org.ctp.enchantmentsolution.listeners.abilities;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class MagicGuardListener implements Listener, Runnable{
	
	private static List<PotionEffectType> BAD_POTIONS = Arrays.asList(PotionEffectType.BLINDNESS, PotionEffectType.CONFUSION, PotionEffectType.HARM,
			PotionEffectType.HUNGER, PotionEffectType.POISON, PotionEffectType.SLOW, PotionEffectType.SLOW_DIGGING, PotionEffectType.UNLUCK,
			PotionEffectType.WEAKNESS, PotionEffectType.WITHER);
	
	@EventHandler
	public void onPotionSplash(PotionSplashEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.MAGIC_GUARD)) return;
		try {
			Field eventField = PotionSplashEvent.class.getDeclaredField("affectedEntities");
			eventField.setAccessible(true);
			Map<?, ?> affectedEntities = (Map<?, ?>) eventField.get(event);
			Iterator<?> iterator = affectedEntities.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<?, ?> entry = (Entry<?, ?>) iterator.next();
				if (entry.getKey() instanceof Player) {
					Player player = (Player) entry.getKey();
					ItemStack shield = player.getInventory().getItemInOffHand();
					if(shield.getType().equals(Material.SHIELD)) {
						if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
							iterator.remove();
							for(PotionEffect effect : event.getPotion().getEffects()) {
								if(!BAD_POTIONS.contains(effect.getType())) {
									player.addPotionEffect(effect);
								}
							}
						}
					}
				}
			}
			eventField.set(event, affectedEntities);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.MAGIC_GUARD)) return;
		if(event.getCause().equals(DamageCause.MAGIC) || event.getCause().equals(DamageCause.POISON)) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack shield = player.getInventory().getItemInOffHand();
				if(shield.getType().equals(Material.SHIELD)) {
					if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.MAGIC_GUARD)) return;
		if(event.getDamager() instanceof AreaEffectCloud) {
			if(event.getEntity() instanceof Player) {
				Player player = (Player) event.getEntity();
				ItemStack shield = player.getInventory().getItemInOffHand();
				if(shield.getType().equals(Material.SHIELD)) {
					if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		ItemMeta meta = item.getItemMeta();
		if(meta instanceof PotionMeta) {
			PotionMeta potionMeta = (PotionMeta) meta;
			Player player = event.getPlayer();
			ItemStack shield = player.getInventory().getItemInOffHand();
			if(shield.getType().equals(Material.SHIELD)) {
				if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
					potionMeta.removeCustomEffect(PotionEffectType.HARM);
					if(potionMeta.getBasePotionData().getType().equals(PotionType.INSTANT_DAMAGE)){
						potionMeta.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE));
					}
				}
			}
			item.setItemMeta(potionMeta);
			event.setItem(item);
		}
	}
	
	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.MAGIC_GUARD)) return;
		for(Player player : Bukkit.getOnlinePlayers()) {
			ItemStack shield = player.getInventory().getItemInOffHand();
			if(shield.getType().equals(Material.SHIELD)) {
				if(Enchantments.hasEnchantment(shield, DefaultEnchantments.MAGIC_GUARD)) {
					for(PotionEffect effect : player.getActivePotionEffects()) {
						if(BAD_POTIONS.contains(effect.getType())) {
							player.removePotionEffect(effect.getType());
						}
					}
				}
			}
		}
	}

}
