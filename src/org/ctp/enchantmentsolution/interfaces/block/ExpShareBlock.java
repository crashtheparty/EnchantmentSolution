package org.ctp.enchantmentsolution.interfaces.block;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockExpEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.ExpShareType;
import org.ctp.enchantmentsolution.events.player.ExpSharePlayerEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.interfaces.effects.block.BlockChangeExpEffect;

public class ExpShareBlock extends BlockChangeExpEffect {

	public ExpShareBlock() {
		super(RegisterEnchantments.EXP_SHARE, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.NORMAL, 0, "1", 0, "1", false, false, false, false, 0.5, true, new BlockCondition[0]);
	}

	@Override
	public BlockChangeExpResult run(Player player, ItemStack[] items, BlockData brokenData, BlockExpEvent event) {
		BlockChangeExpResult result = super.run(player, items, brokenData, event);
		int level = result.getLevel();

		if (level == 0) return null;

		int exp = result.getExp();
		if (exp > 0) {
			ExpSharePlayerEvent experienceEvent = new ExpSharePlayerEvent(player, level, ExpShareType.BLOCK, event.getExpToDrop(), exp);
			Bukkit.getPluginManager().callEvent(experienceEvent);

			if (!experienceEvent.isCancelled() && experienceEvent.getNewExp() >= 0) {
				event.setExpToDrop(experienceEvent.getNewExp());
				return new BlockChangeExpResult(level, experienceEvent.getNewExp());
			}
		}

		return null;
	}

}
