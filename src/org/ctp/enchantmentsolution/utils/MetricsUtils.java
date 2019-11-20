package org.ctp.enchantmentsolution.utils;

import java.util.concurrent.Callable;

import org.ctp.enchantmentsolution.EnchantmentSolution;
import org.ctp.enchantmentsolution.utils.compatibility.Metrics;

public class MetricsUtils {

	public static void init() {
		Metrics metrics = new Metrics(EnchantmentSolution.getPlugin());
		
		metrics.addCustomChart(new Metrics.SingleLineChart("level_fifty", new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            if(ConfigUtils.isLevel50()) {
	            	return 1;
	            }
	            return 0;
	        }
	    }));
		
		metrics.addCustomChart(new Metrics.SingleLineChart("level_thirty", new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            if(!ConfigUtils.isLevel50()) {
	            	return 1;
	            }
	            return 0;
	        }
	    }));
		
		metrics.addCustomChart(new Metrics.SingleLineChart("advanced_file", new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            if(ConfigUtils.useAdvancedFile()) {
	            	return 1;
	            }
	            return 0;
	        }
	    }));
		
		metrics.addCustomChart(new Metrics.SingleLineChart("basic_file", new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            if(!ConfigUtils.useAdvancedFile()) {
	            	return 1;
	            }
	            return 0;
	        }
	    }));
		
		metrics.addCustomChart(new Metrics.SingleLineChart("enhanced_gui", new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            if(ConfigUtils.useESGUI()) {
	            	return 1;
	            }
	            return 0;
	        }
	    }));
		
		metrics.addCustomChart(new Metrics.SingleLineChart("vanilla_gui", new Callable<Integer>() {
	        @Override
	        public Integer call() throws Exception {
	            if(!ConfigUtils.useESGUI()) {
	            	return 1;
	            }
	            return 0;
	        }
	    }));
	}
}
