package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class IronDefenseListener implements Listener{
	
	private static List<DamageCause> CONTACT_CAUSES = Arrays.asList(DamageCause.BLOCK_EXPLOSION, DamageCause.CONTACT, DamageCause.CUSTOM, DamageCause.ENTITY_ATTACK,
			DamageCause.ENTITY_EXPLOSION, DamageCause.ENTITY_SWEEP_ATTACK, DamageCause.LIGHTNING, DamageCause.PROJECTILE, DamageCause.THORNS);

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.IRON_DEFENSE)) return;
		if(!CONTACT_CAUSES.contains(event.getCause())) return;
		Entity attacked = event.getEntity();
		Entity attacker = event.getDamager();
		if(attacker instanceof AreaEffectCloud) return;
		if(attacked instanceof HumanEntity){
			HumanEntity player = (HumanEntity) attacked;
			ItemStack shield = player.getEquipment().getItemInOffHand();
			if(shield == null) return;
			if(player.isBlocking()) return;
			if(Enchantments.hasEnchantment(shield, DefaultEnchantments.IRON_DEFENSE)){
				int level = Enchantments.getLevel(shield, DefaultEnchantments.IRON_DEFENSE);
				double percentage = .1 + .05 * level;
				double damage = event.getDamage() * percentage;
				event.setDamage(event.getDamage() - damage);
				int unbreaking = Enchantments.getLevel(shield, Enchantment.DURABILITY);
				int num_breaks = 0;
				int shieldDamage = (int) damage;
				if(shieldDamage < damage) shieldDamage += 1;
				for(int i = 0; i < shieldDamage; i++) {
					double chance = (1.0D) / (unbreaking + 1.0D);
					double random = Math.random();
					if(chance > random) {
						num_breaks ++;
					}
				}
				if(num_breaks > 0) {
					DamageUtils.setDamage(shield, DamageUtils.getDamage(shield.getItemMeta()) + num_breaks);
					if(DamageUtils.getDamage(shield.getItemMeta()) > shield.getType().getMaxDurability()) {
						player.getEquipment().setItemInOffHand(new ItemStack(Material.AIR));
						player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
						return;
					}
				}
			}
		}
	}
}
