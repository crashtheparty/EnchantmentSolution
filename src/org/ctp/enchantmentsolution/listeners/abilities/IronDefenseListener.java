package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Statistic;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class IronDefenseListener extends EnchantmentListener{
	
	private static List<DamageCause> CONTACT_CAUSES = Arrays.asList(DamageCause.BLOCK_EXPLOSION, DamageCause.CONTACT, DamageCause.CUSTOM, DamageCause.ENTITY_ATTACK,
			DamageCause.ENTITY_EXPLOSION, DamageCause.ENTITY_SWEEP_ATTACK, DamageCause.LIGHTNING, DamageCause.PROJECTILE, DamageCause.THORNS);

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.IRON_DEFENSE, event)) return;
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
				int shieldDamage = (int) damage;
				if(shieldDamage < damage) shieldDamage += 1;
				super.damageItem(player, shield, shieldDamage);
				if(player instanceof Player) {
					((Player) player).incrementStatistic(Statistic.DAMAGE_BLOCKED_BY_SHIELD, (int) (damage * 10));
				}
			}
		}
	}
}
