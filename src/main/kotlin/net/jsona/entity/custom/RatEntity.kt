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
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.passive.TameableEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.projectile.thrown.PotionEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.potion.Potions
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class RatEntity(entityType: EntityType<out TameableEntity>, world: World) : TameableEntity(entityType, world){
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
        // I just took these from Wolf lol
        goalSelector.add(0, SwimGoal(this))
        goalSelector.add(5, MeleeAttackGoal(this, 1.0, false))
        goalSelector.add(7, ShootProjectileGoal(this));
        goalSelector.add(10, LookAroundGoal(this))
        targetSelector.add(1, TrackOwnerAttackerGoal(this))
        targetSelector.add(2, AttackWithOwnerGoal(this))
        targetSelector.add(3, RevengeGoal(this).setGroupRevenge())
    }

    override fun tick() {
        super.tick()
        if (world.isClient()){
            setupAnimationStates()
        }
    }

    fun setTheOwner(player: PlayerEntity) {
        this.setOwner(player)
    }

    override fun createChild(world: ServerWorld, entity: PassiveEntity): PassiveEntity? {
        return ModEntities.BREWERMOB.create(world)
    }

    override fun isBreedingItem(stack: ItemStack?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isPushable(): Boolean {
        return true
    }

    companion object {
        fun createAttributes(): DefaultAttributeContainer.Builder {
            return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.7)
                .add(EntityAttributes.GENERIC_ARMOR, 0.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
            }
        }


    class ShootProjectileGoal(private val mob: RatEntity) : Goal() {
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