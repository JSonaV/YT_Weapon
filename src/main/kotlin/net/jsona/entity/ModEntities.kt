package net.jsona.entity

import BlockDestroyingProjectileEntity
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.jsona.entity.custom.BrewermobEntity
import net.jsona.entity.custom.DirtmobEntity
import net.jsona.entity.custom.MagmamobEntity
import net.jsona.entity.custom.RatEntity
import net.jsona.testmod.Testmod.MOD_ID
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.World

object ModEntities {
    val DIRTMOB: EntityType<DirtmobEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(MOD_ID, "dirtmob"),
        EntityType.Builder.create(::DirtmobEntity, SpawnGroup.CREATURE)
            .dimensions(1f, 1f) // Use your desired dimensions\
            .build("dirtmob")
    )

    val MAGMAMOB: EntityType<MagmamobEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(MOD_ID, "magmamob"),
        EntityType.Builder.create(::MagmamobEntity, SpawnGroup.CREATURE)
            .dimensions(1f, 1f) // Use your desired dimensions\
            .build("magmamob")
    )

    val BREWERMOB: EntityType<BrewermobEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(MOD_ID, "brewermob"),
        EntityType.Builder.create(::BrewermobEntity, SpawnGroup.CREATURE)
            .dimensions(1f, 1f) // Use your desired dimensions\
            .build("brewermob")
    )


    val BLOCK_DESTROYING_PROJECTILE_ENTITY: EntityType<BlockDestroyingProjectileEntity> =
        Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of("yourmodid", "block_destroying_projectile"),
            EntityType.Builder.create(::BlockDestroyingProjectileEntity, SpawnGroup.MISC)
                .dimensions(1f, 1f) // Adjust dimensions as necessary
                .build("block_destroying_projectile")
        )

    val RAT: EntityType<RatEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(MOD_ID, "rat"),
        EntityType.Builder.create(::RatEntity, SpawnGroup.CREATURE)
            .dimensions(0.5f, 0.5f)
            .build("rat")
    )
}