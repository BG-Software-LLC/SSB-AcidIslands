package com.bgsoftware.ssbacidislands.chunkdata;

import org.bukkit.Material;

public interface ChunkDataAccessor {

    void fillEntireChunkSectionForYLevel(int y, Material blockType);

    void fillChunkSectionRowsForYLevelRange(int minY, int maxY, Material blockType);

    default void fillChunkSectionRowsForYLevel(int y, Material blockType) {
        fillChunkSectionRowsForYLevelRange(y, y, blockType);
    }

}
