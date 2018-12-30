package org.ctp.enchantmentsolution.listeners.abilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;

public class VoidWalkerListener implements Listener, Runnable{
	
	public static List<Block> BLOCKS = new ArrayList<Block>();
	private static List<VoidWalkerPlayer> HAS_VOID_WALKER = new ArrayList<VoidWalkerPlayer>();
	private static Map<Player, Integer> DELAYS = new HashMap<Player, Integer>();

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event){
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.VOID_WALKER)) return;
		Player player = event.getPlayer();
		Location loc = player.getLocation();
		if(player.isFlying() || player.isGliding() || player.isInsideVehicle()){
			return;
		}
		ItemStack boots = player.getInventory().getBoots();
		if((event.getTo().getX() != loc.getX() && event.getTo().getZ() != loc.getZ()) && !(DELAYS.get(player) != null && DELAYS.get(player) > 7)){
			if(boots != null){
				if(Enchantments.hasEnchantment(boots, DefaultEnchantments.VOID_WALKER)){
					int radius = 1 + Enchantments.getLevel(boots, DefaultEnchantments.VOID_WALKER);
					for(int x = -radius; x <= radius; x++){
						for(int z = -radius; z <= radius; z++){
							if(Math.abs(x) + Math.abs(z) > radius + 1) continue;
							Location airLoc = new Location(loc.getWorld(), loc.getBlockX() + x, loc.getBlockY() - 1, loc.getBlockZ() + z);
							Block air = airLoc.getBlock();
							if((air.getType().equals(Material.AIR))){
								air.setMetadata("VoidWalker", new FixedMetadataValue(EnchantmentSolution.PLUGIN, new Integer(4)));
								air.setType(Material.OBSIDIAN);
								BLOCKS.add(air);
							}else if(air.getType().equals(Material.OBSIDIAN)){
								List<MetadataValue> values = air.getMetadata("VoidWalker");
								if(values != null){
									for(MetadataValue value : values){
										if(value.asInt() > 0){
											air.setMetadata("VoidWalker", new FixedMetadataValue(EnchantmentSolution.PLUGIN, new Integer(4)));
										}
									}
								}
							}
						}
					}
				}
			}
		} else if ((event.getTo().getBlockY() != loc.getBlockY())) {
			if(boots != null){
				if(Enchantments.hasEnchantment(boots, DefaultEnchantments.VOID_WALKER)){
					int radius = 1 + Enchantments.getLevel(boots, DefaultEnchantments.VOID_WALKER);
					for(int x = -radius; x <= radius; x++){
						for(int z = -radius; z <= radius; z++){
							if(Math.abs(x) + Math.abs(z) > radius + 1) continue;
							Location airLoc = new Location(loc.getWorld(), loc.getBlockX() + x, event.getTo().getBlockY() - 1, loc.getBlockZ() + z);
							Block air = airLoc.getBlock();
							if((air.getType().equals(Material.AIR))){
								air.setMetadata("VoidWalker", new FixedMetadataValue(EnchantmentSolution.PLUGIN, new Integer(4)));
								air.setType(Material.OBSIDIAN);
								BLOCKS.add(air);
							}else if(air.getType().equals(Material.OBSIDIAN)){
								List<MetadataValue> values = air.getMetadata("VoidWalker");
								if(values != null){
									for(MetadataValue value : values){
										if(value.asInt() > 0){
											air.setMetadata("VoidWalker", new FixedMetadataValue(EnchantmentSolution.PLUGIN, new Integer(4)));
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.VOID_WALKER)) return;
		Bukkit.getScheduler().runTaskLater(EnchantmentSolution.PLUGIN, new Runnable(){
			
			@Override
			public void run() {
				Player player = event.getPlayer();
				ItemStack boots = player.getInventory().getBoots();
				if(boots != null) {
					boots = null;
				}
				VoidWalkerPlayer voidWalkerPlayer = new VoidWalkerPlayer(player, boots);
				
				HAS_VOID_WALKER.add(voidWalkerPlayer);
			}
		}, 0l);
		
	}
	
	@EventHandler
	public void onPlayerLogout(PlayerQuitEvent event) {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.VOID_WALKER)) return;
		VoidWalkerPlayer remove = null;
		for(VoidWalkerPlayer voidWalkerPlayer : HAS_VOID_WALKER) {
			if(voidWalkerPlayer.getPlayer().getUniqueId().toString().equals(event.getPlayer().getUniqueId().toString())) {
				voidWalkerPlayer.setBoots(null);
				remove = voidWalkerPlayer;
				break;
			}
		}
		if(remove != null) {
			HAS_VOID_WALKER.remove(remove);
		}
	}

	@Override
	public void run() {
		if(!DefaultEnchantments.isEnabled(DefaultEnchantments.VOID_WALKER)) return;
		for(VoidWalkerPlayer voidWalkerPlayer : HAS_VOID_WALKER) {
			Player player = voidWalkerPlayer.getPlayer();
			if(player != null && Bukkit.getOnlinePlayers().contains(player)) {
				ItemStack boots = player.getInventory().getBoots();
				voidWalkerPlayer.setBoots(boots);
				if(voidWalkerPlayer.hasVoidWalker()) {
					if(DELAYS.containsKey(player)) {
						if (DELAYS.get(player) == null || DELAYS.get(player) - 1 <= 0) {
							DELAYS.remove(player);
						} else {
							DELAYS.put(player, DELAYS.get(player) - 1);
						}
					} else if(player.isSneaking()){
						DELAYS.put(player, 10);
						for(int i = BLOCKS.size() - 1; i >= 0; i--){
							Block block = BLOCKS.get(i);
							if(block.getLocation().getBlockY() > 1) {
								List<MetadataValue> values = block.getMetadata("VoidWalker");
								if(values != null){
									int radius = 1 + Enchantments.getLevel(boots, DefaultEnchantments.VOID_WALKER);
									List<Block> blocks = new ArrayList<Block>();
									for(int x = -radius; x <= radius; x++){
										for(int z = -radius; z <= radius; z++){
											if(Math.abs(x) + Math.abs(z) > radius + 1) continue;
											blocks.add(player.getLocation().getBlock().getRelative(x, -1, z));
										}
									}
									if(blocks.contains(block)) {
										block.setType(Material.AIR);
										BLOCKS.remove(i);
										Block lower = block.getRelative(BlockFace.DOWN);
										if((lower.getType().equals(Material.AIR))){
											lower.setMetadata("VoidWalker", new FixedMetadataValue(EnchantmentSolution.PLUGIN, new Integer(4)));
											lower.setType(Material.OBSIDIAN);
											BLOCKS.add(lower);
										}
									}
								}
							}
						}
					}
				}
			} else {
				HAS_VOID_WALKER.remove(voidWalkerPlayer);
			}
		}
	}
	
	protected class VoidWalkerPlayer{
		
		private Player player;
		private ItemStack boots;
		
		public VoidWalkerPlayer(Player player, ItemStack boots) {
			this.player = player;
			this.setBoots(boots);
		}

		public Player getPlayer() {
			return player;
		}

		public ItemStack getBoots() {
			return boots;
		}
		
		public void setBoots(ItemStack boots) {
			this.boots = boots;
		}
		
		public boolean hasVoidWalker() {
			if(boots == null) {
				return false;
			}
			return Enchantments.hasEnchantment(boots, DefaultEnchantments.VOID_WALKER);
		}
		
	}
}
