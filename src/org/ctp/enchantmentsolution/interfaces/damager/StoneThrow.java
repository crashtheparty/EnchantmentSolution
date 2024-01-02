package org.ctp.enchantmentsolution.interfaces.damager;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.StoneThrowEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagedIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.DamagerEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class StoneThrow extends DamagerEffect {

	public StoneThrow() {
		super(RegisterEnchantments.STONE_THROW, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, 1, "0", "roundUp(0.4 * %level% + 1.2)", false, false, false, true, new DamageCondition[] { new DamagedIsTypeCondition(false, new MobData("BAT"), new MobData("BEE"), new MobData("BLAZE"), new MobData("ENDER_DRAGON"), new MobData("GHAST"), new MobData("PARROT"), new MobData("PHANTOM"), new MobData("VEX"), new MobData("WITHER")) });
	}

	@Override
	public DamagerResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		DamagerResult result = super.run(damager, damaged, items, event);
		int level = result.getLevel();
		if (level == 0) return null;
		double damage = result.getDamage();

		StoneThrowEvent stoneThrow = new StoneThrowEvent((LivingEntity) damager, level, (LivingEntity) damaged, event.getDamage(), damage);
		Bukkit.getPluginManager().callEvent(stoneThrow);
		if (!stoneThrow.isCancelled()) {
			event.setDamage(stoneThrow.getNewDamage());

			if (damager instanceof Player && damaged instanceof Phantom) {
				Phantom phantom = (Phantom) damaged;
				if (phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() == phantom.getHealth() && phantom.getHealth() <= damage) AdvancementUtils.awardCriteria((Player) damager, ESAdvancement.JUST_DIE_ALREADY, "phantom");
			}
			if (damager instanceof Player && damaged instanceof EnderDragon) AdvancementUtils.awardCriteria((Player) damager, ESAdvancement.UNDERKILL, "dragon");
		}
		return new DamagerResult(stoneThrow.getEnchantment().getLevel(), stoneThrow.getDamage());
	}

}
