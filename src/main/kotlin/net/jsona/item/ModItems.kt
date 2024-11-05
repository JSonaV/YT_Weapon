package net.jsona.item

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.jsona.item.custom.*
import net.jsona.testmod.Testmod
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModItems {
    val PINK_GARNET = registerItem("pink_garnet", Item(Item.Settings()))
    val RAW_PINK_GARNET = registerItem("raw_pink_garnet", Item(Item.Settings()))
    val METAL_DETECTOR = registerItem("metal_detector", MetalDetectorItem(Item.Settings()))
    val MULTICAST_ROD = registerItem("multicast_rod", MultiCastFishingRodItem(Item.Settings().maxDamage(64)))
    val DOUBLE_BUCKET_EMPTY = registerItem("double_bucket", DoubleBucketItem(Item.Settings()))
    val DOUBLE_BUCKET_WATER = registerItem("double_water_bucket", DoubleBucketWaterItem(Item.Settings()))
    val MAGMAMOB_SPAWNER = registerItem("magmamob_spawner", MagmamobSpawnerItem(Item.Settings()))
    val DIRTMOB_SPAWNER = registerItem("dirtmob_spawner", DirtmobSpawnerItem(Item.Settings()))
    val BREWERMOB_SPAWNER = registerItem("brewermob_spawner", BrewermobSpawnerItem(Item.Settings()))
    val BEGIN_RAID = registerItem("begin_raid", BeginRaidItem(Item.Settings()))
    var FLATTENER = registerItem("flattener", FlattenerItem(Item.Settings()))
    var RATINATOR = registerItem("ratinator", Ratinator(Item.Settings()))


    fun registerItem(name:String,item:Item): Item? {
        return Registry.register(Registries.ITEM, Identifier.of(Testmod.MOD_ID, name), item)
    }

    fun registerModItems(){
        Testmod.logger.info("Registering items for "+ Testmod.MOD_ID)

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register { entries ->
            entries.add(PINK_GARNET)
            entries.add(RAW_PINK_GARNET)
            entries.add(METAL_DETECTOR)
            entries.add(MULTICAST_ROD)
            entries.add(DOUBLE_BUCKET_EMPTY)
            entries.add(DOUBLE_BUCKET_WATER)
            entries.add(MAGMAMOB_SPAWNER)
            entries.add(BREWERMOB_SPAWNER)
            entries.add(DIRTMOB_SPAWNER)
            entries.add(BEGIN_RAID)
            entries.add(FLATTENER)
            entries.add(RATINATOR)
}   }
}