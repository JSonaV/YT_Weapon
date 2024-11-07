package net.jsona.item

import net.minecraft.component.type.FoodComponent
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects

object ModFoodComponents {
    val STEAKCOCHICARROCHOPIE = FoodComponent.Builder()
        .statusEffect(StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 5, 2), 1f)
        .statusEffect(StatusEffectInstance(StatusEffects.SPEED, 20 * 5, 1), 1f)
        .statusEffect(StatusEffectInstance(StatusEffects.ABSORPTION, 20 * 5, 2), 1f)
        .nutrition(4)
        .saturationModifier(1.2F)
        .alwaysEdible()
        .build()
}