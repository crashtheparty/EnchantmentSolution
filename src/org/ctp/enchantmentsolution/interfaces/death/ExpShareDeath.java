package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.ExpShareType;
import org.ctp.enchantmentsolution.events.entity.ExpShareEntityEvent;
import org.ctp.enchantmentsolution.events.player.ExpSharePlayerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KilledIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerExistsCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.ModifyExperienceEffect;

public class ExpShareDeath extends ModifyExperienceEffect {

	public ExpShareDeath() {
		super(RegisterEnchantments.EXP_SHARE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, 0, "1", 0, "1", false, false, false, false, 0.5, true, new DeathCondition[] { new KillerExistsCondition(false), new KilledIsTypeCondition(true, new MobData("PLAYER")) });
	}

	@Override
	public ModifyExperienceResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		ModifyExperienceResult result = super.run(killer, killed, items, drops, event);
		if (result.getLevel() == 0) return null;
		int exp = result.getExp();

		if (exp > 0) {
			if (killer instanceof Player) {
				ExpSharePlayerEvent experienceEvent = new ExpSharePlayerEvent((Player) killer, result.getLevel(), ExpShareType.MOB, event.getDroppedExp(), exp);

				Bukkit.getPluginManager().callEvent(experienceEvent);

				if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) {
					event.setDroppedExp(experienceEvent.getNewExp());
					return new ModifyExperienceResult(result.getLevel(), experienceEvent.getNewExp());
				}
			} else {
				ExpShareEntityEvent experienceEvent = new ExpShareEntityEvent((LivingEntity) killer, result.getLevel(), ExpShareType.MOB, event.getDroppedExp(), exp);

				Bukkit.getPluginManager().callEvent(experienceEvent);

				if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) {
					event.setDroppedExp(experienceEvent.getNewExp());
					return new ModifyExperienceResult(result.getLevel(), experienceEvent.getNewExp());
				}
			}
		} else {}
		return null;
	}

}
