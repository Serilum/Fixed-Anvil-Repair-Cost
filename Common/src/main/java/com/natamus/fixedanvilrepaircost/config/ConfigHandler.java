package com.natamus.fixedanvilrepaircost.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.fixedanvilrepaircost.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry(min = 0, max = 100) public static int repairCostLevelAmount = 3;
	@Entry(min = 0, max = 64) public static int repairCostMaterialAmount = 1;
	@Entry(min = 0, max = 1.0) public static double percentRepairedPerAction = 0.3333;

	public static void initConfig() {
		configMetaData.put("repairCostLevelAmount", Arrays.asList(
			"The amount of level it costs to repair an item on an anvil. A value of 0 keeps vanilla behaviour."
		));
		configMetaData.put("repairCostMaterialAmount", Arrays.asList(
			"The amount of material it costs to repair an item on an anvil. A value of 0 keeps vanilla behaviour."
		));
		configMetaData.put("percentRepairedPerAction", Arrays.asList(
			"How much the item is repaired per action. By default 33.33%, so 3 of 'repairCostMaterialAmount' fully repairs any item."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}