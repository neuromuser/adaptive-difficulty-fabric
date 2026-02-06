package com.neuromuser.adaptivedifficulty.mixin;

import com.neuromuser.adaptivedifficulty.Difficulty;
import com.neuromuser.adaptivedifficulty.DifficultyHelper;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @ModifyArgs(
            method = "addExhaustion",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;addExhaustion(F)V")
    )
    private void modifyExhaustionAmount(Args args) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        float originalExhaustion = args.get(0);

        Difficulty difficulty = DifficultyHelper.getDifficulty(player);
        float modifier = difficulty.getExhaustionModifier();

        args.set(0, originalExhaustion * modifier);
    }
}