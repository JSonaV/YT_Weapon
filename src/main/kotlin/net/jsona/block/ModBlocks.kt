package net.jsona.block

import net.jsona.block.custom.BreakableBrickBlock
import net.jsona.block.custom.KillerBlock
import net.jsona.block.custom.PlatformBlock
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.jsona.ytwpn.Ytwpn
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.jsona.block.custom.TeleportPipeBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object ModBlocks {

    val PINK_GARNET_BLOCK: Block = registerBlock("pink_garnet_block",
        Block(AbstractBlock.Settings.create().strength(4f)
            .requiresTool().sounds(BlockSoundGroup.AMETHYST_BLOCK)))
    val PIPE_DEATH_BLOCK: Block = registerBlock("pipe_death_block",
        KillerBlock(AbstractBlock.Settings.copy(Blocks.CACTUS).strength(20f)))
    val PLATFORM_BLOCK: Block = registerBlock("platform_block",
        PlatformBlock(AbstractBlock.Settings.create().strength(4f)))
    val TELEPORT_PIPE_BLOCK: Block = registerBlock("teleport_pipe_block",
        TeleportPipeBlock(AbstractBlock.Settings.create().strength(4f)))
    val BREAKABLE_BRICK_BLOCK: Block = registerBlock("breakable_brick_block",
        BreakableBrickBlock(AbstractBlock.Settings.create().strength(4f)))

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
            entries.add(PINK_GARNET_BLOCK)
            entries.add(PIPE_DEATH_BLOCK)
            entries.add(PLATFORM_BLOCK)
            entries.add(TELEPORT_PIPE_BLOCK)
            entries.add(BREAKABLE_BRICK_BLOCK)
        }
    }
}