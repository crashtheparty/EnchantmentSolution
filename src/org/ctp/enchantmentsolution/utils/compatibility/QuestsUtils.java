package org.ctp.enchantmentsolution.utils.compatibility;

import org.bukkit.event.block.BlockBreakEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;

import com.leonardobishop.quests.bukkit.BukkitQuestsPlugin;
import com.leonardobishop.quests.bukkit.tasktype.type.MiningCertainTaskType;
import com.leonardobishop.quests.bukkit.tasktype.type.MiningTaskType;
import com.leonardobishop.quests.common.tasktype.TaskType;

public class QuestsUtils {

	public static void handle(BlockBreakEvent event) {
		if (EnchantmentSolution.getPlugin().hasQuests()) for(TaskType t: BukkitQuestsPlugin.getPlugin(BukkitQuestsPlugin.class).getTaskTypeManager().getTaskTypes())
			if (t instanceof MiningTaskType) ((MiningTaskType) t).onBlockBreak(event);
			else if (t instanceof MiningCertainTaskType) ((MiningCertainTaskType) t).onBlockBreak(event);
	}
}
