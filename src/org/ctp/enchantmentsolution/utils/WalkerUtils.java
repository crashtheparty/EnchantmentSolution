package org.ctp.enchantmentsolution.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.events.blocks.DamageState;
import org.ctp.enchantmentsolution.nms.PacketNMS;
import org.ctp.enchantmentsolution.utils.items.ItemUtils;

public class WalkerUtils {

	private static Map<Enchantment, List<Block>> BLOCKS = new HashMap<Enchantment, List<Block>>();
	private static Map<Player, Integer> DELAYS = new HashMap<Player, Integer>();

	public static void updateBlocks(Player player, ItemStack boots, Location loc, Enchantment enchantment,
	List<Material> checkMaterial, Material replaceMaterial, String metadata) {
		int radius = 1 + ItemUtils.getLevel(boots, enchantment);
		for(int x = -radius; x <= radius; x++) {
			for(int z = -radius; z <= radius; z++) {
				if (Math.abs(x) + Math.abs(z) > radius + 1) {
					continue;
				}
				Location blockLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() - 1,
				loc.getBlockZ() + z);
				Block block = blockLoc.getBlock();
				if (checkMaterial.contains(block.getType())) {
					if (block.getBlockData() instanceof Levelled) {
						Levelled level = (Levelled) block.getBlockData();
						if (level.getLevel() != 0) {
							continue;
						}
					}
					addBlockMetadata(block, enchantment, metadata, DamageState.getDefaultMeta());
					block.setType(replaceMaterial);
					List<Block> blocks = BLOCKS.get(enchantment);
					if (blocks == null) {
						blocks = new ArrayList<Block>();
					}
					blocks.add(block);
					BLOCKS.put(enchantment, blocks);
					if (enchantment == RegisterEnchantments.MAGMA_WALKER) {
						if (block.getWorld().getEnvironment() == Environment.THE_END) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.FLAME_KEEPER, "flame");
						}
					}
				} else if (block.getType() == replaceMaterial) {
					List<MetadataValue> values = block.getMetadata(metadata);
					if (values != null) {
						for(MetadataValue value: values) {
							if (value.asInt() >= 0) {
								updateBlockMetadata(block, enchantment, metadata, value, value.asInt());
							}
						}
					}
				}
			}
		}
	}

	public static void addBlockMetadata(Block block, Enchantment enchantment, String metadata,
	MetadataValue metaValue) {
		int packetID = -100;
		if (block.hasMetadata("packet_id")) {
			for(MetadataValue value: block.getMetadata("packet_id")) {
				if (value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
					packetID = value.asInt();
					break;
				}
			}
		}
		if (packetID == -100) {
			packetID = PacketNMS.addParticle(block, DamageState.getStage(metaValue.asInt()));
		}
		block.setMetadata(metadata, metaValue);
		block.setMetadata("packet_id", new FixedMetadataValue(EnchantmentSolution.getPlugin(), packetID));
	}

	public static void updateBlockMetadata(Block block, Enchantment enchantment, String metadata,
	MetadataValue metaValue, int data) {
		int packetID = -1;
		if (block.hasMetadata("packet_id")) {
			for(MetadataValue value: block.getMetadata("packet_id")) {
				if (value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
					packetID = value.asInt();
					break;
				}
			}
		}
		if (packetID == -1) {
			packetID = PacketNMS.addParticle(block, DamageState.getStage(data));
		} else {
			PacketNMS.updateParticle(block, DamageState.getStage(data), packetID);
		}
		block.setMetadata(metadata, new FixedMetadataValue(EnchantmentSolution.getPlugin(), data));
		block.setMetadata("packet_id", new FixedMetadataValue(EnchantmentSolution.getPlugin(), packetID));
	}

	public static void removeBlockMetadata(Block block, Enchantment enchantment, String metadata) {
		int packetID = -1;
		if (block.hasMetadata("packet_id")) {
			for(MetadataValue value: block.getMetadata("packet_id")) {
				if (value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
					packetID = value.asInt();
					break;
				}
			}
		}
		if (packetID > -1) {
			PacketNMS.updateParticle(block, 10, packetID);
		}
		block.removeMetadata(metadata, EnchantmentSolution.getPlugin());
		block.removeMetadata("packet_id", EnchantmentSolution.getPlugin());
	}

	public static void updateBlocks(Enchantment enchantment, String metadata, List<Material> checkMaterial,
	Material replaceMaterial) {
		if (!RegisterEnchantments.isEnabled(enchantment)) {
			return;
		}
		try {
			List<Block> blocks = BLOCKS.get(enchantment);
			if (blocks == null) {
				BLOCKS.put(enchantment, new ArrayList<Block>());
				return;
			} else if (blocks.size() == 0) {
				return;
			}
			for(int i = blocks.size() - 1; i >= 0; i--) {
				Block block = blocks.get(i);
				if (!block.getWorld().isChunkLoaded(block.getX() / 16, block.getZ() / 16)) {
					continue;
				}
				List<MetadataValue> values = block.getMetadata(metadata);
				if (values != null) {
					for(MetadataValue value: values) {
						if (value.asInt() >= 0) {
							boolean update = true;
							start: for(Player player: Bukkit.getOnlinePlayers()) {
								ItemStack boots = player.getInventory().getBoots();
								if (boots != null && update) {
									if (ItemUtils.hasEnchantment(boots, enchantment)) {
										int radius = 1 + ItemUtils.getLevel(boots, enchantment);
										for(int x = -radius; x <= radius; x++) {
											for(int z = -radius; z <= radius; z++) {
												if (Math.abs(x) + Math.abs(z) > radius + 1) {
													continue;
												}
												if (player.getLocation().getBlock().getRelative(x, -1, z)
												.equals(block)) {
													update = false;
													break start;
												}
											}
										}
									}
								}
							}
							if (update) {
								if (value.asInt() <= 0) {
									removeBlockMetadata(block, enchantment, metadata);
									block.setType(checkMaterial.get(0));
									blocks.remove(i);
								} else {
									updateBlockMetadata(block, enchantment, metadata, value, value.asInt() - 1);
								}
							} else {
								updateBlockMetadata(block, enchantment, metadata, value, value.asInt());
							}
						} else {
							removeBlockMetadata(block, enchantment, metadata);
							block.setType(checkMaterial.get(0));
							blocks.remove(i);
						}
					}
				} else {
					block.setType(checkMaterial.get(0));
					blocks.remove(i);
				}
			}
			BLOCKS.put(enchantment, blocks);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static Map<Enchantment, List<Block>> getBlocks() {
		return BLOCKS;
	}

	public static Map<Player, Integer> getDelays() {
		return DELAYS;
	}
}
