package com.bgsoftware.ssbacidislands.biomegrid;

import com.bgsoftware.common.reflection.ClassInfo;
import com.bgsoftware.common.reflection.ReflectField;
import com.bgsoftware.common.reflection.ReflectMethod;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;

public class BiomeGridAccessor_Legacy implements BiomeGridAccessor {

    private static final ReflectField<Object[]> CRAFT_BIOME_GRID_BIOME = new ReflectField<>(
            new ClassInfo("generator.CustomChunkGenerator$CustomBiomeGrid", ClassInfo.PackageType.CRAFTBUKKIT),
            Object[].class, "biome");
    private static final ReflectMethod<Object> CRAFT_BLOCK_BIOME_TO_BIOME_BASE = new ReflectMethod<>(
            new ClassInfo("block.CraftBlock", ClassInfo.PackageType.CRAFTBUKKIT),
            "biomeToBiomeBase", Biome.class);

    public static boolean isCompatiable() {
        return CRAFT_BIOME_GRID_BIOME.isValid() && CRAFT_BLOCK_BIOME_TO_BIOME_BASE.isValid();
    }

    private final Object[] biome;

    public BiomeGridAccessor_Legacy(ChunkGenerator.BiomeGrid biomeGrid) {
        this.biome = CRAFT_BIOME_GRID_BIOME.get(biomeGrid);
    }

    @Override
    public void fillEntireChunk(Biome biome) {
        Object biomeBase = getBiomeBaseFromBukkit(biome);
        Arrays.fill(this.biome, biomeBase);
    }

    private static Object getBiomeBaseFromBukkit(Biome bukkitBiome) {
        return CRAFT_BLOCK_BIOME_TO_BIOME_BASE.invoke(null, bukkitBiome);
    }

}
