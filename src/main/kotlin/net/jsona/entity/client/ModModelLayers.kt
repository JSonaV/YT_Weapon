package net.jsona.entity.client

import net.jsona.ytwpn.Ytwpn
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.util.Identifier

object ModModelLayers {

    val RAT: EntityModelLayer =
        EntityModelLayer(Identifier.of(Ytwpn.MOD_ID, "rat"),  "main")

}