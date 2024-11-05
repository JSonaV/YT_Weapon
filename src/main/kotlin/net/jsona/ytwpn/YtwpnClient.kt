package net.jsona.ytwpn

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.jsona.entity.ModEntities
import net.jsona.entity.client.*
import net.jsona.ytwpn.entity.client.RatModel

class YtwpnClient : ClientModInitializer {
    override fun onInitializeClient() {

        EntityRendererRegistry.register(ModEntities.BLOCK_DESTROYING_PROJECTILE_ENTITY) { context ->
            NoModelRenderer(context)
        }


        EntityRendererRegistry.register(ModEntities.RAT) { context ->
            RatRenderer(context)
        }
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.RAT) { RatModel.getTexturedModelData() }
    }
}