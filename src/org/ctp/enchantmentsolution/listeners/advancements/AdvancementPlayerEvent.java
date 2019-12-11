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
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class AdvancementPlayerEvent implements Listener{

	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
		Player player = event.getPlayer();
		if(player.getWorld().getTime() <= 12540 || player.getWorld().getTime() >= 23459) {
			ItemStack helmet = player.getInventory().getHelmet();
			if(helmet != null && ItemUtils.hasEnchantment(helmet, RegisterEnchantments.UNREST)) {
				AdvancementUtils.awardCriteria(player, ESAdvancement.I_AINT_AFRAID_OF_NO_GHOSTS, "unrest", 1);
			}
		}
	}
	@EventHandler(priority=EventPriority.MONITOR)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if(player != null) {
			if(ItemUtils.hasEnchantment(event.getItemDrop().getItemStack(), RegisterEnchantments.EXP_SHARE)) {
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
					if(offlinePlayer.getPlayer() != null && !offlinePlayer.getPlayer().equals(entity)) {
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
		if(chestplate != null && ItemUtils.hasEnchantment(chestplate, RegisterEnchantments.LIFE)) {
			int level = ItemUtils.getLevel(chestplate, RegisterEnchantments.LIFE);
			if(item.getType() == Material.ENCHANTED_GOLDEN_APPLE && level >= RegisterEnchantments.getCustomEnchantment(RegisterEnchantments.LIFE).getMaxLevel()) {
				AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.EXTRA_POWER, "power");
			}
		}
	}

}
