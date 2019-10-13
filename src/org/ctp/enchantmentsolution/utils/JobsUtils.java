package org.ctp.enchantmentsolution.utils;

import java.util.List;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.CMILib.CMIEnchantment;
import com.gamingmesh.jobs.CMILib.ItemManager.CMIMaterial;
import com.gamingmesh.jobs.actions.BlockActionInfo;
import com.gamingmesh.jobs.actions.EnchantActionInfo;
import com.gamingmesh.jobs.actions.ItemActionInfo;
import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.FastPayment;
import com.gamingmesh.jobs.container.JobsPlayer;
import com.gamingmesh.jobs.listeners.JobsPaymentListener;
import com.gamingmesh.jobs.stuff.FurnaceBrewingHandling;

public class JobsUtils {

	@SuppressWarnings("deprecation")
	public static void sendEnchantAction(Player player, ItemStack item, ItemStack resultStack,
			List<EnchantmentLevel> levels) {
		Jobs plugin = Jobs.getInstance();
		if (!plugin.isEnabled())
			return;
		// disabling plugin in world
		if (player != null && !Jobs.getGCManager().canPerformActionInWorld(player.getWorld()))
			return;

		if (resultStack == null)
			return;

		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName()))
			return;

		// check if in creative
		if (!payIfCreative(player))
			return;

		// check if player is riding
		if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle())
			return;

		if (!payForItemDurabilityLoss(player))
			return;

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);

		if (jPlayer == null)
			return;

		for(EnchantmentLevel enchLevel: levels) {
			Enchantment enchant = enchLevel.getEnchant().getRelativeEnchantment();
			int level = enchLevel.getLevel();
			String enchantName = enchant.getName();
			if (level <= 0)
				continue;

			Jobs.action(jPlayer, new EnchantActionInfo(enchantName, level, ActionType.ENCHANT));
		}
		Jobs.action(jPlayer, new ItemActionInfo(resultStack, ActionType.ENCHANT));
	}

	@SuppressWarnings("deprecation")
	public static void sendAnvilAction(Player player, ItemStack first, ItemStack combine, ItemStack resultStack,
			InventoryAction action, ClickType clickType, boolean isShiftClick) {
		Jobs plugin = Jobs.getInstance();
		if (!plugin.isEnabled())
			return;
		// disabling plugin in world
		if (player != null && !Jobs.getGCManager().canPerformActionInWorld(player.getWorld()))
			return;

		// If event is nothing or place, do nothing
		switch (action) {
			case NOTHING:
			case PLACE_ONE:
			case PLACE_ALL:
			case PLACE_SOME:
				return;
			default:
				break;
		}

		// Check if inventory is full and using shift click, possible money dupping fix
		if (player.getInventory().firstEmpty() == -1 && isShiftClick) {
			player.sendMessage(Jobs.getLanguage().getMessage("message.crafting.fullinventory"));
			return;
		}

		if (resultStack == null)
			return;

		String OriginalName = null;
		String NewName = null;
		if (first.hasItemMeta() && first.getItemMeta().getDisplayName() != null)
			OriginalName = first.getItemMeta().getDisplayName();
		if (resultStack.hasItemMeta() && resultStack.getItemMeta().getDisplayName() != null)
			NewName = resultStack.getItemMeta().getDisplayName();
		if (OriginalName != NewName && combine == null)
			if (!Jobs.getGCManager().PayForRenaming)
				return;

		// Check for world permissions
		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName()))
			return;

		// check if in creative
		if (!payIfCreative(player))
			return;

		// check if player is riding
		if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle())
			return;

		// Fix for possible money duplication bugs.
		switch (clickType) {
			case UNKNOWN:
			case WINDOW_BORDER_LEFT:
			case WINDOW_BORDER_RIGHT:
			case NUMBER_KEY:
				return;
			default:
				break;
		}

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
		if (jPlayer == null)
			return;

		if (Jobs.getGCManager().PayForEnchantingOnAnvil && combine != null
				&& combine.getType().equals(Material.ENCHANTED_BOOK)) {
			List<EnchantmentLevel> levels = Enchantments.getEnchantmentLevels(resultStack);
			
			for(EnchantmentLevel enchLevel: levels) {
				Enchantment enchant = enchLevel.getEnchant().getRelativeEnchantment();
				int level = enchLevel.getLevel();
				String enchantName = enchant.getName();
				if (level <= 0)
					continue;

				Jobs.action(jPlayer, new EnchantActionInfo(enchantName, level, ActionType.ENCHANT));
			}
		} else
			Jobs.action(jPlayer, new ItemActionInfo(resultStack, ActionType.REPAIR));
	}

	public static void sendBlockBreakAction(BlockBreakEvent event) {
		Jobs plugin = Jobs.getInstance();
		// make sure plugin is enabled
		if (!plugin.isEnabled())
			return;
		// disabling plugin in world
		if (event.getBlock() != null && !Jobs.getGCManager().canPerformActionInWorld(event.getBlock().getWorld()))
			return;
		Block block = event.getBlock();
		if (block == null)
			return;

		Player player = event.getPlayer();

		if (player == null || !player.isOnline())
			return;

		// check if in creative
		if (!payIfCreative(player))
			return;

		// check if player is riding
		if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle())
			return;

		CMIMaterial cmat = CMIMaterial.get(block);
		if (cmat.equals(CMIMaterial.FURNACE) && block.hasMetadata(JobsPaymentListener.furnaceOwnerMetadata))
			FurnaceBrewingHandling.removeFurnace(block);
		else if (cmat.equals(CMIMaterial.SMOKER) && block.hasMetadata(JobsPaymentListener.furnaceOwnerMetadata))
			FurnaceBrewingHandling.removeFurnace(block);
		else if (cmat.equals(CMIMaterial.BLAST_FURNACE) && block.hasMetadata(JobsPaymentListener.furnaceOwnerMetadata))
			FurnaceBrewingHandling.removeFurnace(block);
		else if (cmat.equals(CMIMaterial.BREWING_STAND) && block.hasMetadata(JobsPaymentListener.brewingOwnerMetadata))
			FurnaceBrewingHandling.removeBrewing(block);

		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName()))
			return;

		BlockActionInfo bInfo = new BlockActionInfo(block, ActionType.BREAK);

		FastPayment fp = Jobs.FastPayment.get(player.getUniqueId());
		if (fp != null) {
			if (fp.getTime() > System.currentTimeMillis()) {
				if (fp.getInfo().getName().equalsIgnoreCase(bInfo.getName())
						|| fp.getInfo().getNameWithSub().equalsIgnoreCase(bInfo.getNameWithSub())) {
					Jobs.perform(fp.getPlayer(), fp.getInfo(), fp.getPayment(), fp.getJob());
					return;
				}
			}
			Jobs.FastPayment.remove(player.getUniqueId());
		}

		if (!payForItemDurabilityLoss(player))
			return;

		// restricted area multiplier

		// Item in hand
		ItemStack item = Jobs.getNms().getItemInMainHand(player);
		if (item != null && !item.getType().equals(Material.AIR)) {
			// Protection for block break with silktouch
			if (Jobs.getGCManager().useSilkTouchProtection) {
				for(Entry<Enchantment, Integer> one: item.getEnchantments().entrySet()) {
					if (CMIEnchantment.get(one.getKey()) == CMIEnchantment.SILK_TOUCH) {
						if (Jobs.getBpManager().isInBp(block))
							return;
					}
				}
			}
		}

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
		if (jPlayer == null)
			return;

		Jobs.action(jPlayer, bInfo, block);
	}

	public static void sendBlockPlaceAction(BlockPlaceEvent event) {
		Jobs plugin = Jobs.getInstance();
		// make sure plugin is enabled
		if (!plugin.isEnabled())
			return;
		Block block = event.getBlock();

		if (block == null)
			return;

		// disabling plugin in world
		if (!Jobs.getGCManager().canPerformActionInWorld(block.getWorld()))
			return;

		// check to make sure you can build
		if (!event.canBuild())
			return;

		Player player = event.getPlayer();

		if (player == null || !player.isOnline())
			return;

		// check if in creative
		if (!payIfCreative(player))
			return;

		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName()))
			return;

		// check if player is riding
		if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle())
			return;

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
		if (jPlayer == null)
			return;

		Jobs.action(jPlayer, new BlockActionInfo(block, ActionType.PLACE), block);
	}
	
    public static void sendFishAction(PlayerFishEvent event) {
		Jobs plugin = Jobs.getInstance();
		// make sure plugin is enabled
		if (!plugin.isEnabled())
		    return;
		//disabling plugin in world
		if (event.getPlayer() != null && !Jobs.getGCManager().canPerformActionInWorld(event.getPlayer().getWorld()))
		    return;
	
		Player player = event.getPlayer();
	
		// check if in creative
		if (!payIfCreative(player))
		    return;
	
		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName()))
		    return;
	
		// check if player is riding
		if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle())
		    return;
	
		if (!payForItemDurabilityLoss(player))
		    return;
	
		if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH) && event.getCaught() instanceof Item) {
		    JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
		    if (jPlayer == null)
			return;
		    ItemStack items = ((Item) event.getCaught()).getItemStack();
		    Jobs.action(jPlayer, new ItemActionInfo(items, ActionType.FISH));
		}
    }

	private static boolean payIfCreative(Player player) {
		if (player.getGameMode().equals(GameMode.CREATIVE) && !Jobs.getGCManager().payInCreative()
				&& !player.hasPermission("jobs.paycreative"))
			return false;
		return true;
	}

	// Prevent item durability loss
	private static boolean payForItemDurabilityLoss(Player p) {
		ItemStack hand = Jobs.getNms().getItemInMainHand(p);

		if (!Jobs.getGCManager().payItemDurabilityLoss && hand != null && !hand.getType().equals(Material.AIR)
				&& hand.getType().getMaxDurability() - Jobs.getNms().getDurability(hand) != hand.getType()
						.getMaxDurability()) {
			for(String whiteList: Jobs.getGCManager().WhiteListedItems) {
				String item = whiteList.contains("=") ? whiteList.split("=")[0] : whiteList;
				if (item.contains("-")) {
					item = item.split("-")[0];
				}

				CMIMaterial mat = CMIMaterial.get(item);
				if (mat == null) {
					mat = CMIMaterial.get(item.replace(" ", "_").toUpperCase());
				}

				if (mat == null) {
					// try integer method
					Integer matId = null;
					try {
						matId = Integer.valueOf(item);
					} catch (NumberFormatException e) {
					}
					if (matId != null) {
						mat = CMIMaterial.get(matId);
					}
				}

				if (whiteList.contains("=") && whiteList.split("=").length == 2) {
					if (!hand.getEnchantments().containsKey(CMIEnchantment.getEnchantment(whiteList.split("=")[1]))) {
						return false;
					}
				}

				if (mat != null && hand.getType().equals(mat.getMaterial())) {
					return true;
				}
			}
			return false;
		}

		return true;
	}

}
