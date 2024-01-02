package org.ctp.enchantmentsolution.interfaces.interact;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.FlashPlaceEvent;
import org.ctp.enchantmentsolution.events.blocks.FlashUpdateEvent;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.ActionIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.interact.UpdateLightEffect;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;

public class Flash extends UpdateLightEffect {

	public Flash() {
		super(RegisterEnchantments.FLASH, EnchantmentMultipleType.HIGHEST, EnchantmentItemLocation.HANDS, EventPriority.HIGHEST, 15, false, new InteractCondition[] { new ActionIsTypeCondition(false, Action.RIGHT_CLICK_BLOCK) });
	}

	@Override
	public UpdateLightResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		UpdateLightResult result = super.run(player, items, event);
		if (result.getLevel() == 0 || result.getBlock() == null) return null;

		ItemStack item = null;
		for(ItemStack i: items)
			if (player.getInventory().getItem(event.getHand()).equals(i)) item = i;
		if (item == null) return null;

		Block block = event.getClickedBlock();
		Block setBlock = result.getBlock();
		MatData light = new MatData("LIGHT");
		if (MatData.isAir(setBlock.getType())) {
			int lightLevel = result.getLightLevel();
			BlockState state = setBlock.getState();
			BlockData oldData = setBlock.getBlockData();
			setBlock.setType(light.getMaterial());
			BlockData newData = setBlock.getBlockData();
			((Levelled) newData).setLevel(lightLevel);
			setBlock.setBlockData(newData);
			FlashPlaceEvent flash = new FlashPlaceEvent(setBlock, state, block, item, player, true, event.getHand(), lightLevel);
			Bukkit.getPluginManager().callEvent(flash);

			if (flash.isCancelled()) {
				setBlock.setType(oldData.getMaterial());
				setBlock.setBlockData(oldData);
			} else {
				BlockPlaceEvent place = new BlockPlaceEvent(setBlock, state, block, item, player, true, event.getHand());
				Bukkit.getPluginManager().callEvent(place);

				if (place.isCancelled()) {
					setBlock.setType(oldData.getMaterial());
					setBlock.setBlockData(oldData);
				} else {
					DamageUtils.damageItem(player, item, 1, 2);
					setBlock.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, setBlock.getLocation().clone().add(0.5, 0.5, 0.5), lightLevel, 0.2, 0, 0.2);
					setBlock.getWorld().playSound(setBlock.getLocation(), Sound.ITEM_DYE_USE, 0.5f, 1);
					BlockFace[] faces = new BlockFace[] { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST, BlockFace.UP, BlockFace.DOWN };
					for(BlockFace face: faces)
						if (setBlock.getRelative(face).getType() == Material.TORCH) AdvancementUtils.awardCriteria(player, ESAdvancement.USELESS, "torch");
				}
				return new UpdateLightResult(result.getLevel(), setBlock, lightLevel, 0);
			}
		} else if (light.getMaterial() == setBlock.getType()) {
			BlockData data = setBlock.getBlockData();
			int lightLevel = result.getPreviousLevel();
			int newLightLevel = result.getLightLevel() % 16;
			FlashUpdateEvent flash = new FlashUpdateEvent(setBlock, data, player, lightLevel, newLightLevel);
			Bukkit.getPluginManager().callEvent(flash);

			if (!flash.isCancelled()) {
				BlockData oldData = setBlock.getBlockData();
				setBlock.setType(light.getMaterial());
				BlockData newData = setBlock.getBlockData();
				((Levelled) newData).setLevel(newLightLevel);
				setBlock.setBlockData(newData);
				BlockPhysicsEvent physics = new BlockPhysicsEvent(setBlock, data);
				Bukkit.getPluginManager().callEvent(physics);

				if (physics.isCancelled()) {
					setBlock.setType(oldData.getMaterial());
					setBlock.setBlockData(oldData);
				} else {
					DamageUtils.damageItem(player, item, 1, 2);
					if (newLightLevel > 0) setBlock.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, setBlock.getLocation().clone().add(0.2, 0, 0.2), newLightLevel, 0.5, 0, 0.5);
					else
						setBlock.getWorld().spawnParticle(Particle.FLAME, setBlock.getLocation(), 20, 0.5, 0, 0.5);
					setBlock.getWorld().playSound(setBlock.getLocation(), Sound.ITEM_DYE_USE, 0.5f, 1);
					return new UpdateLightResult(result.getLevel(), setBlock, lightLevel, newLightLevel);
				}
			}
		}
		return null;
	}

}
