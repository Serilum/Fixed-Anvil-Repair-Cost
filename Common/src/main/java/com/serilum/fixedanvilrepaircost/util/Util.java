package com.serilum.fixedanvilrepaircost.util;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;

public class Util {
	public static int getCombinedRepairDamage(ItemStack leftstack, ItemStack rightstack) {
		int maxdamage = leftstack.getMaxDamage();
		int leftdurability = maxdamage - leftstack.getDamageValue();
		int rightdurability = maxdamage - rightstack.getDamageValue();

		int newdamage = maxdamage - (leftdurability + rightdurability + maxdamage * 12 / 100);
		if (newdamage < 0) {
			newdamage = 0;
		}

		return newdamage;
	}

	public static int repairPerVanillaMaterial(ItemStack itemstack, int availablematerials) {
		int materialsused = 0;
		int repairamount = getVanillaRepairAmountPerMaterial(itemstack);

		while (repairamount > 0 && materialsused < availablematerials) {
			itemstack.setDamageValue(itemstack.getDamageValue() - repairamount);
			materialsused++;
			repairamount = getVanillaRepairAmountPerMaterial(itemstack);
		}

		return materialsused;
	}

	public static int getVanillaRepairAmountPerMaterial(ItemStack itemstack) {
		return Math.min(itemstack.getDamageValue(), itemstack.getMaxDamage() / 4);
	}

	public static int getPriorWorkCost(ItemStack leftstack, ItemStack rightstack) {
		return leftstack.getOrDefault(DataComponents.REPAIR_COST, 0) + rightstack.getOrDefault(DataComponents.REPAIR_COST, 0);
	}
}
