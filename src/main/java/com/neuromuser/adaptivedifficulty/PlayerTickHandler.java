package com.neuromuser.adaptivedifficulty;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerTickHandler {

    public static void onPlayerTick(ServerPlayerEntity player) {
        Difficulty difficulty = DifficultyHelper.getDifficulty(player);
        HungerManager hungerManager = player.getHungerManager();
        int foodLevel = hungerManager.getFoodLevel();

        // Peaceful mode food regeneration
        if (difficulty == Difficulty.PEACEFUL) {
            hungerManager.setSaturationLevel(1.0f);
            int regenRate = difficulty.getFoodRegenerationRate();
            if (regenRate > 0 && player.getServer().getTicks() % 20 == 0) {
                hungerManager.setFoodLevel(Math.min(foodLevel + regenRate, 20));
            }
        }

        // Starvation damage handling
        if (foodLevel <= 0) {
            float health = player.getHealth();
            float threshold = difficulty.getStarvationDamageThreshold();

            if (health > threshold && !difficulty.preventStarvationDamage()) {
                player.damage(player.getDamageSources().starve(), 1.0f);
            }
        }
    }
}