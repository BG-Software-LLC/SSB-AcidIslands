package com.bgsoftware.ssbacidislands.biomegrid;

import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public class BiomeGridAccessor_117 implements BiomeGridAccessor {

    private final ChunkGenerator.BiomeGrid biomeGrid;

    public BiomeGridAccessor_117(ChunkGenerator.BiomeGrid biomeGrid) {
        this.biomeGrid = biomeGrid;
    }

    @Override
    public void fillEntireChunk(Biome biome) {
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z)
                biomeGrid.setBiome(x, z, biome);
        }
    }

}
