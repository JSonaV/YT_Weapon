package net.jsona.entity.client

import net.jsona.entity.custom.DirtmobEntity
import net.jsona.entity.custom.MagmamobEntity
import net.jsona.testmod.entity.client.DirtmobModel
import net.jsona.testmod.entity.client.MagmamobModel
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier


class MagmamobRenderer(context: EntityRendererFactory.Context) : MobEntityRenderer<MagmamobEntity, MagmamobModel<MagmamobEntity>>(
    context,
    MagmamobModel(context.getPart(ModModelLayers.MAGMAMOB)),
    0.6f
) {
    companion object {
        private val TEXTURE = Identifier.of("testmod", "textures/entity/magmamob.png") // I checked this path.
    }

    override fun getTexture(entity: MagmamobEntity): Identifier {
        return TEXTURE
    }

    override fun render(
        mobEntity: MagmamobEntity,
        f: Float,
        g: Float,
        matrixStack: MatrixStack,
        vertexConsumerProvider: VertexConsumerProvider,
        i: Int
    ) {

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i)
    }
}
