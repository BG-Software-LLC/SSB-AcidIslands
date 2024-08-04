package com.bgsoftware.ssbacidislands.biomegrid;

import com.bgsoftware.common.reflection.ClassInfo;
import com.bgsoftware.common.reflection.ReflectField;
import com.bgsoftware.common.reflection.ReflectMethod;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;

public class BiomeGridAccessor_116 implements BiomeGridAccessor {

    private static final ReflectField<Object> CRAFT_BIOME_GRID_BIOME_STORAGE = new ReflectField<>(
            new ClassInfo("generator.CustomChunkGenerator$CustomBiomeGrid", ClassInfo.PackageType.CRAFTBUKKIT),
            Object.class, "biome");

    private static final ReflectField<Object[]> BIOME_STORAGE_BIOMES = new ReflectField<>(
            new ClassInfo("BiomeStorage", ClassInfo.PackageType.NMS),
            Object[].class, "h");

    private static final ReflectField<Object> BIOME_STORAGE_REGISTRY = new ReflectField<>(
            new ClassInfo("BiomeStorage", ClassInfo.PackageType.NMS),
            Object.class, "registry");
    private static final ReflectMethod<Object> CRAFT_BLOCK_BIOME_TO_BIOME_BASE = new ReflectMethod<>(
            new ClassInfo("block.CraftBlock", ClassInfo.PackageType.CRAFTBUKKIT),
            "biomeToBiomeBase",
            new ClassInfo("IRegistry", ClassInfo.PackageType.NMS),
            new ClassInfo(Biome.class));

    public static boolean isCompatiable() {
        return CRAFT_BIOME_GRID_BIOME_STORAGE.isValid() && BIOME_STORAGE_BIOMES.isValid() &&
                BIOME_STORAGE_REGISTRY.isValid() && CRAFT_BLOCK_BIOME_TO_BIOME_BASE.isValid();
    }

    private final Object[] biomes;
    private final Object biomesRegistry;

    public BiomeGridAccessor_116(ChunkGenerator.BiomeGrid biomeGrid) {
        Object biomeStorageHandle = CRAFT_BIOME_GRID_BIOME_STORAGE.get(biomeGrid);
        this.biomes = BIOME_STORAGE_BIOMES.get(biomeStorageHandle);
        this.biomesRegistry = BIOME_STORAGE_REGISTRY.get(biomeStorageHandle);
    }

    @Override
    public void fillEntireChunk(Biome biome) {
        Object biomeBase = getBiomeBaseFromBukkit(biome);
        Arrays.fill(this.biomes, biomeBase);
    }

    private Object getBiomeBaseFromBukkit(Biome bukkitBiome) {
        return CRAFT_BLOCK_BIOME_TO_BIOME_BASE.invoke(null, this.biomesRegistry, bukkitBiome);
    }

}
