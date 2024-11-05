package net.jsona.entity.goal

import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.TargetPredicate
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.projectile.ArrowEntity
import net.minecraft.util.math.Box
import net.minecraft.util.math.MathHelper
import java.util.*

class ShootHostileMobsGoal(private val mob: MobEntity, private val range: Double) : Goal() {

    init {
        // Define this goal as one that requires continuous execution
        controls = EnumSet.of(Control.MOVE, Control.LOOK)
    }

    override fun canStart(): Boolean {
        // Find the nearest hostile mob within range
        val target = mob.world.getClosestEntity(HostileEntity::class.java, TargetPredicate.DEFAULT, mob, mob.x, mob.y, mob.z, Box(mob.blockPos).expand(range))
        return target != null
    }

    override fun start() {
        // Set the found hostile entity as the target
        val target = mob.world.getClosestEntity(HostileEntity::class.java, TargetPredicate.DEFAULT, mob, mob.x, mob.y, mob.z, Box(mob.blockPos).expand(range))
        mob.target = target
    }

    override fun tick() {
        val target = mob.target
        if (target != null && mob.distanceTo(target) <= range) {
            // Make the mob shoot at the target
            mob.lookAtEntity(target, 10.0f, 10.0f)
            shootProjectileAt(target)
        }
    }

    private fun shootProjectileAt(target: LivingEntity) {
        val world = mob.world
        if (!world.isClient) {
            // Create a projectile, such as an arrow
            val arrow = ArrowEntity(EntityType.ARROW, world)

            // Calculate the direction from the mob to the target
            val dx = target.x - mob.x
            val dy = target.eyeY - arrow.y
            val dz = target.z - mob.z
            val distance = MathHelper.sqrt((dx * dx + dz * dz).toFloat())

            // Set the velocity of the arrow
            arrow.setVelocity(dx, dy + distance * 0.2, dz, 1.5f, 1.0f)

            // Set the owner of the projectile (the mob shooting the arrow)
            arrow.owner = mob

            // Spawn the arrow entity in the world
            world.spawnEntity(arrow)
        }
    }
}