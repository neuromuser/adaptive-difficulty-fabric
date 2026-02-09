package com.neuromuser.adaptivedifficulty.network;

import com.neuromuser.adaptivedifficulty.Difficulty;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;

public class PlayerDifficultyData implements AutoSyncedComponent {
    private Difficulty difficulty = Difficulty.NORMAL;

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setDifficulty(String id) {
        this.difficulty = Difficulty.fromId(id);
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        if (tag.contains("Difficulty", 8)) {
            String id = tag.getString("Difficulty");
            this.difficulty = Difficulty.fromId(id);
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
        tag.putString("Difficulty", difficulty.getId());
    }
}