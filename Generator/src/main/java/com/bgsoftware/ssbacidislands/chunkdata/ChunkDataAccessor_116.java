package com.bgsoftware.ssbacidislands.chunkdata;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

public class ChunkDataAccessor_116 implements ChunkDataAccessor {

    private final ChunkGenerator.ChunkData chunkData;

    public ChunkDataAccessor_116(ChunkGenerator.ChunkData chunkData) {
        this.chunkData = chunkData;
    }

    @Override
    public void fillEntireChunkSectionForYLevel(int y, Material blockType) {
        int chunkSectionBase = y >> 4 << 4;
        this.chunkData.setRegion(0, chunkSectionBase, 0, 16, chunkSectionBase + 16, 16, blockType);
    }

    @Override
    public void fillChunkSectionRowsForYLevelRange(int minY, int maxY, Material blockType) {
        if (minY > maxY)
            return;

        this.chunkData.setRegion(0, minY, 0, 16, maxY + 1, 16, blockType);
    }

}
