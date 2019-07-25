package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.DamageEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class DrownedEntity {
	
	public static List<DrownedEntity> ENTITIES = new ArrayList<DrownedEntity>();
	private int damageTime;
	private UUID hurtEntity, attackerEntity;
	private int ticksLastDamage = 1;
	
	private DrownedEntity(LivingEntity hurtEntity, LivingEntity attackerEntity, int ticks) {
		this.hurtEntity = hurtEntity.getUniqueId();
		this.attackerEntity = attackerEntity.getUniqueId();
		this.damageTime = ticks;
	}
	
	public static void addNewDrowned(LivingEntity entity, HumanEntity human, int ticks) {
		for(int i = ENTITIES.size() - 1; i >= 0; i--) {
			DrownedEntity drowned = ENTITIES.get(i);
			if(drowned.getHurtEntity().getUniqueId().equals(entity.getUniqueId())) {
				if(drowned.getDamageTime() < ticks) {
					drowned.setDamageTime(ticks);
				}
				return;
			}
		}
		ENTITIES.add(new DrownedEntity(entity, human, ticks));
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
			double chance = level / ((double) level + 1);
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
