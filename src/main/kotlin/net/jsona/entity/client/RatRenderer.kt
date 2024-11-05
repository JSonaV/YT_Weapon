package net.jsona.entity.client

import net.jsona.entity.custom.RatEntity
import net.jsona.entity.custom.DirtmobEntity
import net.jsona.testmod.entity.client.RatModel
import net.jsona.testmod.entity.client.DirtmobModel
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier


class RatRenderer(context: EntityRendererFactory.Context) : MobEntityRenderer<RatEntity, RatModel<RatEntity>>(
    context,
    RatModel(context.getPart(ModModelLayers.RAT)),
    0.6f
) {
    companion object {
        private val TEXTURE = Identifier.of("testmod", "textures/entity/rat.png") // I checked this path.
    }

    override fun getTexture(entity: RatEntity): Identifier {
        return TEXTURE
    }

    override fun render(
        mobEntity: RatEntity,
        f: Float,
        g: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        i: Int
    ) {

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }
}
