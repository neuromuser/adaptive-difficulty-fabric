        package com.neuromuser.adaptivedifficulty;

        import com.neuromuser.adaptivedifficulty.command.AdaptiveCommand;
        import com.neuromuser.adaptivedifficulty.event.ExperienceHandler;
        import com.neuromuser.adaptivedifficulty.mixin.LivingEntityAccessor;
        import net.fabricmc.api.ModInitializer;
        import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
        import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
        import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

        public class AdaptiveDifficulty implements ModInitializer {
                public static final String MOD_ID = "adaptivedifficulty";
                public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

                @Override
                public void onInitialize() {
                        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> AdaptiveCommand.register(dispatcher));

                        ServerTickEvents.START_SERVER_TICK.register(server -> server.getPlayerManager().getPlayerList().forEach(PlayerTickHandler::onPlayerTick));

                        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
                                int xp = ((LivingEntityAccessor) entity).invokeGetXpToDrop();
                                ExperienceHandler.onEntityDropExperience(entity, xp);
                        });
                        LOGGER.info("Adaptive Difficulty initialized with {} difficulty levels", Difficulty.values().length);
                }
        }