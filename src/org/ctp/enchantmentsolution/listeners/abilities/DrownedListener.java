package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Drowned;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.entity.WaterMob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.DamageEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class DrownedListener extends EnchantmentListener implements Runnable{
	
	private static List<DrownedEntity> ENTITIES = new ArrayList<DrownedEntity>();
	
	private static Map<UUID, ItemStack> ENTITY_IDS = new HashMap<UUID, ItemStack>();
	
	@EventHandler
	public void onProjectileLaunch(ProjectileLaunchEvent event){
		if(!canRun(DefaultEnchantments.DROWNED, event)) return;
		Projectile proj = event.getEntity();
		if(proj instanceof Trident){
			Trident trident = (Trident) proj;
			if(trident.getShooter() instanceof Player){
				Player player = (Player) trident.getShooter();
				ItemStack tridentItem = player.getInventory().getItemInMainHand();
				if(tridentItem != null) {
					ENTITY_IDS.put(trident.getUniqueId(), tridentItem);
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(!canRun(DefaultEnchantments.DROWNED, event)) return;
		if(event.getDamager() instanceof Trident) {
			Trident trident = (Trident) event.getDamager();
			HumanEntity human = (HumanEntity) trident.getShooter();
			if(ENTITY_IDS.containsKey(trident.getUniqueId())) {
				ItemStack item = ENTITY_IDS.get(trident.getUniqueId());
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.DROWNED)) {
					int level = Enchantments.getLevel(item, DefaultEnchantments.DROWNED);
					int ticks = (1 + level * 3) * 20;
					if(event.getEntity() instanceof LivingEntity) {
						LivingEntity entity = (LivingEntity) event.getEntity();
						if(!(entity instanceof WaterMob) && !(entity instanceof Drowned) && !(entity instanceof Guardian)) {
							addNewDrowned(entity, human, ticks);
						}
					}
				}
			}
		} else {
			if(event.getDamager() instanceof HumanEntity) {
				HumanEntity human = (HumanEntity) event.getDamager();
				ItemStack item = human.getInventory().getItemInMainHand();
				if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.DROWNED)){
					int level = Enchantments.getLevel(item, DefaultEnchantments.DROWNED);
					int ticks = (1 + level * 3) * 20;
					if(event.getEntity() instanceof LivingEntity) {
						LivingEntity entity = (LivingEntity) event.getEntity();
						if(!(entity instanceof WaterMob) && !(entity instanceof Drowned) && !(entity instanceof Guardian)) {
							addNewDrowned(entity, human, ticks);
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if(!canRun(DefaultEnchantments.DROWNED, event)) return;
		if(event.getHitEntity() != null) return;
		if(ENTITY_IDS.containsKey(event.getEntity().getUniqueId())) {
			ENTITY_IDS.remove(event.getEntity().getUniqueId());
		}
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(!canRun(DefaultEnchantments.DROWNED, event)) return;
		for(int i = ENTITIES.size() - 1; i >= 0; i--) {
			DrownedEntity entity = ENTITIES.get(i);
			if(entity.getHurtEntity().equals(event.getEntity())) {
				ENTITIES.remove(entity);
				break;
			}
		}
	}
	
	private void addNewDrowned(LivingEntity hurtEntity, LivingEntity attackerEntity, int ticks) {
		for(int i = ENTITIES.size() - 1; i >= 0; i--) {
			DrownedEntity drowned = ENTITIES.get(i);
			if(drowned.getHurtEntity().getUniqueId().equals(hurtEntity.getUniqueId())) {
				if(drowned.getDamageTime() < ticks) {
					drowned.setDamageTime(ticks);
				}
				return;
			}
		}
		ENTITIES.add(new DrownedEntity(hurtEntity, attackerEntity, ticks));
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.DROWNED)) return;
		Map<UUID, Integer> attackers = new HashMap<UUID, Integer>();
		for(int i = ENTITIES.size() - 1; i >= 0; i--) {
			DrownedEntity entity = ENTITIES.get(i);
			entity.inflictDamage();
			if(entity.getDamageTime() <= 0) {
				ENTITIES.remove(entity);
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
	
	protected class DrownedEntity {
		
		private int damageTime;
		private UUID hurtEntity, attackerEntity;
		private int ticksLastDamage = 1;
		
		public DrownedEntity(LivingEntity hurtEntity, LivingEntity attackerEntity, int ticks) {
			this.hurtEntity = hurtEntity.getUniqueId();
			this.attackerEntity = attackerEntity.getUniqueId();
			this.damageTime = ticks;
		}
		
		public LivingEntity getHurtEntity() {
			return (LivingEntity) Bukkit.getEntity(this.hurtEntity);
		}
		
		public LivingEntity getAttackerEntity() {
			return (LivingEntity) Bukkit.getEntity(this.attackerEntity);
		}
		
		public void inflictDamage() {
			LivingEntity hurtEntity = getHurtEntity();
			if(hurtEntity == null) return;
			hurtEntity.setRemainingAir(0);
			if(ticksLastDamage >= 20) {
				int level = 0;
				if(hurtEntity instanceof HumanEntity) {
					HumanEntity hEntity = (HumanEntity) hurtEntity;
					ItemStack helmet = hEntity.getInventory().getHelmet();
					
					if(helmet != null) {
						level = Enchantments.getLevel(helmet, Enchantment.OXYGEN);
					}
				}
				double chance = (double) level / ((double) level + 1);
				double random = Math.random();
				if(chance <= random) {
					DamageEvent.damageEntity(hurtEntity, "drown", 2);
				}
				if(hurtEntity.isDead() && hurtEntity instanceof Player && getAttackerEntity() instanceof Player) {
					AdvancementUtils.awardCriteria(((Player) getAttackerEntity()), ESAdvancement.HEX_BAG, "player");
				}
				ticksLastDamage = 1;
			}else {
				ticksLastDamage++;
			}
			damageTime --;
		}
		
		public int getDamageTime() {
			return damageTime;
		}
		
		public void setDamageTime(int time) {
			damageTime = time;
		}
		
	}

}
