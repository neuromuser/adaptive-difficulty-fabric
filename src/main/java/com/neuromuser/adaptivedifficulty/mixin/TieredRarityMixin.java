package com.neuromuser.adaptivedifficulty.mixin;

import com.neuromuser.adaptivedifficulty.DifficultyHelper;
import com.neuromuser.adaptivedifficulty.Difficulty;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collections;
import java.util.List;

@Mixin(targets = "draylar.tiered.api.ModifierUtils")
public class TieredRarityMixin {

    @Inject(
            method = "getRandomAttributeIDFor",
            at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", ordinal = 2),
            locals = LocalCapture.CAPTURE_FAILSOFT
    )
    private static void applyAdaptiveDifficultyBuff(
            @Nullable PlayerEntity playerEntity,
            Item item,
            boolean reforge,
            CallbackInfoReturnable<Identifier> cir,
            List<Identifier> potentialAttributes,
            List<Integer> attributeWeights
    ) {
        if (playerEntity != null && !attributeWeights.isEmpty()) {
            Difficulty currentDifficulty = DifficultyHelper.getDifficulty(playerEntity);
            float buff = currentDifficulty.getTieredzRarityBuff();

            if (buff != 1.0f) {
                int maxWeight = Collections.max(attributeWeights);

                for (int i = 0; i < attributeWeights.size(); ++i) {
                    if (attributeWeights.get(i) > maxWeight / 3) {
                        int currentWeight = attributeWeights.get(i);
                        int modifiedWeight = Math.max(1, (int) (currentWeight / buff));
                        attributeWeights.set(i, modifiedWeight);
                    }
                }
            }
        }
    }
}