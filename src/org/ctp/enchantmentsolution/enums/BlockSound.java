package org.ctp.enchantmentsolution.enums;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;

public class BlockSound {

	private static BlockSound ANVIL = new BlockSound("BLOCK_ANVIL_BREAK", Arrays.asList(MaterialSound.ANVIL, MaterialSound.BELL, MaterialSound.CHIPPED_ANVIL, MaterialSound.DAMAGED_ANVIL), 0.3f, 1.0f);
	private static BlockSound WOOD = new BlockSound("BLOCK_WOOD_BREAK", Arrays.asList(MaterialSound.OAK_PLANKS, MaterialSound.SPRUCE_PLANKS, MaterialSound.BIRCH_PLANKS, MaterialSound.JUNGLE_PLANKS, MaterialSound.ACACIA_PLANKS, MaterialSound.DARK_OAK_PLANKS, MaterialSound.OAK_LOG, MaterialSound.SPRUCE_LOG, MaterialSound.BIRCH_LOG, MaterialSound.JUNGLE_LOG, MaterialSound.ACACIA_LOG, MaterialSound.DARK_OAK_LOG, MaterialSound.STRIPPED_SPRUCE_LOG, MaterialSound.STRIPPED_BIRCH_LOG, MaterialSound.STRIPPED_JUNGLE_LOG, MaterialSound.STRIPPED_ACACIA_LOG, MaterialSound.STRIPPED_DARK_OAK_LOG, MaterialSound.STRIPPED_OAK_LOG, MaterialSound.OAK_WOOD, MaterialSound.SPRUCE_WOOD, MaterialSound.BIRCH_WOOD, MaterialSound.JUNGLE_WOOD, MaterialSound.ACACIA_WOOD, MaterialSound.DARK_OAK_WOOD, MaterialSound.STRIPPED_OAK_WOOD, MaterialSound.STRIPPED_SPRUCE_WOOD, MaterialSound.STRIPPED_BIRCH_WOOD, MaterialSound.STRIPPED_JUNGLE_WOOD, MaterialSound.STRIPPED_ACACIA_WOOD, MaterialSound.STRIPPED_DARK_OAK_WOOD, MaterialSound.NOTE_BLOCK, MaterialSound.WHITE_BED, MaterialSound.ORANGE_BED, MaterialSound.MAGENTA_BED, MaterialSound.LIGHT_BLUE_BED, MaterialSound.YELLOW_BED, MaterialSound.LIME_BED, MaterialSound.PINK_BED, MaterialSound.GRAY_BED, MaterialSound.LIGHT_GRAY_BED, MaterialSound.CYAN_BED, MaterialSound.PURPLE_BED, MaterialSound.BLUE_BED, MaterialSound.BROWN_BED, MaterialSound.GREEN_BED, MaterialSound.RED_BED, MaterialSound.BLACK_BED, MaterialSound.BOOKSHELF, MaterialSound.TORCH, MaterialSound.WALL_TORCH, MaterialSound.OAK_STAIRS, MaterialSound.CHEST, MaterialSound.CRAFTING_TABLE, MaterialSound.OAK_SIGN, MaterialSound.SPRUCE_SIGN, MaterialSound.BIRCH_SIGN, MaterialSound.ACACIA_SIGN, MaterialSound.JUNGLE_SIGN, MaterialSound.DARK_OAK_SIGN, MaterialSound.OAK_DOOR, MaterialSound.OAK_WALL_SIGN, MaterialSound.SPRUCE_WALL_SIGN, MaterialSound.BIRCH_WALL_SIGN, MaterialSound.ACACIA_WALL_SIGN, MaterialSound.JUNGLE_WALL_SIGN, MaterialSound.DARK_OAK_WALL_SIGN, MaterialSound.LEVER, MaterialSound.OAK_PRESSURE_PLATE, MaterialSound.SPRUCE_PRESSURE_PLATE, MaterialSound.BIRCH_PRESSURE_PLATE, MaterialSound.JUNGLE_PRESSURE_PLATE, MaterialSound.ACACIA_PRESSURE_PLATE, MaterialSound.DARK_OAK_PRESSURE_PLATE, MaterialSound.OAK_FENCE, MaterialSound.PUMPKIN, MaterialSound.CARVED_PUMPKIN, MaterialSound.JACK_O_LANTERN, MaterialSound.REPEATER, MaterialSound.OAK_TRAPDOOR, MaterialSound.SPRUCE_TRAPDOOR, MaterialSound.BIRCH_TRAPDOOR, MaterialSound.JUNGLE_TRAPDOOR, MaterialSound.ACACIA_TRAPDOOR, MaterialSound.DARK_OAK_TRAPDOOR, MaterialSound.BROWN_MUSHROOM_BLOCK, MaterialSound.RED_MUSHROOM_BLOCK, MaterialSound.MUSHROOM_STEM, MaterialSound.MELON, MaterialSound.ATTACHED_PUMPKIN_STEM, MaterialSound.ATTACHED_MELON_STEM, MaterialSound.OAK_FENCE_GATE, MaterialSound.COCOA, MaterialSound.OAK_BUTTON, MaterialSound.SPRUCE_BUTTON, MaterialSound.BIRCH_BUTTON, MaterialSound.JUNGLE_BUTTON, MaterialSound.ACACIA_BUTTON, MaterialSound.DARK_OAK_BUTTON, MaterialSound.TRAPPED_CHEST, MaterialSound.LIGHT_WEIGHTED_PRESSURE_PLATE, MaterialSound.HEAVY_WEIGHTED_PRESSURE_PLATE, MaterialSound.COMPARATOR, MaterialSound.DAYLIGHT_DETECTOR, MaterialSound.WHITE_BANNER, MaterialSound.ORANGE_BANNER, MaterialSound.MAGENTA_BANNER, MaterialSound.LIGHT_BLUE_BANNER, MaterialSound.YELLOW_BANNER, MaterialSound.LIME_BANNER, MaterialSound.PINK_BANNER, MaterialSound.GRAY_BANNER, MaterialSound.LIGHT_GRAY_BANNER, MaterialSound.CYAN_BANNER, MaterialSound.PURPLE_BANNER, MaterialSound.BLUE_BANNER, MaterialSound.BROWN_BANNER, MaterialSound.GREEN_BANNER, MaterialSound.RED_BANNER, MaterialSound.BLACK_BANNER, MaterialSound.WHITE_WALL_BANNER, MaterialSound.ORANGE_WALL_BANNER, MaterialSound.MAGENTA_WALL_BANNER, MaterialSound.LIGHT_BLUE_WALL_BANNER, MaterialSound.YELLOW_WALL_BANNER, MaterialSound.LIME_WALL_BANNER, MaterialSound.PINK_WALL_BANNER, MaterialSound.GRAY_WALL_BANNER, MaterialSound.LIGHT_GRAY_WALL_BANNER, MaterialSound.CYAN_WALL_BANNER, MaterialSound.PURPLE_WALL_BANNER, MaterialSound.BLUE_WALL_BANNER, MaterialSound.BROWN_WALL_BANNER, MaterialSound.GREEN_WALL_BANNER, MaterialSound.RED_WALL_BANNER, MaterialSound.BLACK_WALL_BANNER, MaterialSound.OAK_SLAB, MaterialSound.SPRUCE_SLAB, MaterialSound.BIRCH_SLAB, MaterialSound.JUNGLE_SLAB, MaterialSound.ACACIA_SLAB, MaterialSound.DARK_OAK_SLAB, MaterialSound.SPRUCE_FENCE_GATE, MaterialSound.BIRCH_FENCE_GATE, MaterialSound.JUNGLE_FENCE_GATE, MaterialSound.ACACIA_FENCE_GATE, MaterialSound.DARK_OAK_FENCE_GATE, MaterialSound.SPRUCE_FENCE, MaterialSound.BIRCH_FENCE, MaterialSound.JUNGLE_FENCE, MaterialSound.ACACIA_FENCE, MaterialSound.DARK_OAK_FENCE, MaterialSound.SPRUCE_DOOR, MaterialSound.BIRCH_DOOR, MaterialSound.JUNGLE_DOOR, MaterialSound.ACACIA_DOOR, MaterialSound.DARK_OAK_DOOR, MaterialSound.END_ROD, MaterialSound.CHORUS_PLANT, MaterialSound.CHORUS_FLOWER, MaterialSound.NETHER_WART_BLOCK, MaterialSound.LOOM, MaterialSound.BARREL, MaterialSound.CARTOGRAPHY_TABLE, MaterialSound.FLETCHING_TABLE, MaterialSound.LECTERN, MaterialSound.SMITHING_TABLE, MaterialSound.CAMPFIRE, MaterialSound.COMPOSTER, MaterialSound.SPRUCE_STAIRS, MaterialSound.BIRCH_STAIRS, MaterialSound.JUNGLE_STAIRS, MaterialSound.ACACIA_STAIRS, MaterialSound.DARK_OAK_STAIRS, MaterialSound.PUMPKIN_STEM, MaterialSound.MELON_STEM));
	private static BlockSound GRAVEL = new BlockSound("BLOCK_GRAVEL_BREAK", Arrays.asList(MaterialSound.DIRT, MaterialSound.COARSE_DIRT, MaterialSound.PODZOL, MaterialSound.FARMLAND, MaterialSound.CLAY, MaterialSound.GRAVEL));
	private static BlockSound GRASS = new BlockSound("BLOCK_GRASS_BREAK", Arrays.asList(MaterialSound.GRASS_BLOCK, MaterialSound.OAK_LEAVES, MaterialSound.SPRUCE_LEAVES, MaterialSound.BIRCH_LEAVES, MaterialSound.JUNGLE_LEAVES, MaterialSound.ACACIA_LEAVES, MaterialSound.DARK_OAK_LEAVES, MaterialSound.SPONGE, MaterialSound.WET_SPONGE, MaterialSound.GRASS, MaterialSound.FERN, MaterialSound.DEAD_BUSH, MaterialSound.DANDELION, MaterialSound.POPPY, MaterialSound.BLUE_ORCHID, MaterialSound.ALLIUM, MaterialSound.AZURE_BLUET, MaterialSound.RED_TULIP, MaterialSound.ORANGE_TULIP, MaterialSound.WHITE_TULIP, MaterialSound.PINK_TULIP, MaterialSound.OXEYE_DAISY, MaterialSound.CORNFLOWER, MaterialSound.WITHER_ROSE, MaterialSound.LILY_OF_THE_VALLEY, MaterialSound.BROWN_MUSHROOM, MaterialSound.RED_MUSHROOM, MaterialSound.TNT, MaterialSound.SUGAR_CANE, MaterialSound.VINE, MaterialSound.MYCELIUM, MaterialSound.LILY_PAD, MaterialSound.HAY_BLOCK, MaterialSound.SUNFLOWER, MaterialSound.LILAC, MaterialSound.ROSE_BUSH, MaterialSound.PEONY, MaterialSound.TALL_GRASS, MaterialSound.LARGE_FERN, MaterialSound.GRASS_PATH, MaterialSound.DRIED_KELP_BLOCK, MaterialSound.OAK_SAPLING, MaterialSound.SPRUCE_SAPLING, MaterialSound.BIRCH_SAPLING, MaterialSound.JUNGLE_SAPLING, MaterialSound.ACACIA_SAPLING, MaterialSound.DARK_OAK_SAPLING, MaterialSound.OAK_STAIRS, MaterialSound.JUKEBOX));
	private static BlockSound METAL = new BlockSound("BLOCK_METAL_BREAK", Arrays.asList(MaterialSound.POWERED_RAIL, MaterialSound.DETECTOR_RAIL, MaterialSound.GOLD_BLOCK, MaterialSound.IRON_BLOCK, MaterialSound.SPAWNER, MaterialSound.DIAMOND_BLOCK, MaterialSound.RAIL, MaterialSound.IRON_DOOR, MaterialSound.IRON_BARS, MaterialSound.EMERALD_BLOCK, MaterialSound.REDSTONE_BLOCK, MaterialSound.HOPPER, MaterialSound.ACTIVATOR_RAIL, MaterialSound.IRON_TRAPDOOR, MaterialSound.TURTLE_EGG));
	private static BlockSound GLASS = new BlockSound("BLOCK_GLASS_BREAK", Arrays.asList(MaterialSound.BLUE_ICE, MaterialSound.GLASS, MaterialSound.ICE, MaterialSound.GLOWSTONE, MaterialSound.NETHER_PORTAL, MaterialSound.WHITE_STAINED_GLASS, MaterialSound.ORANGE_STAINED_GLASS, MaterialSound.MAGENTA_STAINED_GLASS, MaterialSound.LIGHT_BLUE_STAINED_GLASS, MaterialSound.YELLOW_STAINED_GLASS, MaterialSound.LIME_STAINED_GLASS, MaterialSound.PINK_STAINED_GLASS, MaterialSound.GRAY_STAINED_GLASS, MaterialSound.LIGHT_GRAY_STAINED_GLASS, MaterialSound.CYAN_STAINED_GLASS, MaterialSound.PURPLE_STAINED_GLASS, MaterialSound.BLUE_STAINED_GLASS, MaterialSound.BROWN_STAINED_GLASS, MaterialSound.GREEN_STAINED_GLASS, MaterialSound.RED_STAINED_GLASS, MaterialSound.BLACK_STAINED_GLASS, MaterialSound.GLASS_PANE, MaterialSound.END_PORTAL_FRAME, MaterialSound.REDSTONE_LAMP, MaterialSound.WHITE_STAINED_GLASS_PANE, MaterialSound.ORANGE_STAINED_GLASS_PANE, MaterialSound.MAGENTA_STAINED_GLASS_PANE, MaterialSound.LIGHT_BLUE_STAINED_GLASS_PANE, MaterialSound.YELLOW_STAINED_GLASS_PANE, MaterialSound.LIME_STAINED_GLASS_PANE, MaterialSound.PINK_STAINED_GLASS_PANE, MaterialSound.GRAY_STAINED_GLASS_PANE, MaterialSound.LIGHT_GRAY_STAINED_GLASS_PANE, MaterialSound.CYAN_STAINED_GLASS_PANE, MaterialSound.PURPLE_STAINED_GLASS_PANE, MaterialSound.BLUE_STAINED_GLASS_PANE, MaterialSound.BROWN_STAINED_GLASS_PANE, MaterialSound.GREEN_STAINED_GLASS_PANE, MaterialSound.RED_STAINED_GLASS_PANE, MaterialSound.BLACK_STAINED_GLASS_PANE, MaterialSound.SEA_LANTERN, MaterialSound.PACKED_ICE, MaterialSound.FROSTED_ICE));
	private static BlockSound WOOL = new BlockSound("BLOCK_WOOL_BREAK", Arrays.asList(MaterialSound.WHITE_WOOL, MaterialSound.ORANGE_WOOL, MaterialSound.MAGENTA_WOOL, MaterialSound.LIGHT_BLUE_WOOL, MaterialSound.YELLOW_WOOL, MaterialSound.LIME_WOOL, MaterialSound.PINK_WOOL, MaterialSound.GRAY_WOOL, MaterialSound.LIGHT_GRAY_WOOL, MaterialSound.CYAN_WOOL, MaterialSound.PURPLE_WOOL, MaterialSound.BLUE_WOOL, MaterialSound.BROWN_WOOL, MaterialSound.GREEN_WOOL, MaterialSound.RED_WOOL, MaterialSound.BLACK_WOOL, MaterialSound.FIRE, MaterialSound.CACTUS, MaterialSound.CAKE, MaterialSound.WHITE_CARPET, MaterialSound.ORANGE_CARPET, MaterialSound.MAGENTA_CARPET, MaterialSound.LIGHT_BLUE_CARPET, MaterialSound.YELLOW_CARPET, MaterialSound.LIME_CARPET, MaterialSound.PINK_CARPET, MaterialSound.GRAY_CARPET, MaterialSound.LIGHT_GRAY_CARPET, MaterialSound.CYAN_CARPET, MaterialSound.PURPLE_CARPET, MaterialSound.BLUE_CARPET, MaterialSound.BROWN_CARPET, MaterialSound.GREEN_CARPET, MaterialSound.RED_CARPET, MaterialSound.BLACK_CARPET));
	private static BlockSound SAND = new BlockSound("BLOCK_SAND_BREAK", Arrays.asList(MaterialSound.WHITE_CONCRETE_POWDER, MaterialSound.ORANGE_CONCRETE_POWDER, MaterialSound.MAGENTA_CONCRETE_POWDER, MaterialSound.LIGHT_BLUE_CONCRETE_POWDER, MaterialSound.YELLOW_CONCRETE_POWDER, MaterialSound.LIME_CONCRETE_POWDER, MaterialSound.PINK_CONCRETE_POWDER, MaterialSound.GRAY_CONCRETE_POWDER, MaterialSound.LIGHT_GRAY_CONCRETE_POWDER, MaterialSound.CYAN_CONCRETE_POWDER, MaterialSound.PURPLE_CONCRETE_POWDER, MaterialSound.BLUE_CONCRETE_POWDER, MaterialSound.BROWN_CONCRETE_POWDER, MaterialSound.GREEN_CONCRETE_POWDER, MaterialSound.RED_CONCRETE_POWDER, MaterialSound.BLACK_CONCRETE_POWDER, MaterialSound.SAND, MaterialSound.RED_SAND, MaterialSound.SOUL_SAND));
	private static BlockSound SNOW = new BlockSound("BLOCK_SNOW_BREAK", Arrays.asList(MaterialSound.SNOW, MaterialSound.SNOW_BLOCK));
	private static BlockSound LADDER = new BlockSound("BLOCK_LADDER_BREAK", Arrays.asList(MaterialSound.LADDER));
	private static BlockSound SLIME_BLOCK = new BlockSound("BLOCK_SLIME_BLOCK_BREAK", Arrays.asList(MaterialSound.SLIME_BLOCK, MaterialSound.SEA_PICKLE));
	private static BlockSound HONEY_BLOCK = new BlockSound("BLOCK_HONEY_BLOCK_BREAK", Arrays.asList(MaterialSound.HONEY_BLOCK));
	private static BlockSound WET_GRASS = new BlockSound("BLOCK_WET_GRASS_BREAK", Arrays.asList(MaterialSound.SEAGRASS, MaterialSound.TALL_SEAGRASS, MaterialSound.KELP, MaterialSound.KELP_PLANT, MaterialSound.TUBE_CORAL, MaterialSound.BRAIN_CORAL, MaterialSound.BUBBLE_CORAL, MaterialSound.FIRE_CORAL, MaterialSound.HORN_CORAL, MaterialSound.TUBE_CORAL_FAN, MaterialSound.BRAIN_CORAL_FAN, MaterialSound.BUBBLE_CORAL_FAN, MaterialSound.FIRE_CORAL_FAN, MaterialSound.HORN_CORAL_FAN, MaterialSound.TUBE_CORAL_WALL_FAN, MaterialSound.BRAIN_CORAL_WALL_FAN, MaterialSound.BUBBLE_CORAL_WALL_FAN, MaterialSound.FIRE_CORAL_WALL_FAN, MaterialSound.HORN_CORAL_WALL_FAN));
	private static BlockSound CORAL_BLOCK = new BlockSound("BLOCK_CORAL_BLOCK_BREAK", Arrays.asList(MaterialSound.TUBE_CORAL_BLOCK, MaterialSound.BRAIN_CORAL_BLOCK, MaterialSound.BUBBLE_CORAL_BLOCK, MaterialSound.FIRE_CORAL_BLOCK, MaterialSound.HORN_CORAL_BLOCK, MaterialSound.HONEYCOMB_BLOCK));
	private static BlockSound BAMBOO = new BlockSound("BLOCK_BAMBOO_BREAK", Arrays.asList(MaterialSound.BAMBOO));
	private static BlockSound BAMBOO_SAPLING = new BlockSound("BLOCK_BAMBOO_SAPLING_BREAK", Arrays.asList(MaterialSound.BAMBOO_SAPLING));
	private static BlockSound SCAFFOLDING = new BlockSound("BLOCK_SCAFFOLDING_BREAK", Arrays.asList(MaterialSound.SCAFFOLDING));
	private static BlockSound SWEET_BERRY_BUSH = new BlockSound("BLOCK_SWEET_BERRY_BUSH_BREAK", Arrays.asList(MaterialSound.SWEET_BERRY_BUSH));
	private static BlockSound CROP = new BlockSound("BLOCK_CROP_BREAK", Arrays.asList(MaterialSound.WHEAT, MaterialSound.CARROTS, MaterialSound.POTATOES, MaterialSound.BEETROOTS));
	private static BlockSound NETHER_WART = new BlockSound("BLOCK_NETHER_WART_BREAK", Arrays.asList(MaterialSound.NETHER_WART));
	private static BlockSound LANTERN = new BlockSound("BLOCK_LANTERN_BREAK", Arrays.asList(MaterialSound.LANTERN));
	private static BlockSound STONE = new BlockSound("BLOCK_STONE_BREAK", Arrays.asList());
	private static List<BlockSound> SOUNDS = Arrays.asList(ANVIL, WOOD, GRAVEL, GRASS, METAL, GLASS, WOOL, SAND, SNOW, LADDER, SLIME_BLOCK, HONEY_BLOCK, WET_GRASS, CORAL_BLOCK, BAMBOO, BAMBOO_SAPLING, SCAFFOLDING, SWEET_BERRY_BUSH, CROP, NETHER_WART, LANTERN);

	private final List<MaterialSound> types;
	private final String sound;
	private final float volume, pitch;

	private BlockSound(String sound, List<MaterialSound> types) {
		this(sound, types, 1.0F, 1.0F);
	}

	private BlockSound(String sound, List<MaterialSound> types, float volume, float pitch) {
		this.sound = sound;
		this.types = types;
		this.volume = volume;
		this.pitch = pitch;
	}

	public List<MaterialSound> getTypes() {
		return types;
	}

	public Sound getSound() {
		return Sound.valueOf(sound);
	}

	public String getSoundString() {
		return sound;
	}

	public float getVolume() {
		return volume;
	}

	public float getPitch() {
		return pitch;
	}

	public float getVolume(Material m) {
		for(MaterialSound type: getTypes())
			if (type.getMaterial().hasMaterial() && type.getMaterial().getMaterial() == m && type.getVolume() > 0) return type.getVolume();
		return volume;
	}

	public float getPitch(Material m) {
		for(MaterialSound type: getTypes())
			if (type.getMaterial().hasMaterial() && type.getMaterial().getMaterial() == m && type.getPitch() > 0) return type.getPitch();
		return pitch;
	}

	public static BlockSound getSound(Material m) {
		for(BlockSound sound: SOUNDS) {
			if (sound.getSound() == null) continue;
			for(MaterialSound type: sound.getTypes())
				if (type.getMaterial().hasMaterial() && type.getMaterial().getMaterial() == m) return sound;
		}
		return STONE;
	}
}
