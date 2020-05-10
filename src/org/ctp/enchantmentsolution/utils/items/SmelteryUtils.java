package org.ctp.enchantmentsolution.utils.items;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enums.BlockSound;
import org.ctp.enchantmentsolution.enums.ItemBreakType;
import org.ctp.enchantmentsolution.enums.MatData;
import org.ctp.enchantmentsolution.events.blocks.SmelteryEvent;
import org.ctp.enchantmentsolution.mcmmo.McMMOHandler;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.VersionUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.SmelteryMaterial;
import org.ctp.enchantmentsolution.utils.compatibility.JobsUtils;
import org.ctp.enchantmentsolution.utils.config.ConfigString;

public class SmelteryUtils {

	public static void handleSmeltery(BlockBreakEvent event, Player player, Block blockBroken, ItemStack item) {
		SmelteryMaterial smeltery = SmelteryUtils.getSmelteryItem(blockBroken, item);
		if (smeltery != null) {
			ItemStack smelted = smeltery.getSmelted();
			int experience = 0;
			boolean fortune = false;
			if (blockBroken.getType() == Material.IRON_ORE || blockBroken.getType() == Material.GOLD_ORE) {
				experience = (int) (Math.random() * 3) + 1;
				fortune = true;
			}
			SmelteryEvent smelteryEvent = new SmelteryEvent(blockBroken, player, smelted, smeltery.getToMaterial(), experience, fortune);
			Bukkit.getPluginManager().callEvent(smelteryEvent);

			if (!smelteryEvent.isCancelled()) {
				Block newBlock = smelteryEvent.getBlock();
				ItemStack afterSmeltery = smelteryEvent.getDrop();
				afterSmeltery.setType(smelteryEvent.getChangeTo());
				if (smelteryEvent.willFortune()) {
					afterSmeltery = FortuneUtils.getFortuneForSmeltery(afterSmeltery, item);
					if (afterSmeltery.getAmount() > 1 && afterSmeltery.getType() == Material.IRON_INGOT) AdvancementUtils.awardCriteria(player, ESAdvancement.IRONT_YOU_GLAD, "iron");
				}
				player.incrementStatistic(Statistic.MINE_BLOCK, smelteryEvent.getBlock().getType());
				player.incrementStatistic(Statistic.USE_ITEM, item.getType());
				McMMOHandler.handleMcMMO(event, item);
				if (EnchantmentSolution.getPlugin().isJobsEnabled()) JobsUtils.sendBlockBreakAction(event);
				DamageUtils.damageItem(player, item);
				ItemUtils.dropItem(afterSmeltery, newBlock.getLocation());
				Location loc = newBlock.getLocation().clone().add(0.5, 0.5, 0.5);
				AbilityUtils.dropExperience(loc, experience);
				if (ConfigString.USE_PARTICLES.getBoolean()) loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 20, newBlock.getBlockData());
				if (ConfigString.PLAY_SOUND.getBoolean()) {
					BlockSound sound = BlockSound.getSound(newBlock.getType());
					loc.getWorld().playSound(loc, sound.getSound(), sound.getVolume(newBlock.getType()), sound.getPitch(newBlock.getType()));
				}
				event.getBlock().setType(Material.AIR);
			}
		}
	}

	public static SmelteryEvent handleSmelteryTelepathy(Player player, Block blockBroken, ItemStack item) {
		SmelteryMaterial smeltery = SmelteryUtils.getSmelteryItem(blockBroken, item);
		if (smeltery != null) {
			ItemStack smelted = smeltery.getSmelted();
			int experience = 0;
			boolean fortune = false;
			if (blockBroken.getType() == Material.IRON_ORE || blockBroken.getType() == Material.GOLD_ORE) {
				experience = (int) (Math.random() * 3) + 1;
				fortune = true;
			}
			return new SmelteryEvent(blockBroken, player, smelted, smeltery.getToMaterial(), experience, fortune);
		}
		return null;
	}

	public static SmelteryMaterial getSmelteryItem(Block block, ItemStack item) {
		String material = null;
		ItemBreakType type = ItemBreakType.getType(item.getType());
		switch (block.getType().name()) {
			case "BASALT":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "POLISHED_BASALT";
				break;
			case "IRON_ORE":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "IRON_INGOT";
				break;
			case "GOLD_ORE":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "GOLD_INGOT";
				break;
			case "SAND":
				material = "GLASS";
				break;
			case "COBBLESTONE":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "STONE";
				break;
			case "STONE":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "SMOOTH_STONE";
				break;
			case "STONE_BRICKS":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "CRACKED_STONE_BRICKS";
				break;
			case "NETHERRACK":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "NETHER_BRICK";
				break;
			case "CLAY":
				material = "TERRACOTTA";
				break;
			case "CACTUS":
				if (VersionUtils.getBukkitVersionNumber() > 3) material = "GREEN_DYE";
				else
					material = "CACTUS_GREEN";
				break;
			case "SEA_PICKLE":
				material = "LIME_DYE";
				break;
			case "OAK_LOG":
			case "BIRCH_LOG":
			case "SPRUCE_LOG":
			case "JUNGLE_LOG":
			case "DARK_OAK_LOG":
			case "ACACIA_LOG":
			case "OAK_WOOD":
			case "BIRCH_WOOD":
			case "SPRUCE_WOOD":
			case "JUNGLE_WOOD":
			case "DARK_OAK_WOOD":
			case "ACACIA_WOOD":
			case "STRIPPED_OAK_LOG":
			case "STRIPPED_BIRCH_LOG":
			case "STRIPPED_SPRUCE_LOG":
			case "STRIPPED_JUNGLE_LOG":
			case "STRIPPED_DARK_OAK_LOG":
			case "STRIPPED_ACACIA_LOG":
			case "STRIPPED_OAK_WOOD":
			case "STRIPPED_BIRCH_WOOD":
			case "STRIPPED_SPRUCE_WOOD":
			case "STRIPPED_JUNGLE_WOOD":
			case "STRIPPED_DARK_OAK_WOOD":
			case "STRIPPED_ACACIA_WOOD":
				material = "CHARCOAL";
				break;
			case "CHORUS_FRUIT":
				material = "POPPED_CHORUS_FRUIT";
				break;
			case "WET_SPONGE":
				material = "SPONGE";
				break;
			case "BLACK_TERRACOTTA":
			case "BLUE_TERRACOTTA":
			case "BROWN_TERRACOTTA":
			case "CYAN_TERRACOTTA":
			case "GRAY_TERRACOTTA":
			case "GREEN_TERRACOTTA":
			case "LIGHT_BLUE_TERRACOTTA":
			case "LIGHT_GRAY_TERRACOTTA":
			case "LIME_TERRACOTTA":
			case "MAGENTA_TERRACOTTA":
			case "ORANGE_TERRACOTTA":
			case "PINK_TERRACOTTA":
			case "PURPLE_TERRACOTTA":
			case "RED_TERRACOTTA":
			case "WHITE_TERRACOTTA":
			case "YELLOW_TERRACOTTA":
				if (type != null && type.getBreakTypes().contains(block.getType())) {
					String[] terra = block.getType().name().split("_");
					String[] newTerra = new String[terra.length + 1];
					for(int i = 0; i < terra.length - 1; i++)
						newTerra[i] = terra[i];
					newTerra[terra.length - 1] = "GLAZED";
					newTerra[terra.length] = terra[terra.length - 1];
					material = String.join("_", newTerra);
				}
				break;
			case "QUARTZ_BLOCK":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "SMOOTH_QUARTZ";
				break;
			case "SANDSTONE":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "SMOOTH_SANDSTONE";
				break;
			case "RED_SANDSTONE":
				if (type != null && type.getBreakTypes().contains(block.getType())) material = "SMOOTH_RED_SANDSTONE";
				break;
			default:

		}
		if (material != null) {
			MatData data = new MatData(material);
			if (data.getMaterial() != null) return new SmelteryMaterial(new ItemStack(data.getMaterial()), block.getType(), data.getMaterial());
		}
		return null;
	}
}
