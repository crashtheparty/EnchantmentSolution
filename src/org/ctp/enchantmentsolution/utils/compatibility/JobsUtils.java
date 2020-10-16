package org.ctp.enchantmentsolution.utils.compatibility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.ChatUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.CMILib.CMIEnchantment;
import com.gamingmesh.jobs.CMILib.CMIMaterial;
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
		Jobs plugin = getJobs();
		// make sure plugin is enabled
		if (plugin == null) return;
		// disabling plugin in world
		if (!Jobs.getGCManager().canPerformActionInWorld(player.getWorld())) return;

		if (resultStack == null) return;

		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName())) return;

		// check if in creative
		if (!payIfCreative(player)) return;

		// Prevent item durability loss
		if (!Jobs.getGCManager().payItemDurabilityLoss && DamageUtils.getMaxDamage(item) - DamageUtils.getDamage(item) != DamageUtils.getMaxDamage(item)) return;

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);

		if (jPlayer == null) return;

		for(EnchantmentLevel enchLevel: levels) {
			Enchantment enchant = enchLevel.getEnchant().getRelativeEnchantment();
			int level = enchLevel.getLevel();
			String enchantName = enchant.getName();
			if (enchantName == null) continue;

			Jobs.action(jPlayer, new EnchantActionInfo(enchantName, level, ActionType.ENCHANT));
		}
		Jobs.action(jPlayer, new ItemActionInfo(resultStack, ActionType.ENCHANT));
	}

	@SuppressWarnings("deprecation")
	public static void sendAnvilAction(Player player, ItemStack combine, ItemStack resultStack) {
		Jobs plugin = getJobs();
		// make sure plugin is enabled
		if (plugin == null) return;
		// disabling plugin in world
		if (!Jobs.getGCManager().canPerformActionInWorld(player.getWorld())) return;

		// Check for world permissions
		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName())) return;

		// check if in creative
		if (!payIfCreative(player)) return;

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
		if (jPlayer == null) return;

		if (Jobs.getGCManager().PayForEnchantingOnAnvil && (combine.getType() == Material.ENCHANTED_BOOK || combine.getType() == Material.BOOK)) {
			Map<Enchantment, Integer> enchants = resultStack.getEnchantments();
			for(Entry<Enchantment, Integer> oneEnchant: enchants.entrySet()) {
				Enchantment enchant = oneEnchant.getKey();
				if (enchant == null) continue;

				String enchantName = enchant.getName();
				if (enchantName == null) continue;

				Integer level = oneEnchant.getValue();
				if (level == null) continue;

				Jobs.action(jPlayer, new EnchantActionInfo(enchantName, level, ActionType.ENCHANT));
			}
		} else
			Jobs.action(jPlayer, new ItemActionInfo(resultStack, ActionType.REPAIR));
	}

	public static void sendBlockBreakAction(BlockBreakEvent event) {
		Jobs plugin = getJobs();
		// make sure plugin is enabled
		if (plugin == null) return;

		Block block = event.getBlock();
		// disabling plugin in world
		if (!Jobs.getGCManager().canPerformActionInWorld(block.getWorld())) return;

		Player player = event.getPlayer();

		if (player == null || !player.isOnline()) return;

		// check if in creative
		if (!payIfCreative(player)) return;

		// check if player is riding
		if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle()) return;

		CMIMaterial cmat = CMIMaterial.get(block);
		if (cmat.equals(CMIMaterial.FURNACE) || cmat.equals(CMIMaterial.SMOKER) || cmat.equals(CMIMaterial.BLAST_FURNACE) && block.hasMetadata(JobsPaymentListener.furnaceOwnerMetadata)) FurnaceBrewingHandling.removeFurnace(block);
		else if (cmat.equals(CMIMaterial.BREWING_STAND) || cmat.equals(CMIMaterial.LEGACY_BREWING_STAND) && block.hasMetadata(JobsPaymentListener.brewingOwnerMetadata)) FurnaceBrewingHandling.removeBrewing(block);

		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName())) return;

		BlockActionInfo bInfo = new BlockActionInfo(block, ActionType.BREAK);

		FastPayment fp = Jobs.FASTPAYMENT.get(player.getUniqueId());
		if (fp != null) {
			if (fp.getTime() > System.currentTimeMillis()) if (fp.getInfo().getName().equalsIgnoreCase(bInfo.getName()) || fp.getInfo().getNameWithSub().equalsIgnoreCase(bInfo.getNameWithSub())) {
				Jobs.perform(fp.getPlayer(), fp.getInfo(), fp.getPayment(), fp.getJob());
				return;
			}
			Jobs.FASTPAYMENT.remove(player.getUniqueId());
		}

		if (!payForItemDurabilityLoss(player)) return;

		// restricted area multiplier

		// Item in hand
		ItemStack item = Jobs.getNms().getItemInMainHand(player);
		if (item != null && !item.getType().equals(Material.AIR)) // Protection for block break with silktouch
			if (Jobs.getGCManager().useSilkTouchProtection) for(Entry<Enchantment, Integer> one: item.getEnchantments().entrySet())
				if (CMIEnchantment.get(one.getKey()) == CMIEnchantment.SILK_TOUCH) if (Jobs.getBpManager().isInBp(block)) return;

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
		if (jPlayer == null) return;

		Jobs.action(jPlayer, bInfo, block);
	}

	public static void sendBlockPlaceAction(BlockPlaceEvent event) {
		Jobs plugin = getJobs();
		// make sure plugin is enabled
		if (plugin == null) return;
		Block block = event.getBlock();

		if (block == null) return;

		// disabling plugin in world
		if (!Jobs.getGCManager().canPerformActionInWorld(block.getWorld())) return;

		// check to make sure you can build
		if (!event.canBuild()) return;

		Player player = event.getPlayer();

		if (player == null || !player.isOnline()) return;

		// check if in creative
		if (!payIfCreative(player)) return;

		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName())) return;

		// check if player is riding
		if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle()) return;

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
		if (jPlayer == null) return;

		Jobs.action(jPlayer, new BlockActionInfo(block, ActionType.PLACE), block);
	}

	public static void sendFishAction(PlayerFishEvent event) {
		Jobs plugin = getJobs();
		// make sure plugin is enabled
		if (plugin == null) return;

		Player player = event.getPlayer();
		// disabling plugin in world
		if (!Jobs.getGCManager().canPerformActionInWorld(player.getWorld())) return;

		// check if in creative
		if (!payIfCreative(player)) return;

		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName())) return;

		// check if player is riding
		if (Jobs.getGCManager().disablePaymentIfRiding && player.isInsideVehicle()) return;

		if (!payForItemDurabilityLoss(player)) return;

		if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH) && event.getCaught() instanceof Item) {
			JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
			if (jPlayer == null) return;
			ItemStack items = ((Item) event.getCaught()).getItemStack();
			Jobs.action(jPlayer, new ItemActionInfo(items, ActionType.FISH));
		}
	}

	private static boolean payIfCreative(Player player) {
		if (player.getGameMode().equals(GameMode.CREATIVE) && !Jobs.getGCManager().payInCreative() && !player.hasPermission("jobs.paycreative")) return false;
		return true;
	}

	// Prevent item durability loss
	private static boolean payForItemDurabilityLoss(Player p) {
		if (Jobs.getGCManager().payItemDurabilityLoss) return true;

		ItemStack hand = Jobs.getNms().getItemInMainHand(p);
		CMIMaterial cmat = CMIMaterial.get(hand);

		HashMap<Enchantment, Integer> got = Jobs.getGCManager().whiteListedItems.get(cmat);
		if (got == null) return false;

		if (Jobs.getNms().getDurability(hand) == 0) return true;

		for(Entry<Enchantment, Integer> oneG: got.entrySet()) {
			if (!hand.getEnchantments().containsKey(oneG.getKey())) return false;
			if (oneG.getValue() != null && hand.getEnchantments().get(oneG.getKey()) != oneG.getValue()) return false;
		}

		return true;
	}
	
	private static Jobs getJobs() {
		Jobs plugin = null;
		try {
			plugin = Jobs.getInstance();
			if (plugin == null)
				ChatUtils.sendWarning("Jobs has not been initialized properly - ES will ignore it.");
		} catch (Exception ex) {
			ChatUtils.sendWarning("Jobs is not installed on this server - something is wrong.");
		}
		return plugin != null ? plugin.isEnabled() ? plugin : null : null;
	}

}
