package com.neuromuser.adaptivedifficulty;

import com.neuromuser.adaptivedifficulty.network.AdaptiveComponents;
import com.neuromuser.adaptivedifficulty.network.PlayerDifficultyData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

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

    public static void setDifficulty(PlayerEntity player, String id) {
        getData(player).setDifficulty(id);
        AdaptiveComponents.PLAYER_DIFFICULTY.sync(player);
    }

    public static void initialize(PlayerEntity player) {
        PlayerDifficultyData data = getData(player);
        World world = player.getWorld();
        net.minecraft.world.Difficulty worldDifficulty = world.getDifficulty();

        // Initialize to world difficulty if not set
        setDifficulty(player, Difficulty.fromMinecraftDifficulty(worldDifficulty));
    }
}