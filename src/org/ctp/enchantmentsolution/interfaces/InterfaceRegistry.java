package org.ctp.enchantmentsolution.interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ctp.enchantmentsolution.enchantments.EnchantmentWrapper;
import org.ctp.enchantmentsolution.interfaces.block.*;
import org.ctp.enchantmentsolution.interfaces.damaged.IronDefense;
import org.ctp.enchantmentsolution.interfaces.damaged.Pushback;
import org.ctp.enchantmentsolution.interfaces.damager.*;
import org.ctp.enchantmentsolution.interfaces.death.*;
import org.ctp.enchantmentsolution.interfaces.effects.*;
import org.ctp.enchantmentsolution.interfaces.effects.block.*;
import org.ctp.enchantmentsolution.interfaces.equip.*;
import org.ctp.enchantmentsolution.interfaces.fishing.Angler;
import org.ctp.enchantmentsolution.interfaces.fishing.ExpShareFishing;
import org.ctp.enchantmentsolution.interfaces.fishing.Fried;
import org.ctp.enchantmentsolution.interfaces.interact.*;
import org.ctp.enchantmentsolution.interfaces.item.Tank;
import org.ctp.enchantmentsolution.interfaces.movement.Icarus;
import org.ctp.enchantmentsolution.interfaces.projectile.*;
import org.ctp.enchantmentsolution.interfaces.soulbound.SoulReaper;
import org.ctp.enchantmentsolution.interfaces.takedamage.MagicGuard;
import org.ctp.enchantmentsolution.interfaces.takedamage.MagmaWalkerDamage;
import org.ctp.enchantmentsolution.interfaces.walker.MagmaWalker;
import org.ctp.enchantmentsolution.interfaces.walker.VoidWalker;

public class InterfaceRegistry {

	private static HashMap<EnchantmentWrapper, List<EntityDamageEffect>> DAMAGER = new HashMap<EnchantmentWrapper, List<EntityDamageEffect>>();
	private static HashMap<EnchantmentWrapper, List<EntityDamageEffect>> DAMAGED = new HashMap<EnchantmentWrapper, List<EntityDamageEffect>>();
	private static HashMap<EnchantmentWrapper, List<EntityTakeDamageEffect>> TAKE_DAMAGE = new HashMap<EnchantmentWrapper, List<EntityTakeDamageEffect>>();
	private static HashMap<EnchantmentWrapper, List<EntityEquipEffect>> EQUIPPED = new HashMap<EnchantmentWrapper, List<EntityEquipEffect>>();
	private static HashMap<EnchantmentWrapper, List<DeathEffect>> KILLER = new HashMap<EnchantmentWrapper, List<DeathEffect>>();
	private static HashMap<EnchantmentWrapper, List<DeathEffect>> KILLED = new HashMap<EnchantmentWrapper, List<DeathEffect>>();
	private static HashMap<EnchantmentWrapper, List<PlayerInteractEffect>> INTERACT = new HashMap<EnchantmentWrapper, List<PlayerInteractEffect>>();
	private static HashMap<EnchantmentWrapper, List<ItemDamageEffect>> ITEM_DAMAGE = new HashMap<EnchantmentWrapper, List<ItemDamageEffect>>();
	private static HashMap<EnchantmentWrapper, List<SoulboundEffect>> SOULBOUND = new HashMap<EnchantmentWrapper, List<SoulboundEffect>>();
	private static HashMap<EnchantmentWrapper, List<BlockDropItemEffect>> BLOCK_DROPS = new HashMap<EnchantmentWrapper, List<BlockDropItemEffect>>();
	private static HashMap<EnchantmentWrapper, List<BlockBreakEffect>> BLOCK_BREAK = new HashMap<EnchantmentWrapper, List<BlockBreakEffect>>();
	private static HashMap<EnchantmentWrapper, List<BlockExpEffect>> BLOCK_EXP = new HashMap<EnchantmentWrapper, List<BlockExpEffect>>();
	private static HashMap<EnchantmentWrapper, List<EntityChangeBlockEffect>> CHANGE_BLOCK = new HashMap<EnchantmentWrapper, List<EntityChangeBlockEffect>>();
	private static HashMap<EnchantmentWrapper, List<ProjectileLaunchEffect>> PROJECTILE_LAUNCH = new HashMap<EnchantmentWrapper, List<ProjectileLaunchEffect>>();
	private static HashMap<EnchantmentWrapper, List<ProjectileHitDamagerEffect>> PROJECTILE_HIT_DAMAGER = new HashMap<EnchantmentWrapper, List<ProjectileHitDamagerEffect>>();
	private static HashMap<EnchantmentWrapper, List<ProjectileHitDamagedEffect>> PROJECTILE_HIT_DAMAGED = new HashMap<EnchantmentWrapper, List<ProjectileHitDamagedEffect>>();
	private static HashMap<EnchantmentWrapper, List<FishingEffect>> FISHING = new HashMap<EnchantmentWrapper, List<FishingEffect>>();
	private static HashMap<EnchantmentWrapper, List<MovementEffect>> MOVEMENT = new HashMap<EnchantmentWrapper, List<MovementEffect>>();

	private static HashMap<EnchantmentWrapper, WalkerInterface> WALKER = new HashMap<EnchantmentWrapper, WalkerInterface>();

	public static void firstLoad() {
		registerDamagerEnchantment(new BaneOfAnthropoids());
		registerDamagerEnchantment(new Blindness());
		registerDamagerEnchantment(new Brine());
		registerDamagerEnchantment(new DetonatorDamage());
		registerDamagerEnchantment(new Drowned());
		registerDamagerEnchantment(new GungHo());
		registerDamagerEnchantment(new HollowPoint());
		registerDamagerEnchantment(new IrenesLassoDamage());
		registerDamagerEnchantment(new Javelin());
		registerDamagerEnchantment(new KnockUp());
		registerDamagerEnchantment(new LagCurseDamage());
		registerDamagerEnchantment(new Lancer());
		registerDamagerEnchantment(new LifeDrain());
		registerDamagerEnchantment(new Pacified());
		registerDamagerEnchantment(new SandVeil());
		registerDamagerEnchantment(new ShockAspect());
		registerDamagerEnchantment(new StoneThrow());
		registerDamagerEnchantment(new StreakDamage());
		registerDamagerEnchantment(new Truant());
		registerDamagerEnchantment(new Venom());
		registerDamagerEnchantment(new Whipped());
		registerDamagerEnchantment(new Withering());

		registerDamagedEnchantment(new IronDefense());
		registerDamagedEnchantment(new Pushback());

		registerTakeDamageEnchantment(new MagicGuard());
		registerTakeDamageEnchantment(new MagmaWalkerDamage());

		registerEquipEnchantment(new Armored());
		registerEquipEnchantment(new FrequentFlyer());
		registerEquipEnchantment(new GungHoEquip());
		registerEquipEnchantment(new Joggers());
		registerEquipEnchantment(new Life());
		registerEquipEnchantment(new Plyometrics());
		registerEquipEnchantment(new QuickStrike());
		registerEquipEnchantment(new Toughness());
		registerEquipEnchantment(new Unrest());
		registerEquipEnchantment(new WaterBreathing());

		registerKillerEnchantment(new Beheading());
		registerKillerEnchantment(new Butcher());
		registerKillerEnchantment(new ExpShareDeath());
		registerKillerEnchantment(new Husbandry());
		registerKillerEnchantment(new Pillage());
		registerKillerEnchantment(new Recycler());
		registerKillerEnchantment(new StreakDeath());
		registerKillerEnchantment(new Transmutation());

		registerKilledEnchantment(new SoulboundDeath());

		registerItemDamageEnchantment(new Tank());

		registerSoulboundEnchantment(new SoulReaper());

		registerInteractEnchantment(new Flash());
		registerInteractEnchantment(new FlowerGift());
		registerInteractEnchantment(new Frosty());
		registerInteractEnchantment(new IrenesLasso());
		registerInteractEnchantment(new MoisturizeExtinguish());
		registerInteractEnchantment(new MoisturizeInteract());
		registerInteractEnchantment(new MoisturizeWaterLog());
		registerInteractEnchantment(new Overkill());
		registerInteractEnchantment(new SplatterFest());
		registerInteractEnchantment(new Zeal());

		registerBlockDropEnchantment(new GreenThumbDrop());
		registerBlockDropEnchantment(new InfestationCurse());
		registerBlockDropEnchantment(new RareEarth());
		registerBlockDropEnchantment(new Smeltery());
		registerBlockDropEnchantment(new Telepathy());

		registerBlockBreakEnchantment(new GreenThumbBreak());
		registerBlockBreakEnchantment(new LagCurseBlockBreak());
		registerBlockBreakEnchantment(new Hammer());
		registerBlockBreakEnchantment(new HammerD());
		registerBlockBreakEnchantment(new Drill());

		registerBlockExpEnchantment(new ExpShareBlock());

		registerChangeBlockEnchantment(new LightWeight());

		registerProjectileLaunchEnchantment(new DetonatorMetadata());
		registerProjectileLaunchEnchantment(new LagCurseProjectile());
		registerProjectileLaunchEnchantment(new Sniper());

		registerProjectileHitDamagerEnchantment(new Detonator());
		registerProjectileHitDamagerEnchantment(new HollowPointInvulnerable());

		registerProjectileHitDamagedEnchantment(new HardBounce());
		
		registerMovementEnchantment(new Icarus());

		registerFishingEnchantment(new Angler());
		registerFishingEnchantment(new ExpShareFishing());
		registerFishingEnchantment(new Fried());

		registerWalkerEnchantment(new MagmaWalker());
		registerWalkerEnchantment(new VoidWalker());
	}

	public static boolean registerWalkerEnchantment(WalkerInterface inter) {
		if (WALKER.containsKey(inter.getEnchantment())) return false;
		WALKER.put(inter.getEnchantment(), inter);
		return true;
	}

	public static boolean registerDamagerEnchantment(EntityDamageEffect effect) {
		List<EntityDamageEffect> effects = DAMAGER.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<EntityDamageEffect>();
		effects.add(effect);
		DAMAGER.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerDamagedEnchantment(EntityDamageEffect effect) {
		List<EntityDamageEffect> effects = DAMAGED.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<EntityDamageEffect>();
		effects.add(effect);
		DAMAGED.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerTakeDamageEnchantment(EntityTakeDamageEffect effect) {
		List<EntityTakeDamageEffect> effects = TAKE_DAMAGE.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<EntityTakeDamageEffect>();
		effects.add(effect);
		TAKE_DAMAGE.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerEquipEnchantment(EntityEquipEffect effect) {
		List<EntityEquipEffect> effects = EQUIPPED.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<EntityEquipEffect>();
		effects.add(effect);
		EQUIPPED.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerKillerEnchantment(DeathEffect effect) {
		List<DeathEffect> effects = KILLER.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<DeathEffect>();
		effects.add(effect);
		KILLER.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerKilledEnchantment(DeathEffect effect) {
		List<DeathEffect> effects = KILLED.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<DeathEffect>();
		effects.add(effect);
		KILLED.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerInteractEnchantment(PlayerInteractEffect effect) {
		List<PlayerInteractEffect> effects = INTERACT.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<PlayerInteractEffect>();
		effects.add(effect);
		INTERACT.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerItemDamageEnchantment(ItemDamageEffect effect) {
		List<ItemDamageEffect> effects = ITEM_DAMAGE.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<ItemDamageEffect>();
		effects.add(effect);
		ITEM_DAMAGE.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerSoulboundEnchantment(SoulboundEffect effect) {
		List<SoulboundEffect> effects = SOULBOUND.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<SoulboundEffect>();
		effects.add(effect);
		SOULBOUND.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerBlockDropEnchantment(BlockDropItemEffect effect) {
		List<BlockDropItemEffect> effects = BLOCK_DROPS.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<BlockDropItemEffect>();
		effects.add(effect);
		BLOCK_DROPS.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerBlockBreakEnchantment(BlockBreakEffect effect) {
		List<BlockBreakEffect> effects = BLOCK_BREAK.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<BlockBreakEffect>();
		effects.add(effect);
		BLOCK_BREAK.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerBlockExpEnchantment(BlockExpEffect effect) {
		List<BlockExpEffect> effects = BLOCK_EXP.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<BlockExpEffect>();
		effects.add(effect);
		BLOCK_EXP.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerChangeBlockEnchantment(EntityChangeBlockEffect effect) {
		List<EntityChangeBlockEffect> effects = CHANGE_BLOCK.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<EntityChangeBlockEffect>();
		effects.add(effect);
		CHANGE_BLOCK.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerProjectileLaunchEnchantment(ProjectileLaunchEffect effect) {
		List<ProjectileLaunchEffect> effects = PROJECTILE_LAUNCH.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<ProjectileLaunchEffect>();
		effects.add(effect);
		PROJECTILE_LAUNCH.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerProjectileHitDamagerEnchantment(ProjectileHitDamagerEffect effect) {
		List<ProjectileHitDamagerEffect> effects = PROJECTILE_HIT_DAMAGER.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<ProjectileHitDamagerEffect>();
		effects.add(effect);
		PROJECTILE_HIT_DAMAGER.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerProjectileHitDamagedEnchantment(ProjectileHitDamagedEffect effect) {
		List<ProjectileHitDamagedEffect> effects = PROJECTILE_HIT_DAMAGED.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<ProjectileHitDamagedEffect>();
		effects.add(effect);
		PROJECTILE_HIT_DAMAGED.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerFishingEnchantment(FishingEffect effect) {
		List<FishingEffect> effects = FISHING.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<FishingEffect>();
		effects.add(effect);
		FISHING.put(effect.getEnchantment(), effects);
		return true;
	}

	public static boolean registerMovementEnchantment(MovementEffect effect) {
		List<MovementEffect> effects = MOVEMENT.get(effect.getEnchantment());
		if (effects == null) effects = new ArrayList<MovementEffect>();
		effects.add(effect);
		MOVEMENT.put(effect.getEnchantment(), effects);
		return true;
	}

	protected static HashMap<EnchantmentWrapper, WalkerInterface> getWalkerInterfaces() {
		return WALKER;
	}

	public static HashMap<EnchantmentWrapper, List<EntityDamageEffect>> getDamagerEffects() {
		return DAMAGER;
	}

	public static HashMap<EnchantmentWrapper, List<EntityDamageEffect>> getDamagedEffects() {
		return DAMAGED;
	}

	public static HashMap<EnchantmentWrapper, List<EntityTakeDamageEffect>> getTakeDamageEffects() {
		return TAKE_DAMAGE;
	}

	public static HashMap<EnchantmentWrapper, List<EntityEquipEffect>> getEquipEffects() {
		return EQUIPPED;
	}

	public static HashMap<EnchantmentWrapper, List<DeathEffect>> getKillerEffects() {
		return KILLER;
	}

	public static HashMap<EnchantmentWrapper, List<DeathEffect>> getKilledEffects() {
		return KILLED;
	}

	public static HashMap<EnchantmentWrapper, List<PlayerInteractEffect>> getInteractEffects() {
		return INTERACT;
	}

	public static HashMap<EnchantmentWrapper, List<ItemDamageEffect>> getItemDamageEffects() {
		return ITEM_DAMAGE;
	}

	public static HashMap<EnchantmentWrapper, List<SoulboundEffect>> getSoulboundEffects() {
		return SOULBOUND;
	}

	public static HashMap<EnchantmentWrapper, List<BlockDropItemEffect>> getBlockDropItemEffects() {
		return BLOCK_DROPS;
	}

	public static HashMap<EnchantmentWrapper, List<BlockBreakEffect>> getBlockBreakEffects() {
		return BLOCK_BREAK;
	}

	public static HashMap<EnchantmentWrapper, List<BlockExpEffect>> getBlockExpEffects() {
		return BLOCK_EXP;
	}

	public static HashMap<EnchantmentWrapper, List<EntityChangeBlockEffect>> getChangeBlockEffects() {
		return CHANGE_BLOCK;
	}

	public static HashMap<EnchantmentWrapper, List<ProjectileLaunchEffect>> getProjectileLaunchEffects() {
		return PROJECTILE_LAUNCH;
	}

	public static HashMap<EnchantmentWrapper, List<ProjectileHitDamagerEffect>> getProjectileHitDamagerEffects() {
		return PROJECTILE_HIT_DAMAGER;
	}

	public static HashMap<EnchantmentWrapper, List<ProjectileHitDamagedEffect>> getProjectileHitDamagedEffects() {
		return PROJECTILE_HIT_DAMAGED;
	}

	public static HashMap<EnchantmentWrapper, List<FishingEffect>> getFishingEffects() {
		return FISHING;
	}

	public static HashMap<EnchantmentWrapper, List<MovementEffect>> getMovementEffects() {
		return MOVEMENT;
	}
}
