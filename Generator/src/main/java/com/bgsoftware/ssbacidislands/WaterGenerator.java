package com.bgsoftware.ssbacidislands;

import com.bgsoftware.ssbacidislands.biomegrid.BiomeGridAccessor;
import com.bgsoftware.ssbacidislands.biomegrid.BiomeGridAccessor_116;
import com.bgsoftware.ssbacidislands.biomegrid.BiomeGridAccessor_117;
import com.bgsoftware.ssbacidislands.biomegrid.BiomeGridAccessor_Legacy;
import com.bgsoftware.ssbacidislands.chunkdata.ChunkDataAccessor;
import com.bgsoftware.ssbacidislands.chunkdata.ChunkDataAccessor_116;
import com.bgsoftware.ssbacidislands.chunkdata.ChunkDataAccessor_Legacy;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public final class WaterGenerator extends ChunkGenerator {

    private static final Biome NETHER_BIOME = getNetherBiome();
    private static final Function<ChunkData, ChunkDataAccessor> CHUNK_DATA_ACCESSOR_CREATOR = initializeChunkDataAccessorCreator();
    private static final Function<BiomeGrid, BiomeGridAccessor> BIOME_GRID_ACCESSOR_CREATOR = initializeBiomeGridAccessorCreator();

    private static Function<ChunkData, ChunkDataAccessor> initializeChunkDataAccessorCreator() {
        if (ChunkDataAccessor_Legacy.isCompatiable())
            return ChunkDataAccessor_Legacy::new;

        return ChunkDataAccessor_116::new;
    }

    private static Function<BiomeGrid, BiomeGridAccessor> initializeBiomeGridAccessorCreator() {
        if (BiomeGridAccessor_Legacy.isCompatiable())
            return BiomeGridAccessor_Legacy::new;

        if (BiomeGridAccessor_116.isCompatiable())
            return BiomeGridAccessor_116::new;

        return BiomeGridAccessor_117::new;
    }

    private static int ISLANDS_HEIGHT;
    private static Location SPAWN_LOCATION;


    public WaterGenerator(SuperiorSkyblock plugin) {
        ISLANDS_HEIGHT = plugin.getSettings().getIslandHeight() - 3;
        SPAWN_LOCATION = new Location(null, 0, ISLANDS_HEIGHT, 0);
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        SPAWN_LOCATION.setWorld(world);
        return SPAWN_LOCATION;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        ChunkData chunkData = createChunkData(world);

        Material blockType;
        Material groundBlockType;
        Biome worldBiome;

        switch (world.getEnvironment()) {
            case NORMAL:
                blockType = Material.WATER;
                groundBlockType = Material.SAND;
                worldBiome = Biome.PLAINS;
                break;
            case NETHER:
                blockType = Material.LAVA;
                groundBlockType = Material.NETHERRACK;
                worldBiome = NETHER_BIOME;
                break;
            default:
                return chunkData;
        }

        ChunkDataAccessor chunkDataAccessor = CHUNK_DATA_ACCESSOR_CREATOR.apply(chunkData);
        BiomeGridAccessor biomeGridAccessor = BIOME_GRID_ACCESSOR_CREATOR.apply(biomes);

        biomeGridAccessor.fillEntireChunk(worldBiome);

        // Optimization - all the sections between the min-height to islands-height can be
        // fully set with the block type.
        for (int y = 0; y + 16 < ISLANDS_HEIGHT; y += 16) {
            chunkDataAccessor.fillEntireChunkSectionForYLevel(y, blockType);
        }

        // Last section have ground - layer of bedrock, then sand / netherrack
        chunkDataAccessor.fillChunkSectionRowsForYLevel(0, Material.BEDROCK);
        chunkDataAccessor.fillChunkSectionRowsForYLevel(1, groundBlockType);

        // For the last section, we need to determine what is faster - filling entire chunk section
        // with water, then setting rows to air, or the opposite. This is only needed in case the
        // island height is not the last block level in the chunk section.
        int islandHeightOffsetFromChunkSection = ISLANDS_HEIGHT & 0xf;
        if (islandHeightOffsetFromChunkSection < 0xf) {
            int islandHeightChunkSectionBase = ISLANDS_HEIGHT - islandHeightOffsetFromChunkSection;
            if (islandHeightOffsetFromChunkSection > 8) {
                // island height is larger than 8, which means most of the chunk section is water.
                chunkDataAccessor.fillEntireChunkSectionForYLevel(ISLANDS_HEIGHT, blockType);
                chunkDataAccessor.fillChunkSectionRowsForYLevelRange(ISLANDS_HEIGHT, islandHeightChunkSectionBase + 0xf, Material.AIR);
            } else {
                // island height is lower than 8, which means most of the chunk section is air.
                chunkDataAccessor.fillChunkSectionRowsForYLevelRange(islandHeightChunkSectionBase, ISLANDS_HEIGHT, blockType);
            }
        }

        if (chunkX == 0 && chunkZ == 0) {
            // Set bedrock at spawn
            chunkData.setBlock(0, ISLANDS_HEIGHT + 3, 0, Material.BEDROCK);
        }

        return chunkData;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return Collections.emptyList();
    }

    private static Biome getNetherBiome() {
        try {
            return Biome.valueOf("NETHER_WASTES");
        } catch (Throwable ex) {
            return Biome.valueOf("HELL");
        }
    }

}
