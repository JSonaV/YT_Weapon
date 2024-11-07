package net.jsona.block

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.jsona.block.custom.*
import net.jsona.ytwpn.Ytwpn
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object ModBlocks {


    val FUNDY_PAN_BLOCK: Block = registerBlock("fundy_cauldron_block",
        FundyPanBlock(AbstractBlock.Settings.create().strength(4f)))

    fun registerBlock(name:String, block:Block): Block {
        registerBlockItem(name, block)
        return Registry.register(Registries.BLOCK, Identifier.of(Ytwpn.MOD_ID, name), block)
    }

    fun registerBlockItem(name:String, block: Block){
        Registry.register(Registries.ITEM, Identifier.of(Ytwpn.MOD_ID, name), BlockItem(block, Item.Settings()))
    }

    fun registerModBlocks(){
        Ytwpn.logger.info("Registering blocks for "+ Ytwpn.MOD_ID)
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register{ entries ->
            entries.add(FUNDY_PAN_BLOCK)
        }
    }
}