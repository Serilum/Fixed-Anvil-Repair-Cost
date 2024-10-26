package com.natamus.fixedanvilrepaircost.neoforge.events;

import com.natamus.fixedanvilrepaircost.events.RepairEvent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import oshi.util.tuples.Triplet;

@EventBusSubscriber
public class NeoForgeRepairEvent {
	@SubscribeEvent
	public static void onRepairEvent(AnvilUpdateEvent e) {
		Triplet<Integer, Integer, ItemStack> triple = RepairEvent.onRepairEvent(null, e.getLeft(), e.getRight(), e.getOutput(), e.getName(), e.getCost(), e.getPlayer());
		if (triple == null) {
			return;
		}

		if (triple.getA() >= 0) {
			e.setCost(triple.getA());
		}

		if (triple.getB() >= 0) {
			e.setMaterialCost(triple.getB());
		}

		if (triple.getC() != null) {
			e.setOutput(triple.getC());
		}
	}
}
