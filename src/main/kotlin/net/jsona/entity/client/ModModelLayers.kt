package net.jsona.entity.client

import net.jsona.testmod.Testmod
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.util.Identifier

object ModModelLayers {
    val DIRTMOB: EntityModelLayer =
        EntityModelLayer(Identifier.of(Testmod.MOD_ID, "dirtmob"),  "main")

    val MAGMAMOB: EntityModelLayer =
        EntityModelLayer(Identifier.of(Testmod.MOD_ID, "magmamob"),  "main")

    val BREWERMOB: EntityModelLayer =
        EntityModelLayer(Identifier.of(Testmod.MOD_ID, "brewermob"),  "brewermob")

    val RAT: EntityModelLayer =
        EntityModelLayer(Identifier.of(Testmod.MOD_ID, "rat"),  "main")

}