package com.natamus.fixedanvilrepaircost;

import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.check.ShouldLoadCheck;
import com.natamus.collective.fabric.callbacks.CollectiveAnvilEvents;
import com.natamus.fixedanvilrepaircost.events.RepairEvent;
import com.natamus.fixedanvilrepaircost.util.Reference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		if (!ShouldLoadCheck.shouldLoad(Reference.MOD_ID)) {
			return;
		}

		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		CollectiveAnvilEvents.ANVIL_CHANGE.register((AnvilMenu anvilmenu, ItemStack left, ItemStack right, ItemStack output, String itemName, int baseCost, Player player) -> {
			return RepairEvent.onRepairEvent(anvilmenu, left, right, output, itemName, baseCost, player);
		});
	}

	private static void setGlobalConstants() {

	}
}
