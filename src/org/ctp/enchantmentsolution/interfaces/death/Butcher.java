package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.drops.ButcherEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.DropTypeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerExistsCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.MultiplyDropsEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Butcher extends MultiplyDropsEffect {

	public Butcher() {
		super(RegisterEnchantments.BUTCHER, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, new DropTypeCondition(false, new MatData[] { new MatData("BEEF"), new MatData("COOKED_BEEF"), new MatData("CHICKEN"), new MatData("COOKED_CHICKEN"), new MatData("COD"), new MatData("COOKED_COD"), new MatData("MUTTON"), new MatData("COOKED_MUTTON"), new MatData("PORKCHOP"), new MatData("COOKED_PORKCHOP"), new MatData("RABBIT"), new MatData("COOKED_RABBIT"), new MatData("SALMON"), new MatData("COOKED_SALMON") }), 0, "0", "0", "%amount% * ((%random% * 2 * %level%) - 1)", false, false, false, true, new DeathCondition[] {new KillerExistsCondition(false)});
	}

	@Override
	public MultiplyDropsResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		MultiplyDropsResult result = super.run(killer, killed, items, drops, event);
		int level = result.getLevel();
		if (level == 0) return null;
		Collection<ItemStack> populated = result.getPopulated();
		Collection<ItemStack> original = result.getOriginal();
		if (result.isModified()) {
			ButcherEvent butcher = new ButcherEvent((LivingEntity) killer, level, populated, original);

			Bukkit.getPluginManager().callEvent(butcher);

			if (!butcher.isCancelled()) {
				drops.clear();
				for(ItemStack drop: populated)
					drops.add(drop);
				for(ItemStack drop: original)
					drops.add(drop);
				for(ItemStack drop: drops)
					if (drop.getType().name().contains("COOKED_") && killer instanceof Player) AdvancementUtils.awardCriteria((Player) killer, ESAdvancement.MEAT_READY_TO_EAT, "beef");
				return new MultiplyDropsResult(level, populated, original, result.isModified());
			}
		}
		return null;
	}
}
