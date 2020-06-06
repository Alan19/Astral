package com.alan19.astral.entities;

import com.alan19.astral.effects.AstralEffects;
import com.alan19.astral.util.Constants;
import com.alan19.astral.util.MobUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class CrystalSpiderEntity extends SpiderEntity {

    public CrystalSpiderEntity(EntityType<? extends SpiderEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
        this.getAttribute(Constants.ASTRAL_ATTACK_DAMAGE).setBaseValue(2);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        //Do nothing when falling
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new CrystalSpiderEntity.AttackGoal(this));
        this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
        this.targetSelector.addGoal(2, new TargetGoal<>(this, PlayerEntity.class));
        this.targetSelector.addGoal(3, new TargetGoal<>(this, IronGolemEntity.class));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(6, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(SpiderEntity spider) {
            super(spider, 1.0D, true);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !this.attacker.isBeingRidden();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();
            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            }
            else {
                return super.shouldContinueExecuting();
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.getWidth();
        }
    }

    /**
     * Crystal Spiders target Astral Players and Iron Golems when it's dark
     *
     * @param <T> The entity target as a class
     */
    static class TargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public TargetGoal(SpiderEntity spider, Class<T> classTarget) {
            super(spider, classTarget, true);
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        @Override
        public boolean shouldExecute() {
            float f = this.goalOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }

        @Override
        @ParametersAreNonnullByDefault
        protected boolean isSuitableTarget(@Nullable LivingEntity potentialTarget, EntityPredicate targetPredicate) {
            return potentialTarget != null && potentialTarget.isPotionActive(AstralEffects.ASTRAL_TRAVEL.get());
        }
    }

    @Override
    public boolean attackEntityAsMob(@Nonnull Entity entityIn) {
        boolean isAttackSuccessful = MobUtils.attackEntityAsMobWithAstralDamage(this, entityIn);
        if (isAttackSuccessful) {
            if (entityIn instanceof LivingEntity) {
                int mindVenomDuration = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    mindVenomDuration = 7;
                }
                else if (this.world.getDifficulty() == Difficulty.HARD) {
                    mindVenomDuration = 15;
                }

                if (mindVenomDuration > 0) {
                    ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(AstralEffects.MIND_VENOM.get(), mindVenomDuration * 20, 0));
                }
            }
            return true;
        }
        else {
            return false;
        }
    }
}
