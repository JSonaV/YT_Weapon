package net.jsona.block.custom

import net.jsona.item.ModItems
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.stream.Stream


class FundyPanBlock(settings: Settings) : Block(settings) {
    val SHAPE_N = Stream.of(
        createCuboidShape(1.0, 0.0, 2.0, 2.0, 16.0, 3.0),
        createCuboidShape(1.0, 0.0, 14.0, 2.0, 16.0, 15.0),
        createCuboidShape(14.0, 0.0, 14.0, 15.0, 16.0, 15.0),
        createCuboidShape(14.0, 0.0, 2.0, 15.0, 16.0, 3.0),
        createCuboidShape(2.0, 2.0, 3.0, 4.0, 15.0, 14.0),
        createCuboidShape(4.0, 2.0, 3.0, 12.0, 15.0, 5.0),
        createCuboidShape(4.0, 2.0, 12.0, 12.0, 15.0, 14.0),
        createCuboidShape(12.0, 2.0, 3.0, 14.0, 15.0, 14.0),
        createCuboidShape(3.0, 1.0, 4.0, 13.0, 2.0, 13.0)
    ).reduce { v1, v2 -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR) }.get();

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return SHAPE_N
    }

    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!world.isClient) {
            val itemsOnBlock = mutableListOf<Entity>()
            val requiredItems = setOf(
                Items.COOKED_BEEF,
                Items.COOKED_COD,
                Items.COOKED_PORKCHOP,
                Items.COOKED_CHICKEN,
                Items.CARROT,
                Items.COOKIE,
            )
            val foundItems = mutableSetOf<Item>()

            val entitiesOnBlock = world.getEntitiesByClass(Entity::class.java, getBoundingBox(pos).offset(0.0, 0.2, 0.0)) { true }

            for (ent in entitiesOnBlock) {
                if (ent is ItemEntity) {
                    val item = ent.stack.item
                    if (item in requiredItems) {
                        foundItems.add(item)
                    }
                }
            }

            // Check if all required items are present
            if (foundItems.containsAll(requiredItems)) {
                print("HUWOO")
                for (ent in entitiesOnBlock) {
                    if (ent is ItemEntity) {
                        ent.kill()
                    }
                }
                val schopEntity = ItemEntity(world, pos.x.toDouble() + 0.5, pos.y.toDouble() + 0.5, pos.z.toDouble() + 0.5, ModItems.STEAKCOCHICARROCHOPIE?.defaultStack)
                schopEntity.setVelocity(0.0, 0.6,0.0)
                world.spawnEntity(schopEntity)
            }
        }
        super.onSteppedOn(world, pos, state, entity)
    }

    private fun getBoundingBox(pos: BlockPos) = Box(pos)
}
