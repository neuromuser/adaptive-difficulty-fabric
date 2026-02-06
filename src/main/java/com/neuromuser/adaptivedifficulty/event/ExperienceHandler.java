package com.neuromuser.adaptivedifficulty.event;

import com.neuromuser.adaptivedifficulty.Difficulty;
import com.neuromuser.adaptivedifficulty.DifficultyHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;

import java.util.List;

public class ExperienceHandler {

    private static final double NEARBY_RANGE = 16.0;

    public static void onEntityDropExperience(LivingEntity entity, int experience) {
        if (experience <= 0 || entity.getWorld().isClient) {
            return;
        }

        // Get the highest difficulty multiplier from nearby players
        float maxMultiplier = 1.0f;

        List<ServerPlayerEntity> nearbyPlayers = entity.getWorld().getEntitiesByClass(
                ServerPlayerEntity.class,
                new Box(entity.getPos().subtract(NEARBY_RANGE, NEARBY_RANGE, NEARBY_RANGE),
                        entity.getPos().add(NEARBY_RANGE, NEARBY_RANGE, NEARBY_RANGE)),
                player -> true
        );

        for (ServerPlayerEntity player : nearbyPlayers) {
            Difficulty difficulty = DifficultyHelper.getDifficulty(player);
            float multiplier = difficulty.getExpMultiplier();
            if (multiplier > maxMultiplier) {
                maxMultiplier = multiplier;
            }
        }

        if (maxMultiplier != 1.0f) {
            int bonusExp = Math.round(experience * (maxMultiplier - 1.0f));
            if (bonusExp > 0) {
                ExperienceOrbEntity.spawn((ServerWorld) entity.getWorld(), entity.getPos(), bonusExp);
            }
        }
    }
}