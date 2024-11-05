package net.jsona.entity.client

import net.jsona.entity.custom.DirtmobEntity
import net.jsona.testmod.entity.client.DirtmobModel
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier


class DirtmobRenderer(context: EntityRendererFactory.Context) : MobEntityRenderer<DirtmobEntity, DirtmobModel<DirtmobEntity>>(
    context,
    DirtmobModel(context.getPart(ModModelLayers.DIRTMOB)),
    0.6f
) {
    companion object {
        private val TEXTURE = Identifier.of("testmod", "textures/entity/dirtmob.png") // I checked this path.
    }

    override fun getTexture(entity: DirtmobEntity): Identifier {
        return TEXTURE
    }

    override fun render(
        mobEntity: DirtmobEntity,
        f: Float,
        g: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        i: Int
    ) {

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }
}
