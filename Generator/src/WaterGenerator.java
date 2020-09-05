import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SuppressWarnings({"deprecation", "unused", "NullableProblems"})
public final class WaterGenerator extends ChunkGenerator {

    private final double islandsLevel;

    public WaterGenerator(JavaPlugin plugin){
        File settingsFile = new File(plugin.getDataFolder(), "config.yml");

        if(!settingsFile.exists()){
            islandsLevel = 97;
        }
        else{
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(settingsFile);
            islandsLevel = cfg.getDouble("islands-height") - 3;
        }
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world, 0, islandsLevel, 0);
    }

    public byte[][] generateBlockSections(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        byte[][] blockSections = new byte[world.getMaxHeight() / 16][];

        Material blockToSet = null;
        Biome biomeToSet = null;

        switch (world.getEnvironment()){
            case NETHER: {
                blockToSet = Material.LAVA;
                biomeToSet = getNetherBiome();
                break;
            }
            case NORMAL: {
                blockToSet = Material.WATER;
                biomeToSet = Biome.PLAINS;
                break;
            }
        }

        if(blockToSet != null && biomeToSet != null){
            for(int x = 0; x < 16; x++){
                for(int z = 0; z < 16; z++){
                    for(int y = 0; y < world.getMaxHeight(); y++){
                        biomes.setBiome(x, y, z, biomeToSet);
                        switch (y){
                            case 1:
                                setBlock(blockSections, x, y, z, 12);
                                break;
                            case 0:
                                setBlock(blockSections, x, y, z, 7);
                                break;
                            default:
                                if(y <= islandsLevel){
                                    setBlock(blockSections, x, y, z, blockToSet.getId());
                                }
                                break;
                        }
                    }
                }
            }
        }

        return blockSections;
    }

    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biomes) {
        ChunkData chunkData = createChunkData(world);

        Material blockToSet = null;
        Biome biomeToSet = null;

        switch (world.getEnvironment()){
            case NETHER: {
                blockToSet = Material.LAVA;
                biomeToSet = getNetherBiome();
                break;
            }
            case NORMAL: {
                blockToSet = Material.WATER;
                biomeToSet = Biome.PLAINS;
                break;
            }
        }

        if(blockToSet != null && biomeToSet != null){
            for(int x = 0; x < 16; x++){
                for(int z = 0; z < 16; z++){
                    for(int y = 0; y < world.getMaxHeight(); y++){
                        biomes.setBiome(x, y, z, biomeToSet);
                        switch (y){
                            case 1:
                                chunkData.setBlock(x, y, z, Material.SAND);
                                break;
                            case 0:
                                chunkData.setBlock(x, y, z, Material.BEDROCK);
                                break;
                            default:
                                if(y <= islandsLevel){
                                    chunkData.setBlock(x, y, z, blockToSet);
                                }
                                break;
                        }
                    }
                }
            }
        }


        return chunkData;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<>();
    }

    @SuppressWarnings("SameParameterValue")
    private void setBlock(byte[][] blocks, int x, int y, int z, int blockId){
        if(blocks[y >> 4] == null)
            blocks[y >> 4] = new byte[4096];

        blocks[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) blockId;
    }

    private static Biome getNetherBiome(){
        try{
            return Biome.valueOf("NETHER_WASTES");
        }catch (Throwable ex){
            return Biome.valueOf("HELL");
        }
    }

}
