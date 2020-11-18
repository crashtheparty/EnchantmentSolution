package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
import org.ctp.enchantmentsolution.events.player.ExpShareEvent;
import org.ctp.enchantmentsolution.events.player.ExpShareEvent.ExpShareType;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

@SuppressWarnings("unused")
public class FishingListener extends Enchantmentable {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerFish(PlayerFishEvent event) {
		runMethod(this, "anglerFried", event, PlayerFishEvent.class);
		runMethod(this, "expShare", event, PlayerFishEvent.class);
	}

	private void anglerFried(PlayerFishEvent event) {
		if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
			List<Material> fish = Arrays.asList(Material.COD, Material.COOKED_COD, Material.SALMON, Material.COOKED_SALMON, Material.TROPICAL_FISH, Material.PUFFERFISH);
			Item item = (Item) event.getCaught();
			ItemStack caught = item.getItemStack();
			Player player = event.getPlayer();
			ItemStack rod = player.getInventory().getItemInMainHand();
			if (EnchantmentUtils.hasEnchantment(rod, RegisterEnchantments.FRIED) && canRun(RegisterEnchantments.FRIED, event) && fish.contains(caught.getType())) {
				if (isDisabled(player, RegisterEnchantments.FRIED)) return;
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
					} else if (caught.getType() == Material.TROPICAL_FISH) AdvancementUtils.awardCriteria(player, ESAdvancement.NEMO_ENIM_COQUIT, "tropical_fish");
				}
			}
			if (EnchantmentUtils.hasEnchantment(rod, RegisterEnchantments.ANGLER) && canRun(RegisterEnchantments.ANGLER, event) && fish.contains(caught.getType())) {
				if (isDisabled(player, RegisterEnchantments.ANGLER)) return;
				AnglerEvent e = new AnglerEvent(player, caught.getType(), EnchantmentUtils.getLevel(rod, RegisterEnchantments.ANGLER));
				Bukkit.getPluginManager().callEvent(e);
				if (!e.isCancelled()) {
					caught.setType(e.getFish());
					caught.setAmount(e.getFishAmount());
					Material type = e.getFish();
					if (type == Material.COOKED_COD) type = Material.COD;
					if (type == Material.COOKED_SALMON) type = Material.SALMON;
					AdvancementUtils.awardCriteria(player, ESAdvancement.FED_FOR_A_LIFETIME, type.name().toLowerCase(Locale.ROOT));
				}
			}
			((Item) event.getCaught()).setItemStack(caught);
		}
	}

	private void expShare(PlayerFishEvent event) {
		if (!canRun(RegisterEnchantments.EXP_SHARE, event)) return;
		Player player = event.getPlayer();
		if (isDisabled(player, RegisterEnchantments.EXP_SHARE)) return;
		ItemStack item = player.getInventory().getItemInMainHand();
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.EXP_SHARE)) {
			int exp = event.getExpToDrop();
			if (exp > 0) {
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.EXP_SHARE);

				ExpShareEvent experienceEvent = new ExpShareEvent(player, level, ExpShareType.FISH, exp, AbilityUtils.setExp(exp, level));
				Bukkit.getPluginManager().callEvent(experienceEvent);

				if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) event.setExpToDrop(experienceEvent.getNewExp());
			}
		}
	}
}
