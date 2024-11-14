package net.jsona.item.custom

import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Equipment
import net.minecraft.item.Item
import net.minecraft.item.ItemUsageContext
import net.minecraft.util.ActionResult


class CloverItem(settings: Settings?) : Item(settings), Equipment  {
    override fun getSlotType(): EquipmentSlot {
        return EquipmentSlot.HEAD
    }

    override fun useOnBlock(context: ItemUsageContext?): ActionResult {



        return super.useOnBlock(context)
    }
}