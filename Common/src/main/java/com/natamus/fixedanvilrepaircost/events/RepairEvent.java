package com.natamus.fixedanvilrepaircost.events;

import com.natamus.fixedanvilrepaircost.config.ConfigHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import oshi.util.tuples.Triplet;

public class RepairEvent {
	public static Triplet<Integer, Integer, ItemStack> onRepairEvent(AnvilMenu anvilmenu, ItemStack leftstack, ItemStack rightstack, ItemStack outputstack, String itemName, long baseCost, Player player) {
		int newlevelcost = -1;
		int newmaterialcost = -1;
		ItemStack newoutput = null;
		
		if (!rightstack.getItem().equals(Items.ENCHANTED_BOOK) && !leftstack.getItem().equals(rightstack.getItem())) {
			Item leftitem = leftstack.getItem();
			if (!leftstack.isValidRepairItem(rightstack)) {
				return null;
			}
			
			int levelcost = ConfigHandler.repairCostLevelAmount;
			int materialcost = ConfigHandler.repairCostMaterialAmount;
			
			if (levelcost >= 1 || materialcost >= 1) {
				if (levelcost >= 1) {
					newlevelcost = levelcost;
				}
				if (materialcost >= 1) {
					newmaterialcost = materialcost;
					
					if (materialcost > rightstack.getCount()) {
						newoutput = ItemStack.EMPTY;
						return new Triplet<Integer, Integer, ItemStack>(newlevelcost, newmaterialcost, newoutput);
					}
				}
				
				int currentdamage = leftstack.getDamageValue();
				int maxdamage = leftstack.getMaxDamage();
				int repairamount = (int)(maxdamage * ConfigHandler.percentRepairedPerAction);
				
				currentdamage -= repairamount;
				if (currentdamage < 0) {
					currentdamage = 0;
				}
				
				ItemStack newoutputstack = leftstack.copy();
				newoutputstack.setDamageValue(currentdamage);
				
				newoutput = newoutputstack;
			}
			
			return new Triplet<Integer, Integer, ItemStack>(newlevelcost, newmaterialcost, newoutput);
		}
		
		return null;
	}
}
