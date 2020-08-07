package org.ctp.enchantmentsolution.utils.player;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ctp.crashapi.item.ItemData;
import org.ctp.crashapi.item.ItemType;
import org.ctp.crashapi.nms.ServerNMS;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.*;
import org.ctp.enchantmentsolution.events.player.FrequentFlyerEvent;
import org.ctp.enchantmentsolution.events.player.FrequentFlyerEvent.FFType;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.PermissionUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ItemEquippedSlot;
import org.ctp.enchantmentsolution.utils.abilityhelpers.OverkillDeath;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.attributes.FlySpeedAttribute;

public class ESPlayer {

	private static Map<Integer, Integer> GLOBAL_BLOCKS = new HashMap<Integer, Integer>();
	private static double CONTAGION_CHANCE = 0.0005, FORCE_FEED_CHANCE = 0.005;
	private final OfflinePlayer player;
	private Player onlinePlayer;
	private RPGPlayer rpg;
	private Map<Enchantment, Integer> cooldowns;
	private List<ItemStack> soulItems;
	private Map<Integer, Integer> blocksBroken;
	private float currentExhaustion, pastExhaustion;
	private ItemStack elytra;
	private boolean canFly, didTick, reset;
	private int flightDamage, flightDamageLimit, frequentFlyerLevel, icarusDelay;
	private FFType currentFFType;
	private List<OverkillDeath> overkillDeaths;
	private List<AttributeLevel> attributes;
	private ESPlayerAttributeInstance flyAttribute = new FlySpeedAttribute();

	public ESPlayer(OfflinePlayer player) {
		this.player = player;
		onlinePlayer = player.getPlayer();
		rpg = RPGUtils.getPlayer(player);
		cooldowns = new HashMap<Enchantment, Integer>();
		blocksBroken = new HashMap<Integer, Integer>();
		overkillDeaths = new ArrayList<OverkillDeath>();
		attributes = new ArrayList<AttributeLevel>();
		currentFFType = FFType.NONE;
		removeSoulItems();
		flyAttribute.addModifier(new AttributeModifier(UUID.fromString("ffffffff-ffff-ffff-ffff-000000000000"), "frequentFlyerFlight", -0.08, Operation.ADD_NUMBER));
	}

	public OfflinePlayer getPlayer() {
		return player;
	}

	public boolean isOnline() {
		return onlinePlayer != null && onlinePlayer.isOnline();
	}

	public Player getOnlinePlayer() {
		return onlinePlayer;
	}

	public void reloadPlayer() {
		for(Player p: Bukkit.getOnlinePlayers())
			if (p.getUniqueId().equals(player.getUniqueId())) onlinePlayer = p;
	}

	public ItemStack[] getArmor() {
		ItemStack[] armor = new ItemStack[4];
		if (!isOnline()) return armor;
		armor[0] = getOnlinePlayer().getInventory().getHelmet();
		armor[1] = getOnlinePlayer().getInventory().getChestplate();
		armor[2] = getOnlinePlayer().getInventory().getLeggings();
		armor[3] = getOnlinePlayer().getInventory().getBoots();

		return armor;
	}

	public ItemStack[] getEquipped() {
		ItemStack[] equipped = new ItemStack[6];
		if (!isOnline()) return equipped;
		equipped[0] = getOnlinePlayer().getInventory().getHelmet();
		equipped[1] = getOnlinePlayer().getInventory().getChestplate();
		equipped[2] = getOnlinePlayer().getInventory().getLeggings();
		equipped[3] = getOnlinePlayer().getInventory().getBoots();
		equipped[4] = getOnlinePlayer().getInventory().getItemInMainHand();
		equipped[5] = getOnlinePlayer().getInventory().getItemInOffHand();

		return equipped;
	}

	public long getCooldown(Enchantment enchant) {
		return cooldowns.containsKey(enchant) ? cooldowns.get(enchant) : 0;
	}

	public boolean setCooldown(Enchantment enchant) {
		cooldowns.put(enchant, ServerNMS.getCurrentTick());
		return cooldowns.containsKey(enchant);
	}

	public RPGPlayer getRPG() {
		return rpg;
	}

	public List<ItemStack> getSoulItems() {
		return soulItems;
	}

	public void setSoulItems(List<ItemStack> items) {
		soulItems = items;
	}

	public void removeSoulItems() {
		soulItems = new ArrayList<ItemStack>();
	}

	public boolean canBreakBlock() {
		int tick = ServerNMS.getCurrentTick();
		if (GLOBAL_BLOCKS.containsKey(tick) && GLOBAL_BLOCKS.get(tick) >= ConfigString.MULTI_BLOCK_BLOCKS_GLOBAL.getInt()) return false;
		if (blocksBroken.containsKey(tick) && blocksBroken.get(tick) >= ConfigString.MULTI_BLOCK_BLOCKS_PLAYER.getInt()) return false;
		return true;
	}

	public void breakBlock() {
		int tick = ServerNMS.getCurrentTick();
		int blocks = 1;
		if (blocksBroken.containsKey(tick)) blocks += blocksBroken.get(tick);
		blocksBroken.put(tick, blocks);
		int globalBlocks = 1;
		if (GLOBAL_BLOCKS.containsKey(tick)) globalBlocks += GLOBAL_BLOCKS.get(tick);
		GLOBAL_BLOCKS.put(tick, globalBlocks);
	}

	public double getContagionChance() {
		double playerChance = 0;
		if (isOnline()) for(ItemStack item: getOnlinePlayer().getInventory().getContents())
			if (item != null && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_CONTAGION)) playerChance += CONTAGION_CHANCE;
		return playerChance;
	}

	public List<ItemStack> getCurseableItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		if (isOnline()) {
			for(ItemStack item: getOnlinePlayer().getInventory().getContents())
				if (item != null && ItemType.hasEnchantMaterial(new ItemData(item)) && canAddCurse(item) && !hasAllCurses(item)) items.add(item);
			for(ItemStack item: getOnlinePlayer().getInventory().getExtraContents())
				if (item != null && ItemType.hasEnchantMaterial(new ItemData(item)) && canAddCurse(item) && !hasAllCurses(item)) items.add(item);
		}
		return items;
	}

	private boolean hasAllCurses(ItemStack item) {
		boolean noCurse = true;
		for(CustomEnchantment enchantment: RegisterEnchantments.getCurseEnchantments())
			if (enchantment.isCurse() && EnchantmentUtils.canAddEnchantment(enchantment, item) && !EnchantmentUtils.hasEnchantment(item, enchantment.getRelativeEnchantment())) {
				noCurse = false;
				break;
			}
		return noCurse;
	}

	private boolean canAddCurse(ItemStack item) {
		boolean addCurse = false;
		if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_STAGNANCY)) return false;
		for(CustomEnchantment enchantment: RegisterEnchantments.getCurseEnchantments())
			if (enchantment.isCurse() && EnchantmentUtils.canAddEnchantment(enchantment, item)) {
				addCurse = true;
				break;
			}
		return addCurse;
	}

	public int getExhaustion() {
		if (!isOnline()) return 0;
		return AbilityUtils.getExhaustionCurse(getOnlinePlayer());
	}

	public void setCurrentExhaustion() {
		pastExhaustion = currentExhaustion;
		currentExhaustion = AbilityUtils.getExhaustion(getOnlinePlayer());
	}

	public void resetCurrentExhaustion() {
		pastExhaustion = 0;
		currentExhaustion = 0;
	}

	public float getPastExhaustion() {
		return pastExhaustion;
	}

	public float getCurrentExhaustion() {
		return currentExhaustion;
	}

	public ItemStack getElytra() {
		return elytra;
	}

	public boolean hasFrequentFlyer() {
		frequentFlyerLevel = 0;
		ItemStack newElytra = null;
		for(ItemStack item: getArmor())
			if (item != null && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.FREQUENT_FLYER) && !DamageUtils.aboveMaxDamage(item)) {
				int level = EnchantmentUtils.getLevel(item, RegisterEnchantments.FREQUENT_FLYER);
				if (level > frequentFlyerLevel) {
					frequentFlyerLevel = level;
					newElytra = item;
				}
			}
		reset = elytra == null || !elytra.equals(newElytra);
		elytra = newElytra;
		return elytra != null;
	}

	public void setFrequentFlyer() {
		boolean fly = frequentFlyerLevel > 0 && elytra != null;
		flightDamageLimit = 60;
		setCanFly(fly);
		setFlightSpeed(fly);
		if (reset) flightDamage = flightDamageLimit;
	}

	private void setFlightSpeed(boolean canFly) {
		if (canFly) {
			flyAttribute.removeModifier(UUID.fromString("ffffffff-ffff-ffff-ffff-000000001111"));
			flyAttribute.addModifier(new AttributeModifier(UUID.fromString("ffffffff-ffff-ffff-ffff-000000001111"), "frequentFlyerLevel", 0.016 * frequentFlyerLevel, Operation.ADD_NUMBER));
			player.getPlayer().setFlySpeed((float) flyAttribute.getValue());
		} else if (player.isOnline()) player.getPlayer().setFlySpeed((float) flyAttribute.getDefaultValue());
	}

	public boolean canFly(boolean online) {
		if (online) {
			if (!isOnline()) return false;
			if (canFly && !getOnlinePlayer().getAllowFlight()) {
				resetFlyer();
				setCanFly(canFly);
				if (!getOnlinePlayer().getAllowFlight()) return false;
			}
		}
		return canFly;
	}

	public void setCanFly(boolean canFly) {
		if (!isOnline()) return;
		Player player = getOnlinePlayer();
		boolean permission = PermissionUtils.check(player, "enchantmentsolution.enable-flight", "enchantmentsolution.abilities.has-external-flight");
		boolean modifyCanFly = canFly || player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR);
		FrequentFlyerEvent event = null;
		if (frequentFlyerLevel == 0 && this.canFly && !modifyCanFly) {
			if (!permission && currentFFType == FFType.FLIGHT) event = new FrequentFlyerEvent(player, frequentFlyerLevel, FFType.REMOVE_FLIGHT);
		} else if (!this.canFly && modifyCanFly && currentFFType == FFType.NONE) event = new FrequentFlyerEvent(player, frequentFlyerLevel, FFType.ALLOW_FLIGHT);
		if (event != null) {
			Bukkit.getPluginManager().callEvent(event);
			if (!event.isCancelled()) {
				this.canFly = modifyCanFly;
				if (event.getType().doesAllowFlight()) currentFFType = FFType.FLIGHT;
				else
					currentFFType = FFType.NONE;
				if (this.canFly || !permission) {
					player.setAllowFlight(this.canFly);
					if (player.isFlying() && !this.canFly) player.setFlying(false);
				}
			}
		}
	}

	public void resetFlyer() {
		if (!isOnline()) return;
		Player player = getOnlinePlayer();
		FrequentFlyerEvent event = new FrequentFlyerEvent(player, frequentFlyerLevel, FFType.REMOVE_FLIGHT);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			this.canFly = false;
			currentFFType = FFType.NONE;
			player.setAllowFlight(this.canFly);
			if (player.isFlying() && !this.canFly) player.setFlying(false);
		}
	}

	public void logoutFlyer() {
		currentFFType = FFType.NONE;
		canFly = false;
		frequentFlyerLevel = 0;
		elytra = null;
		getOnlinePlayer().setAllowFlight(false);
	}

	public int getFlightDamage() {
		return flightDamage;
	}

	public void minus() {
		Player player = getOnlinePlayer();
		if (player.getLocation().getY() >= 12000) AdvancementUtils.awardCriteria(player, ESAdvancement.CRUISING_ALTITUDE, "elytra");
		flightDamage --;
		if (flightDamage <= 0) {
			if (elytra != null) DamageUtils.damageItem(player, elytra);
			flightDamage = flightDamageLimit;
		}
		setDidTick(true);
	}

	public void setDidTick(boolean b) {
		didTick = b;
	}

	public boolean didTick() {
		return didTick;
	}

	public double getForceFeedChance() {
		return FORCE_FEED_CHANCE;
	}

	public List<ItemStack> getForceFeedItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(ItemStack item: getEquipped())
			if (item != null && EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.FORCE_FEED)) items.add(item);
		return items;
	}

	public boolean hasForceFeed() {
		return getForceFeedItems().size() > 0;
	}

	public void setIcarusDelay(int icarusDelay) {
		this.icarusDelay = icarusDelay;
	}

	public int getIcarusDelay() {
		return icarusDelay;
	}

	public void minusIcarusDelay() {
		if (icarusDelay > 0) icarusDelay--;
	}

	public List<OverkillDeath> getOverkillDeaths() {
		return overkillDeaths;
	}

	public void addOverkillDeath(OverkillDeath death) {
		overkillDeaths.add(death);
	}

	public List<AttributeLevel> getAttributes() {
		return attributes;
	}

	public void addAttribute(AttributeLevel attribute) {
		attributes.add(attribute);
	}

	public void removeAttribute(Attributable attribute, ItemEquippedSlot slot) {
		Iterator<AttributeLevel> iter = attributes.iterator();
		while (iter.hasNext()) {
			AttributeLevel level = iter.next();
			if (level.getAttribute().equals(attribute) && level.getSlot().equals(slot)) iter.remove();
		}
	}

}
