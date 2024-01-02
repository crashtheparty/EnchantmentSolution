package org.ctp.enchantmentsolution.interfaces.conditions.block;

import java.util.Arrays;

import org.bukkit.GameMode;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.ctp.enchantmentsolution.interfaces.conditions.BlockCondition;

public class BlockGamemodeCondition implements BlockCondition {

	private final boolean opposite;
	private GameMode[] gamemodes;

	public BlockGamemodeCondition(boolean opposite, GameMode... gamemodes) {
		this.opposite = opposite;
		this.gamemodes = gamemodes;
	}

	@Override
	public boolean metCondition(Player player, BlockData brokenData, BlockEvent event) {
		if (Arrays.asList(gamemodes).contains(player.getGameMode())) return !opposite;
		return opposite;
	}

	public boolean isOpposite() {
		return opposite;
	}

	public GameMode[] getGamemodes() {
		return gamemodes;
	}

	public void setGamemodes(GameMode[] gamemodes) {
		this.gamemodes = gamemodes;
	}

}
