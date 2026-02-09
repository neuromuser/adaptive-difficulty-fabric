package com.neuromuser.adaptivedifficulty;

import com.neuromuser.adaptivedifficulty.network.AdaptiveComponents;
import com.neuromuser.adaptivedifficulty.network.PlayerDifficultyData;
import net.minecraft.entity.player.PlayerEntity;

public class DifficultyHelper {

    public static PlayerDifficultyData getData(PlayerEntity player) {
        return AdaptiveComponents.PLAYER_DIFFICULTY.get(player);
    }

    public static Difficulty getDifficulty(PlayerEntity player) {
        return getData(player).getDifficulty();
    }

    public static void setDifficulty(PlayerEntity player, Difficulty difficulty) {
        getData(player).setDifficulty(difficulty);
        AdaptiveComponents.PLAYER_DIFFICULTY.sync(player);
    }

    public static void initialize(PlayerEntity player) {
        setDifficulty(player, Difficulty.NORMAL);
    }
}