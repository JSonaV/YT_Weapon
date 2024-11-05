package net.jsona.entity.custom

import net.jsona.entity.ModEntities
import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.PotionContentsComponent
import net.minecraft.entity.AnimationState
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.entity.projectile.thrown.PotionEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.potion.Potions
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.WorldEvents

class BrewermobEntity(entityType: EntityType<out AnimalEntity>, world: World) : AnimalEntity(entityType, world){
    var isShooting = false
    var idleAnimationState: AnimationState = AnimationState()
    var idleAnimationTimeout = 0

    fun setupAnimationStates(){
        if (idleAnimationTimeout <= 0){
            idleAnimationTimeout = 40
            idleAnimationState.start(this.age)
        } else {
            --idleAnimationTimeout
        }
    }

    override fun initGoals() {
        goalSelector.add(0, SwimGoal(this))

        this.goalSelector.add(7, ShootProjectileGoal(this));
        this.targetSelector.add(1, ActiveTargetGoal(this, HostileEntity::class.java, 10, true, false) { entity ->
            Math.abs(entity.y - this.y) <= 4.0
        })
    }

    override fun tick() {
        super.tick()
        if (world.isClient()){
            setupAnimationStates()
        }
    }

    override fun onKilledOther(world: ServerWorld, other: LivingEntity): Boolean {
        val bl = super.onKilledOther(world, other)

        // The dirtmob killed something, now reward nearby players
        val levelAmount = 5  // Amount of XP to give

        // Get nearby players within 10 blocks
        val nearbyPlayers = world.getEntitiesByClass(
            PlayerEntity::class.java,
            Box(pos.add(-500.0, -10.0, -500.0), pos.add(500.0, 10.0, 500.0))
        ) { true }

        // Loop through each player and give them experience
        for (player in nearbyPlayers) {
            player.addExperienceLevels(levelAmount)
            player.world.playSound(null, player.blockPos, SoundEvents.ENTITY_ARROW_HIT_PLAYER, player.soundCategory, 1.0f, 1.0f)

        }
        return bl
    }

    override fun createChild(world: ServerWorld, entity: PassiveEntity): PassiveEntity? {
        return ModEntities.BREWERMOB.create(world)
    }

    override fun isBreedingItem(stack: ItemStack?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPushable(): Boolean {
        return false
    }

    companion object {
        fun createAttributes(): DefaultAttributeContainer.Builder {
            return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
            }
        }


    class ShootProjectileGoal(private val mob: BrewermobEntity) : Goal() {
        var cooldown = 0

        override fun canStart(): Boolean {
            // Check if there is a target and mob can see it
            return mob.target != null && mob.canSee(mob.target)
        }

        override fun start() {
            cooldown = 0
        }

        override fun stop() {
            mob.isShooting = false
        }

        override fun shouldRunEveryTick(): Boolean {
            return true
        }

        override fun tick() {
            val target: LivingEntity? = mob.target

            if (target != null && mob.canSee(target)) {
                cooldown++


                if (cooldown == 60) {
                    val world = mob.world
                    val potionStack = ItemStack(Items.SPLASH_POTION)
                    potionStack.set(
                        DataComponentTypes.POTION_CONTENTS,
                        PotionContentsComponent(Potions.HEALING)
                    )
                    val potionEntity = PotionEntity(world, mob)
                    potionEntity.setItem(potionStack)

                    potionEntity.setVelocity(Vec3d(0.0, 0.7, 0.0))
                    potionEntity.setPosition(mob.x, mob.y + 1.0, mob.z)

                    world.spawnEntity(potionEntity)
                    // Reset the cooldown with a delay
                    cooldown = -40
                }

                // Update mob shooting state
                mob.isShooting = cooldown > 10
            } else if (cooldown > 0) {
                cooldown--
            }
        }
    }
}