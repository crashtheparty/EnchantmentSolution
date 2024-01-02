package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Collection;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobInterface;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.entity.HusbandryEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KilledHasInterfaceCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerExistsCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.SpawnOnDeathEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.AnimalNBT;

public class Husbandry extends SpawnOnDeathEffect {

	public Husbandry() {
		super(RegisterEnchantments.HUSBANDRY, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.HIGHEST, 0, "0.05 * (1 + %level%)", false, true, new DeathCondition[] { new KillerExistsCondition(false), new KilledHasInterfaceCondition(false, new MobInterface("Ageable")) });
	}

	@Override
	public DeathResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		DeathResult result = super.run(killer, killed, items, drops, event);
		if (result.getLevel() == 0) return null;

		if (result.getChance() > Math.random()) {
			HusbandryEvent husbandry = new HusbandryEvent(killed, (LivingEntity) killer, killed.getLocation(), result.getLevel());
			Bukkit.getPluginManager().callEvent(husbandry);
			if (!husbandry.isCancelled()) {
				Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
					Entity e = AnimalNBT.setHusbandry((Creature) killed);
					if (e != null && killer instanceof Player) AdvancementUtils.awardCriteria((Player) killer, ESAdvancement.WILDLIFE_CONSERVATION, e.getType().name().toLowerCase(Locale.ROOT));
				}, 1l);

				return new DeathResult(result.getLevel(), 1);
			}
		}
		return null;
	}

}
