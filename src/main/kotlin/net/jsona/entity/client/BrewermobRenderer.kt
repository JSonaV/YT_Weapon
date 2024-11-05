package net.jsona.entity.client

import net.jsona.entity.custom.BrewermobEntity
import net.jsona.entity.custom.DirtmobEntity
import net.jsona.testmod.entity.client.BrewermobModel
import net.jsona.testmod.entity.client.DirtmobModel
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier


class BrewermobRenderer(context: EntityRendererFactory.Context) : MobEntityRenderer<BrewermobEntity, BrewermobModel<BrewermobEntity>>(
    context,
    BrewermobModel(context.getPart(ModModelLayers.BREWERMOB)),
    0.6f
) {
    companion object {
        private val TEXTURE = Identifier.of("testmod", "textures/entity/brewermob.png") // I checked this path.
    }

    override fun getTexture(entity: BrewermobEntity): Identifier {
        return TEXTURE
    }

    override fun render(
        mobEntity: BrewermobEntity,
        f: Float,
        g: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        i: Int
    ) {

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }
}
