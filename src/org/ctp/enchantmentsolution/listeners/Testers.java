package org.ctp.enchantmentsolution.listeners;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.ctp.enchantmentsolution.events.ExperienceEvent;
import org.ctp.enchantmentsolution.events.modify.LagEvent;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class Testers implements Listener {

	@EventHandler
	public void onExperienceEvent(ExperienceEvent event) {
		ChatUtils.sendInfo("Experience Event Found: " + event.getType().name() + " " + event.getOldExp() + " "
				+ event.getNewExp());

		int modify = (int) (Math.random() * 4);

		switch (modify) {
			case 0:
				ChatUtils.sendInfo("No modification");
				break;
			case 1:
				ChatUtils.sendInfo("Double exp");
				event.setNewExp(event.getNewExp() * 2);
				break;
			case 2:
				ChatUtils.sendInfo("Half exp");
				event.setNewExp(event.getNewExp() / 2);
				break;
			case 3:
				ChatUtils.sendInfo("Cancel");
				event.setCancelled(true);
				break;
		}
	}

	@EventHandler
	public void onLagEvent(LagEvent event) {
		ChatUtils.sendInfo("Lag Event Found: " + event.getSound().name());

		int modify = (int) (Math.random() * 2);

		switch (modify) {
			case 0:
				ChatUtils.sendInfo("No modification");
				break;
			case 1:
				ChatUtils.sendInfo("Randomize Sound");
				int size = Sound.values().length;
				event.setSound(Sound.values()[(int) (Math.random() * size)]);
				break;
		}
	}
}
