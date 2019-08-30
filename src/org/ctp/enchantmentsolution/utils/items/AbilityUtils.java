package org.ctp.enchantmentsolution.utils.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.events.LagParticle;
import org.ctp.enchantmentsolution.events.SmelteryEvent;
import org.ctp.enchantmentsolution.events.TelepathyBreakEvent;
import org.ctp.enchantmentsolution.events.TelepathyDropEvent;
import org.ctp.enchantmentsolution.events.ExpShareEvent;
import org.ctp.enchantmentsolution.events.LagEvent;
import org.ctp.enchantmentsolution.nms.McMMO;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.LocationUtils;

public class AbilityUtils {

	private static List<Block> WAND_BLOCKS = new ArrayList<Block>();
	private static List<Block> HEIGHT_WIDTH_BLOCKS = new ArrayList<Block>();
	private static List<Material> CROPS = Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.NETHER_WART, 
			Material.BEETROOTS, Material.COCOA_BEANS);

	public static void breakBlockTelepathy(BlockBreakEvent event, Player player, ItemStack tool, 
			Block block, Collection<ItemStack> originalDrops) {
		breakBlockTelepathy(event, player, tool, block, originalDrops, new ArrayList<ItemStack>());
	}
	
	public static void breakBlockTelepathy(BlockBreakEvent event, Player player, ItemStack tool, 
			Block block, Collection<ItemStack> originalDrops, Collection<ItemStack> extraDrops) {
		if(Enchantments.hasEnchantment(tool, DefaultEnchantments.SMELTERY)) {
			switch(event.getBlock().getType()) {
				case IRON_ORE:
				case GOLD_ORE:
					event.setExpToDrop((int) (Math.random() * 3) + 1 + event.getExpToDrop());
					break;
				default:
					break;
			}
		}
		int newExp = event.getExpToDrop();
		if(Enchantments.hasEnchantment(tool, DefaultEnchantments.EXP_SHARE)) {
			newExp = setExp(event.getExpToDrop(), Enchantments.getLevel(tool, DefaultEnchantments.EXP_SHARE));
			ExpShareEvent expShare = new ExpShareEvent(player, event.getExpToDrop(), newExp);
			Bukkit.getPluginManager().callEvent(expShare);
			if(!expShare.isCancelled()) {
				newExp = expShare.getNewExp();
			}
		}
		
		TelepathyBreakEvent telepathy = new TelepathyBreakEvent(player, tool, block);
		Bukkit.getPluginManager().callEvent(telepathy);
		
		if(!telepathy.isCancelled()) {
			Collection<ItemStack> finalDrops = new ArrayList<ItemStack>();
			if (telepathy.applyFortune()) {
				finalDrops.addAll(Fortune.getFortuneItems(player, tool, block, originalDrops, telepathy.applySmeltery()));
			} else if (telepathy.applySilkTouch()
					&& SilkTouch.getSilkTouchItem(block, tool) != null) {
				finalDrops.add(SilkTouch.getSilkTouchItem(block, tool));
			} else {
				if (telepathy.applySmeltery()) {
					ItemStack smelted = Smeltery.getSmelteryItem(block, tool);
					if (smelted != null) {
						SmelteryEvent smeltery = new SmelteryEvent(player, tool, block, smelted, true);
						Bukkit.getPluginManager().callEvent(smeltery);
						if(!event.isCancelled()) {
							finalDrops.add(smeltery.getDrop());
						}
					}
				}
				if(finalDrops.size() == 0) {
					finalDrops.addAll(originalDrops);
				}
			}
			finalDrops.addAll(extraDrops);
			
			TelepathyDropEvent telepathyDrop = new TelepathyDropEvent(player, finalDrops, newExp, LocationUtils.offset(player.getLocation()));
			Bukkit.getPluginManager().callEvent(telepathyDrop);
			
			if(!telepathyDrop.willDrop()) {
				ItemUtils.giveItemsToPlayer(player, telepathyDrop.getDrops(), telepathyDrop.getDropLocation(), true, telepathyDrop.willDropNaturally());
				
				AbilityUtils.giveExperience(player, event.getExpToDrop());
				player.incrementStatistic(Statistic.MINE_BLOCK, block.getType());
				player.incrementStatistic(Statistic.USE_ITEM, tool.getType());
				McMMO.handleMcMMO(event, tool);
				damageItem(player, tool);
				block.setType(Material.AIR);
				event.setCancelled(true);
			}
		}
	}
	
	public static ItemStack getGoldDiggerItems(ItemStack item,
			Block brokenBlock) {

		if(brokenBlock.getBlockData() instanceof Ageable) {
			Ageable age = (Ageable) brokenBlock.getBlockData();
			if(CROPS.contains(brokenBlock.getType())) {
				if(age.getAge() != age.getMaximumAge()) {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
		int level = Enchantments.getLevel(item,
				DefaultEnchantments.GOLD_DIGGER);
		int amount = 0;
		while(level > 0) {
			double random = Math.random();
			double chance = 1.0 / 6.0;
			if(chance > random) {
				amount ++;
			}
			level --;
		}
		if(amount > 0) {
			return (new ItemStack(Material.GOLD_NUGGET, amount));
		}
		
		return null;
	}
	
	public static void dropExperience(Location loc, int amount) {
		if(amount > 0) {
			loc.getWorld().spawn(loc, ExperienceOrb.class).setExperience(amount);
		}
	}
	
	public static void giveExperience(Player player, int amount) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		PlayerInventory playerInv = player.getInventory();
		for(ItemStack i : playerInv.getArmorContents()) {
			if(i != null && Enchantments.hasEnchantment(i, Enchantment.MENDING)) {
				items.add(i);
			}
		}
		if(playerInv.getItemInMainHand() != null && Enchantments.hasEnchantment(playerInv.getItemInMainHand(), Enchantment.MENDING)) {
			items.add(playerInv.getItemInMainHand());
		}
		if(playerInv.getItemInOffHand() != null && Enchantments.hasEnchantment(playerInv.getItemInOffHand(), Enchantment.MENDING)) {
			items.add(playerInv.getItemInOffHand());
		}
		
		if(items.size() > 0) {
			Collections.shuffle(items);
			ItemStack item = items.get(0);
			int durability = DamageUtils.getDamage(item.getItemMeta());
			while(amount > 0 && durability > 0) {
				durability -= 2;
				amount--;
			}
			if(durability < 0) durability = 0;
			DamageUtils.setDamage(item, durability);
			if(amount > 0) {
				player.giveExp(amount);
			}
		} else {
			player.giveExp(amount);
		}
	}
	
	public static int setExp(int exp, int level) {
		int totalExp = exp;
		if(exp > 0){
			for(int i = 0; i < exp * level; i++){
				double chance = .50;
				double random = Math.random();
				if(chance > random){
					totalExp++;
				}
			}
		}
		return totalExp;
	}
	
	public static void createEffects(Player player) {
		int random = (int) ((Math.random() * 5) + 2);
		
		List<LagParticle> particles = new ArrayList<LagParticle>();
		
		for(int i = 0; i < random; i++) {
			Particle particle = generateParticle();
			int numParticles = (int) ((Math.random() * 400) + 11);
			particles.add(new LagParticle(particle, numParticles));
		}
		LagEvent event = new LagEvent(player, particles, player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE);
		Bukkit.getPluginManager().callEvent(event);
		
		if(event.isCancelled()) return;
		
		for(LagParticle particle : event.getParticles()) {
			player.getWorld().spawnParticle(particle.getParticle(), event.getLocation(), particle.getNumParticles(), 0.5, 2, 0.5);
		}

		player.getWorld().playSound(event.getLocation(), event.getSound(), 1, 1);
		AdvancementUtils.awardCriteria(player, ESAdvancement.LAAAGGGGGG, "lag");
	}
	
	private static Particle generateParticle() {
		Particle particle = null;
		int tries = 0;
		while(particle == null && tries < 10) {
			int particleType = (int) (Math.random() * Particle.values().length);
			particle = Particle.values()[particleType];
			if(!particle.getDataType().isAssignableFrom(Void.class)) {
				particle = null;
			}
			
		}
		return particle;
	}
	
	public static List<DamageCause> getContactCauses() {
		return Arrays.asList(DamageCause.BLOCK_EXPLOSION, DamageCause.CONTACT, DamageCause.CUSTOM, DamageCause.ENTITY_ATTACK,
				DamageCause.ENTITY_EXPLOSION, DamageCause.ENTITY_SWEEP_ATTACK, DamageCause.LIGHTNING, DamageCause.PROJECTILE, DamageCause.THORNS);
	}
	
	public static int getExhaustionCurse(Player player) {
		int exhaustionCurse = 0;
		for(ItemStack item : player.getInventory().getArmorContents()) {
			if(item != null && Enchantments.hasEnchantment(item, DefaultEnchantments.CURSE_OF_EXHAUSTION)){
				exhaustionCurse += Enchantments.getLevel(item, DefaultEnchantments.CURSE_OF_EXHAUSTION);
			}
		}
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		if(mainHand != null && Enchantments.hasEnchantment(mainHand, DefaultEnchantments.CURSE_OF_EXHAUSTION)){
			exhaustionCurse += Enchantments.getLevel(mainHand, DefaultEnchantments.CURSE_OF_EXHAUSTION);
		}
		ItemStack offHand = player.getInventory().getItemInOffHand();
		if(offHand != null && Enchantments.hasEnchantment(offHand, DefaultEnchantments.CURSE_OF_EXHAUSTION)){
			exhaustionCurse += Enchantments.getLevel(offHand, DefaultEnchantments.CURSE_OF_EXHAUSTION);
		}
		return exhaustionCurse;
	}
	
	public static ItemStack damageItem(HumanEntity player, ItemStack item) {
		return damageItem(player, item, 1.0D, 1.0D);
	}
	
	public static ItemStack damageItem(HumanEntity player, ItemStack item, double damage) {
		return damageItem(player, item, damage, 1.0D);
	}
	
	public static ItemStack damageItem(HumanEntity player, ItemStack item, double damage, double extraChance) {
		if (player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR))
			return null;
		int numBreaks = 0;
		int unbreaking = Enchantments.getLevel(item, Enchantment.DURABILITY);
		for(int i = 0; i < damage; i++) {
			double chance = (1.0D) / (unbreaking + extraChance);
			double random = Math.random();
			if(chance > random) {
				numBreaks ++;
			}
		};
		if (item.getItemMeta().isUnbreakable()) numBreaks = 0;
		if (numBreaks > 0) {
			DamageUtils.setDamage(item, DamageUtils.getDamage(item.getItemMeta()) + numBreaks);
			if (DamageUtils.getDamage(item.getItemMeta()) > item.getType().getMaxDurability()) {
				ItemStack deadItem = item.clone();
				player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
				if(player instanceof Player) {
					Player p = (Player) player;
					p.incrementStatistic(Statistic.BREAK_ITEM, item.getType());
				}
				return deadItem;
			}
		}
		return item;
	}
	
	public static float getExhaustion(Player player) {
		return player.getFoodLevel() * 4 + player.getSaturation() * 4 - player.getExhaustion();
	}

	public static List<Block> getWandBlocks() {
		return WAND_BLOCKS;
	}
	
	public static void addWandBlock(Block block) {
		WAND_BLOCKS.add(block);
	}

	public static void removeWandBlock(Block block) {
		WAND_BLOCKS.remove(block);
	}

	public static List<Block> getHeightWidthBlocks() {
		return HEIGHT_WIDTH_BLOCKS;
	}
	
	public static void addHeightWidthBlock(Block block) {
		HEIGHT_WIDTH_BLOCKS.add(block);
	}

	public static void removeHeightWidthBlock(Block block) {
		HEIGHT_WIDTH_BLOCKS.remove(block);
	}
}
