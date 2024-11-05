import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class GridMovementOnBeat(val player: PlayerEntity, val world: World, val range: Double) {

    private var lastTickNum = -1
    private var moved = false

    fun onTick(tickNum: Int) {
        if ((tickNum == 20 || tickNum == 10) && !moved) {
            moved = true // Set moved flag to true to prevent re-processing within this beat

            // Get all entities around the player within a bounding box
            val nearbyEntities = getNearbyEntities(player, range)

            // For each entity, apply grid movement based on its facing direction
            for (entity in nearbyEntities) {
                if (entity.velocity.lengthSquared() > 0) {
                    applyGridMovement(entity)
                    println(entity.velocity)
                }
            }
        } else if (tickNum != lastTickNum) {
            // Reset moved flag and set velocity to zero when it's not the beat
            moved = false
            setEntitiesVelocityToZero()
        }
        lastTickNum = tickNum // Update last tick number
    }

    // Helper function to get nearby living entities within a given range
    private fun getNearbyEntities(player: PlayerEntity, range: Double): List<LivingEntity> {
        val playerPos = player.blockPos

        // Define a bounding box around the player's position
        val boundingBox = Box(
            playerPos.x - range, playerPos.y - range, playerPos.z - range,
            playerPos.x + range, playerPos.y + range, playerPos.z + range
        )

        // Return a list of living entities within the bounding box (excluding the player)
        return world.getEntitiesByClass(
            LivingEntity::class.java, boundingBox
        ) { it != player }
    }

    // Apply the grid movement for a specific entity based on its facing direction
    private fun applyGridMovement(entity: LivingEntity) {
        val entityFacing = entity.horizontalFacing.unitVector

        val targetBlockPos = entity.blockPos.add(entityFacing.x.toInt(), 0, entityFacing.z.toInt())
        val blockAboveTargetPos = targetBlockPos.up()

        // Check if the target block and the block above are air
        val targetBlockState = world.getBlockState(targetBlockPos)
        val blockAboveTargetState = world.getBlockState(blockAboveTargetPos)

        // Move entity if both the target block and block above are clear
        if (targetBlockState.isAir && blockAboveTargetState.isAir) {
            entity.teleport(
                targetBlockPos.x + 0.5,
                targetBlockPos.y.toDouble(),
                targetBlockPos.z + 0.5,
                false
            )
        }
    }

    // Set the velocity of all nearby entities to zero
    private fun setEntitiesVelocityToZero() {
        val nearbyEntities = getNearbyEntities(player, range)
        for (entity in nearbyEntities) {
            entity.velocity = Vec3d(entity.velocity.x / 10.0, entity.velocity.y, entity.velocity.z / 10.0)
        }
    }
}
