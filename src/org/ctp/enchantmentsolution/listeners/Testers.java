package org.ctp.enchantmentsolution.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.ctp.enchantmentsolution.events.damage.KnockUpEvent;
import org.ctp.enchantmentsolution.events.damage.StoneThrowEvent;
import org.ctp.enchantmentsolution.utils.ChatUtils;

public class Testers implements Listener {
	
	@EventHandler
	public void onKnockUpEvent(KnockUpEvent event) {
		ChatUtils.sendInfo("Knock Up Event Found: " + event.getDamage() + " " + event.getNewDamage()
			+ " " + event.getEntity().getName() + " " + event.getDamager().getName() + " " + event.getKnockUp());
		
		int modify = (int) (Math.random() * 5);

		switch(modify) {
			case 0:
				ChatUtils.sendInfo("No modification");
				break;
			case 1:
				ChatUtils.sendInfo("Double damage");
				event.setNewDamage(event.getNewDamage() * 2);
				break;
			case 2:
				ChatUtils.sendInfo("Half damage");
				event.setNewDamage(event.getNewDamage() / 2);
				break;
			case 3:
				ChatUtils.sendInfo("Decrease knockup");
				event.setKnockUp(event.getKnockUp() / 2);
				break;
			case 4:
				ChatUtils.sendInfo("Increase knockup");
				event.setKnockUp(event.getKnockUp() * 2);
				break;
		}
	}
	
	@EventHandler
	public void onStoneThrowEvent(StoneThrowEvent event) {
		ChatUtils.sendInfo("Stone Throw Event Found: " + event.getDamage() + " " + event.getNewDamage()
			+ " " + event.getEntity().getName() + " " + event.getDamager().getName());
		
		int modify = (int) (Math.random() * 3);

		switch(modify) {
			case 0:
				ChatUtils.sendInfo("No modification");
				break;
			case 1:
				ChatUtils.sendInfo("Double damage");
				event.setNewDamage(event.getNewDamage() * 2);
				break;
			case 2:
				ChatUtils.sendInfo("Half damage");
				event.setNewDamage(event.getNewDamage() / 2);
				break;
		}
	}

}
