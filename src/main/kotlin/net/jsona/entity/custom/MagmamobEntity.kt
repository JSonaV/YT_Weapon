package net.jsona.entity.custom

import net.jsona.entity.ModEntities
import net.minecraft.entity.AnimationState
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.damage.DamageSources
import net.minecraft.entity.damage.DamageType
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.AnimalEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.FireballEntity
import net.minecraft.item.ItemStack
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraft.world.WorldEvents

class MagmamobEntity(entityType: EntityType<out AnimalEntity>, world: World) : AnimalEntity(entityType, world){
    var isShooting = false
    var idleAnimationState: AnimationState = AnimationState()
    var attackAnimationState: AnimationState = AnimationState()
    var idleAnimationTimeout = 0

    fun setupAnimationStates(){
        if (attackingTime <= 0) {
            if (idleAnimationTimeout <= 0) {
                idleAnimationTimeout = 20 * 4
                idleAnimationState.start(this.age)
            } else {
                --idleAnimationTimeout
            }
        } else {
            if (attackingTime == 20) {
                attackAnimationState.start(this.age)
            }
            --attackingTime
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

        val levelAmount = 5  // Amount of XP to give

        // Get nearby players within 500 blocks
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
        return ModEntities.MAGMAMOB.create(world)
    }

    override fun isBreedingItem(stack: ItemStack?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPushable(): Boolean {
        return false
    }

    companion object {
        var attackingTime = 0

        fun createAttributes(): DefaultAttributeContainer.Builder {
            return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1.0)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.0)
            }
        }


    class ShootProjectileGoal(private val mob: MagmamobEntity) : Goal() {
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
                if (cooldown == 20) {
                    val world = mob.world
                    val radius = 5.0
                    attackingTime = 20
                    for (i in 0 until 100) {

                        val randomX = (world.random.nextDouble() - 0.5) * radius * 2
                        val randomY = world.random.nextDouble() * 2
                        val randomZ = (world.random.nextDouble() - 0.5) * radius * 2

                        world.addParticle(ParticleTypes.FLAME,  mob.x + randomX, mob.y + randomY, mob.z + randomZ, randomX/100, randomY/100, randomZ/100)
                    }

                    if (!world.isClient) {
                        world.playSound(null, mob.blockPos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.PLAYERS, 1.0f, 1.0f)

                        val entities = world.getOtherEntities(mob, mob.boundingBox.expand(radius))
                        for (entity in entities) {
                            if (entity is HostileEntity && entity != mob) {
                                entity.setOnFireFor(5.0f)
                                mob.health -= 2
                            }
                        }

                    }

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