package net.jsona.item.custom
import net.minecraft.block.Blocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.hit.HitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.RaycastContext
import net.minecraft.world.World

class DoubleBucketWaterItem(settings: Item.Settings) : Item(settings) {

    override fun use(world: World, player: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = player.getStackInHand(hand)
        // Raycast to determine where the player is looking (like a normal bucket)
        val blockHitResult: BlockHitResult = raycast(
            world, player, RaycastContext.FluidHandling.NONE
        )

        // If the player misses a block, pass
        if (blockHitResult.type == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack)
        }

        val blockPos = blockHitResult.blockPos
        val direction = blockHitResult.side
        val placePos = blockPos.offset(direction)

        if (!world.isClient) {
            // Ensure player can modify the target block
            if (world.canPlayerModifyAt(player, placePos)) {
                // Get two diagonal positions relative to the clicked block
                val diagonalPos1 = placePos.add(0, 0, 0) // Positive diagonal
                val diagonalPos2 = placePos.add(-1, 0, -1) // Negative diagonal

                // Check if both positions are replaceable (e.g., air or liquid)
                if (world.getBlockState(diagonalPos1).isAir && world.getBlockState(diagonalPos2).isAir) {
                    // Place water blocks diagonally
                    world.setBlockState(diagonalPos1, Blocks.WATER.defaultState)
                    world.setBlockState(diagonalPos2, Blocks.WATER.defaultState)

                    // Play water placing sound
                    world.playSound(null, player.x, player.y, player.z,
                        SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F
                    )

                    // Update stats and decrement bucket if not in creative
                    player.incrementStat(Stats.USED.getOrCreateStat(this))
                    if (!player.isCreative) {
                        itemStack.decrement(1)
                        player.giveItemStack(ItemStack(Items.BUCKET)) // Give back an empty bucket
                    }

                    // Mark the action as successful
                    return TypedActionResult.success(itemStack, world.isClient)
                }
            }
        }

        return TypedActionResult.fail(itemStack)
    }
}