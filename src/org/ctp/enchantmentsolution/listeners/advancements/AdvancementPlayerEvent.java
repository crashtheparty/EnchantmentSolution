package org.ctp.enchantmentsolution.listeners.advancements;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.CERegister;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.enums.ItemSlotType;
import org.ctp.enchantmentsolution.enums.MatData;
import org.ctp.enchantmentsolution.events.ArmorEquipEvent;
import org.ctp.enchantmentsolution.events.ItemEquipEvent;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerBlock;
import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class AdvancementPlayerEvent implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
		Player player = event.getPlayer();
		if (player.getWorld().getTime() <= 12540 || player.getWorld().getTime() >= 23459) {
			ItemStack helmet = player.getInventory().getHelmet();
			if (helmet != null && ItemUtils.hasEnchantment(helmet, RegisterEnchantments.UNREST)) AdvancementUtils.awardCriteria(player, ESAdvancement.I_AINT_AFRAID_OF_NO_GHOSTS, "unrest", 1);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		if (player != null) if (ItemUtils.hasEnchantment(event.getItemDrop().getItemStack(), RegisterEnchantments.EXP_SHARE)) event.getItemDrop().setMetadata("exp_share", new FixedMetadataValue(EnchantmentSolution.getPlugin(), player.getUniqueId().toString()));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Player) if (event.getItem().hasMetadata("exp_share") && event.getItem().getMetadata("exp_share").size() > 0) for(MetadataValue meta: event.getItem().getMetadata("exp_share")) {
			OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(meta.asString()));
			if (offlinePlayer.getPlayer() != null && !offlinePlayer.getPlayer().equals(entity)) AdvancementUtils.awardCriteria(offlinePlayer.getPlayer(), ESAdvancement.SHARING_IS_CARING, "player");
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		ItemStack item = event.getItem();
		ItemStack chestplate = event.getPlayer().getInventory().getChestplate();
		if (chestplate != null && ItemUtils.hasEnchantment(chestplate, RegisterEnchantments.LIFE)) {
			int level = ItemUtils.getLevel(chestplate, RegisterEnchantments.LIFE);
			if (item.getType() == Material.ENCHANTED_GOLDEN_APPLE && level >= CERegister.LIFE.getMaxLevel()) AdvancementUtils.awardCriteria(event.getPlayer(), ESAdvancement.EXTRA_POWER, "power");
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onArmorEquip(ArmorEquipEvent event) {
		if (event.isCancelled()) return;
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
			if (event.getNewArmorPiece() != null && ItemUtils.hasEnchantment(event.getNewArmorPiece(), RegisterEnchantments.TANK)) {
				Player player = event.getPlayer();
				boolean hasTank = true;
				for(ItemStack item: player.getInventory().getArmorContents())
					if (item == null || !(ItemUtils.hasEnchantment(item, RegisterEnchantments.TANK) && ItemUtils.getLevel(item, RegisterEnchantments.TANK) >= CERegister.TANK.getMaxLevel())) hasTank = false;

				if (hasTank) AdvancementUtils.awardCriteria(player, ESAdvancement.PANZER_SOLDIER, "tank");
			}
			if (event.getNewArmorPiece() != null && ItemUtils.hasEnchantment(event.getNewArmorPiece(), RegisterEnchantments.FORCE_FEED)) {
				Player player = event.getPlayer();
				boolean hasFeed = true;
				for(ItemStack item: player.getInventory().getArmorContents())
					if (item == null || !ItemUtils.hasEnchantment(item, RegisterEnchantments.FORCE_FEED)) hasFeed = false;

				if (hasFeed) AdvancementUtils.awardCriteria(player, ESAdvancement.HUNGRY_HIPPOS, "armor");
			}
		}, 0l);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemEquip(ItemEquipEvent event) {
		if (event.isCancelled()) return;
		ItemStack item = event.getNewItem();
		if (event.getSlot() == ItemSlotType.MAIN_HAND) Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), () -> {
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_STAGNANCY)) {
				Player player = event.getPlayer();
				if (item.getType() == Material.IRON_PICKAXE && ItemUtils.hasEnchantment(item, Enchantment.DURABILITY) && ItemUtils.getLevel(item, Enchantment.DURABILITY) >= CERegister.UNBREAKING.getMaxLevel()) AdvancementUtils.awardCriteria(player, ESAdvancement.STAINLESS_STEEL, "iron_pickaxe");
				if (item.getType() == new MatData("NETHERITE_SWORD").getMaterial() && ItemUtils.hasEnchantment(item, Enchantment.DAMAGE_ALL) && ItemUtils.getLevel(item, Enchantment.DAMAGE_ALL) >= CERegister.SHARPNESS.getMaxLevel()) AdvancementUtils.awardCriteria(player, ESAdvancement.NETHER_DULL, "netherite_sword");
			}
		}, 0l);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
		WalkerBlock walkerBlock = WalkerUtils.getWalker(block);
		if (player.getInventory().getBoots() != null) {
			ItemStack boots = player.getInventory().getBoots();
			if (ItemUtils.hasEnchantment(boots, RegisterEnchantments.MAGMA_WALKER) && walkerBlock != null && walkerBlock.getEnchantment() == RegisterEnchantments.MAGMA_WALKER && player.getFireTicks() > 0) AdvancementUtils.awardCriteria(player, ESAdvancement.THIS_GIRL_IS_ON_FIRE, "lava");

			if (ItemUtils.hasEnchantment(boots, RegisterEnchantments.VOID_WALKER) && walkerBlock != null && walkerBlock.getEnchantment() == RegisterEnchantments.VOID_WALKER && !LocationUtils.hasBlockBelow(block.getRelative(BlockFace.DOWN).getLocation())) AdvancementUtils.awardCriteria(player, ESAdvancement.MADE_FOR_WALKING, "boots");
		}
	}

}
