package org.ctp.enchantmentsolution.listeners.advancements;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class AdvancementPlayerEvent implements Listener{
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
		Player player = event.getPlayer();
		if(player.getWorld().getTime() <= 12540 || player.getWorld().getTime() >= 23459) {
			ItemStack helmet = player.getInventory().getHelmet();
			if(helmet != null && Enchantments.hasEnchantment(helmet, DefaultEnchantments.UNREST)) {
				AdvancementUtils.awardCriteria(player, ESAdvancement.I_AINT_AFRAID_OF_NO_GHOSTS, "unrest", 1);
			}
		}
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(player != null) {
			if(Enchantments.hasEnchantment(event.getItemDrop().getItemStack(), DefaultEnchantments.EXP_SHARE)) {
				event.getItemDrop().setMetadata("exp_share", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		Entity entity = event.getEntity();
		if(entity instanceof Player) {
			if(event.getItem().hasMetadata("exp_share") && event.getItem().getMetadata("exp_share").size() > 0) {
				for(MetadataValue meta : event.getItem().getMetadata("exp_share")) {
					OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(meta.asString()));
					if(offlinePlayer.getPlayer() != null) {
						AdvancementUtils.awardCriteria(offlinePlayer.getPlayer(), ESAdvancement.SHARING_IS_CARING, "player");
					}
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		ItemStack chestplate = event.getPlayer().getInventory().getChestplate();
		if(chestplate != null && Enchantments.hasEnchantment(chestplate, DefaultEnchantments.LIFE)) {
			int level = Enchantments.getLevel(chestplate, DefaultEnchantments.LIFE);
			if(item.getType() == Material.ENCHANTED_GOLDEN_APPLE && level >= DefaultEnchantments.getCustomEnchantment(DefaultEnchantments.LIFE).getMaxLevel()) {
				AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.EXTRA_POWER, "power");
			}
		}
	}

}
