package org.ctp.enchantmentsolution.listeners.abilities;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.items.nms.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;

public class SmelteryListener implements Listener{

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.SMELTERY)) return;
		Block blockBroken = event.getBlock();
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)) return;
		if(item != null) {
			if(Enchantments.hasEnchantment(item, DefaultEnchantments.SMELTERY)) {
				ItemStack smelted = AbilityUtils.getSmelteryItem(blockBroken, item);
				if(smelted != null) {
					if(!DefaultEnchantments.isEnabled(DefaultEnchantments.TELEPATHY) || !Enchantments.hasEnchantment(item, DefaultEnchantments.TELEPATHY)) {
						McMMO.handleMcMMO(event);
						int unbreaking = Enchantments.getLevel(item, Enchantment.DURABILITY);
						double chance = (1.0D) / (unbreaking + 1.0D);
						double random = Math.random();
						if(chance > random) {
							DamageUtils.setDamage(item, DamageUtils.getDamage(item.getItemMeta()) + 1);
							if(DamageUtils.getDamage(item.getItemMeta()) > item.getType().getMaxDurability()) {
								player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
								player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
							}
						}
						switch(event.getBlock().getType()) {
						case IRON_ORE:
						case GOLD_ORE:
							AbilityUtils.dropExperience(blockBroken.getLocation().add(0.5, 0.5, 0.5), (int) (Math.random() * 3) + 1);
							break;
						default:
							break;
						}
						event.getBlock().setType(Material.AIR);
						Item droppedItem = player.getWorld().dropItem(
								blockBroken.getLocation().add(0.5, 0.5, 0.5),
								smelted);
						droppedItem.setVelocity(new Vector(0,0,0));
					}
				}
			}
		}
	}
}
