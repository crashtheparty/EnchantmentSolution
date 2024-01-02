package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.entity.DetonateCreeperEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagedIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.CreeperIgniteEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class DetonatorDamage extends CreeperIgniteEffect {

	public DetonatorDamage() {
		super(RegisterEnchantments.DETONATOR, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, true, 0, "(%level% - 1) * 5", false, true, new DamageCondition[] { new DamagedIsTypeCondition(false, new MobData("CREEPER")) });
	}

	@Override
	public CreeperResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		Creeper creeper = (Creeper) damaged;
		CreeperResult result = super.run(damager, damaged, items, event);

		int ticks = result.getTicks();
		int level = result.getLevel();
		if (level == 0) return null;
		DetonateCreeperEvent detonator = new DetonateCreeperEvent(creeper, level, ticks, damager);

		if (!detonator.isCancelled()) {
			if (damager instanceof Player) {
				Player player = (Player) damager;
				if (creeper.getMaxFuseTicks() > detonator.getDetonateTicks()) AdvancementUtils.awardCriteria(player, ESAdvancement.BLAST_OFF, "creeper");
			}
			creeper.setMaxFuseTicks(detonator.getDetonateTicks());
			if (result.isIgnite()) creeper.ignite();

			return new CreeperResult(detonator.getEnchantment().getLevel(), result.isIgnite(), detonator.getDetonateTicks());
		}

		return null;
	}
}
