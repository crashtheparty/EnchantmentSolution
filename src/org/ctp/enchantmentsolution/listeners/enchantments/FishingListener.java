package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.fishing.AnglerEvent;
import org.ctp.enchantmentsolution.events.fishing.FriedEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class FishingListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerFish(PlayerFishEvent event) {
		if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			Item item = (Item) event.getCaught();
			ItemStack caught = item.getItemStack();
			Player player = event.getPlayer();
			ItemStack rod = player.getInventory().getItemInMainHand();
			if (ItemUtils.hasEnchantment(rod, RegisterEnchantments.FRIED)) {
				if (canRun(RegisterEnchantments.FRIED, event)) {
					FriedEvent e = new FriedEvent(player, caught.getType());
					Bukkit.getPluginManager().callEvent(e);
					if (!e.isCancelled()) {
						caught.setType(e.getFish());
						if (caught.getType().equals(Material.COD)) {
							caught.setType(Material.COOKED_COD);
							AdvancementUtils.awardCriteria(player, ESAdvancement.FISH_STICKS, "cooked");
						} else if (caught.getType().equals(Material.SALMON)) {
							caught.setType(Material.COOKED_SALMON);
							AdvancementUtils.awardCriteria(player, ESAdvancement.FISH_STICKS, "cooked");
						} else if (caught.getType() == Material.TROPICAL_FISH) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.NEMO_ENIM_COQUIT, "tropical_fish");
						}
					}
				}
			}
			if (ItemUtils.hasEnchantment(rod, RegisterEnchantments.ANGLER)) {
				if (canRun(RegisterEnchantments.ANGLER, event)) {
					List<Material> fish = Arrays.asList(Material.COD, Material.COOKED_COD, Material.SALMON,
					Material.COOKED_SALMON, Material.TROPICAL_FISH, Material.PUFFERFISH);
					if (fish.contains(caught.getType())) {
						AnglerEvent e = new AnglerEvent(player, caught.getType(),
						ItemUtils.getLevel(rod, RegisterEnchantments.ANGLER));
						Bukkit.getPluginManager().callEvent(e);
						if (!e.isCancelled()) {
							caught.setType(e.getFish());
							caught.setAmount(e.getFishAmount());
							Material type = e.getFish();
							if (type == Material.COOKED_COD) {
								type = Material.COD;
							}
							if (type == Material.COOKED_SALMON) {
								type = Material.SALMON;
							}
							AdvancementUtils.awardCriteria(player, ESAdvancement.FED_FOR_A_LIFETIME,
							type.name().toLowerCase());
						}
					}
				}
			}
			((Item) event.getCaught()).setItemStack(caught);
		}
	}
}
