package net.jsona.block.custom

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class BreakableBrickBlock(settings: Settings) : Block(settings) {
    val COLLISION_SHAPE: VoxelShape = createCuboidShape(0.0, 1.0, 0.0, 16.0, 16.0, 16.0)
    val OUTLINE_SHAPE: VoxelShape = createCuboidShape(0.0, 1.0, 0.0, 16.0, 16.0, 16.0)


    override fun getCollisionShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return COLLISION_SHAPE
    }

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return OUTLINE_SHAPE
    }

    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        val player = entity.pos.y
        println("$player and $pos")
        if (!world.isClient && entity is PlayerEntity) {
            if (!entity.isOnGround) {
                // Player is moving upwards and hits the block with head, break the block
                world.breakBlock(pos, true) // `true` drops the block item
            }
        }
    }
}