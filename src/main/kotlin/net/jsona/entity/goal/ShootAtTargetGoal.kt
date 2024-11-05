package net.jsona.entity.goal


import net.jsona.entity.custom.DirtmobEntity
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.Goal
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.projectile.ArrowEntity
import net.minecraft.util.math.Vec3d

class ShootAtTargetGoal(
    private val mob: DirtmobEntity,
    private val shootInterval: Int = 20, // Time between shots in ticks
    private val shootRange: Double = 10.0 // Radius to check for targets
) : Goal() {
    private var tickCount = 0

    override fun canStart(): Boolean {
        return mob.world.getEntitiesByClass(HostileEntity::class.java, mob.boundingBox.expand(shootRange), null)
            .isNotEmpty()
    }

    override fun tick() {
        // Find the closest hostile entity
        val targets = mob.world.getEntitiesByClass(HostileEntity::class.java, mob.boundingBox.expand(shootRange), null)
        if (targets.isNotEmpty()) {
            val target = targets[0] // Select the first target (you may want to improve this)

            // Face the target
            mob.lookAtEntity(target, 10.0f, 10.0f)

            // Shoot the arrow if enough time has passed
            if (tickCount >= shootInterval) {
                shootArrow(target)
                tickCount = 0 // Reset the tick count
            } else {
                tickCount++
            }
        }
    }

    private fun shootArrow(target: HostileEntity) {
        // Create and shoot the arrow
        val arrow = ArrowEntity(EntityType.ARROW, mob.world) // Create an arrow entity
        arrow.setPosition(mob.x, mob.y + mob.height / 2.0, mob.z)
        val direction = Vec3d(target.x - mob.x, target.y - mob.y, target.z - mob.z).normalize()
        arrow.setVelocity(direction.x, direction.y, direction.z, 1.6f, 0f) // Set velocity and direction
        mob.world.spawnEntity(arrow) // Spawn the arrow in the world
    }
}
