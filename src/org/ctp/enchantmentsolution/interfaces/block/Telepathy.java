package org.ctp.enchantmentsolution.interfaces.block;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.TelepathyBlockEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;
import org.ctp.enchantmentsolution.interfaces.effects.block.TransferDropToInvEffect;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class Telepathy extends TransferDropToInvEffect {

	public Telepathy() {
		super(RegisterEnchantments.TELEPATHY, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.ATTACKED, EventPriority.MONITOR, new BlockCondition[0]);
	}

	@Override
	public TransferDropToInvResult run(Player player, ItemStack[] items, BlockData brokenData, BlockDropItemEvent event) {
		TransferDropToInvResult result = super.run(player, items, brokenData, event);

		if (result.getLevel() == 0) return null;

		ItemStack item = items[0];

		List<ItemStack> itemStacks = new ArrayList<ItemStack>();
		for(Item i: event.getItems())
			itemStacks.add(i.getItemStack());
		TelepathyBlockEvent telepathy = new TelepathyBlockEvent(event.getBlock(), brokenData, player, itemStacks);
		Bukkit.getPluginManager().callEvent(telepathy);

		if (!telepathy.isCancelled()) {
			ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
			esPlayer.addTelepathyItems(telepathy.getItems());
			McMMOHandler.handleBlockDrops(event, item, RegisterEnchantments.TELEPATHY);
			event.setCancelled(true);
			return result;
		}

		return null;
	}

}
