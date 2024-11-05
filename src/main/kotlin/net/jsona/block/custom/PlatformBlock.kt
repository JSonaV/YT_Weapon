package net.jsona.block.custom

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView


class PlatformBlock(settings: Settings) : Block(settings) {
    // Define VoxelShapes as static variables for reuse
    companion object {
        val BOTTOM: BooleanProperty = BooleanProperty.of("bottom")
        private val NORMAL_OUTLINE_SHAPE: VoxelShape
        private val BOTTOM_OUTLINE_SHAPE: VoxelShape
        private val COLLISION_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0)
        private val OUTLINE_SHAPE = VoxelShapes.fullCube().offset(0.0, -1.0, 0.0)
        init {
            val voxelShape = Block.createCuboidShape(0.0, 14.0, 0.0, 16.0, 16.0, 16.0)
            val voxelShape2 = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 16.0, 2.0)
            val voxelShape3 = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 16.0, 2.0)
            val voxelShape4 = Block.createCuboidShape(0.0, 0.0, 14.0, 2.0, 16.0, 16.0)
            val voxelShape5 = Block.createCuboidShape(14.0, 0.0, 14.0, 16.0, 16.0, 16.0)
            NORMAL_OUTLINE_SHAPE = VoxelShapes.union(voxelShape, voxelShape2, voxelShape3, voxelShape4, voxelShape5)

            val voxelShape6 = Block.createCuboidShape(0.0, 0.0, 0.0, 2.0, 2.0, 16.0)
            val voxelShape7 = Block.createCuboidShape(14.0, 0.0, 0.0, 16.0, 2.0, 16.0)
            val voxelShape8 = Block.createCuboidShape(0.0, 0.0, 14.0, 16.0, 2.0, 16.0)
            val voxelShape9 = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 2.0)
            BOTTOM_OUTLINE_SHAPE = VoxelShapes.union(COLLISION_SHAPE, NORMAL_OUTLINE_SHAPE, voxelShape7, voxelShape6, voxelShape9, voxelShape8)
        }
    }

    // Add the BOTTOM property to the block's state
    init {
        this.defaultState = this.stateManager.defaultState.with(BOTTOM, false)
    }

    // Append the BOTTOM property to the block state manager
    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(BOTTOM)
    }

    // Returns the outline shape based on whether the player is holding the block or if it's the bottom shape
    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return VoxelShapes.cuboid(0.0, 0.5, 0.0, 1.0, 1.0, 1.0)
    }

    // Defines the collision shape based on the player's position relative to the block
    override fun getCollisionShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return if (context.isAbove(VoxelShapes.fullCube(), pos, true) && !context.isDescending()) {
            NORMAL_OUTLINE_SHAPE
        } else {
            VoxelShapes.empty()
        }
    }

    // For raycasting purposes, we always return the full cube shape
    override fun getRaycastShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos
    ): VoxelShape {
        return VoxelShapes.fullCube()
    }
}

