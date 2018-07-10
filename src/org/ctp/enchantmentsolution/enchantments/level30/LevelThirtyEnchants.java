package org.ctp.enchantmentsolution.enchantments.level30;

import org.ctp.enchantmentsolution.enchantments.DefaultEnchantments;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Angler;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Beheading;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Brine;
import org.ctp.enchantmentsolution.enchantments.level30.custom.ExpShare;
import org.ctp.enchantmentsolution.enchantments.level30.custom.FrequentFlyer;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Fried;
import org.ctp.enchantmentsolution.enchantments.level30.custom.KnockUp;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Life;
import org.ctp.enchantmentsolution.enchantments.level30.custom.MagmaWalker;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Sacrifice;
import org.ctp.enchantmentsolution.enchantments.level30.custom.ShockAspect;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Smeltery;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Sniper;
import org.ctp.enchantmentsolution.enchantments.level30.custom.SoulReaper;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Soulbound;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Tank;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Telepathy;
import org.ctp.enchantmentsolution.enchantments.level30.custom.Warp;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.AquaAffinity;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.BaneOfArthropods;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.BlastProtection;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.CurseOfBinding;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.CurseOfVanishing;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.DepthStrider;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Efficiency;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.FeatherFalling;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.FireAspect;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.FireProtection;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Flame;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Fortune;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.FrostWalker;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Infinity;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Knockback;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Looting;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.LuckOfTheSea;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Lure;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Mending;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Power;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.ProjectileProtection;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Protection;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Punch;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Respiration;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Sharpness;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.SilkTouch;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Smite;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.SweepingEdge;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Thorns;
import org.ctp.enchantmentsolution.enchantments.level30.vanilla.Unbreaking;
import org.ctp.enchantmentsolution.nms.Version;

public class LevelThirtyEnchants{
	
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
