package com.intezya.harderthanhard.spawn;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class NetherSpawnTuner {
    public static void tune() {
        BiomeModifications.addSpawn(
            BiomeSelectors.foundInTheNether(),
            SpawnGroup.MONSTER,
            EntityType.GHAST,
            1000,
            4,
            8
        );
    }
}
