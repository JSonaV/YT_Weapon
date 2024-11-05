package net.jsona.testmod

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.jsona.entity.ModEntities
import net.jsona.entity.client.*
import net.jsona.testmod.entity.client.BrewermobModel
import net.jsona.testmod.entity.client.DirtmobModel
import net.jsona.testmod.entity.client.MagmamobModel
import net.jsona.testmod.entity.client.RatModel
import net.minecraft.client.render.entity.EntityRenderer

class TestmodClient : ClientModInitializer {
    override fun onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.DIRTMOB) { context ->
            DirtmobRenderer(context)
        }
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DIRTMOB) { DirtmobModel.getTexturedModelData() }


        EntityRendererRegistry.register(ModEntities.MAGMAMOB) { context ->
            MagmamobRenderer(context)
        }
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MAGMAMOB) { MagmamobModel.getTexturedModelData() }


        EntityRendererRegistry.register(ModEntities.BREWERMOB) { context ->
            BrewermobRenderer(context)
        }
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.BREWERMOB) { BrewermobModel.getTexturedModelData() }

        EntityRendererRegistry.register(ModEntities.BLOCK_DESTROYING_PROJECTILE_ENTITY) { context ->
            NoModelRenderer(context)
        }


        EntityRendererRegistry.register(ModEntities.RAT) { context ->
            RatRenderer(context)
        }
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.RAT) { RatModel.getTexturedModelData() }
    }
}