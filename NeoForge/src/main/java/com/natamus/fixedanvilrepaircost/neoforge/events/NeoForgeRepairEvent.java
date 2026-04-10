package com.natamus.fixedanvilrepaircost.neoforge.events;

import com.natamus.fixedanvilrepaircost.events.RepairEvent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.bus.api.SubscribeEvent;
import oshi.util.tuples.Triplet;

public class NeoForgeRepairEvent {
	@SubscribeEvent
	public static void onRepairEvent(AnvilUpdateEvent e) {
		Triplet<Integer, Integer, ItemStack> triple = RepairEvent.onRepairEvent(null, e.getLeft(), e.getRight(), e.getOutput(), e.getName(), e.getXpCost(), e.getPlayer());
		if (triple == null) {
			return;
		}

		if (triple.getA() >= 0) {
			e.setXpCost(triple.getA());
		}

		if (triple.getB() >= 0) {
			e.setMaterialCost(triple.getB());
		}

		if (triple.getC() != null) {
			e.setOutput(triple.getC());
		}
	}
}
