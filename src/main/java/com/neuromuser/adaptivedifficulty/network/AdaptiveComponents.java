package com.neuromuser.adaptivedifficulty.network;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class AdaptiveComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerDifficultyData> PLAYER_DIFFICULTY =
            ComponentRegistry.getOrCreate(new Identifier("adaptivedifficulty", "player_difficulty"), PlayerDifficultyData.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYER_DIFFICULTY, player -> new PlayerDifficultyData(), RespawnCopyStrategy.ALWAYS_COPY);
    }
}