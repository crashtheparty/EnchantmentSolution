package org.ctp.enchantmentsolution.enchantments.level50;

import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Angler;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Beheading;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Brine;
import org.ctp.enchantmentsolution.enchantments.level50.custom.ExpShare;
import org.ctp.enchantmentsolution.enchantments.level50.custom.FrequentFlyer;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Fried;
import org.ctp.enchantmentsolution.enchantments.level50.custom.KnockUp;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Life;
import org.ctp.enchantmentsolution.enchantments.level50.custom.MagmaWalker;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Sacrifice;
import org.ctp.enchantmentsolution.enchantments.level50.custom.ShockAspect;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Smeltery;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Sniper;
import org.ctp.enchantmentsolution.enchantments.level50.custom.SoulReaper;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Soulbound;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Tank;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Telepathy;
import org.ctp.enchantmentsolution.enchantments.level50.custom.Warp;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.AquaAffinity;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.BaneOfArthropods;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.BlastProtection;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.CurseOfBinding;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.CurseOfVanishing;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.DepthStrider;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Efficiency;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.FeatherFalling;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.FireAspect;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.FireProtection;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Flame;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Fortune;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.FrostWalker;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Infinity;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Knockback;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Looting;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.LuckOfTheSea;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Lure;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Mending;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Power;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.ProjectileProtection;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Protection;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Punch;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Respiration;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Sharpness;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.SilkTouch;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Smite;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.SweepingEdge;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Thorns;
import org.ctp.enchantmentsolution.enchantments.level50.vanilla.Unbreaking;
import org.ctp.enchantmentsolution.nms.Version;

public class LevelFiftyEnchants {
	public static void addDefaultEnchantments() {
		if(DefaultEnchantments.getEnchantments().size() > 0) return;
		DefaultEnchantments.addDefaultEnchantment(new AquaAffinity());
		DefaultEnchantments.addDefaultEnchantment(new BaneOfArthropods());
		DefaultEnchantments.addDefaultEnchantment(new BlastProtection());
		DefaultEnchantments.addDefaultEnchantment(new DepthStrider());
		DefaultEnchantments.addDefaultEnchantment(new Efficiency());
		DefaultEnchantments.addDefaultEnchantment(new FeatherFalling());
		DefaultEnchantments.addDefaultEnchantment(new FireAspect());
		DefaultEnchantments.addDefaultEnchantment(new FireProtection());
		DefaultEnchantments.addDefaultEnchantment(new Flame());
		DefaultEnchantments.addDefaultEnchantment(new Fortune());
		DefaultEnchantments.addDefaultEnchantment(new Infinity());
		DefaultEnchantments.addDefaultEnchantment(new Knockback());
		DefaultEnchantments.addDefaultEnchantment(new Looting());
		DefaultEnchantments.addDefaultEnchantment(new LuckOfTheSea());
		DefaultEnchantments.addDefaultEnchantment(new Lure());
		DefaultEnchantments.addDefaultEnchantment(new Power());
		DefaultEnchantments.addDefaultEnchantment(new ProjectileProtection());
		DefaultEnchantments.addDefaultEnchantment(new Protection());
		DefaultEnchantments.addDefaultEnchantment(new Punch());
		DefaultEnchantments.addDefaultEnchantment(new Respiration());
		DefaultEnchantments.addDefaultEnchantment(new Sharpness());
		DefaultEnchantments.addDefaultEnchantment(new SilkTouch());
		DefaultEnchantments.addDefaultEnchantment(new Smite());
		DefaultEnchantments.addDefaultEnchantment(new Thorns());
		DefaultEnchantments.addDefaultEnchantment(new Unbreaking());
		
		if(Version.VERSION_NUMBER > 1) {
			DefaultEnchantments.addDefaultEnchantment(new Mending());
			DefaultEnchantments.addDefaultEnchantment(new FrostWalker());
		}
		
		if(Version.VERSION_NUMBER > 5) {
			DefaultEnchantments.addDefaultEnchantment(new CurseOfBinding());
			DefaultEnchantments.addDefaultEnchantment(new CurseOfVanishing());
		}
		
		if(Version.VERSION_NUMBER > 6) {
			DefaultEnchantments.addDefaultEnchantment(new SweepingEdge());
		}

		DefaultEnchantments.addDefaultEnchantment(new Soulbound());
		DefaultEnchantments.addDefaultEnchantment(new SoulReaper());
		DefaultEnchantments.addDefaultEnchantment(new ShockAspect());
		DefaultEnchantments.addDefaultEnchantment(new Beheading());
		DefaultEnchantments.addDefaultEnchantment(new KnockUp());
		DefaultEnchantments.addDefaultEnchantment(new Life());
		DefaultEnchantments.addDefaultEnchantment(new Warp());
		DefaultEnchantments.addDefaultEnchantment(new ExpShare());
		DefaultEnchantments.addDefaultEnchantment(new Sniper());
		DefaultEnchantments.addDefaultEnchantment(new Telepathy());
		DefaultEnchantments.addDefaultEnchantment(new Smeltery());
		DefaultEnchantments.addDefaultEnchantment(new Sacrifice());
		DefaultEnchantments.addDefaultEnchantment(new Angler());
		DefaultEnchantments.addDefaultEnchantment(new Fried());
		DefaultEnchantments.addDefaultEnchantment(new FrequentFlyer());
		DefaultEnchantments.addDefaultEnchantment(new Tank());
		DefaultEnchantments.addDefaultEnchantment(new Brine());
		
		if(Version.VERSION_NUMBER > 3) {
			DefaultEnchantments.addDefaultEnchantment(new MagmaWalker());
		}
	}
}
