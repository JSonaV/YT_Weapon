package net.jsona.block.custom

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.World
import net.minecraft.world.BlockView
import kotlin.concurrent.thread
import kotlin.math.abs

class TeleportPipeBlock(settings: Settings) : Block(settings) {
    private val cooldownMap = mutableMapOf<PlayerEntity, Long>()
    private val cooldownTimeMillis = 1000L // 1 second cooldown

    companion object {
        val FACING: DirectionProperty = DirectionProperty.of("facing", Direction.values().toList())
    }

    init {
        // Default state facing north
        defaultState = stateManager.defaultState.with(FACING, Direction.NORTH)
    }

    // Add the facing property to the block's state manager
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    // Handle block placement and set its direction based on player look
    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return defaultState.with(FACING, ctx.playerLookDirection.opposite) // PlayerLookDirection for both horizontal and vertical
    }

    // Handle rotation of the block
    override fun rotate(state: BlockState, rotation: BlockRotation): BlockState {
        return state.with(FACING, rotation.rotate(state.get(FACING)))
    }

    // Handle mirroring of the block
    override fun mirror(state: BlockState, mirror: BlockMirror): BlockState {
        return state.rotate(mirror.getRotation(state.get(FACING)))
    }


    // Triggered when an entity (like a player) steps on the block
    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        if (world.isClient || entity !is PlayerEntity || !entity.isSneaking) return

        val currentTime = System.currentTimeMillis()
        val lastTeleportTime = cooldownMap[entity] ?: 0L

        if (currentTime - lastTeleportTime < cooldownTimeMillis) {
            // If within cooldown period, do nothing
            return
        }

        cooldownMap[entity] = currentTime // Update last teleport time

        val connectedBlocks = findConnectedBlocks(world, pos)
        if (connectedBlocks.isNotEmpty()) {
            val startPos = connectedBlocks.first()
            val endPos = connectedBlocks.last()

            val lastBlockState = world.getBlockState(endPos)
            val lastFacingDirection = lastBlockState.get(FACING)

            // Determine the teleport destination based on the current position
            val targetPos = if (pos == startPos) endPos else startPos

            // Teleport the player to the target position
            entity.teleport(targetPos.x + 0.5, targetPos.y.toDouble() + 1, targetPos.z + 0.5, true)

            // Calculate velocity based on the facing direction
            val velocityMultiplier = 1.0 // Adjust this value as needed
            val velocity = when (lastFacingDirection) {
                Direction.NORTH -> Vec3d(0.0, 0.0, -velocityMultiplier)
                Direction.SOUTH -> Vec3d(0.0, 0.0, velocityMultiplier)
                Direction.EAST -> Vec3d(velocityMultiplier, 0.0, 0.0)
                Direction.WEST -> Vec3d(-velocityMultiplier, 0.0, 0.0)
                Direction.UP -> Vec3d(0.0, velocityMultiplier, 0.0)
                Direction.DOWN -> Vec3d(0.0, -velocityMultiplier, 0.0)
                else -> Vec3d(0.0, 0.0, 0.0)
            }
            // Apply the velocity to the player
            entity.setVelocity(velocity)
            entity.velocityModified = true

            // Play sound effect
            playTeleportSound(world, entity)
        }
    }

    private fun playTeleportSound(world: World, entity: Entity) {
        // Run on a separate thread to simulate delay
        thread {
            try {
                Thread.sleep(50)

                world.playSound(null, entity.blockPos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 0.8f, 0.7f)
                Thread.sleep(40)
                world.playSound(null, entity.blockPos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 0.8f, 1f)
                Thread.sleep(300) // Delay between sounds

                world.playSound(null, entity.blockPos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 0.8f, 0.7f)
                Thread.sleep(40)
                world.playSound(null, entity.blockPos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 0.8f, 1f)
                Thread.sleep(300-40)

                world.playSound(null, entity.blockPos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 0.8f, 0.7f)
                Thread.sleep(40)
                world.playSound(null, entity.blockPos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.BLOCKS, 0.8f, 1f)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    // Find all connected blocks, handling non-linear shapes like L-shapes
    private fun findConnectedBlocks(world: World, startPos: BlockPos): List<BlockPos> {
        val visited = mutableSetOf<BlockPos>()
        val queue = ArrayDeque<Pair<BlockPos, Direction?>>()
        val connectedBlocks = mutableListOf<BlockPos>()

        queue.add(Pair(startPos, null))  // Start with the initial position and no previous direction
        visited.add(startPos)

        while (queue.isNotEmpty()) {
            val (currentPos, fromDirection) = queue.removeFirst()
            connectedBlocks.add(currentPos)

            // Check all 6 directions
            for (direction in Direction.entries) {
                if (direction == fromDirection?.opposite) continue // Skip the direction we came from

                val nextPos = currentPos.offset(direction)
                if (world.getBlockState(nextPos).block is TeleportPipeBlock && nextPos !in visited) {
                    queue.add(Pair(nextPos, direction))
                    visited.add(nextPos)
                }
            }
        }

        return connectedBlocks
    }




}
