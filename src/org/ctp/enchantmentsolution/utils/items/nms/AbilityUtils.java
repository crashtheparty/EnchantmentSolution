package org.ctp.enchantmentsolution.utils.items.nms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.Enchantments;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.items.DamageUtils;
import org.ctp.enchantmentsolution.utils.items.nms.fortune.Fortune_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.fortune.Fortune_v1_14;
import org.ctp.enchantmentsolution.utils.items.nms.silktouch.SilkTouch_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.silktouch.SilkTouch_v1_14;
import org.ctp.enchantmentsolution.utils.items.nms.smeltery.Smeltery_v1_13;
import org.ctp.enchantmentsolution.utils.items.nms.smeltery.Smeltery_v1_14;

public class AbilityUtils {

	private static List<Block> WAND_BLOCKS = new ArrayList<Block>();
	private static List<Block> HEIGHT_WIDTH_BLOCKS = new ArrayList<Block>();
	private static List<Material> CROPS = Arrays.asList(Material.WHEAT, Material.CARROTS, Material.POTATOES, Material.NETHER_WART, 
			Material.BEETROOTS, Material.COCOA_BEANS);
	
	public static ItemStack getSmelteryItem(Block block, ItemStack item) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return Smeltery_v1_13.getSmelteryItem(block, item);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return Smeltery_v1_14.getSmelteryItem(block, item);
		}
		return null;
	}
	
	public static ItemStack getSilkTouchItem(Block block, ItemStack item){
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return SilkTouch_v1_13.getSilkTouchItem(block, item);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return SilkTouch_v1_14.getSilkTouchItem(block, item);
		}
		return null;
	}

	public static Collection<ItemStack> getFortuneItems(ItemStack item,
			Block brokenBlock, Collection<ItemStack> priorItems) {
		switch(EnchantmentSolution.getPlugin().getBukkitVersion().getVersionNumber()) {
		case 1:
		case 2:
		case 3:
			return Fortune_v1_13.getFortuneItems(item, brokenBlock, priorItems);
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			return Fortune_v1_14.getFortuneItems(item, brokenBlock, priorItems);
		}
		return null;
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
		int numParticles = (int) ((Math.random() * 400) + 11);
		
		for(int i = 0; i < random; i++) {
			Particle particle = generateParticle();
			player.getWorld().spawnParticle(particle, player.getLocation(), numParticles, 0.5, 2, 0.5);
		}
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1, 1);
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
