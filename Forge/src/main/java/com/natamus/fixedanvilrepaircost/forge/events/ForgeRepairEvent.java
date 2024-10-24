package com.natamus.fixedanvilrepaircost.forge.events;

import com.natamus.fixedanvilrepaircost.events.RepairEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import oshi.util.tuples.Triplet;

@EventBusSubscriber
public class ForgeRepairEvent {
	@SubscribeEvent
	public void onRepairEvent(AnvilUpdateEvent e) {
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
