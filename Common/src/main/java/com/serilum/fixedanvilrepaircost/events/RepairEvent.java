package com.serilum.fixedanvilrepaircost.events;

import com.serilum.fixedanvilrepaircost.config.ConfigHandler;
import com.serilum.fixedanvilrepaircost.util.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import oshi.util.tuples.Triplet;

public class RepairEvent {
	public static Triplet<Integer, Integer, ItemStack> onRepairEvent(AnvilMenu anvilmenu, ItemStack leftstack, ItemStack rightstack, ItemStack outputstack, String itemName, int baseCost, Player player) {
		if (rightstack.isEmpty() || rightstack.getItem().equals(Items.ENCHANTED_BOOK)) {
			return null;
		}

		if (!leftstack.isDamaged()) {
			return null;
		}

		int levelcost = ConfigHandler.repairCostLevelAmount;
		if (leftstack.getItem().equals(rightstack.getItem())) {
			if (levelcost < 1 || rightstack.isEnchanted()) {
				return null;
			}

			int combineddamage = Util.getCombinedRepairDamage(leftstack, rightstack);
			if (combineddamage >= leftstack.getDamageValue()) {
				return null;
			}

			ItemStack combinedoutput = leftstack.copy();
			combinedoutput.setDamageValue(combineddamage);
			return new Triplet<>(levelcost, 0, combinedoutput);
		}

		Item leftitem = leftstack.getItem();
		if (!leftitem.isValidRepairItem(leftstack, rightstack)) {
			return null;
		}

		int materialcost = ConfigHandler.repairCostMaterialAmount;
		if (levelcost < 1 && materialcost < 1) {
			return null;
		}

		if (materialcost > rightstack.getCount()) {
			return new Triplet<>(levelcost, materialcost, ItemStack.EMPTY);
		}

		ItemStack newoutput = leftstack.copy();
		int materialsused = materialcost;
		if (materialcost >= 1) {
			int currentdamage = leftstack.getDamageValue();
			int maxdamage = leftstack.getMaxDamage();
			int repairamount = (int)(maxdamage * ConfigHandler.percentRepairedPerAction);

			currentdamage -= repairamount;
			if (currentdamage < 0) {
				currentdamage = 0;
			}

			newoutput.setDamageValue(currentdamage);
		}
		else {
			materialsused = Util.repairPerVanillaMaterial(newoutput, rightstack.getCount());
		}

		int newlevelcost = levelcost;
		if (levelcost < 1) {
			newlevelcost = Util.getPriorWorkCost(leftstack, rightstack) + materialsused;
		}

		return new Triplet<>(newlevelcost, materialsused, newoutput);
	}
}
