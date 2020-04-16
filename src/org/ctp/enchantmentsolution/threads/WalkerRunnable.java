package org.ctp.enchantmentsolution.threads;

import org.ctp.enchantmentsolution.utils.abilityhelpers.WalkerUtils;

public class WalkerRunnable implements Runnable {

	@Override
	public void run() {
		WalkerUtils.updateBlocks();
		WalkerUtils.incrementTick();
	}

}
