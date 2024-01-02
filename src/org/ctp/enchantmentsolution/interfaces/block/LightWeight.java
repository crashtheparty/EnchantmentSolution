package org.ctp.enchantmentsolution.interfaces.block;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.LightWeightEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.EntityBlockCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.block.EntityChangeBlockFromCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.block.EntityChangeBlockToCondition;
import org.ctp.enchantmentsolution.interfaces.effects.block.EntityChangeBlockEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class LightWeight extends EntityChangeBlockEffect {

	public LightWeight() {
		super(RegisterEnchantments.LIGHT_WEIGHT, EnchantmentMultipleType.ALL, EnchantmentItemLocation.WEARING, EventPriority.HIGHEST, new EntityBlockCondition[] { new EntityChangeBlockFromCondition(false, new MatData("FARMLAND")), new EntityChangeBlockToCondition(false, new MatData("DIRT")) });
	}

	@Override
	public EntityChangeBlockResult run(Entity entity, Block block, ItemStack[] items, EntityChangeBlockEvent event) {
		EntityChangeBlockResult result = super.run(entity, block, items, event);

		if (result.getLevel() == 0) return null;

		HumanEntity human = (HumanEntity) entity;

		LightWeightEvent lightWeight = new LightWeightEvent(event.getBlock(), human);
		Bukkit.getPluginManager().callEvent(lightWeight);

		if (!lightWeight.isCancelled()) {
			event.setCancelled(true);
			Block relative = event.getBlock().getRelative(BlockFace.UP);
			if (relative.getBlockData() instanceof Ageable) {
				Ageable crop = (Ageable) relative.getBlockData();
				if (crop.getAge() == crop.getMaximumAge() && human instanceof Player) AdvancementUtils.awardCriteria((Player) human, ESAdvancement.LIGHT_AS_A_FEATHER, "boots");
			}
			return result;
		}

		return null;
	}

}
