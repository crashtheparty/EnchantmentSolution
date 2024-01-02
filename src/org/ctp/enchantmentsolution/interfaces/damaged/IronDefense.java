package org.ctp.enchantmentsolution.interfaces.damaged;

import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.IronDefenseEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagedBlockingCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagerIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagedEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class IronDefense extends DamagedEffect {

	public IronDefense() {
		super(RegisterEnchantments.IRON_DEFENSE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.DEFENDED, EventPriority.HIGHEST, 0, 1, "0", "1 - (0.10 + 0.05 * %level%)", false, false, false, true, new DamageCondition[] { new DamagedBlockingCondition(true), new DamagerIsTypeCondition(true, new MobData("AREA_EFFECT_CLOUD")) });
	}

	@Override
	public DamagedResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagedResult result = super.run(damager, damaged, items, event);
		double damage = result.getDamage();
		int level = result.getLevel();
		if (level == 0) return null;
		int shieldDamage = (int) damage;
		if (shieldDamage < damage) shieldDamage += 1;

		IronDefenseEvent ironDefense = new IronDefenseEvent((LivingEntity) damaged, level, event.getDamage(), damage, items[0], shieldDamage);
		Bukkit.getPluginManager().callEvent(ironDefense);

		if (!ironDefense.isCancelled()) {
			event.setDamage(ironDefense.getNewDamage());
			if (damaged instanceof Player) {
				Player player = (Player) damaged;
				DamageUtils.damageItem(player, ironDefense.getShield(), ironDefense.getShieldDamage());

				if ((int) (ironDefense.getNewDamage() * 10) > 0) player.incrementStatistic(Statistic.DAMAGE_BLOCKED_BY_SHIELD, (int) (ironDefense.getNewDamage() * 10));
				if (player.getHealth() <= ironDefense.getDamage() && player.getHealth() > ironDefense.getNewDamage()) AdvancementUtils.awardCriteria(player, ESAdvancement.IRON_MAN, "blocked");
			}
			return new DamagedResult(ironDefense.getEnchantment().getLevel(), damage);
		}
		return null;
	}
}
