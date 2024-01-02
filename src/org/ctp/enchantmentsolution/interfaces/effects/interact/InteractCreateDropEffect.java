package org.ctp.enchantmentsolution.interfaces.effects.interact;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.MatData;
import org.ctp.enchantmentsolution.interfaces.EnchantmentItemLocation;
import org.ctp.enchantmentsolution.interfaces.EnchantmentMultipleType;
import org.ctp.enchantmentsolution.interfaces.conditions.InteractCondition;
import org.ctp.enchantmentsolution.interfaces.conditions.interact.ActionIsTypeCondition;
import org.ctp.enchantmentsolution.interfaces.effects.PlayerInteractEffect;
import org.ctp.enchantmentsolution.utils.items.EnchantmentDrops;
import org.ctp.enchantmentsolution.utils.items.Interactions;

public class InteractCreateDropEffect extends PlayerInteractEffect {

	private EnchantmentDrops drops;
	private Particle success, failure;
	private int numParticles;
	private Sound successSound, failureSound;

	public InteractCreateDropEffect(EnchantmentWrapper enchantment, EnchantmentMultipleType type, EnchantmentItemLocation location, EventPriority priority,
	EnchantmentDrops drops, Particle success, Sound successSound, Particle failure, Sound failureSound, int numParticles, InteractCondition[] conditions) {
		super(enchantment, type, location, priority, addCondition(conditions, new ActionIsTypeCondition(false, Action.RIGHT_CLICK_BLOCK)));
		this.drops = drops;
		this.success = success;
		this.successSound = successSound;
		this.failure = failure;
		this.failureSound = failureSound;
		this.numParticles = numParticles;
	}

	@Override
	public CreateDropResult run(Player player, ItemStack[] items, PlayerInteractEvent event) {
		int level = getLevel(items);
		Block block = event.getClickedBlock();

		Interactions interactions = null;
		for(Interactions i: drops.getDrops())
			for(MatData data: i.getInteract())
				if (data.getMaterial() == block.getType()) {
					interactions = i;
					break;
				}

		return new CreateDropResult(level, interactions);
	}

	public Particle getSuccess() {
		return success;
	}

	public Particle getFailure() {
		return failure;
	}

	public int getNumParticles() {
		return numParticles;
	}

	public Sound getSuccessSound() {
		return successSound;
	}

	public Sound getFailureSound() {
		return failureSound;
	}

	public class CreateDropResult extends EffectResult {

		private final Interactions interactions;

		public CreateDropResult(int level, Interactions interactions) {
			super(level);
			this.interactions = interactions;
		}

		public Interactions getInteractions() {
			return interactions;
		}
	}

}
