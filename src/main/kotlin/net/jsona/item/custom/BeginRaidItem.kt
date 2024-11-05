package net.jsona.item.custom

import net.jsona.entity.ModEntities
import net.jsona.entity.custom.BrewermobEntity
import net.jsona.item.ModItems
import net.jsona.testmod.Testmod
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.RaycastContext
import net.minecraft.world.World
import kotlin.random.Random


class BeginRaidItem(settings: Settings?) : Item(settings) {
    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        val player = user
        val itemStack = user?.getStackInHand(hand)
        if (player != null && world != null) {
            val playerPos = player.pos
            val zombiesToSpawn = 10 * Testmod.waveNumber

            for (i in 0 until zombiesToSpawn) {
                val spawnPos = getRandomPos(playerPos, 70.0, 90.0)

                val zombie = EntityType.ZOMBIE.create(world)
                if (zombie != null) {
                    zombie.refreshPositionAndAngles(spawnPos.x.toDouble(), spawnPos.y.toDouble(), spawnPos.z.toDouble(), 0.0f, 0.0f)
                    zombie.target = player
                    world.spawnEntity(zombie)
                    zombie.target = player
                }
            }
            Testmod.waveNumber += 1
            Testmod.waveOngoing = true
            return TypedActionResult.success(itemStack)
        }
        return TypedActionResult.pass(itemStack)
    }

    private fun getRandomPos(center: Vec3d, minRadius: Double, maxRadius: Double): BlockPos {
        val distance = Random.nextDouble(minRadius, maxRadius)

        // Random angle in radians
        val angle = Random.nextDouble(0.0, 2 * Math.PI)

        // Calculate random x and z positions using polar coordinates
        val xOffset = distance * Math.cos(angle)
        val zOffset = distance * Math.sin(angle)

        // Return new position
        return BlockPos((center.x + xOffset).toInt(), (center.y).toInt(), (center.z + zOffset).toInt())
    }
}