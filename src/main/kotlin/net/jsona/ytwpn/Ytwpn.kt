package net.jsona.ytwpn

import net.jsona.block.ModBlocks
import net.jsona.item.ModItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.jsona.entity.ModEntities
import net.jsona.entity.custom.RatEntity
import net.minecraft.server.world.ServerWorld
import org.slf4j.LoggerFactory

object Ytwpn : ModInitializer {
	var waveNumber = 1
	var waveOngoing = false

	val MOD_ID = "ytwpn"
	val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItems.registerModItems()
		ModBlocks.registerModBlocks()



		FabricDefaultAttributeRegistry.register(ModEntities.RAT, RatEntity.createAttributes())

		ServerTickEvents.START_WORLD_TICK.register { world ->
			if (world is ServerWorld) {

			}
		}
	}
}


