package org.ctp.enchantmentsolution.threads;

import org.ctp.enchantmentsolution.utils.Reflectionable;

@SuppressWarnings("unused")
public class MiscRunnable implements Runnable, Reflectionable {

	@Override
	public void run() {
		runMethod(this, "drowned");
		runMethod(this, "magicGuard");
		runMethod(this, "sandVeil");
	}

}
