package org.ctp.enchantmentsolution.interfaces.damager;

import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.crashapi.entity.MobInterface;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.damage.LassoDamageEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DamageCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagedHasInterfaceCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagedIsOwnedCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.damage.DamagerIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.damage.LassoEntityEffect;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.LassoMob;

public class IrenesLassoDamage extends LassoEntityEffect {

	public IrenesLassoDamage() {
		super(RegisterEnchantments.IRENES_LASSO, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, new DamageCondition[] { new DamagedIsOwnedCondition(false, true), new DamagerIsTypeCondition(false, new MobData("PLAYER")), new DamagedHasInterfaceCondition(false, new MobInterface("Animals"))});
	}
	
	@Override
	public LassoEntityResult run(Entity damager, Entity damaged, ItemStack[] items, EntityDamageByEntityEvent event) {
		LassoEntityResult result = super.run(damager, damaged, items, event);
		if (result == null) return null;
		ItemStack useItem = result.getItem();
		
		event.setCancelled(true);
		Creature animals = (Creature) damaged;
		LassoDamageEvent lasso = new LassoDamageEvent(animals, result.getLevel(), (Player) damager);
		Bukkit.getPluginManager().callEvent(lasso);
		if (!lasso.isCancelled()) {
			if (damaged instanceof Tameable) {
				String type = damaged.getType().name().toLowerCase(Locale.ROOT);
				AdvancementUtils.awardCriteria((Player) damager, ESAdvancement.THORGY, type);
				AdvancementUtils.awardCriteria((Player) damager, ESAdvancement.FREE_PETS, type);
			}
			McMMOHandler.customName(damaged);
			LassoMob mob = new LassoMob(damaged, useItem, getEnchantment());
			EnchantmentSolution.addLassoMob(getEnchantment(), mob);
			damaged.remove();
			return new LassoEntityResult(result.getLevel(), damaged, useItem);
		}
		return null;
	}
}
