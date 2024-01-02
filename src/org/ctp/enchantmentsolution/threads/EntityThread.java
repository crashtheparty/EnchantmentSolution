package org.ctp.enchantmentsolution.threads;

import java.util.Iterator;
import java.util.List;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.player.ESEntity;

public class EntityThread implements Runnable {

	@Override
	public void run() {
		List<ESEntity> entities = EnchantmentSolution.getAllESEntities();
		Iterator<ESEntity> iter = entities.iterator();
		while (iter.hasNext()) {
			ESEntity entry = iter.next();
			entry.runPotionEffects();
		}
	}

}
