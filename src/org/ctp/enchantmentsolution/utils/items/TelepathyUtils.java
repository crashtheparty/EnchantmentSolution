package org.ctp.enchantmentsolution.utils.items;

public class TelepathyUtils {

//	private static void damageItem(BlockBreakEvent event) {
//		Player player = event.getPlayer();
//		ItemStack item = player.getInventory().getItemInMainHand();
//		AbilityUtils.giveExperience(player, event.getExpToDrop());
//		player.incrementStatistic(Statistic.MINE_BLOCK, event.getBlock().getType());
//		player.incrementStatistic(Statistic.USE_ITEM, item.getType());
//		DamageUtils.damageItem(player, item);
//		McMMOHandler.handleMcMMO(event, item);
//		QuestsUtils.handle(event);
//		Block newBlock = event.getBlock();
//		Location loc = newBlock.getLocation().clone().add(0.5, 0.5, 0.5);
//		if (EnchantmentSolution.getPlugin().isJobsEnabled()) JobsUtils.sendBlockBreakAction(event);
//		if (ConfigString.USE_PARTICLES.getBoolean()) loc.getWorld().spawnParticle(Particle.BLOCK_CRACK, loc, 20, newBlock.getBlockData());
//		if (ConfigString.PLAY_SOUND.getBoolean()) {
//			BlockSound sound = BlockSound.getSound(newBlock.getType());
//			loc.getWorld().playSound(loc, sound.getSound(), sound.getVolume(newBlock.getType()), sound.getPitch(newBlock.getType()));
//		}
//		newBlock.setType(Material.AIR);
//	}
}
