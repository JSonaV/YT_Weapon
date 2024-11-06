package net.jsona.item.custom

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents
import net.minecraft.block.Blocks
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*


class TheAnvilItem(settings: Settings?) : Item(settings) {

    private val anvilPositions = mutableMapOf<ServerWorld, MutableMap<BlockPos, List<BlockPos>>>()
    var anvilTimer = 0

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {


        if (world is ServerWorld) {
            val radius = 5.0
            val entities = world.getOtherEntities(user, user.boundingBox.expand(radius))
            for (entity in entities) {
                if (entity is LivingEntity) {
                    val positions = listOf(
                        entity.blockPos.add(1, 0, 0),
                        entity.blockPos.add(-1, 0, 0),
                        entity.blockPos.add(0, 0, 1),
                        entity.blockPos.add(0, 0, -1),
                        entity.blockPos.add(1, 0, 1),
                        entity.blockPos.add(-1, 0, -1),
                        entity.blockPos.add(1, 0, -1),
                        entity.blockPos.add(-1, 0, 1)
                    )
                    for (pos in positions) {
                        if (world.isAir(pos)) {
                            world.setBlockState(pos, Blocks.OAK_FENCE.defaultState)
                        } else {
                            world.breakBlock(pos, false)
                            world.setBlockState(pos, Blocks.OAK_FENCE.defaultState)
                        }

                    }


                    val anvilSpawnPos = BlockPos(entity.blockPos.x, entity.blockPos.y + 20, entity.blockPos.z)
                    if (world.isAir(anvilSpawnPos)) {
                        world.setBlockState(anvilSpawnPos, Blocks.DAMAGED_ANVIL.defaultState)
                        anvilTimer = 100
                    }

                    anvilPositions.computeIfAbsent(world) { mutableMapOf() }[anvilSpawnPos] = positions


                    entity.teleport(
                        world,
                        entity.blockPos.x + 0.5,
                        entity.blockPos.y.toDouble(),
                        entity.blockPos.z + 0.5,
                        setOf(),
                        entity.yaw,
                        entity.pitch
                    )
                    (world as? ServerWorld)?.spawnParticles(
                        net.minecraft.particle.ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                        entity.x,
                        entity.y,
                        entity.z,
                        10,
                        (-0.5 + Random().nextFloat()) * 1.5,
                        (-0.5 + Random().nextFloat()) * 1.5,
                        (-0.5 + Random().nextFloat()) * 1.5,
                        (-0.5 + Random().nextFloat())
                    )
                    (world as? ServerWorld)?.spawnParticles(
                        net.minecraft.particle.ParticleTypes.ELECTRIC_SPARK,
                        entity.x,
                        entity.y + 1,
                        entity.z,
                        20,
                        (-0.5 + Random().nextFloat()) * 1.5,
                        (-0.5 + Random().nextFloat()) * 1.5,
                        (-0.5 + Random().nextFloat()) * 1.5,
                        (-0.5 + Random().nextFloat())
                    )
                }


            }
        }
        return TypedActionResult.success(user.getStackInHand(hand))
    }
    init {
        ServerTickEvents.END_WORLD_TICK.register { world ->
            if (world is ServerWorld) {
                if (anvilTimer > 0) {
                    anvilTimer--
                } else {
                    // I asked ChatGPT for this part and i have no idea what it does so i'm keeping it in
                    val iterator = anvilPositions[world]?.iterator()
                    iterator?.forEach { (anvilPos, fencePositions) ->
                        if (!world.getBlockState(anvilPos).isOf(Blocks.DAMAGED_ANVIL)) {
                            // Anvil is broken, remove fences
                            for (pos in fencePositions) {
                                if (world.getBlockState(pos).block == Blocks.OAK_FENCE) {
                                    world.setBlockState(pos, Blocks.AIR.defaultState)
                                }
                            }
                            // Remove this entry from the map
                            iterator.remove()
                        }
                    }
                }
            }
        }
    }
}