package org.ctp.enchantmentsolution.utils.compatibility;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.helper.EnchantmentLevel;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.actions.EnchantActionInfo;
import com.gamingmesh.jobs.actions.ItemActionInfo;
import com.gamingmesh.jobs.container.ActionType;
import com.gamingmesh.jobs.container.JobsPlayer;

public class JobsUtils {

	@SuppressWarnings("deprecation")
	public static void sendEnchantAction(Player player, ItemStack item, ItemStack resultStack, List<EnchantmentLevel> levels) {
		Jobs plugin = Jobs.getInstance();
		if (!plugin.isEnabled())
		    return;
		//disabling plugin in world
		if (!Jobs.getGCManager().canPerformActionInWorld(player.getWorld()))
		    return;

		if (resultStack == null)
		    return;

		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName()))
		    return;

		// check if in creative
		if (!payIfCreative(player))
		    return;

		// Prevent item durability loss
		if (!Jobs.getGCManager().payItemDurabilityLoss && item.getType().getMaxDurability() - DamageUtils.getDamage(item.getItemMeta()) != item.getType().getMaxDurability())
		    return;

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);

		if (jPlayer == null)
		    return;
		
		for (EnchantmentLevel enchLevel : levels) {
			Enchantment enchant = enchLevel.getEnchant().getRelativeEnchantment();
			int level = enchLevel.getLevel();
			String enchantName = enchant.getName();
		    if (enchantName == null)
			continue;
		    
		    Jobs.action(jPlayer, new EnchantActionInfo(enchantName, level, ActionType.ENCHANT));
		}
		Jobs.action(jPlayer, new ItemActionInfo(resultStack, ActionType.ENCHANT));
	}
	
	@SuppressWarnings("deprecation")
	public static void sendAnvilAction(Player player, ItemStack combine, ItemStack resultStack) {
		Jobs plugin = Jobs.getInstance();
		// make sure plugin is enabled
		if (!plugin.isEnabled())
		    return;
		//disabling plugin in world
		if (!Jobs.getGCManager().canPerformActionInWorld(player.getWorld()))
		    return;

		// Check for world permissions
		if (!Jobs.getPermissionHandler().hasWorldPermission(player, player.getLocation().getWorld().getName()))
		    return;

		// check if in creative
		if (!payIfCreative(player))
		    return;

		JobsPlayer jPlayer = Jobs.getPlayerManager().getJobsPlayer(player);
		if (jPlayer == null)
		    return;

		if (Jobs.getGCManager().PayForEnchantingOnAnvil && (combine.getType() == Material.ENCHANTED_BOOK || combine.getType() == Material.BOOK)) {
		    Map<Enchantment, Integer> enchants = resultStack.getEnchantments();
		    for (Entry<Enchantment, Integer> oneEnchant : enchants.entrySet()) {
			    Enchantment enchant = oneEnchant.getKey();
			    if (enchant == null)
				continue;
	
			    String enchantName = enchant.getName();
			    if (enchantName == null)
				continue;
	
			    Integer level = oneEnchant.getValue();
			    if (level == null)
				continue;
	
			    Jobs.action(jPlayer, new EnchantActionInfo(enchantName, level, ActionType.ENCHANT));
		    }
		} else
		    Jobs.action(jPlayer, new ItemActionInfo(resultStack, ActionType.REPAIR));
	}
	
	private static boolean payIfCreative(Player player) {
		if (player.getGameMode().equals(GameMode.CREATIVE) && !Jobs.getGCManager().payInCreative() && !player.hasPermission("jobs.paycreative"))
		    return false;
		return true;
    }
	
}
