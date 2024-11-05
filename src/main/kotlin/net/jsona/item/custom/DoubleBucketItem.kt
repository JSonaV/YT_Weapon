package net.jsona.item.custom

import net.jsona.item.ModItems
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.RaycastContext
import net.minecraft.world.World


class DoubleBucketItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)

        // Raycast to check if the player is looking at a water source block
        val blockHitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY)

        // If the player missed or didn't hit a block, return pass
        if (blockHitResult.type == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack)
        }

        val blockPos = blockHitResult.blockPos
        val blockState = world.getBlockState(blockPos)

        // Check if the block is water
        if (blockState.fluidState.fluid === Fluids.WATER) {
            if (!world.isClient) {
                // Replace the empty bucket with a water bucket
                val waterBucket = ItemStack(ModItems.DOUBLE_BUCKET_WATER)
                user.setStackInHand(hand, waterBucket)

                // Play sound for filling the bucket
                world.playSound(
                    null, user.x, user.y, user.z,
                    SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0f, 1.0f
                )

                // Update stats for using the bucket
                user.incrementStat(Stats.USED.getOrCreateStat(this))

                // Trigger server-side bucket filled criteria
                if (user is ServerPlayerEntity) {
                    Criteria.FILLED_BUCKET.trigger(user, waterBucket)
                }
            }
            return TypedActionResult.success(itemStack, world.isClient())
        }

        return TypedActionResult.pass(itemStack)
    }
}