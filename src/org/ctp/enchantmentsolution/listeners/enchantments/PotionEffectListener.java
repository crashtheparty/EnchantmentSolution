package org.ctp.enchantmentsolution.listeners.enchantments;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.EntityPotionEffectEvent.Action;
import org.bukkit.event.entity.EntityPotionEffectEvent.Cause;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.potion.MagicGuardPotionEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.ESArrays;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class PotionEffectListener extends Enchantmentable {

	@EventHandler
	public void onEntityPotionEffect(EntityPotionEffectEvent event) {
		if (!canRun(RegisterEnchantments.MAGIC_GUARD, event)) return;
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (isDisabled(player, RegisterEnchantments.MAGIC_GUARD)) return;
			ItemStack shield = player.getInventory().getItemInOffHand();
			if (EnchantmentUtils.hasEnchantment(shield, RegisterEnchantments.MAGIC_GUARD) && (event.getAction() == Action.ADDED || event.getAction() == Action.CHANGED) && ESArrays.getBadPotions().contains(event.getModifiedType())) {
				MagicGuardPotionEvent magicGuard = new MagicGuardPotionEvent(player, event.getModifiedType());
				Bukkit.getPluginManager().callEvent(magicGuard);

				if (!magicGuard.isCancelled()) {
					event.setCancelled(true);
					if (event.getCause() == Cause.FOOD) AdvancementUtils.awardCriteria(player, ESAdvancement.THAT_FOOD_IS_FINE, "shield");
				}
			}
		}
	}
}