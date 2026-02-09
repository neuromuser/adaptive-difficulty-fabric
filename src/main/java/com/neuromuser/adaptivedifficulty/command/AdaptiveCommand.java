package com.neuromuser.adaptivedifficulty.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.neuromuser.adaptivedifficulty.Difficulty;
import com.neuromuser.adaptivedifficulty.DifficultyHelper;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class AdaptiveCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        var command = CommandManager.literal("adaptive")
                .executes(ctx -> showDifficulty(ctx, null));

        var targetArgument = CommandManager.argument("target", EntityArgumentType.player())
                .executes(ctx -> showDifficulty(ctx, EntityArgumentType.getPlayer(ctx, "target")));

        for (Difficulty difficulty : Difficulty.values()) {
            String id = difficulty.getId();

            targetArgument = targetArgument.then(
                    CommandManager.literal(id)
                            .requires(source -> source.hasPermissionLevel(2))
                            .executes(ctx -> setDifficulty(ctx, EntityArgumentType.getPlayer(ctx, "target"), difficulty))
            );
        }

        command = command.then(targetArgument.requires(source -> source.hasPermissionLevel(2)));
        dispatcher.register(command);
    }

    private static int showDifficulty(CommandContext<ServerCommandSource> ctx, ServerPlayerEntity target) {
        ServerPlayerEntity player = target;

        if (player == null) {
            try {
                player = ctx.getSource().getPlayerOrThrow();
            } catch (Exception e) {
                ctx.getSource().sendError(Text.literal("This command must be run by a player"));
                return 0;
            }
        }

        Difficulty difficulty = DifficultyHelper.getDifficulty(player);
        String message = target == null ?
                "Your difficulty: " + difficulty.getDisplayName() :
                target.getName().getString() + "'s difficulty: " + difficulty.getDisplayName();

        ctx.getSource().sendFeedback(() -> Text.literal(message), false);
        return 1;
    }

    private static int setDifficulty(CommandContext<ServerCommandSource> ctx, ServerPlayerEntity target, Difficulty difficulty) {
        ServerPlayerEntity player = target;

        if (player == null) {
            try {
                player = ctx.getSource().getPlayerOrThrow();
            } catch (Exception e) {
                ctx.getSource().sendError(Text.literal("This command must be run by a player"));
                return 0;
            }
        }

        DifficultyHelper.setDifficulty(player, difficulty);

        String message = (target == null ? "Set your" : "Set " + target.getName().getString() + "'s") +
                " difficulty to " + difficulty.getDisplayName();

        ctx.getSource().sendFeedback(() -> Text.literal(message), true);
        return 1;
    }
}