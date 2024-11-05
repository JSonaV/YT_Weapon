package net.jsona.item.custom

import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FishingBobberEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent


class MultiCastFishingRodItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)

        if (!world.isClient && world is ServerWorld) {
            val numBobbers = 3 // Number of bobbers to cast

            for (i in 0 until numBobbers) {
                // Create and initialize the fishing bobber entity
                val bobber = FishingBobberEntity(user, world, 0, 0)
                // Position each bobber slightly apart
                bobber.setPosition(user.x + (i - 1) * 1.0, user.y + 1.0, user.z)

                // Set a small initial velocity to keep the bobber from falling too quickly
                val velocity = Vec3d(
                    (user.random.nextDouble() - 0.5) * 0.2,
                    0.2,
                    (user.random.nextDouble() - 0.5) * 0.2
                )
                bobber.setVelocity(velocity)

                // Log information about the bobber
                println("Spawning FishingBobberEntity at ${bobber.pos} with velocity ${bobber.velocity}")

                // Spawn the bobber entity in the world
                world.spawnEntity(bobber)
            }

            world.playSound(
                null,
                user.x,
                user.y,
                user.z,
                SoundEvents.ENTITY_FISHING_BOBBER_THROW,
                SoundCategory.NEUTRAL,
                0.5f,
                0.4f / (world.random.nextFloat() * 0.4f + 0.8f)
            )

            user.emitGameEvent(GameEvent.ITEM_INTERACT_START)
        }

        return TypedActionResult.success(itemStack, world.isClient)
    }
}