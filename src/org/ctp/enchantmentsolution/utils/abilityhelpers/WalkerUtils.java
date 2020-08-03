package org.ctp.enchantmentsolution.utils.abilityhelpers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Levelled;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.crashapi.nms.PacketNMS;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.*;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;

public class WalkerUtils {

	private static ArrayList<WalkerBlock> BLOCKS = new ArrayList<WalkerBlock>();
	private static int TICK;

	public static boolean hasBlock(Block block) {
		for(WalkerBlock walker: BLOCKS)
			if (walker.getBlock().equals(block)) return true;
		return false;
	}

	public static void updateBlocks(Player player, ItemStack boots, Location loc, Enchantment enchantment,
	List<Material> checkMaterial, Material replaceMaterial, String metadata) {
		int level = EnchantmentUtils.getLevel(boots, enchantment);
		int radius = 1 + level;
		for(int x = -radius; x <= radius; x++)
			for(int z = -radius; z <= radius; z++) {
				if (Math.abs(x) + Math.abs(z) > radius + 1) continue;
				Location blockLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
				Block block = blockLoc.getBlock();
				BlockState previousState = block.getState();
				if (checkMaterial.contains(block.getType())) {
					if (block.getBlockData() instanceof Levelled) {
						Levelled levelled = (Levelled) block.getBlockData();
						if (levelled.getLevel() != 0) continue;
					}
					if (!hasBlock(block)) if (enchantment == RegisterEnchantments.MAGMA_WALKER) {
						block.setType(replaceMaterial);
						WalkerBlock walker = new WalkerBlock(enchantment, block, checkMaterial.get(0), TICK);
						MagmaWalkerBlockEvent magmaWalker = new MagmaWalkerBlockEvent(block, previousState, player, level);
						Bukkit.getPluginManager().callEvent(magmaWalker);

						if (!magmaWalker.isCancelled()) {
							BLOCKS.add(walker);
							setBlockMeta(walker);
							if (block.getWorld().getEnvironment() == Environment.THE_END) AdvancementUtils.awardCriteria(player, ESAdvancement.FLAME_KEEPER, "flame");
						} else
							block.setType(checkMaterial.get(0));
					} else if (enchantment == RegisterEnchantments.VOID_WALKER) {
						block.setType(replaceMaterial);
						WalkerBlock walker = new WalkerBlock(enchantment, block, checkMaterial.get(0), TICK);
						VoidWalkerBlockEvent voidWalker = new VoidWalkerBlockEvent(block, previousState, player, level);
						Bukkit.getPluginManager().callEvent(voidWalker);

						if (!voidWalker.isCancelled()) {
							BLOCKS.add(walker);
							setBlockMeta(walker);
						} else
							block.setType(checkMaterial.get(0));
					}
				}
			}
	}

	private static void setBlockMeta(WalkerBlock block) {
		int packetID = -100;
		if (block.getBlock().hasMetadata("packet_id")) for(MetadataValue value: block.getBlock().getMetadata("packet_id"))
			if (value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
				packetID = value.asInt();
				break;
			}
		if (packetID == -100) packetID = PacketNMS.addParticle(block.getBlock(), block.getDamage().getDamage());
		else
			packetID = PacketNMS.updateParticle(block.getBlock(), block.getDamage().getDamage(), packetID);
		if (block.getDamage() == DamageState.BREAK) {
			block.getBlock().removeMetadata(block.getMeta(), EnchantmentSolution.getPlugin());
			block.getBlock().removeMetadata("packet_id", EnchantmentSolution.getPlugin());
		} else {
			block.getBlock().setMetadata(block.getMeta(), new FixedMetadataValue(EnchantmentSolution.getPlugin(), block.getDamage().getStage()));
			block.getBlock().setMetadata("packet_id", new FixedMetadataValue(EnchantmentSolution.getPlugin(), packetID));
		}
	}

	public static void updateBlocks() {
		for(int i = BLOCKS.size() - 1; i >= 0; i--) {
			WalkerBlock walker = BLOCKS.get(i);
			if (walker.getTick() == TICK && new Random().nextInt(4) == 0) if (walker.getEnchantment() == RegisterEnchantments.MAGMA_WALKER) {
				MagmaWalkerDamageBlockEvent event = new MagmaWalkerDamageBlockEvent(walker.getBlock(), walker.getNextDamage());
				Bukkit.getPluginManager().callEvent(event);

				if (!event.isCancelled()) {
					walker.nextDamage();
					setBlockMeta(walker);
					if (walker.getDamage() == DamageState.BREAK) {
						walker.getBlock().setType(walker.getReplaceType());
						BLOCKS.remove(i);
					}
				}
			} else if (walker.getEnchantment() == RegisterEnchantments.VOID_WALKER) {
				VoidWalkerDamageBlockEvent event = new VoidWalkerDamageBlockEvent(walker.getBlock(), walker.getNextDamage());
				Bukkit.getPluginManager().callEvent(event);

				if (!event.isCancelled()) {
					walker.nextDamage();
					setBlockMeta(walker);
					if (walker.getDamage() == DamageState.BREAK) {
						walker.getBlock().setType(walker.getReplaceType());
						BLOCKS.remove(i);
					}
				}
			}
		}
	}

	public static int getTick() {
		return TICK;
	}

	public static void incrementTick() {
		TICK++;
		if (TICK % 12 == 0) TICK = 0;
	}

	public static WalkerBlock getWalker(Block block) {
		for(WalkerBlock walker: BLOCKS)
			if (walker.getBlock().equals(block)) return walker;
		return null;
	}

	public static void addBlocks(List<WalkerBlock> blocks) {
		for(WalkerBlock block: blocks)
			if (!hasBlock(block.getBlock())) {
				BLOCKS.add(block);
				setBlockMeta(block);
			}
	}

	public static List<WalkerBlock> getBlocks() {
		List<WalkerBlock> blocks = new ArrayList<WalkerBlock>();
		for(WalkerBlock block: BLOCKS)
			blocks.add(block);
		return blocks;
	}
}
