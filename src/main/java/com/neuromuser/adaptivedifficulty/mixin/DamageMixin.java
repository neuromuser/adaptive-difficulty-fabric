package com.neuromuser.adaptivedifficulty.mixin;

import com.neuromuser.adaptivedifficulty.DamageHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public class DamageMixin {

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private float modifyDamageAmount(float amount, DamageSource source) {
        return DamageHandler.modifyDamage((LivingEntity) (Object) this, source, amount);
    }
}