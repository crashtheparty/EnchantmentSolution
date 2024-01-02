package org.ctp.enchantmentsolution.interfaces.fishing;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.ExpShareType;
import org.ctp.enchantmentsolution.events.player.ExpSharePlayerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.FishingCondition;
import org.ctp.enchantmentsolution.interfaces.effects.fishing.FishingExpEffect;

public class ExpShareFishing extends FishingExpEffect {

	public ExpShareFishing() {
		super(RegisterEnchantments.EXP_SHARE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, 0, "1", 0, "1", false, false, false, false, 0.5, true, new FishingCondition[0]);
	}

	@Override
	public FishingExpResult run(Player player, ItemStack[] items, PlayerFishEvent event) {
		FishingExpResult result = super.run(player, items, event);
		if (result.getLevel() == 0) return null;
		int exp = result.getExp();

		if (exp > 0) {
			ExpSharePlayerEvent experienceEvent = new ExpSharePlayerEvent(player, result.getLevel(), ExpShareType.MOB, event.getExpToDrop(), exp);

			Bukkit.getPluginManager().callEvent(experienceEvent);

			if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) {
				event.setExpToDrop(experienceEvent.getNewExp());
				return new FishingExpResult(result.getLevel(), experienceEvent.getNewExp());
			}
		}
		return null;
	}
}
