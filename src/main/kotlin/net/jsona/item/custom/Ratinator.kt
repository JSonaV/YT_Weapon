package net.jsona.item.custom

import net.jsona.entity.ModEntities
import net.jsona.entity.custom.BrewermobEntity
import net.jsona.item.ModItems
import net.jsona.testmod.Testmod
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.MobEntity
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


class Ratinator(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (!world.isClient) {
            val ratEntity = ModEntities.RAT.create(world)
            ratEntity?.setPos(user.pos.x, user.pos.y, user.pos.z)
            ratEntity?.setOwner(user)
            world.spawnEntity(ratEntity)
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
}