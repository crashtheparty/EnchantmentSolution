package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.Lootable;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.EnchantmentLevel;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class PillageListener implements Listener{
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		if(EnchantmentSolution.getBukkitVersion().getVersionNumber() > 3) {
			if(!DefaultEnchantments.isEnabled(DefaultEnchantments.STONE_THROW)) return;
			if(event.getEntity().getKiller() != null) {
				Player player = event.getEntity().getKiller();
				ItemStack item = player.getInventory().getItemInOffHand();
				if(item == null || !Enchantments.hasEnchantment(item, DefaultEnchantments.STONE_THROW)) {
					item = player.getInventory().getItemInMainHand();
					if(Enchantments.hasEnchantment(item, Enchantment.LOOT_BONUS_MOBS)) return;
				}
				if(Enchantments.hasEnchantment(item, DefaultEnchantments.STONE_THROW)) {
					event.getDrops().clear();
					int level = Enchantments.getLevel(item, DefaultEnchantments.STONE_THROW);
					List<EnchantmentLevel> levels = Enchantments.getEnchantmentLevels(item);
					Enchantments.addEnchantmentToItem(item, DefaultEnchantments.getCustomEnchantment(Enchantment.LOOT_BONUS_MOBS), level);
					LootContext.Builder contextBuilder = new LootContext.Builder(event.getEntity().getLocation());
					contextBuilder.killer(player);
					contextBuilder.lootedEntity(event.getEntity());
					contextBuilder.lootingModifier(level);
					LootContext context = contextBuilder.build();
					Collection<ItemStack> items = ((Lootable)event.getEntity()).getLootTable().populateLoot(new Random(), context);
					event.getDrops().addAll(items);
					Enchantments.removeAllEnchantments(item);
					Enchantments.addEnchantmentsToItem(item, levels);
				}
			}
		}
	}
}
