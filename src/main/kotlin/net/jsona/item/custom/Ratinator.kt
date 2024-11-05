package net.jsona.item.custom

import net.jsona.entity.ModEntities
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World
import kotlin.random.Random


class Ratinator(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!world.isClient) {
            for (i in 1..5) {
                val ratEntity = ModEntities.RAT.create(world)
                ratEntity?.setPos(user.pos.x + randomFloat(2.0), user.pos.y, user.pos.z + randomFloat(2.0))
                ratEntity?.setOwner(user)
                ratEntity?.timeLeft = 200 + (Random.nextDouble() * 100).toInt()
                world.spawnEntity(ratEntity)
            }
        }
        return TypedActionResult.success(user.getStackInHand(hand))
    }

    override fun postHit(stack: ItemStack, target: LivingEntity, attacker: LivingEntity): Boolean {
        val world = attacker.world
        if (!world.isClient && attacker is PlayerEntity) {
            if (Random.nextDouble() < 0.33333333){
                val ratEntity = ModEntities.RAT.create(world)
                ratEntity?.setPos(target.pos.x, target.pos.y, target.pos.z)
                ratEntity?.setOwner(attacker)
                world.spawnEntity(ratEntity)
            }
        }
        return super.postHit(stack, target, attacker)
    }

    fun randomFloat(range: Double):Double{
        return Random.nextFloat() * (range - -range) + -range
    }
}