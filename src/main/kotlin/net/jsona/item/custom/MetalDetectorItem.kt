package net.jsona.item.custom

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class MetalDetectorItem(settings: Settings) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val yawDeg = user.yaw
        val yawRad = Math.toRadians(yawDeg.toDouble())
        user.setVelocity(-Math.sin(yawRad)/2, 0.5, Math.cos(yawRad)/2)


        return TypedActionResult.success(user.getStackInHand(hand))
    }
}
