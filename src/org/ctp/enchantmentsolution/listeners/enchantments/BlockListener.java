package org.ctp.enchantmentsolution.listeners.enchantments;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.ExperienceEvent;
import org.ctp.enchantmentsolution.events.ExperienceEvent.ExpShareType;
import org.ctp.enchantmentsolution.events.blocks.GoldDiggerEvent;
import org.ctp.enchantmentsolution.events.modify.LagEvent;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.abillityhelpers.GoldDiggerCrop;
import org.ctp.enchantmentsolution.utils.abillityhelpers.ParticleEffect;
import org.ctp.enchantmentsolution.utils.items.*;

@SuppressWarnings("unused")
public class BlockListener extends Enchantmentable {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		runMethod(this, "expShare", event, BlockBreakEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreakHighest(BlockBreakEvent event) {
		// runMethod(this, "heightWidth", event, BlockBreakEvent.class);
		runMethod(this, "curseOfLag", event, BlockBreakEvent.class);
		runMethod(this, "goldDigger", event, BlockBreakEvent.class);
		// runMethod(this, "smeltery", event, BlockBreakEvent.class);
		// runMethod(this, "telepathy", event, BlockBreakEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlaceHighest(BlockPlaceEvent event) {
		// runMethod(this, "wand", event, BlockPlaceEvent.class);
	}

	private void curseOfLag(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.CURSE_OF_LAG, event)) {
			return;
		}
		Player player = event.getPlayer();
		if (player != null) {
			ItemStack item = player.getInventory().getItemInMainHand();
			if (item != null && ItemUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_LAG)) {
				LagEvent lag = new LagEvent(player, player.getLocation(), AbilityUtils.createEffects(player));

				Bukkit.getPluginManager().callEvent(lag);
				if (!lag.isCancelled() && lag.getEffects().size() > 0) {
					Location loc = lag.getLocation();
					for(ParticleEffect effect: lag.getEffects()) {
						loc.getWorld().spawnParticle(effect.getParticle(), loc, effect.getNum(), effect.getVarX(),
								effect.getVarY(), effect.getVarZ());
					}
					if (lag.getSound() != null) {
						loc.getWorld().playSound(loc, lag.getSound(), lag.getVolume(), lag.getPitch());
					}
					AdvancementUtils.awardCriteria(player, ESAdvancement.LAAAGGGGGG, "lag");
				}
			}
		}
	}

	private void expShare(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.EXP_SHARE, event)) {
			return;
		}
		Player player = event.getPlayer();
		ItemStack killItem = player.getInventory().getItemInMainHand();
		if (killItem != null && ItemUtils.hasEnchantment(killItem, RegisterEnchantments.EXP_SHARE)) {
			int exp = event.getExpToDrop();
			if (exp > 0) {
				int level = ItemUtils.getLevel(killItem, RegisterEnchantments.EXP_SHARE);

				ExperienceEvent experienceEvent = new ExperienceEvent(player, ExpShareType.BLOCK, exp,
						AbilityUtils.setExp(exp, level));
				Bukkit.getPluginManager().callEvent(experienceEvent);

				if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) {
					event.setExpToDrop(experienceEvent.getNewExp());
				}
			}
		}
	}

	private void goldDigger(BlockBreakEvent event) {
		if (!canRun(RegisterEnchantments.GOLD_DIGGER, event)) {
			return;
		}
		if (event.isCancelled()) {
			return;
		}
		Player player = event.getPlayer();
		ItemStack item = player.getInventory().getItemInMainHand();
		if (item != null) {
			if (!ItemUtils.hasEnchantment(item, RegisterEnchantments.TELEPATHY)) {
				if (ItemUtils.hasEnchantment(item, RegisterEnchantments.GOLD_DIGGER)) {
					ItemStack goldDigger = AbilityUtils.getGoldDiggerItems(item, event.getBlock());
					if (goldDigger != null) {
						GoldDiggerEvent goldDiggerEvent = new GoldDiggerEvent(player, event.getBlock(), goldDigger,
								GoldDiggerCrop.getExp(event.getBlock().getType(),
										ItemUtils.getLevel(item, RegisterEnchantments.GOLD_DIGGER)));
						Bukkit.getPluginManager().callEvent(goldDiggerEvent);

						if (!goldDiggerEvent.isCancelled()) {
							AbilityUtils.dropExperience(goldDiggerEvent.getBlock().getLocation(),
									goldDiggerEvent.getExpToDrop());
							ItemUtils.dropItem(goldDiggerEvent.getGoldItem(), goldDiggerEvent.getBlock().getLocation(),
									true);
							AdvancementUtils.awardCriteria(player, ESAdvancement.FOURTY_NINERS, "goldblock",
									goldDigger.getAmount());
							player.incrementStatistic(Statistic.USE_ITEM, item.getType());
							DamageUtils.damageItem(player, item);
						}
					}
				}
			}
		}
	}
}
