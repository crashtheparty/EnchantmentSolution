package org.ctp.enchantmentsolution.rpg.threads;

import org.ctp.enchantmentsolution.rpg.RPGPlayer;

public class RPGThread implements Runnable {

	private int run = 160, id;
	private final RPGPlayer player;

	public RPGThread(RPGPlayer player) {
		this.player = player;
	}

	@Override
	public void run() {
		run--;
		if (run == 0) player.removeFromBar();
	}

	public int getRun() {
		return run;
	}

	public void setRun() {
		run = 160;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RPGPlayer getPlayer() {
		return player;
	}

}
