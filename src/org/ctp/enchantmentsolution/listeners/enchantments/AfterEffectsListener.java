package org.ctp.enchantmentsolution.listeners.enchantments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.ExperienceEvent;
import org.ctp.enchantmentsolution.events.ExperienceEvent.ExpShareType;
import org.ctp.enchantmentsolution.listeners.Enchantmentable;
import org.ctp.enchantmentsolution.utils.abillityhelpers.DrownedEntity;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

@SuppressWarnings("unused")
public class AfterEffectsListener extends Enchantmentable {

	private static List<UUID> SACRIFICE_ADVANCEMENT = new ArrayList<UUID>();

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		runMethod(this, "drowned", event, EntityDeathEvent.class);
		runMethod(this, "expShare", event, EntityDeathEvent.class);
		runMethod(this, "pillage", event, EntityDeathEvent.class);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		runMethod(this, "sacrifice", event, PlayerDeathEvent.class);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		runMethod(this, "sacrifice", event, PlayerRespawnEvent.class);
	}

	private void drowned(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.DROWNED, event)) {
			return;
		}
		Iterator<DrownedEntity> entities = EnchantmentSolution.getDrowned().iterator();
		while (entities.hasNext()) {
			DrownedEntity entity = entities.next();
			if (entity.getHurtEntity().equals(event.getEntity())) {
				entities.remove();
				break;
			}
		}
	}

	private void expShare(EntityDeathEvent event) {
		if (!canRun(RegisterEnchantments.EXP_SHARE, event)) {
			return;
		}
		if (event.getEntity() instanceof Player) {
			return;
		}
		Entity killer = event.getEntity().getKiller();
		if (killer instanceof Player) {
			Player player = (Player) killer;
			ItemStack killItem = player.getInventory().getItemInMainHand();
			if (killItem != null && ItemUtils.hasEnchantment(killItem, RegisterEnchantments.EXP_SHARE)) {
				int exp = event.getDroppedExp();
				if (exp > 0) {
					int level = ItemUtils.getLevel(killItem, RegisterEnchantments.EXP_SHARE);

					ExperienceEvent experienceEvent = new ExperienceEvent(player, ExpShareType.BLOCK, exp,
							AbilityUtils.setExp(exp, level));
					Bukkit.getPluginManager().callEvent(experienceEvent);

					if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) {
						event.setDroppedExp(experienceEvent.getNewExp());
					}
				}
			}
		}
	}
}
