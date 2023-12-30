package org.ctp.enchantmentsolution.utils.player;

import org.ctp.crashapi.utils.ServerUtils;

public class CheckTimer {

	private boolean check;
	private long tick;
	private final int ticksBetween;

	public CheckTimer(int ticksBetween) {
		this.ticksBetween = ticksBetween;
		set(true, ServerUtils.getCurrentTick());
	}

	public int getTicksBetween() {
		return ticksBetween;
	}

	public long getTick() {
		return tick;
	}

	public void setTick(long tick) {
		this.tick = tick;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	
	public void set(boolean check, long tick) {
		setCheck(check);
		setTick(tick);
	}
	
	public boolean shouldCheck() {
		long currentTick = ServerUtils.getCurrentTick();
		long checkTick = tick + ticksBetween;
		return check && checkTick <= currentTick;
	}

}
