package net.jsona.item.custom

import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.Equipment
import net.minecraft.item.Item


class CloverItem(settings: Settings?) : Item(settings), Equipment  {
    override fun getSlotType(): EquipmentSlot {
        return EquipmentSlot.HEAD
    }
}