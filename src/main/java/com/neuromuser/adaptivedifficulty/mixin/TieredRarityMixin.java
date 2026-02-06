package com.neuromuser.adaptivedifficulty.mixin;

import com.neuromuser.adaptivedifficulty.DifficultyHelper;
import com.neuromuser.adaptivedifficulty.Difficulty;
import draylar.tiered.api.ModifierUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ModifierUtils.class)
public class TieredRarityMixin {

    @Unique
    private static ThreadLocal<PlayerEntity> adaptiveDifficulty$currentPlayer = new ThreadLocal<>();

    @Unique
    private static ThreadLocal<Float> adaptiveDifficulty$currentBuff = new ThreadLocal<>();

    @Inject(
            method = "getRandomAttributeIDFor",
            at = @At("HEAD")
    )
    private static void adaptiveDifficulty$capturePlayer(
            @Nullable PlayerEntity playerEntity,
            Item item,
            boolean reforge,
            CallbackInfoReturnable<Identifier> cir
    ) {
        adaptiveDifficulty$currentPlayer.set(playerEntity);
        if (playerEntity != null) {
            Difficulty difficulty = DifficultyHelper.getDifficulty(playerEntity);
            adaptiveDifficulty$currentBuff.set(difficulty.getTieredzRarityBuff());
        } else {
            adaptiveDifficulty$currentBuff.set(1.0f);
        }
    }

    @Inject(
            method = "getRandomAttributeIDFor",
            at = @At("TAIL")
    )
    private static void adaptiveDifficulty$clearThreadLocal(
            @Nullable PlayerEntity playerEntity,
            Item item,
            boolean reforge,
            CallbackInfoReturnable<Identifier> cir
    ) {
        adaptiveDifficulty$currentPlayer.remove();
        adaptiveDifficulty$currentBuff.remove();
    }

    @ModifyArg(
            method = "getRandomAttributeIDFor",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/libz/util/SortList;concurrentSort(Ljava/util/List;[Ljava/util/List;)V"
            ),
            index = 0
    )
    private static List<Integer> adaptiveDifficulty$modifyWeights(List<Integer> attributeWeights) {
        PlayerEntity player = adaptiveDifficulty$currentPlayer.get();
        Float buff = adaptiveDifficulty$currentBuff.get();

        if (player != null && buff != null && buff != 1.0f && attributeWeights != null && !attributeWeights.isEmpty()) {
            int maxWeight = 0;
            for (int weight : attributeWeights) {
                if (weight > maxWeight) {
                    maxWeight = weight;
                }
            }

            for (int i = 0; i < attributeWeights.size(); ++i) {
                int currentWeight = attributeWeights.get(i);
                if (currentWeight > maxWeight / 3) {
                    int modifiedWeight = Math.max(1, (int) (currentWeight / buff));
                    attributeWeights.set(i, modifiedWeight);
                }
            }
        }

        return attributeWeights;
    }
}