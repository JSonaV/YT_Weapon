package net.jsona.ytwpn.mixin;

import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.*;

@Mixin(FishingRodItem.class)
public class FishingRodItemMixin {
    int bobberCount = 1;
    @Overwrite
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        System.out.println("HEYYY");
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            // Get all fishing bobbers owned by the player within a certain range
            List<FishingBobberEntity> bobbers = world.getEntitiesByClass(FishingBobberEntity.class, user.getBoundingBox().expand(50.0), bobber -> bobber.getOwner() == user);

            if (!bobbers.isEmpty()) {
                // Reel in all bobbers
                for (FishingBobberEntity bobber : bobbers) {
                    int damage = bobber.use(itemStack);
                    itemStack.damage(damage, user, LivingEntity.getSlotForHand(hand));
                }

                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE,
                        SoundCategory.NEUTRAL,
                        1.0f,
                        0.4f / (world.random.nextFloat() * 0.4f + 0.8f)
                );
                user.emitGameEvent(GameEvent.ITEM_INTERACT_FINISH);
            } else {
                // Cast new bobbers
                world.playSound(
                        null,
                        user.getX(),
                        user.getY(),
                        user.getZ(),
                        SoundEvents.ENTITY_FISHING_BOBBER_THROW,
                        SoundCategory.NEUTRAL,
                        0.5f,
                        0.4f / (world.random.nextFloat() * 0.4f + 0.8f)
                );

                // Spawn new bobbers


                ItemEnchantmentsComponent component = itemStack.getEnchantments();
                Set<RegistryEntry<Enchantment>> enchantments = component.getEnchantments();

                final List<Boolean> result = new ArrayList<Boolean>();

                bobberCount = 1;

                enchantments.forEach((enchantment) -> {
                    if (enchantment.matchesId(Identifier.of("testmod:multicast"))) {
                        if (component.getLevel(enchantment) > 0) {
                            bobberCount = component.getLevel(enchantment) * 10;
                        }
                    }
                });


                for (int i = 0; i < bobberCount; i++) {
                    FishingBobberEntity fishingBobberEntity = new FishingBobberEntity(user, world, 0, 0);

                    // Get the player's look direction
                    Vec3d lookDirection = user.getRotationVec(1.0f);

                    // Add some variation to the direction
                    double variationX = -0.1 + (0.5 * new Random().nextDouble(-1, 1));
                    double variationZ = -0.1 + (0.5 * new Random().nextDouble(-1, 1));

                    // Set the velocity in the direction the player is looking, with added variation
                    double velocityX = lookDirection.x + variationX;
                    double velocityY = lookDirection.y * 0.5; // Reduce Y velocity slightly to prevent them from flying too high
                    double velocityZ = lookDirection.z + variationZ;

                    fishingBobberEntity.setVelocity(velocityX, velocityY, velocityZ);
                    world.spawnEntity(fishingBobberEntity);
                }
            }

            user.incrementStat(Stats.USED.getOrCreateStat((FishingRodItem) (Object) this));
            user.emitGameEvent(GameEvent.ITEM_INTERACT_START);
        }



        return TypedActionResult.success(itemStack, world.isClient());
    }


}


