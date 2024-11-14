package net.jsona.item

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.jsona.item.custom.*
import net.jsona.ytwpn.Ytwpn
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ModItems {
    val FLATTENER = registerItem("flattener", FlattenerItem(Item.Settings()))
    val RATINATOR = registerItem("ratinator", RatinatorItem(Item.Settings()))
    val CLOVER = registerItem("clover", CloverItem(Item.Settings()))
    val THE_ANVIL = registerItem("the_anvil", TheAnvilItem(Item.Settings()))
    val STEAKCOCHICARROCHOPIE = registerItem("steakcochicarrochopie", SteacoItem(Item.Settings().food(ModFoodComponents.STEAKCOCHICARROCHOPIE)))


    fun registerItem(name:String,item:Item): Item? {
        return Registry.register(Registries.ITEM, Identifier.of(Ytwpn.MOD_ID, name), item)
    }

    fun registerModItems(){
        Ytwpn.logger.info("Registering items for "+ Ytwpn.MOD_ID)

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register { entries ->
            entries.add(FLATTENER)
            entries.add(RATINATOR)
            entries.add(CLOVER)
            entries.add(THE_ANVIL)
            entries.add(STEAKCOCHICARROCHOPIE)
        }
    }
}