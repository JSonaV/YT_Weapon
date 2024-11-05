package net.jsona.block.custom

import com.mojang.serialization.MapCodec
import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldAccess

class KillerBlock(settings: Settings) : Block(settings) {
    val COLLISION_SHAPE: VoxelShape = createCuboidShape(1.0, 0.0, 1.0, 15.0, 15.0, 15.0)
    val OUTLINE_SHAPE: VoxelShape = createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0)


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

    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction?,
        neighborState: BlockState?,
        world: WorldAccess,
        pos: BlockPos?,
        neighborPos: BlockPos?
    ): BlockState {
        if (!state.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1)
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos)
    }

    override fun onEntityCollision(state: BlockState?, world: World, pos: BlockPos?, entity: Entity) {
        entity.damage(world.damageSources.cactus(), 100.0f)
    }

}
