package net.jsona.item.custom

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.passive.FoxEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent


class SteacoItem(settings: Settings?) : Item(settings) {
    override fun finishUsing(stack: ItemStack?, world: World, user: LivingEntity): ItemStack {
        val itemStack = super.finishUsing(stack, world, user)
        if (!world.isClient) {
            if (user is PlayerEntity) {
                user.kill()
            }
        }

        return itemStack
    }
}