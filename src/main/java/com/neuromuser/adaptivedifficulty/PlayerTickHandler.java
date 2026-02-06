package com.neuromuser.adaptivedifficulty;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

public class PlayerTickHandler {

    public static void onPlayerTick(ServerPlayerEntity player) {
        Difficulty difficulty = DifficultyHelper.getDifficulty(player);
        HungerManager hungerManager = player.getHungerManager();
        int foodLevel = hungerManager.getFoodLevel();

        if (difficulty == Difficulty.PEACEFUL) {
            hungerManager.setSaturationLevel(1.0f);
            if (Objects.requireNonNull(player.getServer()).getTicks() % 20 == 0) {
                hungerManager.setFoodLevel(Math.min(foodLevel + 1, 20));
            }
        }

        if (foodLevel <= 0) {
            float health = player.getHealth();
            float threshold = difficulty.getStarvationDamageThreshold();

            if (health > threshold && !difficulty.preventStarvationDamage()) {
                player.damage(player.getDamageSources().starve(), 1.0f);
            }
        }
    }
}