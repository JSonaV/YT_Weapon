package net.jsona.entity.client

import BlockDestroyingProjectileEntity
import net.minecraft.client.render.entity.EntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.util.Identifier
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.client.render.VertexConsumerProvider

class NoModelRenderer(context: EntityRendererFactory.Context) : EntityRenderer<BlockDestroyingProjectileEntity>(context) {

    // No texture needed, return null
    override fun getTexture(entity: BlockDestroyingProjectileEntity): Identifier? = null

    // Override render method without doing anything, making the entity invisible
    override fun render(
        entity: BlockDestroyingProjectileEntity,
        yaw: Float,
        tickDelta: Float,
        matrixStack: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int
    ) {
        // Do nothing to skip rendering a model
    }
}