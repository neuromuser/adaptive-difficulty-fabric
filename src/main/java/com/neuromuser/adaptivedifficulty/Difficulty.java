package com.neuromuser.adaptivedifficulty;

public enum Difficulty {
    PEACEFUL("peaceful", "Peaceful", 0, 0.0f, Float.MAX_VALUE, true, false, 0.5f, 1),
    EASY("easy", "Easy", 1, 0.5f, 10.0f, false, true, 0.75f, 0),
    NORMAL("normal", "Normal", 2, 1.0f, 1.0f, false, true, 1.0f, 0),
    HARD("hard", "Hard", 3, 1.5f, 0.0f, false, true, 1.25f, 0),
    NIGHTMARE("nightmare", "Nightmare", 4, 2.5f, 0.0f, false, true, 2.0f, 0);

    private final String id;
    private final String displayName;
    private final int numericId;
    private final float damageMultiplier;
    private final float starvationDamageThreshold;
    private final boolean preventStarvationDamage;
    private final boolean allowsHostileMobTargeting;
    private final float expMultiplier;
    private final int foodRegenerationRate;

    Difficulty(String id, String displayName, int numericId, float damageMultiplier,
               float starvationDamageThreshold, boolean preventStarvationDamage,
               boolean allowsHostileMobTargeting, float expMultiplier, int foodRegenerationRate) {
        this.id = id;
        this.displayName = displayName;
        this.numericId = numericId;
        this.damageMultiplier = damageMultiplier;
        this.starvationDamageThreshold = starvationDamageThreshold;
        this.preventStarvationDamage = preventStarvationDamage;
        this.allowsHostileMobTargeting = allowsHostileMobTargeting;
        this.expMultiplier = expMultiplier;
        this.foodRegenerationRate = foodRegenerationRate;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getNumericId() {
        return numericId;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    public float getStarvationDamageThreshold() {
        return starvationDamageThreshold;
    }

    public boolean preventStarvationDamage() {
        return preventStarvationDamage;
    }

    public boolean allowsHostileMobTargeting() {
        return allowsHostileMobTargeting;
    }

    public float getExpMultiplier() {
        return expMultiplier;
    }

    public int getFoodRegenerationRate() {
        return foodRegenerationRate;
    }

    public net.minecraft.world.Difficulty getMinecraftDifficulty() {
        return switch (numericId) {
            case 0 -> net.minecraft.world.Difficulty.PEACEFUL;
            case 1 -> net.minecraft.world.Difficulty.EASY;
            case 2 -> net.minecraft.world.Difficulty.NORMAL;
            default -> net.minecraft.world.Difficulty.HARD;
        };
    }

    public static Difficulty fromId(String id) {
        for (Difficulty diff : values()) {
            if (diff.id.equals(id)) {
                return diff;
            }
        }
        return NORMAL;
    }

    public static Difficulty fromNumericId(int id) {
        for (Difficulty diff : values()) {
            if (diff.numericId == id) {
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