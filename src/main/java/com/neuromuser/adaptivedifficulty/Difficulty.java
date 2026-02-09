package com.neuromuser.adaptivedifficulty;

public enum Difficulty {
    PEACEFUL("peaceful", "Peaceful", 0.0f, Float.MAX_VALUE, 1.0f, true,0.5f, 1.0f),
    EASY("easy", "Easy", 0.5f, 10.0f, 1.0f, false, 0.75f, 1.0f),
    NORMAL("normal", "Normal", 1.0f, 1.0f, 1.0f, false, 1.0f, 1.0f),
    HARD("hard", "Hard", 1.5f, 0.0f, 1.25f, false, 1.25f, 1.4f),
    NIGHTMARE("nightmare", "Nightmare",  2.5f, 0.0f, 2.0f, false, 2.0f, 2.5f);

    private final String id;
    private final String displayName;
    private final float damageMultiplier;
    private final float starvationDamageThreshold;
    private final float exhaustionModifier;
    private final boolean preventStarvationDamage;
    private final float expMultiplier;
    private final float tieredzRarityBuff;

    Difficulty(String id, String displayName, float damageMultiplier,
               float starvationDamageThreshold, float exhaustionModifier, boolean preventStarvationDamage
            , float expMultiplier, float tieredzRarityBuff) {
        this.id = id;
        this.displayName = displayName;
        this.damageMultiplier = damageMultiplier;
        this.starvationDamageThreshold = starvationDamageThreshold;
        this.exhaustionModifier = exhaustionModifier;
        this.preventStarvationDamage = preventStarvationDamage;
        this.expMultiplier = expMultiplier;
        this.tieredzRarityBuff = tieredzRarityBuff;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    public float getStarvationDamageThreshold() {
        return starvationDamageThreshold;
    }

    public float getExhaustionModifier() {
        return exhaustionModifier;
    }

    public boolean preventStarvationDamage() {
        return preventStarvationDamage;
    }

    public float getExpMultiplier() {
        return expMultiplier;
    }

    public float getTieredzRarityBuff() {
        return tieredzRarityBuff;
    }


    public static Difficulty fromId(String id) {
        for (Difficulty diff : values()) {
            if (diff.id.equals(id)) {
                return diff;
            }
        }
        return NORMAL;
    }

    public static Difficulty fromMinecraftDifficulty(net.minecraft.world.Difficulty difficulty) {
        return switch (difficulty) {
            case PEACEFUL -> PEACEFUL;
            case EASY -> EASY;
            case NORMAL -> NORMAL;
            case HARD -> HARD;
        };
    }
}