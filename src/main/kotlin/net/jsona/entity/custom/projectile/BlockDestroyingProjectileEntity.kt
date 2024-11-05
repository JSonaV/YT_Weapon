import net.minecraft.client.world.ClientWorld
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.data.DataTracker
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class BlockDestroyingProjectileEntity(
    entityType: EntityType<out BlockDestroyingProjectileEntity>,
    world: World
) : Entity(entityType, world) {

    var direction: Vec3d = Vec3d.ZERO
    private var lifetime: Int = 60 // Lifetime in ticks (e.g., 60 ticks for 3 seconds at 20 ticks per second)
    var facin: Direction = Direction.NORTH
    val speed = 0.5

    override fun initDataTracker(builder: DataTracker.Builder?) {
        //none
    }


    override fun tick() {
        super.tick()

        // Move the projectile in the assigned direction
        val startPos = pos
        val endPos = startPos.add(direction.multiply(0.5))

        // Create a list to store blocks that will be broken
        val blocksToBreak = mutableListOf<BlockPos>()


        // Define the area of effect dimensions
        val width = 40
        val height = 20

        // Use steps to control how finely you check the area
        val steps = 0
        var bPos = BlockPos(0, 0, 0)
            // Iterate over each step along the path
            for (step in 0..steps) {

                // Check a w x h area around the current position
                for (dx in -width / 2..width / 2) {
                    for (dz in -1..height) {
                        // Calculate the block position for the current offset
                        if (world is ServerWorld) {
                            if (facin == Direction.SOUTH || facin == Direction.NORTH) { // Facing South n North
                                bPos = BlockPos(blockPos.x + dx, blockPos.y + dz, blockPos.z)
                            } else { // Facing East n West
                                bPos = BlockPos(blockPos.x, blockPos.y + dz, blockPos.z + dx)
                            }

                                (world as? ServerWorld)?.spawnParticles(
                                    net.minecraft.particle.ParticleTypes.ELECTRIC_SPARK,
                                    bPos.x.toDouble(), bPos.y.toDouble(), bPos.z.toDouble(),
                                    1, 0.0, 0.0, 0.0, 0.0
                                )

                        }

                        val blockState = world.getBlockState(bPos)


                        if (!blockState.isAir) {
                            blocksToBreak.add(bPos)
                        }

                    }
                }


            // Break the blocks that were hit
            for (block in blocksToBreak) {
                world.breakBlock(block, false)
                for (i in 0..0) {
                    if (random.nextFloat() < 0.5f) continue // 50% chance of spawning smoke
                    (world as? ServerWorld)?.spawnParticles(
                        net.minecraft.particle.ParticleTypes.CAMPFIRE_SIGNAL_SMOKE,
                        block.x.toDouble(), block.y.toDouble(), block.z.toDouble(),
                        1, (-0.5 + random.nextFloat()) * 1.5, (-0.5 + random.nextFloat()) * 1.5, (-0.5 + random.nextFloat()) * 1.5, (-0.5 + random.nextFloat())
                    )
                }
            }


            // Update position and decrement lifetime
            setPosition(endPos)
            lifetime--
            if (lifetime <= 0) {
                discard()
            }
        }

    }

    override fun readCustomDataFromNbt(nbt: NbtCompound?) {
        //none
    }

    override fun writeCustomDataToNbt(nbt: NbtCompound?) {
        //none
    }



}
