package net.jsona.testmod

import BlockDestroyingProjectileEntity
import net.jsona.block.ModBlocks
import net.jsona.item.ModItems
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.jsona.entity.ModEntities
import net.jsona.entity.client.DirtmobRenderer
import net.jsona.entity.custom.BrewermobEntity
import net.jsona.entity.custom.DirtmobEntity
import net.jsona.entity.custom.MagmamobEntity
import net.jsona.entity.custom.RatEntity
import net.minecraft.block.Blocks
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import org.slf4j.LoggerFactory
import net.minecraft.client.MinecraftClient.*
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.entity.mob.ZombieEntity
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos

object Testmod : ModInitializer {
	var waveNumber = 1
	var waveOngoing = false

	val MOD_ID = "testmod"
	val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItems.registerModItems()
		ModBlocks.registerModBlocks()


		FabricDefaultAttributeRegistry.register(ModEntities.DIRTMOB, DirtmobEntity.createAttributes())
		FabricDefaultAttributeRegistry.register(ModEntities.MAGMAMOB, MagmamobEntity.createAttributes())
		FabricDefaultAttributeRegistry.register(ModEntities.BREWERMOB, BrewermobEntity.createAttributes())
		FabricDefaultAttributeRegistry.register(ModEntities.RAT, RatEntity.createAttributes())

		ServerTickEvents.START_WORLD_TICK.register { world ->
			if (world is ServerWorld) {
				var zombieExists = false
				for (player in world.players) {
					val radius = 100.0
					val box = Box(
						player.x - radius, player.y - radius, player.z - radius,
						player.x + radius, player.y + radius, player.z + radius
					)

					val zombies = world.getEntitiesByClass(ZombieEntity::class.java, box) { zombie ->
						zombie.isAlive
					}

					for (zombie in zombies) {
						zombie.target = player
						zombieExists = true
					}
					if(!zombieExists){
						waveOngoing = false
					}
					if(waveOngoing){
						world.timeOfDay = 18000
					}
					else{
						world.timeOfDay = 6000
					}





				}



			}
		}
	}
}


