package org.ctp.enchantmentsolution.utils.compatibility;

import org.bukkit.event.block.BlockBreakEvent;
import org.ctp.enchantmentsolution.EnchantmentSolution;

import com.leonardobishop.quests.Quests;
import com.leonardobishop.quests.quests.tasktypes.TaskType;
import com.leonardobishop.quests.quests.tasktypes.types.MiningCertainTaskType;
import com.leonardobishop.quests.quests.tasktypes.types.MiningTaskType;

public class QuestsUtils {

	public static void handle(BlockBreakEvent event) {
		if (EnchantmentSolution.getPlugin().hasQuests()) for(TaskType t: Quests.get().getTaskTypeManager().getTaskTypes())
			if (t instanceof MiningTaskType) ((MiningTaskType) t).onBlockBreak(event);
			else if (t instanceof MiningCertainTaskType) ((MiningCertainTaskType) t).onBlockBreak(event);
	}
}
