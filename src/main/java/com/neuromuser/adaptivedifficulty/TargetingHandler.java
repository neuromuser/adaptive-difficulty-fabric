package com.neuromuser.adaptivedifficulty;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class TargetingHandler {

    public static boolean canTarget(LivingEntity entity, LivingEntity target) {
        if (!(target instanceof PlayerEntity player)) {
            return true;
        }

        Difficulty difficulty = DifficultyHelper.getDifficulty(player);
        return difficulty.allowsHostileMobTargeting();
    }
}