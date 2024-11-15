package net.jsona.entity

import BlockDestroyingProjectileEntity

import net.jsona.entity.custom.RatEntity
import net.jsona.ytwpn.Ytwpn.MOD_ID
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModEntities {


    val BLOCK_DESTROYING_PROJECTILE_ENTITY: EntityType<BlockDestroyingProjectileEntity> =
        Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(MOD_ID, "block_destroying_projectile"),
            EntityType.Builder.create(::BlockDestroyingProjectileEntity, SpawnGroup.MISC)
                .dimensions(1f, 1f) // Adjust dimensions as necessary
                .build("block_destroying_projectile")
        )
    //test
    val RAT: EntityType<RatEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        Identifier.of(MOD_ID, "rat"),
        EntityType.Builder.create(::RatEntity, SpawnGroup.CREATURE)
            .dimensions(0.5f, 0.5f)
            .build("rat")
    )
}