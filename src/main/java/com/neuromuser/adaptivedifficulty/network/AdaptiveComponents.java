package com.neuromuser.adaptivedifficulty.network;


import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class AdaptiveComponents implements EntityComponentInitializer {
    public static final ComponentKey<PlayerDifficultyData> PLAYER_DIFFICULTY =
            ComponentRegistry.getOrCreate(Identifier.of("adaptivedifficulty", "player_difficulty"), PlayerDifficultyData.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(PLAYER_DIFFICULTY, player -> new PlayerDifficultyData(), RespawnCopyStrategy.ALWAYS_COPY);
    }
}