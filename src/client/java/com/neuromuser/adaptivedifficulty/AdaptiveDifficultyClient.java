package com.neuromuser.adaptivedifficulty;

import net.fabricmc.api.ClientModInitializer;

public class AdaptiveDifficultyClient implements ClientModInitializer {
        @Override
        public void onInitializeClient() {
                AdaptiveDifficulty.LOGGER.info(
                    "Client initialization for {}!", AdaptiveDifficulty.MOD_ID);
        }
}
