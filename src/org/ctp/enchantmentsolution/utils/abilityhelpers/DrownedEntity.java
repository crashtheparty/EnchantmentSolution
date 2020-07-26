package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.nms.DamageEvent;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.events.damage.DrownDamageEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class DrownedEntity {

	private int damageTime;
	private UUID hurtEntity, attackerEntity;
	private int ticksLastDamage = 1;
	private int level;

	public DrownedEntity(LivingEntity hurtEntity, LivingEntity attackerEntity, int ticks, int level) {
		this.hurtEntity = hurtEntity.getUniqueId();
		this.attackerEntity = attackerEntity.getUniqueId();
		damageTime = ticks;
		setLevel(level);
	}

	public LivingEntity getHurtEntity() {
		return (LivingEntity) Bukkit.getEntity(hurtEntity);
	}

	public LivingEntity getAttackerEntity() {
		return (LivingEntity) Bukkit.getEntity(attackerEntity);
	}

	public void inflictDamage() {
		LivingEntity hurtEntity = getHurtEntity();
		if (hurtEntity instanceof Player) {
			if (!((Player) hurtEntity).isOnline() || hurtEntity == null) return;
		} else if (hurtEntity == null || hurtEntity.isDead()) {
			damageTime = 0;
			return;
		}
		hurtEntity.setRemainingAir(0);
		if (ticksLastDamage >= 20) {
			int level = 0;
			if (hurtEntity instanceof HumanEntity) {
				HumanEntity hEntity = (HumanEntity) hurtEntity;
				ItemStack helmet = hEntity.getInventory().getHelmet();

				if (helmet != null) level = EnchantmentUtils.getLevel(helmet, Enchantment.OXYGEN);
			}
			double chance = level / ((double) level + 1);
			double random = Math.random();
			DrownDamageEvent event = new DrownDamageEvent(hurtEntity, this.level, 2, chance <= random ? 2 : 0);
			Bukkit.getPluginManager().callEvent(event);
			if (!event.isCancelled() && event.getNewDamage() > 0) {
				DamageEvent.damageEntity(hurtEntity, "drown", (float) event.getNewDamage());
				if (hurtEntity.isDead() && hurtEntity instanceof Player && getAttackerEntity() instanceof Player) AdvancementUtils.awardCriteria((Player) getAttackerEntity(), ESAdvancement.HEX_BAG, "player");
			}
			ticksLastDamage = 1;
		} else
			ticksLastDamage++;
		damageTime--;
	}

	public int getDamageTime() {
		return damageTime;
	}

	public void setDamageTime(int time) {
		damageTime = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
