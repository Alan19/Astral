package com.alan19.astral.entity.crystalspider;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.RangedAttackMob;

import java.util.EnumSet;

public class WebAttackGoal extends Goal {

    private final Mob entityHost;
    private final RangedAttackMob rangedAttackEntityHost;
    private LivingEntity attackTarget;
    private int rangedAttackTime = -1;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;

    public WebAttackGoal(RangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
        this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
    }

    public WebAttackGoal(RangedAttackMob attacker, double movespeed, int attackIntervalMin, int maxAttackTime, float maxAttackDistanceIn) {
        if (!(attacker instanceof LivingEntity)) {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else {
            this.rangedAttackEntityHost = attacker;
            this.entityHost = (Mob) attacker;
            this.entityMoveSpeed = movespeed;
            this.attackIntervalMin = attackIntervalMin;
            this.maxRangedAttackTime = maxAttackTime;
            this.attackRadius = maxAttackDistanceIn;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        LivingEntity livingentity = this.entityHost.getTarget();
        if (livingentity != null && livingentity.isAlive()) {
            this.attackTarget = livingentity;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return this.canUse() || !this.entityHost.getNavigation().isDone();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    @Override
    public void stop() {
        this.attackTarget = null;
        this.seeTime = 0;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    @Override
    public void tick() {
        double d0 = this.entityHost.distanceToSqr(this.attackTarget.getX(), this.attackTarget.getY(), this.attackTarget.getZ());
        boolean flag = this.entityHost.getSensing().hasLineOfSight(this.attackTarget);
        if (flag) {
            this.seeTime++;
        }
        else {
            this.seeTime = 0;
        }

        if (d0 <= this.maxAttackDistance && this.seeTime >= 5) {
            this.entityHost.getNavigation().stop();
        }
        else {
            this.entityHost.getNavigation().moveTo(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookControl().setLookAt(this.attackTarget, 30.0F, 30.0F);
        if (--this.rangedAttackTime == 0) {
            if (!flag) {
                return;
            }

            float f = Mth.sqrt((float) d0) / this.attackRadius;
            float lvt_5_1_ = Mth.clamp(f, 0.1F, 1.0F);
            this.rangedAttackEntityHost.performRangedAttack(this.attackTarget, lvt_5_1_);
            this.rangedAttackTime = Mth.floor(f * (this.maxRangedAttackTime - this.attackIntervalMin) + this.attackIntervalMin);
        }
        else if (this.rangedAttackTime < 0) {
            float f2 = Mth.sqrt((float) d0) / this.attackRadius;
            this.rangedAttackTime = Mth.floor(f2 * (this.maxRangedAttackTime - this.attackIntervalMin) + this.attackIntervalMin);
        }
    }
}
