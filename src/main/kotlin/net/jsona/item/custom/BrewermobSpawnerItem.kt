package net.jsona.item.custom

import net.jsona.entity.ModEntities
import net.jsona.entity.custom.BrewermobEntity
import net.jsona.item.ModItems
import net.minecraft.advancement.criterion.Criteria
import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluids
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.stat.Stats
import net.minecraft.text.Text
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.RaycastContext
import net.minecraft.world.World


class BrewermobSpawnerItem(settings: Settings?) : Item(settings) {
    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        val itemStack = user.getStackInHand(hand)

        // Only perform action on server side
        if (!world.isClient) {
            // Check if the player has at least 10 levels
            if (user.experienceLevel < 7) {
                user.sendMessage(Text.of("You need at least 10 levels to summon the Brewer Defense!"), true)
                return TypedActionResult.fail(itemStack)
            }

            // Check for nearby Brewermobs within a 10 block radius
            val nearbyBrewermobs = world.getEntitiesByClass(
                BrewermobEntity::class.java,
                user.boundingBox.expand(5.0), // 10-block radius
                { true }
            )

            if (nearbyBrewermobs.isNotEmpty()) {
                user.sendMessage(Text.of("There is already a Brewer Defense nearby! [5 blocks]"), true)
                return TypedActionResult.fail(itemStack)
            }

            // If player has enough levels, subtract 10 levels
            user.addExperienceLevels(-7)

            // Summon the Brewermob
            val brewermobEntity = BrewermobEntity(ModEntities.BREWERMOB, world)
            brewermobEntity.refreshPositionAndAngles(user.x, user.y, user.z, user.yaw, user.pitch)
            world.spawnEntity(brewermobEntity)

            user.sendMessage(Text.of("A Brewer Defense has been summoned!"), true)


            return TypedActionResult.success(itemStack)
        }

        return TypedActionResult.pass(itemStack)
    }
}