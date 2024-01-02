package org.ctp.enchantmentsolution.utils.items;

import java.util.*;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.enchantments.RegisterEnchantments;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ParticleEffect;
import org.ctp.enchantmentsolution.utils.player.ESPlayer;

public class AbilityUtils {

	public static void giveExperience(Player player, int amount) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		ESPlayer esPlayer = EnchantmentSolution.getESPlayer(player);
		for(ItemStack i: esPlayer.getEquipped())
			if (EnchantmentUtils.hasEnchantment(i, Enchantment.MENDING)) {
				if (DamageUtils.getDamage(i) == 0) continue;
				items.add(i);
			}

		while (items.size() > 0) {
			Collections.shuffle(items);
			ItemStack item = items.get(0);
			int durability = DamageUtils.getDamage(item);
			while (amount > 0 && durability > 0) {
				durability -= 2;
				amount--;
			}
			if (durability < 0) durability = 0;
			DamageUtils.setDamage(item, durability);
			items.remove(0);
		}
		if (amount > 0) player.giveExp(amount);
	}

	public static int setExp(int exp, int level) {
		int totalExp = exp;
		if (exp > 0) for(int i = 0; i < exp * level; i++) {
			double chance = .50;
			double random = Math.random();
			if (chance > random) totalExp++;
		}
		return totalExp;
	}

	public static ParticleEffect[] createEffects(int effectMultiple, int effectMinimum, int numMultiple, int numMaximum, Particle[] blacklist,
	Particle[] whitelist) {
		int random = (int) (Math.random() * effectMultiple + effectMinimum);
		List<ParticleEffect> particles = new ArrayList<ParticleEffect>();
		for(int i = 0; i < random; i++)
			particles.add(generateParticle(numMultiple, numMaximum, blacklist, whitelist));
		return particles.toArray(new ParticleEffect[particles.size()]);
	}

	private static ParticleEffect generateParticle(int numMultiple, int numMaximum, Particle[] blacklist, Particle[] whitelist) {
		Particle particle = null;
		List<Particle> particles = new ArrayList<Particle>();
		if (whitelist != null && whitelist.length > 0) particles.addAll(Arrays.asList(whitelist));
		else {
			particles.addAll(Arrays.asList(Particle.values()));
			if (blacklist != null && blacklist.length > 0) particles.removeAll(Arrays.asList(blacklist));
		}
		Iterator<Particle> iter = particles.iterator();
		while (iter.hasNext()) {
			Particle p = iter.next();
			if (!p.getDataType().isAssignableFrom(Void.class)) iter.remove();
		}
		int numParticles = (int) (Math.random() * numMultiple + numMaximum);
		int particleType = (int) (Math.random() * particles.size());
		particle = particles.get(particleType);
		return new ParticleEffect(particle, numParticles);
	}

	public static List<DamageCause> getContactCauses() {
		return Arrays.asList(DamageCause.BLOCK_EXPLOSION, DamageCause.CONTACT, DamageCause.CUSTOM, DamageCause.ENTITY_ATTACK, DamageCause.ENTITY_EXPLOSION, DamageCause.ENTITY_SWEEP_ATTACK, DamageCause.LIGHTNING, DamageCause.PROJECTILE, DamageCause.THORNS);
	}

	public static int getExhaustionCurse(Player player) {
		EnchantmentWrapper curse = RegisterEnchantments.CURSE_OF_EXHAUSTION;
		int exhaustionCurse = 0;
		for(ItemStack item: player.getInventory().getArmorContents())
			if (EnchantmentUtils.hasEnchantment(item, curse)) exhaustionCurse += EnchantmentUtils.getLevel(item, curse);
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		if (EnchantmentUtils.hasEnchantment(mainHand, curse)) exhaustionCurse += EnchantmentUtils.getLevel(mainHand, curse);
		ItemStack offHand = player.getInventory().getItemInOffHand();
		if (EnchantmentUtils.hasEnchantment(offHand, curse)) exhaustionCurse += EnchantmentUtils.getLevel(offHand, curse);
		return exhaustionCurse;
	}

	public static float getExhaustion(Player player) {
		return player.getFoodLevel() * 4 + player.getSaturation() * 4 - player.getExhaustion();
	}

	public static boolean isDoubleFlower(Material material) {
		switch (material.name()) {
			case "SUNFLOWER":
			case "PEONY":
			case "ROSE_BUSH":
			case "LILAC":
				return true;
			default:
				return false;
		}
	}
}
