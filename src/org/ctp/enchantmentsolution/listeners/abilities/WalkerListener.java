package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Levelled;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.listeners.abilities.helpers.WalkerHelper;
import org.ctp.enchantmentsolution.nms.PacketNMS;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;

public class WalkerListener extends EnchantmentListener implements Runnable{

	private static Map<Enchantment, List<Block>> BLOCKS = new HashMap<Enchantment, List<Block>>();
	private static Map<Player, Integer> DELAYS = new HashMap<Player, Integer>();
	private static int run = 0;
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		if(!canRun(event, false, DefaultEnchantments.VOID_WALKER, DefaultEnchantments.MAGMA_WALKER)) return;
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		if(player.isFlying() || player.isGliding() || player.isInsideVehicle()){
			return;
		}
		ItemStack boots = player.getInventory().getBoots();
		if(boots != null && Enchantments.hasEnchantment(boots, DefaultEnchantments.VOID_WALKER)) {
			WalkerHelper voidWalker = new WalkerHelper(DefaultEnchantments.VOID_WALKER);
			if (!DefaultEnchantments.isEnabled(voidWalker.getEnchantment())) return;
			if (DELAYS.containsKey(player)) return;
			if (LocationUtils.isLocationDifferent(event.getFrom(), event.getTo(), false)) {
				updateBlocks(player, boots, loc, voidWalker);
			} else if (LocationUtils.isLocationDifferent(event.getFrom(), event.getTo(), true)) {
				updateBlocks(player, boots, event.getTo(), voidWalker);
			}
		} else if(boots != null && Enchantments.hasEnchantment(boots, DefaultEnchantments.MAGMA_WALKER)
				&& LocationUtils.isLocationDifferent(event.getFrom(), event.getTo(), false)) {
			WalkerHelper magmaWalker = new WalkerHelper(DefaultEnchantments.MAGMA_WALKER);
			if(!DefaultEnchantments.isEnabled(magmaWalker.getEnchantment())) return;
			updateBlocks(player, boots, loc, magmaWalker);
		}
	}

	public static List<Block> getBlocks(Enchantment enchantment) {
		if(BLOCKS != null && BLOCKS.get(enchantment) != null) {
			return BLOCKS.get(enchantment);
		}
		return Arrays.asList();
	}

	public static void setBlocks(Enchantment enchantment, List<Block> blocks) {
		BLOCKS.put(enchantment, blocks);
	}
	
	private void updateBlocks(Player player, ItemStack boots, Location loc, WalkerHelper helper) {
		int radius = 1 + Enchantments.getLevel(boots, helper.getEnchantment());
		for(int x = -radius; x <= radius; x++){
			for(int z = -radius; z <= radius; z++){
				if(Math.abs(x) + Math.abs(z) > radius + 1) continue;
				Location blockLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
				Block block = blockLoc.getBlock();
				if(helper.getTypes().contains(block.getType())){
					if(block.getBlockData() instanceof Levelled) {
						Levelled level = (Levelled) block.getBlockData();
						if(level.getLevel() != 0) continue;
					}
					addBlockMetadata(block, helper);
					block.setType(helper.getReplace());
					List<Block> blocks = BLOCKS.get(helper.getEnchantment());
					blocks.add(block);
					BLOCKS.put(helper.getEnchantment(), blocks);
					if(helper.getEnchantment() == DefaultEnchantments.MAGMA_WALKER) {
						if(block.getWorld().getEnvironment() == Environment.THE_END) {
							AdvancementUtils.awardCriteria(player, ESAdvancement.FLAME_KEEPER, "flame");
						}
					}
				}else if(block.getType() == helper.getReplace()){
					List<MetadataValue> values = block.getMetadata(helper.getMetadata());
					if(values != null){
						for(MetadataValue value : values){
							if(value.asInt() >= 0){
								updateBlockMetadata(block, helper, helper.getValue().asInt());
							}
						}
					}
				}
			}
		}
	}
	
	private void addBlockMetadata(Block block, WalkerHelper helper) {
		int packetID = -1;
		if(block.hasMetadata("packet_id")) {
			for(MetadataValue value : block.getMetadata("packet_id")){
				if(value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
					packetID = value.asInt();
					break;
				}
			}
		}
		if(packetID == -1) {
			packetID = PacketNMS.addParticle(block, helper.getStage(helper.getValue().asInt()));
		}
		block.setMetadata(helper.getMetadata(), helper.getValue());
		block.setMetadata("packet_id", new FixedMetadataValue(EnchantmentSolution.getPlugin(), packetID));
	}
	
	private void updateBlockMetadata(Block block, WalkerHelper helper, int data) {
		int packetID = -1;
		if(block.hasMetadata("packet_id")) {
			for(MetadataValue value : block.getMetadata("packet_id")){
				if(value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
					packetID = value.asInt();
					break;
				}
			}
		}
		if(packetID == -1) {
			packetID = PacketNMS.addParticle(block, helper.getStage(data));
		} else {
			PacketNMS.updateParticle(block, helper.getStage(data), packetID);
		}
		block.setMetadata(helper.getMetadata(), new FixedMetadataValue(EnchantmentSolution.getPlugin(), data));
		block.setMetadata("packet_id", new FixedMetadataValue(EnchantmentSolution.getPlugin(), packetID));
	}
	
	private void removeBlockMetadata(Block block, WalkerHelper helper) {
		int packetID = -1;
		if(block.hasMetadata("packet_id")) {
			for(MetadataValue value : block.getMetadata("packet_id")){
				if(value.getOwningPlugin().equals(EnchantmentSolution.getPlugin())) {
					packetID = value.asInt();
					break;
				}
			}
		}
		if(packetID > -1) {
			PacketNMS.updateParticle(block, 10, packetID);
		}
		block.removeMetadata(helper.getMetadata(), EnchantmentSolution.getPlugin());
		block.removeMetadata("packet_id", EnchantmentSolution.getPlugin());
	}
	
	private void updateBlocks(WalkerHelper helper) {
		if(!DefaultEnchantments.isEnabled(helper.getEnchantment())) return;
		try {
			List<Block> blocks = BLOCKS.get(helper.getEnchantment());
			if(blocks == null) {
				BLOCKS.put(helper.getEnchantment(), new ArrayList<Block>());
				return;
			} else if (blocks.size() == 0) {
				return;
			}
			for(int i = blocks.size() - 1; i >= 0; i--) {
				Block block = blocks.get(i);
				if(!block.getWorld().isChunkLoaded(block.getX()/16, block.getZ()/16)) {
					continue;
				}
				List<MetadataValue> values = block.getMetadata(helper.getMetadata());
				if(values != null){
					for(MetadataValue value : values){
						if(value.asInt() >= 0){
							boolean update = true;
							start:
							for(Player player : Bukkit.getOnlinePlayers()){
								ItemStack boots = player.getInventory().getBoots();
								if(boots != null && update){
									if(Enchantments.hasEnchantment(boots, helper.getEnchantment())){
										int radius = 1 + Enchantments.getLevel(boots, helper.getEnchantment());
										for(int x = -radius; x <= radius; x++){
											for(int z = -radius; z <= radius; z++){
												if(Math.abs(x) + Math.abs(z) > radius + 1) continue;
												if(player.getLocation().getBlock().getRelative(x, -1, z).equals(block)) {
													update = false;
													break start;
												}
											}
										}
									}
								}
							}
							if (update) {
								if(value.asInt() <= 0) {
									removeBlockMetadata(block, helper);
									block.setType(helper.getTypes().get(0));
									blocks.remove(i);
								} else {
									updateBlockMetadata(block, helper, value.asInt() - 1);
								}
							} else {
								updateBlockMetadata(block, helper, helper.getValue().asInt());
							}
						} else{
							removeBlockMetadata(block, helper);
							block.setType(helper.getTypes().get(0));
							blocks.remove(i);
						}
					}
				} else {
					block.setType(helper.getTypes().get(0));
					blocks.remove(i);
				}
			}
			BLOCKS.put(helper.getEnchantment(), blocks);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		if(run == 0) {
			updateBlocks(new WalkerHelper(DefaultEnchantments.MAGMA_WALKER));
			updateBlocks(new WalkerHelper(DefaultEnchantments.VOID_WALKER));
		}
		try {
			for(Player player : Bukkit.getOnlinePlayers()) {
				ItemStack boots = player.getInventory().getBoots();
				if(boots != null && Enchantments.hasEnchantment(boots, DefaultEnchantments.VOID_WALKER)) {
					if(DELAYS.containsKey(player)) {
						if (DELAYS.get(player) == null || DELAYS.get(player) - 1 <= 0) {
							DELAYS.remove(player);
						} else {
							DELAYS.put(player, DELAYS.get(player) - 1);
						}
					} else if(player.isSneaking()){
						DELAYS.put(player, 10);
						List<Block> blocks = BLOCKS.get(DefaultEnchantments.VOID_WALKER);
						WalkerHelper helper = new WalkerHelper(DefaultEnchantments.VOID_WALKER);
						for(int i = blocks.size() - 1; i >= 0; i--){
							Block block = blocks.get(i);
							if(block.getLocation().getBlockY() > 1) {
								List<MetadataValue> values = block.getMetadata("VoidWalker");
								if(values != null){
									int radius = 1 + Enchantments.getLevel(boots, DefaultEnchantments.VOID_WALKER);
									List<Block> bs = new ArrayList<Block>();
									for(int x = -radius; x <= radius; x++){
										for(int z = -radius; z <= radius; z++){
											if(Math.abs(x) + Math.abs(z) > radius + 1) continue;
											bs.add(player.getLocation().getBlock().getRelative(x, -1, z));
										}
									}
									if(bs.contains(block)) {
										removeBlockMetadata(block, helper);
										block.setType(Material.AIR);
										blocks.remove(i);
										Block lower = block.getRelative(BlockFace.DOWN);
										if((lower.getType().equals(Material.AIR))){
											addBlockMetadata(lower, helper);
											lower.setType(Material.OBSIDIAN);
											blocks.add(lower);
										}
									}
								}
							}
						}
						BLOCKS.put(DefaultEnchantments.VOID_WALKER, blocks);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		run++;
		if(run / 20 != 0) {
			run = 0;
		}
	}

}
