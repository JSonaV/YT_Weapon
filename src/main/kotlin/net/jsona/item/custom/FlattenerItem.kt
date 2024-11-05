package net.jsona.item.custom

import net.jsona.entity.ModEntities
import net.jsona.entity.ModEntities.BLOCK_DESTROYING_PROJECTILE_ENTITY
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
import net.minecraft.particle.ParticleTypes
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
import java.lang.Math.*
import kotlin.random.Random


class FlattenerItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack>? {
        val itemStack = user.getStackInHand(hand)
        if (!world.isClient) {
            val projectile = BLOCK_DESTROYING_PROJECTILE_ENTITY.create(world) ?: return TypedActionResult.pass(itemStack)
            projectile.setPosition(user.pos.x, user.pos.y + 1, user.pos.z)
            projectile.direction = getDirectionVector(user.yaw, user.pitch)
            projectile.facin = user.facing
            world.spawnEntity(projectile)
        }
        // Apply knockback to the player
        val knockbackStrength = 0.8f 
        val knockbackDirection = getDirectionVector(user.yaw, user.pitch)
        user.addVelocity(-knockbackDirection.x * knockbackStrength, 0.7, -knockbackDirection.z * knockbackStrength)

        world.playSound(user, user.blockPos, SoundEvents.ITEM_MACE_SMASH_GROUND, SoundCategory.PLAYERS, 2.0f, 0.6f)






        return TypedActionResult.success(itemStack)

    }

    fun getDirectionVector(yaw: Float, pitch: Float): Vec3d {
        // Convert angles to radians for trigonometric calculations
        val yawRad = toRadians(yaw.toDouble())
        val pitchRad = toRadians(pitch.toDouble())

        // Calculate the direction vector components
        val x = -cos(pitchRad) * sin(yawRad)
        val z = cos(pitchRad) * cos(yawRad)



        // Return the snapped direction vector with y as 0
        return Vec3d(x, 0.0, z)
    }
}