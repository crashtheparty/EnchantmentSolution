package org.ctp.enchantmentsolution.listeners.abilities.helpers;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class EntityAccuracy{
	private static List<EntityAccuracy> ENTITIES = new ArrayList<EntityAccuracy>();
	
	private Player attacker;
	private LivingEntity entity;
	private double accuracy;
	private int run, scheduler;
	
	private EntityAccuracy(Player attacker, LivingEntity entity, double accuracy) {
		this.setAttacker(attacker);
		this.setEntity(entity);
		this.setAccuracy(accuracy);
		resetRun();
	}
	
	public static List<EntityAccuracy> getEntities(){
		return ENTITIES;
	}

	public static void addEntity(Player player, LivingEntity entity, double accuracy) {
		ENTITIES.add(new EntityAccuracy(player, entity, accuracy));
	}

	public LivingEntity getEntity() {
		return entity;
	}

	public void setEntity(LivingEntity entity) {
		this.entity = entity;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	
	public void resetRun() {
		run = 160;
	}

	public int getScheduler() {
		return scheduler;
	}

	public void setScheduler(int scheduler) {
		this.scheduler = scheduler;
	}

	public Player getAttacker() {
		return attacker;
	}

	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}

	public int getRun() {
		return run;
	}
	
	public void minus() {
		run -= 1;
	}
}