package org.ctp.enchantmentsolution.utils.player;

import java.util.*;

import org.bukkit.*;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.ctp.crashapi.events.ItemEquipEvent.HandMethod;
import org.ctp.crashapi.item.*;
import org.ctp.crashapi.nms.ServerNMS;
import org.ctp.crashapi.utils.DamageUtils;
import org.ctp.crashapi.utils.ItemUtils;
import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.advancements.ESAdvancement;
import org.ctp.enchantmentsolution.enchantments.*;
import org.ctp.enchantmentsolution.events.player.FrequentFlyerEvent;
import org.ctp.enchantmentsolution.events.player.FrequentFlyerEvent.FFType;
import org.ctp.enchantmentsolution.listeners.enchantments.AsyncGaiaController;
import org.ctp.enchantmentsolution.listeners.enchantments.AsyncHWDController;
import org.ctp.enchantmentsolution.rpg.RPGPlayer;
import org.ctp.enchantmentsolution.rpg.RPGUtils;
import org.ctp.enchantmentsolution.utils.AdvancementUtils;
import org.ctp.enchantmentsolution.utils.PermissionUtils;
import org.ctp.enchantmentsolution.utils.abilityhelpers.GaiaUtils.GaiaTrees;
import org.ctp.enchantmentsolution.utils.abilityhelpers.ItemEquippedSlot;
import org.ctp.enchantmentsolution.utils.abilityhelpers.OverkillDeath;
import org.ctp.enchantmentsolution.utils.abilityhelpers.Streak;
import org.ctp.enchantmentsolution.utils.config.ConfigString;
import org.ctp.enchantmentsolution.utils.items.AbilityUtils;
import org.ctp.enchantmentsolution.utils.items.EnchantmentUtils;
import org.ctp.enchantmentsolution.utils.player.attributes.FlySpeedAttribute;

public class ESPlayer {

	private static Map<Long, Integer> GLOBAL_BLOCKS = new HashMap<Long, Integer>();
	private static double CONTAGION_CHANCE = 0.0005;
	private final OfflinePlayer player;
	private Player onlinePlayer;
	private RPGPlayer rpg;
	private Map<Enchantment, Long> cooldowns;
	private List<EnchantmentTimedDisable> timedDisable;
	private List<EnchantmentDisable> disable;
	private List<ItemStack> soulItems, telepathyItems;
	private Map<Long, Integer> blocksBroken;
	private float currentExhaustion, pastExhaustion;
	private ItemStack elytra;
	private boolean canFly, didTick, reset, sacrificeAdvancement, plyometricsAdvancement;
	private int flightDamage, flightDamageLimit, frequentFlyerLevel, icarusDelay, underwaterTicks;
	private FFType currentFFType;
	private List<OverkillDeath> overkillDeaths;
	private List<AttributeLevel> attributes;
	private ESPlayerAttributeInstance flyAttribute = new FlySpeedAttribute();
	private Streak streak;
	private Runnable telepathyTask;
	private AsyncHWDController hwdController;
	private AsyncGaiaController gaiaController;

	public ESPlayer(OfflinePlayer player) {
		this.player = player;
		onlinePlayer = player.getPlayer();
		rpg = RPGUtils.getPlayer(player);
		cooldowns = new HashMap<Enchantment, Long>();
		timedDisable = new ArrayList<EnchantmentTimedDisable>();
		disable = new ArrayList<EnchantmentDisable>();
		blocksBroken = new HashMap<Long, Integer>();
		overkillDeaths = new ArrayList<OverkillDeath>();
		attributes = new ArrayList<AttributeLevel>();
		currentFFType = FFType.NONE;
		telepathyItems = new ArrayList<ItemStack>();
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

	public boolean isInInventory(ItemStack item) {
		for(int i = 0; i < 41; i++) {
			ItemStack content = getOnlinePlayer().getInventory().getItem(i);
			if (content == null) continue;
			if (item.equals(content)) return true;
		}
		return false;
	}

	public ItemStack getMainHand() {
		if (!isOnline()) return new ItemStack(Material.AIR);
		return getOnlinePlayer().getInventory().getItemInMainHand();
	}

	public ItemStack getOffhand() {
		if (!isOnline()) return new ItemStack(Material.AIR);
		return getOnlinePlayer().getInventory().getItemInOffHand();
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

	public ItemSlot[] getArmorAndType() {
		ItemSlot[] armor = new ItemSlot[4];
		if (!isOnline()) return armor;
		armor[0] = new ItemSlot(getOnlinePlayer().getInventory().getHelmet(), ItemSlotType.HELMET);
		armor[1] = new ItemSlot(getOnlinePlayer().getInventory().getChestplate(), ItemSlotType.CHESTPLATE);
		armor[2] = new ItemSlot(getOnlinePlayer().getInventory().getLeggings(), ItemSlotType.LEGGINGS);
		armor[3] = new ItemSlot(getOnlinePlayer().getInventory().getBoots(), ItemSlotType.BOOTS);

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

	public ItemStack[] getInventoryItems() {
		return isOnline() ? getOnlinePlayer().getInventory().getContents() : new ItemStack[41];
	}

	public List<ItemStack> getUnstableItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(ItemStack item: getEquipped())
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_INSTABILITY)) items.add(item);
		return items;
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
		long tick = ServerNMS.getCurrentTick();
		if (GLOBAL_BLOCKS.containsKey(tick) && GLOBAL_BLOCKS.get(tick) >= ConfigString.MULTI_BLOCK_BLOCKS_GLOBAL.getInt()) return false;
		if (blocksBroken.containsKey(tick) && blocksBroken.get(tick) >= ConfigString.MULTI_BLOCK_BLOCKS_PLAYER.getInt()) return false;
		return true;
	}

	public void breakBlock() {
		long tick = ServerNMS.getCurrentTick();
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
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.CURSE_OF_CONTAGION)) playerChance += CONTAGION_CHANCE;
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
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.FREQUENT_FLYER) && !DamageUtils.aboveMaxDamage(item)) {
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
			canFly = false;
			currentFFType = FFType.NONE;
			player.setAllowFlight(canFly);
			if (player.isFlying() && !canFly) player.setFlying(false);
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
		flightDamage--;
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

	public double getForceFeedChance(int level) {
		return 0.0075 + level * 0.0075;
	}

	public List<ItemStack> getForceFeedItems() {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for(ItemStack item: getEquipped())
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.FORCE_FEED)) items.add(item);
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

	public void giveTelepathyItems() {
		Collection<ItemStack> newItems = new ArrayList<ItemStack>(telepathyItems);
		telepathyItems = new ArrayList<ItemStack>();
		telepathyTask = null;
		Player p = Bukkit.getPlayer(getOnlinePlayer().getUniqueId());
		ItemUtils.giveItemsToPlayer(p, newItems, p.getLocation(), true, HandMethod.PICK_UP);
	}

	public void addTelepathyItems(Collection<ItemStack> items) {
		checkTelepathyTask();
		for(ItemStack item: items)
			addTelepathyItem(item);
		checkTelepathyTask();
	}

	private void checkTelepathyTask() {
		if (telepathyTask == null) {
			telepathyTask = () -> giveTelepathyItems();
			Bukkit.getScheduler().runTaskLater(EnchantmentSolution.getPlugin(), telepathyTask, 0l);
		}
	}

	private void addTelepathyItem(ItemStack item) {
		checkTelepathyTask();
		int amount = item.getAmount();
		for(ItemStack i: telepathyItems)
			if (i.isSimilar(item) && i.getMaxStackSize() > 1) if (i.getAmount() == i.getMaxStackSize()) continue;
			else if (i.getAmount() + amount > i.getMaxStackSize()) {
				amount -= i.getMaxStackSize();
				i.setAmount(i.getMaxStackSize());
				break;
			} else {
				i.setAmount(amount + i.getAmount());
				amount = 0;
				break;
			}
			else {}
		if (amount > 0) {
			item.setAmount(amount);
			telepathyItems.add(item);
		}
		checkTelepathyTask();
	}

	public int addToStreak(LivingEntity entity) {
		if (streak == null) streak = new Streak();
		int entityStreak = streak.addToStreak(entity);
		if (entityStreak == 100) AdvancementUtils.awardCriteria(onlinePlayer, ESAdvancement.DOUBLE_DAMAGE, "kills");
		return entityStreak;
	}

	public int getStreak() {
		if (streak == null) return 0;
		return streak.getStreak();
	}

	public EntityType getType() {
		return streak.getType();
	}

	public void setStreak(EntityType type, int streak) {
		this.streak.setStreak(type, streak);
	}

	public void addTimedDisableEnchant(JavaPlugin plugin, Enchantment enchant, int ticks) {
		long tick = ServerNMS.getCurrentTick() + ticks;
		if (!isTimedDisableEnchant(plugin, enchant)) timedDisable.add(new EnchantmentTimedDisable(plugin, enchant, tick));
	}

	public void addTimeToDisableEnchant(JavaPlugin plugin, Enchantment enchant, int moreTicks) {
		if (isTimedDisableEnchant(plugin, enchant)) {
			EnchantmentTimedDisable disable = getTimedDisable(plugin, enchant);
			disable.addToEndTime(moreTicks);
		} else
			addTimedDisableEnchant(plugin, enchant, moreTicks);
	}

	private EnchantmentTimedDisable getTimedDisable(JavaPlugin plugin, Enchantment enchant) {
		for(EnchantmentTimedDisable etd: timedDisable)
			if (etd.isSimilar(plugin, enchant)) return etd;
		return null;
	}

	public void setTimeDisableEnchant(JavaPlugin plugin, Enchantment enchant, int ticks) {
		long tick = ServerNMS.getCurrentTick() + ticks;
		if (isTimedDisableEnchant(plugin, enchant)) {
			EnchantmentTimedDisable disable = getTimedDisable(plugin, enchant);
			disable.setEndTime(tick);
		} else
			addTimedDisableEnchant(plugin, enchant, ticks);
	}

	public void removeTimedDisableEnchant(JavaPlugin plugin, Enchantment enchant) {
		Iterator<EnchantmentTimedDisable> iter = timedDisable.iterator();
		while (iter.hasNext()) {
			EnchantmentTimedDisable etd = iter.next();
			if (etd.isSimilar(plugin, enchant)) iter.remove();
		}
	}

	public void removeTimeFromDisableEnchant(JavaPlugin plugin, Enchantment enchant, int lessTicks) {
		if (isTimedDisableEnchant(plugin, enchant)) {
			EnchantmentTimedDisable disable = getTimedDisable(plugin, enchant);
			disable.removeFromEndTime(lessTicks);
		}
	}

	public boolean isTimedDisableEnchant(JavaPlugin plugin, Enchantment enchant) {
		for(EnchantmentTimedDisable etd: timedDisable)
			if (etd.isSimilar(plugin, enchant)) return true;
		return false;
	}

	public boolean hasTimedDisable(Player player, Enchantment enchant) {
		for(EnchantmentTimedDisable etd: timedDisable)
			if (etd.getEnchantment() == enchant) return true;
		return false;
	}

	public void setDisabledEnchant(JavaPlugin plugin, Enchantment enchant) {
		if (!isDisabledEnchant(plugin, enchant)) disable.add(new EnchantmentDisable(plugin, enchant));
	}

	public boolean isDisabledEnchant(JavaPlugin plugin, Enchantment enchant) {
		for(EnchantmentDisable e: disable)
			if (e.isSimilar(plugin, enchant)) return true;
		return false;
	}

	public void removeDisabledEnchant(JavaPlugin plugin, Enchantment enchant) {
		Iterator<EnchantmentDisable> iter = disable.iterator();
		while (iter.hasNext()) {
			EnchantmentDisable e = iter.next();
			if (e.isSimilar(plugin, enchant)) iter.remove();
		}
	}

	public boolean hasDisabled(Player player, Enchantment enchant) {
		for(EnchantmentDisable e: disable)
			if (e.getEnchantment() == enchant) return true;
		return false;
	}

	public boolean getSacrificeAdvancement() {
		return sacrificeAdvancement;
	}

	public void setSacrificeAdvancement(boolean sacrificeAdvancement) {
		this.sacrificeAdvancement = sacrificeAdvancement;
	}

	public boolean getPlyometricsAdvancement() {
		return plyometricsAdvancement;
	}

	public void setPlyometricsAdvancement(boolean plyometricsAdvancement) {
		this.plyometricsAdvancement = plyometricsAdvancement;
	}

	public void addUnderwaterTick() {
		for(ItemStack item: getArmor())
			if (EnchantmentUtils.hasEnchantment(item, RegisterEnchantments.WATER_BREATHING)) {
				underwaterTicks++;
				if (underwaterTicks >= 13900) AdvancementUtils.awardCriteria(onlinePlayer, ESAdvancement.WORLD_RECORD, "record");
				return;
			}
		resetUnderwaterTick();
	}

	public void resetUnderwaterTick() {
		underwaterTicks = 0;
	}

	public void addToHWDController(ItemStack item, Block original, List<Location> allBlocks) {
		if (hwdController == null) hwdController = new AsyncHWDController(getOnlinePlayer(), item, original, allBlocks);
		else if (!hwdController.addBlocks(original, allBlocks)) hwdController = new AsyncHWDController(getOnlinePlayer(), item, original, allBlocks);
	}

	public boolean correctHWDItem(ItemStack item) {
		return hwdController == null || item.isSimilar(hwdController.getItem());
	}

	public void removeHWDController() {
		hwdController = null;
	}

	public void addToGaiaController(ItemStack item, Block original, List<Location> allBlocks, GaiaTrees tree) {
		if (gaiaController == null) gaiaController = new AsyncGaiaController(getOnlinePlayer(), item, original, allBlocks, tree);
		else if (!gaiaController.addBlocks(original, allBlocks)) gaiaController = new AsyncGaiaController(getOnlinePlayer(), item, original, allBlocks, tree);
	}

	public boolean correctGaiaItem(ItemStack item) {
		return gaiaController == null || item.isSimilar(gaiaController.getItem());
	}

	public boolean correctGaiaTree(GaiaTrees tree) {
		return gaiaController == null || tree == gaiaController.getTree();
	}

	public void removeGaiaController() {
		gaiaController = null;
	}
}
