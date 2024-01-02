package org.ctp.enchantmentsolution.interfaces.interact;

import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.interact.LassoInteractEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.HandPacketCondition;
import org.ctp.enchantmentsolution.interfaces.effects.interact.RespawnEntityEffect;
import org.ctp.enchantmentsolution.persistence.PersistenceUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.LassoMob;

public class IrenesLasso extends RespawnEntityEffect {

	public IrenesLasso() {
		super(RegisterEnchantments.IRENES_LASSO, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, new InteractCondition[] { new HandPacketCondition(false, EquipmentSlot.HAND) });
	}
	
	@Override
	public RespawnEntityResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		RespawnEntityResult result = super.run(player, items, event);
		if (result.getLevel() == 0 || result.getMob() == null) return null;
		
		LassoInteractEvent lasso = new LassoInteractEvent(player, result.getLevel(), result.getItem(), event.getClickedBlock(), event.getBlockFace(), result.getMob());
		if (!lasso.isCancelled()) {
			event.setCancelled(true);
			LassoMob fromLasso = lasso.getLassoMob();
			ItemStack item = fromLasso.getItem();
			Location loc = lasso.getBlock().getRelative(lasso.getFace()).getLocation().clone().add(0.5, 0, 0.5);
			if (loc.getBlock().isPassable()) {
				fromLasso.spawnEntity(loc);
				PersistenceUtils.removeAnimal(item, fromLasso.getEntityID());
				DamageUtils.damageItem(player, item, 1, 2);
				player.incrementStatistic(Statistic.USE_ITEM, item.getType());
				EnchantmentSolution.removeLassoMob(getEnchantment(), fromLasso);
				return new RespawnEntityResult(result.getLevel(), fromLasso, item);
			}
		}
		
		return null;
	}

}
