package com.neuromuser.adaptivedifficulty;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class DamageHandler {

    public static float modifyDamage(LivingEntity entity, DamageSource source, float amount) {
        if (!(entity instanceof PlayerEntity player)) {
            return amount;
        }

        Difficulty difficulty = DifficultyHelper.getDifficulty(player);

        if (source.isOf(net.minecraft.entity.damage.DamageTypes.STARVE)) {
            if (difficulty.preventStarvationDamage()) {
                return 0.0f;
            }

            float health = player.getHealth();
            float threshold = difficulty.getStarvationDamageThreshold();
            if (health <= threshold) {
                return 0.0f;
            }
            return amount;
        }

        if (!source.isScaledWithDifficulty()) {
            return amount;
        }

        float multiplier = difficulty.getDamageMultiplier();
        return amount * multiplier;
    }
}