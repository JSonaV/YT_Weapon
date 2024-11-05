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
import net.minecraft.registry.tag.ItemTags
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class RatEntity(entityType: EntityType<out TameableEntity>, world: World) : TameableEntity(entityType, world){
    var isShooting = false
    var idleAnimationState: AnimationState = AnimationState()
    var idleAnimationTimeout = 0

    var timeLeft = 180

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
        goalSelector.add(6, WanderAroundFarGoal(this, 1.0))
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
        return ModEntities.RAT.create(world)
    }

    override fun isBreedingItem(stack: ItemStack): Boolean {
        return stack.isIn(ItemTags.MEAT)
    }

    override fun isPushable(): Boolean {
        return true
    }

    companion object {
        fun createAttributes(): DefaultAttributeContainer.Builder {
            return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_ARMOR, 0.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.3)
            }
        }

    override fun mobTick() {
        timeLeft--
        if (timeLeft <= 0){
            kill()
        }
        super.mobTick()
    }
}