package org.ctp.enchantmentsolution.interfaces.death;

import java.util.Collection;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.entity.MobData;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.drops.TransmutationEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.DeathCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KilledIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.death.KillerExistsCondition;
import org.ctp.enchantmentsolution.interfaces.effects.death.MatOption;
import org.ctp.enchantmentsolution.interfaces.effects.death.ModifyDropsEffect;
import org.ctp.enchantmentsolution.interfaces.effects.death.ModifyLoot;
import org.ctp.enchantmentsolution.interfaces.effects.death.ModifyLoot.LootResult;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Transmutation extends ModifyDropsEffect {

	private static final ModifyLoot LOOT = new ModifyLoot(new MatData[0], new MatData[] { new MatData("SALMON"), new MatData("COD"), new MatData("TROPICAL_FISH"), new MatData("PUFFERFISH"), new MatData("KELP"), new MatData("TRIDENT"), new MatData("SCUTE"), new MatData("TURTLE_EGG"), new MatData("INK_SAC"), new MatData("PRISMARINE_SHARD"), new MatData("PRISMARINE_CRYSTALS"), new MatData("NAUTILUS_SHELL"), new MatData("HEART_OF_THE_SEA"), new MatData("BRAIN_CORAL"), new MatData("BUBBLE_CORAL"), new MatData("FIRE_CORAL"), new MatData("HORN_CORAL"), new MatData("TUBE_CORAL"), new MatData("BRAIN_CORAL_FAN"), new MatData("BUBBLE_CORAL_FAN"), new MatData("FIRE_CORAL_FAN"), new MatData("HORN_CORAL_FAN"), new MatData("TUBE_CORAL_FAN"), new MatData("BRAIN_CORAL_BLOCK"), new MatData("BUBBLE_CORAL_BLOCK"), new MatData("FIRE_CORAL_BLOCK"), new MatData("HORN_CORAL_BLOCK"), new MatData("TUBE_CORAL_BLOCK"), new MatData("SPONGE"), new MatData("SEA_PICKLE"), new MatData("NETHER_STAR") }, new MatOption[] { new MatOption("SALMON", 1200, 1, 2), new MatOption("COD", 1200, 1, 2), new MatOption("TROPICAL_FISH", 750, 1, 1), new MatOption("PUFFERFISH", 400, 1, 1), new MatOption("KELP", 400, 1, 4), new MatOption("TRIDENT", 8, 1, 1), new MatOption("SCUTE", 300, 1, 2), new MatOption("TURTLE_EGG", 50, 1, 1), new MatOption("INK_SAC", 1000, 1, 4), new MatOption("PRISMARINE_SHARD", 750, 1, 3), new MatOption("PRISMARINE_CRYSTALS", 500, 1, 3), new MatOption("NAUTILUS_SHELL", 140, 1, 1), new MatOption("HEART_OF_THE_SEA", 2, 1, 1), new MatOption("BRAIN_CORAL", 100, 1, 4), new MatOption("BUBBLE_CORAL", 100, 1, 4), new MatOption("FIRE_CORAL", 100, 1, 4), new MatOption("HORN_CORAL", 100, 1, 4), new MatOption("TUBE_CORAL", 100, 1, 4), new MatOption("BRAIN_CORAL_FAN", 100, 1, 4), new MatOption("BUBBLE_CORAL_FAN", 100, 1, 4), new MatOption("FIRE_CORAL_FAN", 100, 1, 4), new MatOption("HORN_CORAL_FAN", 100, 1, 4), new MatOption("TUBE_CORAL_FAN", 100, 1, 4), new MatOption("BRAIN_CORAL_BLOCK", 300, 1, 1), new MatOption("BUBBLE_CORAL_BLOCK", 300, 1, 1), new MatOption("FIRE_CORAL_BLOCK", 300, 1, 1), new MatOption("HORN_CORAL_BLOCK", 300, 1, 1), new MatOption("TUBE_CORAL_BLOCK", 300, 1, 1), new MatOption("SPONGE", 400, 1, 1), new MatOption("SEA_PICKLE", 400, 1, 4) });

	public Transmutation() {
		super(RegisterEnchantments.TRANSMUTATION, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, LOOT, false, "", new DeathCondition[] { new KillerExistsCondition(false), new KilledIsTypeCondition(true, new MobData("PLAYER")) });
	}

	@Override
	public ModifyDropsResult run(Entity killer, Entity killed, ItemStack[] items, Collection<ItemStack> drops, EntityDeathEvent event) {
		ModifyDropsResult result = super.run(killer, killed, items, drops, event);
		if (result.getLevel() == 0) return null;

		boolean changedLoot = result.isChanged();
		Collection<ItemStack> newDrops = result.getNewDrops();
		if (killed instanceof Wither) {
			changedLoot = true;
			for(int i = 0; i < 64; i++) {
				LootResult res = getLoot().getLoot(isEnchant(), getLootKind());
				if (res.isChanged()) newDrops.add(res.getModifiedItem());
			}
		}

		if (!changedLoot) {
			if (killer instanceof Player) AdvancementUtils.awardCriteria((Player) killer, ESAdvancement.POSEIDONS_DAY_OFF, "day_off");
			return null;
		}
		if (killer instanceof Player) {
			Player player = (Player) killer;
			for(ItemStack drop: newDrops)
				switch (drop.getType().name()) {
					case "COD":
					case "SALMON":
					case "PUFFERFISH":
					case "TROPICAL_FISH":
						AdvancementUtils.awardCriteria(player, ESAdvancement.FISHY_BUSINESS, drop.getType().name().toLowerCase(Locale.ROOT));
						break;
					case "TRIDENT":
						AdvancementUtils.awardCriteria(player, ESAdvancement.POSEIDON_REBORN, "trident");
						break;
					default:
						break;
				}
		}

		TransmutationEvent transmutation = new TransmutationEvent((LivingEntity) killer, (LivingEntity) killed, drops, event.getDrops());
		Bukkit.getPluginManager().callEvent(transmutation);

		if (!transmutation.isCancelled()) {
			event.getDrops().clear();
			for(ItemStack drop: transmutation.getDrops())
				event.getDrops().add(drop);
			return new ModifyDropsResult(result.getLevel(), transmutation.getDrops(), result.getOriginalDrops(), changedLoot);
		}
		return null;
	}

}
