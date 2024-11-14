package net.jsona.ytwpn

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.jsona.block.ModBlocks
import net.jsona.entity.ModEntities
import net.jsona.entity.custom.RatEntity
import net.jsona.item.ModItems
import net.minecraft.client.render.RenderLayer
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.property.IntProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.slf4j.LoggerFactory

object Ytwpn : ModInitializer {


	val MOD_ID = "ytwpn"
	val logger = LoggerFactory.getLogger(MOD_ID)

	override fun onInitialize() {
		ModItems.registerModItems()
		ModBlocks.registerModBlocks()
		FabricDefaultAttributeRegistry.register(ModEntities.RAT, RatEntity.createAttributes())
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOVER_BED, RenderLayer.getCutout());

		ServerTickEvents.START_WORLD_TICK.register { world ->
			if (world is ServerWorld) {
				for (player in world.players) {
					if (player.getEquippedStack(EquipmentSlot.HEAD).item == ModItems.CLOVER) {
						val luckEffect = StatusEffectInstance(StatusEffects.LUCK, 40, 3)
						player.addStatusEffect(luckEffect, null)
					}
					checkForClover(world, player)
				}
			}
		}
	}

	fun checkForClover(world: World, player: PlayerEntity) {
		val radius = 2
		val playerPos = BlockPos(player.blockPos.x, player.blockPos.y, player.blockPos.z)
		BlockPos.iterate(
			playerPos.add(-radius, -radius, -radius),
			playerPos.add(radius, radius, radius)
		).forEach { pos ->
			val block = world.getBlockState(pos).block
			val blockstate = world.getBlockState(pos)
			if (block == ModBlocks.CLOVER_BED) {
				val flowerAmount = world.getBlockState(pos).get(Properties.FLOWER_AMOUNT)
				// Apply Regeneration effect if the block is found
				player.addStatusEffect(StatusEffectInstance(StatusEffects.LUCK, 30*flowerAmount, flowerAmount-1), null)
				player.addStatusEffect(StatusEffectInstance(StatusEffects.SPEED, 30*flowerAmount, flowerAmount-1), null)
			}
		}

	}
}


