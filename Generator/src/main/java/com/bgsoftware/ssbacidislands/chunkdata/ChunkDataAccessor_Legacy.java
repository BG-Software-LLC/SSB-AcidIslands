package com.bgsoftware.ssbacidislands.chunkdata;

import com.bgsoftware.common.reflection.ClassInfo;
import com.bgsoftware.common.reflection.ReflectField;
import com.google.common.base.Preconditions;
import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;

public class ChunkDataAccessor_Legacy implements ChunkDataAccessor {

    private static final ReflectField<char[][]> CRAFT_CHUNK_DATA_SECTIONS = new ReflectField<>(
            new ClassInfo("generator.CraftChunkData", ClassInfo.PackageType.CRAFTBUKKIT),
            char[][].class, "sections");

    public static boolean isCompatiable() {
        return CRAFT_CHUNK_DATA_SECTIONS.isValid();
    }

    private final char[][] sections;

    public ChunkDataAccessor_Legacy(ChunkGenerator.ChunkData chunkData) {
        this.sections = CRAFT_CHUNK_DATA_SECTIONS.get(chunkData);
    }

    @Override
    public void fillEntireChunkSectionForYLevel(int y, Material blockType) {
        char blockId = getBlockId(blockType);
        char[] section = getSectionForY(y);
        Arrays.fill(section, blockId);
    }

    @Override
    public void fillChunkSectionRowsForYLevelRange(int minY, int maxY, Material blockType) {
        if (minY > maxY)
            return;

        Preconditions.checkArgument((minY >> 4) == (maxY >> 4),
                "minY: " + minY + ", maxY: " + maxY + " are not in the same chunk section");

        char blockId = getBlockId(blockType);
        char[] section = getSectionForY(minY);

        int minIndex = (minY & 15) << 8;
        int maxIndex = (maxY & 15) << 8 | 0xf << 4 | 0xf;
        for (int i = minIndex; i <= maxIndex; ++i)
            section[i] = blockId;
    }

    private char[] getSectionForY(int y) {
        char[] section = this.sections[y >> 4];
        if (section == null)
            section = this.sections[y >> 4] = new char[4096];
        return section;
    }

    private static char getBlockId(Material blockType) {
        return (char) (blockType.getId() << 4);
    }

}
